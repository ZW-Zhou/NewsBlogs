package com.example.newsblogs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newsblogs.entity.Essay;
import com.example.newsblogs.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="news1.db3";
    private static final String TABLE_NAME="newsUser";
    private static final int VERSION=1;
    private static final String KEY_NAME="uname";
    private static final String KEY_PASSWORD="upassword";
    private static final String KEY_WORDS="userwords";
    private static final String KEY_GRADE="usergrade";
    public static final String CREATE_TABLE="create table "+TABLE_NAME+"("+KEY_NAME+
            " text primary key,"+KEY_PASSWORD+" text not null,"+KEY_WORDS+" text, "+KEY_GRADE+" integer not null);";
    SQLiteDatabase db;

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        db=this.getWritableDatabase();//
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    //查询数据库
    public boolean selectDatabase(String uname,String upwd){
        db = getReadableDatabase();
        String sql = "select * from "+TABLE_NAME+" where "+KEY_NAME+"=? and "+KEY_PASSWORD+"=?";
        Cursor cursor = db.rawQuery(sql,new String[]{uname,upwd});
        //int i = cursor.getCount();
        //Log.i("zzw",i+"");
        if (cursor.getCount()==0){
            return false;
        }else{
            return true;
        }
//        while(cursor.moveToNext()){
//
//            if(cursor.getString(1).equals(uname)&&cursor.getString(2).equals(upwd)){
//                return true;
//            }
//        }
        //return false;
    }

    //插入数据
    public long addData(String rname,String rpassword){
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,rname);
        values.put(KEY_PASSWORD,rpassword);
        values.put(KEY_WORDS,"快编辑你的个性签名吧");
        values.put(KEY_GRADE,1);// 1 表示普通用户，0 管理员，其他为游客
        long insertResult = db.insert(TABLE_NAME,null,values);
        return insertResult;//为-1表示插入失败
    }

    //修改个性签名
    public void addWords(String userName,String userWordss){
        String sql = "update "+TABLE_NAME+" set userwords=? where "+KEY_NAME+"=?";
        //ContentValues values=new ContentValues();
        //values.put(KEY_WORDS,userWords);
        db.execSQL(sql,new String[]{userWordss,userName});
    }

    //查询用户所有信息
    public User queryUserByName(String name){
        String sql = "select * from newsUser where uname=?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        User user = new User();
        while (cursor.moveToNext()){
            user.setName(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setWords(cursor.getString(cursor.getColumnIndex("userwords")));
            user.setGrade(cursor.getInt(cursor.getColumnIndex("usergrade")));
        }
        return user;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
