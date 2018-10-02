package com.friendship.REST;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.friendship.Activities.*;
import com.friendship.Objects.AES256Util;
import com.friendship.Objects.BoardObj;
import com.friendship.Objects.UserObj;
import com.friendship.Objects.ProfileObj;
import com.friendship.Objects.MoimObj;
import com.friendship.Objects.MainObj;

import org.bouncycastle.jcajce.provider.digest.Keccak.DigestKeccak;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ObjManager {
    private RESTClient RClient;
    private AES256Util acoder;
    private boolean check = false;
    private String[] ProfParams = {"nick", "icon", "iconuri", "comm", "birth", "sex", "favos", "region", "join"},
            MoimParams = {"title", "onecomm", "cont", "back", "limit", "regi", "cate", "agel", "ageh", "mem"};
    private Activity A = null;

    public ObjManager(String path) {
        this.RClient = RESTClient.getInstance(path);
    }

    private void runTask(String method, String obj, int id) {
        String[] objs = new String[2];
        objs[0] = method;
        if (obj != null) objs[1] = obj;
        else objs[1] = null;
        RESTTask rest = new RESTTask();
        rest.id = id;
        rest.execute(objs);
    }

    // 로그인
    public void Login(UserObj U, LoginActivity L) {
        A = L;
        JSONObject Jobj = new JSONObject();
        try {
            Jobj.put("email", U.getEmail());
            Jobj.put("password", Encode(U.getPassword(), 0, "512"));
            runTask("POST", Encode(URLEncoder.encode(Jobj.toString(), "UTF-8"), 1, "user-login-process"), -1);
        } catch (Exception e) {
            L.onResult(false);
        }
    }

    // 인터넷 상태 체크 or 로그아웃
    public boolean CheckorLogout() {
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                check = (RClient.GET() instanceof  String) || (boolean)RClient.GET();
            }
        });
        thr.start();
        try {
            thr.join();
        } catch (InterruptedException e) {
            return false;
        }
        return check;
    }

    // 회원 가입
    public void SignUp(UserObj U, SignUp S) {
        A = S;
        JSONObject Jobj = new JSONObject();
        // 인증 키 생성하고 이메일 인증 보낸다.
        try {
            Jobj.put("id", 1);
            Jobj.put("email", U.getEmail());
            Jobj.put("password", Encode(U.getPassword(), 0, "512"));
            runTask("POST", Encode(URLEncoder.encode(Jobj.toString(), "UTF-8"), 1, "user-signup-process"), -1);
        } catch (Exception e) {
            S.onResult(false);
        }
    }

    // 프로필 불러오기
    public Object[] GetProf(String res, Activity p) {
        if (A == null) {
            A = p;
            runTask("GET", null, 2);
            return null;
        } else {
            Object[] objs = new Object[2];
            try {
                JSONObject Jobj = new JSONObject(res);
                String prof = Decode(Jobj.getString("prof"), "getting-user-profile");
                JSONObject obj1 = new JSONObject(prof);
                ProfileObj P = new ProfileObj();
                P.setNick(obj1.getString(ProfParams[0]));
                P.setIcon(ByteToBitmap(obj1.getString(ProfParams[1])));
                P.setComm(obj1.getString(ProfParams[3]));
                P.setBirth(obj1.getString(ProfParams[4]));
                P.setSex((obj1.getInt(ProfParams[5]) != 0));
                P.setFavos(obj1.getString(ProfParams[6]).split(","));
                P.setRegion(obj1.getString(ProfParams[7]));
                objs[0] = P;

                MoimObj[] M = GetMoimList(Decode(Jobj.getString("moim"), "getting-usermoim-list"));
                objs[1] = M;
                return objs;
            } catch (Exception e) {
                return objs;
            }
        }
    }

    // 프로필 설정하기
    public void SetProf(ProfileObj obj, ProfileSettings p) {
        A = p;
        JSONObject Jobj = new JSONObject();
        try {
            Jobj.put(ProfParams[0], obj.getNick());
            Jobj.put(ProfParams[1], BitmapToByte(obj.getIcon()));
            Jobj.put(ProfParams[2], "user/" + RClient.getID() + "/profile.png");
            Jobj.put(ProfParams[3], obj.getComm());
            Jobj.put(ProfParams[4], obj.getBirth());
            Jobj.put(ProfParams[5], obj.getSex());
            Jobj.put(ProfParams[6], obj.getFavos()[0] + "," + obj.getFavos()[1] + "," + obj.getFavos()[2]);
            Jobj.put(ProfParams[7], obj.getRegion());
            runTask("POST", Encode(URLEncoder.encode(Jobj.toString(), "UTF-8"), 1, "setting-user-profile"), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 모임 리스트 불러오기
    @Nullable
    private MoimObj[] GetMoimList(String res) {
        try {
            JSONArray Jarray = new JSONArray(res);
            MoimObj[] Ms = new MoimObj[Jarray.length()];
            for (int i = 0; i < Jarray.length(); i++) {
                Ms[i] = new MoimObj();
                JSONObject Jobj = Jarray.getJSONObject(i);
                Ms[i].setId(Jobj.getInt("id"));
                Ms[i].setTitle(Jobj.getString(MoimParams[0]));
                Ms[i].setComm(Jobj.getString(MoimParams[1]));
                Ms[i].setBack(ByteToBitmap(Jobj.getString(MoimParams[3])));
                Ms[i].setMem_num(Ms[i].getMembers().size());
            }
            return Ms;
        } catch (Exception e) {
            return null;
        }
    }

    // 모임 멤버 불러오기
    @Nullable
    private ArrayList<MoimObj.Member> SetMember(String str) {
        try {
            JSONArray jarr = new JSONArray(str);
            int i = 0;
            ArrayList<MoimObj.Member> array = new ArrayList<>();
            while (!jarr.isNull(i)) {
                JSONObject jobj = jarr.getJSONObject(i);
                MoimObj.Member mem = new MoimObj.Member();
                mem.setMem_id(jobj.getString("id"));
                mem.setMem_nick(jobj.getString("nick"));
                mem.setMem_comm(jobj.getString("comm"));
                mem.setMem_icon(ByteToBitmap(jobj.getString("icon")));
                mem.setMem_lev(jobj.getInt("lev"));
                i++;
                array.add(mem);
            }
            return array;
        } catch (JSONException e) {
            return null;
        }
    }

    // 모임 정보 불러오기
    public MoimObj GetMoim(String res, Activity m) {
        if (A == null) {
            A = m;
            runTask("GET", null, 3);
            return null;
        } else {
            String[] MoimParams = {"title", "onecomm", "content", "back", "limit", "regi", "cate", "mem", "board", "isJoin"};
            MoimObj M = new MoimObj();
            try {
                JSONObject Jobj = new JSONObject(res);
                M.setTitle(Jobj.getString(MoimParams[0]));
                M.setComm(Jobj.getString(MoimParams[1]));
                M.setContent(Jobj.getString(MoimParams[2]));
                M.setBack(ByteToBitmap(Jobj.getString(MoimParams[3])));
                M.setLimit(Jobj.getInt(MoimParams[4]));
                M.setReg(Jobj.getString(MoimParams[5]));
                M.setCate(Jobj.getString(MoimParams[6]));
                M.setMembers(SetMember(Jobj.getString(MoimParams[7])));
                M.setMem_num(M.getMembers().size());

                // 게시판
                if (Jobj.getString(MoimParams[8]) != null) {
                    JSONArray Jarr = new JSONArray((Jobj.getString(MoimParams[8])));
                    BoardObj[] board = new BoardObj[Jarr.length()];
                    for (int i = 0; i < board.length; i++) {
                        JSONObject j = Jarr.getJSONObject(i);
                        board[i].setB_Title(j.getString("title"));
                        board[i].setB_Content(j.getString("content"));
                        board[i].setB_Writer(j.getString("writer"));
                        board[i].setWdate(j.getString("wtime"));
                    }
                    M.setBoard(board);
                }

                // 모임장, 가입 여부
                for (MoimObj.Member i : M.getMembers()) {
                    if (i.getMem_id().equals(RClient.getID()) && i.getMem_lev() == 1)   M.cap = true;
                }
                M.isJoin = Jobj.getBoolean("isJoin");
                return M;
            } catch (Exception e) {
                return null;
            }
        }
    }

    // 모임 설정
    public void SetMoim(MoimObj mObj, Moim M) {
        A = M;
        try {
            JSONObject Jobj = new JSONObject();
            Jobj.put(MoimParams[0], mObj.getTitle());
            Jobj.put(MoimParams[1], mObj.getComm());
            Jobj.put(MoimParams[2], mObj.getContent());
            Jobj.put(MoimParams[3], BitmapToByte(mObj.getBack()));
            Jobj.put(MoimParams[4], mObj.getLimit());
            Jobj.put(MoimParams[5], mObj.getReg());
            Jobj.put(MoimParams[6], mObj.getCate());
            Jobj.put(MoimParams[7], mObj.getAgel());
            Jobj.put(MoimParams[8], mObj.getAgeh());
            runTask("POST", Encode(URLEncoder.encode(Jobj.toString(), "UTF-8"), 1, "setting-moim-objects"), -1);
        } catch (Exception e) {
            M.onResult(false);
        }
    }

    // 게시판 설정
    public void setBoard(BoardObj[] b) {
        final String[] BParams = {"title", "writer", "content", "date", "comm"};
        try {
            JSONArray jarr = new JSONArray();
            int i = 0;
            for (BoardObj bobj : b) {
                JSONObject jobj = new JSONObject();
                jobj.put(BParams[0], bobj.getB_Title());
                jobj.put(BParams[1], bobj.getB_Writer());
                jobj.put(BParams[2], bobj.getB_Content());
                jobj.put(BParams[3], bobj.getWdate());
                jobj.put(BParams[4], bobj.getCommenters());
                jarr.put(i++, jobj);
            }
            runTask("POST", Encode(URLEncoder.encode(jarr.toString(), "UTF-8"), 1, "setting-board-object"), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 게시판 불러오기
    public BoardObj[] GetBoard(String res, MoimInfo M) {
        if (A == null) {
            A = M;
            runTask("GET", null, 4);
            return null;
        } else {
            final String[] BParams = {"title", "writer", "content", "date", "comm"};
            try {
                JSONArray jarr = new JSONArray(res);
                BoardObj[] b = new BoardObj[jarr.length()];
                for (int i = 0; i < b.length; i++) {
                    JSONObject jobj = jarr.getJSONObject(i);
                    b[i] = new BoardObj();
                    b[i].setB_Title(jobj.getString(BParams[0]));
                    b[i].setB_Writer(jobj.getString(BParams[1]));
                    b[i].setB_Content(jobj.getString(BParams[2]));
                    b[i].setWdate(jobj.getString(BParams[3]));
                    b[i].setCommenters((JSONArray) jobj.get(BParams[4]));
                }
                return b;
            } catch (Exception e) {
                return null;
            }
        }
    }

    // 메인화면에 띄울 모임들 불러오기
    public MainObj GetMain(String res, Activity M) {
        if (A == null) {
            A = M;
            runTask("GET", null, 5);
            return null;
        } else {
            try {
                JSONArray jarr = new JSONArray(res);
                int[] ids = new int[jarr.length()];
                Bitmap[] imgs = new Bitmap[jarr.length()];
                String[] titles = new String[jarr.length()], intros = new String[jarr.length()];
                for (int i = 0; i < jarr.length(); i++) {
                    JSONObject Jobj = jarr.getJSONObject(i);
                    ids[i] = Jobj.getInt("id");
                    imgs[i] = ByteToBitmap(Jobj.getString("icon"));
                    titles[i] = Jobj.getString("title");
                    intros[i] = Jobj.getString("onecomm");
                }
                return new MainObj(ids, imgs, titles, intros);
            } catch (JSONException e) {
                return null;
            }
        }
    }

    public void ask(String title, String content, Contact c) {
        A = c;
        JSONObject jobj = new JSONObject();
        try {
            //jobj.put("id", getSession());
            jobj.put("title", title);
            jobj.put("content", content);
            runTask("POST", Encode(URLEncoder.encode(jobj.toString(), "UTF-8"), 1, "ask-question-fail"), -1);
        } catch (Exception e) {
            c.onResult(false);
        }
    }

    @Nullable
    public Bitmap ByteToBitmap(String str) {
        try {
            byte[] bytearr = Base64.decode(str);
            return BitmapFactory.decodeByteArray(bytearr, 0, bytearr.length);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public String BitmapToByte(Bitmap img) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return Base64.toBase64String(stream.toByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    // 암호화
    @Nullable
    private String Encode(String key, int id, String hash) {
        try {
            if (id == 0) {
                DigestKeccak dcoder = new DigestKeccak(Integer.parseInt(hash));
                dcoder.update(key.getBytes());
                byte[] digest = dcoder.digest();
                return org.bouncycastle.util.encoders.Hex.toHexString(digest);
            } else if (id == 1) {
                acoder = new AES256Util(hash);
                return acoder.aesEncode(key);
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 복호화
    @Nullable
    private String Decode(String key, String hash) {
        try {
            acoder = new AES256Util(hash);
            return acoder.aesDecode(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private class RESTTask extends AsyncTask<String, Void, Object> {
        public int id;

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Object result = null;
            try {
                switch (id) {
                    // 단순 POST
                    case -1:
                        result = o;
                        break;

                    // 프로필 불러오기
                    case 2:
                        result = GetProf(o.toString(), null);
                        break;

                    // 모임 정보 불러오기
                    case 3:
                        result = GetMoim(Decode(o.toString(), "getting-moim-info"), null);
                        break;

                    // 게시판 불러오기
                    case 4:
                        result = GetBoard(Decode(o.toString(), "getting-moim-boards"), null);
                        break;

                    // 메인 불러오기
                    case 5:
                        result = GetMain(Decode(o.toString(), "getting-main-objects"), null);
                        break;

                    default:
                        break;
                }
                Class c = A.getClass();
                Method mlist [] = c.getDeclaredMethods();
                for (Method m : mlist){
                    if (m.getName().equals("onResult")) m.invoke(A, result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Object doInBackground(String[] objects) {
            Object obj = null;
            switch (objects[0]) {
                case "GET":
                    obj = RClient.GET();
                    break;
                case "POST":
                    obj = RClient.POST(objects[1]);
                    break;
                default:
                    break;
            }
            return obj;
        }
    }
}