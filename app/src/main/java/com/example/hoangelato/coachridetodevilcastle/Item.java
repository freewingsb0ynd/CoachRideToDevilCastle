package com.example.hoangelato.coachridetodevilcastle;

/**
 * Created by Hoangelato on 26/07/2016.
 */
public class Item {
    public int itemType;
    public String itemEffect;

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
