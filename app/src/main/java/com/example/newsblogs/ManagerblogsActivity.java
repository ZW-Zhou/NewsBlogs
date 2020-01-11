package com.example.newsblogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newsblogs.adapter.EssayAdapter;
import com.example.newsblogs.adapter.ManageressayAdapter;
import com.example.newsblogs.db.MyDataBaseOfEssay;
import com.example.newsblogs.db.UserDatabaseHelper;
import com.example.newsblogs.entity.Essay;
import com.example.newsblogs.entity.User;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class ManagerblogsActivity extends AppCompatActivity {

    private ListView managerList;
    private List<Essay> managerEssay;
    private ManageressayAdapter adapter;
    MyDataBaseOfEssay myDataBaseOfEssay;
    UserDatabaseHelper userDatabaseHelper;
    private int grade;//等级权限
    private Boolean canChange = false;
    private String choosedTitle,getUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerblogs);
        setTitle("博客管理");
        initView();
        choosedTitle=null;

        Log.i("zzw",grade+"");
        //加载ListView 及 实例化数据库帮助类
        myDataBaseOfEssay = new MyDataBaseOfEssay(this, "essay.db3", null, 1);
        myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);
        if(grade==0){
            managerEssay = myDataBaseOfEssay.queryAllEssay();
        }else{//显示用户文章！！！！！！！！

            managerEssay = myDataBaseOfEssay.queryUserEssay(getUser);
        }
        adapter = new ManageressayAdapter(this,managerEssay);
        adapter.setChoosed(0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        managerList.setAdapter(adapter);
        managerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Essay essay = managerEssay.get(i);
                choosedTitle = essay.getTitle();
                Log.i("zzw",choosedTitle);

                new AlertDialog.Builder(ManagerblogsActivity.this)
                        .setIcon(R.drawable.warm)
                        .setTitle("管理文章")
                        .setMessage("选择删除或管理文章！")
                        .setPositiveButton("取消",null)
                        .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                //System.exit(0);
                                Intent fintent = new Intent(ManagerblogsActivity.this,FixessayActivity.class);
                                fintent.putExtra("name",choosedTitle);
                                startActivity(fintent);
                            }
                        })
                        .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int deletRe = 1;
                                myDataBaseOfEssay.deleteByTitle(choosedTitle);
                                if(grade==0){
                                    managerEssay = myDataBaseOfEssay.queryAllEssay();
                                }else{//显示用户文章！！！！！！！！
                                    managerEssay = myDataBaseOfEssay.queryAllEssay();
                                }
                                adapter = new ManageressayAdapter(ManagerblogsActivity.this,managerEssay);
                                managerList.setAdapter(adapter);
                                deleteResultAlert("成功");
                                //adapter.notifyDataSetChanged();
                                //finish();
                                //System.exit(0);
                            }
                        })
                        .show();

            }
        });

    }
    public void initView(){

        //titleList = new ArrayList<>();
        Intent intent = getIntent();
        getUser = intent.getStringExtra("name");
        managerList = (ListView) findViewById(R.id.mager_lv);
        userDatabaseHelper = new UserDatabaseHelper(this);
        User user = new User();
        user = userDatabaseHelper.queryUserByName(getUser);
        grade = user.getGrade();

    }

    //
    public void deleteResultAlert(String re){
        new AlertDialog.Builder(ManagerblogsActivity.this)
                            .setTitle("结果")
                            .setIcon(R.drawable.success)
                            .setMessage("删除文章"+re+"!")
                            .setNegativeButton("关闭", null)
                            .show();
        adapter.notifyDataSetChanged();
    }

}
