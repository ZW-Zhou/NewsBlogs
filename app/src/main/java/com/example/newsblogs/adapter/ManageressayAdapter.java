package com.example.newsblogs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newsblogs.R;
import com.example.newsblogs.entity.Essay;

import java.util.List;


public class ManageressayAdapter extends BaseAdapter{

    List<Essay> essaysList;
    Context context;
    public int visibility=View.GONE,choosed;
    ImageView imageView,imageChoosed;
    TextView tvTitle;
    RelativeLayout relaLayout;

    public ManageressayAdapter(Context context, List<Essay> essays){
        this.context=context;
        this.essaysList=essays;
    }
    @Override
    public int getCount() {
        return essaysList.size();
    }

    @Override
    public Object getItem(int i) {
        return essaysList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(view==null){
            view=  LayoutInflater.from(context).inflate(R.layout.managerlist_item,parent,false);
        }
        imageView=(ImageView)view.findViewById(R.id.image);
        imageChoosed=(ImageView)view.findViewById(R.id.choosed_img);
        tvTitle=(TextView)view.findViewById(R.id.title);
        relaLayout=(RelativeLayout) view.findViewById(R.id.items_rela);

        imageChoosed.setVisibility(visibility);//设置不可见
        tvTitle.setText(essaysList.get(i).getTitle());
        //relaLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        //Log.i("zzw",i+"");

        if(essaysList.get(i).getTitle().length()%2==0){
            if(essaysList.get(i).getTitle().length()%4==0){
                imageView.setImageResource(R.drawable.t1);
            }else{
                imageView.setImageResource(R.drawable.t3);
            }

        }else if(((essaysList.get(i).getTitle().length())+1)%4==0){
            imageView.setImageResource(R.drawable.t4);
        }else{
            imageView.setImageResource(R.drawable.t5);
        }

        return view;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getChoosed() {
        return choosed;
    }

    public void setChoosed(int choosed) {
        this.choosed = choosed;
    }



}

