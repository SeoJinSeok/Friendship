package com.friendship.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Objects.MoimObj;


public class chatFragment extends Fragment implements View.OnClickListener {
    private LinearLayout my, other;

    public void setObj(MoimObj[] M, MoimObj[] O) {
        Context con = getContext();

        LinearLayout[] pmoim = new LinearLayout[M.length];
        ImageView[] img = new ImageView[M.length];
        TextView[] title = new TextView[M.length];
        TextView[] intro = new TextView[M.length];

        for (int i = 0; i < M.length; i++) {
            // 전체 Layout 설정
            pmoim[i] = new LinearLayout(con);
            pmoim[i].setLayoutParams(new LinearLayout.LayoutParams(220, ViewGroup.LayoutParams.MATCH_PARENT));
            pmoim[i].setOrientation(LinearLayout.VERTICAL);

            // Image 설정
            img[i] = new ImageView(con);
            img[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160));
            img[i].setImageBitmap(M[i].getBack());

            // 제목, 한줄 소개 설정
            title[i] = new TextView(con);
            title[i].setTextSize(20);
            title[i].setTextColor(Color.BLUE);
            title[i].setText(M[i].getTitle());

            intro[i] = new TextView(con);
            intro[i].setTextSize(12);
            intro[i].setText(M[i].getComm());

            pmoim[i].addView(img[i], 0);
            pmoim[i].addView(title[i], 1);
            pmoim[i].addView(intro[i], 2);

            my.addView(pmoim[i], i);
        }

        for (int i = 0; i < O.length; i++) {
            // 전체 Layout 설정
            pmoim[i] = new LinearLayout(con);
            pmoim[i].setLayoutParams(new LinearLayout.LayoutParams(220, ViewGroup.LayoutParams.MATCH_PARENT));
            pmoim[i].setOrientation(LinearLayout.VERTICAL);

            // Image 설정
            img[i] = new ImageView(con);
            img[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160));
            img[i].setImageBitmap(O[i].getBack());

            // 제목, 한줄 소개 설정
            title[i] = new TextView(con);
            title[i].setTextSize(20);
            title[i].setTextColor(Color.BLUE);
            title[i].setText(O[i].getTitle());

            intro[i] = new TextView(con);
            intro[i].setTextSize(12);
            intro[i].setText(O[i].getComm());

            pmoim[i].addView(img[i], 0);
            pmoim[i].addView(title[i], 1);
            pmoim[i].addView(intro[i], 2);

            other.addView(pmoim[i], i);
        }
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        View mimg = view.findViewById(R.id.chatf_mymoimimg), oimg = view.findViewById(R.id.chatf_othermoimimg);
        mimg.setBackgroundResource(R.drawable.profile_moimlist);
        oimg.setBackgroundResource(R.drawable.profile_moimlist);
        my = view.findViewById(R.id.chatf_mymoim);
        other = view.findViewById(R.id.chatf_othermoim);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }
}
