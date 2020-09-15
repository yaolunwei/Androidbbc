package com.bigoat.bbc.sample.my;

import androidx.databinding.ViewDataBinding;

import com.bigoat.bbc.base.BaseDialog;
import com.bigoat.bbc.base.BaseViewModel;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-07-14
 *     desc   : 所有Dialog必须继承MyDialog
 * </pre>
 */
public abstract class MyDialog<Binding extends ViewDataBinding, ViewMode extends BaseViewModel> extends BaseDialog<Binding, ViewMode> {

}
