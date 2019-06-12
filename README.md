## BBC(基础业务组件)
> 业务组件的基础，所有业务组件必须基于该组件进行开发，提供一站式开发

### 快速使用
2. gradle:
```groovy
implementation 'com.bigoat.android:bbc:0.0.1'
```

### 约定成俗
以下继承关系是**必须的**
- `Application` *extends* `BaseApplication`
- `Activity` *extends* `BaseActivity`
- `Fragment` *extends* `BaseFragment`
- `ViewModel` *extends* `BaseViewModel`
- `BaseLiveData` 代替 `LiveData` 
- `@Repository` 注解网络请求接口


### BaseApplication
> 全局初始化，例如网络，日志等

提供全局标准初始化方法 `init()`

### BaseActivity && BaseFragment
> 负责UI和用户事件处理，不要做复杂的逻辑处理

提供通用`Log` `Toast` `SP` `Permissions` `Dialog` 等标准约定操作，重载皆可实现自定义处理

### BaseViewModel
> 用户逻辑处理，数据驱动UI，发起网络请求也在这里，不要处理UI操作

重要的事说三篇:
- `不能引入Android有关的包(例如：Activity Fragment Context)`
- `不能引入Android有关的包(例如：Activity Fragment Context)`
- `不能引入Android有关的包(例如：Activity Fragment Context)`

### BaseLiveData
> 可观察带生命周期的数据对象

例如 `BaseLiveData<User>`

### @Repository
> 用于注解网络请求接口自动初始化网络请求对象
