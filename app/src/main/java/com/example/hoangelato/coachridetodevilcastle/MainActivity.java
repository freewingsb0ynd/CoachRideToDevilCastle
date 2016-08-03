package com.example.hoangelato.coachridetodevilcastle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hoangelato.coachridetodevilcastle.Client.ClientActivity;

import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    Button createGameBtn;
    Button joinGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //passToGamePlayActivity();
        initView();
    }

    private void initView() {
        createGameBtn = (Button) findViewById(R.id.btn_create);
        joinGameBtn = (Button) findViewById(R.id.btn_join);

        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HostWaitingActivity.class);
                startActivity(intent);
            }
        });

        joinGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });
    }


    private void passToGamePlayActivity() {
        Intent intent= new Intent(MainActivity.this, GamePlayActivity.class);
        startActivity(intent);
    }
}
