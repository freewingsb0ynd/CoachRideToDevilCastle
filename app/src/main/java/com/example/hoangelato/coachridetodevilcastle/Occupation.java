package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Occupation {
    private int occupationType;
    private boolean isUsed;
    public boolean isOccupied;
    private int occupationSrc;

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
                occupationSrc= R.drawable.occupation_bodyguard;
                break;
            case 1:
                occupationSrc= R.drawable.occupation_clairvoyant;
                break;
            case 2:
                occupationSrc= R.drawable.occupation_diplomat;
                break;
            case 3:
                occupationSrc= R.drawable.occupation_doctor;
                break;
            case 4:
                occupationSrc= R.drawable.occupation_duelist;
                break;
            case 5:
                occupationSrc= R.drawable.occupation_grandmaster;
                break;
            case 6:
                occupationSrc= R.drawable.occupation_hypnotist;
                break;
            case 7:
                occupationSrc= R.drawable.occupation_poisonmixer;
                break;
            case 8:
                occupationSrc= R.drawable.occupation_priest;
                break;
            case 9:
                occupationSrc= R.drawable.occupation_thug;
                break;
            default:occupationSrc =-1;
                break;
        }
        isUsed = false;
        isOccupied=false;
    }
}
