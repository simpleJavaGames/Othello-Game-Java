import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Scanner;

//There is 2 boards - the front end tileboard and the back end board.

//The place piece problem only applies to white for some reason.
public class Main extends Application {
    //Negotiable variables depending on what special rules/etc.
    int turnCounter = 0;
    Board board = new Board();
    private boolean whiteTurn = false;

    //Un-negotiable variables, game will not run without these.
    private boolean running = true;
    private int numWhitePieces = 2;
    private int numBlackPieces = 2;
    private final Player white = new Player(true);
    private final Player black = new Player(false);
    private Tile [][]tileBoard = new Tile[8][8];
    Label whoseTurn = new Label();
    Label whitePiecesLabel = new Label();
    Label blackPiecesLabel = new Label();

    //todo For some reason you cannot create a constructor in JavaFX, look up reason later.

    private Parent createContent(){
        //initialize the whoseTurn label.
        whoseTurn.setFont((Font.font(60)));
        whoseTurn.setTranslateX(475);
        whoseTurn.setTranslateY(800);

        //Initialize the white and black piece labels.
        whitePiecesLabel.setText("White:"+ numWhitePieces);
        blackPiecesLabel.setText("Black:"+ numBlackPieces);

        blackPiecesLabel.setTranslateX(250);
        blackPiecesLabel.setTranslateY(800);
        whitePiecesLabel.setTranslateX(0);
        whitePiecesLabel.setTranslateY(800);

        whitePiecesLabel.setFont(Font.font(60));
        blackPiecesLabel.setFont(Font.font(60));

        Pane root = new Pane();
        root.setPrefSize(800,900);

        //Add all the grid spaces.(with invisible circles in them.)
        for(int rowPos=0;rowPos<8;rowPos++){
            for (int colPos=0;colPos<8;colPos++){
                tileBoard[rowPos][colPos] = new Tile(rowPos,colPos);
                tileBoard[rowPos][colPos].setTranslateY(rowPos * 100);
                tileBoard[rowPos][colPos].setTranslateX(colPos * 100);
                root.getChildren().add(tileBoard[rowPos][colPos]);
            }
        }

        root.getChildren().add(whitePiecesLabel);
        root.getChildren().add(blackPiecesLabel);
        root.getChildren().add(whoseTurn);
        return root;
    }

    public class Tile extends StackPane{
        private final int rowPos;
        private final int colPos;

        private Circle piece = new Circle(40);
        //Initialize all the grid spots.
        public Tile(int newRowPos,int newColPos) {
            rowPos = newRowPos;
            colPos = newColPos;
            //Initialize all the circles inside the grids as transparent until we set it as either black or white.
            piece.setFill(null);

            Rectangle border = new Rectangle(100,100);
            border.setFill(Color.DARKGREEN);
            border.setStroke(Color.BLACK);

            setAlignment(Pos.CENTER);
            getChildren().addAll(border,piece);
            //todo you need multithreading here to make the last piece show up.

            setOnMouseClicked(event -> {
                if(!running) return;
                if(event.getButton() == MouseButton.PRIMARY){
                    if(whiteTurn && board.placePiece(white,this.rowPos,this.colPos) && running){
                        whiteTurn = false;
                        setWhitePiece();
                        updateBoard();
                        isPlayable(black);
                        turnCounter++;
                    }
                    else if (!whiteTurn && board.placePiece(black,this.rowPos,this.colPos) && running){
                        whiteTurn = true;
                        setBlackPiece();
                        updateBoard();
                        isPlayable(white);
                        turnCounter++;
                    }
                }
            });
        }

        public void setWhitePiece(){
            piece.setStroke(Color.BLACK);
            piece.setFill(Color.WHITE);
        }

        public void setBlackPiece(){
            piece.setFill(Color.BLACK);
        }

        public void hidePiece(){
            piece.setStroke(null);
            piece.setFill(null);
        }
    }

    public void initializeBoard(){
        //initialize the front-end board.
        tileBoard[3][3].setWhitePiece();
        tileBoard[3][4].setBlackPiece();
        tileBoard[4][3].setBlackPiece();
        tileBoard[4][4].setWhitePiece();
    }

    public void updateBoard(){
        //make this update pieces as well as the board state.
        updatePieces();
        if(whiteTurn){
            whoseTurn.setText("White's turn");
        }else{
            whoseTurn.setText("Black's turn");
        }
    }


    //todo this is super Suboptimal, if you have time, fix this.
    public void updatePieces(){
        int blackPieces = 0;
        int whitePieces = 0;
        //brute force check every spot to make sure the board state is updated.
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board.getBoardSpot(i,j) == null) continue;
                else{
                    if(board.getBoardSpot(i,j).getIsSpotWhite()){
                        tileBoard[i][j].setWhitePiece();
                        whitePieces++;
                    }
                    else{
                        tileBoard[i][j].setBlackPiece();
                        blackPieces++;
                    }
                }
            }
        }
        numBlackPieces = blackPieces;
        numWhitePieces = whitePieces;
        whitePiecesLabel.setText("White:"+ numWhitePieces);
        blackPiecesLabel.setText("Black:"+ numBlackPieces);
    }

    //todo check this cause it was buggy.
    public void isPlayable(Player player){
        if(turnCounter == 59){
            running = false;
            System.out.println("Game is over.");
            displayWhoWon();
            return;
        }

        boolean gameUnplayable = true;
        for(int i=0;(i<8) && gameUnplayable;i++){
            for(int j=0;(j<8) && gameUnplayable;j++){
                if(board.getBoardSpot(i,j) == null){
                    if(board.isSpotValid(player, i, j)){//if there is no more legal moves for that player, it will be true.
                        gameUnplayable = false;
                    }
                }
            }
        }

        if(gameUnplayable){
            running = false;
            System.out.println("No more legal moves. Ending Game");
            displayWhoWon();
        }
    }

    //todo make this add a label to the screen so the front end can see who won.
    public void displayWhoWon(){
        if(numWhitePieces < numBlackPieces){
            System.out.println("Black wins!");
        }else{
            System.out.println("White wins!");
        }
        playWinningPieceAnimation();
    }

    //todo fully implement the winning piece animation.
    //todo make this into its own thread so white and black can run concurrently.
    public void playWinningPieceAnimation(){
        clearTileBoard();
        waitHalfSecond();
        waitHalfSecond();
        //set all pieces from the top left of board to however many pieces there are to white to display how
        //pieces white has.
        int numWhiteFullRow = numWhitePieces / 8;
        int numWhitePartialRow = numWhitePieces % 8;

        //set the whole row to white.
        for(int i=0;i<numWhiteFullRow;i++){
            for(int j=0;j<8;j++){
                tileBoard[i][j].setWhitePiece();
                //thread.sleep for more drama.
            }
        }

        //set part of the row to white.
        if(numWhitePartialRow > 0){
            int partialRowIndexWhite = numWhiteFullRow+1;
            for(int j=0;j<numWhitePartialRow;j++){
                tileBoard[partialRowIndexWhite][j].setWhitePiece();
                //thread.sleep for more drama.
            }
        }

        int numBlackFullRow = numBlackPieces / 8; //if 20 should be 2 full rows
        int numBlackPartialRow = numBlackPieces % 8; // if 20 should be 4 remaining

        //starting from the bottom right, fill the board to however many pieces black has.

        //set the whole row to black
        for(int i=7;i>(7-numBlackFullRow);i--){
            for(int j=7;j>=0;j--){
                tileBoard[i][j].setBlackPiece();
            }
        }

        //set part of the row to black
        if(numBlackPartialRow > 0){
            int partialRowIndexBlack = 7-numBlackFullRow;
            for(int j=7;j>=numBlackPartialRow;j--){
                tileBoard[partialRowIndexBlack][j].setBlackPiece();
            }
        }

    }

    public void clearTileBoard() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) tileBoard[i][j].hidePiece();
    }

    public void waitHalfSecond(){
        try{Thread.sleep(500);}
        catch(Exception e){System.out.println("Thread failed.");}
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("OthelloGame");
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
        initializeBoard();
        updateBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}