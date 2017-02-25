package com.vigorous.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vigorous.testandroideverything.R;

public class BottomTabActivity extends AppCompatActivity {

    //底部导航栏的实现方式目前想到以下几种：
    //1. tabHost
    //2. LinearLayout + TextView (另外看到一个： http://www.cnblogs.com/yc-755909659/p/4286672.html)
    //3. RadioGroup
    //4. tabLayout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);//目前使用方式2，但这种方式不易于扩展比如未读消息之类的;
        //另外有些样式重复的，可以提取到style里面
        TextView tvChat = (TextView) findViewById(R.id.tv_chat);
        tvChat.setSelected(true);
        TextView tvOpen = (TextView) findViewById(R.id.tv_open);
        tvOpen.setSelected(true);
    }

}
