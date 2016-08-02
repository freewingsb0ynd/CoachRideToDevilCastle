package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Item {
    private int itemType;
    private String itemSrc;
    public boolean isOwned;


    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemSrc() {
        return itemSrc;
    }

    public void setItemSrc(String itemSrc) {
        this.itemSrc = itemSrc;
    }

    public Item(int itemType) {
        this.itemType = itemType;
        switch (itemType){
            case 0:
                itemSrc= "@drawable/item_key";
                break;
            case 1:
                itemSrc= "@drawable/item_goblet";
                break;
            case 2:
                itemSrc= "@drawable/item_secretbaggobblet";
                break;
            case 3:
                itemSrc= "@drawable/item_secretbagkey";
                break;
            case 4:
                itemSrc= "@drawable/item_blackpearl";
                break;
            case 5:
                itemSrc= "@drawable/item_brokenmirror";
                break;
            case 6:
                itemSrc= "@drawable/item_castingknives";
                break;
            case 7:
                itemSrc= "@drawable/item_coat";
                break;
            case 8:
                itemSrc= "@drawable/item_dagger";
                break;
            case 9:
                itemSrc= "@drawable/item_gloves";
                break;
            case 10:
                itemSrc= "@drawable/item_monocle";
                break;
            case 11:
                itemSrc= "@drawable/item_poisonring";
                break;
            case 12:
                itemSrc= "@drawable/item_privilege";
                break;
            case 13:
                itemSrc= "@drawable/item_sextant";
                break;
            case 14:
                itemSrc= "@drawable/item_thecoatofarmoroftheloge";
                break;
            case 15:
                itemSrc= "@drawable/item_tome";
                break;
            case 16:
                itemSrc= "@drawable/item_whip";
                break;
            default: break;
        }
    }
}
