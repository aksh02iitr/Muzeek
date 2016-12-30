package com.stabstudio.advancedmusicplayer;


import android.media.MediaMetadataRetriever;
import android.os.Parcel;
import android.os.Parcelable;

public class MyParcelable implements Parcelable{

    public MediaMetadataRetriever mediaMetadataRetriever;

    public MyParcelable(MediaMetadataRetriever mmr){
        this.mediaMetadataRetriever = mmr;
    }

    public MyParcelable(Parcel in) {
        mediaMetadataRetriever = (MediaMetadataRetriever) in.readValue(ClassLoader.getSystemClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeValue(mediaMetadataRetriever);
    }

    public static final Parcelable.Creator<MyParcelable> CREATOR = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };
}
