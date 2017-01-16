package com.vigorous.testandroideverything;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.vigorous.widget.LinePathView;

import java.io.IOException;

/**
 * Created by lxm on 2017/1/2.
 */

public class SignActivity extends BaseActivity implements View.OnClickListener {
    Button btnClear, btnSave, btnChangeColor, btnChangeWidth;
    LinePathView mPathView;
    //CookieManager cookieManager;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                mPathView.clear();
                break;
            case R.id.btn_save:
                if (mPathView.getTouched()) {
                    try {
                        mPathView.save("/sdcard/qm.png", true, 10);
                        setResult(100);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SignActivity.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_change_color:
                mPathView.setBackColor(Color.RED);

                mPathView.setPenColor(Color.WHITE);
                mPathView.clear();
                break;
            case R.id.btn_change_width:
                mPathView.setPaintWidth(20);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mPathView = (LinePathView) findViewById(R.id.sign_view);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnChangeColor = (Button) findViewById(R.id.btn_change_color);
        btnChangeWidth = (Button) findViewById(R.id.btn_change_width);
        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnChangeColor.setOnClickListener(this);
        btnChangeWidth.setOnClickListener(this);
    }
}
