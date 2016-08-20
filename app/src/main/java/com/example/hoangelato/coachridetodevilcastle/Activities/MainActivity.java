package com.example.hoangelato.coachridetodevilcastle.Activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.hoangelato.coachridetodevilcastle.Activities.ClientActivities.ClientConnectActivity;
import com.example.hoangelato.coachridetodevilcastle.Activities.HostActivities.HostWaitingActivity;
import com.example.hoangelato.coachridetodevilcastle.R;
import com.example.hoangelato.coachridetodevilcastle.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String USERNAME;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView();
    }

    private void initView() {
        binding.buttonCreateGame.setOnClickListener(this);
        binding.buttonJoinGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (binding.editTextUsername.getText().toString().equals("")) {
            Toast.makeText(this, "Username can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (view == binding.buttonCreateGame) {
                moveToHostWaitingActivity();
            } else if (view == binding.buttonJoinGame) {
                moveToClientConnectActivity();
            }
        }
    }

    private void moveToClientConnectActivity() {
        Intent nextScreen = new Intent(MainActivity.this, ClientConnectActivity.class);
        startActivity(nextScreen);
    }
    private void moveToHostWaitingActivity() {
        Intent nextScreen = new Intent(MainActivity.this, HostWaitingActivity.class);
        startActivity(nextScreen);
    }
}
