package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.RequestManager;
import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.response.models.ChatResponse;
import com.greenapex.widgets.CustomRoundedImageView;
import com.greenapex.widgets.CustomTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends BaseAdapter implements OnClickListener {

    private final RequestManager imageLoader;
    Context context;

    static ArrayList<ChatResponse> list;
    private static final int ChatMessage = 0;
    private static final int AlertNotification = 1;

    public ChatAdapter(Context context, ArrayList<ChatResponse> list, RequestManager imageLoader) {
        super();
        this.context = context;
        ChatAdapter.list = list;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getActivityType().equalsIgnoreCase(Constants.CHAT)) {
            return ChatMessage;
        } else {
            return AlertNotification;
        }
//        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        ChatResponse data = list.get(position);
        int ViewType = getItemViewType(position);
        if (convertView == null) {
            switch (ViewType) {
                case ChatMessage: {
                    LayoutInflater layoutInflator = LayoutInflater.from(context);
                    convertView = layoutInflator.inflate(R.layout.row_chat, null);
                    holder = new ViewHolder();

                    holder.tvFileName = (CustomTextView) convertView.findViewById(R.id.tvName_rowChat);
                    holder.tvMessage_rowChat = (CustomTextView) convertView.findViewById(R.id.tvMessage_rowChat);
                    holder.tvTime_rowChat = (CustomTextView) convertView.findViewById(R.id.tvTime_rowChat);
                    holder.tvName_rowChat = (CustomTextView) convertView.findViewById(R.id.tvName_rowChat);
                    holder.ivUserPic_rowChat = (CustomRoundedImageView) convertView.findViewById(R.id.ivUserPic_rowChat);
                    convertView.setTag(holder);
                    break;
                }
                case AlertNotification: {
                    LayoutInflater layoutInflator = LayoutInflater.from(context);
                    convertView = layoutInflator.inflate(R.layout.chat_alert_notification_item, null);
                    holder = new ViewHolder();
                    holder.textAlertMessage = (CustomTextView) convertView.findViewById(R.id.textAlertMessage);
                    convertView.setTag(holder);
                    break;
                }
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (ViewType) {
            case ChatMessage: {
                loadChatData(holder, data);
                break;
            }
            case AlertNotification: {
                loadAlertNotification(holder, data);
                break;
            }
        }
        return convertView;
    }

    private void loadAlertNotification(ViewHolder holder, ChatResponse data) {
        holder.textAlertMessage.setText(data.getActivityType() + " at " + getformatedDate(data.getDatetimestamp()));
    }

    private void loadChatData(ViewHolder holder, ChatResponse data) {
        holder.tvMessage_rowChat.setText(data.getDesc());
        holder.tvTime_rowChat.setText(getformatedDate(data.getDatetimestamp()));
        holder.tvName_rowChat.setText(data.getUserName());
        imageLoader.load(Constants.BaseImageDomain + data.getProfilePic()).into(holder.ivUserPic_rowChat);
    }

    static class ViewHolder {
        CustomTextView tvFileName;
        CustomTextView tvMessage_rowChat;
        CustomTextView tvTime_rowChat;
        CustomTextView textAlertMessage;
        CustomRoundedImageView ivUserPic_rowChat;
        CustomTextView tvName_rowChat;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private String getformatedDate(String date) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = format.parse(date);
            SimpleDateFormat newformat = new SimpleDateFormat(" MMM F, yyyy hh:mm a");
            String formatedDate = newformat.format(newDate);
            return formatedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
