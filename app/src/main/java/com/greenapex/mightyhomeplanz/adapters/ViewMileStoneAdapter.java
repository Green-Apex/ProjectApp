package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.greenapex.R;
import com.greenapex.Request.models.JobDOCModel;
import com.greenapex.widgets.CustomTextView;

import java.util.ArrayList;

public class ViewMileStoneAdapter extends BaseAdapter implements OnClickListener {

    Context context;

    static ArrayList<JobDOCModel> list;

    public ViewMileStoneAdapter(Context context, ArrayList<JobDOCModel> list) {
        super();
        this.context = context;
        ViewMileStoneAdapter.list = list;
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
        JobDOCModel data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.row_view_docs, null);
            holder = new ViewHolder();

            holder.tvFileName = (CustomTextView) convertView.findViewById(R.id.tvName_rowViewDocs);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvFileName.setText(data.getDocTitle());

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
