package com.example.hoangelato.coachridetodevilcastle;

import java.io.Serializable;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Occupation implements Serializable {
    private int occupationType;
    private boolean isUsed;
    public boolean isOccupied;
    private int occupationSrc;
    private String occupationName;

    public String getOccupationName() {
        return occupationName;
    }

    public int getOccupationType() {
        return occupationType;
    }

    public void setOccupationType(int occupationType) {
        this.occupationType = occupationType;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public int getOccupationSrc() {
        return occupationSrc;
    }

    public void setOccupationSrc(int occupationSrc) {
        this.occupationSrc = occupationSrc;
    }

    public Occupation(int occupationType) {
        this.occupationType = occupationType;
        switch (occupationType){
            case 0:
                occupationName = "Bodyguard";
                occupationSrc= R.drawable.occupation_bodyguard;
                break;
            case 1:
                occupationName = "Clairvoyant";
                occupationSrc= R.drawable.occupation_clairvoyant;
                break;
            case 2:
                occupationName = "Diplomat";
                occupationSrc= R.drawable.occupation_diplomat;
                break;
            case 3:
                occupationName = "Doctor";
                occupationSrc= R.drawable.occupation_doctor;
                break;
            case 4:
                occupationName = "Duelist";
                occupationSrc= R.drawable.occupation_duelist;
                break;
            case 5:
                occupationName = "Grandmaster";
                occupationSrc= R.drawable.occupation_grandmaster;
                break;
            case 6:
                occupationName = "Hypnotist";
                occupationSrc= R.drawable.occupation_hypnotist;
                break;
            case 7:
                occupationName = "Poison Mixer";
                occupationSrc= R.drawable.occupation_poisonmixer;
                break;
            case 8:
                occupationName = "Priest";
                occupationSrc= R.drawable.occupation_priest;
                break;
            case 9:
                occupationName = "Thug";
                occupationSrc= R.drawable.occupation_thug;
                break;
            default:
                occupationName = "dm";
                occupationSrc =-1;
                break;
        }
        isUsed = false;
        isOccupied=false;
    }
}
