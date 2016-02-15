package com.acrobat.mightyhomeplanz.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class ProjectModel implements Parcelable {

    private String id, name, startDate, status, image;

    @Override
    public int describeContents() {
        return 0;
    }

    public ProjectModel() {
    }

    public static final Creator<ProjectModel> CREATOR = new Creator<ProjectModel>() {

        @Override
        public ProjectModel[] newArray(int size) {
            return new ProjectModel[size];
        }

        @Override
        public ProjectModel createFromParcel(Parcel source) {
            return new ProjectModel(source);
        }
    };

    public ProjectModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        startDate = in.readString();
        status = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(startDate);
        dest.writeString(status);
        dest.writeString(image);
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getstartDate() {
        return startDate;
    }

    public void setstartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
