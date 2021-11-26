package controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.CSVDictReader;
import model.CustomButton;

public class MainMenuController {

    @FXML
    private VBox menuVBox;

    private String rootPath;
    private String LANG;
    private int menuButtonsCount;
    private CustomButton[] menuButtons;
    private List<CSVRecord> buttonLabelsDict;
    private EventHandler<MouseEvent> buttonEventHandler;

    public MainMenuController(){
        rootPath = System.getProperty("user.dir");
        LANG = "EN";
        menuButtonsCount = 8;
        menuButtons = new CustomButton[menuButtonsCount];
    }

    @FXML
    void initialize() throws IOException{
        loadButtonLabels();
        initializeButtonEventHandler();
        initializeButtons();
    }

    private void loadButtonLabels() throws IOException{
        
        String csvFilePath = rootPath + "/src/lang/button-labels.csv";

        try {
            buttonLabelsDict = CSVDictReader.parseCSV(csvFilePath);
        }catch(Exception IOException){
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    private void initializeButtonEventHandler(){
        buttonEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CustomButton btn = (CustomButton) event.getSource();
                int buttonID = btn.getButtonID();
                System.out.println("You pressed button number: " + buttonID);
            }
        };
    }

    private void initializeButtons(){
        CustomButton newButton;
        for(int i=0; i < menuButtonsCount; i++){
            newButton = new CustomButton(i);
            newButton.setText(buttonLabelsDict.get(i).get(LANG));
            newButton.setPrefSize(200, 50);
            newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            menuButtons[i] = newButton;
            menuVBox.getChildren().add(menuButtons[i]);
        }
    }
}
