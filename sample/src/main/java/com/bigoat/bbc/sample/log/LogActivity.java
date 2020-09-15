package com.bigoat.bbc.sample.log;

import androidx.annotation.NonNull;

import com.bigoat.bbc.base.ILog;
import com.bigoat.bbc.sample.R;
import com.bigoat.bbc.sample.databinding.LogActivityBinding;
import com.bigoat.bbc.sample.my.MyActivity;
import com.zzhoujay.richtext.RichText;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 2020-04-28
 *     desc   : 日志
 * </pre>
 */
public class LogActivity extends MyActivity<LogActivityBinding, LogViewModel> {

    @Override
    protected int myView() {
        return R.layout.log_activity;
    }

    @Override
    protected void myCreate(@NonNull LogActivityBinding bind, @NonNull LogViewModel vm) {
        setTitle("日志组件");
        RichText.initCacheDir(this);

        // 动态调用日志
        logd("logd 我是debug日志");
        loge("loge 我是错误日志");
        logf("logf 输出到文件日志");
        logj(new Log("我是Json格式日志", 12, "logj"));
        logx("<xml><title>我还能输出xml格式的日志</title><body>logx</body></xml>");

        logd("============== 华丽分割线 ============");

        // 静态调用日志
        ILog.d("我是debug日志");
        ILog.e("我是错误日志");
        ILog.f("输出到文件日志");
        ILog.j(new Log("我是Json格式日志", 12, "未知"));
        ILog.x("<xml><title>我还能输出xml格式的日志</title><body>是的就是这样</body></xml>");

        String md = "## 日志组件\n" +
                "\n" +
                "### 配置\n" +
                "ILog.getConfig()         : 获取 log 配置\n\n" +
                "Config.setLogSwitch      : 设置 log 总开关\n\n" +
                "Config.setConsoleSwitch  : 设置 log 控制台开关\n\n" +
                "Config.setGlobalTag      : 设置 log 全局 tag\n\n" +
                "Config.setLogHeadSwitch  : 设置 log 头部信息开关\n\n" +
                "Config.setLog2FileSwitch : 设置 log 文件开关\n\n" +
                "Config.setDir            : 设置 log 文件存储目录\n\n" +
                "Config.setFilePrefix     : 设置 log 文件前缀\n\n" +
                "Config.setBorderSwitch   : 设置 log 边框开关\n\n" +
                "Config.setSingleTagSwitch: 设置 log 单一 tag 开关（为美化 AS 3.1 的 Logcat）\n\n" +
                "Config.setConsoleFilter  : 设置 log 控制台过滤器\n\n" +
                "Config.setFileFilter     : 设置 log 文件过滤器\n\n" +
                "Config.setStackDeep      : 设置 log 栈深度\n\n" +
                "Config.setStackOffset    : 设置 log 栈偏移\n\n" +
                "Config.setSaveDays       : 设置 log 可保留天数\n\n" +
                "Config.addFormatter      : 新增 log 格式化器\n\n" +
                "\n" +
                "### 使用 \n" +
                "\n" +
                "logd(\"我是debug日志\");\n" +
                "\n" +
                "loge(\"我是错误日志\");\n" +
                "\n" +
                "logf(\"输出到文件日志\");\n" +
                "\n" +
                "logj(new Log(\"我是Json格式日志\", 12, \"未知\"));\n" +
                "\n" +
                "logx(\"<xml><title>我还能输出xml格式的日志</title><body>是的就是这样</body></xml>\");\n" +
                " \n" +
                "1. 以上方法可以直接在继承 `BaseActivity` `BaseFragment` `BaseDialog`\n" +
                "   `BaseViewModel` 中使用\n" +
                "  \n" +
                "2. 任何类实现`ILog`接口以上方法可以直接使用\n" +
                "例如： `public class Login implements ILog`\n" +
                "   \n" +
                "3. 其他类使用 \n" +
                "ILog.d(\"我是debug日志\");\n" +
                "ILog.e(\"我是错误日志\");\n" +
                "ILog.f(\"输出到文件日志\");\n" +
                "ILog.j(new Log(\"我是Json格式日志\", 12, \"未知\"));\n" +
                "ILog.x(\"<xml><title>我还能输出xml格式的日志</title><body>是的就是这样</body></xml>\");\n" +
                "\n" +
                "### RoadMap\n" +
                "- 屏幕上显示日志";

        RichText.fromMarkdown(md).into(bind.doc);
        bind.setVm(vm);
    }
}
