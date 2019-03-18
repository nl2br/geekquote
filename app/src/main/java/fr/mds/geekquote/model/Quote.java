package fr.mds.geekquote.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Quote implements Serializable {

    private String strQuote;
    private int rating;
    private Date creationDate;

    public Quote(String strQuote) {
        this.strQuote = strQuote;
        this.rating = 0;
        this.creationDate = new Date();
    }

    public String getStrQuote() {
        return strQuote;
    }

    public void setStrQuote(String strQuote) {
        this.strQuote = strQuote;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return strQuote;
    }
/*
    protected Quote(Parcel in) {
        strQuote = in.readString();
        rating = in.readInt();
        creationDate = new Date(in.readLong());
    }

    public final Creator<Quote> CREATOR = new Creator() {
        @Override
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(strQuote);
        parcel.writeInt(rating);
        parcel.writeLong(Long.parseLong(creationDate.toString()));
    }*/
}
