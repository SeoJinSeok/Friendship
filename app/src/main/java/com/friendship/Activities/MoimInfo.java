package com.friendship.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Fragments.imoim_album;
import com.friendship.Fragments.imoim_chat;
import com.friendship.Fragments.imoim_main;
import com.friendship.Fragments.imoim_mem;
import com.friendship.Objects.MoimObj;
import com.friendship.REST.ObjManager;
import com.friendship.REST.RESTClient;

import java.io.Serializable;

public class MoimInfo extends AppCompatActivity implements View.OnClickListener, Serializable {
    private int BB = Color.rgb(0, 144, 255);
    private MoimObj mObj = null;
    private TextView title1, title2, content, main, chat, album, mem, sel, msetting;
    private int mid;
    private View layout = null;
    private FragmentTransaction ft;
    private imoim_main ifrag;
    private imoim_chat cfrag;
    private imoim_album pfrag;
    private imoim_mem mfrag;
    private Fragment cur;
    private View mylayout, bg, top;
    private ObjManager oMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moim_info);
        layout = findViewById(R.id.layout_moim_info);
        layout.setVisibility(View.GONE);
        Intent parent = getIntent();
        mid = parent.getIntExtra("mid", 0);

        Button back = findViewById(R.id.minfo_back);
        back.setOnClickListener(this);
        bg = findViewById(R.id.minfo_backimg);
        title1 = findViewById(R.id.minfo_title);
        title2 = findViewById(R.id.minfo_title2);
        top = findViewById(R.id.minfo_top);

        // 내 모임 정보
        if (mObj.isJoin) {
            if (mObj.cap) {
                msetting = (Button) findViewById(R.id.minfo_msetting);
                msetting.setText("모임 설정");
                msetting.setOnClickListener(this);
            }
            mylayout = findViewById(R.id.minfo_mymoim);
            mylayout.setPadding(0, 300, 0, 0);
            View menu = findViewById(R.id.minfo_menu);
            menu.setBackgroundResource(R.drawable.minfo_menu);
            main = findViewById(R.id.minfo_main);
            main.setText(R.string.minfo_main);
            main.setTextColor(Color.BLACK);
            main.setOnClickListener(this);
            sel = main;
            chat = findViewById(R.id.minfo_chat);
            chat.setText(R.string.minfo_chat);
            chat.setTextColor(Color.BLUE);
            album = findViewById(R.id.minfo_album);
            album.setText(R.string.minfo_album);
            album.setTextColor(Color.BLUE);
            mem = findViewById(R.id.minfo_member);
            mem.setText(R.string.minfo_member);
            mem.setTextColor(Color.BLUE);
            main.setOnClickListener(this);
            chat.setOnClickListener(this);
            album.setOnClickListener(this);
            mem.setOnClickListener(this);
            ifrag = new imoim_main();
            cfrag = new imoim_chat();
            pfrag = new imoim_album();
            mfrag = new imoim_mem();
            cur = ifrag;
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.minfo_frag, cur);
            ft.commit();
        }
        // 다른 모임 정보
        else {
            layout.setBackgroundResource(R.drawable.moiminfo);
            Button join = findViewById(R.id.minfo_join);
            join.setOnClickListener(this);
            content = findViewById(R.id.minfo_content);
        }

        if (mid == 0) {
            AlertDialog.Builder alarm = new AlertDialog.Builder(this);
            DialogInterface.OnCancelListener cancel = new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            };
            alarm.setOnCancelListener(cancel);
            alarm.setMessage("오류가 발생했습니다.");
            alarm.show();
        }
        startTask(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moim_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.moim_alarm:
            case R.id.moim_out:
                AlertDialog.Builder confirm = new AlertDialog.Builder(MoimInfo.this);
                confirm.setTitle("하선");
                confirm.setMessage("하선 하시겠습니까?");
                confirm.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ObjManager o = new ObjManager("moim.jsp?jq=0");
                        o.CheckorLogout();
                    }
                });
                confirm.setNegativeButton("아니오", null);
                confirm.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.minfo_back:
                finish();
                return;
            case R.id.minfo_main:
                top.setBackgroundColor(Color.TRANSPARENT);
                msetting.setVisibility(View.VISIBLE);
                sel.setTextColor(Color.BLUE);
                main.setTextColor(Color.BLACK);
                bg.setBackground(new BitmapDrawable(getResources(), mObj.getBack()));
                mylayout.setPadding(0, 300, 0, 0);
                cur = ifrag;
                break;
            case R.id.minfo_chat:
                top.setBackgroundColor(BB);
                msetting.setVisibility(View.GONE);
                sel.setTextColor(Color.BLUE);
                chat.setTextColor(Color.BLACK);
                mylayout.setPadding(0, 0, 0, 0);
                bg.setBackground(null);
                sel = chat;
                cur = cfrag;
                break;
            case R.id.minfo_album:
                top.setBackgroundColor(BB);
                msetting.setVisibility(View.GONE);
                bg.setBackground(null);
                sel.setTextColor(Color.BLUE);
                album.setTextColor(Color.BLACK);
                mylayout.setPadding(0, 0, 0, 0);
                sel = album;
                cur = pfrag;
                break;
            case R.id.minfo_member:
                top.setBackgroundColor(BB);
                msetting.setVisibility(View.GONE);
                bg.setBackground(new BitmapDrawable(getResources(), mObj.getBack()));
                sel.setTextColor(Color.BLUE);
                mem.setTextColor(Color.BLACK);
                mylayout.setPadding(0, 300, 0, 0);
                sel = mem;
                cur = mfrag;
                break;
            case R.id.minfo_msetting:
                Intent moim = new Intent(this, Moim.class);
                moim.putExtra("new", false);
                startActivity(moim);
                break;
            case R.id.minfo_join:
                startTask(1);
                return ;
            default:
                break;
        }
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.minfo_frag, cur);
        ft.commit();
    }

    @SuppressWarnings("unused")
    public void onResult(Object res) {
        // 모임 객체 불러오기
        if (mObj == null) {
            mObj = (MoimObj) res;
            oMgr = new ObjManager("");
            if (oMgr.CheckorLogout()) {
                bg.setBackground(new BitmapDrawable(getResources(), mObj.getBack()));
                title2.setText(mObj.getTitle());
                if (mObj.isJoin) {
                    title1.setText(mObj.getTitle());
                    content.setText(mObj.getContent());
                } else {
                    if (mObj.getBoard() != null) ifrag.setObj(mObj.getBoard());
                    for (MoimObj.Member m : mObj.getMembers()){

                        //if (RESTClient.getInstance("").getID().equals(m.getMem_id()))
                    }
                    mfrag.setobj(mObj.getMembers(), mid);
                }
            }
            layout.setVisibility(View.VISIBLE);
        }
    }

    private void startTask(int id) {
        switch (id) {
            case 0:
                oMgr = new ObjManager("moim.jsp?mid=" + mid);
                oMgr.GetMoim(null, this);
                break;
            case 1:
                oMgr = new ObjManager("moim.jsp?mid="+ mid + "&jq=" + 1);
                if (oMgr.CheckorLogout()){
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setMessage("가입이 완료되었습니다.");
                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            Intent intent = new Intent(MoimInfo.this, MoimInfo.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alert.show();
                }
                break;
        }
    }
}