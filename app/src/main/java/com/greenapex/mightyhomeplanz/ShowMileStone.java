package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.greenapex.R;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.mightyhomeplanz.entities.MileStoneStatusModel;
import com.greenapex.widgets.CustomTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_milestone);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity = ShowMileStone.this;
        init();
        Bundle params = getIntent().getExtras();
        if (params != null) {

            String milestone = params.getString(Constants.MILESTONE, "");
            if (milestone.length() > 0) {
                mileStoneModel = getGson().fromJson(milestone, MileStoneModel.class);
                if (mileStoneModel != null) {
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
        etMilestoneTitle_AddMilestone = (CustomTextView) findViewById(R.id.etMilestoneTitle_AddMilestone);
        etMilestoneDesc_AddMilestone = (CustomTextView) findViewById(R.id.etMilestoneDesc_AddMilestone);
        etAmount_AddMilestone = (CustomTextView) findViewById(R.id.etAmount_AddMilestone);
        spStatus_AddMilestone = (Spinner) findViewById(R.id.spStatus_AddMilestone);
        spStatus_AddMilestone.setEnabled(false);
        etMilestoneTitle_AddMilestone = (CustomTextView) findViewById(R.id.etMilestoneTitle_AddMilestone);

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
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra(Constants.MILESTONE, getData());
//                resultIntent.putExtra(Constants.EDITMILESTONEPOSITION, editPosition);
//                setResult(Activity.RESULT_OK, resultIntent);
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
