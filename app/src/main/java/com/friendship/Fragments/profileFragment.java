package com.friendship.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Activities.MoimInfo;
import com.friendship.Objects.MoimObj;
import com.friendship.Objects.ProfileObj;
import com.friendship.Objects.RoundImage;
import com.friendship.REST.ObjManager;

public class profileFragment extends Fragment implements View.OnClickListener {
    private TextView nick, favs[];
    private View icon;
    private LinearLayout list;
    private ProfileObj P;
    private MoimObj [] M;

    public void setobj(ProfileObj P, MoimObj[] M) {
        this.P = P;
        this.M = M;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nick = null;
        favs = null;
        icon = null;
        list = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ObjManager o = new ObjManager("profile.jsp?id=" + getArguments().getString("id"));
                    Object [] objs = o.GetProf(null, new Activity());
                    setobj((ProfileObj)objs[0], (MoimObj[])objs[1]);
                }
            }).run();
        }
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        View back = view.findViewById(R.id.p_back);
        back.setBackgroundResource(R.drawable.prof_back);
        icon = view.findViewById(R.id.p_icon);
        nick = view.findViewById(R.id.p_nick);
        favs = new TextView[3];
        favs[0] = view.findViewById(R.id.prof_favo1);
        favs[1] = view.findViewById(R.id.prof_favo2);
        favs[2] = view.findViewById(R.id.prof_favo3);
        View join = view.findViewById(R.id.prof_joined);
        join.setBackgroundResource(R.drawable.profile_moimlist);
        list = view.findViewById(R.id.prof_moimlist);
        Context con = getContext();
        if (P != null) {
            nick.setText(P.getNick());
            String[] strs = P.getFavos();
            int SKYBLUE = Color.rgb(0, 144, 255);
            for (int i = 0; i < strs.length; i++) {
                favs[i].setText(strs[i]);
                favs[i].setTextColor(Color.WHITE);
                favs[i].setBackgroundColor(SKYBLUE);
            }
            Bitmap tmp = Bitmap.createScaledBitmap(P.getIcon(), 200, 200, false);
            RoundImage rImg = new RoundImage(tmp);
            icon.setBackground(rImg);
        }

        if (M != null) {
            int num = M.length;

            LinearLayout[] pmoim = new LinearLayout[num];
            ImageView[] img = new ImageView[num];
            TextView[] title = new TextView[num], intro = new TextView[num];

            for (int i = 0; i < num; i++) {
                // 전체 Layout 설정
                pmoim[i] = new LinearLayout(con);
                pmoim[i].setLayoutParams(new LinearLayout.LayoutParams(160, ViewGroup.LayoutParams.MATCH_PARENT));
                pmoim[i].setOrientation(LinearLayout.VERTICAL);

                // Image 설정
                img[i] = new ImageView(con);
                img[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 120));
                img[i].setImageBitmap(M[i].getBack());

                // 제목, 한줄 소개 설정
                title[i] = new TextView(con);
                title[i].setTextSize(16);
                title[i].setTextColor(Color.BLUE);
                title[i].setText(M[i].getTitle());

                intro[i] = new TextView(con);
                intro[i].setTextSize(8);
                intro[i].setText(M[i].getComm());

                pmoim[i].addView(img[i], 0);
                pmoim[i].addView(title[i], 1);
                pmoim[i].addView(intro[i], 2);
                pmoim[i].setId(M[i].getId());

                list.addView(pmoim[i], i);
                pmoim[i].setOnClickListener(this);
            }
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent info = new Intent(getActivity(), MoimInfo.class);
        info.putExtra("mid", view.getId());
        info.putExtra("mymoim", true);
        startActivity(info);
    }
}