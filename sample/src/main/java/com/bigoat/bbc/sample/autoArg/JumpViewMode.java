package com.bigoat.bbc.sample.autoArg;

import com.bigoat.bbc.base.AutoArg;
import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.sample.my.MyViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 19-6-12
 *     desc   :
 * </pre>
 */
public class JumpViewMode extends MyViewModel {
    // 基本类型
    @AutoArg
    public byte mByte;
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

    public BaseLiveData<String> argsData = new BaseLiveData<>();

    public BaseLiveData<String> docData = new BaseLiveData<>();

    @Override
    public void onCreate() {
        String doc = "使用说明：  \n\n" +
                "startActivity(JumpTwoActivity.class)\n" +
                "                    .with(\"sourceArgs\", stringBuffer)\n" +
                "                    .with(\"mByte\", mByte)\n" +
                "                    .with(\"mShort\", mShort)\n" +
                "                    .with(\"mInt\", mInt)\n" +
                "                    .with(\"mInts\", mInts)\n" +
                "                    .with(\"mLong\", mLong)\n" +
                "                    .with(\"mFloat\", mFloat)\n" +
                "                    .with(\"mDouble\", mDouble)\n" +
                "                    .with(\"mDoubles\", mDoubles)\n" +
                "                    .with(\"mChar\", mChar)\n" +
                "                    .with(\"mString\", mString)\n" +
                "                    .with(\"mStrings\", mStrings)\n" +
                "                    .with(\"mBoolean\", mBoolean)\n" +
                "                    .with(\"mPersion\", mPersion)\n" +
                "                    .with(\"mPersions\", mPersions)\n" +
                "                    .with(\"mPersionList\", mPersionList)\n" +
                "                    .with(\"mAnimal\", mAnimal)\n" +
                "                    .with(\"mGoldenRetrieverGog\", mGoldenRetrieverGog)\n" +
                "                    .with(\"mMap\", mMap)\n" +
                "                    .with(\"mMapObj\", mMapObj)\n" +
                "                    .go();";
        docData.value(doc);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("VM接收：\n\n");
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

        argsData.setValue(stringBuffer.toString());
    }
}
