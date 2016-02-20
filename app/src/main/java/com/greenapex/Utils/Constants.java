package com.greenapex.Utils;

/**
 * Created by rultech on 15/2/16.
 */
public class Constants {

    public static final int ACTION_IMAGE_CAPTURE = 9999;
    public static final int ACTION_PICK = 8888;
    public static final int CROP_PIC_REQUEST_CODE = 7777;
    public static final boolean DEBUG = true;

    public static String registerWebservice = "http://104.236.239.37:8080/v1/user/register";
    public static String loginWebservice = "http://104.236.239.37:8080/v1/user/auth?";
    public static String forgotPasswordWebservice = "http://104.236.239.37:8080/v1/user/forgetpassword?";
    public static String AddJobWebservice = "http://104.236.239.37:8080/v1/job/createjob?ownerID=";

    public static String getNewJobWebservice = "http://104.236.239.37:8080/v1/job/getnewjoblistbyownerid?ownerID=";
    public static String getActiveJobWebservice = "http://104.236.239.37:8080/v1/job/getactivejoblistbyownerid?ownerID=";
    public static String getCompletedJobWebservice = "http://104.236.239.37:8080/v1/job/getcompletedjoblistbyownerid?ownerID=";
    public static String getTotalJobWebservice = "http://104.236.239.37:8080/v1/job/gettotaljobsbyowner?ownerID=";

    public static String updateJobDescriptionWebservice = "http://104.236.239.37:8080/v1/job/updatejobdesc?ownerID=OW5017&jobID=JOB50016";


//    public static String AddJobWebservice_Temp = "http://104.236.239.37:8080/v1/job/createjob?ownerID=";

    //    email=shalaka.nayal@green-apex.com";

    public static String mightyHomePlanz = "mightyHomePlanz";
    public static String UserData = "UserData";

    public static final int METHOD_POST = 0;
    public static final int METHOD_GET = 1;


    public static final String NEW = "New";
    public static String ASSIGNED = "Assigned";
    public static final String REQUESTED_FOR_PAYMENT = "Requested For Payment";
    public static final String UNDERESTIMATION = "Under Estimation";
    public static final String REJECTED = "Rejected";
    public static final String INPROGRESS = "Inprogress";
    public static final String COMPLETED = "Completed";
    public static final String OWNER = "owner";
    public static final String PM = "pm";
    public static final String MM = "mm";
}
