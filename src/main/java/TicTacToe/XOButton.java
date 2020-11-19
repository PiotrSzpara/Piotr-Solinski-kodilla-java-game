package TicTacToe;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class XOButton extends Button {
    private int value;
    Image x = new Image("file:src/main/resources/xo/x1.png");
    Image o = new Image("file:src/main/resources/xo/o4.png");
    ImageView xV = new ImageView(x);
    ImageView oV = new ImageView(o);

    public XOButton() {
        xV.setFitHeight(125);
        xV.setFitWidth(125);
        xV.setPreserveRatio(true);
        oV.setFitHeight(125);
        oV.setFitWidth(125);
        oV.setPreserveRatio(true);

        this.setState(0);
    }
    public void setState(int state) {
        if (state == 0) {
            this.setGraphic(null);
            this.setText("");
            this.value = 0;
        }else if (state == 1) {
            this.setGraphic(oV);
            this.value = 1;
            this.setDisable(true);
            this.setOpacity(1);
        }else if (state == -1) {
            this.getOnMouseClicked();
            this.setGraphic(xV);
            this.value = -1;
            this.setDisable(true);
            this.setOpacity(1);
        }
    }
    public int getValue() {
    return value;
    }

}
