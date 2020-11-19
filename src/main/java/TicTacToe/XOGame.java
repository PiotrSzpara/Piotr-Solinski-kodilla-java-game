package TicTacToe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class XOGame extends Application implements EventHandler<ActionEvent> {
    private final Image imageback = new Image("file:src/main/resources/xoplansza.png");
    boolean gameOver = false;
    int activePlayer = 0;
    private final Label status = new Label();
    private XOButton [] buttons;
    private final int MAXBUTTONS = 9;

    public List game = new ArrayList<>();
    private String toString(Object o) {
        return "";

    }

    public void newGame() {

        for (int i = 0; i < MAXBUTTONS; i++) {
            buttons[i].setState(0);
            buttons[i].setDisable(false);
            buttons[i].setStyle("-fx-background-color: white");
            status.setText("ZACZYNAMY ?");
            gameOver = false;
            activePlayer = 0;
            game.clear();
        }
    }

    @Override
    public void handle(ActionEvent event) {

        for (int i=0; i<MAXBUTTONS; i++) {
            if (buttons[i] == event.getSource()) {
                status.setText("");
                if (activePlayer == 0) {
                    try {
                        buttons[i].setState(1);
                        game.add(i);
                        status.setText("ruch:"+ game.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                        status.setText("Zły ruch");
                    }
                    checkForWinner();
                    activePlayer = 1;
                }else {

                    try {
                        if(activePlayer == 1){


                            computerMove();
                            Thread.sleep(-3000);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    status.setText("ruch:"+ game.size());
                }
                checkForWinner();
            }
        }
    }
    public void computerMove() {
        Random r = new Random();
        int r1 = r.nextInt(9);
        if(buttons[r1].getValue() == 0) {
            buttons[r1].setState(-1);

            if (buttons[r1].getValue() == -1) {
                activePlayer = 0;
            }else {
                activePlayer = 1;
            }
        }
    }
    public void blockO() {
        int b1 = buttons[0].getValue();
        int b2 = buttons[1].getValue();
        int b3 = buttons[2].getValue();
        int b4 = buttons[3].getValue();
        int b5 = buttons[4].getValue();
        int b6 = buttons[5].getValue();
        int b7 = buttons[6].getValue();
        int b8 = buttons[7].getValue();
        int b9 = buttons[8].getValue();

        if (b1 + b5 + b9 == 2 || b2 + b5 + b8 == 2 || b3 + b5 + b7 == 2 || b4 + b5 + b6 == 2) {
            int last = game.size();
            int x = Integer.parseInt(toString(game.get(last)));


            buttons[8 - x].setState(-1);
            game.add(8-x);
            activePlayer = 0;


        }
    }



    public void computerSmartMove() {
        var b1 = buttons[0].getValue();
        var b2 = buttons[1].getValue();
        var b3 = buttons[2].getValue();
        var b4 = buttons[3].getValue();
        var b5 = buttons[4].getValue();
        var b6 = buttons[5].getValue();
        var b7 = buttons[6].getValue();
        var b8 = buttons[7].getValue();
        var b9 = buttons[8].getValue();



        if (game.size() == 1) {
            if (b5 == 1) {
                buttons[0].setState(-1);
                game.add(0);
            } else {
                buttons[4].setState(-1);
                game.add(4);
            }
            activePlayer = 0;


        } else if(game.size() == 3) {
            //blockO();

            if (b5 == 1 && b1 == -1) {
                if (b2 == 1 && game.size() == 3) {
                    buttons[7].setState(-1);
                    game.add(7);}
                if (b3 == 1 && game.size() == 3) {
                    buttons[6].setState(-1);
                    game.add(6);}
                if (b4 == 1 && game.size() == 3) {
                    buttons[5].setState(-1);
                    game.add(5);}
                if (b6 == 1 && game.size() == 3) {
                    buttons[3].setState(-1);
                    game.add(3);}
                if (b7 == 1 && game.size() == 3) {
                    buttons[2].setState(-1);
                    game.add(2);}
                if (b8 == 1 && game.size() == 3) {
                    buttons[1].setState(-1);
                    game.add(1);}
                if (b9 == 1 && game.size() == 3) {
                    buttons[2].setState(-1);
                    game.add(2);
                }


            } else {

            }
            activePlayer = 0;


        } else if (game.size() == 5) {
            if (b5 == 1 && game.size() == 5) {
                if (b2 == 1 && b7 != 1) {
                    buttons[6].setState(-1);
                    game.add(6);}
                if (b2 == 1 && b7 == 1) {
                    buttons[2].setState(-1);
                    game.add(2);}
                if (b2 == 1 && b4 == 1) {
                    buttons[5].setState(-1);
                    game.add(5);}
                if (b2 == 1 && b6 == 1) {
                    buttons[3].setState(-1);
                    game.add(3);}
                if (b3 == 1 && b4 != 1) {
                    buttons[3].setState(-1);
                    game.add(3);}//x win
                if (b3 == 1 && b4 == 1) {
                    buttons[5].setState(-1);
                    game.add(5);}
                if (b4 == 1 && b3 != 1) {
                    buttons[2].setState(-1);
                    game.add(2);}
                if (b4 == 1 && b3 == 1) {
                    buttons[6].setState(-1);
                    game.add(6);}
                if (b6 == 1 && b7 != 1) {
                    buttons[6].setState(-1); //x win
                    gameOver();}
                if (b6 == 1 && b7 == 1) {
                    buttons[2].setState(-1); //x win
                    game.add(2);}
                if (b7 == 1 && b2 != 1) {
                    buttons[1].setState(-1);
                    gameOver();
                    } //x win
                if (b7 == 1 && b2 == 1) {
                    buttons[7].setState(-1);
                    game.add(7);}
                if (b8 == 1 && b3 != 1) {
                    buttons[2].setState(-1);
                    gameOver(); } //x win
                if (b8 == 1 && b3 == 1) {
                    buttons[6].setState(-1);
                    game.add(6);}
                if (b9 == 1 && b2 != 1) {
                    buttons[1].setState(-1);
                    gameOver();} //x win
                if (b9 == 1 && b2 == 1) {
                    buttons[7].setState(-1);
                    game.add(7);}


                checkForWinner();

            }





        }else if (game.size() == 7) {
            if (b5 == 1) {
                if (b2 == 1 && b7 != 1) {
                    buttons[6].setState(-1);
                    game.add(6);
                }
                if (b2 == 1 && b7 == 1) {
                    buttons[2].setState(-1);
                    game.add(2);
                }
                if (b3 == 1 && b4 == 1) {
                    buttons[5].setState(-1);
                    game.add(5);
                }
                if (b4 == 1 && b3 != 1) {
                    buttons[2].setState(-1);
                    game.add(2);
                }
                if (b4 == 1 && b3 == 1) {
                    buttons[6].setState(-1);
                    game.add(6);
                }
                if (b7 == 1 && b2 == 1) {
                    buttons[7].setState(-1);
                    game.add(7);
                }
                if (b8 == 1 && b3 == 1) {
                    buttons[6].setState(-1);
                    game.add(6);
                }
                if (b9 == 1 && b2 == 1) {
                    buttons[7].setState(-1);
                    game.add(7);
                }
            }
        }




    }




    public void checkForWinner() {
        if (!gameOver) {
            int b1 = buttons[0].getValue();
            int b2 = buttons[1].getValue();
            int b3 = buttons[2].getValue();
            int b4 = buttons[3].getValue();
            int b5 = buttons[4].getValue();
            int b6 = buttons[5].getValue();
            int b7 = buttons[6].getValue();
            int b8 = buttons[7].getValue();
            int b9 = buttons[8].getValue();

            if (b1 + b2 + b3 == 3 || b4 + b5 + b6 == 3 || b7 + b8 + b9 == 3
                    || b1 + b4 + b7 == 3 || b2 + b5 + b8 == 3 || b3 + b6 + b9 == 3
                    || b1 + b5 + b9 == 3 || b3 + b5 + b7 == 3) {
                    status.setText("WYGRANA ! \nKONIEC GRY");
                    for (Button btn : buttons){
                        String bg = "-fx-background-color:  lime ; -fx-background-radius: 30";
                        btn.setDisable(true);
                        if (b1 + b2 + b3 == 3){
                            buttons[0].setStyle(bg); buttons[1].setStyle(bg); buttons[2].setStyle(bg);
                        }else if (b4 + b5 + b6 == 3){
                            buttons[3].setStyle(bg); buttons[4].setStyle(bg); buttons[5].setStyle(bg);
                        }else if (b7 + b8 + b9 == 3){
                            buttons[6].setStyle(bg); buttons[7].setStyle(bg); buttons[8].setStyle(bg);
                        }else if (b1 + b4 + b7 == 3){
                            buttons[0].setStyle(bg); buttons[3].setStyle(bg); buttons[6].setStyle(bg);
                        }else if (b2 + b5 + b8 == 3){
                            buttons[1].setStyle(bg); buttons[4].setStyle(bg); buttons[7].setStyle(bg);
                        }else if (b3 + b6 + b9 == 3){
                            buttons[2].setStyle(bg); buttons[5].setStyle(bg); buttons[8].setStyle(bg);
                        }else if (b1 + b5 + b9 == 3){
                            buttons[0].setStyle(bg); buttons[4].setStyle(bg); buttons[8].setStyle(bg);
                        }else if(b3 + b5 + b7 == 3){
                            buttons[2].setStyle(bg); buttons[4].setStyle(bg); buttons[6].setStyle(bg);
                        }
                    }
                    gameOver();
                } else if (b1 + b2 + b3 == -3 || b4 + b5 + b6 == -3 || b7 + b8 + b9 == -3
                    || b1 + b4 + b7 == -3 || b2 + b5 + b8 == -3 || b3 + b6 + b9 == -3
                    || b1 + b5 + b9 == -3 || b3 + b5 + b7 == -3) {
                    status.setText("PRZEGRANA ! \nKONIEC GRY");
                for (Button btn : buttons){
                    String bg = "-fx-background-color: red ; -fx-background-radius: 50";
                    btn.setDisable(true);
                    if (b1 + b2 + b3 == 3){
                        buttons[0].setStyle(bg); buttons[1].setStyle(bg); buttons[2].setStyle(bg);
                    }else if (b4 + b5 + b6 == -3){
                        buttons[3].setStyle(bg); buttons[4].setStyle(bg); buttons[5].setStyle(bg);
                    }else if (b7 + b8 + b9 == -3){
                        buttons[6].setStyle(bg); buttons[7].setStyle(bg); buttons[8].setStyle(bg);
                    }else if (b1 + b4 + b7 == -3){
                        buttons[0].setStyle(bg); buttons[3].setStyle(bg); buttons[6].setStyle(bg);
                    }else if (b2 + b5 + b8 == -3){
                        buttons[1].setStyle(bg); buttons[4].setStyle(bg); buttons[7].setStyle(bg);
                    }else if (b3 + b6 + b9 == -3){
                        buttons[2].setStyle(bg); buttons[5].setStyle(bg); buttons[8].setStyle(bg);
                    }else if (b1 + b5 + b9 == -3){
                        buttons[0].setStyle(bg); buttons[4].setStyle(bg); buttons[8].setStyle(bg);
                    }else if(b3 + b5 + b7 == -3){
                        buttons[2].setStyle(bg); buttons[4].setStyle(bg); buttons[6].setStyle(bg);
                    }
                }
                    gameOver();
                } else if (b1 !=0 && b2 != 0 && b3 != 0 && b4 != 0
                    && b5 != 0 && b6 != 0 && b7 != 0 && b8 != 0 && b9 != 0 ) {
                    status.setText("REMIS ! \nKONIEC GRY");
            }
        }
    }

    public void gameOver() {
        for (int i=0; i<MAXBUTTONS; i++){
                buttons[i].setDisable(true);

        }
    }

    @Override
    public void start(Stage primaryStage) {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Button newGameButton = new Button();
        newGameButton.setPrefSize(240, 80);
        newGameButton.setFont(new Font("Century Gothic", 30));
        newGameButton.setText("NOWA GRA");
        newGameButton.setOnAction(e ->
                newGame());

        status.setFont(new Font("Century Gothic", 32));

        buttons = new XOButton[MAXBUTTONS];
        for (int i=0; i<MAXBUTTONS; i++) {

            buttons[i] = new XOButton();
            buttons[i].setDisable(true);
            buttons[i].setPrefSize(160,160);
            buttons[i].setOnAction(this);
            buttons[i].setStyle("-fx-background-color: white");

        }

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
            grid.setPadding(new Insets(78, 0, 0, 390));
        grid.setHgap(50);
        grid.setVgap(50);
        grid.setBackground(background);
        grid.add(newGameButton,0,1);
        grid.add(status,0,3);

        int buttonIndex = 0;
        for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
            for (int colIndex = 0; colIndex < 3; colIndex++) {
                grid.add(buttons[buttonIndex], colIndex+2, rowIndex+1);
                buttonIndex++;
            }
        }
        Scene scene = new Scene(grid, 1200, 900, Color.FUCHSIA);

        primaryStage.setTitle("XOGame");
        primaryStage.setScene(scene);
        primaryStage.show();

        newGame();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
 /*public void game() {
        for(int i = 0; i < MAXBUTTONS; i++) {
            buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button currentButton = (Button) event.getSource();
                    try {
                    buttons[currentButton].setState(1);
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                    if (gameOver) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error message");
                        alert.setContentText("Game over !!  Try to restart the game");
                        alert.show();
                    } else {
                        if (buttons[i].getValue() == 0) {
                            if (activePlayer == 1) {
                                checkForWinner();
                                activePlayer = 0;
                            }else if(activePlayer == 0 ){
                                try {
                                buttons[i].setState(-1);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
         }
       });
    }*/

