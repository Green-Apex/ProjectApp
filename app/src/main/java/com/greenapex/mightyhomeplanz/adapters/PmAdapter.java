package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.greenapex.R;
import com.greenapex.mightyhomeplanz.entities.JobOwnerModel;
import com.greenapex.widgets.CustomRoundedImageView;
import com.greenapex.widgets.CustomTextView;

import java.util.ArrayList;

public class PmAdapter extends BaseAdapter implements OnClickListener {

    Context context;

    static ArrayList<JobOwnerModel> list;


    public PmAdapter(Context context, ArrayList<JobOwnerModel> list) {
        super();
        this.context = context;
        PmAdapter.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public JobOwnerModel getItem(int position) {
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
        final JobOwnerModel data = getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.row_job_owner, null);
            holder = new ViewHolder();

            holder.customTxtEmail = (CustomTextView) convertView.findViewById(R.id.customTxtEmail);
            holder.customTxtContactNo = (CustomTextView) convertView.findViewById(R.id.customTxtContactNo);
            holder.customTxtJoName = (CustomTextView) convertView.findViewById(R.id.customTxtJoName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        loadData(holder, data);


        return convertView;
    }

    private void loadData(ViewHolder holder, JobOwnerModel data) {

        holder.customTxtEmail.setText(data.getEmail());
        holder.customTxtContactNo.setText(data.getContactNo());
        holder.customTxtJoName.setText(data.getFname() + " " + data.getLname());

    }

    static class ViewHolder {
        CustomTextView customTxtEmail;
        CustomTextView customTxtContactNo;
        CustomTextView customTxtJoName;
        CustomRoundedImageView customRoundedImageView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
