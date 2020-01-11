package com.example.newsblogs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsblogs.R;
import com.example.newsblogs.entity.Essay;

import java.util.List;


public class EssayAdapter extends BaseAdapter{

    List<Essay> essaysList;
    Context context;

    public EssayAdapter(Context context, List<Essay> essays){
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
            view=  LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        }
        ImageView imageView=(ImageView)view.findViewById(R.id.image);
        TextView tvTitle=(TextView)view.findViewById(R.id.title);

        tvTitle.setText(essaysList.get(i).getTitle());

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
}

