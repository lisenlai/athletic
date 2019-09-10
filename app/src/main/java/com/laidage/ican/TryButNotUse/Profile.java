package com.laidage.ican.TryButNotUse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laidage.ican.Login;
import com.laidage.ican.R;

public class Profile extends AppCompatActivity {
//显示个人资料
    Button unLogin;
    SharedPreferences sprfMain;
    SharedPreferences.Editor editorMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unLogin = findViewById(R.id.unLogin);
        unLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprfMain= PreferenceManager.getDefaultSharedPreferences(Profile.this);
                editorMain=sprfMain.edit();
                editorMain.putBoolean("isLogin",false);
                editorMain.apply();
                Intent intent=new Intent(Profile.this,Login.class);
                startActivity(intent);
                Profile.this.finish();
            }
        });
    }
}
