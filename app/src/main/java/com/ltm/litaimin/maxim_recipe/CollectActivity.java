package com.ltm.litaimin.maxim_recipe;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import db.Recipedao;
import vo.MenuDetail;

import static tool.getdrawable.getdrawable;

public class CollectActivity extends Activity {

    private ListView lv_collectlist;
    private List<MenuDetail> menuDetails=null;
    private Myadapter myadapter;
    private Thread thread;
    private Recipedao recipedao;
    Handler handler=new Handler(){
         @Override
         public void handleMessage(Message msg) {
             if(menuDetails!=null){
                 myadapter=new Myadapter();
                 lv_collectlist.setAdapter(myadapter);
             }
             if(myadapter!=null){
                 myadapter.notifyDataSetChanged();
             }
         }
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_list);
        init_view();
        init_date();
    }

    private void init_view() {
        lv_collectlist = findViewById(R.id.lv_collectlist);
    }

    private void init_date() {
        thread = new Thread() {
            @Override
            public void run() {
                recipedao = Recipedao.getRecipedao(CollectActivity.this);
                menuDetails = new ArrayList<MenuDetail>();
                menuDetails = recipedao.selectall_collect();
                handler.sendEmptyMessage(0);
            }
        };
        thread.start();
    lv_collectlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent();
            intent.putExtra("menuid",menuDetails.get(position).getMenuid());
            startActivity(intent);
        }
    });
    }

    class Myadapter extends BaseAdapter{
        private ViewHolder holder;
        @Override
        public int getCount() {
            return menuDetails.size();
        }

        @Override
        public MenuDetail getItem(int position) {
            return menuDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MenuDetail item = getItem(position);
            if(convertView==null){
                convertView=View.inflate(getApplicationContext(),R.layout.collect_item,null);
                holder = new ViewHolder();
                holder.iv_icon = convertView.findViewById(R.id.iv_icon);
                holder.iv_star = convertView.findViewById(R.id.iv_star);
                holder.tv_mainmaterial = convertView.findViewById(R.id.tv_mainmaterial);
                holder.tv_abstracts = convertView.findViewById(R.id.tv_abstracts);
                holder.tv_name = convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }
             holder = (ViewHolder)convertView.getTag();
             holder.tv_name.setText(item.getMenuname());
             holder.tv_abstracts.setText(item.getAbstracts());
             holder.tv_mainmaterial.setText(item.getMainmaterial());
            Drawable getdrawable = getdrawable(item.getSpic(), getApplicationContext());
            if(getdrawable==null){
                getdrawable=getDrawable(R.drawable.ic_launcher_background);
            }
            holder.iv_icon.setBackgroundDrawable(getdrawable);
            holder.iv_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recipedao.delete_collect(item.getMenuid());
                    Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                    menuDetails.remove(position);
                    handler.sendEmptyMessage(0);
                }
            });
            return convertView;
        }
        class ViewHolder{
            ImageView iv_icon;
            ImageView iv_star;
            TextView tv_mainmaterial;
            TextView tv_abstracts;
            TextView tv_name;
        }
    }
}
