package com.example.hoangelato.coachridetodevilcastle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Collections;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {
    public static Host host;
    ImageView imgPlayer1Team, imgPlayer1Occu;
    ImageView imgPlayer2Team, imgPlayer2Occu;
    ImageView imgPlayer3Team, imgPlayer3Occu;
    ImageView imgPlayer4Team, imgPlayer4Occu;
    ImageView imgPlayer5Team, imgPlayer5Occu;
    ImageView imgPlayer6Team, imgPlayer6Occu;
    ImageView imgPlayer7Team, imgPlayer7Occu;
    ImageView imgPlayer8Team, imgPlayer8Occu;
    Button btnSupportATK, btnSupportDEF, btnAbstain;
    Player p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initGame();
        initHost();
        loadImg();
    }

    private void loadImg() {
        int indexP = host.playersList.indexOf(p);
        if(p.getTeam()==1) imgPlayer1Team.setImageResource(R.drawable.team_blue);
        else if (p.getTeam() ==2) imgPlayer1Team.setImageResource(R.drawable.team_red);
        switch (p.getOccupation().getOccupationType()){
            case 0:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_bodyguard);
                break;
            case 1:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_clairvoyant);
                break;
            case 2:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_diplomat);
                break;
            case 3:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_doctor);
                break;
            case 4:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_duelist);
                break;
            case 5:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_grandmaster);
                break;
            case 6:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_hypnotist);
                break;
            case 7:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_poisonmixer);
                break;
            case 8:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_priest);
                break;
            case 9:
                imgPlayer1Occu.setImageResource(R.drawable.occupation_thug);
                break;
            default:break;
        }

    }


    private void initHost() {
        host=new Host();
        //connect to host
        p = new Player("This device", host);

    }

    private void initGame() {
        //find id
        imgPlayer1Team = (ImageView) findViewById(R.id.player1_team_slot);
        imgPlayer1Occu = (ImageView) findViewById(R.id.player1_occu_slot);
        imgPlayer2Team = (ImageView) findViewById(R.id.player2_team_slot);
        imgPlayer2Occu = (ImageView) findViewById(R.id.player2_occu_slot);
        imgPlayer3Team = (ImageView) findViewById(R.id.player3_team_slot);
        imgPlayer3Occu = (ImageView) findViewById(R.id.player3_occu_slot);
        imgPlayer4Team = (ImageView) findViewById(R.id.player4_team_slot);
        imgPlayer4Occu = (ImageView) findViewById(R.id.player4_occu_slot);
        imgPlayer5Team = (ImageView) findViewById(R.id.player5_team_slot);
        imgPlayer5Occu = (ImageView) findViewById(R.id.player5_occu_slot);
        imgPlayer6Team = (ImageView) findViewById(R.id.player6_team_slot);
        imgPlayer6Occu = (ImageView) findViewById(R.id.player6_occu_slot);
        imgPlayer7Team = (ImageView) findViewById(R.id.player7_team_slot);
        imgPlayer7Occu = (ImageView) findViewById(R.id.player7_occu_slot);
        imgPlayer8Team = (ImageView) findViewById(R.id.player8_team_slot);
        imgPlayer8Occu = (ImageView) findViewById(R.id.player8_occu_slot);

        btnSupportATK = (Button) findViewById(R.id.btn_sp_atk);
        btnSupportDEF = (Button) findViewById(R.id.btn_sp_def);
        btnAbstain = (Button) findViewById(R.id.btn_abstain);
    }

}
