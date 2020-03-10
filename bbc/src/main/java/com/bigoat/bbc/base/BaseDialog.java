package com.bigoat.bbc.base;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseDialog<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends DialogFragment {
    protected String tag;

    protected Binding bind;
    protected ViewModel vm;

    private BaseActivity act;

    /**
     * 布局文件
     *
     * @return layoutId
     */
    public abstract @LayoutRes int myView();

    /**
     * 业务逻辑
     *
     * @param bind Binding
     * @param vm ViewModel
     */
    protected abstract void myCreate(Binding bind, ViewModel vm);

    /**
     * 创建ViewMode
     *
     * @return ViewModel
     */
    private ViewModel createModel() {
        Type type = getClass().getGenericSuperclass();

        if (type instanceof ParameterizedType) {
            ParameterizedType paraType = (ParameterizedType) type;

            if (paraType.getActualTypeArguments().length == 2) {
                Class clazz = (Class<ViewModel>) paraType.getActualTypeArguments()[1];
                if (shareViewMode()) {
                    return (ViewModel) ViewModelProviders.of(getActivity()).get(clazz);
                } else {
                    return (ViewModel) ViewModelProviders.of(this).get(clazz);
                }
            } else {
                throw new RuntimeException("请配置正确的泛型参数, eg: MyDialog extends BaseDialog<?, ?>");
            }

        } else {
            if (shareViewMode()) {
                return (ViewModel) ViewModelProviders.of(getActivity()).get(BaseViewModel.class);
            } else {
                return (ViewModel) ViewModelProviders.of(this).get(BaseViewModel.class);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, myView(), container, false);

        vm = createModel();

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() instanceof BaseActivity) {
            act = (BaseActivity) getActivity();
        } else {
            throw new RuntimeException(getClass().getSimpleName() + " 必须继承 BaseActivity");
        }

        tag = getClass().getSimpleName();

        bind.setLifecycleOwner(this);

        vm.progressData.observe(getViewLifecycleOwner(), s -> {
            if (s == null) {
                hideProgress();
            } else {
                showProgress(s);
            }
        });

        myCreate(bind, vm);

        vm.create();
    }

    protected boolean shareViewMode() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        vm.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        vm.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        vm.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        vm.onDestroy();
    }

    public BaseDialog with(@NonNull String key, @NonNull Object value) {
        act.with(key, value);
        return this;
    }

    public BaseDialog startActivity(Class activity) {
        act.intent = new Intent(act, activity);
        return this;
    }

    public void go() {
        act.go();
    }

    protected void go(Class activity, Object... args) {
        act.go(activity, args);
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
        LogUtils.json(tag, GsonUtils.toJson(json));
    }

    protected void showProgress(@NonNull String msg) {
        act.showProgress(msg);
    }

    protected void hideProgress() {
        act.hideProgress();
    }

    public void show(FragmentManager manager) {
        if (!isAdded()) {
            show(manager, getClass().getSimpleName());
        }
    }
}
