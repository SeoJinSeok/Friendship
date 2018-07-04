package com.friendship.Objects;

import android.graphics.Bitmap;

import java.io.Serializable;

public class MoimObj implements Serializable {
    private boolean isJoin;  // 모임장 여부
    private String cate, title, comm, content, reg;   // 모임 카테고리, 제목, 한 줄 소개, 내용, 지역
    private Bitmap back;  // 모임 아이콘, 배경
    private int id, limit, agel, ageh;    //  모임 id, 인원, 나이 제한
    private int mem_num, attend;    // 출석 수
    private Member[] members;

    public boolean getCap() {
        return isJoin;
    }

    public void setCap(boolean isJoin) {
        this.isJoin = isJoin;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public int getAgel() {
        return agel;
    }

    public void setAgel(int agel) {
        this.agel = agel;
    }

    public int getAgeh() {
        return ageh;
    }

    public void setAgeh(int ageh) {
        this.ageh = ageh;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public Bitmap getBack() {
        return back;
    }

    public void setBack(Bitmap back) {
        this.back = back;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getAttend() {
        return attend;
    }

    public void setAttend(int attend) {
        this.attend = attend;
    }

    public int getMem_num() {
        return mem_num;
    }

    public void setMem_num(int mem_num) {
        this.mem_num = mem_num;
    }

    public Member[] getMembers() {
        return members;
    }

    public void setMembers(Member[] members) {
        this.members = members;
    }

    public static class Member {
        private String mem_id, mem_nick, mem_comm;
        private Bitmap mem_icon;
        private int mem_lev;

        public String getMem_id() {
            return mem_id;
        }

        public void setMem_id(String mem_id) {
            this.mem_id = mem_id;
        }

        public String getMem_nick() {
            return mem_nick;
        }

        public void setMem_nick(String mem_nick) {
            this.mem_nick = mem_nick;
        }

        public String getMem_comm() {
            return mem_comm;
        }

        public void setMem_comm(String mem_comm) {
            this.mem_comm = mem_comm;
        }

        public Bitmap getMem_icon() {
            return mem_icon;
        }

        public void setMem_icon(Bitmap mem_icon) {
            this.mem_icon = mem_icon;
        }

        public int getMem_lev() {
            return mem_lev;
        }

        public void setMem_lev(int mem_lev) {
            this.mem_lev = mem_lev;
        }
    }
}
