package com.friendship.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.friendship.R;

public class SelectRegion extends Activity implements View.OnClickListener {
    private final String[] StateList = {"서울특별시", "경기도", "강원도", "경상남도", "경상북도", "광주광역시", "대구광역시", "대전광역시", "부산광역시", "세종시", "울산광역시", "인천광역시",
            "전라남도", "전라북도", "충청남도", "충청북도", "제주도"},
            SeoulCity = {"강남구", "강동구", "강북구", "강서구", "구로구", "관악구", "금천구", "광진구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
                    "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"},
            BusanCity = {"강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"},
            DaeguCity = {"남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"},
            IncheonCity = {"남구", "남동구", "계양구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"},
            GwangjuCity = {"광산구", "남구", "동구", "북구", "서구"},
            DaejunCity = {"대덕구", "동구", "서구", "유성구", "중구"},
            UlsanCity = {"남구", "동구", "북구", "중구"},
            GyonggiCity = {"가평", "고양", "구리", "군포", "김포", "과천", "광명", "광주", "남양주", "동두천", "부천", "성남", "수원", "시흥", "안양", "안산",
                    "안성", "양주", "양평", "여주", "연천", "오산", "용인", "이천", "의정부", "의왕", "파주", "평택", "포천", "하남", "화성"},
            GwangwonCity = {"강릉", "고성", "동해", "삼척", "속초", "양구", "양양", "영월", "인제", "원주", "정선", "춘천", "철원", "태백", "평창", "홍천", "화천", "횡성"},
            SejongCity = null,
            ChungBukCity = {"괴산", "단양", "보은", "영동", "옥천", "음성", "제천", "증평", "진천", "청주", "충주"},
            ChungNamCity = {"공주", "금산", "논산", "계룡", "당진", "보령", "부여", "아산", "예산", "서산", "서천", "천안", "청양", "태산", "홍성"},
            JunBukCity = {"군산", "김제", "남원", "고창", "무주", "부안", "순창", "익산", "임실", "완주", "장수", "전주", "정읍", "진안"},
            JunNamCity = {"강진", "고흥", "곡성", "구례", "광양", "나주", "담양", "목포", "무안", "보성", "순천", "신안", "여수", "영광", "영암", "장성", "장흥", "진도", "함평", "화순"},
            GyongBukCity = {"경산", "경주", "고령", "구미", "군위", "김천", "문경", "봉화", "성주", "상주", "안동", "영덕", "영양", "영주", "영천", "예천", "울릉", "울진", "의성", "청도", "청송", "칠곡", "포항"},
            GyongNamCity = {"거제", "거창", "고성", "김해", "남해", "밀양", "사천", "산청", "양산", "의령", "의창", "진주", "진해", "창원", "통영", "함안", "함양", "합천"},
            JejuCity = {"제주", "서귀포"};
    private final String[][] CityParam = {SeoulCity, GyonggiCity, GwangwonCity, GyongNamCity, GyongBukCity, GwangjuCity, DaeguCity, DaejunCity, BusanCity, SejongCity, UlsanCity, IncheonCity,
            JunNamCity, JunBukCity, ChungNamCity, ChungBukCity, JejuCity};
    private String reg = null;
    private Button State[] = new Button[31];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_region);
        View view = findViewById(R.id.activity_select_region);
        view.setBackgroundResource(R.drawable.sel_reg);
        setView(0, null);
    }

    private void setReg(String r) {
        if (reg == null) {
            if (r.equals("세종시")) {
                reg = r;
                Intent Result = new Intent();
                Result.putExtra("region", reg);
                setResult(RESULT_OK, Result);
                finish();
            }
            reg = r + " - ";
        } else {
            reg += r;
            Intent Result = new Intent();
            Result.putExtra("region", reg);
            setResult(RESULT_OK, Result);
            finish();
        }
    }

    @Override
    final public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sel1:
                setReg(State[0].getText().toString());
                setView(1, CityParam[0]);
                break;
            case R.id.sel2:
                setReg(State[1].getText().toString());
                setView(1, CityParam[1]);
                break;
            case R.id.sel3:
                setReg(State[2].getText().toString());
                setView(1, CityParam[2]);
                break;
            case R.id.sel4:
                setReg(State[3].getText().toString());
                setView(1, CityParam[3]);
                break;
            case R.id.sel5:
                setReg(State[4].getText().toString());
                setView(1, CityParam[4]);
                break;
            case R.id.sel6:
                setReg(State[5].getText().toString());
                setView(1, CityParam[5]);
                break;
            case R.id.sel7:
                setReg(State[6].getText().toString());
                setView(1, CityParam[6]);
                break;
            case R.id.sel8:
                setReg(State[7].getText().toString());
                setView(1, CityParam[7]);
                break;
            case R.id.sel9:
                setReg(State[8].getText().toString());
                setView(1, CityParam[8]);
                break;
            case R.id.sel10:
                setReg(State[9].getText().toString());
                setView(1, CityParam[9]);
                break;
            case R.id.sel11:
                setReg(State[10].getText().toString());
                setView(1, CityParam[10]);
                break;
            case R.id.sel12:
                setReg(State[11].getText().toString());
                setView(1, CityParam[11]);
                break;
            case R.id.sel13:
                setReg(State[12].getText().toString());
                setView(1, CityParam[12]);
                break;
            case R.id.sel14:
                setReg(State[13].getText().toString());
                setView(1, CityParam[13]);
                break;
            case R.id.sel15:
                setReg(State[14].getText().toString());
                setView(1, CityParam[14]);
                break;
            case R.id.sel16:
                setReg(State[15].getText().toString());
                setView(1, CityParam[15]);
                break;
            case R.id.sel17:
                setReg(State[16].getText().toString());
                setView(1, CityParam[16]);
                break;
            case R.id.sel18:
                setReg(State[17].getText().toString());
                setView(1, CityParam[17]);
                break;
            case R.id.sel19:
                setReg(State[18].getText().toString());
                setView(1, CityParam[18]);
                break;
            case R.id.sel20:
                setReg(State[19].getText().toString());
                setView(1, CityParam[19]);
                break;
            case R.id.sel21:
                setReg(State[20].getText().toString());
                setView(1, CityParam[20]);
                break;
            case R.id.sel22:
                setReg(State[21].getText().toString());
                setView(1, CityParam[21]);
                break;
            case R.id.sel23:
                setReg(State[22].getText().toString());
                setView(1, CityParam[22]);
                break;
            case R.id.sel24:
                setReg(State[23].getText().toString());
                setView(1, CityParam[23]);
                break;
            case R.id.sel25:
                setReg(State[24].getText().toString());
                setView(1, CityParam[24]);
                break;
            case R.id.sel26:
                setReg(State[25].getText().toString());
                setView(1, CityParam[25]);
                break;
            case R.id.sel27:
                setReg(State[26].getText().toString());
                setView(1, CityParam[26]);
                break;
            case R.id.sel28:
                setReg(State[27].getText().toString());
                setView(1, CityParam[27]);
                break;
            case R.id.sel29:
                setReg(State[28].getText().toString());
                setView(1, CityParam[28]);
                break;
            case R.id.sel30:
                setReg(State[29].getText().toString());
                setView(1, CityParam[29]);
                break;
            case R.id.sel31:
                setReg(State[30].getText().toString());
                setView(1, CityParam[30]);
                break;
            default:
                break;
        }
    }

    private void setView(int id, String[] city) {
        if (id == 0) {
            State[0] = findViewById(R.id.sel1);
            State[1] = findViewById(R.id.sel2);
            State[2] = findViewById(R.id.sel3);
            State[3] = findViewById(R.id.sel4);
            State[4] = findViewById(R.id.sel5);
            State[5] = findViewById(R.id.sel6);
            State[6] = findViewById(R.id.sel7);
            State[7] = findViewById(R.id.sel8);
            State[8] = findViewById(R.id.sel9);
            State[9] = findViewById(R.id.sel10);
            State[10] = findViewById(R.id.sel11);
            State[11] = findViewById(R.id.sel12);
            State[12] = findViewById(R.id.sel13);
            State[13] = findViewById(R.id.sel14);
            State[14] = findViewById(R.id.sel15);
            State[15] = findViewById(R.id.sel16);
            State[16] = findViewById(R.id.sel17);
            State[17] = findViewById(R.id.sel18);
            State[18] = findViewById(R.id.sel19);
            State[19] = findViewById(R.id.sel20);
            State[20] = findViewById(R.id.sel21);
            State[21] = findViewById(R.id.sel22);
            State[22] = findViewById(R.id.sel23);
            State[23] = findViewById(R.id.sel24);
            State[24] = findViewById(R.id.sel25);
            State[25] = findViewById(R.id.sel26);
            State[26] = findViewById(R.id.sel27);
            State[27] = findViewById(R.id.sel28);
            State[28] = findViewById(R.id.sel29);
            State[29] = findViewById(R.id.sel30);
            State[30] = findViewById(R.id.sel31);

            for (int i = 0; i < 31; i++) {
                if (i < 17) State[i].setText(StateList[i]);
                State[i].setOnClickListener(this);
            }
        } else {
            for (int i = 0; i < 31; i++) {
                if (i < city.length) State[i].setText(city[i]);
                else State[i].setText(null);
            }
        }
    }
}
