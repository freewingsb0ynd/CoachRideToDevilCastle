package com.example.hoangelato.coachridetodevilcastle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {
    public static Host host;
    public static Occupation occupation0, occupation1, occupation2, occupation3, occupation4,
            occupation5, occupation6, occupation7, occupation8, occupation9;
    public static Item item0, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10,
            item11, item12, item13, item14, item15, item16, item17, item18, item19, item20;
    public static int gameTurn;
    Player humanPlayer, botPlayer1, botPlayer2, botPlayer3, botPlayer4, botPlayer5, botPlayer6, botPlayer7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initGame();
        initPlayers();
        initHost();
        initAndShuffleCards();
        addPlayersToHost();


    }

    private void initAndShuffleCards() {
        for (int i=0 ; i<8; i++ ) System.out.println("teamBudget: phan tu thu " + i + " la " + host.teamBudget[i]);
        System.out.println("\n");
        shuffleArray(host.teamBudget);

        for (int i=0 ; i<8; i++ ) System.out.println("teamBudget: phan tu thu " + i + " la" + host.teamBudget[i]);
        System.out.println("\n");

        for (int i=0 ; i<8; i++ ) System.out.println("orderList: phan tu thu " + i + " la " + host.orderList[i]);
        System.out.println("\n");
        shuffleArray(host.orderList);

        for (int i=0 ; i<8; i++ ) System.out.println("orderList: phan tu thu " + i + " la" + host.orderList[i]);
        System.out.println("\n");

        //initialize 10 occupation
        occupation0 = new Occupation(0); host.occupationsLeft.add(0,occupation0);
        occupation1 = new Occupation(1); host.occupationsLeft.add(1,occupation1);
        occupation2 = new Occupation(2); host.occupationsLeft.add(2,occupation2);
        occupation3 = new Occupation(3); host.occupationsLeft.add(3,occupation3);
        occupation4 = new Occupation(4); host.occupationsLeft.add(4,occupation4);
        occupation5 = new Occupation(5); host.occupationsLeft.add(5,occupation5);
        occupation6 = new Occupation(6); host.occupationsLeft.add(6,occupation6);
        occupation7 = new Occupation(7); host.occupationsLeft.add(7,occupation7);
        occupation8 = new Occupation(8); host.occupationsLeft.add(8,occupation8);
        occupation9 = new Occupation(9); host.occupationsLeft.add(9,occupation9);


        //initialize 21 items
        item0  = new Item(0) ; host.itemsLeft.add(0,item0);
        item1  = new Item(0) ; host.itemsLeft.add(1,item1);
        item2  = new Item(0) ; host.itemsLeft.add(2,item2);
        item3  = new Item(1) ; host.itemsLeft.add(3,item3);
        item4  = new Item(1) ; host.itemsLeft.add(4,item4);
        item5  = new Item(1) ; host.itemsLeft.add(5,item5);
        item6  = new Item(2) ; host.itemsLeft.add(6,item6);
        item7  = new Item(3) ; host.itemsLeft.add(7,item7);
        item8  = new Item(4) ; host.itemsLeft.add(8,item8);
        item9  = new Item(5) ; host.itemsLeft.add(9,item9);
        item10 = new Item(6) ; host.itemsLeft.add(10,item10);
        item11 = new Item(7) ; host.itemsLeft.add(11,item11);
        item12 = new Item(8) ; host.itemsLeft.add(12,item12);
        item13 = new Item(9) ; host.itemsLeft.add(13,item13);
        item14 = new Item(10); host.itemsLeft.add(14,item14);
        item15 = new Item(11); host.itemsLeft.add(15,item15);
        item16 = new Item(12); host.itemsLeft.add(16,item16);
        item17 = new Item(13); host.itemsLeft.add(17,item17);
        item18 = new Item(14); host.itemsLeft.add(18,item18);
        item19 = new Item(15); host.itemsLeft.add(19,item19);
        item20 = new Item(16); host.itemsLeft.add(20,item20);


    }


    private void addPlayersToHost() {



        host.playersList.add(0, humanPlayer);   humanPlayer.setTeam(host.teamBudget[0]);    humanPlayer.setOrder(host.orderList[0]);
        host.playersList.add(1, botPlayer1);    botPlayer1.setTeam(host.teamBudget[1]);     botPlayer1.setOrder(host.orderList[1]);
        host.playersList.add(2, botPlayer2);    botPlayer2.setTeam(host.teamBudget[2]);     botPlayer2.setOrder(host.orderList[2]);
        host.playersList.add(3, botPlayer3);    botPlayer3.setTeam(host.teamBudget[3]);     botPlayer3.setOrder(host.orderList[3]);
        host.playersList.add(4, botPlayer4);    botPlayer4.setTeam(host.teamBudget[4]);     botPlayer4.setOrder(host.orderList[4]);
        host.playersList.add(5, botPlayer5);    botPlayer5.setTeam(host.teamBudget[5]);     botPlayer5.setOrder(host.orderList[5]);
        host.playersList.add(6, botPlayer6);    botPlayer6.setTeam(host.teamBudget[6]);     botPlayer6.setOrder(host.orderList[6]);
        host.playersList.add(7, botPlayer7);    botPlayer7.setTeam(host.teamBudget[7]);     botPlayer7.setOrder(host.orderList[7]);

        for (Player p: host.playersList){
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

    private void initHost() {
        host=new Host();


        host.occupationsLeft.add(0, new Occupation(0));

        //... more occupations

        host.itemsLeft.add(0, new Item(0));

        //... more items
    }

    private void initGame() {
        //find id

        gameTurn=0;
    }

}
