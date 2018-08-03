package com.example.bea.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {

    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    public Steps(int id, String shortDescription, String description, String videoURL, String thumbnailURL){
        this.mId = id;
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoURL = videoURL;
        this.mThumbnailURL = thumbnailURL;
    }

    protected Steps(Parcel in) {
        mId = in.readInt();
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoURL = in.readString();
        mThumbnailURL = in.readString();
    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    public int getId(){return mId;}
    public String getShortDescription(){return mShortDescription;}
    public String getDescription(){return mDescription;}
    public String getVideoURL(){return mVideoURL;}
    public String getThumbnailURL(){return mThumbnailURL;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoURL);
        dest.writeString(mThumbnailURL);
    }
}
