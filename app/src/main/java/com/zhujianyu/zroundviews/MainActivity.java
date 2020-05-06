package com.zhujianyu.zroundviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zhujianyu.roundviews.Orientation;
import com.zhujianyu.roundviews.RoundRelativeLayout;
import com.zhujianyu.roundviews.RoundTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RoundTextView rtvText = findViewById(R.id.rtv_text);
        rtvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaaa", "点击TextView");
            }
        });
    }
}
