package battleship.classes;

import javafx.scene.control.PasswordField;

public class CustomPasswordField  extends PasswordField {
    
    private String passwordFieldName;

    public CustomPasswordField(String passwordFieldName) {
        this.passwordFieldName = passwordFieldName;
    }

    public String getPasswordFieldName() {
        return passwordFieldName;
    }

    public void setPasswordFieldName(String passwordFieldName) {
        this.passwordFieldName = passwordFieldName;
    }

}
