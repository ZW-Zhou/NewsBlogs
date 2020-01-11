package com.example.newsblogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.newsblogs.db.MyDataBaseOfEssay;
import com.example.newsblogs.entity.Essay;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FixessayActivity extends AppCompatActivity {

    Spinner sortFix;
    EditText tieleFix,contentFix;
    TextView timeFix;
    MyDataBaseOfEssay myDataBaseOfEssay;
    String getTitle;
    Essay essay;
    String sortTxt,titleFixTxt,contentFixTxt,oldTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixessay);
        setTitle("修改文章");
        initView();

        Intent intent = getIntent();
        getTitle = intent.getStringExtra("name");
        oldTitle = intent.getStringExtra("name");
    }

    public void initView(){
        myDataBaseOfEssay = new MyDataBaseOfEssay(this, "essay.db3", null, 1);
        myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);
        essay = new Essay();
        sortFix = (Spinner)findViewById(R.id.type_spinner2);
        tieleFix = (EditText)findViewById(R.id.title22);
        contentFix = (EditText)findViewById(R.id.content22);
        timeFix = (TextView) findViewById(R.id.create_time_tv2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        essay = myDataBaseOfEssay.onlyQueryOneEssay(getTitle);
        tieleFix.setText(essay.getTitle());
        contentFix.setText(essay.getContext());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //创建对象制定日期格式
        String date = format.format(new Date());
        timeFix.setText(date);

    }

    //确认修改按钮单击事件
    public void changeEssay(View view) {
        sortTxt = sortFix.getSelectedItem().toString();
        titleFixTxt = tieleFix.getText().toString();
        //Log.i("zzw",titleFixTxt);
        contentFixTxt = contentFix.getText().toString();
        myDataBaseOfEssay.updateEssay(oldTitle,titleFixTxt,contentFixTxt,sortTxt);
        new AlertDialog.Builder(FixessayActivity.this)
                .setTitle("结果")
                .setIcon(R.drawable.success)
                .setMessage("修改成功！")
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        })
                .show();

    }

    //取消按钮单击事件
    public void cancelChange(View view) {
        finish();
        //Intent intent = new Intent(this,ManagerblogsActivity.class);
        //startActivity(intent);
    }
}
