package battleship.classes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import battleship.Main;
import battleship.model.User;
import battleship.util.DBUtil;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MenuVBox extends VBox {

    private List<User> users;
    private DBUtil db;

    protected int buttonWidth;
    protected int buttonHeight;
    protected int textFieldWidth;
    protected int textFieldHeight;
    protected String defaultLanguage;
    private ArrayList<CustomButton> buttons;
    private ArrayList<CustomTextField> textFields;
    private CustomButton newButton;
    private CustomTextField newTextField;
    protected List<CSVRecord> buttonLabelsList;
    protected List<CSVRecord> textFieldPromptLabelsList;

    public MenuVBox(List<CSVRecord> buttonLabelsList, List<CSVRecord> textFieldPromptLabelsList) {
        users = Main.getUsers();
        db = Main.getDB();

        this.buttonWidth = 300;
        this.buttonHeight = 50;
        this.textFieldWidth = 400;
        this.textFieldHeight = 30;

        this.buttonLabelsList = buttonLabelsList;
        this.textFieldPromptLabelsList = textFieldPromptLabelsList;
        this.defaultLanguage = "EN";

        this.buttons = new ArrayList<CustomButton>();
        this.textFields = new ArrayList<CustomTextField>();
    }

    public void addButton(int buttonID,  EventHandler<MouseEvent> eventHandler) {
            newButton = new CustomButton(buttonID);
            newButton.setText(buttonLabelsList.get(buttonID).get(defaultLanguage));
            newButton.setPrefSize(buttonWidth, buttonHeight);
            newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            buttons.add(newButton);
            this.getChildren().add(newButton);
    }

    public void addSelectLangButtons(EventHandler<MouseEvent> eventHandler){
        List<String> availableLangsList = buttonLabelsList.get(0).getParser().getHeaderNames();
        ArrayList<String> availableLangsArrayList = new ArrayList<String>(availableLangsList);
        availableLangsArrayList.remove(0);                                  //Usuwanie komórki "ButtonID"
        int availableLangsCount = availableLangsArrayList.size();

        for(int i=0; i < availableLangsCount; i++){
            newButton = new CustomButton(99);                         //id=99 zarezerwowane dla buttonow do zmiany jezyka
            newButton.setText(availableLangsArrayList.get(i));
            newButton.setPrefSize(buttonWidth, buttonHeight);
            newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
            buttons.add(newButton);
            this.getChildren().add(newButton);
        }
    }

    public void addTextField(int textFieldID) {
        newTextField = new CustomTextField(textFieldID);
        newTextField.setPromptText(textFieldPromptLabelsList.get(textFieldID).get(defaultLanguage));
        newTextField.setPrefSize(textFieldWidth, textFieldHeight);
        textFields.add(newTextField);
        this.getChildren().add(newTextField);
    }

    public void registerNewUser() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        String username = textFields.get(0).getText();
        String password = textFields.get(1).getText();
    
        if(isUsernameUnique(username) && fieldAreNotEmpty(username, password)){
            String hashedPassword = hashMD5(password.getBytes("UTF-8"));
            db.insertUser(username, hashedPassword);
        }
    }

    private boolean fieldAreNotEmpty(String username, String password) {
        if(username.equals("") || password.equals("")){
            return false;
        } else {
            return true;
        }
    }

    private boolean isUsernameUnique(String username) {
        for(User user : users){
            if(username.equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }

    private String hashMD5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theMD5digest = md.digest(bytes);
        return theMD5digest.toString();

    }

    public ArrayList<CustomButton> getMenuButtons() {
        return buttons;
    }

}
