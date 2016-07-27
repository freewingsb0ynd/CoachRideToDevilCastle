package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Occupation {
    public int occupationType;
    public Boolean isUsed;
    public String occupationSkill;

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
