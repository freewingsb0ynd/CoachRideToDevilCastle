package com.example.hoangelato.coachridetodevilcastle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {
    public static Host host;
    ImageView imgPlayer1Team; ImageButton imgPlayer1Occu;
    ImageView imgPlayer2item, imgPlayer2Occu;
    ImageView imgPlayer3item, imgPlayer3Occu;
    ImageView imgPlayer4item, imgPlayer4Occu;
    ImageView imgPlayer5item, imgPlayer5Occu;
    ImageView imgPlayer6item, imgPlayer6Occu;
    ImageView imgPlayer7item, imgPlayer7Occu;
    ImageView imgPlayer8item, imgPlayer8Occu;
    ImageView imgPlayer1Item1, imgPlayer1Item2, imgPlayer1Item3, imgPlayer1Item4, imgPlayer1Item5;
    ImageView imgHostItem, imgHostOccu;
    TextView hostNumberofItems, hostNumberofOccu;
    Button btnSupportATK, btnSupportDEF, btnAbstain;
    Player p;
    TextView player2NumberofItems, player3NumberofItems, player4NumberofItems,
            player5NumberofItems, player6NumberofItems, player7NumberofItems, player8NumberofItems;
    ArrayList<ImageView> playerItemsView= new ArrayList<>();
    ArrayList<TextView> numberofItemsOfAllPlayersView = new ArrayList<>();
    int indexP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initGame();
        initHost();
        loadImg();

    }

    private void loadImg() {
        p= host.humanPlayer;
        indexP = host.playersList.indexOf(p);
        if (p.getTeam() == 1) imgPlayer1Team.setImageResource(R.drawable.team_blue);
        else if (p.getTeam() == 2) imgPlayer1Team.setImageResource(R.drawable.team_red);
        imgPlayer1Occu.setImageResource(p.getOccupation().getOccupationSrc());
        for (int i=0; i<5; i++){
            if(i<p.itemsList.size())
            playerItemsView.get(i).setImageResource(p.itemsList.get(i).getItemSrc());
        }


        updateNumberofItemsforAllPlayers();



    }

    public void updateNumberofItemsforAllPlayers() {
        for(int i=0; i<7; i++){
            numberofItemsOfAllPlayersView.get(i).setText(host.playersList.get((indexP+i+1)%8).itemsList.size()+"");
        }
        hostNumberofItems.setText(host.itemsLeft.size()+"");
        hostNumberofOccu.setText(host.occupationsLeft.size()+"");
    }


    private void initHost() {
        host=new Host();
        //connect to host
        //p = new Player("This device", host);

    }

    private void initGame() {
        //find id
        imgPlayer1Team = (ImageView) findViewById(R.id.player1_team_slot);
        imgPlayer1Occu = (ImageButton) findViewById(R.id.player1_occu_slot);
        imgPlayer2item = (ImageView) findViewById(R.id.player2_item_slot);
        imgPlayer2Occu = (ImageView) findViewById(R.id.player2_occu_slot);
        imgPlayer3item = (ImageView) findViewById(R.id.player3_item_slot);
        imgPlayer3Occu = (ImageView) findViewById(R.id.player3_occu_slot);
        imgPlayer4item = (ImageView) findViewById(R.id.player4_item_slot);
        imgPlayer4Occu = (ImageView) findViewById(R.id.player4_occu_slot);
        imgPlayer5item = (ImageView) findViewById(R.id.player5_item_slot);
        imgPlayer5Occu = (ImageView) findViewById(R.id.player5_occu_slot);
        imgPlayer6item = (ImageView) findViewById(R.id.player6_item_slot);
        imgPlayer6Occu = (ImageView) findViewById(R.id.player6_occu_slot);
        imgPlayer7item = (ImageView) findViewById(R.id.player7_item_slot);
        imgPlayer7Occu = (ImageView) findViewById(R.id.player7_occu_slot);
        imgPlayer8item = (ImageView) findViewById(R.id.player8_item_slot);
        imgPlayer8Occu = (ImageView) findViewById(R.id.player8_occu_slot);

        player2NumberofItems= (TextView) findViewById(R.id.player2_number_items);
        player3NumberofItems= (TextView) findViewById(R.id.player3_number_items);
        player4NumberofItems= (TextView) findViewById(R.id.player4_number_items);
        player5NumberofItems= (TextView) findViewById(R.id.player5_number_items);
        player6NumberofItems= (TextView) findViewById(R.id.player6_number_items);
        player7NumberofItems= (TextView) findViewById(R.id.player7_number_items);
        player8NumberofItems= (TextView) findViewById(R.id.player8_number_items);
        numberofItemsOfAllPlayersView.add(player2NumberofItems);
        numberofItemsOfAllPlayersView.add(player3NumberofItems);
        numberofItemsOfAllPlayersView.add(player4NumberofItems);
        numberofItemsOfAllPlayersView.add(player5NumberofItems);
        numberofItemsOfAllPlayersView.add(player6NumberofItems);
        numberofItemsOfAllPlayersView.add(player7NumberofItems);
        numberofItemsOfAllPlayersView.add(player8NumberofItems);

        imgHostItem = (ImageView) findViewById(R.id.host_item_slot);
        imgHostOccu = (ImageView) findViewById(R.id.host_occu_slot);
        hostNumberofItems = (TextView) findViewById(R.id.host_number_items);
        hostNumberofOccu = (TextView) findViewById(R.id.host_number_occu);


        imgPlayer1Item1= (ImageView) findViewById(R.id.item1_slot);
        imgPlayer1Item2= (ImageView) findViewById(R.id.item2_slot);
        imgPlayer1Item3= (ImageView) findViewById(R.id.item3_slot);
        imgPlayer1Item4= (ImageView) findViewById(R.id.item4_slot);
        imgPlayer1Item5= (ImageView) findViewById(R.id.item5_slot);
        playerItemsView.add(imgPlayer1Item1);
        playerItemsView.add(imgPlayer1Item2);
        playerItemsView.add(imgPlayer1Item3);
        playerItemsView.add(imgPlayer1Item4);
        playerItemsView.add(imgPlayer1Item5);


        btnSupportATK = (Button) findViewById(R.id.btn_sp_atk);
        btnSupportDEF = (Button) findViewById(R.id.btn_sp_def);
        btnAbstain = (Button) findViewById(R.id.btn_abstain);

    }

}
