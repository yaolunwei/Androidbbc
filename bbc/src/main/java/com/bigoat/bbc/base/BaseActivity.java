package com.bigoat.bbc.base;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigoat.bbc.R;
import com.blankj.utilcode.util.GsonUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.blankj.utilcode.util.GsonUtils.toJson;


/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 18-11-15
 *     desc   : Activity基础
 * </pre>
 */
public abstract class BaseActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends AppCompatActivity implements IToast, ILog {
    public static final int REQUEST_CODE = -19920115;

    protected String tag;

    // 跳转携带
    private Bundle bundle;

    protected Binding bind;
    protected ViewModel vm;

    // 进度
    private View progressView;

    protected Intent intent;

    /**
     * 布局文件
     *
     * @return layoutId
     */
    protected abstract @LayoutRes int myView();

    /**
     * 业务逻辑
     *
     * @param bind Binding
     * @param vm ViewModel
     */
    protected abstract void myCreate(@NonNull Binding bind, @NonNull ViewModel vm);

    @Deprecated
    protected void myStart() {
        super.onStart();
    }


    /**
     * 创建ViewMode
     *
     * @return ViewModel
     */
    private ViewModel createViewModel() {
        Type type = getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            ParameterizedType paraType = (ParameterizedType) type;

            if (paraType.getActualTypeArguments().length == 2) {
                Class clazz = (Class<ViewModel>) paraType.getActualTypeArguments()[1];
                return (ViewModel) new ViewModelProvider(this).get(clazz);
            } else {
                throw new RuntimeException("请配置正确的泛型参数, eg: MyActivity extends BaseActivity<?, ?>");
            }

        } else {
            BaseViewModel baseViewModel = new ViewModelProvider(this).get(BaseViewModel.class);
            return (ViewModel)baseViewModel;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getClass().getSimpleName();

        bundle = new Bundle();

        bind = DataBindingUtil.setContentView(this, myView());

        vm = createViewModel();

        injectArgs(getIntent().getExtras());

        bind.setLifecycleOwner(this);

        vm.progressData.observe(this, s -> {
            if (s == null) {
                hideProgress();
            } else {
                showProgress(s);
            }
        });

        vm.finishResultCodeData.observe(this, integer -> {
            if (integer!=null) {
                finish(integer);
            }
        });

        myCreate(bind, vm);

        vm.create();
    }

    @Override
    protected void onStart() {
        super.onStart();
        vm.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vm.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        vm.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vm.onDestroy();
    }

    public BaseActivity with(@NonNull String key, @NonNull Object value) {
        if (value instanceof Byte) {
            bundle.putByte(key, (Byte) value);
        } else if (value instanceof Short) {
            bundle.putShort(key, (Short) value);
        } else if (value instanceof Character) {
            bundle.putChar(key, (Character) value);
        } else if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value instanceof Long) {
            bundle.putLong(key, (Long) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            bundle.putFloat(key, (Float) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key, (Double) value);
        } else {
            bundle.putString(key, toJson(value));
        }

        return this;
    }

    public BaseActivity startActivity(@NonNull Class activity) {
        intent = new Intent(this, activity);
        return this;
    }

    public void go() {
        go(REQUEST_CODE);
    }

    public void go(int requestCode) {
        if (intent == null) {
            throw new RuntimeException("请先设置跳转Activity eg：startActivity(Class activity)");
        }

        intent.putExtras(bundle);
        if (requestCode == REQUEST_CODE) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    protected void go(Class activity) {
        go(activity, REQUEST_CODE);
    }

    protected void go(@NonNull Class activity, int requestCode) {
        intent = new Intent(this, activity);
        go(requestCode);
    }

    @Deprecated
    protected void go(Class activity, Object... args) {
        intent = new Intent(this, activity);
        for (int i = 0; i < args.length; i++) {
            Object value = args[i];
            if (value instanceof String) {
                bundle.putString("arg"+i, (String) value);
            } else if (value instanceof Boolean) {
                bundle.putBoolean("arg"+i, (Boolean) value);
            } else if (value instanceof Integer) {
                bundle.putInt("arg"+i, (Integer) value);
            } else if (value instanceof Float) {
                bundle.putFloat("arg"+i, (Float) value);
            } else if (value instanceof Double) {
                bundle.putDouble("arg"+i, (Double) value);
            } else {
                bundle.putString("arg"+i, toJson(value));
            }
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myGoResult(requestCode, resultCode, data);
    }
    // 跳转回调
    protected void myGoResult(int requestCode, int resultCode, @Nullable Intent data) {
    }

    protected void showProgress(@NonNull String msg) {
        if (progressView == null) {
            progressView = getLayoutInflater().inflate(R.layout.progress_layout, null);
            progressView.setOnClickListener(v -> {});
            addContentView(progressView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        TextView tv = progressView.findViewById(R.id.msg);
        tv.setText(msg);

        progressView.setVisibility(View.VISIBLE);
    }

    protected void showProgress() {
        showProgress("");
    }

    protected void hideProgress() {
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
        }
    }

    private void injectArgs(Bundle bundle) {
        if (bundle != null) {
            injectBundle(this, bundle);
            injectBundle(vm, bundle);
        }
    }

    // 参数注入
    private void injectBundle(Object o, Bundle bundle) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                boolean hasAutoArg = field.isAnnotationPresent(AutoArg.class);
                if (hasAutoArg) {
                    field.setAccessible(true);
                    Object value = bundle.get(field.getName());
                    if (value == null) continue;

                    Class type = field.getType();
                    if (isPrimitive(type)) {
                        field.set(o, value);
                    } else {
                        logd("other type: " + type.getName());
                        try {
                            if (value instanceof String) {
                                String stringValue = (String) value;
                                field.set(o, GsonUtils.fromJson(stringValue, type));
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    // 是否基本类型包含包装类型
    private boolean isPrimitive(Class c) {
        return c.isPrimitive()
                || c == Byte.class
                || c == Short.class
                || c == Integer.class
                || c == Long.class
                || c == Float.class
                || c == Double.class
                || c == Character.class
                || c == String.class
                || c == Boolean.class;
    }

    public void finish(int resultCode, Intent intent) {
        if (intent == null) {
            setResult(resultCode, new Intent());
        } else {
            setResult(resultCode, intent);
        }

        finish();
    }

    public void finish(int resultCode) {
        finish(resultCode, null);
    }

    // xml中finish
    public void finish(View view) {
        finish();
    }
}