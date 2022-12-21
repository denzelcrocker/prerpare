package com.example.prerpare;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Parcel;
import android.os.Parcelable;

import android.os.Bundle;

public class Mask implements Parcelable{
    private int ID;
    private String Title;
    private int Cost;
    private int StockAvailability;
    private int AvailabilityInTheStore;
    private String Description;
    private String Rewiews;
    private String Image;

    public Mask(int ID,String Title, int Cost, int StockAvailability, int AvailabilityInTheStore, String Description, String Rewiews, String Image){
        this.ID = ID;
        this.Title = Title;
        this.Cost = Cost;
        this.StockAvailability = StockAvailability;
        this.AvailabilityInTheStore = AvailabilityInTheStore;
        this.Description = Description;
        this.Rewiews = Rewiews;
        this.Image = Image;
    }
    protected Mask(Parcel in)
    {
        ID = in.readInt();
        Title = in.readString();
        Cost = in.readInt();
        StockAvailability = in.readInt();
        AvailabilityInTheStore = in.readInt();
        Description = in.readString();
        Rewiews = in.readString();
        Image = in.readString();
    }
    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public int getStockAvailability() {
        return StockAvailability;
    }

    public void setStockAvailability(int stockAvailability) {
        StockAvailability = stockAvailability;
    }

    public int getAvailabilityInTheStore() {
        return AvailabilityInTheStore;
    }

    public void setAvailabilityInTheStore(int availabilityInTheStore) {
        AvailabilityInTheStore = availabilityInTheStore;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRewiews() {
        return Rewiews;
    }

    public void setRewiews(String rewiews) {
        Rewiews = rewiews;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(Title);
        parcel.writeInt(Cost);
        parcel.writeInt(StockAvailability);
        parcel.writeInt(AvailabilityInTheStore);
        parcel.writeString(Description);
        parcel.writeString(Rewiews);
        parcel.writeString(Image);
    }
}