package com.bigoat.bbc.http;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.logging.Level;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static java.util.Collections.unmodifiableList;

/**
 * Service adapts a Java interface to HTTP calls by using annotations on the declared methods to
 * define how requests are made. Create instances using {@linkplain Builder
 * the builder} and pass your interface to {@link #create} to generate an implementation.
 * <p>
 * For example,
 * <pre><code>
 * Service service = new Service.Builder()
 *     .baseUrl("https://api.example.com/")
 *     .addConverterFactory(GsonConverterFactory.create())
 *     .build();
 *
 * MyApi api = service.create(MyApi.class);
 * Response&lt;User&gt; user = api.getUser().execute();
 * </code></pre>
 *
 * @author Bob Lee (bob@squareup.com)
 * @author Jake Wharton (jw@squareup.com)
 */
public final class Service {
  private final Map<Method, ServiceMethod<?>> serviceMethodCache = new ConcurrentHashMap<>();

  final okhttp3.Call.Factory callFactory;
  final HttpUrl baseUrl;
  String serverUrl = "";
  final List<Converter.Factory> converterFactories;
  final List<CallAdapter.Factory> callAdapterFactories;
  final @Nullable
  Executor callbackExecutor;
  final boolean validateEagerly;
  final boolean serverUrlEnable;

  Service(okhttp3.Call.Factory callFactory, HttpUrl baseUrl,
          List<Converter.Factory> converterFactories, List<CallAdapter.Factory> callAdapterFactories,
          @Nullable Executor callbackExecutor, boolean validateEagerly, boolean serverUrlEnable) {
    this.callFactory = callFactory;
    this.baseUrl = baseUrl;
    this.converterFactories = converterFactories; // Copy+unmodifiable at call site.
    this.callAdapterFactories = callAdapterFactories; // Copy+unmodifiable at call site.
    this.callbackExecutor = callbackExecutor;
    this.validateEagerly = validateEagerly;
    this.serverUrlEnable = serverUrlEnable;
  }

  @SuppressWarnings("unchecked") // Single-interface proxy creation guarded by parameter safety.
  public <T> T create(final Class<T> service) {
    Utils.validateServiceInterface(service);
    if (validateEagerly) {
      eagerlyValidateMethods(service);
    }
    return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
        new InvocationHandler() {
          private final Platform platform = Platform.get();
          private final Object[] emptyArgs = new Object[0];

          @Override public Object invoke(Object proxy, Method method, @Nullable Object[] args)
              throws Throwable {
            // If the method is a method from Object then defer to normal invocation.
            if (method.getDeclaringClass() == Object.class) {
              return method.invoke(this, args);
            }
            if (platform.isDefaultMethod(method)) {
              return platform.invokeDefaultMethod(method, service, proxy, args);
            }
            return loadServiceMethod(service, method).invoke(args != null ? args : emptyArgs);
          }
        });
  }

  private void eagerlyValidateMethods(Class<?> service) {
    Platform platform = Platform.get();
    for (Method method : service.getDeclaredMethods()) {
      if (!platform.isDefaultMethod(method)) {
        loadServiceMethod(method);
      }
    }
  }

  ServiceMethod<?> loadServiceMethod(Class<?> service, Method method) {
    if (serverUrlEnable) {
      Annotation annotation = service.getAnnotation(ServiceUrl.class);
      if (annotation != null) {
        serverUrl = ((ServiceUrl) annotation).value();
        if (!serverUrl.endsWith("/")) {
          throw new IllegalArgumentException("ServiceUrl must end in /: " + serverUrl);
        }
      }
    }

    return loadServiceMethod(method);
  }

    ServiceMethod<?> loadServiceMethod(Method method) {
    ServiceMethod<?> result = serviceMethodCache.get(method);
    if (result != null) return result;

    synchronized (serviceMethodCache) {
      result = serviceMethodCache.get(method);
      if (result == null) {
        result = ServiceMethod.parseAnnotations(this, method);
        serviceMethodCache.put(method, result);
      }
    }
    return result;
  }

  /**
   * The factory used to create {@linkplain okhttp3.Call OkHttp calls} for sending a HTTP requests.
   * Typically an instance of {@link OkHttpClient}.
   */
  public okhttp3.Call.Factory callFactory() {
    return callFactory;
  }

  /** The API base URL. */
  public HttpUrl baseUrl() {
    return baseUrl;
  }

  /**
   * Returns a list of the factories tried when creating a
   * {@linkplain #callAdapter(Type, Annotation[])} call adapter}.
   */
  public List<CallAdapter.Factory> callAdapterFactories() {
    return callAdapterFactories;
  }

  /**
   * Returns the {@link CallAdapter} for {@code returnType} from the available {@linkplain
   * #callAdapterFactories() factories}.
   *
   * @throws IllegalArgumentException if no call adapter available for {@code type}.
   */
  public CallAdapter<?, ?> callAdapter(Type returnType, Annotation[] annotations) {
    return nextCallAdapter(null, returnType, annotations);
  }

  /**
   * Returns the {@link CallAdapter} for {@code returnType} from the available {@linkplain
   * #callAdapterFactories() factories} except {@code skipPast}.
   *
   * @throws IllegalArgumentException if no call adapter available for {@code type}.
   */
  public CallAdapter<?, ?> nextCallAdapter(@Nullable CallAdapter.Factory skipPast, Type returnType,
      Annotation[] annotations) {
    Utils.checkNotNull(returnType, "returnType == null");
    Utils.checkNotNull(annotations, "annotations == null");

    int start = callAdapterFactories.indexOf(skipPast) + 1;
    for (int i = start, count = callAdapterFactories.size(); i < count; i++) {
      CallAdapter<?, ?> adapter = callAdapterFactories.get(i).get(returnType, annotations, this);
      if (adapter != null) {
        return adapter;
      }
    }

    StringBuilder builder = new StringBuilder("Could not locate call adapter for ")
        .append(returnType)
        .append(".\n");
    if (skipPast != null) {
      builder.append("  Skipped:");
      for (int i = 0; i < start; i++) {
        builder.append("\n   * ").append(callAdapterFactories.get(i).getClass().getName());
      }
      builder.append('\n');
    }
    builder.append("  Tried:");
    for (int i = start, count = callAdapterFactories.size(); i < count; i++) {
      builder.append("\n   * ").append(callAdapterFactories.get(i).getClass().getName());
    }
    throw new IllegalArgumentException(builder.toString());
  }

  /**
   * Returns an unmodifiable list of the factories tried when creating a
   * {@linkplain #requestBodyConverter(Type, Annotation[], Annotation[]) request body converter}, a
   * {@linkplain #responseBodyConverter(Type, Annotation[]) response body converter}, or a
   * {@linkplain #stringConverter(Type, Annotation[]) string converter}.
   */
  public List<Converter.Factory> converterFactories() {
    return converterFactories;
  }

  /**
   * Returns a {@link Converter} for {@code type} to {@link RequestBody} from the available
   * {@linkplain #converterFactories() factories}.
   *
   * @throws IllegalArgumentException if no converter available for {@code type}.
   */
  public <T> Converter<T, RequestBody> requestBodyConverter(Type type,
                                                            Annotation[] parameterAnnotations, Annotation[] methodAnnotations) {
    return nextRequestBodyConverter(null, type, parameterAnnotations, methodAnnotations);
  }

  /**
   * Returns a {@link Converter} for {@code type} to {@link RequestBody} from the available
   * {@linkplain #converterFactories() factories} except {@code skipPast}.
   *
   * @throws IllegalArgumentException if no converter available for {@code type}.
   */
  public <T> Converter<T, RequestBody> nextRequestBodyConverter(
      @Nullable Converter.Factory skipPast, Type type, Annotation[] parameterAnnotations,
      Annotation[] methodAnnotations) {
    Utils.checkNotNull(type, "type == null");
    Utils.checkNotNull(parameterAnnotations, "parameterAnnotations == null");
    Utils.checkNotNull(methodAnnotations, "methodAnnotations == null");

    int start = converterFactories.indexOf(skipPast) + 1;
    for (int i = start, count = converterFactories.size(); i < count; i++) {
      Converter.Factory factory = converterFactories.get(i);
      Converter<?, RequestBody> converter =
          factory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, this);
      if (converter != null) {
        //noinspection unchecked
        return (Converter<T, RequestBody>) converter;
      }
    }

    StringBuilder builder = new StringBuilder("Could not locate RequestBody converter for ")
        .append(type)
        .append(".\n");
    if (skipPast != null) {
      builder.append("  Skipped:");
      for (int i = 0; i < start; i++) {
        builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
      }
      builder.append('\n');
    }
    builder.append("  Tried:");
    for (int i = start, count = converterFactories.size(); i < count; i++) {
      builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
    }
    throw new IllegalArgumentException(builder.toString());
  }

  /**
   * Returns a {@link Converter} for {@link ResponseBody} to {@code type} from the available
   * {@linkplain #converterFactories() factories}.
   *
   * @throws IllegalArgumentException if no converter available for {@code type}.
   */
  public <T> Converter<ResponseBody, T> responseBodyConverter(Type type, Annotation[] annotations) {
    return nextResponseBodyConverter(null, type, annotations);
  }

  /**
   * Returns a {@link Converter} for {@link ResponseBody} to {@code type} from the available
   * {@linkplain #converterFactories() factories} except {@code skipPast}.
   *
   * @throws IllegalArgumentException if no converter available for {@code type}.
   */
  public <T> Converter<ResponseBody, T> nextResponseBodyConverter(
      @Nullable Converter.Factory skipPast, Type type, Annotation[] annotations) {
    Utils.checkNotNull(type, "type == null");
    Utils.checkNotNull(annotations, "annotations == null");

    int start = converterFactories.indexOf(skipPast) + 1;
    for (int i = start, count = converterFactories.size(); i < count; i++) {
      Converter<ResponseBody, ?> converter =
          converterFactories.get(i).responseBodyConverter(type, annotations, this);
      if (converter != null) {
        //noinspection unchecked
        return (Converter<ResponseBody, T>) converter;
      }
    }

    StringBuilder builder = new StringBuilder("Could not locate ResponseBody converter for ")
        .append(type)
        .append(".\n");
    if (skipPast != null) {
      builder.append("  Skipped:");
      for (int i = 0; i < start; i++) {
        builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
      }
      builder.append('\n');
    }
    builder.append("  Tried:");
    for (int i = start, count = converterFactories.size(); i < count; i++) {
      builder.append("\n   * ").append(converterFactories.get(i).getClass().getName());
    }
    throw new IllegalArgumentException(builder.toString());
  }

  /**
   * Returns a {@link Converter} for {@code type} to {@link String} from the available
   * {@linkplain #converterFactories() factories}.
   */
  public <T> Converter<T, String> stringConverter(Type type, Annotation[] annotations) {
    Utils.checkNotNull(type, "type == null");
    Utils.checkNotNull(annotations, "annotations == null");

    for (int i = 0, count = converterFactories.size(); i < count; i++) {
      Converter<?, String> converter =
          converterFactories.get(i).stringConverter(type, annotations, this);
      if (converter != null) {
        //noinspection unchecked
        return (Converter<T, String>) converter;
      }
    }

    // Nothing matched. Resort to default converter which just calls toString().
    //noinspection unchecked
    return (Converter<T, String>) BuiltInConverters.ToStringConverter.INSTANCE;
  }

  /**
   * The executor used for {@link Callback} methods on a {@link Call}. This may be {@code null},
   * in which case callbacks should be made synchronously on the background thread.
   */
  public @Nullable Executor callbackExecutor() {
    return callbackExecutor;
  }

  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Build a new {@link Service}.
   * <p>
   * Calling {@link #baseUrl} is required before calling {@link #build()}. All other methods
   * are optional.
   */
  public static final class Builder {
    private final Platform platform;
    private @Nullable okhttp3.Call.Factory callFactory;
    private @Nullable HttpUrl baseUrl;
    private final List<Converter.Factory> converterFactories = new ArrayList<>();
    private final List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>();
    private @Nullable Executor callbackExecutor;
    private boolean validateEagerly;
    private boolean serverUrlEnable = true;
    private String logTag = "Lecent";
    private HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;

    Builder(Platform platform) {
      this.platform = platform;
    }

    public Builder() {
      this(Platform.get());
    }

    Builder(Service service) {
      platform = Platform.get();
      callFactory = service.callFactory;
      baseUrl = service.baseUrl;

      // Do not add the default BuiltIntConverters and platform-aware converters added by build().
      for (int i = 1,
           size = service.converterFactories.size() - platform.defaultConverterFactoriesSize();
           i < size; i++) {
        converterFactories.add(service.converterFactories.get(i));
      }

      // Do not add the default, platform-aware call adapters added by build().
      for (int i = 0,
           size = service.callAdapterFactories.size() - platform.defaultCallAdapterFactoriesSize();
           i < size; i++) {
        callAdapterFactories.add(service.callAdapterFactories.get(i));
      }

      callbackExecutor = service.callbackExecutor;
      validateEagerly = service.validateEagerly;
    }

    /**
     * The HTTP client used for requests.
     * <p>
     * This is a convenience method for calling {@link #callFactory}.
     */
    public Builder client(OkHttpClient client) {
      return callFactory(Utils.checkNotNull(client, "client == null"));
    }

    /**
     * Specify a custom call factory for creating {@link Call} instances.
     * <p>
     * Note: Calling {@link #client} automatically sets this value.
     */
    public Builder callFactory(okhttp3.Call.Factory factory) {
      this.callFactory = Utils.checkNotNull(factory, "factory == null");
      return this;
    }

    /**
     * Set the API base URL.
     *
     * @see #baseUrl(HttpUrl)
     */
    public Builder baseUrl(String baseUrl) {
      Utils.checkNotNull(baseUrl, "baseUrl == null");
      return baseUrl(HttpUrl.get(baseUrl));
    }

    /**
     * Set the API base URL.
     * <p>
     * The specified endpoint values (such as with {@link GET @GET}) are resolved against this
     * value using {@link HttpUrl#resolve(String)}. The behavior of this matches that of an
     * {@code <a href="">} link on a website resolving on the current URL.
     * <p>
     * <b>Base URLs should always end in {@code /}.</b>
     * <p>
     * A trailing {@code /} ensures that endpoints values which are relative paths will correctly
     * append themselves to a base which has path components.
     * <p>
     * <b>Correct:</b><br>
     * Base URL: http://example.com/api/<br>
     * Endpoint: foo/bar/<br>
     * Result: http://example.com/api/foo/bar/
     * <p>
     * <b>Incorrect:</b><br>
     * Base URL: http://example.com/api<br>
     * Endpoint: foo/bar/<br>
     * Result: http://example.com/foo/bar/
     * <p>
     * This method enforces that {@code baseUrl} has a trailing {@code /}.
     * <p>
     * <b>Endpoint values which contain a leading {@code /} are absolute.</b>
     * <p>
     * Absolute values retain only the host from {@code baseUrl} and ignore any specified path
     * components.
     * <p>
     * Base URL: http://example.com/api/<br>
     * Endpoint: /foo/bar/<br>
     * Result: http://example.com/foo/bar/
     * <p>
     * Base URL: http://example.com/<br>
     * Endpoint: /foo/bar/<br>
     * Result: http://example.com/foo/bar/
     * <p>
     * <b>Endpoint values may be a full URL.</b>
     * <p>
     * Values which have a host replace the host of {@code baseUrl} and values also with a scheme
     * replace the scheme of {@code baseUrl}.
     * <p>
     * Base URL: http://example.com/<br>
     * Endpoint: https://github.com/square/retrofit/<br>
     * Result: https://github.com/square/retrofit/
     * <p>
     * Base URL: http://example.com<br>
     * Endpoint: //github.com/square/service/<br>
     * Result: http://github.com/square/retrofit/ (note the scheme stays 'http')
     */
    public Builder baseUrl(HttpUrl baseUrl) {
      Utils.checkNotNull(baseUrl, "baseUrl == null");
      List<String> pathSegments = baseUrl.pathSegments();
      if (!"".equals(pathSegments.get(pathSegments.size() - 1))) {
        throw new IllegalArgumentException("baseUrl must end in /: " + baseUrl);
      }
      this.baseUrl = baseUrl;
      return this;
    }

    /** Add converter factory for serialization and deserialization of objects. */
    public Builder addConverterFactory(Converter.Factory factory) {
      converterFactories.add(Utils.checkNotNull(factory, "factory == null"));
      return this;
    }

    /**
     * Add a call adapter factory for supporting service method return types other than {@link
     * Call}.
     */
    public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
      callAdapterFactories.add(Utils.checkNotNull(factory, "factory == null"));
      return this;
    }

    /**
     * The executor on which {@link Callback} methods are invoked when returning {@link Call} from
     * your service method.
     * <p>
     * Note: {@code executor} is not used for {@linkplain #addCallAdapterFactory custom method
     * return types}.
     */
    public Builder callbackExecutor(Executor executor) {
      this.callbackExecutor = Utils.checkNotNull(executor, "executor == null");
      return this;
    }

    /** Returns a modifiable list of call adapter factories. */
    public List<CallAdapter.Factory> callAdapterFactories() {
      return this.callAdapterFactories;
    }

    /** Returns a modifiable list of converter factories. */
    public List<Converter.Factory> converterFactories() {
      return this.converterFactories;
    }

    /**
     * When calling {@link #create} on the resulting {@link Service} instance, eagerly validate
     * the configuration of all methods in the supplied interface.
     */
    public Builder validateEagerly(boolean validateEagerly) {
      this.validateEagerly = validateEagerly;
      return this;
    }

    public Builder serverUrlEnable(boolean serverUrlEnable) {
      this.serverUrlEnable = serverUrlEnable;
      return this;
    }

      public Builder logTag(String logTag) {
          this.logTag = logTag;
          return this;
      }

      public Builder logLevel(HttpLoggingInterceptor.Level logLevel) {
          this.logLevel = logLevel;
          return this;
      }

    /**
     * Create the {@link Service} instance using the configured values.
     * <p>
     * Note: If neither {@link #client} nor {@link #callFactory} is called a default {@link
     * OkHttpClient} will be created and used.
     */
    public Service build() {
      if (baseUrl == null) {
        throw new IllegalStateException("Base URL required.");
      }

      okhttp3.Call.Factory callFactory = this.callFactory;
      if (callFactory == null) {
          OkHttpClient.Builder builder = new OkHttpClient.Builder();

          if (logLevel != HttpLoggingInterceptor.Level.NONE) {
              HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logTag);
              loggingInterceptor.setPrintLevel(logLevel);
              loggingInterceptor.setColorLevel(Level.INFO);
              builder.addInterceptor(loggingInterceptor);
          }

          callFactory = builder.build();
      }

      Executor callbackExecutor = this.callbackExecutor;
      if (callbackExecutor == null) {
        callbackExecutor = platform.defaultCallbackExecutor();
      }

      // Make a defensive copy of the adapters and add the default Call adapter.
      List<CallAdapter.Factory> callAdapterFactories = new ArrayList<>(this.callAdapterFactories);
      callAdapterFactories.addAll(platform.defaultCallAdapterFactories(callbackExecutor));

      // Make a defensive copy of the converters.
      List<Converter.Factory> converterFactories = new ArrayList<>(
          1 + this.converterFactories.size() + platform.defaultConverterFactoriesSize());

      // Add the built-in converter factory first. This prevents overriding its behavior but also
      // ensures correct behavior when using converters that consume all types.
      converterFactories.add(new BuiltInConverters());
      converterFactories.addAll(this.converterFactories);
      converterFactories.addAll(platform.defaultConverterFactories());

      return new Service(callFactory, baseUrl, unmodifiableList(converterFactories),
          unmodifiableList(callAdapterFactories), callbackExecutor, validateEagerly, serverUrlEnable);
    }
  }
}
