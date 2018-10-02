package com.friendship.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;

public class imoim_mem extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    private ArrayList<MoimObj.Member> M;
    private int mid;
    private int[] ids;
    private int lev;

    public void setobj(ArrayList<MoimObj.Member> M, int mid) {
        this.M = M; this.mid = mid;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_imoim_mem, container, false);
        ListView list = view.findViewById(R.id.member_list);
        ListViewAdapter adapter = new ListViewAdapter();
        list.setAdapter(adapter);
        // 멤버 아이디를 길이만큼 설정
        ids = new int[M.size()];

        // 멤버 View 불러오고 띄우기
        ObjManager oMgr = new ObjManager("");
        for(int i=0; i<ids.length; i++) {
            MoimObj.Member m = M.get(i);
            ids[i] = Integer.valueOf(m.getMem_id());
            adapter.addItem(oMgr.BitmapToByte(m.getMem_icon()), m.getMem_nick(), m.getMem_comm(), 1);
            View item = (View) adapter.getItem(i);
            item.setId(ids[i]);
            item.setOnClickListener(this);
            if (lev < 3)  item.setOnLongClickListener(this);
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
                prof.putExtra("id", i);
                startActivity(prof);
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        final int id = view.getId();
        DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MoimObj.Member mem = null;
                for (MoimObj.Member m : M){
                    if (Integer.valueOf(m.getMem_id()) == id){
                        mem = m;
                        lev = mem.getMem_lev();
                        break;
                    }
                }
                switch(i){
                    case 1: // 1 대 1 대화

                    case 2: // 하선시키기
                        AlertDialog.Builder check = new AlertDialog.Builder(getContext());
                        check.setMessage("하선시키겠습니까?");
                        check.setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ObjManager o = new ObjManager("moim.jsp?mid=" + mid + "&jq=" + 0 + "&id=" + id);
                                o.CheckorLogout();
                            }
                        });
                        check.setNegativeButton("아니오", null);
                        check.show();
                        break;
                    case 3: // 선장 넘기기
                        mem.setMem_lev(1);
                        //mem.get
                        break;
                    case 4: // 부선장 임명/해임
                        if (Integer.valueOf(mem.getMem_id()) == 2)   mem.setMem_lev(2);

                        break;
                }
            }
        };

        CharSequence [] lists = null;
        if (lev == 2){
            lists = new CharSequence[2];
            lists[0] = "1 대 1 대화";
            lists[1] = "하선시키기";
        }else if (lev == 1){
            lists = new CharSequence[4];
            lists[0] = "1 대 1 대화";
            lists[1] = "하선시키기";
            lists[2] = "선장 넘기기";
            lists[3] = "부선장 임명/해임";
        }
        for (int i: ids){
            if (id == i){
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("멤버 설정").setItems(lists, click);
                alert.show();
            }
        }
        return false;
    }
}