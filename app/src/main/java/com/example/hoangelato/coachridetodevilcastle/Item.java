package com.example.hoangelato.coachridetodevilcastle;

import java.io.Serializable;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Item implements Serializable{
    private int itemType;
    private int itemSrc;
    public boolean isOwned;
    private String itemName;


    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemSrc() {
        return itemSrc;
    }

    public void setItemSrc(int itemSrc) {
        this.itemSrc = itemSrc;
    }

    public String getItemName() {
        return itemName;
    }

    public Item(int itemType) {
        this.itemType = itemType;
        switch (itemType){
            case 0:
                itemSrc= R.drawable.item_key;
                itemName = "Key";
                break;
            case 1:
                itemSrc= R.drawable.item_goblet;
                itemName = "Goblet";
                break;
            case 2:
                itemSrc= R.drawable.item_secretbagkey;
                itemName = "Secret Bag (Key)";
                break;
            case 3:
                itemSrc= R.drawable.item_secretbaggoblet;
                itemName = "Secret Bag (Goblet)";
                break;
            case 4:
                itemName = "Black Pearl";
                itemSrc= R.drawable.item_blackpearl;
                break;
            case 5:
                itemName = "Broken Mirror";
                itemSrc= R.drawable.item_brokenmirror;
                break;
            case 6:
                itemName = "Casting Knives";
                itemSrc= R.drawable.item_castingknives;
                break;
            case 7:
                itemName = "Coat";
                itemSrc= R.drawable.item_coat;
                break;
            case 8:
                itemName = "Dagger";
                itemSrc= R.drawable.item_dagger;
                break;
            case 9:
                itemName = "Gloves";
                itemSrc= R.drawable.item_gloves;
                break;
            case 10:
                itemName = "Monocle";
                itemSrc= R.drawable.item_monocle;
                break;
            case 11:
                itemName = "Poison Ring";
                itemSrc= R.drawable.item_poisonring;
                break;
            case 12:
                itemName = "Priviledge";
                itemSrc= R.drawable.item_privilege;
                break;
            case 13:
                itemName = "Sextant";
                itemSrc= R.drawable.item_sextant;
                break;
            case 14:
                itemName = "The Coat of Armor of the Loge";
                itemSrc= R.drawable.item_thecoatofarmoroftheloge;
                break;
            case 15:
                itemName = "Tome";
                itemSrc= R.drawable.item_tome;
                break;
            case 16:
                itemName = "Whip";
                itemSrc= R.drawable.item_whip;
                break;
            default:
                itemName = "dcm";
                itemSrc = -1;
                break;
        }
    }
}
