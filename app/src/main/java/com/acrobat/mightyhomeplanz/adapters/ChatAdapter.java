package com.acrobat.mightyhomeplanz.adapters;

import java.util.ArrayList;

import com.acrobat.mightyhomeplanz.R;
import com.acrobat.mightyhomeplanz.entities.ProjectModel;
import com.acrobat.widgets.CustomTextView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatAdapter extends BaseAdapter implements OnClickListener {

    Context context;

    static ArrayList<ProjectModel> list;

    public ChatAdapter(Context context, ArrayList<ProjectModel> list) {
        super();
        this.context = context;
        ChatAdapter.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
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
        ProjectModel data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.row_chat, null);
            holder = new ViewHolder();

            holder.tvFileName = (CustomTextView) convertView.findViewById(R.id.tvName_rowChat);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        CustomTextView tvFileName;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
