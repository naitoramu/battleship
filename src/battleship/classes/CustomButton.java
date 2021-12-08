package battleship.classes;

import javafx.scene.control.Button;

public class CustomButton extends Button{
    private String buttonName;

    public CustomButton(String buttonName){
        super();
        this.setButtonName(buttonName);
    }

    public String getButtonName() {
        return buttonName;
    }
    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

}
