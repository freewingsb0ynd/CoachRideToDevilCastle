package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Item {
    private int itemType;
    private String itemEffect;
    private boolean isOwned;

    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemEffect() {
        return itemEffect;
    }

    public void setItemEffect(String itemEffect) {
        this.itemEffect = itemEffect;
    }

    public Item(int itemType) {
        this.itemType = itemType;
        switch (itemType){
            case 0:
                itemEffect= "Effect0";
                break;
            // 21 items
            default: break;

        }
    }
}
