package com.laidage.ican;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Login extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    Button login1;
    Button registered;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在加载布局文件前判断是否登陆过
        sprfMain= PreferenceManager.getDefaultSharedPreferences(this);
        editorMain=sprfMain.edit();
        //.getBoolean("isLogin",false)；当找不到"isLogin"所对应的键值是默认返回false
        if(sprfMain.getBoolean("isLogin",false)){
            //实现自动登录
            Intent intent=new Intent(Login.this,MainScreen.class);
            startActivity(intent);
            Login.this.finish();
        }
        Bmob.initialize(getApplicationContext(),"719b4ed0c516e3c559866bc6119a82fd");
        setContentView(R.layout.activity_login);
        editText1=findViewById(R.id.account);
        editText2=findViewById(R.id.password);
        login1=findViewById(R.id.login);
        registered = findViewById(R.id.registered);
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText1.getText().toString();
                String password = editText2.getText().toString();
                BmobUser user = new BmobUser();
                user.setUsername(username);
                user.setPassword(password);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            Intent intent=new Intent(Login.this,MainScreen.class);
                            editorMain.putBoolean("isLogin",true);
                            editorMain.apply();
                            Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            Login.this.finish();
                        }else{
                            Toast.makeText(Login.this, "手机号码或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Registered.class);
                startActivity(intent);
                Login.this.finish();
            }
        });
    }
}
