package com.friendship.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Activities.SelectFavorites;
import com.friendship.Activities.SelectRegion;
import com.friendship.Objects.MoimObj;

import static android.app.Activity.RESULT_OK;

public class makemoim1 extends Fragment implements View.OnClickListener {
    final private int SEL_CATE = 2, SEL_REG = 3;
    private EditText agel, ageh, limit;
    private TextView catename, state, city;

    public boolean getObj(MoimObj M) {
        if (M == null) return false;
        catename.setText(M.getCate());
        String[] reg = M.getReg().split("-");
        state.setText(reg[0]);
        city.setText(reg[1]);
        agel.setText(M.getAgel());
        ageh.setText(M.getAgeh());
        return true;
    }

    public Object[] setObj() {
        Object[] objs = new Object[5];
        objs[0] = catename.getText().toString();
        if (city.getText().toString().equals("전체")) objs[1] = state.getText().toString();
        else objs[1] = state.getText().toString() + " - " + city.getText().toString();
        objs[2] = agel.getText().toString();
        objs[3] = ageh.getText().toString();
        objs[4] = limit.getText().toString();
        return objs;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makemoim1, container, false);
        View icate, ireg, iage, iageto, ilimit;
        TextView catetitle, regtitle, agetitle, limittitle;
        icate = view.findViewById(R.id.makemoim1_cateimg);
        icate.setBackgroundResource(R.drawable.icon);
        catetitle = view.findViewById(R.id.makemoim1_catetitle);
        catetitle.setText("카테고리");
        ImageButton cate = view.findViewById(R.id.makemoim1_category);
        cate.setBackgroundColor(Color.TRANSPARENT);
        catename = view.findViewById(R.id.makemoim1_categoryname);
        catename.setText("카테고리를 선택하세요.");
        ireg = view.findViewById(R.id.makemoim1_regimg);
        ireg.setBackgroundResource(R.drawable.icon);
        regtitle = view.findViewById(R.id.makemoim1_regtitle);
        regtitle.setText("지역");
        state = view.findViewById(R.id.makemoim1_state);
        state.setBackgroundResource(R.drawable.addmoim1_state);
        city = view.findViewById(R.id.makemoim1_city);
        city.setBackgroundResource(R.drawable.addmoim1_state);
        iage = view.findViewById(R.id.makemoim1_ageimg);
        iage.setBackgroundResource(R.drawable.icon);
        agetitle = view.findViewById(R.id.makemoim1_agetitle);
        agetitle.setText("나이제한");
        agel = view.findViewById(R.id.makemoim1_agelow);
        agel.setBackgroundResource(R.drawable.addmoim1_age);
        iageto = view.findViewById(R.id.makemoim1_ageto);
        iageto.setBackgroundResource(R.drawable.addmoim1_ager);
        ageh = view.findViewById(R.id.makemoim1_agehigh);
        ageh.setBackgroundResource(R.drawable.addmoim1_age);
        ilimit = view.findViewById(R.id.makemoim1_limitimg);
        ilimit.setBackgroundResource(R.drawable.addmoim1_limit);
        limit = view.findViewById(R.id.makemoim1_limit);
        limittitle = view.findViewById(R.id.makemoim1_limittitle);
        limittitle.setText("모임정원");

        cate.setOnClickListener(this);
        state.setOnClickListener(this);
        city.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEL_CATE && resultCode == RESULT_OK && data != null) {
            catename.setText(data.getStringArrayExtra("favos")[0]);
        } else if (requestCode == SEL_REG && resultCode == RESULT_OK && data != null) {
            String[] str = data.getStringExtra("region").split("-");
            state.setText(str[0]);
            state.setTextColor(Color.WHITE);
            city.setText(str[1]);
            city.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent CS;
        switch (id) {
            case R.id.makemoim1_category:
                CS = new Intent(getContext(), SelectFavorites.class);
                CS.putExtra("max", 0);
                startActivityForResult(CS, SEL_CATE);
                break;
            case R.id.makemoim1_state:
                CS = new Intent(getContext(), SelectRegion.class);
                startActivityForResult(CS, SEL_REG);
                break;
            case R.id.makemoim1_city:
                CS = new Intent(getContext(), SelectRegion.class);
                startActivityForResult(CS, SEL_REG);
                break;
            default:
                break;
        }
    }
}
