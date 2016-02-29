package com.greenapex.mightyhomeplanz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.greenapex.R;
import com.greenapex.Request.models.AddSowRequest;
import com.greenapex.Utils.Constants;
import com.greenapex.mightyhomeplanz.adapters.MileStoneAdapter;
import com.greenapex.Request.models.JobDOCModel;
import com.greenapex.mightyhomeplanz.entities.MileStoneModel;
import com.greenapex.mightyhomeplanz.entities.MileStoneStatusModel;
import com.greenapex.webservice.AddSowWebservice;
import com.greenapex.widgets.CustomEditText;
import com.greenapex.widgets.CustomTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AddSow extends BaseActivity implements OnClickListener {
    private Activity activity;
    private ImageButton btnClose;
    private CustomTextView tvSubmit, tvClear;
    private ImageButton btnCloseAddMilestone;
    private LinearLayout linearLayoutTitle;
    private CustomEditText etMilestoneTitleAddMilestone;
    private LinearLayout linearLayoutSelectDocument;
    private CustomTextView tvBrowseAddDocument;
    private ListView listMileStone;
    private ImageButton btnAddMilestone;
    private LinearLayout LinearLayoutSubmit;
    private CustomTextView tvSubmitAddDocument;
    private CustomTextView tvClearAddDocument;
    private ArrayList<MileStoneModel> arrMileStones = new ArrayList<>();
    private MileStoneAdapter mileStoneAdapter;
    private File selectedfilePath;
    private String selectedJobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_addsow);
        activity = AddSow.this;
        Bundle params = getIntent().getExtras();
        if (params != null) {
            selectedJobId = params.getString(Constants.JOBID, "");
        }
        if (selectedJobId == null && selectedJobId.equalsIgnoreCase("")) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
        MileStoneModel firstMilestone = new MileStoneModel();
        firstMilestone.setAmount("0");
        firstMilestone.setTitle("Down Payment");
        firstMilestone.setDescription("Down Payment");
        MileStoneStatusModel mileStoneStatusModel = new MileStoneStatusModel();
        mileStoneStatusModel.setInProgress(true);
        firstMilestone.setStatus(mileStoneStatusModel);
        arrMileStones.add(firstMilestone);
        init();

    }

    public void init() {
        btnCloseAddMilestone = (ImageButton) findViewById(R.id.btnClose_AddMilestone);
        linearLayoutTitle = (LinearLayout) findViewById(R.id.linearLayoutTitle);
        etMilestoneTitleAddMilestone = (CustomEditText) findViewById(R.id.etMilestoneTitle_AddMilestone);
        linearLayoutSelectDocument = (LinearLayout) findViewById(R.id.linearLayoutSelectDocument);
        tvBrowseAddDocument = (CustomTextView) findViewById(R.id.tvBrowse_AddDocument);
        listMileStone = (ListView) findViewById(R.id.listMileStone);
        btnAddMilestone = (ImageButton) findViewById(R.id.btn_AddMilestone);
        LinearLayoutSubmit = (LinearLayout) findViewById(R.id.LinearLayoutSubmit);
        tvSubmitAddDocument = (CustomTextView) findViewById(R.id.tvSubmit_AddDocument);
        tvClearAddDocument = (CustomTextView) findViewById(R.id.tvClear_AddDocument);

        btnCloseAddMilestone.setOnClickListener(this);
        btnAddMilestone.setOnClickListener(this);
        tvBrowseAddDocument.setOnClickListener(this);
        tvSubmitAddDocument.setOnClickListener(this);
        loadData();
        listMileStone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(activity, AddMilestone.class);
                editIntent.putExtra(Constants.EDITMILESTONE, true);
                editIntent.putExtra(Constants.EDITMILESTONEPOSITION, position);
                editIntent.putExtra(Constants.MILESTONE, ((MileStoneModel) parent.getItemAtPosition(position)).toString());
                activity.startActivityForResult(editIntent, Constants.ADDMILESTONE);
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                Toast.makeText(AddSow.this, "Position" + ((MileStoneModel) parent.getItemAtPosition(position)).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        mileStoneAdapter = new MileStoneAdapter(this, arrMileStones, Glide.with(this));
        listMileStone.setAdapter(mileStoneAdapter);
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

            case R.id.btn_AddMilestone:
                activity.startActivityForResult(new Intent(activity, AddMilestone.class), Constants.ADDMILESTONE);
                activity.overridePendingTransition(R.anim.effect_box, R.anim.nothing);
                break;

            case R.id.tvSubmit_AddDocument:
                AddSowWebservice addSowWebservice = new AddSowWebservice(new AddSowWebservice.AddSowWebserviceHandler() {
                    @Override
                    public void addSowWebserviceStart() {

                    }

                    @Override
                    public void addSowWebserviceSucessful(String response, String message) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }

                    @Override
                    public void addSowWebserviceFailedWithMessage(String message) {
                        showToast(message);
                    }
                }, this);


                try {
                    AddSowRequest addSowRequest = new AddSowRequest();
                    addSowRequest.setMilestones(arrMileStones);
                    JobDOCModel jobDOCModel = new JobDOCModel();
                    jobDOCModel.setDocPath(selectedfilePath.getAbsolutePath());
                    jobDOCModel.setDocTitle(selectedfilePath.getName());
                    jobDOCModel.setUploadedUserID(getUserGson().getUserID());
                    addSowRequest.setJobDOC(jobDOCModel);
                    addSowRequest.setJobId(selectedJobId);
                    addSowRequest.setRole(getUserGson().getRole());
                    addSowRequest.setUserID(getUserGson().getUserID());
                    JSONObject params = new JSONObject(addSowRequest.toString());
                    addSowWebservice.callService(params, Constants.METHOD_POST);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tvClear_AddMilestone:
                break;
            case R.id.tvBrowse_AddDocument: {
                new FileChooser(activity).setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(final File file) {
                        // do something with the file
                        selectedfilePath = file;
                        // Toast.makeText(AddSow.this, "file select +" + file.getName(), Toast.LENGTH_SHORT).show();
                    }
                }).showDialog();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        findViewById(R.id.btnClose_AddMilestone).performClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.ADDMILESTONE: {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        String mileStone = data.getExtras().getString(Constants.MILESTONE, "");
                        int mileStonePosition = data.getExtras().getInt(Constants.EDITMILESTONEPOSITION, 0);
                        if (mileStone != null && mileStone.length() > 0) {
                            MileStoneModel mileStoneModel = new GsonBuilder().create().fromJson(mileStone, MileStoneModel.class);
                            if (mileStonePosition >= 0) {
                                arrMileStones.set(mileStonePosition, mileStoneModel);
                            } else {
                                arrMileStones.add(mileStoneModel);
                            }

                            mileStoneAdapter.notifyDataSetChanged();

                        }
                    }

                }
                break;
            }
        }
    }
}
