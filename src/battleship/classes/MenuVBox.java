package battleship.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import battleship.Main;
import battleship.model.User;
import battleship.util.DBUtil;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MenuVBox extends VBox {

    private DBUtil db;

    protected int buttonWidth;
    protected int buttonHeight;
    protected int textFieldWidth;
    protected int textFieldHeight;
    protected String interfaceLanguage;
    private ArrayList<CustomButton> buttons;
    private ArrayList<CustomTextField> textFields;
    private ArrayList<CustomPasswordField> passwordFields;
    private CustomButton newButton;
    protected CSVDictReader buttonLabels;
    protected CSVDictReader promptLabels;
    protected Region spaceBeforeLastButton;

    public MenuVBox(CSVDictReader buttonLabels, CSVDictReader promptLabels) {
        db = Main.getDB();

        this.buttonWidth = 300;
        this.buttonHeight = 50;
        this.textFieldWidth = 400;
        this.textFieldHeight = 30;

        this.buttonLabels = buttonLabels;
        this.promptLabels = promptLabels;
        this.interfaceLanguage = Main.getInterfaceLanguage();

        this.buttons = new ArrayList<CustomButton>();
        this.textFields = new ArrayList<CustomTextField>();
        this.passwordFields = new ArrayList<CustomPasswordField>();
        this.spaceBeforeLastButton = new Region();
    }

    public void addButton(String buttonName, EventHandler<MouseEvent> eventHandler) {
        newButton = new CustomButton(buttonName);
        newButton.setText(buttonLabels.getLabelByName(buttonName).get(interfaceLanguage));
        newButton.setPrefSize(buttonWidth, buttonHeight);
        newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        buttons.add(newButton);
        this.getChildren().add(newButton);
    }

    public void addButton(String buttonName, EventHandler<MouseEvent> eventHandler, int buttonPosition) {
        newButton = new CustomButton(buttonName);
        newButton.setText(buttonLabels.getLabelByName(buttonName).get(interfaceLanguage));
        newButton.setPrefSize(buttonWidth, buttonHeight);
        newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
        buttons.add(newButton);
        this.getChildren().add(buttonPosition, newButton);
    }

    public void replaceButton(String actualButtonName, String newButtonName) {
        for(CustomButton button : buttons) {
            if(button.getButtonName() == actualButtonName){
                button.setButtonName(newButtonName);
                button.setText(buttonLabels.getLabelByName(newButtonName).get(interfaceLanguage));
            }
        }
    }

    public void addSelectLangButtons(EventHandler<MouseEvent> eventHandler) {
        
        for(String language : buttonLabels.getAvailableLanguages()) {
            newButton = new CustomButton("language");
            newButton.setText(language);
            newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            buttons.add(newButton);
            this.getChildren().add(newButton);

        }

    }

    public void addTextField(String textFieldName) {

        CustomTextField newTextField = null;
        newTextField = new CustomTextField(textFieldName);
        newTextField.setPromptText(promptLabels.getLabelByName(textFieldName).get(interfaceLanguage));
        newTextField.setPrefSize(textFieldWidth, textFieldHeight);
        textFields.add(newTextField);
        this.getChildren().add(newTextField);
    }

    public void addPasswordField(String passwordFieldName) {

        CustomPasswordField newPasswordField = new CustomPasswordField(passwordFieldName);
        newPasswordField.setPromptText(promptLabels.getLabelByName(passwordFieldName).get(interfaceLanguage));
        newPasswordField.setPrefSize(textFieldWidth, textFieldHeight);
        passwordFields.add(newPasswordField);
        this.getChildren().add(newPasswordField);
    }

    public void addSpaceBeforeLastButton() {
        // this.setVgrow(spaceBeforeLastButton, Priority.ALWAYS);
        spaceBeforeLastButton.setPrefHeight(buttonHeight*1);
        this.getChildren().add(spaceBeforeLastButton);
    }

    public void setButtonLabels() {

        for(CustomButton btn : buttons) {
            btn.setText(buttonLabels.getLabelByName(btn.getButtonName()).get(interfaceLanguage));
        }
    }

    public boolean logInUser() {
        if (authenticateUser()) {
            clearFields();
            return true;
        } else {
            clearFields();
            return false;
        }
    }

    private boolean authenticateUser() {
        String username = textFields.get(0).getText();
        String password = passwordFields.get(0).getText();

        if (fieldsAreNotEmpty(username, password)) {
            String hashedPassword = sha256(password);
            for (User user : Main.getUsers()) {
                if (username.equals(user.getUsername()) && hashedPassword.equals(user.getPassword())) {
                    return true;
                }
            }
        } else {
            System.out.println("Required fields not filled");
            return false;
        }
        System.out.println("Incorrect username or password");
        return false;

    }

    public boolean registerNewUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String username = textFields.get(0).getText();
        String password = passwordFields.get(0).getText();

        if (isUsernameUnique(username) && fieldsAreNotEmpty(username, password)) {
            String hashedPassword = sha256(password);
            db.insertUser(username, hashedPassword);
            clearFields();
            return true;
        }
        return false;
    }

    private boolean fieldsAreNotEmpty(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isUsernameUnique(String username) {
        for (User user : Main.getUsers()) {
            if (username.equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }

    private void clearFields() {
        for(CustomTextField field : textFields) {
            field.setText("");
        }
        for(CustomPasswordField field : passwordFields) {
            field.setText("");
        }
    }

    public static String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<CustomButton> getMenuButtons() {
        return buttons;
    }

    public void setInterfaceLanguage(String language) {
        this.interfaceLanguage = language;
    }

    public boolean isButtonAlreadyAdded(String buttonName) {
        
        for(CustomButton btn : buttons) {
            if(btn.getButtonName().equals(buttonName)) {
                return true;
            }
        }

        return false;
    }

    public void removeButton(String buttonName) {
        
        CustomButton buttonToBeRemoved = null;
        for(CustomButton btn : buttons) {
            if(btn.getButtonName().equals(buttonName)) {
                buttonToBeRemoved = btn;
            }
        }
        buttons.remove(buttonToBeRemoved);
        this.getChildren().remove(buttonToBeRemoved);
    }

}
