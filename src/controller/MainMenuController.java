package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuController {

    @FXML
    private VBox menuVBox;

    private int menuButtonsCount;
    private Button[] menuButtons;

    public MainMenuController(){
        menuButtonsCount = 7;
        menuButtons = new Button[menuButtonsCount];
    }

    @FXML
    void initialize() {
        initializeButtons();
    }

    private void initializeButtons(){
        Button newButton;
        for(int i=0; i < menuButtonsCount; i++){
            newButton = new Button();
            newButton.setText("Button" + i);
            newButton.setPrefSize(200, 50);
            menuButtons[i] = newButton;
            menuVBox.getChildren().add(menuButtons[i]);
        }
    }
}
