package com.bigoat.bbc.base;

import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 18-11-15
 *     desc   : Dialog基础
 * </pre>
 */
public abstract class BaseDialog<Binding extends ViewDataBinding, ViewModel extends BaseViewModel> extends DialogFragment implements IToast, ILog {
    protected String tag;

    protected Binding bind;
    protected ViewModel vm;

    protected Dialog dialog;
    protected Window window;

    protected WindowManager.LayoutParams params;

    // 全屏
    protected boolean isFullScreen;
    // 底部弹出
    protected boolean isBottomDialog;
    // 底部弹出是否有阴影
    protected boolean isBottomDialogShadow;
    // 底部高度
    protected int bottomDialogHeight;

    // 宽
    protected int width = WindowManager.LayoutParams.WRAP_CONTENT;
    // 高
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;

    // 启用百分比设置宽高
    protected  boolean isPercentageSize;
    // 宽百分比
    protected float widthPercentage = 0.5f;
    // 高百分比
    protected float heightPercentage = 0.5f;

    // 是否显示标题
    protected boolean isShowTitle = false;

    // 显示位置
    private int gravity = Gravity.CENTER;
    private int offsetX = 0;
    private int offsetY = 0;

    protected boolean transparent = true;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFullScreen || isBottomDialog) {
            if (isBottomDialogShadow) {
                setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_Dialog);
            } else {
                setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = getDialog();
        if (dialog != null) {
            window = dialog.getWindow();

            if (window!=null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            if (window !=null && (isFullScreen || isBottomDialog || !isShowTitle)) {
                window.requestFeature(Window.FEATURE_NO_TITLE);
            }
        }

        bind = DataBindingUtil.inflate(inflater, myView(), container, false);

        vm = createModel();

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tag = getClass().getSimpleName();

        if (getActivity() instanceof BaseActivity) {
            act = (BaseActivity) getActivity();
        } else {
            throw new RuntimeException(getClass().getSimpleName() + " 必须继承 BaseActivity");
        }

        bind.setLifecycleOwner(this);

        myCreate(bind, vm);

        vm.create();
    }

    protected boolean shareViewMode() {
        return true;
    }

    public BaseDialog setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        return this;
    }

    public BaseDialog setBottomDialog(int bottomDialogHeight, boolean isBottomDialogShadow) {
        this.isBottomDialog = true;
        this.bottomDialogHeight = bottomDialogHeight;
        this.isBottomDialogShadow = isBottomDialogShadow;
        return this;
    }

    public BaseDialog setWidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public BaseDialog setWidthAndHeightPercentage(float widthPercentage, float heightPercentage) {
        this.isPercentageSize = true;
        this.widthPercentage = widthPercentage>1?1:widthPercentage;
        this.heightPercentage = heightPercentage>1?1:heightPercentage;
        return this;
    }

    public BaseDialog setGravity(int gravity, int offsetX, int offsetY ) {
        this.gravity = gravity;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }

    public BaseDialog setGravity(View view, int offsetX, int offsetY ) {
        if (view==null) return this;
        this.gravity = Gravity.START|Gravity.TOP;
        this.offsetX = (int)view.getX() + offsetX;
        this.offsetY = (int)view.getY() + offsetY;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (window != null) {
            params = window.getAttributes();

            params.x = offsetX;
            params.y = offsetY;
            params.gravity = gravity;

            if (isFullScreen) {
                params.x = 0;
                params.y = 0;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
            } else if (isBottomDialog) {
                params.x = 0;
                params.y = 0;
                params.gravity = Gravity.BOTTOM;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = bottomDialogHeight;
            } else if (isPercentageSize) {
                DisplayMetrics dm = new DisplayMetrics();
                if (getActivity()!=null) {
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                    params.width = (int)(dm.widthPixels * widthPercentage);
                    params.height = (int)(dm.heightPixels * heightPercentage);
                }
            } else {
                params.width = width;
                params.height = height;
            }

            window.setAttributes(params);
        }

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

    @Deprecated
    protected void go(Class activity, Object... args) {
        act.go(activity, args);
    }

    public void show(FragmentManager manager) {
        if (!isAdded()) {
            show(manager, getClass().getSimpleName());
        }
    }
}
