package com.greenapex.Utils;

/**
 * Created by rultech on 15/2/16.
 */
public class Constants {

    public static final int ACTION_IMAGE_CAPTURE = 9999;
    public static final int ACTION_PICK = 8888;
    public static final int CROP_PIC_REQUEST_CODE = 7777;
    public static final boolean DEBUG = true;

    public static final  String BaseImageDomain="http://104.236.239.37:8080/";

    public static final String registerWebservice = "http://104.236.239.37:8080/v1/user/register";
    public static final String loginWebservice = "http://104.236.239.37:8080/v1/user/auth?";
    public static final String forgotPasswordWebservice = "http://104.236.239.37:8080/v1/user/forgetpassword?";
    public static final String AddJobWebservice = "http://104.236.239.37:8080/v1/job/createjob?userID=";
    public static final String AddJobDocWebservice = "http://104.236.239.37:8080/v1/job/addjobdoc?userID=";
    public static final String AssignjobToMMWebservice = "http://104.236.239.37:8080/v1/job/assignjob?mmID=";
    public static final String UpdateJobDescriptionWebservice = "http://104.236.239.37:8080/v1/job/updatejobdesc?jobID=";
    public static final String ChangeJobStatusWebservice = "http://104.236.239.37:8080/v1/job/changestatus?jobID=";
    public static final String UploadFileWebservice = "http://104.236.239.37:8080/v1/job/uploadfile";
    public static final String AddSowWebservice = "http://104.236.239.37:8080/v1/job/addsow?jobID=";

    public static final String GetNewJobWebservice = "http://104.236.239.37:8080/v1/job/getnewjoblistbyid?userID=";
    public static final String GetNewJobForPMWebservice = "http://104.236.239.37:8080/v1/job/getnewjoblist";
    public static final String GetNewJobForMMIDWebservice = "http://104.236.239.37:8080/v1/job/getnewjobbymmid?mmID=";

    public static final String GetActiveJobWebservice = "http://104.236.239.37:8080/v1/job/getactivejoblistbyuser?userID=";
    public static final String GetActiveJobForPMWebservice = "http://104.236.239.37:8080/v1/job/getactivejoblist";
    public static final String GetActiveJobForMMIDWebservice = "http://104.236.239.37:8080/v1/job/getactivejobbymmid?mmID=";

    public static final String GetCompletedJobWebservice = "http://104.236.239.37:8080/v1/job/getcompletedjoblistbyuser?userID=";
    public static final String GetCompletedJobForPMWebservice = "http://104.236.239.37:8080/v1/job/getcompletedjoblist";
    public static final String GetCompletedJobForMMIDWebservice = "http://104.236.239.37:8080/v1/job/getcompletedjobbymmid?mmID=";

    public static final String GetJobDetailByJobIdWebservice = "http://104.236.239.37:8080/v1/job/getjobdetailbyjobid?jobID=";

    public static final String GetMMListWebservice = "http://104.236.239.37:8080/v1/user/getmmlist?userID=";

    public static final String getTotalJobWebservice = "http://104.236.239.37:8080/v1/job/gettotaljobsbyowner?userID=";

    public static final String updateJobDescriptionWebservice = "http://104.236.239.37:8080/v1/job/updatejobdesc?userID=OW5017&jobID=JOB50016";
    public static final String SELECTFILE = "Selected_file";


//    public static String AddJobWebservice_Temp = "http://104.236.239.37:8080/v1/job/createjob?userID=";

    //    email=shalaka.nayal@green-apex.com";

    public static String mightyHomePlanz = "mightyHomePlanz";
    public static String UserData = "UserData";

    public static final int METHOD_POST = 0;
    public static final int METHOD_GET = 1;


    public static final String NEW = "New";
    public static String ASSIGNED = "Assigned";
    public static final String REQUESTED_FOR_PAYMENT = "Requested For Payment";
    public static final String UNDERESTIMATION = "Under Estimation";
    public static final String UNDER_ESTIMATION = "Under_Estimation";
    public static final String REJECTED = "Rejected";
    public static final String INPROGRESS = "Inprogress";
    public static final String COMPLETED = "Completed";
    public static final String CANCEL = "Cancel";
    public static final String OWNER = "JO";
    public static final String PM = "PM";
    public static final String MM = "MM";

    public static final String JOBID = "jobID";
    public static final String MMID = "mmID";
    public static final String USERID = "userID";
    public static final String ROLE = "role";
    public static final String JOBSTATUS = "status";

    public static final int ADDMILESTONE = 1111;
    public static final int ADDSOW = 2222;

    public static final String MILESTONE = "milestone";
    public static final String EDITMILESTONE = "editmilestone";
    public static final String EDITMILESTONEPOSITION = "editmilestoneposition";
    public static String OWNER_REVIEW = "OWNER_REVIEW";
    public static String OWNERREVIEW = "Owner Review";
}
