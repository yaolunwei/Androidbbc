package cn.lecent.bbc.template.my;

import androidx.databinding.ViewDataBinding;

import com.bigoat.bbc.base.BaseDialog;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-06-23
 *     desc   : 所有Dialog必须继承MyDialog
 * </pre>
 */
public abstract class MyDialog<Binding extends ViewDataBinding, ViewMode extends MyViewModel> extends BaseDialog<Binding, ViewMode> {
}
