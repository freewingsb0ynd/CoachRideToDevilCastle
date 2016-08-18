package com.example.hoangelato.coachridetodevilcastle;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangelato.coachridetodevilcastle.Network.Client;
import com.example.hoangelato.coachridetodevilcastle.Network.Connection;
import com.example.hoangelato.coachridetodevilcastle.Network.EventListener;
import com.example.hoangelato.coachridetodevilcastle.Network.NetworkNode;
import com.example.hoangelato.coachridetodevilcastle.Network.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TransferQueue;

public class GamePlayActivity extends AppCompatActivity {
    public static Host host;
    Client mClient;
    ImageView imgPlayer1Team; ImageButton imgPlayer1Occu;
    ImageView imgPlayer2item, imgPlayer2Occu;
    ImageView imgPlayer3item, imgPlayer3Occu;
    ImageView imgPlayer4item, imgPlayer4Occu;
    ImageView imgPlayer5item, imgPlayer5Occu;
    ImageView imgPlayer6item, imgPlayer6Occu;
    ImageView imgPlayer7item, imgPlayer7Occu;
    ImageView imgPlayer8item, imgPlayer8Occu;
    ImageButton imgPlayer1Item1, imgPlayer1Item2, imgPlayer1Item3, imgPlayer1Item4, imgPlayer1Item5;
    ImageView imgHostItem, imgHostOccu;
    LinearLayout itemsSlot;

    RelativeLayout checkedItemsDialog;
    ImageView checkedItem1,checkedItem2,checkedItem3,checkedItem4,checkedItem5;
    ArrayList<ImageView> checkedItemsView;
    Button btnOkCheckedItemDialog;

    TextView tvCheckedItemsDialog;
    TextView hostNumberofItems, hostNumberofOccu;
    ImageButton btnItemsOnHand;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game_play);
        initGame();
        initHost();
        loadImg();
        gameplay();
    }

    private void loadImg() {
        p= host.humanPlayer;
        indexP = host.playersList.indexOf(p);
        if (p.getTeam() == 1) imgPlayer1Team.setImageResource(R.drawable.team_blue);
        else if (p.getTeam() == 2) imgPlayer1Team.setImageResource(R.drawable.team_red);
        imgPlayer1Occu.setImageResource(p.getOccupation().getOccupationSrc());
        for (int i=0; i<p.itemsList.size(); i++){
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

    public void updateToHost() {
        Bundle bundle = new Bundle();
        bundle.putString(NetworkNode.ACTION_TAG, Host.UPDATE_DATA);
        bundle.putSerializable("host", host);

        mClient.send(0, bundle);
    }

    private void initHost() {
        mClient.addEventListener(new EventListener() {
            @Override
            public void onDataReceived(Bundle data, Connection connection) {
                String action = data.getString(NetworkNode.ACTION_TAG);

                if (action.equals("new Host")) {
                    host = (Host) data.getSerializable("Host");
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
        //connect to host
        //p = new Player("This device", host);

    }
    boolean itemsOnHandDialogVisible = false;
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

        btnItemsOnHand = (ImageButton) findViewById(R.id.btn_items_on_hand);
        itemsSlot = (LinearLayout) findViewById(R.id.items_slot);
        imgPlayer1Item1= (ImageButton) findViewById(R.id.item1_slot);
        imgPlayer1Item2= (ImageButton) findViewById(R.id.item2_slot);
        imgPlayer1Item3= (ImageButton) findViewById(R.id.item3_slot);
        imgPlayer1Item4= (ImageButton) findViewById(R.id.item4_slot);
        imgPlayer1Item5= (ImageButton) findViewById(R.id.item5_slot);
        playerItemsView.add(imgPlayer1Item1);
        playerItemsView.add(imgPlayer1Item2);
        playerItemsView.add(imgPlayer1Item3);
        playerItemsView.add(imgPlayer1Item4);
        playerItemsView.add(imgPlayer1Item5);

        btnItemsOnHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!itemsOnHandDialogVisible) {
                    itemsSlot.setVisibility(View.VISIBLE);
                    itemsOnHandDialogVisible = true;
                } else {
                    itemsSlot.setVisibility(View.INVISIBLE);
                    itemsOnHandDialogVisible = false;
                }
            }
        });


        btnSupportATK = (Button) findViewById(R.id.btn_sp_atk);
        btnSupportDEF = (Button) findViewById(R.id.btn_sp_def);
        btnAbstain = (Button) findViewById(R.id.btn_abstain);

        listActionDialog = (RelativeLayout) findViewById(R.id.list_actions_dialog);
        btnTrade = (Button) findViewById(R.id.btn_trade);
        btnWar = (Button) findViewById(R.id.btn_war);
        btnDeclare = (Button) findViewById(R.id.btn_declare);
        btnEnd =(Button) findViewById(R.id.btn_end);

        infoDialog = (RelativeLayout) findViewById(R.id.info_dialog);
        tvInfoDialog = (TextView) findViewById(R.id.tv_info_dialog);
        btnOkInfoDialog = (Button) findViewById(R.id.btn_ok_info_dialog);

        chooseOneIn7OthersPlayers.add(imgPlayer2Occu);
        chooseOneIn7OthersPlayers.add(imgPlayer3Occu);
        chooseOneIn7OthersPlayers.add(imgPlayer4Occu);
        chooseOneIn7OthersPlayers.add(imgPlayer5Occu);
        chooseOneIn7OthersPlayers.add(imgPlayer6Occu);
        chooseOneIn7OthersPlayers.add(imgPlayer7Occu);
        chooseOneIn7OthersPlayers.add(imgPlayer8Occu);

        chooseOneOfYourItems.add(imgPlayer1Item1);
        chooseOneOfYourItems.add(imgPlayer1Item2);
        chooseOneOfYourItems.add(imgPlayer1Item3);
        chooseOneOfYourItems.add(imgPlayer1Item4);
        chooseOneOfYourItems.add(imgPlayer1Item5);

        checkedItemsDialog = (RelativeLayout) findViewById(R.id.checked_items_dialog);
        tvCheckedItemsDialog = (TextView) findViewById(R.id.tv_checked_items_dialog);
        checkedItem1 = (ImageView) findViewById(R.id.checked_item1_slot);
        checkedItem2 = (ImageView) findViewById(R.id.checked_item2_slot);
        checkedItem3 = (ImageView) findViewById(R.id.checked_item3_slot);
        checkedItem4 = (ImageView) findViewById(R.id.checked_item4_slot);
        checkedItem5 = (ImageView) findViewById(R.id.checked_item5_slot);
        checkedItemsView.add(checkedItem1);
        checkedItemsView.add(checkedItem2);
        checkedItemsView.add(checkedItem3);
        checkedItemsView.add(checkedItem4);
        checkedItemsView.add(checkedItem5);
        btnOkCheckedItemDialog = (Button) findViewById(R.id.btn_checked_item_done);


    }

    boolean myturn=false;
    boolean listActionDialogVisible=false;
    RelativeLayout listActionDialog;
    Button btnTrade, btnWar, btnDeclare, btnEnd;
    RelativeLayout infoDialog;
    TextView tvInfoDialog;
    Button btnOkInfoDialog;
    boolean traderChosen = false;
    Bundle tradePutToHost,tradeReceivedFromHost;
    int bundleChosedPlayer, bundleChosedItem;
    ArrayList<ImageView> chooseOneIn7OthersPlayers = new ArrayList<>();
    ArrayList<ImageButton> chooseOneOfYourItems = new ArrayList<>();

    private void gameplay() {
 //       while (host.wonTeam==0) {  //khi chua ben nao thang
            //den turn, mServer goi client
            myturn = true;
            //hien dialog chon action
            if (!listActionDialogVisible){
                listActionDialog.setVisibility(View.VISIBLE);
                listActionDialogVisible=true;
   //             System.out.println("dm sao khong hien len @@@@@");
            }
            if (listActionDialogVisible) {
                tradePutToHost = new Bundle();
                btnTrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listActionDialog.setVisibility(View.INVISIBLE);
                        listActionDialogVisible=false;

                        tvInfoDialog.setText("Choose player you want to trade with");
                        infoDialog.setVisibility(View.VISIBLE);
   //                     System.out.println("chay vao day roi dm");
                        btnOkInfoDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                infoDialog.setVisibility(View.INVISIBLE);
                                traderChosen = true;
  //                              System.out.println("fksdsjkbksd");
                            }
                        });

                        for (int i = 2; i<9;i++) {
                            final int finalI = i;
                            chooseOneIn7OthersPlayers.get(i-2).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (traderChosen){
                                        System.out.println("chon vao p " + finalI);
                                        bundleChosedPlayer = finalI;
                                        tradePutToHost.putSerializable("ChosedPlayer",
                                                host.playersList.get((host.playersList.indexOf(p)+bundleChosedPlayer)%8));
                                    }
                                    itemsSlot.setVisibility(View.VISIBLE);
                                    itemsOnHandDialogVisible = true;
                                    for (int j=0; j<p.itemsList.size();j++){
                                        final int finalJ = j;
                                        chooseOneOfYourItems.get(j).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                bundleChosedItem = finalJ;
                                                itemsSlot.setVisibility(View.INVISIBLE);
                                                itemsOnHandDialogVisible = false;


                                                tradePutToHost.putSerializable("ChosedItems", p.itemsList.get(bundleChosedItem));
                                            }
                                        });
                                    }


                                    //day bundle di, cho kq
                                    //xu ly kq tren server

                                    //kq tra ve
                                    //if( /*request/ received code true*/ )
                                    {
                                    //fake
                                        tradeReceivedFromHost.putSerializable("ItemReceived", host.item0);
                                        tradeReceivedFromHost.putSerializable("PlayerTraded", host.botPlayer1);
                                        //

                                        Item itemSent = (Item) tradePutToHost.getSerializable("ChosedItems");
                                        Item itemReceived = (Item) tradeReceivedFromHost.getSerializable("ItemReceived");
                                        Player playerTraded = (Player) tradeReceivedFromHost.getSerializable("PlayerTraded");


                                        //xet effect

                                        if (itemSent.getItemType()==5){
                                            //sout "Your traded item has prevent the effect of the received item"
                                        }
                                        else if (itemReceived.getItemType()==5){
                                            //sout "You had received Broken Mirror. Your trade-in effect of your item had been prevented"
                                        }
                                        else switch (itemSent.getItemType()){
                                                case 2: //bag
                                                case 3:
                                                    p.itemsList.remove(itemSent);
                                                    Item itemDraw= host.itemsLeft.get(host.itemsLeft.size()-1);
                                                    p.itemsList.add(itemDraw);
                                                    host.itemsLeft.remove(itemDraw);
                                                    sendSignalByItemType(itemReceived.getItemType());

                                                    //hien dialog thong bao voi nguoi choi khac
                                                    break;
                                                case 7:
                                                    Occupation occuSwap = p.getOccupation();
                                                    occuSwap.setUsed(false);
                                                    occuSwap.isOccupied=false;

                                                    p.setOccupation(host.occupationsLeft.get(0));
                                                    p.getOccupation().isOccupied=true;
                                                    imgPlayer1Occu.setImageResource(p.getOccupation().getOccupationSrc());

                                                    host.occupationsLeft.remove(p.getOccupation());
                                                    host.occupationsLeft.add(occuSwap);

                                                    //hien dialog thong bao voi nguoi choi khac
                                                    break;
                                                case 10:



                                                    //xem team receiver

                                                    //hien dialog thong bao voi nguoi choi khac
                                                    break;
                                                case 12:
                                                    for (int k=0; k<playerTraded.itemsList.size(); k++){
                                                        checkedItemsView.get(k).setImageResource(playerTraded.itemsList.get(k).getItemSrc());
                                                    }

                                                    checkedItemsDialog.setVisibility(View.VISIBLE);
                                                    tvCheckedItemsDialog.setVisibility(View.INVISIBLE);

                                                    btnOkCheckedItemDialog.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            checkedItemsDialog.setVisibility(View.INVISIBLE);
                                                        }
                                                    });

                                                    //xem itemlist receiver

                                                    //hien dialog thong bao voi nguoi choi khac
                                                    break;
                                                case 13:
                                                    //sextant
                                                    //hien chon item chuyen di
                                                    //chon nguoi ben canh

                                                    //hien dialog thong bao voi nguoi choi khac
                                                    break;
                                                case 15:
                                                    //doi occu voi receiver
                                                    Occupation o = p.getOccupation();
                                                    p.setOccupation(playerTraded.getOccupation());
                                                    playerTraded.setOccupation(o);
                                                    p.getOccupation().setUsed(false);
                                                    playerTraded.getOccupation().setUsed(false);

                                                    //hien dialog thong bao voi nguoi choi khac
                                                    break;

                                                default:
                                                    break;

                                            }


                                        //switch ()


//                                        int itemIndex = tradePutToHost.getInt("ChosedItems");
//                                        int playerReceiverIndex = (tradePutToHost.getInt("ChosedItems") + host.playersList.indexOf(p))%8;
//                                        Item itemTrade = new Item(itemIndex);
//                                        Item itemReceived = new Item(i1);
//                                        effectOfItemTrade(host, itemTrade, host.playersList.indexOf(p), playerReceiverIndex );
//                                        effectOfItemTrade(host, itemReceived, playerReceiverIndex, host.playersList.indexOf(p));


                                    }
                                    //else hien noti, end turn
                                }
                            });


                        }


                    }
                });

                btnWar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listActionDialog.setVisibility(View.INVISIBLE);
                        listActionDialogVisible=false;
                        tvInfoDialog.setText("Choose player you want to war with");
                        infoDialog.setVisibility(View.VISIBLE);
                        btnOkInfoDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                infoDialog.setVisibility(View.INVISIBLE);
                                traderChosen = true;
                            }
                        });
                    }
                });


            }


  //      }


    }

    public void sendSignalByItemType(int itemType){
        //notify sang device khac
    }


    //hàm sida

    public void  effectOfItemTrade(Host h, Item i, int indexSender, int indexReceiver){
        Player sender = h.playersList.get(indexSender);
        Player receiver = h.playersList.get(indexReceiver);
        switch (i.getItemType()){
            case 5: //broken mirror
                //
                break;
            case 2: //bag
            case 3:
                Item itemDraw= host.itemsLeft.get(host.itemsLeft.size()-1);
                sender.itemsList.remove(i);
                sender.itemsList.add(itemDraw);

                host.itemsLeft.remove(itemDraw);

                break;
            case 4:
                //auto nhan
                //hien info dialog
                break;
            case 7:
                //doi 1 occu voi occuLeft
                //hien dialog voi
                //hien bang occuLeft
                break;
            case 10:
                //xem team receiver
                break;
            case 12:
                //xem itemlist receiver
                break;
            case 13:
                //sextant
                //hien chon item chuyen di
                //chon nguoi ben canh
            case 15:
                //doi occu voi receiver
                Occupation o = sender.getOccupation();
                sender.setOccupation(receiver.getOccupation());
                receiver.setOccupation(o);
                sender.getOccupation().setUsed(false);
                receiver.getOccupation().setUsed(false);
                break;
            default:
                break;
        }



    }



}
