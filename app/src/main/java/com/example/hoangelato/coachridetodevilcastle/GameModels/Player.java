package com.example.hoangelato.coachridetodevilcastle.GameModels;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hoangelato.coachridetodevilcastle.Activities.GameplayActivities.GameplayActivity;
import com.example.hoangelato.coachridetodevilcastle.Activities.MainActivity;
import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkTags;
import com.example.hoangelato.coachridetodevilcastle.R;


/**
 * Created by hoangelato on 26/07/2016.
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

                    case GameTags.EXECUTE_EFFECT:
                        Item sentItem = getPlayerData().itemsList.get(data.getInt(GameTags.SENT_ITEM));
                        int sender = data.getInt(NetworkTags.FROM_CLIENT);
                        Item receivedItem = mHostData.playersList.get(
                                sender
                        ).getItemsList().get(data.getInt(GameTags.RECEIVED_ITEM));

                        executeItemEffect(receivedItem, sentItem, sender);

                        if (data.getBoolean(GameTags.FIRST_EFFECT, false)) {
                            Bundle bundle = createBundleToOtherClient(sender);
                            bundle.putInt(GameTags.SENT_ITEM, data.getInt(GameTags.RECEIVED_ITEM));
                            bundle.putInt(GameTags.RECEIVED_ITEM, data.getInt(GameTags.SENT_ITEM));
                            bundle.putString(GameTags.GAME_ACTION_TAG, GameTags.EXECUTE_EFFECT);

                            mClient.send(bundle);
                        } else {
                            endTurn();
                        }
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

    public void executeItemEffect(Item sentItem, Item receivedItem, final int senderIndex) {
        if (receivedItem.getItemType() == 5) {
            gm.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(gm, "You had received Broken Mirror. Your trade-in effect of your item had been prevented", Toast.LENGTH_LONG);
                }
            });
        } else {
            switch (sentItem.getItemType()) {
                case 2:
                case 3:
                    Item itemDrawn = mHostData.itemsLeft.get(0);
                    mHostData.itemsLeft.remove(itemDrawn);
                    getPlayerData().itemsList.add(itemDrawn);
                    break;
                case 7:
                    Occupation occupation = getPlayerData().getOccupation();

                    occupation.isOccupied = false;
                    occupation.setUsed(false);

                    getPlayerData().setOccupation(mHostData.occupationsLeft.get(0));

                    gm.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gm.binding.player1OccuSlot.setImageResource(
                                    getPlayerData().getOccupation().getOccupationSrc()
                            );
                        }
                    });

                    mHostData.occupationsLeft.remove(getPlayerData().getOccupation());
                    mHostData.occupationsLeft.add(occupation);

                    break;
                case 10:
                    gm.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gm.binding.checkedTeamSlot.setImageResource(
                                    mHostData.playersList.get(senderIndex).getTeam() == 1 ?
                                            R.drawable.team_blue : R.drawable.team_red
                            );

                            gm.showView(gm.binding.checkedTeamAndOccuDialog);

                            gm.binding.btnCheckedTeamAndOccuDone.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            gm.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    gm.hideView(gm.binding.checkedTeamAndOccuDialog);
                                                }
                                            });
                                        }
                                    }
                            );
                        }
                    });

                    break;
                case 12:
                    gm.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0; i < mHostData.playersList.get(senderIndex).itemsList.size(); i++) {
                                final int finalI = i;
                                gm.checkedItemsView.get(finalI).setImageResource(
                                        mHostData.playersList.get(senderIndex).itemsList.get(finalI).getItemSrc()
                                );
                            }

                            gm.binding.btnCheckedItemDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    gm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gm.hideView(gm.binding.checkedItemsDialog);
                                        }
                                    });
                                }
                            });
                        }
                    });

                    break;
                case 15:
                    Occupation occupation1 = getPlayerData().getOccupation();
                    getPlayerData().setOccupation(mHostData.playersList.get(senderIndex).getOccupation());
                    mHostData.playersList.get(senderIndex).setOccupation(occupation1);

                    getPlayerData().getOccupation().setUsed(false);
                    mHostData.playersList.get(senderIndex).getOccupation().setUsed(false);

                    break;
            }

            pushNewDataToHost();
        }
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
        final int offeredItemIndex = data.getInt(GameTags.CHOSED_ITEM);
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
                        finishTrade(offererIndex, offeredItemIndex, -1);
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
                                                            finishTrade(offererIndex, offeredItemIndex, -1);
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
                                                            finishTrade(offererIndex, offeredItemIndex, -1);
                                                        }
                                                        break;
                                                    }
                                                default:
                                                    finishTrade(offererIndex, offeredItemIndex, I);
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

    private void finishTrade(int sender, int receivedItemPos, int sentItemPos) {
        Item sentItem = null;
        if (sentItemPos != -1) {
            sentItem = getPlayerData().getItemsList().get(sentItemPos);
        }
        Item receivedItem = mHostData.playersList.get(sender).getItemsList().get(receivedItemPos);

        if (sentItem != null) {
            getPlayerData().itemsList.remove(sentItem);
            getPlayerData().itemsList.add(receivedItem);

            mHostData.playersList.get(sender).itemsList.remove(receivedItem);
            mHostData.playersList.get(sender).itemsList.add(sentItem);

            pushNewDataToHost();

            Bundle bundle = createBundleToOtherClient(sender);
            bundle.putString(GameTags.GAME_ACTION_TAG, GameTags.EXECUTE_EFFECT);
            bundle.putBoolean(GameTags.FIRST_EFFECT, true);
            bundle.putInt(GameTags.SENT_ITEM, receivedItemPos);
            bundle.putInt(GameTags.RECEIVED_ITEM, sentItemPos);

            mClient.send(bundle);
        } else {
            endTurn();
        }

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gm.hideView(gm.binding.itemOffered);
            }
        });
    }

    private Bundle createBundleToOtherClient(int clientIndex) {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkTags.ACTION_TAG, NetworkTags.ACTION_SEND_TO_OTHER_CLIENT);
        bundle.putInt(NetworkTags.FROM_CLIENT, position);
        bundle.putInt(NetworkTags.TO_CLIENT, clientIndex);

        return bundle;
    }

    public void playTurn() {
        while(gm == null) {
        }
        while (!gm.finishedLoading){
        }

        gm.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("My turn", "");
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
