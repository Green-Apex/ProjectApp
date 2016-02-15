package com.greenapex.mightyhomeplanz.adapters;

import java.util.ArrayList;


import com.greenapex.mightyhomeplanz.entities.ProjectModel;
import com.greenapex.widgets.CustomTextView;
import com.greenapex.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ViewDocsAdapter extends BaseAdapter implements OnClickListener {

    Context context;

    static ArrayList<ProjectModel> list;

    public ViewDocsAdapter(Context context, ArrayList<ProjectModel> list) {
        super();
        this.context = context;
        ViewDocsAdapter.list = list;
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
            convertView = layoutInflator.inflate(R.layout.row_view_docs, null);
            holder = new ViewHolder();

            holder.tvFileName = (CustomTextView) convertView.findViewById(R.id.tvName_rowViewDocs);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvFileName.setText("Quote_v" + (position + 1) + ".pdf");

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
