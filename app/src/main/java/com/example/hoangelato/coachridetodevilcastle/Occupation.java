package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Occupation {
    private int occupationType;
    private boolean isUsed;
    private boolean isOccupied;
    private String occupationSkill;

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

    public String getOccupationSkill() {
        return occupationSkill;
    }

    public void setOccupationSkill(String occupationSkill) {
        this.occupationSkill = occupationSkill;
    }

    public Occupation(int occupationType) {
        this.occupationType = occupationType;
        switch (occupationType){
            case 0:
                occupationSkill= "Skill0";
                break;
            // 10 occupation
            default: break;

        }
        isUsed = false;
        isOccupied=false;
    }
}
