package com.bigoat.bbc.sample.autoArg;

import android.view.View;

import androidx.annotation.NonNull;

import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.JumpActivityBinding;
import com.bigoat.bbc.sample.go.GoActivity;
import com.bigoat.bbc.sample.my.MyActivity;
import com.bigoat.bbc.sample.my.MyViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
public class JumpActivity extends MyActivity<JumpActivityBinding, MyViewModel> {

    @Override
    protected int myView() {
        return R.layout.jump_activity;
    }

    @Override
    protected void myCreate(@NonNull JumpActivityBinding bind, @NonNull MyViewModel vm) {
        setTitle("Activity跳转");

        byte mByte = 1;
        short mShort = 2;
        Integer mInt = 3;
        Integer[] mInts = new Integer[]{1,2,3,4,5,6};
        long mLong = 4;
        Float mFloat = 5.1f;
        double mDouble = 6.6;
        double[] mDoubles = new double[]{1.1, 1.3, 1.4, 1.5, 1.6};
        char mChar = 'A';
        String mString = "This is inject args";
        String[] mStrings = new String[]{"String1", "String2", "String3"};
        boolean mBoolean = true;

        Persion mPersion = new Persion("对象", 78);
        Persion[] mPersions = new Persion[2];
        mPersions[0] = new Persion("对象数组", 1);
        mPersions[1] = new Persion("对象数组", 2);
        List<Persion> mPersionList = new ArrayList<>();
        mPersionList.add(new Persion("对象list1", 1));
        mPersionList.add(new Persion("对象list2", 2));

        Animal mAnimal = new Animal();
        mAnimal.setTag("动物");

        GoldenRetrieverGog mGoldenRetrieverGog = new GoldenRetrieverGog();
        mGoldenRetrieverGog.setName("金毛狗");
        mGoldenRetrieverGog.setSex("母");
        mGoldenRetrieverGog.setTag("狗");

        Map<Integer, String> mMap = new HashMap<>();
        mMap.put(1,"1");
        mMap.put(2,"2");
        mMap.put(3,"3");

        Map<String, Animal> mMapObj = new HashMap<>();
        mMapObj.put("a", mAnimal);
        mMapObj.put("b", mGoldenRetrieverGog);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("原始参数：\n\n");
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

        bind.arg.setOnClickListener(view -> {
            startActivity(JumpTwoActivity.class)
                    .with("sourceArgs", stringBuffer)
                    .with("mByte", mByte)
                    .with("mShort", mShort)
                    .with("mInt", mInt)
                    .with("mInts", mInts)
                    .with("mLong", mLong)
                    .with("mFloat", mFloat)
                    .with("mDouble", mDouble)
                    .with("mDoubles", mDoubles)
                    .with("mChar", mChar)
                    .with("mString", mString)
                    .with("mStrings", mStrings)
                    .with("mBoolean", mBoolean)
                    .with("mPersion", mPersion)
                    .with("mPersions", mPersions)
                    .with("mPersionList", mPersionList)
                    .with("mAnimal", mAnimal)
                    .with("mGoldenRetrieverGog", mGoldenRetrieverGog)
                    .with("mMap", mMap)
                    .with("mMapObj", mMapObj)
                    .go();
        });

        bind.jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go(GoActivity.class);
            }
        });

    }

}
