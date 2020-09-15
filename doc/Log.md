## 日志组件

### 配置
```java
ILog.getConfig()         : 获取 log 配置
Config.setLogSwitch      : 设置 log 总开关
Config.setConsoleSwitch  : 设置 log 控制台开关
Config.setGlobalTag      : 设置 log 全局 tag
Config.setLogHeadSwitch  : 设置 log 头部信息开关
Config.setLog2FileSwitch : 设置 log 文件开关
Config.setDir            : 设置 log 文件存储目录
Config.setFilePrefix     : 设置 log 文件前缀
Config.setBorderSwitch   : 设置 log 边框开关
Config.setSingleTagSwitch: 设置 log 单一 tag 开关（为美化 AS 3.1 的 Logcat）
Config.setConsoleFilter  : 设置 log 控制台过滤器
Config.setFileFilter     : 设置 log 文件过滤器
Config.setStackDeep      : 设置 log 栈深度
Config.setStackOffset    : 设置 log 栈偏移
Config.setSaveDays       : 设置 log 可保留天数
Config.addFormatter      : 新增 log 格式化器
```

### 使用 

```java
logd("我是debug日志");

loge("我是错误日志");

logf("输出到文件日志");

logj(new Log("我是Json格式日志", 12, "未知"));

logx("<xml><title>我还能输出xml格式的日志</title><body>是的就是这样</body></xml>");
```
 
1. 以上方法可以直接在继承 `BaseActivity` `BaseFragment` `BaseDialog`
   `BaseViewModel` 中使用
  
2. 任何类实现`ILog`接口以上方法可以直接使用
例如： `public class Login implements ILog`
   
3. 其他类使用 
```java
ILog.d("我是debug日志");
ILog.e("我是错误日志");
ILog.f("输出到文件日志");
ILog.j(new Log("我是Json格式日志", 12, "未知"));
ILog.x("<xml><title>我还能输出xml格式的日志</title><body>是的就是这样</body></xml>");
```

### RoadMap
- 屏幕上显示日志
- 点对点网络打印

