package com.friendship.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.friendship.R;
import com.friendship.Objects.ListViewAdapter;
import com.friendship.Objects.MoimObj;
import com.friendship.REST.ObjManager;

public class imoim_mem extends Fragment implements View.OnClickListener {
    private MoimObj.Member[] M;
    private int[] ids;

    public void setobj(MoimObj.Member[] M) {
        this.M = M;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_imoim_mem, container, false);
        ListView list = view.findViewById(R.id.member_list);
        ListViewAdapter adapter = new ListViewAdapter();
        list.setAdapter(adapter);
        // 멤버 아이디를 길이만큼 설정
        ids = new int[M.length];

        // 멤버 View 불러오고 띄우기
        ObjManager oMgr = new ObjManager("");
        int i = 0;
        ids[i] = M[0].getMem_id().hashCode();
        adapter.addItem(oMgr.BitmapToByte(M[0].getMem_icon()), M[0].getMem_nick(), M[0].getMem_comm(), 1);
        View item = (View) adapter.getItem(i);
        item.setId(ids[i]);
        item.setOnClickListener(this);
        i++;
        for (MoimObj.Member m : M) {
            ids[i] = m.getMem_id().hashCode();
            adapter.addItem(oMgr.BitmapToByte(m.getMem_icon()), m.getMem_nick(), m.getMem_comm(), 1);
            item = (View) adapter.getItem(i);
            item.setId(ids[i]);
            item.setOnClickListener(this);
            i++;
        }
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onClick(View view) {
        for (int i : ids) {
            if (view.getId() == i) {
                Intent prof = new Intent(getContext(), profileFragment.class);
                ObjManager oMgr = new ObjManager("");
                //oMgr.GetProf(null, profileFragment.class);
                //prof.putExtra("")
                startActivity(prof);
            }
        }
    }
}