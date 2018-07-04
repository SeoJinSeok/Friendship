package com.friendship.Fragments;

import android.content.Context;
import android.content.Intent;
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

public class moimFragment extends Fragment implements View.OnClickListener {
    private MoimObj[] M = null;

    public void setobj(MoimObj[] Moimobj) {
        M = Moimobj;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_moim, container, false);
        View[] imgs = new View[3];
        imgs[0] = view.findViewById(R.id.moimf_joinimg);
        imgs[0].setBackgroundResource(R.drawable.profile_moimlist);
        imgs[1] = view.findViewById(R.id.moimf_regimg);
        imgs[1].setBackgroundResource(R.drawable.profile_moimlist);
        imgs[2] = view.findViewById(R.id.moimf_themeimg);
        imgs[2].setBackgroundResource(R.drawable.profile_moimlist);

        LinearLayout [] lists = new LinearLayout[3];
        lists[0] = view.findViewById(R.id.moimf_joinlist);
        lists[1] = view.findViewById(R.id.moimf_reglist);
        lists[2] = view.findViewById(R.id.moimf_themelist);

        if (M != null) {
            Context con = getContext();
            int num = M.length;

            LinearLayout[] pmoim = new LinearLayout[3];
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

                lists[0].addView(pmoim[i], i);
                pmoim[i].setId(M[i].getId());
                pmoim[i].setOnClickListener(this);
            }
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent moimInfo = new Intent(getActivity(), MoimInfo.class);
        moimInfo.putExtra("mid", view.getId());
        startActivity(moimInfo);
    }
}