package com.example.bea.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import static android.os.UserHandle.readFromParcel;

public class Ingredients  implements Parcelable {

    private int mQuantity = 0;
    private String mMeasure;
    private String mIngredient;

    public Ingredients(int quantity, String measure, String ingredient){
        mQuantity=quantity;
        mIngredient=ingredient;
        mMeasure=measure;
    }
    public Ingredients(){}
    public Ingredients(Parcel in){
        readFromParcel(in);
    }

    public static final Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public int getQuantity(){return mQuantity;}
    public String getIngredient(){return mIngredient;}
    public String getMeasure(){return mMeasure;}

    public void setQuantity(int quantity){this.mQuantity = quantity;}
    public void setMeasure (String measure){this.mMeasure = measure;}
    public void setIngredient(String ingredient){this.mIngredient = ingredient;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
    private void readFromParcel(Parcel in){
        mQuantity = in.readInt();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }
}
