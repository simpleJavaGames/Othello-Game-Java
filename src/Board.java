public class Board {
    private boardSpot [][] board = new boardSpot[8][8];

    //This initializes the board and the pieces that should be there when an othelloGame begins.
    Board(){
        board[3][3] = new boardSpot(true,3,3);
        board[3][4] = new boardSpot(false,3,4);
        board[4][3] = new boardSpot(false,4,3);
        board[4][4] = new boardSpot(true,4,3);
    }

    public boolean placePiece(Player player,int rowPos,int columnPos){
        //if spot is valid.
        if(board[rowPos][columnPos] == null && checkAndReplaceBoard(player,rowPos,columnPos)){
            board[rowPos][columnPos] = new boardSpot(player.getIsWhite(),rowPos,columnPos);
            return true;
        }else{
            return false;
        }
    }

    //TODO check to make sure this actually works.
    //Checks if the spot in question is actually a valid spot for a piece.
    public boolean checkAndReplaceBoard(Player player, int rowPos, int columnPos){
        boolean hasSomethingToTake = false;
        boolean hasEndPiece = false;
        boolean isSpotValid = false;
        int endPieceRow = 0;
        int endPieceColumn = 0;

        //Checking if spot is legal vertically
        //From spot to top of board, does it have something to capture and an endpiece?
        for(int i=(rowPos-1);i>=0;i--){
            if(board[i][columnPos] == null) break;
            //does it have an opposite tile from tile to top of board / next endpiece?
            if((board[i][columnPos].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[i][columnPos].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceRow = i;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake){
                System.out.println("Spot to top of board didn't execute.");
                break;
            }

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                System.out.println("Spot to top of board executed.");
                for(int j=(rowPos-1);j>endPieceRow;j--) {
                    board[j][columnPos].setIsWhite(player.getIsWhite());
                }
                isSpotValid = true;
            }
        }

        //From spot to bottom of board, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceRow = 0;
        for(int i=(rowPos+1);i<8;i++){
            if(board[i][columnPos] == null) break;
            //does it have an opposite tile from tile to bottom of board / next endpiece?
            if((board[i][columnPos].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[i][columnPos].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceRow = i;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake){
                System.out.println("Spot to bottom of board didn't execute.");
                break;
            }

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                System.out.println("From spot to bottom of board executed.");
                for(int j=(rowPos+1);j<endPieceRow;j++){
                    board[j][columnPos].setIsWhite(player.getIsWhite());
                }
                isSpotValid = true;
            }
        }

        //Checking if the spot is legal horizontally
        //From spot to the left side of the board, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceColumn = 0;
        for(int i=(columnPos-1);i>=0;i--){
            if(board[rowPos][i] == null) break;
            //does it have an opposite tile from tile to left side of board.
            if((board[rowPos][i].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[rowPos][i].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceColumn = i;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake){
                System.out.println("Spot to left side of board didn't execute.");
                break;
            }

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                System.out.println("Spot to left side of board executed.");
                for(int j=(columnPos-1);j>endPieceColumn;j--){
                    board[rowPos][j].setIsWhite(player.getIsWhite());
                }
                isSpotValid = true;
            }
        }

        //From spot to the right side of the board, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceColumn = 0;
        for(int i=(columnPos+1);i<8;i++){
            if(board[rowPos][i] == null) break;
            //does it have an opposite tile from tile to left side of board.
            if((board[rowPos][i].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[rowPos][i].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceColumn = i;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake){
                System.out.println("Spot to right side of board didn't execute.");
                break;
            }

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                System.out.println("Spot to right side of board executed");
                for(int j=(columnPos+1);j<endPieceColumn;j++){
                    board[rowPos][j].setIsWhite(player.getIsWhite());
                }
                isSpotValid = true;
            }
        }

        //Checking if the spot is legal diagonally negative (using slope of -1).
        //From spot to the nearest top left corner / endpiece using a slope of -mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceColumn = 0;
        endPieceRow = 0;

        int currentRow = rowPos;  //todo ask if it is possible to set value as and then increment?
        int currentCol = columnPos;
        currentCol--;
        currentRow--;

        while(currentRow >= 0 && currentCol >= 0){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the top left corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceColumn = currentCol;
                endPieceRow = currentRow;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                int tempCol = columnPos;
                int tempRow = rowPos;
                System.out.println("From spot to the nearest top left corner / endpiece executed");
                do{
                    tempRow--;
                    tempCol--;
                    board[tempRow][tempCol].setIsWhite(player.getIsWhite());
                }while(tempRow != endPieceRow && tempCol != endPieceColumn);
                isSpotValid = true;
                break;
            }
            currentCol--;
            currentRow--;
        }

        //From spot to the nearest bottom right corner / endpiece using a slope of -mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceColumn = 0;
        endPieceRow = 0;

        currentRow = rowPos;
        currentCol = columnPos;
        currentCol++;
        currentRow++;

        while(currentRow < 8 && currentCol < 8){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the bottom right corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceColumn = currentCol;
                endPieceRow = currentRow;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                int tempCol = columnPos;
                int tempRow = rowPos;
                System.out.println("From spot to the nearest top left corner / endpiece executed");
                do{
                    tempRow++;
                    tempCol++;
                    board[tempRow][tempCol].setIsWhite(player.getIsWhite());
                }while(tempRow != endPieceRow && tempCol != endPieceColumn);
                isSpotValid = true;
                break;
            }
            currentCol++;
            currentRow++;
        }

        //Checking if the spot is legal diagonally positive (using slope of 1).
        //From spot to the nearest top right corner / endpiece using a slope of mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceColumn = 0;
        endPieceRow = 0;

        currentRow = rowPos;
        currentCol = columnPos;
        currentCol++;
        currentRow--;

        while(currentRow >= 0 && currentCol < 8){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the top right corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceColumn = currentCol;
                endPieceRow = currentRow;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                int tempCol = columnPos;
                int tempRow = rowPos;
                System.out.println("From spot to the nearest top left corner / endpiece executed");
                do{
                    tempRow--;
                    tempCol++;
                    board[tempRow][tempCol].setIsWhite(player.getIsWhite());
                }while(tempRow != endPieceRow && tempCol != endPieceColumn);
                isSpotValid = true;
                break;
            }
            currentCol++;
            currentRow--;
        }

        //From spot to the nearest bottom left corner / endpiece using a slope of mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        endPieceColumn = 0;
        endPieceRow = 0;

        currentRow = rowPos;
        currentCol = columnPos;
        currentCol--;
        currentRow++;

        while(currentRow < 8 && currentCol >= 0){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the top right corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()){
                hasEndPiece = true;
                endPieceColumn = currentCol;
                endPieceRow = currentRow;
            }

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece){
                int tempCol = columnPos;
                int tempRow = rowPos;
                System.out.println("From spot to the nearest top left corner / endpiece executed");
                do{
                    tempRow++;
                    tempCol--;
                    board[tempRow][tempCol].setIsWhite(player.getIsWhite());
                }while(tempRow != endPieceRow && tempCol != endPieceColumn);
                isSpotValid = true;
                break;
            }
            currentCol--;
            currentRow++;
        }

        //if it is valid, it will return true, else it won't.
        return isSpotValid;
    }

    public boolean isSpotValid(Player player, int rowPos, int columnPos){
        boolean hasSomethingToTake = false;
        boolean hasEndPiece = false;
        //Checking if spot is legal vertically
        //From spot to top of board, does it have something to capture and an endpiece?
        for(int i=(rowPos-1);i>=0;i--){
            if(board[i][columnPos] == null) break;
            //does it have an opposite tile from tile to top of board / next endpiece?
            if((board[i][columnPos].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[i][columnPos].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;
        }

        //From spot to bottom of board, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        for(int i=(rowPos+1);i<8;i++){
            if(board[i][columnPos] == null) break;
            //does it have an opposite tile from tile to bottom of board / next endpiece?
            if((board[i][columnPos].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[i][columnPos].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;
        }

        //Checking if the spot is legal horizontally
        //From spot to the left side of the board, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        for(int i=(columnPos-1);i>=0;i--){
            if(board[rowPos][i] == null) break;
            //does it have an opposite tile from tile to left side of board.
            if((board[rowPos][i].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[rowPos][i].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;
        }

        //From spot to the right side of the board, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;
        for(int i=(columnPos+1);i<8;i++){
            if(board[rowPos][i] == null) break;
            //does it have an opposite tile from tile to left side of board.
            if((board[rowPos][i].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
                //does it have an endpoint so the capture is legal?
            else if(board[rowPos][i].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;
        }

        //Checking if the spot is legal diagonally negative (using slope of -1).
        //From spot to the nearest top left corner / endpiece using a slope of -mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;

        int currentRow = rowPos;  //todo ask if it is possible to set value as and then increment?
        int currentCol = columnPos;
        currentCol--;
        currentRow--;

        while(currentRow >= 0 && currentCol >= 0){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the top left corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;
            currentCol--;
            currentRow--;
        }

        //From spot to the nearest bottom right corner / endpiece using a slope of -mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;

        currentRow = rowPos;
        currentCol = columnPos;
        currentCol++;
        currentRow++;

        while(currentRow < 8 && currentCol < 8){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the bottom right corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;

            currentCol++;
            currentRow++;
        }

        //Checking if the spot is legal diagonally positive (using slope of 1).
        //From spot to the nearest top right corner / endpiece using a slope of mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;

        currentRow = rowPos;
        currentCol = columnPos;
        currentCol++;
        currentRow--;

        while(currentRow >= 0 && currentCol < 8){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the top right corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;

            currentCol++;
            currentRow--;
        }

        //From spot to the nearest bottom left corner / endpiece using a slope of mx, does it have something to capture and an endpiece?
        hasEndPiece = false;
        hasSomethingToTake = false;

        currentRow = rowPos;
        currentCol = columnPos;
        currentCol--;
        currentRow++;

        while(currentRow < 8 && currentCol >= 0){
            if(board[currentRow][currentCol] == null) break;
            //does it have a opposite tile from tile to the top right corner of board.
            if((board[currentRow][currentCol].getIsSpotWhite() == !player.getIsWhite())) hasSomethingToTake = true;
            //does it have an endpoint so the capture is legal?
            if(board[currentRow][currentCol].getIsSpotWhite() == player.getIsWhite()) hasEndPiece = true;

            //We reached an endpiece, but no opposite pieces on the way.
            if(hasEndPiece && !hasSomethingToTake) break;

            //It is a legal move.
            if(hasSomethingToTake && hasEndPiece) return true;
            currentCol--;
            currentRow++;
        }

        // this line only execute if literally every single statement is false,
        // meaning it was indeed an illegal move.
        return false;
    }



    public boardSpot getBoardSpot(int row,int col) {
        return board[row][col];
    }

    public void setBoardSpot(int row,int col,boolean isWhite,boolean isTaken){
        board[row][col].setIsTaken(isTaken);
        board[row][col].setIsWhite(isWhite);
    }
}
