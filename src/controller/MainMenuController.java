package controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.CSVDictReader;
import model.CustomButton;
import model.MenuVBox;

public class MainMenuController {

    @FXML
    private VBox menuVBox;
    private MenuVBox mainMenuVBox;
    private MenuVBox changeLangMenuVBox;
    private MenuVBox logOrSignMenuVBox;

    private String rootPath;
    private String LANG;
    private int mainMenuButtonsCount;
    private List<CSVRecord> buttonLabelsDict;
    private EventHandler<MouseEvent> buttonEventHandler;

    public MainMenuController(){
        rootPath = System.getProperty("user.dir");
        LANG = "EN";
        mainMenuButtonsCount = 8;
    }

    @FXML
    void initialize() throws IOException{
        loadButtonLabels();
        initializeButtonEventHandler();
        initializeMainMenu();
        initializeChangeLangMenu();
        initializeLogOrSignInMenu();
        switchMenuVBox(mainMenuVBox);
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
                handleButton(btn);
            }
        };
    }

    private void initializeMainMenu(){
        mainMenuVBox = new MenuVBox(buttonLabelsDict);
        mainMenuVBox.initializeMenuButtons(mainMenuButtonsCount, buttonEventHandler, 0);
    }

    private void initializeChangeLangMenu(){
        changeLangMenuVBox = new MenuVBox(buttonLabelsDict);
        changeLangMenuVBox.initializeSelectLangButtons(buttonEventHandler);
    }

    private void initializeLogOrSignInMenu(){
        logOrSignMenuVBox = new MenuVBox(buttonLabelsDict);
        logOrSignMenuVBox.initializeMenuButtons(2, buttonEventHandler, 8);
    }

    private void handleButton(CustomButton btn){

        int buttonID = btn.getButtonID();

        switch(buttonID){

            case 5:
            switchMenuVBox(changeLangMenuVBox);
            System.out.println("Menu VBox has been changed");
            break;

            case 6:
            switchMenuVBox(logOrSignMenuVBox);
            break;

            case 7:
            Platform.exit();
            break;

            case 99:
            switchLanguage(btn.getText());
            switchMenuVBox(mainMenuVBox);
            System.out.println("Application interface language has been changed");
            break;

            default:
            System.out.println("You pressed button nr. " + buttonID);
            break;
        }
    }

    private void switchMenuVBox(VBox VBox){
        VBox newVBox = new VBox(VBox);
        menuVBox.getChildren().clear();
        menuVBox.getChildren().addAll(newVBox.getChildren());
    }

    private void switchLanguage(String lang){
        setLANG(lang);
        changeButtonsLang();
    }

    private void changeButtonsLang(){
        for(CustomButton btn : mainMenuVBox.getMenuButtons()){
            btn.setText(buttonLabelsDict.get(btn.getButtonID()).get(LANG));
        }

        for(CustomButton btn : logOrSignMenuVBox.getMenuButtons()){
            btn.setText(buttonLabelsDict.get(btn.getButtonID()).get(LANG));
        }
    }

    public void setLANG(String lang){
        LANG = lang;
    }
}
