package com.example.hoangelato.coachridetodevilcastle.Activities.GameplayActivities;

import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * Created by hoangelato on 26/07/2016.
 */

public class GameplayActivity extends AppCompatActivity {

    public GamePlayActivityBinding binding;

    public boolean finishedLoading = false;

    public ArrayList<ImageView> playerItemsView= new ArrayList<>();
    public ArrayList<TextView> numberofItemsOfAllPlayersView = new ArrayList<>();
    public ArrayList<ImageView> chooseOneIn7OthersPlayers = new ArrayList<>();
    public ArrayList<ImageButton> chooseOneOfYourItems = new ArrayList<>();
    public ArrayList<ImageView> checkedItemsView = new ArrayList<>();

    public View.OnClickListener buttonShowItemAction;

    Player mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        binding = DataBindingUtil.setContentView(this, R.layout.game_play_activity);

        if (getIntent().getBooleanExtra(GameTags.IS_HOST, false)) {
            mPlayer = HostWaitingActivity.mPlayer;
        } else {
            mPlayer = ClientWaitingActivity.mPlayer;
        }
        mPlayer.setGameplayActivity(this);

        while (!mPlayer.finishedLoadingDataFromHost) {
            //wait for player to finish getting data from host
        }

        initView();

        finishedLoading = true;
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
        initMainPlayerTeamSlot();
        updateAllPlayers();
    }


    public void updateAllPlayers() {
        updateMainPlayerOccuAndItems();
        updateOtherPlayersStatus();

    }

    private void updateOtherPlayersStatus() {
        updateAllPlayerOccu();
        updateAllPlayerItemCount();
        updateCheckedItems();
    }

    private void updateCheckedItems() {
        for(int i=0; i<5;i++){
            checkedItemsView.get(i).setBackground(null);
            checkedItemsView.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private void updateAllPlayerOccu() {
        for(int i = 0; i < mPlayer.mHostData.numberOfPlayers-1; i++) {
            PlayerData indexingPlayerData = mPlayer.mHostData.playersList.get((mPlayer.position + i+1) % mPlayer.mHostData.numberOfPlayers);
            if (indexingPlayerData.getOccupation().getUsed()) {
                chooseOneIn7OthersPlayers.get(i).setImageResource(indexingPlayerData.getOccupation().getOccupationSrc());
            } else chooseOneIn7OthersPlayers.get(i).setImageResource(R.drawable.occupation_backside);
        }
    }

    private void updateMainPlayerOccuAndItems() {
        PlayerData mData = mPlayer.getPlayerData();
        binding.player1OccuSlot.setImageResource(mPlayer.getPlayerData().getOccupation().getOccupationSrc());
        for (int i=0; i<mData.itemsList.size(); i++){
            playerItemsView.get(i).setImageResource(mData.itemsList.get(i).getItemSrc());
        }
    }

    private void updateAllPlayerItemCount() {

        for(int i = 0; i < mPlayer.mHostData.numberOfPlayers-1; i++) {
            numberofItemsOfAllPlayersView.get(i).setText(
                    mPlayer.mHostData.playersList.get(
                            (mPlayer.position + i+1) % mPlayer.mHostData.numberOfPlayers
                    ).itemsList.size() + ""
            );
        }

        binding.hostNumberItems.setText(mPlayer.mHostData.itemsLeft.size()+"");
        binding.hostNumberOccu.setText(mPlayer.mHostData.occupationsLeft.size()+"");
    }

    private void initMainPlayerTeamSlot() {
        PlayerData mData = mPlayer.getPlayerData();
        if (mData.getTeam() == 1) {
            binding.player1TeamSlot.setImageResource(R.drawable.team_blue);
        } else {
            binding.player1TeamSlot.setImageResource(R.drawable.team_red);
        }

    }

    private void initShowItemsButton() {
        buttonShowItemAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("button", "clicked");
                if (binding.itemsSlot.getVisibility() == View.INVISIBLE) {
                    showView(binding.itemsSlot);
                } else {
                    hideView(binding.itemsSlot);
                }
            }
        };
        binding.btnItemsOnHand.setOnClickListener(buttonShowItemAction);
    }

    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }
}
