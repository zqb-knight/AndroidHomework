## How to use

- 下载

## 任务一：在屏幕旋转时保存“on destory”和“on stop”信息

### 1. 思路

Application类的生存周期长于Activity，声明一个继承于Application的类CustomApplication，将log信息存储于Application类中的变量中，再次打开Activity时访问该变量，得到log历史记录。

### 2.步骤

#### 声明CustomApplication

```java
public class CustomApplication extends Application {
    private static final String VALUE = "";
    private String history;          //存储log历史记录
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量

    }
    public void setValue(String value)
    {
        this.history = value;
    }
    public String getValue()
    {
        return history;
    }
}
```

#### Activity中new一个CustomApplication类变量。

```java
private CustomApplication app;
app = (CustomApplication)getApplication(); // 获得CustomApplication对象
```

#### logAndAppend()方法

输出log并将记录存入application中的history变量中。

```java
private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);
        mLifecycleDisplay.append(lifecycleEvent + "\n");
        app.setValue(app.getValue() + "\n" + lifecycleEvent);
```

## 任务二：计算一个activity里的所有View数目

#### 1.思路

Android的View和ViewGroup是一个树形结构，且view包含于ViewGroup。

因此，这个问题可以抽象为树的叶子节点的数目。

这次采用深度优先搜索的递归算法。

#### 2.步骤

使用了ViewGroup两个API：getChildAt(int) 、getChildCount().

```java
public int getAllChildViewCount(View view) {
        int count = 0;    //计数变量
        if(view == null){
            return 0;
        }
        //遍历树
        if(view instanceof ViewGroup){
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++){
                View child = ((ViewGroup) view).getChildAt(i);
                if(child instanceof  ViewGroup){
                    count += getAllChildViewCount(child);
                }
                else{
                    count++;
                }
            }
        }
        else count++;
        return count;
    }
```



## 任务三：模仿抖音消息界面，使用ReycleView

#### 1. 使用PullParser解析data.xml,将解析到的数据以list传给adapter。

- PullParser解析data.xml（老师提供）

  ```Java
  try {
              InputStream assetInput = getAssets().open("data.xml");
              messages = PullParser.pull2xml(assetInput);
          } catch (Exception exception) {
              exception.printStackTrace();
          }
  ```

- 将messages通过adapter的构造函数传给adapter

  ```java
  mAdapter = new CardAdapter(NUM_LIST_ITEMS, this, messages);
  ```

  #### 2. 在Adapter中的onCreateViewHolder将数据赋值给view

  ```java
  NumberViewHolder viewHolder = new NumberViewHolder(view);
          String name = data.get(viewHolderCount).getTitle();
          viewHolder.nameText.setText(name);
          String content = data.get(viewHolderCount).getDescription();
          viewHolder.contentText.setText(content);
          String time = data.get(viewHolderCount).getTime();
          viewHolder.timeText.setText(time);
          String logo = data.get(viewHolderCount).getIcon();
          Log.d("icon", logo);
          if(logo.equals("TYPE_ROBOT")){
              viewHolder.userImage.setImageResource(R.drawable.session_robot);
          }
          if(logo.equals("TYPE_STRANGER")){
  
              viewHolder.userImage.setImageResource(R.drawable.session_stranger);
          }
          if(logo.equals("TYPE_SYSTEM")){
  
              viewHolder.userImage.setImageResource(R.drawable.session_system_notice);
          }
  
          boolean isOffice = data.get(viewHolderCount).isOfficial();
          if(isOffice) {
              viewHolder.officeImage.setVisibility(view.VISIBLE);
          }
  ```

  #### 3. 添加跳转方法，并将序号和消息描述传给chatroom界面

  ```Java
  @Override
      public void onListItemClick(int clickedItemIndex) {
          Log.d(TAG, "onListItemClick: ");
          Intent intent = new Intent(this, content.class);
          intent.putExtra("info",messages.get(clickedItemIndex).getDescription());
          intent.putExtra("index",clickedItemIndex);  //序号
          startActivity(intent);
      }
  ```

  