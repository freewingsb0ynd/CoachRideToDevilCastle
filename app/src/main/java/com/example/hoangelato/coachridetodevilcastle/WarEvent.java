package com.example.hoangelato.coachridetodevilcastle;

import java.util.ArrayList;

/**
 * Created by Hoangelato on 29/07/2016.
 */
public class WarEvent {
    Player attacker;
    Player deffender;
    int atkPoint, defPoint;
    ArrayList<Player> supporters;
    ArrayList<Player> observers;

    public WarEvent(Player attacker, Player deffender) {
        this.attacker = attacker;
        this.deffender = deffender;
        atkPoint =1; defPoint=1;
        this.supporters = new ArrayList<Player>();
        this.observers = new ArrayList<Player>();
        for (Player p : attacker.host.playersList){
            if(p != attacker && p != deffender)
                observers.add(p);
        }
    }

    public void getVote(Host h){
        int startOrder = h.playersList.indexOf(deffender);
        for (int i=0; i<8; i++){
            int vote=0;
            Player playerChoosing= h.playersList.get(startOrder+i);
            if (playerChoosing.getUsername().contains("Bot")){
                if (playerChoosing.teammatesList.contains(attacker)) vote=1;
                if (playerChoosing.teammatesList.contains(deffender)) vote=2;
            }
            else ;//listen choice
            if (vote!=0) supporters.add(playerChoosing);
            if (vote==1) atkPoint++; if (vote ==2) defPoint++;
        }
    }

    public void usingOccupiationPhase(){
        

    }


}