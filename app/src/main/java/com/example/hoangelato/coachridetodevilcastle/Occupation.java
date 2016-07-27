package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Occupation {
    private int occupationType;
    private Boolean isUsed;
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

    public void setUsed(Boolean used) {
        isUsed = used;
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
    }
}
