package com.bigoat.bbc.adapter;

import androidx.databinding.BindingAdapter;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 图片适配器
 */
public class TextAdaper {
    @BindingAdapter("textHtml")
    public static void htmlText(TextView view, String htmlText) {
        view.setText(Html.fromHtml(htmlText));
    }

    @BindingAdapter(value = {"textStart", "textCenter", "textEnd", "textCenterColor"}, requireAll = false)
    public static void moreText(TextView view, String textStart, String textCenter, String textEnd, String textCenterColor) {
        StringBuffer text = new StringBuffer();

        if (textStart != null) {
            text.append(textStart);
        }

        if (textStart != null) {
            if (textCenterColor == null) {
                text.append(textCenter);
            } else {
                text.append("<font color='"+textCenterColor+"'>" + textCenter + "</font>");
            }
        }

        if (textEnd != null) {
            text.append(textEnd);
        }

        view.setText(Html.fromHtml(text.toString()));
    }

    @BindingAdapter(value = {"startText","middleText", "endText", "middleTextColor"}, requireAll = false)
    public static void htmlText(TextView view, String startText, String middleText, String endText, String middleTextColor) {
        String htmlText = startText + "<font color='"+middleTextColor+"'>" + middleText + "</font>" + endText;
        view.setText(Html.fromHtml(htmlText));
    }

    @BindingAdapter("hintSize")
    public static void setHintTextSize(EditText editText, int textSize) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(editText.getHint());
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(textSize, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint
        editText.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }
}