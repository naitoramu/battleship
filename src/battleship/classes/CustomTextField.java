package battleship.classes;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField{
    
    private String textFieldName;

    public CustomTextField(String textFieldName) {
        this.textFieldName = textFieldName;
    }

    public String getTextFieldName() {
        return textFieldName;
    }

    public void setTextFieldName(String textFieldName) {
        this.textFieldName = textFieldName;
    }

}
