package com.example.hoangelato.coachridetodevilcastle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Collections;
import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {
    public static Host host;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initGame();
        initHost();
    }


    private void initHost() {
        host=new Host();

    }

    private void initGame() {
        //find id

    }

}
