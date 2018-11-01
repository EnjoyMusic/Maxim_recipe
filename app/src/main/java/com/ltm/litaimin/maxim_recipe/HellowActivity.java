package com.ltm.litaimin.maxim_recipe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HellowActivity extends Activity{

    private TextView tv3;
    private TextView tv2;
    private TextView tv1;
    private ConnectivityManager connectivityManager;
    private NetworkInfo activeNetworkInfo;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hellow);
       initnetwork();
        initview();
       initAnmaion();

    }

    //检测网络状态
    private void initnetwork() {
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    }

    private void initAnmaion() {
        Animation animation=new AlphaAnimation(0,1);
        animation.setDuration(2000);
        tv1.startAnimation(animation);
        Animation animation1=new AlphaAnimation(0,1);
        animation1.setStartOffset(2500);
        animation1.setDuration(1500);
        tv2.startAnimation(animation1);
        Animation animation2=new AlphaAnimation(0,1);
        animation2.setStartOffset(4500);
        animation2.setDuration(1000);
        tv3.startAnimation(animation2);
    }


    private void initview() {
        LinearLayout ll_welcome = findViewById(R.id.ll_welcome);
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
        ll_welcome.setOnClickListener(new View.OnClickListener() {
            /**
             * 跳转到首页，传入networkinfo，当networkinfo为空时表示为连接
             */
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("networkinfo",activeNetworkInfo);
               // startActivity(intent);
                System.out.println("进入HomeActivity");
            }
        });
    }
}
