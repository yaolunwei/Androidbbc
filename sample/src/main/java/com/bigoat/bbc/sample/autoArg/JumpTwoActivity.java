package com.bigoat.bbc.sample.autoArg;

import androidx.annotation.NonNull;

import com.bigoat.bbc.base.AutoArg;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.JumpTwoActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-29
 *     desc   : 跳转
 * </pre>
 */
public class JumpTwoActivity extends MyActivity<JumpTwoActivityBinding, JumpViewMode> {
    // 原始数据
    @AutoArg
    public StringBuffer sourceArgs;

    // 基本类型
    @AutoArg
    public Byte mByte;
    @AutoArg
    private short mShort;
    @AutoArg
    public Integer mInt;
    @AutoArg
    public Integer mIntegerNull;
    @AutoArg
    public int mIntNull;
    @AutoArg
    private int[] mInts;
    @AutoArg
    public long mLong;
    @AutoArg
    private float mFloat;
    @AutoArg
    private double mDouble;
    @AutoArg
    private Double[] mDoubles;
    @AutoArg
    private char mChar;
    @AutoArg
    private String mString;
    @AutoArg
    private String[] mStrings;
    @AutoArg
    private boolean mBoolean;

    // 对象
    @AutoArg
    private Persion mPersion;
    @AutoArg
    private Persion mPersionNull;
    @AutoArg
    private Persion[] mPersions;
    @AutoArg
    private List<Persion> mPersionList;
    @AutoArg
    private Animal mAnimal;
    @AutoArg
    private GoldenRetrieverGog mGoldenRetrieverGog;
    @AutoArg
    private Map mMap;
    @AutoArg
    private Map mMapObj;

    public JumpTwoActivity() {
    }

    @Override
    protected int myView() {
        return R.layout.jump_two_activity;
    }

    @Override
    protected void myCreate(@NonNull JumpTwoActivityBinding bind, @NonNull JumpViewMode vm) {
        setTitle("参数注入");

        bind.sourceArg.setText(sourceArgs);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Activity接收：\n\n");
        stringBuffer.append("mByte: " + mByte + "\n\n");
        stringBuffer.append("mShort: " + mShort + "\n\n");
        stringBuffer.append("mInt: " + mInt + "\n\n");
        stringBuffer.append("mInts: " + Arrays.toString(mInts) + "\n\n");
        stringBuffer.append("mLong: " + mLong + "\n\n");
        stringBuffer.append("mFloat: " + mFloat + "\n\n");
        stringBuffer.append("mDouble: " + mDouble + "\n\n");
        stringBuffer.append("mDoubles: " + Arrays.toString(mDoubles) + "\n\n");
        stringBuffer.append("mChar: " + mChar + "\n\n");
        stringBuffer.append("mString: " + mString + "\n\n");
        stringBuffer.append("mStrings: " + Arrays.toString(mStrings) + "\n\n");
        stringBuffer.append("mBoolean: " + mBoolean + "\n\n");
        stringBuffer.append("mPersion: " + mPersion + "\n\n");
        stringBuffer.append("mPersions: " + Arrays.toString(mPersions) + "\n\n");
        stringBuffer.append("mPersionList: " + mPersionList + "\n\n");
        stringBuffer.append("mAnimal: " + mAnimal + "\n\n");
        stringBuffer.append("mGoldenRetrieverGog: " + mGoldenRetrieverGog + "\n\n");
        stringBuffer.append("mMap: " + mMap + "\n\n");
        stringBuffer.append("mMapObj: " + mMapObj + "\n\n");
        stringBuffer.append("mIntegerNull: " + mIntegerNull + "\n\n");
        stringBuffer.append("mIntNull: " + mIntNull + "\n\n");
        stringBuffer.append("mPersionNull: " + mPersionNull + "\n\n");

        bind.activityArg.setText(stringBuffer.toString());

        bind.setVm(vm);
    }

}
