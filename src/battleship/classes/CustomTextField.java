package battleship.classes;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField{
    
    private int textFieldID;

    public CustomTextField(int id) {
        this.setTextFieldID(id);
    }

    public CustomTextField(String text, int id) {
        super(text);
        this.setTextFieldID(id);
    }

    public int getTextFieldID() {
        return textFieldID;
    }

    public void setTextFieldID(int textFieldID) {
        this.textFieldID = textFieldID;
    }

}
