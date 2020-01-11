package com.example.newsblogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.newsblogs.adapter.EssayAdapter;
import com.example.newsblogs.db.MyDataBaseOfEssay;
import com.example.newsblogs.entity.Essay;

import java.util.List;

public class LoveActivity extends AppCompatActivity {

    private ListView loveList;
    private List<Essay> loveEssay;
    private EssayAdapter adapter;
    MyDataBaseOfEssay myDataBaseOfEssay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love);
        setTitle("我的收藏文章");

        //加载ListView
        myDataBaseOfEssay = new MyDataBaseOfEssay(this, "essay.db3", null, 1);
        myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);
        loveEssay = myDataBaseOfEssay.queryLove();
        adapter = new EssayAdapter(this,loveEssay);
        loveList = (ListView) findViewById(R.id.love_lv);
        loveList.setAdapter(adapter);
        loveList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(LoveActivity.this, EssayActivity.class);
                Essay essay = loveEssay.get(i);
                System.out.println(i);
                String title = essay.getTitle();
                String content = essay.getContext();
                System.out.println(content);
                intent.putExtra("title", title);
                intent.putExtra("content",content);
                intent.putExtra("request","look");
                startActivityForResult(intent, 1);
            }
        });
    }
}
