package com.example.newsblogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.newsblogs.db.UserDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

   private Button register;
   private EditText ruserText,rpwdText,repeatText;
   private CheckBox cbChecked;
   private UserDatabaseHelper udbh2;
   //private String rname,rpwd,rrepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setTitle("注册");

    }

    @Override
    protected void onResume() {
        super.onResume();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rname = ruserText.getText().toString().trim();
                String rpwd = rpwdText.getText().toString().trim();
                String rrepeat = repeatText.getText().toString().trim();

                //判断非空
                if(ruserText.length()==0||rpwdText.length()==0||repeatText.length()==0){
                    //对话框
                    AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("提示")
                            .setIcon(R.drawable.warm)
                            .setMessage("请输入完整信息！")
                            .setNegativeButton("关闭", null)
                            .show();
                }else if(!rpwd.equals(rrepeat)){
                    AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("提示")
                            .setIcon(R.drawable.warm)
                            .setMessage("请保持密码和确认密码一致！")
                            .setNegativeButton("关闭", null)
                            .show();
                    ruserText.setText("");
                    rpwdText.setText("");
                    repeatText.setText("");
                }else{
                    Boolean registerResult = registerUser(rname,rpwd);
                    if(registerResult){
                        if (cbChecked.isChecked()){
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("提示")
                                    .setIcon(R.drawable.success)
                                    .setMessage("注册成功！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent=new Intent(RegisterActivity.this,LoginRegisterActivity.class);
                                            startActivity(intent);
                                            //System.exit(0);
                                        }
                                    })
                                    .show();
                        }else{
                            AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                    .setTitle("提示")
                                    .setIcon(R.drawable.warm)
                                    .setMessage("请勾选确认条款！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    })
                                    .show();
                        }

                    }else{
                        AlertDialog show = new AlertDialog.Builder(RegisterActivity.this)
                                .setTitle("提示")
                                .setIcon(R.drawable.fail)
                                .setMessage("注册失败！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent=new Intent(RegisterActivity.this,LoginRegisterActivity.class);
                                        startActivity(intent);
                                        //System.exit(0);
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }

    public void initView(){
        ruserText=(EditText)findViewById(R.id.user_register_text);
        rpwdText=(EditText)findViewById(R.id.password_register_text);
        repeatText=(EditText)findViewById(R.id.repeat_register_text);
        cbChecked=(CheckBox)findViewById(R.id.confirm_cb);
        register=(Button)findViewById(R.id.register_btn);
    }

    //注册 插入数据
    public boolean registerUser(String rname,String rpwd){
        udbh2=new UserDatabaseHelper(this);
        long registerResultValue = udbh2.addData(rname,rpwd);
        if(registerResultValue!=-1){
            return true;
        }else{
            return false;
        }
    }

    //弹出对话复用？
    public void showAlertDialog(String title,String icon,String message,String thisActivityName,String jumpActivityName){

    }

}
