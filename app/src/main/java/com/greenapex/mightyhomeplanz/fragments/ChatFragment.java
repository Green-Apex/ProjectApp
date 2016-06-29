package com.greenapex.mightyhomeplanz.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.adapters.ChatAdapter;
import com.greenapex.response.models.ChatResponse;
import com.greenapex.webservice.GetJobChatWebservice;
import com.greenapex.webservice.SendChatMessageWebservice;
import com.greenapex.widgets.CustomSwipeRefreshLayout;
import com.greenapex.widgets.CustomSwipeRefreshLayout.OnChildScrollUpListener;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ChatFragment extends BaseFragment implements OnClickListener {
    View view;
    Activity activity;

    CustomSwipeRefreshLayout swipeRefresh;
    ListView lv;
    CustomTextView tvSend;

    ArrayList<ChatResponse> list = new ArrayList<ChatResponse>();
    ChatAdapter adapter;
    private String selectJobID;
    private ProgressDialog mProgressDialog;
    private EditText etMessage_fragChat;
    private CustomTextView customTxtNoData;

    public static Fragment newInstance(String jobID) {
        ChatFragment f = new ChatFragment();
        Bundle params = new Bundle();
        params.putString(Constants.JOBID, jobID);
        f.setArguments(params);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_chat, container, false);
        selectJobID = getArguments().getString(Constants.JOBID, "");
        activity = getActivity();

        init();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllChatMessage();
    }

    private void getAllChatMessage() {
        GetJobChatWebservice getJobChatWebservice = new GetJobChatWebservice(new GetJobChatWebservice.GetJobChatWebserviceHandler() {
            @Override
            public void getJobChatWebserviceStart() {
                mProgressDialog.setMessage("loading...");
                mProgressDialog.show();
            }

            @Override
            public void getJobChatWebserviceSucessful(String response, String message) {
                mProgressDialog.dismiss();
                swipeRefresh.setRefreshing(false);
                showToast(message);
                showLog(response);
                if (response != null && response.length() > 0) {
                    try {
                        JSONArray mResponse = new JSONArray(response);
                        if (mResponse.length() > 0) {
                            for (int i = 0; i < mResponse.length(); i++) {
                                ChatResponse chatResponse = getGson().fromJson(mResponse.get(i).toString(), ChatResponse.class);
                                list.add(chatResponse);
                            }
                            adapter = new ChatAdapter(activity, list,getImageLoader());
                            lv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            customTxtNoData.setText("");
                            customTxtNoData.setVisibility(View.GONE);
                        } else {
                            if (list.size() <= 0) {
                                customTxtNoData.setText("No messages avialable");
                                customTxtNoData.setVisibility(View.VISIBLE);
                            } else {
                                customTxtNoData.setText("");
                                customTxtNoData.setVisibility(View.GONE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (list.size() <= 0) {
                        customTxtNoData.setText("No messages avialable");
                        customTxtNoData.setVisibility(View.VISIBLE);
                    } else {
                        customTxtNoData.setText("");
                        customTxtNoData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void getJobChatWebserviceFailedWithMessage(String message) {
                showToast(message);
                swipeRefresh.setRefreshing(false);
                mProgressDialog.dismiss();
            }
        }, getActivity());

        JSONObject params = new JSONObject();
        try {
            params.put(Constants.JOBID, selectJobID);
            params.put(Constants.STARTINDEX, 1);
            params.put(Constants.ENDINDEX, 20);
            getJobChatWebservice.callService(params, Constants.METHOD_GET);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        mProgressDialog = new ProgressDialog(getActivity());
        tvSend = (CustomTextView) view.findViewById(R.id.tvSend_fragChat);
        tvSend.setOnClickListener(this);
        customTxtNoData = (CustomTextView) view.findViewById(R.id.customTxtNoData);
        etMessage_fragChat = (EditText) view.findViewById(R.id.etMessage_fragChat);
        swipeRefresh = (CustomSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_fragChat);
        swipeRefresh.setColorSchemeResources(R.color.app_color, R.color.purple);
        swipeRefresh.setProgressViewOffset(false, 0, 100);

        lv = (ListView) view.findViewById(R.id.lv_fragChat);

        swipeRefresh.setOnChildScrollUpListener(new OnChildScrollUpListener() {
            @Override
            public boolean canChildScrollUp() {
                return lv.getFirstVisiblePosition() > 0 || lv.getChildAt(0) == null || lv.getChildAt(0).getTop() < 0;
            }
        });

//        loadData();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

        });

        swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getAllChatMessage();
//                loadData();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.tvSend_fragChat:
                if (isValid()) {
                    final String msg = etMessage_fragChat.getText().toString();
                    SendChatMessageWebservice sendChatMessageWebservice = new SendChatMessageWebservice(new SendChatMessageWebservice.SendChatMessageWebserviceHandler() {
                        @Override
                        public void sendChatMessageWebserviceStart() {

                        }

                        @Override
                        public void sendChatMessageWebserviceSucessful(String response, String message) {
                            showToast(message);
                            try {
                                ChatResponse chatResponse = getGson().fromJson(response, ChatResponse.class);
                                list.add(chatResponse);
                                adapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void sendChatMessageWebserviceFailedWithMessage(String message) {

                            showToast(msg);
                        }
                    }, getActivity());

                    try {
                        ChatResponse chatResponse = new ChatResponse();
                        chatResponse.setActivityType(Constants.CHAT);
                        chatResponse.setDesc(msg);
                        chatResponse.setJobID(selectJobID);
                        chatResponse.setUserID(getUserGson().getUserID());
                        chatResponse.setRole(getUserGson().getRole());
                        JSONObject params = new JSONObject(chatResponse.toString());
                        sendChatMessageWebservice.callService(params, Constants.METHOD_POST);
                        etMessage_fragChat.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
                break;

        }
    }

    private boolean isValid() {
        if (etMessage_fragChat.getText().length() <= 0) {
            showToast("Please enter some message before sending.");
            etMessage_fragChat.requestFocus();
            return false;

        }
        return true;
    }

//    public void loadData() {
//        for (int i = 0; i < 3; i++) {
//            ProjectModel data = new ProjectModel();
//            data.setId("" + (i + 1));
//            list.add(data);
//        }
//    }

}
