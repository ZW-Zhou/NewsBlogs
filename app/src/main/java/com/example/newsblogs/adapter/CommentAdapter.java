package com.example.newsblogs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsblogs.R;
import com.example.newsblogs.entity.Comment;
import com.example.newsblogs.entity.Essay;

import java.util.List;


public class CommentAdapter extends BaseAdapter{

    List<Comment> commentList;
    Context context;

    public CommentAdapter(Context context, List<Comment> comment){
        this.context=context;
        this.commentList=comment;
    }
    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int i) {
        return commentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(view==null){
            view=  LayoutInflater.from(context).inflate(R.layout.itemcom_layout,parent,false);
        }
        ImageView imageView=(ImageView)view.findViewById(R.id.com_img);
        TextView userName=(TextView)view.findViewById(R.id.name_tv);
        TextView userComment=(TextView)view.findViewById(R.id.comment_tv);

        userName.setText(commentList.get(i).getTitle());
        userComment.setText(commentList.get(i).getContent());

        if(commentList.get(i).getTitle().length()%2==0){
            if(commentList.get(i).getTitle().length()%4==0){
                imageView.setImageResource(R.drawable.head);
            }else{
                imageView.setImageResource(R.drawable.head);
            }

        }else if(((commentList.get(i).getTitle().length())+1)%4==0){
            imageView.setImageResource(R.drawable.head);
        }else{
            imageView.setImageResource(R.drawable.head);
        }

        return view;
    }
}

