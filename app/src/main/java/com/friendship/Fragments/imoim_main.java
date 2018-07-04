package com.friendship.Fragments;

import android.content.Context;
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
import com.friendship.Objects.BoardObj;

public class imoim_main extends Fragment {
    private static BoardObj[] B;
    private LinearLayout list;
    private Context con;

    public imoim_main() {
        super();
    }

    @Override
    public void onResume() {
        super.onResume();
        setObj(B);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        con = null;
    }

    public void setObj(BoardObj[] bobj) {
        B = bobj;
        int j = 0;
        ViewGroup.LayoutParams imgParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30);
        ViewGroup.LayoutParams txtParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        if (B == null) {
            ImageView img = new ImageView(con);
            img.setBackgroundResource(R.drawable.moim_boardbar);
            TextView notice = new TextView(con);
            notice.setTextSize(18);
            notice.setText("공지가 없습니다.");
            list.addView(img, imgParams);
            list.addView(notice, txtParams);
            img = new ImageView(con);
            img.setBackgroundResource(R.drawable.moim_boardbar);
            list.addView(img, imgParams);
        } else {
            for (BoardObj b : B) {
                ImageView img = new ImageView(con);
                img.setBackgroundResource(R.drawable.moim_boardbar);
                TextView notice = new TextView(con);
                notice.setText(b.getB_Content());
                list.addView(img, j++);
                list.addView(notice, j++);
                list.addView(img, j++);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_imoim_main, container, false);
        View img = view.findViewById(R.id.moimmain_noticeimg);
        img.setBackgroundResource(R.drawable.moim_notice);
        list = view.findViewById(R.id.moimmain_notice);
        return view;
    }
}