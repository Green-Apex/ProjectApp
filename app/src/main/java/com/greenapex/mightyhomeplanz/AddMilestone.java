package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.mightyhomeplanz.entities.MileStoneStatusModel;
import com.greenapex.widgets.CustomTextView;

public class AddMilestone extends Activity implements OnClickListener {
    Activity activity;
    ImageButton btnClose;
    CustomTextView tvSubmit, tvClear;
    private EditText etMilestoneTitle_AddMilestone;
    private EditText etMilestoneDesc_AddMilestone;
    private EditText etAmount_AddMilestone;
    private Spinner spStatus_AddMilestone;
    private boolean isForEdit;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_milestone);
        activity = AddMilestone.this;
        init();
        Bundle params = getIntent().getExtras();
        if (params != null) {
            isForEdit = params.getBoolean(Constants.EDITMILESTONE, false);
            if (isForEdit) {
                editPosition = params.getInt(Constants.EDITMILESTONEPOSITION, 0);
                MileStoneModel mileStoneModel = new GsonBuilder().create().fromJson(params.getString(Constants.MILESTONE, ""), MileStoneModel.class);
                if (mileStoneModel != null) {
                    loadData(mileStoneModel);
                }
            }
        }




    }

    private void loadData(MileStoneModel mileStoneModel) {

        etMilestoneTitle_AddMilestone.setText(mileStoneModel.getTitle());
        etMilestoneDesc_AddMilestone.setText(mileStoneModel.getDescription());
        etAmount_AddMilestone.setText(mileStoneModel.getAmount());


        if (mileStoneModel.getStatus().isInProgress()) {
            spStatus_AddMilestone.setSelection(0);
        }
        if (mileStoneModel.getStatus().isInPreview()) {
            spStatus_AddMilestone.setSelection(1);
        }
        if (mileStoneModel.getStatus().isCompleted()) {
            spStatus_AddMilestone.setSelection(2);
        }

    }

    public void init() {
        btnClose = (ImageButton) findViewById(R.id.btnClose_AddMilestone);
        tvSubmit = (CustomTextView) findViewById(R.id.tvSubmit_AddMilestone);
        tvClear = (CustomTextView) findViewById(R.id.tvClear_AddMilestone);
        etMilestoneTitle_AddMilestone = (EditText) findViewById(R.id.etMilestoneTitle_AddMilestone);
        etMilestoneDesc_AddMilestone = (EditText) findViewById(R.id.etMilestoneDesc_AddMilestone);
        etAmount_AddMilestone = (EditText) findViewById(R.id.etAmount_AddMilestone);
        spStatus_AddMilestone = (Spinner) findViewById(R.id.spStatus_AddMilestone);
        etMilestoneTitle_AddMilestone = (EditText) findViewById(R.id.etMilestoneTitle_AddMilestone);

        btnClose.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvClear.setOnClickListener(this);
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
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Constants.MILESTONE, getData());
                resultIntent.putExtra(Constants.EDITMILESTONEPOSITION, editPosition);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                overridePendingTransition(R.anim.nothing, R.anim.effect_box_back);
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
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddMilestone).performClick();
    }
}
