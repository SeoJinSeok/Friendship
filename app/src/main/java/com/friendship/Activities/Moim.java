package com.friendship.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Fragments.makemoim1;
import com.friendship.Fragments.makemoim2;
import com.friendship.Objects.MoimObj;
import com.friendship.REST.ObjManager;

public class Moim extends AppCompatActivity implements View.OnClickListener {
    private Fragment Frag;
    private ImageButton getback;
    private FragmentTransaction FragT;
    private MoimObj mObj;
    private makemoim1 m1;
    private makemoim2 m2;
    private View layout, next, fin, cancel;
    private AlertDialog.Builder falarm;
    private boolean isNew = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Frag = null;
        getback = null;
        FragT = null;
        mObj = null;
        m1 = null;
        m2 = null;
        next = null;
        fin = null;
        cancel = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNew = getIntent().getBooleanExtra("new", true);
        setContentView(R.layout.activity_moim);
        falarm = new AlertDialog.Builder(this);
        View top = findViewById(R.id.makemoim_top);
        top.setBackgroundResource(R.drawable.addmoim_top);
        TextView toptitle = findViewById(R.id.makemoim_title);

        toptitle.setTextColor(Color.WHITE);
        layout = findViewById(R.id.layout_makemoim);
        layout.setBackgroundResource(R.drawable.addmoim1_bg);

        next = findViewById(R.id.makemoim_next);
        next.setBackgroundResource(R.drawable.addmoim1_next);
        getback = findViewById(R.id.makemoim_back);
        getback.setBackgroundResource(R.drawable.back);
        fin = findViewById(R.id.makemoim_fin);
        fin.setBackground(null);
        cancel = findViewById(R.id.makemoim_cancel);
        cancel.setBackground(null);

        next.setOnClickListener(this);
        getback.setOnClickListener(this);

        m1 = new makemoim1();
        m2 = new makemoim2();
        if (isNew) toptitle.setText("모임만들기");
        else {
            toptitle.setText("모임수정");
            startTask(1);
        }
        Frag = m1;
        FragT = getSupportFragmentManager().beginTransaction();
        FragT.replace(R.id.makemoim_frag, Frag);
        FragT.commit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.makemoim_back:
                if (Frag == m1) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else if (Frag == m2) {
                    layout.setBackgroundResource(R.drawable.addmoim1_bg);
                    next.setOnClickListener(this);
                    cancel.setBackground(null);
                    fin.setBackground(null);
                    cancel.setOnClickListener(null);
                    fin.setOnClickListener(null);
                    Frag = m1;
                    FragT = getSupportFragmentManager().beginTransaction();
                    FragT.replace(R.id.makemoim_frag, Frag);
                    FragT.commit();
                }
                break;
            case R.id.makemoim_next:
                // 다음 레이아웃 세팅
                layout.setBackgroundResource(R.drawable.addmoim2_bg);
                next.setOnClickListener(null);
                next.setBackground(null);
                fin.setBackgroundResource(R.drawable.addmoim2_fin);
                fin.setOnClickListener(this);
                cancel.setBackgroundResource(R.drawable.addmoim2_cancel);
                cancel.setOnClickListener(this);

                if (!setObj1(m1.setObj())) break;
                Frag = m2;
                FragT = getSupportFragmentManager().beginTransaction();
                FragT.replace(R.id.makemoim_frag, Frag);
                FragT.commit();
                break;
            case R.id.makemoim_fin:
                onFinish(m2.setObj());
                break;
            case R.id.makemoim_cancel:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private void onFinish(Object[] objs) {
        if (objs[0] == null)   {
            falarm.setMessage("이미지를 설정해 주세요.");
            falarm.show();
        }else {
            mObj.setBack((Bitmap) objs[0]);
            mObj.setTitle((String) objs[1]);
            mObj.setComm((String) objs[2]);
            mObj.setContent((String) objs[3]);
            startTask(0);
        }
    }

    private boolean setObj1(Object[] objs) {
        mObj = new MoimObj();
        mObj.setCate((String) objs[0]);
        mObj.setReg((String) objs[1]);
        mObj.setAgel(Integer.parseInt((String) objs[2]));
        mObj.setAgeh(Integer.parseInt((String) objs[3]));
        mObj.setLimit(Integer.parseInt((String) objs[4]));
        try {
            return true;
        } catch (Exception e) {
            falarm.setMessage("빈 칸을 입력해주세요.");
            falarm.show();
            return false;
        }
    }

    public void onResult(Object obj) {
        if (obj instanceof MoimObj){
            mObj = (MoimObj) obj;
            m1.getObj(mObj);
            m2.getObj(mObj);
            return;
        }

        if (obj != null) {
            falarm.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            });
            if (isNew)  falarm.setMessage("모임이 생성되었습니다.");
            else falarm.setMessage("수정이 완료되었습니다.");
            falarm.show();
        } else {
            falarm.setMessage("빈 곳을 채워주세요.");
            falarm.show();
        }
    }

    private void startTask(int id) {
        ObjManager oMgr;
        switch (id){
            case 0:
                oMgr = (isNew) ? new ObjManager("moim.jsp") : new ObjManager("moim.jsp?mid=" + mObj.getId());
                oMgr.SetMoim(mObj, this);
            case 1:
                oMgr = new ObjManager("moim.jsp?mid=" + mObj.getId());
                oMgr.GetMoim(null, this);
        }

    }
}