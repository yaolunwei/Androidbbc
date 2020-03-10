package com.bigoat.bbc.base;

import androidx.lifecycle.ViewModelProviders;
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
import com.blankj.utilcode.util.LogUtils;

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
public abstract class BaseActivity<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends AppCompatActivity {
    protected String tag;

    protected Binding bind;
    protected ViewModel vm;

    private View progressView;

    protected Intent intent;
    private Bundle bundle = new Bundle();

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
                return (ViewModel) ViewModelProviders.of(this).get(clazz);
            } else {
                throw new RuntimeException("请配置正确的泛型参数, eg: MyActivity extends BaseActivity<?, ?>");
            }

        } else {
            BaseViewModel baseViewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
            return (ViewModel)baseViewModel;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tag = getClass().getSimpleName();

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
        if (value instanceof String) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
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

    public BaseActivity startActivity(Class activity) {
        intent = new Intent(this, activity);
        return this;
    }

    public void go() {
        if (intent == null) {
            throw new RuntimeException("请先设置跳转Activity eg：startActivity(Class activity)");
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

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

    protected void logd(String msg) {
        LogUtils.dTag(tag, msg);
    }

    protected void loge(String msg) {
        LogUtils.eTag(tag, msg);
    }

    protected void logj(String json) {
        LogUtils.json(tag, json);
    }

    protected void logj(Object json) {
        LogUtils.json(tag, toJson(json));
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

    private void injectBundle(Object o, Bundle bundle) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean annotationPresent = field.isAnnotationPresent(AutoArg.class);
                if (annotationPresent) {
                    field.setAccessible(true);

                    Object value = bundle.get(field.getName());

                    if (value instanceof String) {
                        String str = (String) value;
                        try {
                            Object obj = GsonUtils.fromJson(str, field.getType());
                            field.set(o, obj);
                        } catch (Exception e) {
                            field.set(o, str);
                        }

                    } else {
                        field.set(o, value);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("参数注入失败: " + e.getMessage());
        }
    }

}