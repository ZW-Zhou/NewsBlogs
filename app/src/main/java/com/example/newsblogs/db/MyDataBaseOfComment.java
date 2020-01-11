package com.example.newsblogs.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import com.example.newsblogs.entity.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDataBaseOfComment extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private List<Comment> list;
    private  Comment comment;
    private String sql;

    public MyDataBaseOfComment(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE comment(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,name text,content text,date text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void getDataBase(MyDataBaseOfComment myDataBase){
        db = myDataBase.getWritableDatabase();
    }

    public List<Comment> queryAllComment(){
        sql = "select * from essay";
        Cursor cursor = db.rawQuery(sql,new String[]{});
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            comment = new Comment();
            comment.setTitle(cursor.getString(1));
            list.add(comment);
        }
        return list;
    }

    public List<Comment> queryCommentByTitle(String title){
        sql = "select * from comment where title=?";
        Cursor cursor = db.rawQuery(sql,new String[]{title});
        if (cursor.getCount()==0){
            return null;
        }else {
            list = new ArrayList<>();
            while (cursor.moveToNext()){
                comment = new Comment();
                comment.setTitle(cursor.getString(1));
                comment.setName(cursor.getString(2));
                comment.setContent(cursor.getString(3));
                comment.setDate(cursor.getString(4));
                list.add(comment);
            }
        }
        return list;
    }


    public void changeLove(String title,int love){
        sql = "update essay set love=? where title=?";
        db.execSQL(sql,new Object[]{love,title});
    }

    public void updateEssay(String title,String content,String date,String sort,String title1){
        sql = "update essay set content=? and date=? and sort=? and title=?where title=?";
        db.execSQL(sql,new Object[]{content,date,sort,title,title1});
    }

    /**
     * title text,name text,content text,date text
     * @param title
     * @param name
     */
    public void saveComment(String title,String name,String content){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);

        sql = "insert into comment(title,name,content,date) values(?,?,?,?)";
        db.execSQL(sql,new Object[]{title,name,content,date});
    }

    public void deleteComment(String title,String name){
        sql = "delete from love where title=? and name=?";
        db.execSQL(sql,new Object[]{title,name});
    }


    public List<Comment> queryLove(){
        sql = "select * from essay where love=2";
        Cursor cursor = db.rawQuery(sql,null);
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            comment = new Comment();
            comment.setTitle(cursor.getString(1));
            list.add(comment);
        }
        return list;
    }
}
