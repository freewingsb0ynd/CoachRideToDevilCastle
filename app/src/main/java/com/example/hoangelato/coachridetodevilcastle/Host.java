package com.example.hoangelato.coachridetodevilcastle;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Host {
    public Vector<Occupation> occupationsLeft;
    public Vector<Item> itemsLeft;
    public Vector<Player> playersList;
    public static int gameTurn;
    public int[] teamBudget= {1, 1, 1, 1, 2, 2, 2, 2};
    public int[] orderList= {1, 2, 3, 4, 5, 6, 7, 8};
    public int wonTeam;
    public static Occupation occupation0, occupation1, occupation2, occupation3, occupation4,
            occupation5, occupation6, occupation7, occupation8, occupation9;
    public static Item item0, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10,
            item11, item12, item13, item14, item15, item16, item17, item18, item19, item20;

    Player humanPlayer, botPlayer1, botPlayer2, botPlayer3, botPlayer4, botPlayer5, botPlayer6, botPlayer7;


    public Host() {
        occupationsLeft = new Vector<Occupation>();
        itemsLeft = new Vector<Item>();
        playersList = new Vector<Player>();
        wonTeam = 0;
        gameTurn=0;

        initPlayers();
        initAndShuffleCards();
        addPlayersToHost();

    }


    private void initAndShuffleCards() {
        for (int i=0 ; i<8; i++ ) System.out.println("teamBudget: phan tu thu " + i + " la " + teamBudget[i]);
        System.out.println("\n");
        shuffleArray(teamBudget);

        for (int i=0 ; i<8; i++ ) System.out.println("teamBudget: phan tu thu " + i + " la" + teamBudget[i]);
        System.out.println("\n");

        for (int i=0 ; i<8; i++ ) System.out.println("orderList: phan tu thu " + i + " la " + orderList[i]);
        System.out.println("\n");
        shuffleArray(orderList);

        for (int i=0 ; i<8; i++ ) System.out.println("orderList: phan tu thu " + i + " la" + orderList[i]);
        System.out.println("\n");

        //initialize 10 occupation
        occupation0 = new Occupation(0); occupationsLeft.add(0,occupation0);
        occupation1 = new Occupation(1); occupationsLeft.add(1,occupation1);
        occupation2 = new Occupation(2); occupationsLeft.add(2,occupation2);
        occupation3 = new Occupation(3); occupationsLeft.add(3,occupation3);
        occupation4 = new Occupation(4); occupationsLeft.add(4,occupation4);
        occupation5 = new Occupation(5); occupationsLeft.add(5,occupation5);
        occupation6 = new Occupation(6); occupationsLeft.add(6,occupation6);
        occupation7 = new Occupation(7); occupationsLeft.add(7,occupation7);
        occupation8 = new Occupation(8); occupationsLeft.add(8,occupation8);
        occupation9 = new Occupation(9); occupationsLeft.add(9,occupation9);

        for(Occupation o: occupationsLeft){
            System.out.println("occupation thu " + occupationsLeft.indexOf(o)  + " la occu " +o.getOccupationType());
        }



        //initialize 21 items
        item0  = new Item(0) ; itemsLeft.add(0,item0);
        item1  = new Item(0) ; itemsLeft.add(1,item1);
        item2  = new Item(0) ; itemsLeft.add(2,item2);
        item3  = new Item(1) ; itemsLeft.add(3,item3);
        item4  = new Item(1) ; itemsLeft.add(4,item4);
        item5  = new Item(1) ; itemsLeft.add(5,item5);
        item6  = new Item(2) ; itemsLeft.add(6,item6);
        item7  = new Item(3) ; itemsLeft.add(7,item7);
        item8  = new Item(4) ; itemsLeft.add(8,item8);
        item9  = new Item(5) ; itemsLeft.add(9,item9);
        item10 = new Item(6) ; itemsLeft.add(10,item10);
        item11 = new Item(7) ; itemsLeft.add(11,item11);
        item12 = new Item(8) ; itemsLeft.add(12,item12);
        item13 = new Item(9) ; itemsLeft.add(13,item13);
        item14 = new Item(10); itemsLeft.add(14,item14);
        item15 = new Item(11); itemsLeft.add(15,item15);
        item16 = new Item(12); itemsLeft.add(16,item16);
        item17 = new Item(13); itemsLeft.add(17,item17);
        item18 = new Item(14); itemsLeft.add(18,item18);
        item19 = new Item(15); itemsLeft.add(19,item19);
        item20 = new Item(16); itemsLeft.add(20,item20);

        for(Item i: itemsLeft){
            System.out.println("item thu " + itemsLeft.indexOf(i)  + " la item " +i.getItemType());
        }

    }


    private void addPlayersToHost() {



        playersList.add(0, humanPlayer);   humanPlayer.setTeam(teamBudget[0]);    humanPlayer.setOrder(orderList[0]);
        playersList.add(1, botPlayer1);    botPlayer1.setTeam(teamBudget[1]);     botPlayer1.setOrder(orderList[1]);
        playersList.add(2, botPlayer2);    botPlayer2.setTeam(teamBudget[2]);     botPlayer2.setOrder(orderList[2]);
        playersList.add(3, botPlayer3);    botPlayer3.setTeam(teamBudget[3]);     botPlayer3.setOrder(orderList[3]);
        playersList.add(4, botPlayer4);    botPlayer4.setTeam(teamBudget[4]);     botPlayer4.setOrder(orderList[4]);
        playersList.add(5, botPlayer5);    botPlayer5.setTeam(teamBudget[5]);     botPlayer5.setOrder(orderList[5]);
        playersList.add(6, botPlayer6);    botPlayer6.setTeam(teamBudget[6]);     botPlayer6.setOrder(orderList[6]);
        playersList.add(7, botPlayer7);    botPlayer7.setTeam(teamBudget[7]);     botPlayer7.setOrder(orderList[7]);

        for (Player p: playersList){
            System.out.println(p.getUsername() + " o team   "+ p.getTeam() + "   o vi tri thu  " + p.getOrder());
        }



    }

    private void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void initPlayers() {
        //ban dau chi tao ra 1 doi tuong nguoi choi co the tương tac.
        //7 con BOT

        humanPlayer =new Player("Day la nguoi choi");
        botPlayer1 =new Player("Day la Bot1      ");
        botPlayer2 =new Player("Day la Bot2      ");
        botPlayer3 =new Player("Day la Bot3      ");
        botPlayer4 =new Player("Day la Bot4      ");
        botPlayer5 =new Player("Day la Bot5      ");
        botPlayer6 =new Player("Day la Bot6      ");
        botPlayer7 =new Player("Day la Bot7      ");
    }



}
