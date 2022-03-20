package com.schoolaccount32.othellojavafx.Players;

public class Player {
    private int numOfPieces;
    private boolean isWhite;

    Player(boolean isWhite){
        this.isWhite = isWhite;
    }

    public boolean getIsWhite(){
        return isWhite;
    }



}
