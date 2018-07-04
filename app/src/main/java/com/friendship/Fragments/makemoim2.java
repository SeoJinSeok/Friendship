package com.friendship.Fragments;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Objects.MoimObj;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class makemoim2 extends Fragment implements View.OnClickListener {
    private ImageButton back;
    private EditText title, onecomm, content;
    private TextView hint;

    public boolean getObj(MoimObj M) {
        if (M == null) return false;
        back.setBackground(new BitmapDrawable(getContext().getResources(), M.getBack()));
        title.setText(M.getTitle());
        onecomm.setText(M.getComm());
        content.setText(M.getContent());
        return true;
    }

    public Object[] setObj() {
        Object[] objs = new Object[4];
        objs[0] = back.getDrawingCache();
        objs[1] = title.getText().toString();
        objs[2] = onecomm.getText().toString();
        objs[3] = content.getText().toString();
        return objs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_makemoim2, container, false);
        View ititle, ionecomm, icontent;
        TextView titlet, onecommt, contentt;
        back = view.findViewById(R.id.makemoim2_back);
        back.setAdjustViewBounds(true);
        back.setDrawingCacheEnabled(true);
        back.setBackgroundResource(R.drawable.addmoim2_back);
        ititle = view.findViewById(R.id.makemoim2_titleimg);
        ititle.setBackgroundResource(R.drawable.icon);
        titlet = view.findViewById(R.id.makemoim2_titlet);
        titlet.setText("모임명");
        title = view.findViewById(R.id.makemoim2_title);
        ionecomm = view.findViewById(R.id.makemoim2_onecommimg);
        ionecomm.setBackgroundResource(R.drawable.icon2);
        onecommt = view.findViewById(R.id.makemoim2_onecommt);
        onecommt.setText("모임 한줄 소개");
        onecomm = view.findViewById(R.id.makemoim2_onecomm);
        icontent = view.findViewById(R.id.makemoim2_contentimg);
        icontent.setBackgroundResource(R.drawable.icon);
        contentt = view.findViewById(R.id.makemoim2_contentt);
        contentt.setText("모임 소개");
        content = view.findViewById(R.id.makemoim2_content);
        hint = view.findViewById(R.id.makemoim2_hint);
        hint.setText(R.string.moim_content);

        back.setOnClickListener(this);
        content.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final int SET_BACK = 0;
        if (requestCode == SET_BACK && resultCode == RESULT_OK && data != null) {
            Uri img = data.getData();
            CropImage.activity(img).start(getContext(), this);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult res = CropImage.getActivityResult(data);
            Uri cimg = res.getUri();
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(cimg);
                back.setBackground(Drawable.createFromStream(is, cimg.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.makemoim2_back:
                Intent setback = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(setback, 0);
            case R.id.makemoim2_content:
                hint.setText(null);
                break;
            default:
                break;
        }
    }
}