package com.example.hoangelato.coachridetodevilcastle.Activities.GameplayActivities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.hoangelato.coachridetodevilcastle.Activities.ClientActivities.ClientWaitingActivity;
import com.example.hoangelato.coachridetodevilcastle.Activities.HostActivities.HostWaitingActivity;
import com.example.hoangelato.coachridetodevilcastle.GameModels.GameTags;
import com.example.hoangelato.coachridetodevilcastle.GameModels.Player;
import com.example.hoangelato.coachridetodevilcastle.GameModels.PlayerData;
import com.example.hoangelato.coachridetodevilcastle.R;
import com.example.hoangelato.coachridetodevilcastle.databinding.GamePlayActivityBinding;

import java.util.ArrayList;

/**
 * Created by bloe on 18/08/2016.
 */

public class GameplayActivity extends AppCompatActivity {

    GamePlayActivityBinding binding;

    ArrayList<ImageView> playerItemsView= new ArrayList<>();
    ArrayList<TextView> numberofItemsOfAllPlayersView = new ArrayList<>();
    ArrayList<ImageView> chooseOneIn7OthersPlayers = new ArrayList<>();
    ArrayList<ImageButton> chooseOneOfYourItems = new ArrayList<>();
    ArrayList<ImageView> checkedItemsView = new ArrayList<>();

    View.OnClickListener buttonShowItemAction;

    Player mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.game_play_activity);

        if (getIntent().getBooleanExtra(GameTags.IS_HOST, false)) {
            mPlayer = HostWaitingActivity.mPlayer;
        } else {
            mPlayer = ClientWaitingActivity.mPlayer;
        }

        while (!mPlayer.finishedLoadingDataFromHost) {
            //wait for player to finish getting data from host
        }

        //initView();
    }

    private void initView() {
        numberofItemsOfAllPlayersView.add(binding.player2NumberItems);
        numberofItemsOfAllPlayersView.add(binding.player3NumberItems);
        numberofItemsOfAllPlayersView.add(binding.player4NumberItems);
        numberofItemsOfAllPlayersView.add(binding.player5NumberItems);
        numberofItemsOfAllPlayersView.add(binding.player6NumberItems);
        numberofItemsOfAllPlayersView.add(binding.player7NumberItems);
        numberofItemsOfAllPlayersView.add(binding.player8NumberItems);

        playerItemsView.add(binding.item1Slot);
        playerItemsView.add(binding.item2Slot);
        playerItemsView.add(binding.item3Slot);
        playerItemsView.add(binding.item4Slot);
        playerItemsView.add(binding.item5Slot);

        chooseOneIn7OthersPlayers.add(binding.player2OccuSlot);
        chooseOneIn7OthersPlayers.add(binding.player3OccuSlot);
        chooseOneIn7OthersPlayers.add(binding.player4OccuSlot);
        chooseOneIn7OthersPlayers.add(binding.player5OccuSlot);
        chooseOneIn7OthersPlayers.add(binding.player6OccuSlot);
        chooseOneIn7OthersPlayers.add(binding.player7OccuSlot);
        chooseOneIn7OthersPlayers.add(binding.player8OccuSlot);

        chooseOneOfYourItems.add(binding.item1Slot);
        chooseOneOfYourItems.add(binding.item2Slot);
        chooseOneOfYourItems.add(binding.item3Slot);
        chooseOneOfYourItems.add(binding.item4Slot);
        chooseOneOfYourItems.add(binding.item5Slot);

        checkedItemsView.add(binding.checkedItem1Slot);
        checkedItemsView.add(binding.checkedItem2Slot);
        checkedItemsView.add(binding.checkedItem3Slot);
        checkedItemsView.add(binding.checkedItem4Slot);
        checkedItemsView.add(binding.checkedItem5Slot);


        initShowItemsButton();
        initPlayerItemSlots();
    }

    private void initPlayerItemSlots() {
        //initMainPlayerTeamSlot();


    }

    private void initMainPlayerTeamSlot() {
        PlayerData mData = mPlayer.getPlayerData();
        if (mData.getTeam() == 1) {
            binding.player1TeamSlot.setImageResource(R.drawable.team_blue);
        } else {
            binding.player1TeamSlot.setImageResource(R.drawable.team_red);
        }

        for (int i=0; i<mData.itemsList.size(); i++){
            playerItemsView.get(i).setImageResource(mData.itemsList.get(i).getItemSrc());
        }
    }

    private void initShowItemsButton() {
        buttonShowItemAction = new View.OnClickListener() {
            boolean isShown = false;
            @Override
            public void onClick(View view) {
                if (!isShown) {
                    shown();
                } else {
                    hide();
                }
            }

            public void shown() {
                isShown = true;
                binding.itemsSlot.setVisibility(View.VISIBLE);
            }

            public void hide() {
                isShown = false;
                binding.itemsSlot.setVisibility(View.INVISIBLE);
            }
        };
        binding.btnItemsOnHand.setOnClickListener(buttonShowItemAction);
    }


}
