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
import com.greenapex.mightyhomeplanz.entities.JobModel;
import com.greenapex.widgets.CustomRoundedImageView;
import com.greenapex.widgets.CustomTextView;

import java.util.ArrayList;

public class ProjectsAdapter extends BaseAdapter implements OnClickListener {

    private final RequestManager imageLoader;
    Context context;

    static ArrayList<JobModel> list;


    public ProjectsAdapter(Context context, ArrayList<JobModel> list, RequestManager imageLoader) {
        super();
        this.context = context;
        ProjectsAdapter.list = list;
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
        final JobModel data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.row_home, null);
            holder = new ViewHolder();

            holder.tvStatus = (CustomTextView) convertView.findViewById(R.id.tvStatus);
            holder.customTxtJobName = (CustomTextView) convertView.findViewById(R.id.customTxtJobName);
            holder.customTxtStartDate = (CustomTextView) convertView.findViewById(R.id.customTxtStartDate);
            holder.customRoundedImageView = (CustomRoundedImageView) convertView.findViewById(R.id.customRoundedImageView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        loadData(holder, data);


        return convertView;
    }

    private void loadData(ViewHolder holder, JobModel data) {
        if (data.getJobStatus().equalsIgnoreCase(Constants.NEW))
            holder.tvStatus.setText(Constants.NEW);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.ASSIGNED))
            holder.tvStatus.setText(Constants.ASSIGNED);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.COMPLETED))
            holder.tvStatus.setText(Constants.COMPLETED);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.INPROGRESS))
            holder.tvStatus.setText(Constants.INPROGRESS);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.REJECTED))
            holder.tvStatus.setText(Constants.REJECTED);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.UNDER_ESTIMATION))
            holder.tvStatus.setText(Constants.UNDERESTIMATION);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.REQUESTED_FOR_PAYMENT))
            holder.tvStatus.setText(Constants.REQUESTED_FOR_PAYMENT);
        else if (data.getJobStatus().equalsIgnoreCase(Constants.OWNER_REVIEW)) {
            holder.tvStatus.setText(Constants.OWNERREVIEW);
        }
        holder.customTxtJobName.setText(data.getJobTitle());
        holder.customTxtStartDate.setText(data.getJobCreationDate());
        if (data.getImages().size() > 0) {
            imageLoader.load(Constants.BaseImageDomain+data.getImages().get(0)).asBitmap().centerCrop().into(holder.customRoundedImageView);
        }
    }

    static class ViewHolder {
        CustomTextView tvStatus;
        CustomRoundedImageView customRoundedImageView;
        CustomTextView customTxtJobName;
        CustomTextView customTxtStartDate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
