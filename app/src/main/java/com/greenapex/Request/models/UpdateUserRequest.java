package com.greenapex.Request.models;

import com.google.gson.Gson;
import com.greenapex.mightyhomeplanz.entities.AddressModel;

/**
 * Created by admin on 08-Mar-16.by ${COMPUTERNAME}
 */
public class UpdateUserRequest {
    private String lname;

    private String email;

    private AddressModel address;

    private String profilePic;

    private String contactNo;


    private String fname;


    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public UpdateUserRequest() {
        this.fname = "";
        this.lname = "";

        this.email = "";
        this.address = new AddressModel();
        this.profilePic = "";
        this.contactNo = "";

    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }


}
