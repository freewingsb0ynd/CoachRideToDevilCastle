package com.example.hoangelato.coachridetodevilcastle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    public static Host host;
    public static Occupation occupation;
    public static int gameTurn;
    Player humanPlayer, botPlayer1, botPlayer2, botPlayer3, botPlayer4, botPlayer5, botPlayer6, botPlayer7;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGame();
        initPlayers();
        initHost();
        addPlayersToHost();

    }

    private void addPlayersToHost() {
        for (int i=0 ; i<7; i++ ) System.out.println("phan tu thu " + i + " la " + host.teamBudget[i]);
        System.out.println("\n");
        shuffleArray(host.teamBudget);

        for (int i=0 ; i<7; i++ ) System.out.println("phan tu thu " + i + " la" + host.teamBudget[i]);
        System.out.println("\n");

        host.playersList.add(0, humanPlayer);
        host.playersList.add(1, botPlayer1);    botPlayer1.setTeam(host.teamBudget[0]);
        host.playersList.add(2, botPlayer2);    botPlayer2.setTeam(host.teamBudget[1]);
        host.playersList.add(3, botPlayer3);    botPlayer3.setTeam(host.teamBudget[2]);
        host.playersList.add(4, botPlayer4);    botPlayer4.setTeam(host.teamBudget[3]);
        host.playersList.add(5, botPlayer5);    botPlayer5.setTeam(host.teamBudget[4]);
        host.playersList.add(6, botPlayer6);    botPlayer6.setTeam(host.teamBudget[5]);
        host.playersList.add(7, botPlayer7);    botPlayer7.setTeam(host.teamBudget[6]);

        for (Player p: host.playersList){
            System.out.println(p.getUsername() + " o team   "+ p.getTeam()) ;
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

        humanPlayer =new Player("Day la nguoi choi", 1, "", 0, "Pass");
        botPlayer1 =new Player("Day la Bot1", 1, "", 1, "Pass");
        botPlayer2 =new Player("Day la Bot2", 1, "", 2, "Pass");
        botPlayer3 =new Player("Day la Bot3", 1, "", 3, "Pass");
        botPlayer4 =new Player("Day la Bot4", 1, "", 4, "Pass");
        botPlayer5 =new Player("Day la Bot5", 1, "", 5, "Pass");
        botPlayer6 =new Player("Day la Bot6", 1, "", 6, "Pass");
        botPlayer7 =new Player("Day la Bot7", 1, "", 7, "Pass");
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
