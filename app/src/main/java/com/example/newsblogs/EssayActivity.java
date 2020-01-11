package com.example.newsblogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsblogs.db.MyDataBaseOfComment;
import com.example.newsblogs.db.MyDataBaseOfEssay;
import com.example.newsblogs.entity.Essay;

public class EssayActivity extends AppCompatActivity {

    private String strTitle,strContent,getGrade,userName;
    private Intent intent;
    private TextView title,content,author,date,sort;
    private Button comment;
    private EditText editText;
    private ImageView loveEssay;
    private Essay essay;
    MyDataBaseOfEssay myDataBaseOfEssay;
    MyDataBaseOfComment myDataBaseOfComment;
    private int love;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);

        //inten获取接收参数
        intent = getIntent();
        strTitle = intent.getStringExtra("title");
        strContent = intent.getStringExtra("content");
        getGrade = intent.getStringExtra("grade");
        userName = intent.getStringExtra("name");

        //初始化数据库操作
        myDataBaseOfEssay = new MyDataBaseOfEssay(this,"essay.db3",null,1);
        myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);
        myDataBaseOfComment = new MyDataBaseOfComment(this,"essay.db3",null,1);
        myDataBaseOfComment.getDataBase(myDataBaseOfComment);

        sort = (TextView)findViewById(R.id.sort_tv);
        author = (TextView)findViewById(R.id.autho_tv);
        date = (TextView)findViewById(R.id.time_tv);
        title = (TextView)findViewById(R.id.title1);
        content = (TextView)findViewById(R.id.content1);
        loveEssay = (ImageView)findViewById(R.id.loveEssay_btn);
        comment = (Button)findViewById(R.id.comment_btn);
        editText = (EditText)findViewById(R.id.edit_comment) ;

        title.setText(strTitle);
        content.setText(strContent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLove();
        essay = showArticle(strTitle);
        author.setText(essay.getAuthor());
        date.setText(essay.getDate());
        sort.setText(essay.getSort());
    }

    private  Essay showArticle(String title){
        return myDataBaseOfEssay.onlyQueryOneEssay(title);
    }

    private void showLove() {
        essay = myDataBaseOfEssay.queryLove(strTitle);
        love = essay.getLove();
        if (love==1){
            loveEssay.setImageResource(R.drawable.love);
            //loveEssay.setImageDrawable(getResources().getDrawable(R.drawable.love));
        }else {
            loveEssay.setImageResource(R.drawable.love2);
            //loveEssay.setImageDrawable(getResources().getDrawable(R.drawable.love2));
        }
    }

    //喜爱单击事件
    public void loveEssayClick(View view) {

        if("2".equals(getGrade)){
            Toast.makeText(this,"游客无法操作，请先注册登录~",Toast.LENGTH_LONG).show();
        }else{
            //loveEssay.setImageResource(R.drawable.love);
            essay = myDataBaseOfEssay.queryLove(strTitle);
            love = essay.getLove();
            if (love==1){
                loveEssay.setImageResource(R.drawable.love2);
                //loveEssay.setImageDrawable(getResources().getDrawable(R.drawable.love2));
                myDataBaseOfEssay.changeLove(strTitle,2);
            }else {
                essay.setLove(1);
                loveEssay.setImageResource(R.drawable.love);
                //loveEssay.setImageDrawable(getResources().getDrawable(R.drawable.love));
                myDataBaseOfEssay.changeLove(strTitle,1);
            }
        }


    }

    //评论单击事件
    public void commentConfirm(View view) {
        String getComment = editText.getText().toString();
        String title = strTitle;
        String uname = userName;
        if("2".equals(getGrade)){
            new AlertDialog.Builder(EssayActivity.this)
                    .setTitle("提示")
                    .setIcon(R.drawable.warm)
                    .setMessage("游客无法评论，请先注册登录！")
                    .setNegativeButton("关闭", null)
                    .show();
        }else{
            //myDataBaseOfComment.saveComment(title,uname,getComment);
            new AlertDialog.Builder(EssayActivity.this)
                    .setTitle("提示")
                    .setIcon(R.drawable.warm)
                    .setMessage("发布成功")
                    .setNegativeButton("关闭", null)
                    .show();
            editText.setText("");
        }



    }
}
