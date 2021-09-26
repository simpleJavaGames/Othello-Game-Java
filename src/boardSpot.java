public class boardSpot{
    private boolean isWhite;
    private boolean isTaken = false;

    boardSpot(boolean isWhite,int rowPos,int colPos){
        this.isWhite = isWhite;
    }

    public boolean getIsSpotWhite(){
        return isWhite;
    }

    public boolean isSpotTaken(){
        return isTaken;
    }

    public void takeSpot(boolean isWhite){
        this.isWhite = isWhite;
        isTaken = true;
    }

    public void setIsWhite(boolean white) {
        isWhite = white;
    }

    public void setIsTaken(boolean taken) {
        isTaken = taken;
    }
}
