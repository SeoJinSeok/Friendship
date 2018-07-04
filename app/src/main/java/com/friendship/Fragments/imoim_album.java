package com.friendship.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.friendship.R;
import com.friendship.Objects.MoimObj;


public class imoim_album extends Fragment {
    MoimObj mObj;

    public imoim_album() {
    }

    public void setObj(MoimObj mObj) {
        this.mObj = mObj;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_imoim_album, container, false);
        return view;
    }
}
