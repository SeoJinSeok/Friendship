package com.friendship.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.friendship.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class AlarmSetting extends Fragment implements View.OnClickListener {
    private static int skyblue = Color.rgb(0, 144, 255);
    private Button[] btns; // 알림 버튼들
    private JSONObject malarm;
    private boolean isCap = false;
    private int mid;

    public AlarmSetting() {
        super();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mid != 0) setObj(isCap, mid);
    }

    public void setObj(boolean cap, int id) {
        isCap = cap;
        mid = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_alarm_setting, container, false);
        View layout = view.findViewById(R.id.layout_malarm), top = view.findViewById(R.id.malarm_top), back = view.findViewById(R.id.malarm_back), img = view.findViewById(R.id.malarm_img), sailor = view.findViewById(R.id.malarm_sailor);
        layout.setBackgroundResource(R.drawable.msetting_bg);
        top.setBackgroundResource(R.drawable.addmoim_top);
        back.setBackgroundResource(R.drawable.back);
        img.setBackgroundResource(R.drawable.msetting);
        sailor.setBackgroundResource(R.drawable.malarm_bg);
        btns = new Button[8];
        btns[0] =  view.findViewById(R.id.malarm_moimon);
        btns[1] =  view.findViewById(R.id.malarm_moimoff);
        btns[2] =  view.findViewById(R.id.malarm_chaton);
        btns[3] =  view.findViewById(R.id.malarm_chatoff);
        btns[4] =  view.findViewById(R.id.malarm_albumon);
        btns[5] =  view.findViewById(R.id.malarm_albumon);
        btns[6] =  view.findViewById(R.id.malarm_memon);
        btns[7] =  view.findViewById(R.id.malarm_memoff);

        try {
            FileInputStream f = new FileInputStream("alarm.txt");
            int read;
            byte[] b = new byte[512];
            while ((read = f.read(b)) != -1) System.out.write(b, 0, read);
            f.close();
            malarm = new JSONObject(new String(b));
            if (malarm.getBoolean("notice")) {
                btns[0].setBackgroundColor(skyblue);
                btns[0].setTextColor(Color.WHITE);
                btns[1].setBackgroundColor(Color.TRANSPARENT);
                btns[1].setTextColor(Color.BLACK);
            } else {
                btns[1].setBackgroundColor(skyblue);
                btns[1].setTextColor(Color.WHITE);
                btns[0].setBackgroundColor(Color.TRANSPARENT);
                btns[0].setTextColor(Color.BLACK);
            }
            if (malarm.getBoolean("chat")) {
                btns[2].setBackgroundColor(skyblue);
                btns[2].setTextColor(Color.WHITE);
                btns[3].setBackgroundColor(Color.TRANSPARENT);
                btns[3].setTextColor(Color.BLACK);
            } else {
                btns[3].setBackgroundColor(skyblue);
                btns[3].setTextColor(Color.WHITE);
                btns[2].setBackgroundColor(Color.TRANSPARENT);
                btns[2].setTextColor(Color.BLACK);
            }

            if (malarm.getBoolean("album")) {
                btns[4].setBackgroundColor(skyblue);
                btns[4].setTextColor(Color.WHITE);
                btns[5].setBackgroundColor(Color.TRANSPARENT);
                btns[5].setTextColor(Color.BLACK);
            } else {
                btns[5].setBackgroundColor(skyblue);
                btns[5].setTextColor(Color.WHITE);
                btns[4].setBackgroundColor(Color.TRANSPARENT);
                btns[4].setTextColor(Color.BLACK);
            }

            if (malarm.getBoolean("member")) {
                btns[6].setBackgroundColor(skyblue);
                btns[6].setTextColor(Color.WHITE);
                btns[7].setBackgroundColor(Color.TRANSPARENT);
                btns[7].setTextColor(Color.BLACK);
            } else {
                btns[7].setBackgroundColor(skyblue);
                btns[7].setTextColor(Color.WHITE);
                btns[6].setBackgroundColor(Color.TRANSPARENT);
                btns[6].setTextColor(Color.BLACK);
            }
        } catch (Exception e) {
            btns[0].setBackgroundColor(skyblue);
            btns[0].setTextColor(Color.WHITE);
            btns[1].setBackgroundColor(Color.TRANSPARENT);
            btns[1].setTextColor(Color.BLACK);
            btns[2].setBackgroundColor(skyblue);
            btns[2].setTextColor(Color.WHITE);
            btns[3].setBackgroundColor(Color.TRANSPARENT);
            btns[3].setTextColor(Color.BLACK);
            btns[4].setBackgroundColor(skyblue);
            btns[4].setTextColor(Color.WHITE);
            btns[5].setBackgroundColor(Color.TRANSPARENT);
            btns[5].setTextColor(Color.BLACK);
            btns[6].setBackgroundColor(skyblue);
            btns[6].setTextColor(Color.WHITE);
            btns[7].setBackgroundColor(Color.TRANSPARENT);
            btns[7].setTextColor(Color.BLACK);
        }
        Button delout =  view.findViewById(R.id.malarm_outordel);
        if (isCap) delout.setText("선박해지");
        else delout.setText("하선하기");
        delout.setOnClickListener(this);
        for (Button i : btns) {
            i.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.malarm_moimon:
                btns[0].setBackgroundColor(skyblue);
                btns[0].setTextColor(Color.WHITE);
                btns[1].setBackgroundColor(Color.TRANSPARENT);
                btns[1].setTextColor(Color.BLACK);
                try {
                    malarm.put("moim", true);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_moimoff:
                btns[1].setBackgroundColor(skyblue);
                btns[1].setTextColor(Color.WHITE);
                btns[0].setBackgroundColor(Color.TRANSPARENT);
                btns[0].setTextColor(Color.BLACK);
                try {
                    malarm.put("moim", false);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_chaton:
                btns[2].setBackgroundColor(skyblue);
                btns[2].setTextColor(Color.WHITE);
                btns[3].setBackgroundColor(Color.TRANSPARENT);
                btns[3].setTextColor(Color.BLACK);
                try {
                    malarm.put("chat", true);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_chatoff:
                btns[3].setBackgroundColor(skyblue);
                btns[3].setTextColor(Color.WHITE);
                btns[2].setBackgroundColor(Color.TRANSPARENT);
                btns[2].setTextColor(Color.BLACK);
                try {
                    malarm.put("chat", false);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_albumon:
                btns[4].setBackgroundColor(skyblue);
                btns[4].setTextColor(Color.WHITE);
                btns[5].setBackgroundColor(Color.TRANSPARENT);
                btns[5].setTextColor(Color.BLACK);
                try {
                    malarm.put("album", true);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_albumoff:
                btns[5].setBackgroundColor(skyblue);
                btns[5].setTextColor(Color.WHITE);
                btns[4].setBackgroundColor(Color.TRANSPARENT);
                btns[4].setTextColor(Color.BLACK);
                try {
                    malarm.put("album", false);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_memon:
                btns[6].setBackgroundColor(skyblue);
                btns[6].setTextColor(Color.WHITE);
                btns[7].setBackgroundColor(Color.TRANSPARENT);
                btns[7].setTextColor(Color.BLACK);
                try {
                    malarm.put("member", true);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_memoff:
                btns[7].setBackgroundColor(skyblue);
                btns[7].setTextColor(Color.WHITE);
                btns[6].setBackgroundColor(Color.TRANSPARENT);
                btns[6].setTextColor(Color.BLACK);
                try {
                    malarm.put("member", false);
                } catch (JSONException e) {
                    break;
                }
                break;
            case R.id.malarm_outordel:

        }
        try {
            FileOutputStream alarm = new FileOutputStream(mid + "/alarm.txt");
            alarm.write(malarm.toString().getBytes());
            alarm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
