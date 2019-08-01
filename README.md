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

  自定义适配器必须继承  FlowlayoutAdapter
  
  	实现以下方法
  
	  /**
	     * 获取item的数量
	     *
	     * @return
	     */
	    public abstract int getCount();

	    /**
	     * 获取item的数据
	     * @return
	     */
	    public abstract int getItem(int position);

	    /**
	     * 获取itemview
	     * @param position
	     * @param parent
	     * @return
	     */
	    public abstract View getView(int position,FlowLayout parent);
    
    
    适配器方法
    
    	void notifyDataChanged()；
    	调用该方法，刷新数据
    
     	boolean isEmpty()
     	调用该方法，判断数据集合是否为空
```

#
###  FlowLayout setGravity(int gravity)
设置布局方向
```
布局方向 
	LEFT :布局靠左
	CENTER ：布局靠右
	RIGHT ：布局居中
```

#
### FlowLayout setItemMargin(int marginLeft, int marginTop, int marginRight, int marginBottom)
设置Item默认间距```单位：px  如要使用dp单位 使用  dp2px(float dpValues)方法```
```
  marginLeft   左间距
  marginTop    上间距
  marginRight  右间距
  marginBottom 下间距
```

#
### TagFlowLayout setCheckedEnable(boolean checkedEnable)
是否激活选择功能
```
如果设置为  true，则可使用选中功能与点击功能，反之只能使用点击功能而无法使用 getCheckedSet() 方法
```

#
### TagFlowLayout setMaxCheckCount(int maxCheckCount, int... position)
设置选择状态
```
maxCheckCount: 最多可选中的数量 默认为单选
    可设置：
      SINGLECHOICE：单选
      MULTIPLECHOICE：多选，不限数量
      大于1的自然整数：限制为输入的数量
position：预选中的item下标
```
此功能对应 XML 属性为 flow:max_select = SINGLECHOICE | MULTIPLECHOICE | 大于1的自然整数

#
### TagFlowLayout setOnItemClickLienter(OnItemClickLienter onItemClickLienter)
设置item点击监听
```
flowLayout.setOnItemClickLienter(new FlowLayout.OnItemClickLienter() {
            @Override
            public void onClick(int position, ItemView itemView, FlowLayout flowLayout) {
                Log.e("onClick", String.valueOf(position));
            }
        });
	
//参数
 //position：当前点击的item下标
  //itemView：当前点击的item
  //flowLayout：当前flowLayout
```

#
### TagFlowLayout setOnItemCheckedChangeLisenter(OnItemCheckedChangeLisenter onItemCheckedChangeLisenter)
设置item选择监听，如果设置 setCheckedEnable(boolean checkedEnable) 方法为false时，此监听无效
```
flowLayout.setOnItemCheckedChangeLisenter(new FlowLayout.OnItemCheckedChangeLisenter() {
            @Override
            public void onCheckedChange(int position, boolean isChecked) {
                Log.e("onCheckedChange", String.valueOf(position));
            }
	    
	     //参数
	        //position：当前点击的item下标
		//isChecked：是否选中--true当前item已为选中状态
 		//-------------------------------------------------------

            @Override
            public void onChecked(int position) {
                Log.e("onChecked", String.valueOf(position));
            }
	    
	     //参数
	       //position：当前点击的item下标
	       //-------------------------------------------------------
            @Override
            public void onUnChecked(int position) {
                Log.e("onUnChecked", String.valueOf(position));
            }
	    
	     //参数
	       //position：当前点击的item下标
        });
```

#
### void commit() 
提交  执行此方法后以上设置才有效

#
### Set<Integer> getCheckedSet()
获取item选择集合
	
#
### dp2px(float dpValues)
dp单位转px


