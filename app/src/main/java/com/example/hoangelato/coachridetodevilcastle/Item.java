package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Item {
    private int itemType;
    private int itemSrc;
    public boolean isOwned;


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

    public Item(int itemType) {
        this.itemType = itemType;
        switch (itemType){
            case 0:
                itemSrc= R.drawable.item_key;
                break;
            case 1:
                itemSrc= R.drawable.item_goblet;
                break;
            case 2:
                itemSrc= R.drawable.item_secretbagkey;
                break;
            case 3:
                itemSrc= R.drawable.item_secretbaggoblet;
                break;
            case 4:
                itemSrc= R.drawable.item_blackpearl;
                break;
            case 5:
                itemSrc= R.drawable.item_brokenmirror;
                break;
            case 6:
                itemSrc= R.drawable.item_castingknives;
                break;
            case 7:
                itemSrc= R.drawable.item_coat;
                break;
            case 8:
                itemSrc= R.drawable.item_dagger;
                break;
            case 9:
                itemSrc= R.drawable.item_gloves;
                break;
            case 10:
                itemSrc= R.drawable.item_monocle;
                break;
            case 11:
                itemSrc= R.drawable.item_poisonring;
                break;
            case 12:
                itemSrc= R.drawable.item_privilege;
                break;
            case 13:
                itemSrc= R.drawable.item_sextant;
                break;
            case 14:
                itemSrc= R.drawable.item_thecoatofarmoroftheloge;
                break;
            case 15:
                itemSrc= R.drawable.item_tome;
                break;
            case 16:
                itemSrc= R.drawable.item_whip;
                break;
            default:
                itemSrc = -1;
                break;
        }
    }
}
