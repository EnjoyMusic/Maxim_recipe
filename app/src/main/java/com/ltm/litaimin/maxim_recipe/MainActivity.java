package com.ltm.litaimin.maxim_recipe;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tool.Http_Vegetable;
import vo.vegetableinfo;

public class MainActivity extends AppCompatActivity {

    //private List<vegetableinfo> vegetable=new ArrayList<vegetableinfo>();
    private TextView tv_cs;
    private List<vegetableinfo> vegetable;
    Handler mHandel=new Handler(){

        @Override
        public void handleMessage(Message msg) {
        StringBuffer is=new StringBuffer();
            for(vegetableinfo ve: vegetable){
                is.append(ve.getDescription()).append(ve.getTypeid()).append(ve.getTypename()).append(ve.getTypepic());
            }
            tv_cs.setText(is.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }
    private void initview() {
        tv_cs = findViewById(R.id.tv_text);
        initresult();
      //  Log.i("TAG", String.valueOf(vegetable.size()));
    }
    private void initresult() {
        new Thread(){
            @Override
            public void run() {
                //vegetable=
                vegetable = Http_Vegetable.getVegetable();
                Log.i("TAg", String.valueOf(Http_Vegetable.getVegetable().size()));
                mHandel.sendEmptyMessage(0);
            }
        }.start();
    }
}
