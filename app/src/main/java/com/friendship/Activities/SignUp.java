package com.friendship.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.friendship.Objects.UserObj;
import com.friendship.R;
import com.friendship.REST.ObjManager;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText email, password, passconfirm;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        View background = findViewById(R.id.activity_sign_up);
        background.setBackgroundResource(R.drawable.signup_form);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        passconfirm = findViewById(R.id.signup_passwordconfirm);
        send = findViewById(R.id.signup_send);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (password.getText().toString().length() < 6) {
            passconfirm.setError("비밀번호는 6자 이상이어야 합니다.");
            return;
        }

        if (password.getText().toString().equals(passconfirm.getText().toString())) {
            UserObj uobj = new UserObj(email.getText().toString(), password.getText().toString());
            startTask(uobj);
        } else passconfirm.setError("비밀번호가 틀립니다. 다시 입력하세요.");
    }

    public void onResult(boolean ok) {
        if (ok) {
            final Intent prof = new Intent(this, ProfileSettings.class);
            AlertDialog.Builder alarm = new AlertDialog.Builder(this);
            alarm.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    prof.putExtra("isNew", true);
                    startActivity(prof);
                    finish();
                }
            });
            alarm.setMessage("등록하신 이메일로 인증메일을 발송했습니다. 인증하시면 앱 이용이 가능합니다. 먼저 프로필을 설정하세요.");
            alarm.show();
        } else send.setError("오류가 발생했습니다.");
    }

    private void startTask(UserObj U) {
        ObjManager omgr = new ObjManager("signup.jsp");
        omgr.SignUp(U, this);
    }
}
