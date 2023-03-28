package com.example.dreamland.ui.dreams;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dreamland.R;
import com.example.dreamland.ui.util.SendmailUtil;

public class Dreams extends AppCompatActivity  implements View.OnClickListener{
    private Button tsbt;
    private EditText mail;
    private EditText context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dreams);
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
        new Thread(){
            @Override
            public void run(){
                super.run();
                try {
                    new SendmailUtil(mail.getText().toString()).sendTextEmail(context.getText().toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();
    }

}