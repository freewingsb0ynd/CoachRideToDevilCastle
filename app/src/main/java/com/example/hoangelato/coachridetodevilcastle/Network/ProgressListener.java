package com.example.hoangelato.coachridetodevilcastle.Network;

/**
 * Created by NguyenDuc on 8/5/2016.
 */

public abstract class ProgressListener<T>{
    public abstract void onUpdateProgress(double percentage);
    public abstract void onDone(T result);
}
