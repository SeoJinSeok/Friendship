package com.friendship.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.friendship.R;
import com.friendship.REST.ObjManager;

public class Contact extends AppCompatActivity implements View.OnClickListener {
    private EditText title, content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = findViewById(R.id.contact_title);
        content = findViewById(R.id.contact_content);
        Button back = findViewById(R.id.contact_back);
        back.setOnClickListener(this);
        Button submit = findViewById(R.id.contact_submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contact_back:
                Intent Main = new Intent(this, MainActivity.class);
                startActivity(Main);
                finish();
                break;
            case R.id.contact_submit:
                ObjManager oMgr = new ObjManager("contact");
                oMgr.ask(title.getText().toString(), content.getText().toString(), this);
                break;
        }
    }

    public void onResult(boolean ok) {
        final Intent Main = new Intent(this, MainActivity.class);
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        if (ok) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    startActivity(Main);
                    finish();
                }
            });
            alert.setMessage("접수 완료! 답변은 가입하신 이메일로 전송됩니다.");
            alert.show();
        } else {
            alert.setMessage("오류가 발생했습니다.");
            alert.show();
        }
    }
}
