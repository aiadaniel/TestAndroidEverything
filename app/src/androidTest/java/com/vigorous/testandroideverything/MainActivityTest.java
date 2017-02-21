package com.vigorous.testandroideverything;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.vigorous.activities.MainActivity;

/**
 * Created by lxm .
 * 这是专门给activity的测试，可以模拟按键事件等，但要注意ui的测试需要跑在主线程
 * 参考原文： http://www.jb51.net/article/74977.htm
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    MainActivity mainActivity;
    Button btnSign;

    public MainActivityTest(Class activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        /**这个程序中需要输入用户信息和密码，也就是说需要发送key事件，
         * 所以，必须在调用getActivity之前，调用下面的方法来关闭
         * touch模式，否则key事件会被忽略
         */
        setActivityInitialTouchMode(false);

        instrumentation = getInstrumentation();
        mainActivity = (MainActivity) getActivity();
        btnSign = (Button) mainActivity.findViewById(R.id.btn_linepath);
    }

    public void testPreConditions() {
        assertNotNull(btnSign);
    }

    public void testInput() {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnSign.setText("I'm test");
            }
        });

        /**由于测试用例在单独的线程上执行，所以此处需要同步application，
        * 调用waitForIdleSync等待测试线程和UI线程同步，才能进行输入操作。
        * waitForIdleSync和sendKeys不允许在UI线程里运行
        */
        instrumentation.waitForIdleSync();

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnSign.performClick();//click event
            }
        });
    }

    public void testLogin() {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                assertEquals("I'm test",btnSign.getText().toString());
            }
        });
    }


}
