# LaoUtils
More Than a Util
### new 简单的依赖注入.注解事件


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
