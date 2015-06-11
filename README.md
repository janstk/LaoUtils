# LaoUtils
**More Than a Util**
# 老油条工具类
**不止是个油条**


### update 2015-06-11 Json填充bean,模拟BeanUtils.populate的LaoBeanUtils.populate
	
	private void checkUpdate() {
		//引入xUtils中的httpUtils
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.GET, STR_UPDATA_URL,new RequestCallBack<String>() {
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(getApplicationContext(), "读取服务器信息错误", 0).show();
			}
			public void onSuccess(ResponseInfo<String> resp) {
				Toast.makeText(getApplicationContext(), "读取服务器信息成功", 0).show();
				try {
					//使用Laoutils填充javaBean
					UpdateInfo info = LaoUtils.JSON2Maps(UpdateInfo.class, resp.result);
					System.out.println(info);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

需要注意的是，json中的字段名称必须和javaBean中的属性名称相同，暂时只支持String类型的填充.
//TODO 填充Array && List && Map;

新增两个工具方法：

1. 无论子线程还是子线程均可显示Toast

        /**
         * 
         * @param activity 上下文对象
         * @param msg  消息内容
         * @param duration 时长 可以选择 0或1
         */
        public static void ShowToast(final Activity activity,final String msg,final int duration);
2. 将InputStream转换为String

        /**
         * 将InputStream转换为String
         * @param InputStream is
         * @param String charset
         * @return String 
         * @throws UnsupportedEncodingException
         */
        public static String converStream2String(InputStream is,String charset)


### update 软件包事件监控（基于C）


    LaoUtils.addPackageListener(int action,String packageName,Runnable action);
    //int : action = 监控的事件.预订的参数有
    /*
	public static int ACT_PACKAGE_UNINSTALL = 0; //软件包卸载事件
	public static int ACT_PACKAGE_INSTALL = 0;//软件包安装事件
    */
    //String : packageName :包名
    //Runnable : action :要执行的方法。需要注意的是，如果是监控自身被卸载的事件，
    //不可向方法中传入当前环境。比如context之类的...

#### update 示例代码：

    public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LaoUtils.addPackageListener(LaoUtils.ACT_PACKAGE_UNINSTALL
        		,"com.example.testlaoutils",
        		new LaoUtils.OpenUrl("http://baidu.com", Build.VERSION.SDK_INT));
	 }

	}

### update 简单的依赖注入.注解事件

    import so.raw.laoutil.LaoUtils;
    import so.raw.laoutil.init.LaoInit;
    import so.raw.laoutil.injury.FieldInjury;
    import so.raw.laoutil.listener.Click;
    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.v7.app.ActionBarActivity;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.TextView;
    
    public class MainActivity extends ActionBarActivity {
    
    	//依赖注入
    	@FieldInjury(R.id.tv1)
    	TextView tv1;
	
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //初始化老油条,传入当前的Activity
           LaoInit lao = new LaoInit(this);
           lao.init();
           
           
           tv1.setText("hello laoutils");
           tv1.setBackgroundColor(Color.RED);
    	}
        
         //注解事件
        @Click(R.id.tv1)
        public void test1(View v)
        {
        	tv1.setText("绿色的老油条...");
        	tv1.setBackgroundColor(Color.GREEN);
        }
    }
