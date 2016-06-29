package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.mightyhomeplanz.entities.MileStoneStatusModel;
import com.greenapex.webservice.ChangeMileStoneStatusWebservice;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONObject;

/**
 * Created by admin on 04-Mar-16.by ${COMPUTERNAME}
 */
public class ShowMileStone extends BaseActivity implements View.OnClickListener {
    private ShowMileStone activity;
    private ImageButton btnClose;
    private CustomTextView tvSubmit;
    private CustomTextView tvClear;
    private CustomTextView etMilestoneTitle_AddMilestone;
    private CustomTextView etMilestoneDesc_AddMilestone;
    private CustomTextView etAmount_AddMilestone;
    private Spinner spStatus_AddMilestone;
    private MileStoneModel mileStoneModel;
    private CustomTextView tvSubmit_AddMilestone;
    private String JobID;
    private CustomTextView CustTxtTitle;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_milestone);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity = ShowMileStone.this;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("processing");
        init();
        Bundle params = getIntent().getExtras();
        if (params != null) {

            String milestone = params.getString(Constants.MILESTONE, "");
            JobID = params.getString(Constants.JOBID, "");
            if (milestone.length() > 0) {
                mileStoneModel = getGson().fromJson(milestone, MileStoneModel.class);
                if (mileStoneModel != null) {
                    CustTxtTitle.setText("MileStone");
                    loadData(mileStoneModel);
                }
            } else {
                finish();
            }


        }


    }

    private void loadData(MileStoneModel mileStoneModel) {

        etMilestoneTitle_AddMilestone.setText(mileStoneModel.getTitle());
        etMilestoneDesc_AddMilestone.setText(mileStoneModel.getDescription());
        etAmount_AddMilestone.setText(mileStoneModel.getAmount());


        if (mileStoneModel.getStatus().isInProgress()) {
            spStatus_AddMilestone.setSelection(0, false);
        }
        if (mileStoneModel.getStatus().isInPreview()) {
            spStatus_AddMilestone.setSelection(1, false);
        }
        if (mileStoneModel.getStatus().isCompleted()) {
            spStatus_AddMilestone.setSelection(2, false);
        }
//        if (getUserGson().getRole().equalsIgnoreCase(Constants.OWNER)) {
//            tvSubmit_AddMilestone.setVisibility(View.VISIBLE);
//        } else {
//            tvSubmit_AddMilestone.setVisibility(View.GONE);
//        }
        if (mileStoneModel.getStatus().isPaid() && mileStoneModel.getStatus().isCompleted()) {
            tvSubmit_AddMilestone.setVisibility(View.GONE);
        }
        if (!mileStoneModel.getStatus().isPaid() && mileStoneModel.getStatus().isCompleted() && getUserGson().getRole().equalsIgnoreCase(Constants.OWNER)) {
            tvSubmit_AddMilestone.setVisibility(View.VISIBLE);
        }else {
            tvSubmit_AddMilestone.setVisibility(View.GONE);
        }
        if (getUserGson().getRole().equalsIgnoreCase(Constants.MM)) {
            spStatus_AddMilestone.setEnabled(true);
        } else {
            spStatus_AddMilestone.setEnabled(false);
        }
        spStatus_AddMilestone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                if (status.equalsIgnoreCase("Completed"))
                    ChangeMileStoneStatus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_AddMilestone);
        CustTxtTitle = (CustomTextView) findViewById(R.id.CustTxtTitle);
        tvSubmit = (CustomTextView) findViewById(R.id.tvSubmit_AddMilestone);
        tvClear = (CustomTextView) findViewById(R.id.tvClear_AddMilestone);
        etMilestoneTitle_AddMilestone = (CustomTextView) findViewById(R.id.etMilestoneTitle_AddMilestone);
        etMilestoneDesc_AddMilestone = (CustomTextView) findViewById(R.id.etMilestoneDesc_AddMilestone);
        etAmount_AddMilestone = (CustomTextView) findViewById(R.id.etAmount_AddMilestone);
        spStatus_AddMilestone = (Spinner) findViewById(R.id.spStatus_AddMilestone);
        // spStatus_AddMilestone.setEnabled(false);
        etMilestoneTitle_AddMilestone = (CustomTextView) findViewById(R.id.etMilestoneTitle_AddMilestone);
        tvSubmit_AddMilestone = (CustomTextView) findViewById(R.id.tvSubmit_AddMilestone);

        btnClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvClear.setOnClickListener(this);


    }

    private void ChangeMileStoneStatus() {
        ChangeMileStoneStatusWebservice changeMileStoneStatusWebservice = new ChangeMileStoneStatusWebservice(new ChangeMileStoneStatusWebservice.ChangeMileStoneStatusWebserviceHandler() {
            @Override
            public void changeMileStoneStatusWebserviceStart() {
                progressDialog.show();
            }

            @Override
            public void changeMileStoneStatusWebserviceSucessful(String response, String message) {
                mileStoneModel.getStatus().setCompleted(true);
                loadData(mileStoneModel);
                Toast.makeText(ShowMileStone.this, message, Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void changeMileStoneStatusWebserviceFailedWithMessage(String message) {
                Toast.makeText(ShowMileStone.this, message, Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }, this);
        try {
            JSONObject params = new JSONObject();
            params.put(Constants.JOBID, JobID);
            params.put(Constants.USERID, getUserGson().getUserID());
            params.put(Constants.MILESTONE_STATUS, true);
            params.put(Constants.MILESTONEID, mileStoneModel.getMilestoneID());
            changeMileStoneStatusWebservice.callService(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        // TODO onClick
        switch (v.getId()) {
            case R.id.btnClose_AddMilestone:
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                break;

            case R.id.tvSubmit_AddMilestone: {
                if (tvSubmit_AddMilestone.getText().toString().equalsIgnoreCase(getResources().getString(R.string.make_payment))) {
                    Intent PaymentIntent = new Intent(this, PaymentActivity.class);
                    PaymentIntent.putExtra(Constants.SELECTED_MILE_STONE, mileStoneModel.toString());
                    PaymentIntent.putExtra(Constants.JOBID, JobID);
                    startActivityForResult(PaymentIntent, Constants.MILESTONE_PAYMENT);
                } else {
                    finish();
                    overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
                }
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra(Constants.MILESTONE, getData());
//                resultIntent.putExtra(Constants.EDITMILESTONEPOSITION, editPosition);
//                setResult(Activity.RESULT_OK, resultIntent);

            }

            break;

            case R.id.tvClear_AddMilestone:
                break;
        }
    }

    private String getData() {
        MileStoneModel item = new MileStoneModel();
        item.setTitle(etMilestoneTitle_AddMilestone.getText().toString());
        item.setDescription(etMilestoneDesc_AddMilestone.getText().toString());
        item.setAmount(etAmount_AddMilestone.getText().toString());
        MileStoneStatusModel mileStoneStatusModel = new MileStoneStatusModel();
        if (spStatus_AddMilestone.getSelectedItemPosition() == 0) {
            mileStoneStatusModel.setInProgress(true);
        }
        if (spStatus_AddMilestone.getSelectedItemPosition() == 1) {
            mileStoneStatusModel.setInPreview(true);
        }
        if (spStatus_AddMilestone.getSelectedItemPosition() == 2) {
            mileStoneStatusModel.setCompleted(true);
        }
        item.setStatus(mileStoneStatusModel);

        return item.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.MILESTONE_PAYMENT: {
                this.finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddMilestone).performClick();
    }
}
