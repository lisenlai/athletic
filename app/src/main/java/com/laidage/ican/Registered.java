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

public class Registered extends AppCompatActivity {

     EditText editText1;
     EditText editText2;
     Button registered1;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getApplicationContext(), "719b4ed0c516e3c559866bc6119a82fd");
        setContentView(R.layout.activity_registered);
        editText1 = findViewById(R.id.account);
        editText2 = findViewById(R.id.password);
        registered1 = findViewById(R.id.registered1);
        registered1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText1.getText().toString();
                password = editText2.getText().toString();
                BmobUser user = new BmobUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUp(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
                            //自动登录
                            sprfMain= PreferenceManager.getDefaultSharedPreferences(Registered.this);
                            editorMain=sprfMain.edit();
                            editorMain.putBoolean("isLogin",true);
                            editorMain.apply();
                            Toast.makeText(Registered.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Registered.this,MainScreen.class);
                            startActivity(intent);
                            Registered.this.finish();
                        }else{
                            if(username.length() < 11)
                            {
                                Toast.makeText(Registered.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(Registered.this, "该手机号码已被注册", Toast.LENGTH_SHORT).show();
                            }
                        }}
                });
            }
        });
    }
}
