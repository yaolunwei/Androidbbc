package com.bigoat.bbc.sample.dialog;

import android.view.View;

import com.bigoat.bbc.base.BaseDialog;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.CustomDialogBinding;
import com.bigoat.bbc.sample.my.MyDialog;

public class CustomDialog extends MyDialog<CustomDialogBinding, DialogViewModel> {

    private String msg = "";

    @Override
    public int myView() {
        return R.layout.custom_dialog;
    }

    @Override
    protected void myCreate(CustomDialogBinding bind, DialogViewModel vm) {
        vm.msgData.setValue(msg);

        bind.context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        bind.setVm(vm);
    }

    public BaseDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
