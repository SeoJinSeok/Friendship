package com.friendship.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Activities.MoimInfo;
import com.friendship.Objects.MainObj;

public class mainFragment extends Fragment implements View.OnClickListener {
    private final CoordinatorLayout.LayoutParams ilayout = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
    private final CoordinatorLayout.LayoutParams tlayout = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
    private MainObj M = null;
    private LinearLayout premium, interest;
    private ViewPager toppage;
    private View[] sel = new View[3];
    private int pos;
    private Context con;

    public void setobj(MainObj M) {
        this.M = M;
        int[] ids = M.getIds();
        Bitmap[] imgs = M.getImgs();
        String[] titles = M.getTitles(), intros = M.getIntros();
        int i, len = ids.length;
        // TOP 3 모임 설정
        page topadapter = new page(con);
        toppage.setAdapter(topadapter);
        toppage.setCurrentItem(0);
        ViewPager.OnPageChangeListener pchaged = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                toppage.setCurrentItem(position);
                sel[pos].setBackgroundResource(R.drawable.gray);
                pos = position;
                sel[position].setBackgroundResource(R.drawable.blue);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        toppage.addOnPageChangeListener(pchaged);

        // 프리미엄 모임 설정
        LinearLayout[] pmoim = new LinearLayout[len];
        ImageView[] img = new ImageView[len];
        TextView[] title = new TextView[len], intro = new TextView[len];

        for (i = 0; i < len; i++) {
            // 전체 Layout 설정
            pmoim[i] = new LinearLayout(con);
            pmoim[i].setLayoutParams(new LinearLayout.LayoutParams(220, ViewGroup.LayoutParams.MATCH_PARENT));
            pmoim[i].setOrientation(LinearLayout.VERTICAL);

            // Image 설정
            img[i] = new ImageView(con);
            img[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160));
            img[i].setImageBitmap(imgs[i]);

            // 제목, 한줄 소개 설정
            title[i] = new TextView(con);
            title[i].setTextSize(20);
            title[i].setTextColor(Color.BLUE);
            title[i].setText(titles[i]);

            intro[i] = new TextView(con);
            intro[i].setTextSize(12);
            intro[i].setText(intros[i]);

            pmoim[i].addView(img[i], 0);
            pmoim[i].addView(title[i], 1);
            pmoim[i].addView(intro[i], 2);
            pmoim[i].setId(ids[i]);
            pmoim[i].setOnClickListener(this);

            premium.addView(pmoim[i], i);
        }

        // 관심사와 맞는 모임 불러오기
        pmoim = new LinearLayout[len];
        img = new ImageView[len];
        title = new TextView[len];
        intro = new TextView[len];

        for (i = 0; i < len; i++) {
            // 전체 Layout 설정
            pmoim[i] = new LinearLayout(con);
            pmoim[i].setLayoutParams(new LinearLayout.LayoutParams(220, ViewGroup.LayoutParams.MATCH_PARENT));
            pmoim[i].setOrientation(LinearLayout.VERTICAL);

            // Image 설정
            img[i] = new ImageView(con);
            img[i].setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160));
            img[i].setBackground(new BitmapDrawable(con.getResources(), imgs[i]));

            // 제목, 한줄 소개 설정
            title[i] = new TextView(con);
            title[i].setTextSize(20);
            title[i].setTextColor(Color.BLUE);
            title[i].setText(titles[i]);

            intro[i] = new TextView(con);
            intro[i].setTextSize(12);
            intro[i].setText(intros[i]);

            pmoim[i].addView(img[i], 0);
            pmoim[i].addView(title[i], 1);
            pmoim[i].addView(intro[i], 2);
            pmoim[i].setId(ids[i]);
            pmoim[i].setOnClickListener(this);

            interest.addView(pmoim[i], i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        con = getContext().getApplicationContext();
        con.setTheme(R.style.AppTheme);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // TOP 3 모임 페이지 불러오기
        toppage = view.findViewById(R.id.mainf_toppage);

        sel[0] = view.findViewById(R.id.mainf_sel1);
        sel[0].setBackgroundResource(R.drawable.blue);
        sel[1] = view.findViewById(R.id.mainf_sel2);
        sel[1].setBackgroundResource(R.drawable.gray);
        sel[2] = view.findViewById(R.id.mainf_sel3);
        sel[2].setBackgroundResource(R.drawable.gray);

        // 프리미엄 모임 목록 불러오기
        View ptitle = view.findViewById(R.id.mainf_pretitle);
        ptitle.setBackgroundResource(R.drawable.main_moimlist);
        premium = view.findViewById(R.id.mainf_premiumlist);

        // 추천, 관심사와 맞는 모임 목록 불러오기
        View ititle = view.findViewById(R.id.mainf_ititle);
        ititle.setBackgroundResource(R.drawable.main_moimlist);
        interest = view.findViewById(R.id.mainf_interestlist);

        return view;
    }

    @Override
    public void onClick(View view) {
        int ids[] = M.getIds();
        for (int i : ids) {
            if (i == view.getId()) {
                Intent info = new Intent(getActivity(), MoimInfo.class);
                info.putExtra("mid", i);
                startActivity(info);
            }
        }
    }

    private class page extends PagerAdapter implements View.OnClickListener {
        private Context con;

        private page(Context c) {
            super();
            con = c;
        }

        @Override
        public int getCount() {
            return M.getIds().length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Layout 설정
            CoordinatorLayout layout = new CoordinatorLayout(con);

            // 배경화면 설정
            ImageView img = new ImageView(con);
            img.setLayoutParams(ilayout);
            img.setBackground(new BitmapDrawable(con.getResources(), M.getImgs()[position]));

            // 제목 설정
            tlayout.setMargins(30, 0, 0, 38);
            tlayout.gravity = Gravity.BOTTOM | Gravity.START;
            TextView title = new TextView(con);
            title.setText(M.getTitles()[position]);
            title.setTextSize(20);
            title.setTextColor(Color.WHITE);
            title.setLayoutParams(tlayout);

            // 한줄 소개 설정
            tlayout.setMargins(30, 0, 0, 75);
            TextView intro = new TextView(con);
            intro.setText(M.getIntros()[position]);
            intro.setTextSize(16);
            intro.setTextColor(Color.WHITE);
            intro.setLayoutParams(tlayout);

            // Layout에 추가하고 adapterView에 붙인다.
            layout.setId(M.getIds()[position]);
            layout.addView(img);
            layout.addView(title);
            layout.addView(intro);
            layout.setOnClickListener(this);
            container.addView(layout, position);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void onClick(View view) {
            int ids[] = M.getIds();
            for (int i : ids) {
                if (i == view.getId()) {
                    Intent info = new Intent(getActivity(), MoimInfo.class);
                    info.putExtra("mid", i);
                    startActivity(info);
                    break;
                }
            }
        }
    }
}