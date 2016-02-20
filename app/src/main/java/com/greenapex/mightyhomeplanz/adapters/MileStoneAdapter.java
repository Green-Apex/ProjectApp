package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.RequestManager;
import com.greenapex.R;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.widgets.CustomTextView;

import java.util.ArrayList;

public class MileStoneAdapter extends BaseAdapter implements OnClickListener {

    private final RequestManager imageLoader;
    Context context;

    static ArrayList<MileStoneModel> list;


    public MileStoneAdapter(Context context, ArrayList<MileStoneModel> list, RequestManager imageLoader) {
        super();
        this.context = context;
        MileStoneAdapter.list = list;
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
        final MileStoneModel data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.milestone_item, null);
            holder = new ViewHolder();

            holder.milestonetitle = (CustomTextView) convertView.findViewById(R.id.milestonetitle);
            holder.milestonestatus = (CustomTextView) convertView.findViewById(R.id.milestonestatus);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        loadData(holder, data);


        return convertView;
    }

    private void loadData(ViewHolder holder, MileStoneModel data) {
        holder.milestonetitle.setText(data.getTitle());
        if (data.getStatus().isInProgress())
            holder.milestonestatus.setText("In Progress");
        if (data.getStatus().isCompleted())
            holder.milestonestatus.setText("Completed");
        if (data.getStatus().isInPreview())
            holder.milestonestatus.setText("In Preview");
    }

    static class ViewHolder {
        CustomTextView milestonetitle;
        CustomTextView milestonestatus;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
