package com.example.hoangelato.coachridetodevilcastle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Host {
    public ArrayList<Occupation> occupationsLeft;
    public ArrayList<Item> itemsLeft;
    public ArrayList<Player> playersList;
    public static int gameTurn;
    public int[] teamBudget= {1, 1, 1, 1, 2, 2, 2, 2};
    public int[] orderList= {1, 2, 3, 4, 5, 6, 7, 8};
    public int wonTeam;
    Occupation occupation0, occupation1, occupation2, occupation3, occupation4,
            occupation5, occupation6, occupation7, occupation8, occupation9;
    Item item0, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10,
            item11, item12, item13, item14, item15, item16, item17, item18, item19, item20;

    Player humanPlayer, botPlayer1, botPlayer2, botPlayer3, botPlayer4, botPlayer5, botPlayer6, botPlayer7;


    public Host() {
        occupationsLeft = new ArrayList<Occupation>();
        itemsLeft = new ArrayList<Item>();
        playersList = new ArrayList<Player>();
        wonTeam = 0;
        gameTurn=0;

        //init
        initPlayers();
        initGame();

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

        humanPlayer =new Player("Day la nguoi choi",this); playersList.add(humanPlayer);
        botPlayer1 =new Player("Day la Bot1      ",this);  playersList.add(botPlayer1);
        botPlayer2 =new Player("Day la Bot2      ",this);  playersList.add(botPlayer2);
        botPlayer3 =new Player("Day la Bot3      ",this);  playersList.add(botPlayer3);
        botPlayer4 =new Player("Day la Bot4      ",this);  playersList.add(botPlayer4);
        botPlayer5 =new Player("Day la Bot5      ",this);  playersList.add(botPlayer5);
        botPlayer6 =new Player("Day la Bot6      ",this);  playersList.add(botPlayer6);
        botPlayer7 =new Player("Day la Bot7      ",this);  playersList.add(botPlayer7);
    }

    private void initGame() {
        //init order for players
        shuffleArray(orderList);
        for (int i = 0; i < 8; i++){
            for (Player p: playersList)
                if (orderList[playersList.indexOf(p)]==i) Collections.swap(playersList,i,playersList.indexOf(p));
        }

        //separate 2 teams
        shuffleArray(teamBudget);
        for (int i =0; i <8; i++) {
            playersList.get(i).setTeam(teamBudget[i]);
        }

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

        Collections.shuffle(occupationsLeft);

        for (int i=0; i<8; i++) {
            playersList.get(i).setOccupation(occupationsLeft.get(i));
            occupationsLeft.get(i).isOccupied=true;
        }

        // xoa cac occu da dc chia ra
        {
            int maxIndex=occupationsLeft.size()-1;
            for (; maxIndex>-1; maxIndex--) {
                Occupation o= occupationsLeft.get(maxIndex);
                if (o.isOccupied) occupationsLeft.remove(o);
            }
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

           //tron thu tu cac item ko phai tui
        for (int i=20; i>=2; i--){
            Random rnd = new Random();
            int index = rnd.nextInt(i)+2;
            Collections.swap(itemsLeft, index, i);
        }

            //tron thu tu cac 8 item dau tien
        for (int i=7; i>0; i--){
            Random rnd = new Random();
            int index = rnd.nextInt(i);
            Collections.swap(itemsLeft, index, i);
        }

        //phat cac item cho nguoi choi
        for (int i=0; i<8; i++){
            playersList.get(i).itemsList.add(itemsLeft.get(i));
            itemsLeft.get(i).isOwned=true;
        }

        //xoa cac item da dc so huu khoi ds
        {
            int maxIndex=itemsLeft.size()-1;
            for (; maxIndex>-1; maxIndex--) {
                Item i= itemsLeft.get(maxIndex);
                if (i.isOwned)  itemsLeft.remove(i);
            }
        }

        //sout de kiem tra
        for(Occupation o: occupationsLeft){
            System.out.println("occupation thu " + occupationsLeft.indexOf(o)  + " la occu " +o.getOccupationType());
        }

        for(Item i: itemsLeft){
            System.out.println("item thu " + itemsLeft.indexOf(i)  + " la item " +i.getItemType());
        }

        for (int i=0; i<8;i++){
            Player p=playersList.get(i);
            System.out.println(p.getUsername() + " o team   "+ p.getTeam()
                    + " dang co occu  " +p.getOccupation().getOccupationType() + "  dang co item  " + p.itemsList.get(0).getItemType());
        }
    }

    public void gamePlay(){
        playersList.get(gameTurn%8).playTurn();
        gameTurn++;
    }




}
