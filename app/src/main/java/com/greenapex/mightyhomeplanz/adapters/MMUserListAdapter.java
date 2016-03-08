package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.RequestManager;
import com.greenapex.R;
import com.greenapex.response.models.MMListResponse;
import com.greenapex.widgets.CustomTextView;

import java.util.ArrayList;

public class MMUserListAdapter extends BaseAdapter implements OnClickListener {

    private final RequestManager imageLoader;
    Context context;

    static ArrayList<MMListResponse> list;


    public MMUserListAdapter(Context context, ArrayList<MMListResponse> list, RequestManager imageLoader) {
        super();
        this.context = context;
        MMUserListAdapter.list = list;
        this.imageLoader = imageLoader;
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
        final MMListResponse data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.activitiy_mmlist_item, null);
            holder = new ViewHolder();

            holder.customTxtName = (CustomTextView) convertView.findViewById(R.id.customTxtName);
            holder.customTxtTotalProject = (CustomTextView) convertView.findViewById(R.id.customTxtTotalProject);
            holder.customTxtJobCost = (CustomTextView) convertView.findViewById(R.id.customTxtJobCost);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        loadData(holder, data);


        return convertView;
    }

    private void loadData(ViewHolder holder, MMListResponse data) {
        holder.customTxtName.setText(data.getFname() + " " + data.getLname());
        holder.customTxtTotalProject.setText("Total Projects: " + data.getTotalJobs());
        holder.customTxtJobCost.setText("Job Cost: " + data.getJobCost());

    }

    static class ViewHolder {
        CustomTextView customTxtName;
        CustomTextView customTxtTotalProject;
        CustomTextView customTxtJobCost;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
