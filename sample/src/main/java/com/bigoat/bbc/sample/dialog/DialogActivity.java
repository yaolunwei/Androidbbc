package com.bigoat.bbc.sample.dialog;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.DialogActivityBinding;
import com.bigoat.bbc.sample.databinding.ToastActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : Dialog
 * </pre>
 */
public class DialogActivity extends MyActivity<DialogActivityBinding, DialogViewModel> {

    @Override
    protected int myView() {
        return R.layout.dialog_activity;
    }

    @Override
    protected void myCreate(@NonNull DialogActivityBinding bind, @NonNull DialogViewModel vm) {

        bind.normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog().setMsg("正常Dialog").show(getSupportFragmentManager());
            }
        });

        bind.fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("全屏Dialog,setFullScreen(true)")
                        .setFullScreen(true)
                        .show(getSupportFragmentManager());
            }
        });

        bind.bottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("底部Dialog(无阴影),setBottomDialog(200, false)")
                        .setBottomDialog(200, false)
                        .show(getSupportFragmentManager());
            }
        });

        bind.bottomDialogShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("底部Dialog(有阴影),setBottomDialog(200, true)")
                        .setBottomDialog(200, true)
                        .show(getSupportFragmentManager());
            }
        });

        bind.fixedWidthAndHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("固定宽高300x300,setWidthAndHeight(300, 300)")
                        .setWidthAndHeight(300, 300)
                        .show(getSupportFragmentManager());
            }
        });

        bind.proportionalWidthAndHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("比例宽高50%,50%,setWidthAndHeightPercentage(0.5f,0.5f)")
                        .setWidthAndHeightPercentage(0.5f,0.5f)
                        .show(getSupportFragmentManager());
            }
        });

        bind.showPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("显示位置左下偏移(100,100),setGravity(Gravity.BOTTOM|Gravity.START, 100,100)")
                        .setGravity(Gravity.BOTTOM|Gravity.START, 100,100)
                        .show(getSupportFragmentManager());
            }
        });

        bind.showPositionByView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomDialog()
                        .setMsg("通过View显示位置,setGravity(bind.fullScreen, 10,10)")
                        .setGravity(bind.fullScreen, 10,10)
                        .show(getSupportFragmentManager());
            }
        });

        bind.setVm(vm);
    }
}
