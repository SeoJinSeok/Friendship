package com.friendship.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.Objects.ProfileObj;
import com.friendship.Objects.RoundImage;
import com.friendship.REST.ObjManager;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.lang.reflect.Array;

public class ProfileSettings extends AppCompatActivity implements View.OnClickListener {
    private static final int SET_ICON = 1, SET_REGION = 2, SET_FAVORITES = 3, SKYBLUE = Color.rgb(0, 144, 255);
    private View progress, layout;
    private Button Selfav, Man, Woman;
    private EditText Nick, Comm, Year, Month, Date;
    private TextView[] Favos = new TextView[3];
    private ImageButton Icon;
    private String[] favos = new String[3];
    private Button Reg, Finish;
    private ProfileObj Pobj;
    private AlertDialog.Builder alarm;
    private boolean sex;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilesettings);
        layout = findViewById(R.id.layout_profilesettings);
        layout.setBackgroundResource(R.drawable.profile_setting);
        layout.setVisibility(View.INVISIBLE);
        View top = findViewById(R.id.settings_top);
        top.setBackgroundResource(R.drawable.profile_setting_top);
        progress = findViewById(R.id.settings_progress);
        Button back = findViewById(R.id.settings_back);
        back.setBackgroundResource(R.drawable.back);

        Nick = findViewById(R.id.settings_nick);
        Icon = findViewById(R.id.settings_icon);
        Icon.setAdjustViewBounds(true);
        Comm = findViewById(R.id.settings_comment);
        Year = findViewById(R.id.settings_year);
        Month = findViewById(R.id.settings_month);
        Date = findViewById(R.id.settings_date);
        Man = findViewById(R.id.settings_man);
        Woman = findViewById(R.id.settings_woman);
        Selfav = findViewById(R.id.settings_favos);
        Favos[0] = findViewById(R.id.settings_favo1);
        Favos[1] = findViewById(R.id.settings_favo2);
        Favos[2] = findViewById(R.id.settings_favo3);
        Reg = findViewById(R.id.settings_region);
        Finish = findViewById(R.id.settings_finish);

        Icon.setDrawingCacheEnabled(true);
        if (!getIntent().getBooleanExtra("isNew", false))   startTask(0);
        else{
            layout.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }

        back.setOnClickListener(this);
        Icon.setOnClickListener(this);
        Man.setOnClickListener(this);
        Woman.setOnClickListener(this);
        Reg.setOnClickListener(this);
        Selfav.setOnClickListener(this);
        Finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_back:
                finish();
                break;
            case R.id.settings_icon:
                Intent uploadIcon = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(uploadIcon, SET_ICON);
                break;
            case R.id.settings_man:
                sex = false;
                Man.setBackgroundColor(SKYBLUE);
                Man.setTextColor(Color.WHITE);
                Woman.setBackgroundColor(Color.TRANSPARENT);
                Woman.setTextColor(Color.BLACK);
                break;
            case R.id.settings_woman:
                sex = true;
                Woman.setBackgroundColor(SKYBLUE);
                Woman.setTextColor(Color.WHITE);
                Man.setBackgroundColor(Color.TRANSPARENT);
                Man.setTextColor(Color.BLACK);
                break;
            case R.id.settings_region:
                Intent select_reg = new Intent(this, SelectRegion.class);
                startActivityForResult(select_reg, SET_REGION);
                break;
            case R.id.settings_favos:
                Intent select_favos = new Intent(this, SelectFavorites.class);
                select_favos.putExtra("max", 2);
                select_favos.putExtra("favos", favos);
                startActivityForResult(select_favos, SET_FAVORITES);
                break;
            case R.id.settings_finish:
                progress.setVisibility(View.VISIBLE);
                alarm = new AlertDialog.Builder(this);
                SetObject();
                startTask(1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SET_ICON && resultCode == RESULT_OK && data != null) {
            Uri imgUri = data.getData();
            CropImage.activity(imgUri).start(this);
        } else if (requestCode == SET_REGION && resultCode == RESULT_OK && data != null)
            Reg.setText(data.getStringExtra("region"));
        else if (requestCode == SET_FAVORITES && resultCode == RESULT_OK && data != null) {
            String[] fv = data.getStringArrayExtra("favos");

            System.arraycopy(fv, 0, favos, 0, fv.length);
            if (fv.length < 3) for (int i = 2; i >= fv.length; i--) {
                favos[i] = null;
                Favos[i].setText(null);
                Favos[i].setBackground(null);
            }
            for (int i = 0; i < 3; i++) {
                Favos[i].setText(favos[i]);
                Favos[i].setBackgroundColor(SKYBLUE);
            }
            if (favos == null) {
                Favos[0].setBackgroundColor(Color.TRANSPARENT);
                Favos[1].setBackgroundColor(Color.TRANSPARENT);
                Favos[2].setBackgroundColor(Color.TRANSPARENT);
                Selfav.setText("관심사를 선택하세요.");
            } else Selfav.setText(null);
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult res = CropImage.getActivityResult(data);
            Uri uri = res.getUri();
            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap tmp = Bitmap.createScaledBitmap(img, Icon.getWidth(), Icon.getHeight(), true);
                RoundImage rImg = new RoundImage(tmp);
                Icon.setBackground(rImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SetObject() {
        Icon.setDrawingCacheEnabled(true);
        Pobj = new ProfileObj();
        Pobj.setNick(Nick.getText().toString());
        Pobj.setIcon(Icon.getDrawingCache(true));
        Pobj.setComm(Comm.getText().toString());
        Pobj.setBirth(Year.getText().toString() + "-" + Month.getText().toString() + "-" + Date.getText().toString());
        Pobj.setFavos(favos);
        Pobj.setRegion(Reg.getText().toString());
        Pobj.setSex(sex);
    }

    public void onResult(Object obj) {
        layout.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);

        if (obj != null || obj instanceof Array) {
            Pobj = (ProfileObj) ((Object[]) obj)[0];
            if (Pobj.getIcon() == null) {
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.defaulticon);
                Bitmap tmp = Bitmap.createScaledBitmap(img, 200, 200, true);
                RoundImage rImg = new RoundImage(tmp);
                Icon.setBackground(rImg);
            } else {
                Bitmap tmp = Bitmap.createScaledBitmap(Pobj.getIcon(), 200, 200, true);
                RoundImage rImg = new RoundImage(tmp);
                Icon.setBackground(rImg);
            }
            Reg.setText(Pobj.getRegion());
            Nick.setText(Pobj.getNick());
            Comm.setText(Pobj.getComm());
            String[] birth = Pobj.getBirth().split("-");
            Year.setText(birth[0]);
            Month.setText(birth[1]);
            Date.setText(birth[2]);
            if (!Pobj.getSex()) {
                Man.setBackgroundColor(SKYBLUE);
                Man.setTextColor(Color.WHITE);
            } else {
                Woman.setBackgroundColor(SKYBLUE);
                Woman.setTextColor(Color.WHITE);
            }
            String[] fv = Pobj.getFavos();
            System.arraycopy(fv, 0, favos, 0, fv.length);
            for (int i = 0; i < fv.length; i++) {
                Favos[i].setText(favos[i]);
                Favos[i].setBackgroundColor(SKYBLUE);
            }
            if (favos == null) Selfav.setText("관심사를 선택하세요.");
        } else if ((boolean) obj) {
            DialogInterface.OnCancelListener cancel = new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                    Intent Main = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(Main);
                    finish();
                }
            };
            alarm.setOnCancelListener(cancel);
            alarm.setMessage("수정이 완료되었습니다.");
            alarm.show();
        } else {
            Finish.setError("오류가 발생했습니다");
            Finish.requestFocus();
        }
    }

    private void startTask(int id) {
        ObjManager Omgr = new ObjManager("profile.jsp");
        if (id == 0) Omgr.GetProf(null, this);
        else Omgr.SetProf(Pobj, this);
    }
}