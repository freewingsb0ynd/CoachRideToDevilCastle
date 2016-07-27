package com.example.hoangelato.coachridetodevilcastle;

import java.lang.reflect.Array;
import java.util.Collections;
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
        occupation0 = new Occupation(0); occupationsLeft.add(occupation0);
        occupation1 = new Occupation(1); occupationsLeft.add(occupation1);
        occupation2 = new Occupation(2); occupationsLeft.add(occupation2);
        occupation3 = new Occupation(3); occupationsLeft.add(occupation3);
        occupation4 = new Occupation(4); occupationsLeft.add(occupation4);
        occupation5 = new Occupation(5); occupationsLeft.add(occupation5);
        occupation6 = new Occupation(6); occupationsLeft.add(occupation6);
        occupation7 = new Occupation(7); occupationsLeft.add(occupation7);
        occupation8 = new Occupation(8); occupationsLeft.add(occupation8);
        occupation9 = new Occupation(9); occupationsLeft.add(occupation9);

        for(Occupation o: occupationsLeft){
            System.out.println("occupation thu " + occupationsLeft.indexOf(o)  + " la occu " +o.getOccupationType());
        }

        Collections.shuffle(occupationsLeft);

        humanPlayer.setOccupation(occupationsLeft.get(0)); occupationsLeft.get(0).setOccupied(true);
        botPlayer1.setOccupation(occupationsLeft.get(1));  occupationsLeft.get(1).setOccupied(true);
        botPlayer2.setOccupation(occupationsLeft.get(2));  occupationsLeft.get(2).setOccupied(true);
        botPlayer3.setOccupation(occupationsLeft.get(3));  occupationsLeft.get(3).setOccupied(true);
        botPlayer4.setOccupation(occupationsLeft.get(4));  occupationsLeft.get(4).setOccupied(true);
        botPlayer5.setOccupation(occupationsLeft.get(5));  occupationsLeft.get(5).setOccupied(true);
        botPlayer6.setOccupation(occupationsLeft.get(6));  occupationsLeft.get(6).setOccupied(true);
        botPlayer7.setOccupation(occupationsLeft.get(7));  occupationsLeft.get(7).setOccupied(true);

        // LOG: Fix lai, sd remove. nho duyet nguoc
        {   //dao cac occu chua su dung len dau ds
            int endOfList=9;
            for (Occupation o : occupationsLeft) {
                if (o.isOccupied()& !occupationsLeft.get(endOfList).isOccupied()) {
                    Collections.swap(occupationsLeft,occupationsLeft.indexOf(o),endOfList);
                    endOfList--;
                }
            }
        }
        for(Occupation o: occupationsLeft){
            if (!o.isOccupied())System.out.println("occupation thu " + occupationsLeft.indexOf(o)  + " la occu " +o.getOccupationType());
        }

        //initialize 21 items
        item6  = new Item(2) ; itemsLeft.add(item6);
        item7  = new Item(3) ; itemsLeft.add(item7);
        item0  = new Item(0) ; itemsLeft.add(item0);
        item1  = new Item(0) ; itemsLeft.add(item1);
        item2  = new Item(0) ; itemsLeft.add(item2);
        item3  = new Item(1) ; itemsLeft.add(item3);
        item4  = new Item(1) ; itemsLeft.add(item4);
        item5  = new Item(1) ; itemsLeft.add(item5);
        item8  = new Item(4) ; itemsLeft.add(item8);
        item9  = new Item(5) ; itemsLeft.add(item9);
        item10 = new Item(6) ; itemsLeft.add(item10);
        item11 = new Item(7) ; itemsLeft.add(item11);
        item12 = new Item(8) ; itemsLeft.add(item12);
        item13 = new Item(9) ; itemsLeft.add(item13);
        item14 = new Item(10); itemsLeft.add(item14);
        item15 = new Item(11); itemsLeft.add(item15);
        item16 = new Item(12); itemsLeft.add(item16);
        item17 = new Item(13); itemsLeft.add(item17);
        item18 = new Item(14); itemsLeft.add(item18);
        item19 = new Item(15); itemsLeft.add(item19);
        item20 = new Item(16); itemsLeft.add(item20);

        for(Item i: itemsLeft){
            System.out.println("item thu " + itemsLeft.indexOf(i)  + " la item " +i.getItemType());
        }
           //tron thu tu cac item ko phai tui
        for (int i=20; i>1; i--){
            Random rnd = new Random();
            int index = rnd.nextInt(i);
            Collections.swap(itemsLeft, index, i);
        }



           //tron thu tu cac 8 item dau tien
        for (int i=7; i>-1; i--){
            Random rnd = new Random();
            int index = rnd.nextInt(i);
            Collections.swap(itemsLeft, index, i);
        }

        //phat cac item cho nguoi choi
        humanPlayer.itemsList.add(itemsLeft.get(0));
        botPlayer1.itemsList.add(itemsLeft.get(1));
        botPlayer2.itemsList.add(itemsLeft.get(2));
        botPlayer3.itemsList.add(itemsLeft.get(3));
        botPlayer4.itemsList.add(itemsLeft.get(4));
        botPlayer5.itemsList.add(itemsLeft.get(5));
        botPlayer6.itemsList.add(itemsLeft.get(6));
        botPlayer7.itemsList.add(itemsLeft.get(7));



        {   //dao cac item chua su dung len dau ds
            int endOfList= 20;
            for (Item i : itemsLeft) {
                if (i.isOwned()& !itemsLeft.get(endOfList).isOwned()) {
                    Collections.swap(itemsLeft,itemsLeft.indexOf(i),endOfList);
                    endOfList--;
                }
            }
        }

        for(Item i: itemsLeft){
            if (!i.isOwned()) System.out.println("item thu " + itemsLeft.indexOf(i)  + " la item " +i.getItemType());
        }

    }


    private void addPlayersToHost() {
        playersList.add(humanPlayer);   humanPlayer.setTeam(teamBudget[0]);    humanPlayer.setOrder(orderList[0]);
        playersList.add(botPlayer1);    botPlayer1.setTeam(teamBudget[1]);     botPlayer1.setOrder(orderList[1]);
        playersList.add(botPlayer2);    botPlayer2.setTeam(teamBudget[2]);     botPlayer2.setOrder(orderList[2]);
        playersList.add(botPlayer3);    botPlayer3.setTeam(teamBudget[3]);     botPlayer3.setOrder(orderList[3]);
        playersList.add(botPlayer4);    botPlayer4.setTeam(teamBudget[4]);     botPlayer4.setOrder(orderList[4]);
        playersList.add(botPlayer5);    botPlayer5.setTeam(teamBudget[5]);     botPlayer5.setOrder(orderList[5]);
        playersList.add(botPlayer6);    botPlayer6.setTeam(teamBudget[6]);     botPlayer6.setOrder(orderList[6]);
        playersList.add(botPlayer7);    botPlayer7.setTeam(teamBudget[7]);     botPlayer7.setOrder(orderList[7]);


        for (Player p: playersList){

            System.out.println(p.getUsername() + " o team   "+ p.getTeam() + "   o vi tri thu  " + p.getOrder() + " dang co occu  " +p.getOccupation().getOccupationType());
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
