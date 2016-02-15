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

public class ProjectsAdapter extends BaseAdapter implements OnClickListener {

    Context context;

    static ArrayList<ProjectModel> list;
    int tab_position;

    public ProjectsAdapter(Context context, ArrayList<ProjectModel> list, int position) {
        super();
        this.context = context;
        ProjectsAdapter.list = list;
        this.tab_position = position;
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
            convertView = layoutInflator.inflate(R.layout.row_home, null);
            holder = new ViewHolder();

            holder.tvStatus = (CustomTextView) convertView.findViewById(R.id.tvStatus_rowHome);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (tab_position == 0)
            holder.tvStatus.setText("Estimation");
        else if (tab_position == 1)
            holder.tvStatus.setText("Active");
        else if (tab_position == 2)
            holder.tvStatus.setText("Completed");

        return convertView;
    }

    static class ViewHolder {
        CustomTextView tvStatus;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
