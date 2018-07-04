package com.friendship.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.friendship.R;

public class SelectFavorites extends AppCompatActivity implements View.OnClickListener {
    private final int SKYBLUE = Color.rgb(0, 144, 255);
    private Button[] favos = new Button[18];
    private boolean[] isSelected = new boolean[18];
    private String[] fav = new String[3];
    private int num = 0, max = 0;
    private TextView sel;
    private AlertDialog.Builder error;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 뷰 불러오기
        setContentView(R.layout.activity_select_favorites);
        max = getIntent().getIntExtra("max", 0);
        View view = findViewById(R.id.activity_select_favorites);
        view.setBackgroundResource(R.drawable.category);
        Button back = findViewById(R.id.favo_back);
        Button send = findViewById(R.id.favo_send);
        TextView limit = findViewById(R.id.favo_limit);
        limit.setText(Integer.toString(max + 1));
        sel = findViewById(R.id.favo_sel);
        favos[0] = findViewById(R.id.favo_1);
        favos[1] = findViewById(R.id.favo_2);
        favos[2] = findViewById(R.id.favo_3);
        favos[3] = findViewById(R.id.favo_4);
        favos[4] = findViewById(R.id.favo_5);
        favos[5] = findViewById(R.id.favo_6);
        favos[6] = findViewById(R.id.favo_7);
        favos[7] = findViewById(R.id.favo_8);
        favos[8] = findViewById(R.id.favo_9);
        favos[9] = findViewById(R.id.favo_10);
        favos[10] = findViewById(R.id.favo_11);
        favos[11] = findViewById(R.id.favo_12);
        favos[12] = findViewById(R.id.favo_13);
        favos[13] = findViewById(R.id.favo_14);
        favos[14] = findViewById(R.id.favo_15);
        favos[15] = findViewById(R.id.favo_16);
        favos[16] = findViewById(R.id.favo_17);
        favos[17] = findViewById(R.id.favo_18);

        String[] fv = getIntent().getStringArrayExtra("favos");
        if (fv != null) System.arraycopy(fv, 0, fav, 0, fv.length);

        // 선택된 관심사 불러오기
        for (String i : fav) {
            if (i == null) break;
            for (int j = 0; j < 18; j++) {
                if (i.equals(favos[j].getText().toString())) {
                    isSelected[j] = true;
                    favos[j].setTextColor(Color.WHITE);
                    favos[j].setBackgroundColor(SKYBLUE);
                    num++;
                    String txt = String.valueOf(num) + "개";
                    sel.setText(txt);
                }
            }
        }
        error = new AlertDialog.Builder(this);
        back.setOnClickListener(this);
        send.setOnClickListener(this);
        for (int i = 0; i < 18; i++) favos[i].setOnClickListener(this);
    }

    // 선택된 관심사 체크하고 해당 아이콘 삽입
    private void setChecked(int i) {
        if (i < 0) return;
        if (isSelected[i]) {
            isSelected[i] = false;
            num--;
            fav[num] = null;
            String txt = Integer.toString(num) + "개";
            sel.setText(txt);
            favos[i].setTextColor(Color.BLACK);
            favos[i].setBackgroundColor(Color.TRANSPARENT);
        } else {
            if (num > max) {
                error.setMessage("관심사는 최대 " + (max + 1) + "개까지만 선택 가능합니다.");
                error.show();
            } else {
                favos[i].setTextColor(Color.WHITE);
                favos[i].setBackgroundColor(SKYBLUE);
                isSelected[i] = true;
                fav[num] = favos[i].getText().toString();
                num++;
                String txt = Integer.toString(num) + "개";
                sel.setText(txt);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int favoid = -1;
        for (int i = 0; i < 18; i++) {
            if (view.getId() == favos[i].getId()) {
                favoid = i;
                break;
            }
        }
        switch (view.getId()) {
            case R.id.favo_back:
                finish();
                break;
            case R.id.favo_send:
                Intent Result = new Intent();
                Result.putExtra("favos", fav);
                setResult(RESULT_OK, Result);
                finish();
                break;
            default:
                setChecked(favoid);
                break;
        }
    }
}