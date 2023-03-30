package com.example.dreamland.ui.dreams;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.example.dreamland.R;
import com.example.dreamland.ui.dashboard.DashboardActivity;
import com.example.dreamland.ui.util.SendmailUtil;

import static com.blankj.utilcode.util.RegexUtils.isEmail;

public class DreamsActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button tsbt;
    private EditText mail;
    private EditText context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreams);


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