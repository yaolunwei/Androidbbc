package cn.lecent.bbc.template.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.lecent.bbc.template.R;
import cn.lecent.bbc.template.databinding.MainActivityBinding;
import cn.lecent.bbc.template.my.MyActivity;


/**
 * <pre>
 *     author :
 *     e-mail :
 *     time   :
 *     desc   :
 * </pre>
 */
public class MainActivity extends MyActivity<MainActivityBinding, MainViewModel> {

    @Override
    protected int myView() {
        return R.layout.main_activity;
    }

    @Override
    protected void myCreate(@NonNull MainActivityBinding bind, @NonNull MainViewModel vm) {


        bind.setVm(vm);
    }

}
