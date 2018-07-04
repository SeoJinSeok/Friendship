package com.friendship.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Objects.MainObj;
import com.friendship.Objects.MoimObj;
import com.friendship.Objects.ProfileObj;
import com.friendship.Objects.RoundImage;
import com.friendship.REST.ObjManager;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private boolean profload = false;
    private View layout;
    private ImageButton main, profile, moim, chat, category, cache;
    private ImageView navIcon;
    private TextView navNick;
    private com.friendship.Fragments.mainFragment mfrag;
    private com.friendship.Fragments.profileFragment pfrag;
    private com.friendship.Fragments.moimFragment ofrag;
    private com.friendship.Fragments.chatFragment cfrag;
    private Fragment Frag;
    private FragmentTransaction FragT;
    private ObjManager oMgr;
    private ProfileObj P;
    private MoimObj [] M;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        main = null;
        profile = null;
        moim = null;
        chat = null;
        category = null;
        cache = null;
        navIcon = null;
        navNick = null;
        mfrag = null;
        pfrag = null;
        ofrag = null;
        cfrag = null;
        Frag = null;
        FragT = null;
        oMgr = null;
        //recycleView(main);
        //recycleView(micon);
        //recycleView(cmain);
        //recycleView(bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View load = findViewById(R.id.main_load);
        load.setVisibility(View.VISIBLE);
        layout = findViewById(R.id.layout_main);
        layout.setVisibility(View.INVISIBLE);
        View cmain, bottom, micon;
        // 메인 Layout
        main = findViewById(R.id.main_title);
        micon = findViewById(R.id.main_search);
        cmain = findViewById(R.id.main_category);
        cache = main;
        profile = findViewById(R.id.main_profbtn);
        moim = findViewById(R.id.main_moimbtn);
        chat = findViewById(R.id.main_chatbtn);
        category = findViewById(R.id.main_categorybtn);
        bottom = findViewById(R.id.main_bottombtn);

        main.setBackgroundResource(R.drawable.main_title);
        micon.setBackgroundResource(R.drawable.main_icon);
        cmain.setBackgroundResource(R.drawable.main_menu);
        bottom.setBackgroundResource(R.drawable.bottom_menu);

        main.setOnClickListener(this);
        profile.setOnClickListener(this);
        moim.setOnClickListener(this);
        chat.setOnClickListener(this);
        category.setOnClickListener(this);
        bottom.setOnClickListener(this);

        // 슬라이딩 메뉴 레이아웃
        DrawerLayout drawer = findViewById(R.id.main_place);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headview = getLayoutInflater().inflate(R.layout.nav_header_menu_setting, navigationView, false).findViewById(R.id.layout_navheader);
        navigationView.addHeaderView(headview);
        navigationView.setBackgroundResource(R.drawable.navmenu_bg);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationIcon(R.drawable.main_side);
        headview.setBackgroundResource(R.drawable.navmenu_headerbg);
        navIcon = headview.findViewById(R.id.ms_icon);
        navNick = headview.findViewById(R.id.ms_nick);
        mfrag = new com.friendship.Fragments.mainFragment();
        pfrag = new com.friendship.Fragments.profileFragment();
        ofrag = new com.friendship.Fragments.moimFragment();
        cfrag = new com.friendship.Fragments.chatFragment();
        Frag = mfrag;
        FragT = getSupportFragmentManager().beginTransaction();
        FragT.replace(R.id.frag_place, mfrag);
        FragT.commit();
        LoadObj();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.main_title:
                cache.setImageBitmap(null);
                cache = main;
                Frag = mfrag;
                break;
            case R.id.main_profbtn:
                cache.setImageBitmap(null);
                profile.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.choice));
                cache = profile;
                Frag = pfrag;
                break;
            case R.id.main_moimbtn:
                cache.setImageBitmap(null);
                moim.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.choice));
                cache = moim;
                Frag = ofrag;
                break;
            case R.id.main_chatbtn:
                cache.setImageBitmap(null);
                chat.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.choice));
                cache = chat;
                Frag = cfrag;
                break;
            case R.id.main_categorybtn:
                cache.setImageBitmap(null);
                category.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.choice));
                cache = category;
                break;
            case R.id.main_search:
                break;
            case R.id.main_bottombtn:
                if (cache == moim) {
                    Intent newMoim = new Intent(this, Moim.class);
                    newMoim.putExtra("new", true);
                    startActivity(newMoim);
                    finish();
                }
                break;
            default:
                break;
        }
        FragT = getSupportFragmentManager().beginTransaction();
        FragT.replace(R.id.frag_place, Frag);
        FragT.commit();
        Class c = Frag.getClass();
        Method mlist [] = c.getDeclaredMethods();
        for (Method m : mlist) {
            if (m.getName().equals("setobj")){
                try {
                    if (Frag == pfrag) m.invoke(Frag, P, M);
                    else if (Frag == ofrag) m.invoke(Frag, M);
                } catch(Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ms_main) {
            FragT = getSupportFragmentManager().beginTransaction();
            FragT.replace(R.id.frag_place, mfrag);
            FragT.commit();
        } else if (id == R.id.ms_alarm) {

        } else if (id == R.id.ms_invorshare) {

        } else if (id == R.id.ms_pay) {

        } else if (id == R.id.ms_setting) {
            Intent prof = new Intent(this, ProfileSettings.class);
            prof.putExtra("isNew", false);
            startActivity(prof);
        } else if (id == R.id.ms_ask) {
            Intent contact = new Intent(this, Contact.class);
            startActivity(contact);
        } else if (id == R.id.ms_logout) {
            oMgr = new ObjManager("logout.jsp");
            oMgr.CheckorLogout();
            Intent Login = new Intent(this, LoginActivity.class);
            startActivity(Login);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.main_place);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressWarnings("unused")
    public void onResult(Object obj) {
        View load = findViewById(R.id.main_load);
        load.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        if (obj != null) {
            if (!profload) {
                Object[] objs = (Object[]) obj;
                P = (ProfileObj) objs[0];
                M = (MoimObj[]) objs[1];
                RoundImage rImg;
                if (P != null) {
                    rImg = new RoundImage(Bitmap.createScaledBitmap(P.getIcon(), 170, 170, false));
                    navIcon.setBackground(rImg);
                    navNick.setText(P.getNick());
                    oMgr = new ObjManager("main.jsp");
                    oMgr.GetMain(null, this);
                    profload = true;
                }
            } else mfrag.setobj((MainObj) obj);
        }
    }

    private void LoadObj() {
        oMgr = new ObjManager("profile.jsp");
        oMgr.GetProf(null, this);
    }
}