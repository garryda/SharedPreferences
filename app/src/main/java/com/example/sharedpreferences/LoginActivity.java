package com.example.sharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;
/*
整一个思路比较简单，如下：
0）给按钮加入监听器，监听逻辑如下：
1）先判断账号，密码是否正确，不正确的话显示一个toast进行提醒，如果正确，进行第二步；
2）判断其是否勾选了记住密码，如果勾选了，往sharedPreference里面传入数据，如果没勾选，清空sharedPredereces里面
的数据，最后发送；
3）在设置监听之前，要有一个判断是否勾选的逻辑过程，目的是为了如果勾选了，当再次回到这个活动的时候，可以
加载销毁前保存的数据，也就是恢复原有状态
 */
public class LoginActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
    }
    public void bindView(){
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit=(EditText)findViewById(R.id.account);
        passwordEdit=(EditText)findViewById(R.id.password);
        checkBox=(CheckBox)findViewById(R.id.remember_pass);
        login=(Button)findViewById(R.id.login);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            checkBox.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accout=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                if(accout.equals("lpd")
                        &&password.equals("123456")){
                    if(checkBox.isChecked()){
                        editor=pref.edit();
                        editor.putBoolean("remember_password",true);
                        editor.putString("account",accountEdit.getText().toString());
                        editor.putString("password",passwordEdit.getText().toString());
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"account or password is wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
