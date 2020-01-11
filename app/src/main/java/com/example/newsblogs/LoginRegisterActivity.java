package com.example.newsblogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        findViewById(R.id.to_login_btn).setOnClickListener(this);
        findViewById(R.id.to_register_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.to_login_btn:
                intent = new Intent(LoginRegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.to_register_btn:
                intent = new Intent(LoginRegisterActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
