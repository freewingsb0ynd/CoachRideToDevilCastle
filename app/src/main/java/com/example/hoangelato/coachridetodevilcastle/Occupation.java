package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Occupation {
    private int occupationType;
    private boolean isUsed;
    private boolean isOccupied;
    private String occupationSrc;

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


    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getOccupationSrc() {
        return occupationSrc;
    }

    public void setOccupationSrc(String occupationSrc) {
        this.occupationSrc = occupationSrc;
    }

    public Occupation(int occupationType) {
        this.occupationType = occupationType;
        switch (occupationType){
            case 0:
                occupationSrc= "@drawable/occupation_bodyguard";
                break;
            case 1:
                occupationSrc= "@drawable/occupation_clairvoyant";
                break;
            case 2:
                occupationSrc= "@drawable/occupation_diplomat";
                break;
            case 3:
                occupationSrc= "@drawable/occupation_doctor";
                break;
            case 4:
                occupationSrc= "@drawable/occupation_duelist";
                break;
            case 5:
                occupationSrc= "@drawable/occupation_grandmaster";
                break;
            case 6:
                occupationSrc= "@drawable/occupation_hypnotist";
                break;
            case 7:
                occupationSrc= "@drawable/occupation_poisonmixer";
                break;
            case 8:
                occupationSrc= "@drawable/occupation_priest";
                break;
            case 9:
                occupationSrc= "@drawable/occupation_thug";
                break;
            default:break;
        }
        isUsed = false;
        isOccupied=false;
    }
}
