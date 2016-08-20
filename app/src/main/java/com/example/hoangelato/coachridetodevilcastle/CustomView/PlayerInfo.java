package com.example.hoangelato.coachridetodevilcastle.CustomView;

import java.io.Serializable;

/**
 * Created by bloe on 19/08/2016.
 */
public class PlayerInfo implements MyDataNode,Serializable {
    public String playerName;
    public String playerIp;

    public PlayerInfo(String playerName, String playerIp) {
        this.playerName = playerName;
        this.playerIp = playerIp;
    }
}
