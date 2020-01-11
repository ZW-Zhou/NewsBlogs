package com.example.newsblogs;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.newsblogs.adapter.EssayAdapter;
import com.example.newsblogs.adapter.GlideImageLoader;
import com.example.newsblogs.db.MyDataBaseOfEssay;
import com.example.newsblogs.db.UserDatabaseHelper;
import com.example.newsblogs.entity.Essay;
import com.example.newsblogs.entity.User;
import com.example.newsblogs.ui.dashboard.DashboardFragment;
import com.example.newsblogs.ui.home.HomeFragment;
import com.example.newsblogs.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.Destroyable;
import javax.xml.transform.Transformer;


public class MainActivity extends AppCompatActivity {

    private List<Essay> list;
    private Essay essay;
    private ListView lv;
    private EssayAdapter adapter;
    private SQLiteDatabase db;
    private MyDataBaseOfEssay myDataBaseOfEssay;
    private UserDatabaseHelper userDatabaseHelper;
    private String myname;
    private User user;
    private int grade;
    private Spinner spinner;

    //TextView timeTv;
    private EditText title,content;
    private String strTitle,strContent;
    //private Button fabu;

    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            //transaction.replace(R.id.container,new NotificationsFragment());
            //transaction.commit();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    initHome();
                    currentFragment = new HomeFragment();
                    transaction.replace(R.id.container,currentFragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    currentFragment = new DashboardFragment();
                    transaction.replace(R.id.container,currentFragment);
                    transaction.commit();
                    initDashboard();
                    return true;
                case R.id.navigation_notifications:
                    currentFragment = new NotificationsFragment();
                    transaction.replace(R.id.container,currentFragment);
                    transaction.commit();
                    //initNotification();

                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("News博客");

        myDataBaseOfEssay = new MyDataBaseOfEssay(this,"essay.db3",null,1);
        myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //initHome();

        Intent intent = getIntent();
        myname = intent.getStringExtra("name");
        if (myname.equals("游客")){
            grade = 2;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initHome();
        Intent intent = getIntent();
        myname = intent.getStringExtra("name");
        if(grade!=2){
            userDatabaseHelper = new UserDatabaseHelper(this);
            userDatabaseHelper.queryUserByName(myname);
            user = userDatabaseHelper.queryUserByName(myname);
        }


        //如果当前Fragment是NotificationsFragment的实例化对象
        boolean isF = currentFragment instanceof NotificationsFragment;
        if(isF){
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container,new NotificationsFragment());
            transaction.commit();
        }

    }

    //判断现在是哪个fragment
//    public Fragment getVisibleFragment(){
//        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
//        List<Fragment> fragments = fragmentManager.getFragments();
//        for(Fragment fragment : fragments){
//            if(fragment != null && fragment.isVisible())
//                return fragment;
//        }
//        return null;
//    }

    //搜索图标单击事件
    public void searchBlogs(View view) {
        EditText etSearch = findViewById(R.id.etSearch);
        String title = etSearch.getText().toString();
        list = myDataBaseOfEssay.queryOneEssay(title);
        adapter = new EssayAdapter(this,list);
        lv = (ListView) findViewById(R.id.blogs_lv);
        lv.setAdapter(adapter);
        Toast.makeText(this,"查询结果已显示",Toast.LENGTH_SHORT).show();
    }

    //home fragment初始化
    public void initHome(){

        //加载轮播图
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.b1);
        images.add(R.mipmap.b2);
        images.add(R.mipmap.b3);

        List <String> titles = new ArrayList<>();
        titles.add("庆余年盗播链4万条");
        titles.add("高以翔墓园设计效果图已落实");
        titles.add("刘鑫改名成微博大V_快资讯");

        Banner banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        //banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();


        //加载ListView
        MyDataBaseOfEssay myDataBaseOfEssay = new MyDataBaseOfEssay(this, "essay.db3", null, 1);
        myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);
        list = myDataBaseOfEssay.queryAllEssay();
        adapter = new EssayAdapter(this,list);
        lv = (ListView) findViewById(R.id.blogs_lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MainActivity.this, EssayActivity.class);
                Essay essay = list.get(i);
                //System.out.println(i);
                String title = essay.getTitle();
                String content = essay.getContext();
                //System.out.println(content);
                intent.putExtra("title", title);
                intent.putExtra("content",content);
                intent.putExtra("request","look");
                intent.putExtra("grade",grade+"");
                intent.putExtra("name",myname);
                startActivityForResult(intent, 1);
            }
        });

    }

    //Dashboard fragment初始化
    public void initDashboard(){
//        fabu = (Button)findViewById(R.id.yes);
//        fabu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    //发布按钮单击事件
    public void fabu(View view) {

        if(grade==2){
            Toast.makeText(MainActivity.this,"游客无法发布文章，请先注册登录~",Toast.LENGTH_LONG).show();
        }else{
            MyDataBaseOfEssay myDataBaseOfEssay = new MyDataBaseOfEssay(this, "essay.db3", null, 1);
            myDataBaseOfEssay.getDataBase(myDataBaseOfEssay);

            spinner = (Spinner)findViewById(R.id.type_spinner);
            String sortChoosed =  spinner.getSelectedItem().toString();//获取spinner选中内容
            title = (EditText) findViewById(R.id.title2);
            content = (EditText) findViewById(R.id.content2);
            //Log.i("zzw",title.getText().toString()+" "+content.getText().toString());
            if(title.getText().toString().trim().length()==0||content.getText().toString().length()==0){
                Toast.makeText(MainActivity.this,"标题或内容不许为空",Toast.LENGTH_LONG).show();
            }else {
                //myDataBaseOfEssay.saveEssay(title.getText().toString(),content.getText().toString());
                Log.i("zzw",myname);
                myDataBaseOfEssay.saveEssay(title.getText().toString(),content.getText().toString(),sortChoosed,myname);
                Toast.makeText(MainActivity.this,"博客已发布",Toast.LENGTH_LONG).show();
            }
        }
    }

    //notification fragment初始化
    public void initNotification(){
        Intent intent = getIntent();
        String myname = intent.getStringExtra("name");
    }

    //退出登录单击事件
    public void existApp(View view) {
        finish();
    }

    //收藏单击事件
    public void toLovePage(View view) {
        if(grade==2){
            Toast.makeText(MainActivity.this,"游客无法操作，请先注册登录~",Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(MainActivity.this,LoveActivity.class);
            startActivity(intent);
        }
    }

    //跳转修改个性签名页面事件
    public void updateWords(View view){
        if(grade==2){
            Toast.makeText(MainActivity.this,"游客无法操作，请先注册登录~",Toast.LENGTH_LONG).show();
        }else{
            Intent intent = new Intent(MainActivity.this,UpdatewordsActivity.class);
            intent.putExtra("name",myname);
            startActivity(intent);
            //finish();
        }

    }

    //查询words
    public  String  updateWordsHere(){
        if(grade!=2){
            userDatabaseHelper = new UserDatabaseHelper(this);
            user = new User();
            user = userDatabaseHelper.queryUserByName(myname);
            return user.getWords();
        }
        return null;
    }

    //跳转管理博客页面事件
    public void toManagerBlogs(View view) {
        if(grade==2){
            Toast.makeText(MainActivity.this,"游客无法操作，请先注册登录~",Toast.LENGTH_LONG).show();
        }else{
            Intent mintent = new Intent(this,ManagerblogsActivity.class);
            mintent.putExtra("name",myname);
            startActivity(mintent);
        }

    }
}
