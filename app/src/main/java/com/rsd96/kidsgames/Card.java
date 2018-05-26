package com.rsd96.kidsgames;

import android.graphics.drawable.Drawable;

/**
 * Created by Ramshad on 5/11/18.
 */

public class Card {

    int id = 1;
    Drawable cardImage;
    Boolean showCard;


    public Boolean getShowCard() {
        return showCard;
    }

    public void setShowCard(Boolean showCard) {
        this.showCard = showCard;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getCardImage() {
        return cardImage;
    }

    public void setCardImage(Drawable cardImage) {
        this.cardImage = cardImage;
    }



}
