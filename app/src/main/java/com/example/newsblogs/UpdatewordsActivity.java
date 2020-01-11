package com.example.newsblogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsblogs.db.UserDatabaseHelper;

public class UpdatewordsActivity extends AppCompatActivity {

    Button changeWords;
    EditText changeContent;
    UserDatabaseHelper userDatabaseHelper;
    String getName,editConent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatewords);
        setTitle("修改个性签名");
        Intent intent = getIntent();
        getName = intent.getStringExtra("name");
    }

    @Override
    protected void onResume() {
        super.onResume();
        userDatabaseHelper = new UserDatabaseHelper(this);
        changeContent = (EditText)findViewById(R.id.change_edit);
        changeWords = (Button)findViewById(R.id.change_btn);
        changeWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editConent = changeContent.getText().toString().trim();
                if (editConent==null){
                    AlertDialog show = new AlertDialog.Builder(UpdatewordsActivity.this)
                            .setTitle("提示")
                            .setIcon(R.drawable.warm)
                            .setMessage("请输入内容！")
                            .setNegativeButton("关闭", null)
                            .show();
                }else{
                    userDatabaseHelper.addWords(getName,editConent);
                    Toast.makeText(UpdatewordsActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
                    finish();
                    //Intent intent2 = new Intent(UpdatewordsActivity.this,MainActivity.class);
                    //intent2.putExtra("ECONTENT",editConent);
                    //startActivity(intent2);
                }

            }
        });
    }
}
