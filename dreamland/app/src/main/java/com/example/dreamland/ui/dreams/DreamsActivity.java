package com.example.dreamland.ui.dreams;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.example.dreamland.R;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.util.SendmailUtil;
import com.google.android.material.appbar.MaterialToolbar;

import static com.blankj.utilcode.util.RegexUtils.isEmail;

public class DreamsActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button tsbt;
    private EditText mail;
    private EditText context;

//    private long exitTime = 0;
//    //两次返回，返回到home界面（System.exit决定是否退出当前界面，重新加载程序）
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getAction() == android.view.KeyEvent.ACTION_DOWN){
//
//            if((System.currentTimeMillis()-exitTime) > 2000){
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                Intent home = new Intent(Intent.ACTION_MAIN);
//                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                home.addCategory(Intent.CATEGORY_HOME);
//                startActivity(home);
//                //退出系统，不保存之前页面
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreams);

        MaterialToolbar topAppBar=findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tsbt=findViewById(R.id.send_dreams);
        tsbt.setOnClickListener(this);
        mail=findViewById(R.id.mail);
        context=findViewById(R.id.context);

    }
    @Override
    public void onClick(View view){
        System.out.println("chengong ");
        System.out.println(mail.getText().toString());
        System.out.println(context.getText().toString());
                try {
                    if(context.getText().toString().length()==0||context.getText().toString()==""){
                        SweetAlertDialog pDialog = new SweetAlertDialog(DreamsActivity.this,SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("发送失败")
                                .setContentText("内容不得为空");
                        pDialog.show();
                    }
                    else if(!isEmail(mail.getText().toString())){
                        SweetAlertDialog pDialog = new SweetAlertDialog(DreamsActivity.this,SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("发送失败")
                                .setContentText("邮箱格式错误");
                        pDialog.show();
                    }
                    else{
                        new SendmailUtil(mail.getText().toString()).sendTextEmail(context.getText().toString());
                        SweetAlertDialog pDialog = new SweetAlertDialog(DreamsActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("发送成功");
                        pDialog.show();
                    }
                } catch (Exception e) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(DreamsActivity.this,SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("发送失败")
                           .setContentText("请联系管理员");
                    pDialog.show();

                }
    }

}