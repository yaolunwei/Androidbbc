package cn.lecent.bbc.template.my;

import androidx.databinding.ViewDataBinding;

import com.bigoat.bbc.base.BaseActivity;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-06-23
 *     desc   : 所有Activity必须继承MyActivity
 * </pre>
 */
public abstract class MyActivity<Binding extends ViewDataBinding, ViewMode extends MyViewModel> extends BaseActivity<Binding, ViewMode> {
}
