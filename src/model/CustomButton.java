package model;

import javafx.scene.control.Button;

public class CustomButton extends Button{
    private int buttonID;

    public CustomButton(int buttonID){
        super();
        this.buttonID = buttonID;
    }

    public int getButtonID(){
        return buttonID;
    }
}
