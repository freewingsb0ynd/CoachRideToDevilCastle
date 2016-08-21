package com.example.hoangelato.coachridetodevilcastle.GameModels;

import android.os.Bundle;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hoangelato.coachridetodevilcastle.Activities.GameplayActivities.GameplayActivity;
import com.example.hoangelato.coachridetodevilcastle.Activities.MainActivity;
import com.example.hoangelato.coachridetodevilcastle.CustomView.OnItemClickListener;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;


/**
 * Created by bloe on 19/08/2016.
 */
public class Player {

    public Client mClient;
    public HostData mHostData;

    public GameplayActivity gm;

    public int position;

    public boolean finishedLoadingDataFromHost = false;

    public Player(Client mClient) {
        this.mClient = mClient;
        setupClient();
    }

    public void setGameplayActivity(GameplayActivity gm) {
        this.gm = gm;
    }

    public void setupClient() {
        mClient.addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                String action = data.getString(NetworkTags.ACTION_TAG, "null");
                String gameAction = data.getString(GameTags.GAME_ACTION_TAG, "null");

                switch (action) {
                    case NetworkTags.ACTION_PUSH_NEW_DATA_TO_PLAYERS :
                        mHostData = (HostData) data.getSerializable(GameTags.HOST_DATA);

                        if (finishedLoadingDataFromHost) {
                            gm.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gm.updateAllPlayers();
                                }
                            });
                        }

                        finishedLoadingDataFromHost = true;
                        break;
                    case NetworkTags.ACTION_PUSH_PLAYER_POSITION_TO_PLAYER:
                        position = data.getInt(NetworkTags.PLAYER_POSITION);
                }

                switch (gameAction) {
                    case GameTags.ACTION_PLAY_TURN :
                        playTurn();
                        break;

                    case GameTags.ACTION_OFFER_TRADE :
                        answerTrade(data);
                        break;

                }
            }

            @Override
            public void onNewConnection(Connection connection) {

            }

            @Override
            public void onConnectFail(String reason, String destinationIp) {

            }

            @Override
            public void onInitialDataReceived(Bundle data, Connection connection) {

            }

            @Override
            public void onDisconnected(Bundle message, Connection connection) {

            }

            @Override
            public void onLosingConnection(Connection connection) {

            }
        });
        pushPlayerInitialDataToHost();
    }

    public void offerTrade() {

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.hideView(gm.binding.listActionsDialog);
            }
        });

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.binding.tvInfoDialog.setText("Choose player you want to trade with");
                gm.showView(gm.binding.infoDialog);

                gm.binding.btnOkInfoDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gm.hideView(gm.binding.infoDialog);
                            }
                        });
                    }
                });
            }
        });

        for(int i = 0; i < mHostData.numberOfPlayers; i++) {

            final Bundle offerTradePutToHost = new Bundle();

            offerTradePutToHost.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_SEND_TO_OTHER_CLIENT);
            offerTradePutToHost.putInt(NetworkTags.FROM_CLIENT, position);
            offerTradePutToHost.putString(GameTags.GAME_ACTION_TAG, GameTags.ACTION_OFFER_TRADE);

            final Integer I = i;

            gm.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gm.chooseOneIn7OthersPlayers.get(I).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            offerTradePutToHost.putInt(NetworkTags.TO_CLIENT, (I + position + 1)%mHostData.numberOfPlayers);

                            gm.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gm.showView(gm.binding.itemsSlot);

                                    for(int i = 0; i < getPlayerData().itemsList.size(); i++) {
                                        final Integer I = i;

                                        gm.chooseOneOfYourItems.get(I).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                gm.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        gm.hideView(gm.binding.itemsSlot);
                                                    }
                                                });


                                                offerTradePutToHost.putInt(GameTags.CHOSED_ITEM, I);
                                                mClient.send(offerTradePutToHost);

                                                removeAllClickListener();
                                            }
                                        });
                                    }
                                }
                            });


                        }
                    });
                }
            });

        }
    }

    private void answerTrade(Bundle data) {
        final int offererIndex = data.getInt(NetworkTags.FROM_CLIENT);
        int offeredItemIndex = data.getInt(GameTags.CHOSED_ITEM);
        final Item offeredItem = mHostData.playersList.get(offererIndex).getItemsList().get(offeredItemIndex);

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.binding.tvOffererInfo.setText(
                        mHostData.playersList.get(offererIndex).playerName +
                                " offer you " +
                                offeredItem.getItemName()
                );

                gm.binding.itemOfferedSlot.setImageResource(offeredItem.getItemSrc());

                switch (offeredItem.getItemType()) {
                    case 4:
                    case 5:
                        gm.binding.btnRefuseTrade.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }

                gm.binding.btnRefuseTrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishTrade(offererIndex, offeredItem, null);
                    }
                });

                gm.binding.btnAgreeTrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        gm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gm.hideView(gm.binding.itemOffered);

                                gm.showView(gm.binding.itemsSlot);
                            }
                        });

                        for(int i = 0; i < getPlayerData().itemsList.size(); i++) {
                            final int I = i;

                            gm.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gm.chooseOneOfYourItems.get(I).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            switch (offeredItem.getItemType()) {
                                                case 2:
                                                    if (getPlayerData().itemsList.get(I).getItemType() == 3) {
                                                        gm.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(gm, "Can't trade 2 bags", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                        if (getPlayerData().itemsList.size() == 1) {
                                                            finishTrade(offererIndex, offeredItem, null);
                                                        }
                                                        break;
                                                    }
                                                case 3:
                                                    if (getPlayerData().itemsList.get(I).getItemType() == 2) {
                                                        gm.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(gm, "Can't trade 2 bags", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                        if (getPlayerData().itemsList.size() == 1) {
                                                            finishTrade(offererIndex, offeredItem, null);
                                                        }
                                                        break;
                                                    }
                                                default:
                                                    finishTrade(offererIndex, offeredItem, getPlayerData().getItemsList().get(I));
                                                    break;

                                            }
                                        }
                                    });
                                }
                            });

                        }
                    }
                });

                gm.showView(gm.binding.itemOffered);
            }
        });

    }

    private void finishTrade(int sender, Item receivedItem, Item sentItem) {
        if (sentItem != null) {
            getPlayerData().itemsList.remove(sentItem);
            getPlayerData().itemsList.add(receivedItem);

            mHostData.playersList.get(sender).itemsList.remove(receivedItem);
            mHostData.playersList.get(sender).itemsList.add(sentItem);

            pushNewDataToHost();
        }

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.hideView(gm.binding.itemsSlot);
            }
        });

        endTurn();
    }

    public void playTurn() {
        while(gm == null) {
        }
        while (!gm.finishedLoading){
        }

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.showView(gm.binding.listActionsDialog);

                gm.binding.btnTrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {offerTrade();}});

                gm.binding.btnEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        endTurn();
                    }
                });
            }
        });
    }

    private void endTurn() {
        Bundle bundle = new Bundle();
        bundle.putString(GameTags.GAME_ACTION_TAG, GameTags.ACTION_FINISH_TURN);

        mClient.send(bundle);

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.hideView(gm.binding.listActionsDialog);
            }
        });
    }

    private void pushPlayerInitialDataToHost() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_PLAYER_INITIAL_DATA_TO_HOST);
        bundle.putString(NetworkTags.NAME_TAG, MainActivity.USERNAME);
        //other initial data

        mClient.send(bundle);
    }

    public void pushNewDataToHost() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_PUSH_NEW_DATA_TO_HOST);
        bundle.putSerializable(GameTags.HOST_DATA, mHostData);
        mClient.send(0, bundle);
    }

    private void removeAllClickListener() {
        //TODO
    }

    public PlayerData getPlayerData() {
        return mHostData.playersList.get(position);
    }
}
