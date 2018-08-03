package com.example.bea.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import static android.os.UserHandle.readFromParcel;

public class Recipe implements Parcelable {

    public int mID;
    public String mName;
    public int mServings;
    public ArrayList<Ingredients> mIngredientsArrayList;
    public ArrayList<Steps> mStepsArrayList;
    public Recipe(int iD, String name,int servings, ArrayList<Ingredients> ingredientsArrayList, ArrayList<Steps> stepsArrayList){
        mID =iD;
        mName = name;
        mServings = servings;
        mStepsArrayList = stepsArrayList;
        mIngredientsArrayList = ingredientsArrayList;
    }
    public Recipe(){}
    public Recipe(Parcel in){
        readFromParcel(in);
    }
    @SuppressWarnings("unused")
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getID(){return mID;}
    public String getName(){return mName;}
    public int getServings(){return mServings;}
    public ArrayList<Steps> getStepsArrayList(){return mStepsArrayList;}
    public ArrayList<Ingredients> getIngredientsArrayList(){return mIngredientsArrayList;}

    public void setId(int iD){this.mID = iD;}
    public void setName(String name){this.mName = name;}
    public void setServings(int servings){this.mServings = servings;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mID);
        dest.writeString(mName);
        dest.writeInt(mServings);
        if (mIngredientsArrayList == null){
            dest.writeByte((byte) (0x00));
        }else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mIngredientsArrayList);
        }
        if (mStepsArrayList == null){
            dest.writeByte((byte) (0x00));
        }else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mStepsArrayList);
        }
    }

    private void readFromParcel(Parcel in){
        mID = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        if(in.readByte() == 0x01){
            mIngredientsArrayList = new ArrayList<Ingredients>();
            in.readList(mIngredientsArrayList,Ingredients.class.getClassLoader());
        }else{
            mIngredientsArrayList = null;
        }
        if (in.readByte() == 0x01){
            mStepsArrayList = new ArrayList<Steps>();
            in.readList(mStepsArrayList,Steps.class.getClassLoader());
        }else{
            mStepsArrayList = null;
        }
    }
}
