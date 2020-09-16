package com.example.gearbook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Gear implements Parcelable {
    private Date date;
    private String maker;
    private String description;
    private Float price;
    private String comment;

    public Gear(Date date, String maker, String description, Float price, String comment) {
        this.date = date;
        this.maker = maker;
        this.description = description;
        this.price = price;
        this.comment = comment;
    }

    public Gear(Date date, String maker, String description, Float price) {
        this.date = date;
        this.maker = maker;
        this.description = description;
        this.price = price;
    }

    protected Gear(Parcel in) {
        maker = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
        date = (Date) in.readSerializable();
        comment = in.readString();
    }

    public static final Creator<Gear> CREATOR = new Creator<Gear>() {
        @Override
        public Gear createFromParcel(Parcel in) {
            return new Gear(in);
        }

        @Override
        public Gear[] newArray(int size) {
            return new Gear[size];
        }
    };

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maker);
        dest.writeString(description);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(price);
        }
        dest.writeSerializable(date);
        dest.writeString(comment);
    }
}
