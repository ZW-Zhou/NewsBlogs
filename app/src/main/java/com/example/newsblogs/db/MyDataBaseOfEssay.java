package com.example.newsblogs.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.newsblogs.entity.Essay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDataBaseOfEssay extends SQLiteOpenHelper{

    private SQLiteDatabase db;
    private List<Essay> list;
    private  Essay essay;
    private String sql;

    public MyDataBaseOfEssay(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            //sqLiteDatabase.execSQL("CREATE TABLE essay(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,content text,int src)");
            sqLiteDatabase.execSQL("CREATE TABLE essay(id INTEGER PRIMARY KEY AUTOINCREMENT,title text,content text,date text,sort text,love int,author text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public void getDataBase(MyDataBaseOfEssay myDataBase){
            db = myDataBase.getWritableDatabase();
        }

        public List<Essay> queryAllEssay(){
            Date date = new Date();

            String time = date.toString();
            System.out.println(time);

            sql = "select * from essay";
            Cursor cursor = db.rawQuery(sql,new String[]{});
            list = new ArrayList<>();
            while (cursor.moveToNext()){
                essay = new Essay();
                essay.setTitle(cursor.getString(1));
                essay.setContext(cursor.getString(2));
                list.add(essay);
            }
            return list;
        }

    public List<Essay> queryUserEssay(String author){
        Date date = new Date();

        String time = date.toString();
        System.out.println(time);

        sql = "select * from essay where author=?";
        Cursor cursor = db.rawQuery(sql,new String[]{author});
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            essay = new Essay();
            essay.setTitle(cursor.getString(1));
            essay.setContext(cursor.getString(2));
            list.add(essay);
        }
        return list;
    }

    //模糊查询
    public List<Essay> queryOneEssay( String title){
        sql = "select * from essay where title like '%"+ title +"%'";
        Cursor cursor = db.rawQuery(sql,null);
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            essay = new Essay();
            essay.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            essay.setContext(cursor.getString(cursor.getColumnIndex("content")));
            essay.setDate(cursor.getString(cursor.getColumnIndex("date")));
            essay.setSort(cursor.getString(cursor.getColumnIndex("sort")));
            essay.setLove(cursor.getInt(cursor.getColumnIndex("love")));     /**?????????!!!!!!!*/
            essay.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            list.add(essay);
        }   //title text,content text,date text,sort text,love int,author text)");
        return list;
    }

    //
    public Essay onlyQueryOneEssay(String title){
        essay = new Essay();
        sql = "select * from essay where title =?";
        Cursor cursor = db.rawQuery(sql,new String[]{title});
        while (cursor.moveToNext()){
            essay = new Essay();
            essay.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            essay.setContext(cursor.getString(cursor.getColumnIndex("content")));
            essay.setDate(cursor.getString(cursor.getColumnIndex("date")));
            essay.setSort(cursor.getString(cursor.getColumnIndex("sort")));
            essay.setLove(cursor.getInt(cursor.getColumnIndex("love")));     /**?????????!!!!!!!*/
            essay.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            //list.add(essay);
        }   //title text,content text,date text,sort text,love int,author text)");
        return essay;
    }

    public void changeLove(String title,int love){
        sql = "update essay set love=? where title=?";
        db.execSQL(sql,new Object[]{love,title});
    }

    //删除文章
    public void deleteByTitle(String title){
        //String sql = "delete from table essay where title=?";
        sql = "delete from essay where title=?";
        db.execSQL(sql,new String[]{title});

    }

    public List<Essay> queryLove(){
        Date date = new Date();

        String time = date.toString();
        System.out.println(time);

        sql = "select * from essay where love=1";
        Cursor cursor = db.rawQuery(sql,null);
        list = new ArrayList<>();
        while (cursor.moveToNext()){
            essay = new Essay();
            essay.setTitle(cursor.getString(1));
            essay.setContext(cursor.getString(2));
            list.add(essay);
        }
        return list;
    }

    public Essay queryLove(String title){
        sql = "select * from essay where title=?";
        Cursor cursor = db.rawQuery(sql,new String[]{title});
        essay = new Essay();
        while (cursor.moveToNext()){
            essay.setLove(cursor.getInt(5));
        }
        return essay;
    }


    public void saveEssay(String title,String content){
        sql = "insert into essay(title,content) values(?,?)";
        db.execSQL(sql,new Object[]{title,content});
    }
    public void saveEssay(String title,String content,String sort,String author){

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        ////获取当前时间
        //Date date = new Date(System.currentTimeMillis());
        //String dateTime = simpleDateFormat.format(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //创建对象制定日期格式
        String date = format.format(new Date());
        sql = "insert into essay(title,content,sort,author,date) values(?,?,?,?,?)";
        db.execSQL(sql,new Object[]{title,content,sort,author,date});
    }

    public void updateEssay(String title,String newtitle,String content,String sort){

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        ////获取当前时间
        //Date date = new Date(System.currentTimeMillis());
        //String dateTime = simpleDateFormat.format(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //创建对象制定日期格式
        String date = format.format(new Date());
        sql = "update essay set title=? , content=? , sort=? , date=? where title=?";
        db.execSQL(sql,new Object[]{newtitle,content,sort,date,title});
    }



}
