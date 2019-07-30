# 最新版本
 1.0.0

#

### 使用
#
```
在 build.gradle 中添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```
dependencies {
	        implementation 'com.github.mingrq:FlowLayout:Tag'
	}
```
## 方法

### _初始化_

#
### TagFlowLayout setAdapter(FlowlayoutAdapter flowlayoutAdapter)
#### 设置适配器

flowlayoutAdapter：适配器实例

```
适配器

  自定义适配器必须继承FlowlayoutAdapter
```
