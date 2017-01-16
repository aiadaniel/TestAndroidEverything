package com.vigorous.testandroideverything;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vigorous.fragments.DownFragment;
import com.vigorous.fragments.UpFragment;
import com.vigorous.widget.TwoScrollviewLayout;

import static com.vigorous.testandroideverything.R.id.two_scroll_view_layout;

public class ScrollActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        TwoScrollviewLayout layout = (TwoScrollviewLayout) findViewById(two_scroll_view_layout);
        layout.addFragment(new Fragment[]{new UpFragment(),new DownFragment()},getSupportFragmentManager());
    }
}
