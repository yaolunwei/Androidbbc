package com.bigoat.bbc.sample.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bigoat.bbc.base.BaseLiveData;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.my.MyViewModel;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.xuexiang.xui.utils.DrawableUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.xuexiang.xui.utils.ResUtils.getResources;

public class ViewViewModel extends MyViewModel {
    public BaseLiveData<Boolean> currentTimeEnableData = new BaseLiveData<>(true);

    public BaseLiveData<Integer> tData = new BaseLiveData<>(1);

    public Bitmap bitmap;
    public Drawable drawable;
    public String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593686732969&di=f4796b3a05789b92e925e1d7b2253a37&imgtype=0&src=http%3A%2F%2Ft7.baidu.com%2Fit%2Fu%3D1179872664%2C290201490%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1280%26h%3D854";
    public String[] imgUrls = {
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593689841582&di=d73a6e46d1adc8d1d278de7ab00f06d8&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1305%2F24%2Fc1%2F21266756_1369385228661.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593689841580&di=495fe62636156b2cde27471b45871434&imgtype=0&src=http%3A%2F%2F01.minipic.eastday.com%2F20170425%2F20170425100543_3e5d0116a4965b6eca147f485e613e02_7.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593689841578&di=3091153ba579140045e77234f07c26a1&imgtype=0&src=http%3A%2F%2Fwww.flybridal.com%2Fhuangse%2FaHR0cDovL24uc2luYWltZy5jbi9zaW5hY24xNS8xNjgvdzc1OGgxMDEwLzIwMTgwNTI4L2UxMjktaGNhcXVldjU1MTM5NzUuanBn.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593689899240&di=7c87c002b7f27f3d231a5e83fae7f29d&imgtype=0&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1883278254%2C2829362133%26fm%3D214%26gp%3D0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593689841576&di=0d5a25c433dc84ad8cafea5b6ce8cd89&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201309%2F03%2F20130903141830_Q4Wuc.jpeg"
    };
    int i = 0;
    public BaseLiveData<String> dynamicImgData = new BaseLiveData<>(imgUrls[i]);

    @Override
    protected void onCreate() {
        super.onCreate();
        bitmap = ImageUtils.drawable2Bitmap(new ColorDrawable(Color.RED));
        drawable = new ColorDrawable(Color.BLUE);
    }

    @Override
    public void showToastWarn(@NonNull String msg) {

    }

    public void switchCurrentTimeEnable() {
        currentTimeEnableData.value(!currentTimeEnableData.value());
    }

    public void dynamicUpdateImg () {
        if (++i>=imgUrls.length) i = 0;
        dynamicImgData.value(imgUrls[i]);
    }

}
