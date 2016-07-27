package com.example.hoangelato.coachridetodevilcastle;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Vector;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Host {
    public Vector<Occupation> occupationsLeft;
    public Vector<Item> itemsLeft;
    public Vector<Player> playersList;
    public int[] teamBudget= {1, 1, 1, 1, 2, 2, 2, 2};
    public int[] orderList= {1, 2, 3, 4, 5, 6, 7, 8};
    public int wonTeam;

    public Host() {
        occupationsLeft=new Vector<Occupation>();
        itemsLeft= new Vector<Item>();
        playersList= new Vector<Player>();
        wonTeam=0;
    }
}
