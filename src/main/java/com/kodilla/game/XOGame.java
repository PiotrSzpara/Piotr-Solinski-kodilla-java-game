package com.kodilla.game;

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

public class XOGame extends Application implements EventHandler<ActionEvent> {
    private final static int MAX_BUTTONS = 9;
    public static final int COMPUTER = -1;
    public static final int PLAYER = 1;
    public static final int EMPTY = 0;

    boolean gameOver = false;
    int activePlayer = PLAYER;

    private final Image imageBack = new Image("file:src/main/resources/xoplansza.png");
    private final Label status = new Label();
    private XOButton [] buttons;

    public List<Integer> game = new ArrayList<>();

    public void newGame() {
        for (int i = 0; i < MAX_BUTTONS; i++) {
            buttons[i].setState(EMPTY);
            buttons[i].setDisable(false);
            buttons[i].setStyle("-fx-background-color: white");
            status.setText("ZACZYNAMY ?");
            gameOver = false;
            activePlayer = PLAYER;
            game.clear();
        }
    }
    @Override
    public void handle(ActionEvent event) {
        for (int i=0; i<MAX_BUTTONS; i++) {
            if (buttons[i] == event.getSource()) {
                status.setText("");
                if (activePlayer == PLAYER) {
                    try {
                        buttonSetState(i, PLAYER);
                    } catch (Exception e) {
                        e.printStackTrace();
                        status.setText("TRY AGAIN");
                    }
                    checkForWinner();
                    activePlayer = COMPUTER;
                    computerSmartMove();
                }
                checkForWinner();
            }
        }
    }

    public void computerSmartMove() {
        int button1Value = getButtonValue(0);
        int button2Value = getButtonValue(1);
        int button3Value = getButtonValue(2);
        int button4Value = getButtonValue(3);
        int button5Value = getButtonValue(4);
        int button6Value = getButtonValue(5);
        int button7Value = getButtonValue(6);
        int button8Value = getButtonValue(7);
        int button9Value = getButtonValue(8);

        int line1 = button1Value + button2Value + button3Value;
        int line2 = button4Value + button5Value + button6Value;
        int line3 = button7Value + button8Value + button9Value;
        int line4 = button1Value + button4Value + button7Value;
        int line5 = button2Value + button5Value + button8Value;
        int line6 = button3Value + button6Value + button9Value;
        int line7 = button1Value + button5Value + button9Value;
        int line8 = button3Value + button5Value + button7Value;

        if (game.size() == 1) {
            if (buttons[4].getValue() == PLAYER) {
                buttonSetState(0, COMPUTER);
            } else {
                buttonSetState(4, COMPUTER);
            }
            activePlayer = PLAYER;

        } else if (game.size() == 3 || game.size() == 5 || game.size() == 7) {

            int last = game.size();
            int lastOButtonNumber = game.get(last - 1);

            if (activePlayer == COMPUTER && line7 == 2 * COMPUTER)
                blockOOrWin(button9Value, button1Value == EMPTY, 0, 8);
            else if (activePlayer == COMPUTER && line8 == 2 * COMPUTER)
                blockOOrWin(button7Value, button3Value == EMPTY, 2, 6);

            else if (activePlayer == COMPUTER && line4 == 2 * COMPUTER)
                findPlaceForX(button1Value, button4Value, button7Value, 0, 0, 3, 6);
            else if (activePlayer == COMPUTER && line1 == 2 * COMPUTER)
                findPlaceForX(button1Value, button2Value, button3Value, 0, 0, 1, 3);
            else if (activePlayer == COMPUTER && line5 == 2 * COMPUTER)
                findPlaceForX(button2Value, button5Value, button8Value, 0, 1, 4, 7);
            else if (activePlayer == COMPUTER && line6 == 2 * COMPUTER)
                findPlaceForX(button3Value, button6Value, button9Value, 0, 2, 5, 8);
            else if (activePlayer == COMPUTER && line3 == 2 * COMPUTER)
                findPlaceForX(button7Value, button8Value, button9Value, 0, 6, 7, 8);
            else if (activePlayer == COMPUTER && line2 == 2 * COMPUTER)
                findPlaceForX(button4Value, button5Value, button6Value, 0, 3, 4, 5);

            else if (line5 == 2 * PLAYER || line8 == 2 * PLAYER || line2 == 2 * PLAYER)
                buttonSetState(8 - lastOButtonNumber, COMPUTER);

            else if (activePlayer == COMPUTER && line7 == PLAYER && game.size() == 3)
                if (button5Value == PLAYER) buttonSetState(2, COMPUTER);
                else buttonSetState(1, COMPUTER);

            if (activePlayer == COMPUTER && line8 == PLAYER && game.size() == 3)
                blockOOrWin(button2Value, button5Value == PLAYER && button1Value == EMPTY, 0, 1);

            else if (line2 == PLAYER && button5Value == COMPUTER && game.size() == 3)
                buttonSetState(2, COMPUTER);
            else if (line5 == PLAYER && button5Value == COMPUTER && game.size() == 3)
                buttonSetState(3, COMPUTER);

            else if (activePlayer == COMPUTER && line1 == 2 * PLAYER)
                findPlaceForX(button2Value + button3Value, button2Value, button3Value, 2, 0, 1, 2);
            else if (activePlayer == COMPUTER && line4 == 2 * PLAYER)
                findPlaceForX(button4Value + button7Value, button4Value, button7Value, 2, 0, 3, 6);

            else if (activePlayer == COMPUTER && line6 == 2 * PLAYER)
                findPlaceForX(button3Value, button6Value, button9Value, 0, 2, 5, 8);
            else if (activePlayer == COMPUTER && line3 == 2 * PLAYER)
                findPlaceForX(button7Value, button8Value, button9Value, 0, 6, 7, 8);

            else if (activePlayer == COMPUTER && game.size() == 3)
                twoLinesBlock(button1Value, button3Value, button7Value, button9Value, line1, line3, line4, line6);

            else defaultXMove();
        }
    }

    private int getButtonValue(int i) {
        return buttons[i].getValue();
    }

    private void buttonSetState(int i2, int computer) {
        buttons[i2].setState(computer);
        game.add(i2);
        activePlayer = PLAYER;
    }
    private void twoLinesBlock(int button1Value, int button3Value, int button7Value, int button9Value,
                               int line1, int line3, int line4, int line6) {
        if(line1 == PLAYER && line6 == PLAYER && button3Value == EMPTY) buttonSetState(2, COMPUTER);
        else if(line1 == PLAYER && line4 == PLAYER && button1Value == EMPTY) buttonSetState(0, COMPUTER);
        else if (line3 == PLAYER && line4 == PLAYER && button7Value == EMPTY) buttonSetState(6, COMPUTER);
        else if (line3 == PLAYER && line6 == PLAYER && button9Value == EMPTY ) buttonSetState(8, COMPUTER);
    }

    private void blockOOrWin(int button9Value, boolean twoO, int i, int i2) {
        if (twoO) buttonSetState(i, COMPUTER);
        else if (button9Value == EMPTY) buttonSetState(i2, COMPUTER);
    }

    private void findPlaceForX(int button1Value, int button4Value, int button7Value, int i, int i2, int i3, int i4) {
        if (button1Value == i) buttonSetState(i2, COMPUTER);
        else if (button4Value == EMPTY) buttonSetState(i3, COMPUTER);
        else if (button7Value == EMPTY) buttonSetState(i4, COMPUTER);
    }

    private void defaultXMove() {
        for (int i = 0; i<9; i++) {
            if (buttons[i].getValue() == EMPTY && activePlayer == COMPUTER) {
                switch (i+1) {
                    case 1:
                        buttonSetState(0, COMPUTER);
                        break;
                    case 2:
                        buttonSetState(1, COMPUTER);
                        break;
                    case 3:
                        buttonSetState(2, COMPUTER);
                        break;
                    case 4:
                        buttonSetState(3, COMPUTER);
                        break;
                    case 5:
                        buttonSetState(4, COMPUTER);
                        break;
                    case 6:
                        buttonSetState(5, COMPUTER);
                        break;
                    case 7:
                        buttonSetState(6, COMPUTER);
                        break;
                    case 8:
                        buttonSetState(7, COMPUTER);
                        break;
                    case 9:
                        buttonSetState(8, COMPUTER);
                        break;
                } break;
            }
        }
        activePlayer = PLAYER;
    }
    public void checkForWinner() {

        int button1Value = getButtonValue( 0 );
        int button2Value = getButtonValue( 1 );
        int button3Value = getButtonValue( 2 );
        int button4Value = getButtonValue( 3 );
        int button5Value = getButtonValue( 4 );
        int button6Value = getButtonValue( 5 );
        int button7Value = getButtonValue( 6 );
        int button8Value = getButtonValue( 7 );
        int button9Value = getButtonValue( 8 );

        int line1 = button1Value + button2Value + button3Value;
        int line2 = button4Value + button5Value + button6Value;
        int line3 = button7Value + button8Value + button9Value;
        int line4 = button1Value + button4Value + button7Value;
        int line5 = button2Value + button5Value + button8Value;
        int line6 = button3Value + button6Value + button9Value;
        int line7 = button1Value + button5Value + button9Value;
        int line8 = button3Value + button5Value + button7Value;

        if (line1 == 3*PLAYER || line2 == 3*PLAYER || line3 == 3*PLAYER
                || line4 == 3*PLAYER || line5 == 3*PLAYER || line6 == 3*PLAYER
                || line7 == 3*PLAYER || line8 == 3*PLAYER) {
            status.setText("WYGRANA ! \nKONIEC GRY");
            for (Button btn : buttons){
                setStyleMethod(button1Value, button2Value, button3Value, button4Value, button5Value, button6Value, button7Value, button8Value, button9Value, btn, "-fx-background-color:  #33d933 ; -fx-background-radius: 20", 3*PLAYER);
            }
        } else if (line1 == 3*COMPUTER || line2 == 3*COMPUTER || line3 == 3*COMPUTER
                || line4 == 3*COMPUTER || line5 == 3*COMPUTER || line6 == 3*COMPUTER
                || line7 == 3*COMPUTER || line8 == 3*COMPUTER) {
            status.setText("PRZEGRANA ! \nKONIEC GRY");
            for (Button btn : buttons) {
                setStyleMethod(button1Value, button2Value, button3Value, button4Value, button5Value, button6Value, button7Value, button8Value, button9Value, btn, "-fx-background-color: #f12d2d ; -fx-background-radius: 20", 3*COMPUTER);
            }
        }else if (button1Value != EMPTY && button2Value != EMPTY && button3Value != EMPTY && button4Value != EMPTY
                && button5Value != EMPTY && button6Value != EMPTY && button7Value != EMPTY && button8Value != EMPTY && button9Value != EMPTY ) {
            for (Button btn : buttons) {
                String backgroundFX = "-fx-background-color: #cbc7c7 ; -fx-background-radius: 20";
                btn.setDisable(true);
                setStyleForDraw(backgroundFX);
                status.setText("REMIS ! \nKONIEC GRY");
            }
        }
    }
    public void setStyleMethod(int button1Value, int button2Value, int button3Value, int button4Value, int button5Value,
                                int button6Value, int button7Value, int button8Value, int button9Value, Button btn, String style, int sum) {
        btn.setDisable(true);

        if (button1Value + button2Value + button3Value == sum) lineSetStyle(style, 0, 1, 2);
        else if (button4Value + button5Value + button6Value == sum) lineSetStyle(style, 3, 4, 5);
        else if (button7Value + button8Value + button9Value == sum) lineSetStyle(style, 6, 7, 8);
        else if (button1Value + button4Value + button7Value == sum) lineSetStyle(style, 0, 3, 6);
        else if (button2Value + button5Value + button8Value == sum) lineSetStyle(style, 1, 4, 7);
        else if (button3Value + button6Value + button9Value == sum) lineSetStyle(style, 2, 5, 8);
        else if (button1Value + button5Value + button9Value == sum) lineSetStyle(style, 0, 4, 8);
        else if (button3Value + button5Value + button7Value == sum) lineSetStyle(style, 2, 4, 6);
    }
    private void setStyleForDraw(String backgroundFX) {
        lineSetStyle(backgroundFX, 0, 1, 2);
        lineSetStyle(backgroundFX, 3, 4, 5);
        lineSetStyle(backgroundFX, 6, 7, 8);
    }
    private void lineSetStyle(String backgroundFX, int i2, int i3, int i4) {
        buttons[i2].setStyle(backgroundFX);
        buttons[i3].setStyle(backgroundFX);
        buttons[i4].setStyle(backgroundFX);
    }
    @Override
    public void start(Stage primaryStage) {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageBack, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        Button newGameButton = new Button();
        newGameButton.setPrefSize(240, 80);
        newGameButton.setFont(new Font("Century Gothic", 30));
        newGameButton.setText("NOWA GRA");
        newGameButton.setOnAction(e ->
                newGame());

        status.setFont(new Font("Century Gothic", 32));

        buttons = new XOButton[MAX_BUTTONS];
        for (int i=0; i<MAX_BUTTONS; i++) {
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
