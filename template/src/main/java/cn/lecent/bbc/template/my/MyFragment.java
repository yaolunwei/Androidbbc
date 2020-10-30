package cn.lecent.bbc.template.my;

import androidx.databinding.ViewDataBinding;

import com.bigoat.bbc.base.BaseFragment;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-06-23
 *     desc   : 所有Fragment必须继承MyFragment
 * </pre>
 */
public abstract class MyFragment<Binding extends ViewDataBinding, ViewMode extends MyViewModel> extends BaseFragment<Binding, ViewMode> {
}
