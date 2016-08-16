package com.example.hoangelato.coachridetodevilcastle.Network;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NguyenDuc on 8/5/2016.
 */

public class DataHelper {
    public static byte[] toByte(Parcelable parcelable) {
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static Parcel toParcel(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        return parcel;
    }

    public static <T> T toObject(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = toParcel(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }

    public static Bundle toBundle(byte[] bytes) {
        return toObject(bytes, Bundle.CREATOR);
    }

}
