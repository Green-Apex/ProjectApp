package com.greenapex.mightyhomeplanz.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.greenapex.R;
import com.greenapex.Request.models.JobDOCModel;
import com.greenapex.Utils.Constants;
import com.greenapex.response.models.UserResponse;
import com.greenapex.webservice.DeleteDocWebservice;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ViewDocsAdapter extends BaseAdapter implements OnClickListener {

    private final UserResponse userGson;
    private String selectedJobID = "";
    Context context;

    static ArrayList<JobDOCModel> list;

    public ViewDocsAdapter(Context context, ArrayList<JobDOCModel> list, UserResponse userGson, String selectedJobID) {
        super();
        this.context = context;
        ViewDocsAdapter.list = list;
        this.userGson = userGson;
        this.selectedJobID = selectedJobID;
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
        final JobDOCModel data = list.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflator = LayoutInflater.from(context);
            convertView = layoutInflator.inflate(R.layout.row_view_docs, null);
            holder = new ViewHolder();

            holder.tvFileName = (CustomTextView) convertView.findViewById(R.id.tvName_rowViewDocs);
            holder.btnDeleteDoc_rowViewDocs = (ImageButton) convertView.findViewById(R.id.btnDeleteDoc_rowViewDocs);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvFileName.setText(data.getDocTitle());
        holder.btnDeleteDoc_rowViewDocs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDocWebservice deleteDocWebservice = new DeleteDocWebservice(new DeleteDocWebservice.DeleteDocWebserviceHandler() {
                    @Override
                    public void deleteDocWebserviceStart() {

                    }

                    @Override
                    public void deleteDocWebserviceSucessful(String response, String message) {
                        Log.d(this.getClass().getSimpleName(), message);
                        if (response.equalsIgnoreCase("true")) ;
                        list.remove(data);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void deleteDocWebserviceFailedWithMessage(String message) {
                        Log.d(this.getClass().getSimpleName(), message);
                    }
                }, context);

                try {
                    JSONObject params = new JSONObject();
                    params.put(Constants.USERID, userGson.getUserID());
                    params.put(Constants.JOBID, selectedJobID);
                    params.put(Constants.DOCID, data.getDocID());
                    deleteDocWebservice.callService(params);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }

    static class ViewHolder {
        CustomTextView tvFileName;
        ImageButton btnDeleteDoc_rowViewDocs;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
