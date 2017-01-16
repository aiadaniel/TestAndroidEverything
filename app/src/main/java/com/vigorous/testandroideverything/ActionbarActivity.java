package com.vigorous.testandroideverything;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.vigorous.widget.RoundProgress;


public class ActionbarActivity extends ActionBarActivity {

    RoundProgress roundProgress;
    Button btnTestProgress;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.show();;
        roundProgress = (RoundProgress) findViewById(R.id.round_progress);
        btnTestProgress = (Button) findViewById(R.id.btn_test_progress);
        btnTestProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundProgress.stopProgress();
            }
        });
        roundProgress.startProgress();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
