package com.example.newsblogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.newsblogs.db.UserDatabaseHelper;
import com.example.newsblogs.entity.User;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText userText,pwdText;
    CheckBox remberChecked,autoChecked;
    //boolean loginJuderResult=false;
    UserDatabaseHelper udbh;
    //String uName,uPwd;
    String words;
    User user;
    private SharedPreferences sp;//SharedPreferences：以Map形式存放简单数据存储；该对象本身只能获取数据而不支持存储和修改
    private SharedPreferences.Editor editor;//存储修改是通过SharedPreferences.Edit()获取的内部接口Editor对象实现

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");
        initView();
        readLoginInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String uName = userText.getText().toString().trim();
                String uPwd = pwdText.getText().toString().trim();
                Boolean loginJuderResult = loginJudge(uName,uPwd);

                //用户名和密码不可为空
                if(userText.length()==0||pwdText.length()==0){
                    //对话框
                    AlertDialog show = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("提示")
                            .setIcon(R.drawable.warm)
                            .setMessage("请输入用户名及密码！")
                            .setNegativeButton("关闭", null)
                            .show();
                    remberChecked.setChecked(false);
                    autoChecked.setChecked(false);
                }else if(loginJuderResult){
                    //首先判断是否勾选了记住密码
                    if(remberChecked.isChecked()){
                        editor.putString("UNAME",uName);
                        editor.putString("UPWD",uPwd);
                    }
                    else{
                        editor.remove("UNAME");
                        editor.remove("UPWD");
                    }

                    if(autoChecked.isChecked()){
                        editor.putBoolean("isAuto",true);
                    }else{
                        editor.remove("isAuto");
                    }
                    editor.commit();

                    user = udbh.queryUserByName(uName);
                    words = user.getWords();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("name",userText.getText().toString().trim());
                    intent.putExtra("WORDS",words);
                    startActivity(intent);
                }else{
                    AlertDialog show = new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("提示")
                            .setIcon(R.drawable.warm)
                            .setMessage("用户名或密码错误！")
                            .setNegativeButton("关闭", null)
                            .show();
                    userText.setText("");
                    pwdText.setText("");
                    remberChecked.setChecked(false);
                    autoChecked.setChecked(false);
                }
            }
        });
    }

    public void initView(){
        userText=(EditText)findViewById(R.id.user_login_text);
        pwdText=(EditText)findViewById(R.id.password_login_text);
        remberChecked=(CheckBox)findViewById(R.id.remenberpwd);
        autoChecked=(CheckBox)findViewById(R.id.autologin);
        login=(Button)findViewById(R.id.login_btn);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        editor = sp.edit();
    }

    //登录判断 查询数据库结果
    public boolean loginJudge(String uname,String upwd){
        udbh=new UserDatabaseHelper(this);
        boolean selectResult = udbh.selectDatabase(uname,upwd);
        return selectResult;
    }

    private void readLoginInfo(){
        String uname=sp.getString("UNAME",null);
        String upwd=sp.getString("UPWD",null);
        if(uname==null){
            remberChecked.setChecked(false);
        }else{
            userText.setText(uname);
            pwdText.setText(upwd);
            remberChecked.setChecked(true);
        }
        boolean isAuto=sp.getBoolean("isAuto",false);
        if(isAuto){
            //finish();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            //intent.putExtra("login",username);
            autoChecked.setChecked(true);
            startActivity(intent);
        }else{
            autoChecked.setChecked(false);
        }
    }

    public void cancelLogin(View view) {
        finish();
    }

    //游客进入单击事件
    public void visitorLogin(View view) {
        Intent vintent = new Intent(this,MainActivity.class);
        vintent.putExtra("VGRADE",2);
        vintent.putExtra("name","游客");
        startActivity(vintent);
    }
}
