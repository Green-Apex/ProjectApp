package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.entities.JobModel;
import com.greenapex.widgets.CustomTextView;

import java.util.ArrayList;

public class ProjectsAdapter extends BaseAdapter implements OnClickListener {

    Context context;

    static ArrayList<JobModel> list;


    public ProjectsAdapter(Context context, ArrayList<JobModel> list) {
        super();
        this.context = context;
        ProjectsAdapter.list = list;
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
        final JobModel data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.row_home, null);
            holder = new ViewHolder();

            holder.tvStatus = (CustomTextView) convertView.findViewById(R.id.tvStatus_rowHome);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (data.getJobStatus().isNew())
            holder.tvStatus.setText(Constants.NEW);
        else if (data.getJobStatus().isAssigned())
            holder.tvStatus.setText(Constants.ASSIGNED);
        else if (data.getJobStatus().isCompleted())
            holder.tvStatus.setText(Constants.COMPLETED);
        else if (data.getJobStatus().isInProgress())
            holder.tvStatus.setText(Constants.INPROGRESS);
        else if (data.getJobStatus().isRejected())
            holder.tvStatus.setText(Constants.REJECTED);
        else if (data.getJobStatus().isUnderEstimation())
            holder.tvStatus.setText(Constants.UNDERESTIMATION);
        else if (data.getJobStatus().isRequestedForPayment())
            holder.tvStatus.setText(Constants.REQUESTED_FOR_PAYMENT);


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
