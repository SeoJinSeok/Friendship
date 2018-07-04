package com.friendship.Objects;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.friendship.R;
import com.friendship.REST.ObjManager;

import java.util.ArrayList;


public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ChatObj> chatList = new ArrayList<>();

    public ListViewAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_chat, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = convertView.findViewById(R.id.ochat_icon);
        TextView nameTextView = convertView.findViewById(R.id.ochat_name);
        TextView contentTextView = convertView.findViewById(R.id.ochat_content);
        TextView myTextView = convertView.findViewById(R.id.mychat_content);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ChatObj chatItem = chatList.get(i);
        ObjManager oMgr = new ObjManager("");
        if (chatItem.getIcon() != null) {
            // 아이템 내 각 위젯에 데이터 반영
            iconImageView.setBackground(new BitmapDrawable(context.getResources(), oMgr.ByteToBitmap(chatItem.getIcon())));
            nameTextView.setText(chatItem.getNick());
            contentTextView.setText(chatItem.getMsg());
            myTextView.setBackground(null);
        } else {
            nameTextView.setBackground(null);
            contentTextView.setBackground(null);
            myTextView.setText(chatItem.getMsg());
        }
        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return chatList.get(i);
    }

    public void addItem(String icon, String nick, String msg, int id) {
        ChatObj item;

        // 상대방
        if (id == 0) item = new ChatObj(icon, nick, msg);

            // 내꺼
        else item = new ChatObj(null, null, msg);
        chatList.add(item);
    }
}
