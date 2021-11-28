package controller;

import java.io.IOException;
import java.util.ArrayList;
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

public class MainMenuController {

    @FXML
    private VBox menuVBox;
    private VBox mainMenuVBox;
    private VBox selectDifficultyLevelMenuVBox;
    private VBox changeLangMenuVBox;

    private CustomButton[] mainMenuButtons;

    private String rootPath;
    private String LANG;
    private int mainMenuButtonsCount;
    private List<CSVRecord> buttonLabelsDict;
    private EventHandler<MouseEvent> buttonEventHandler;

    public MainMenuController(){
        rootPath = System.getProperty("user.dir");
        LANG = "EN";
        mainMenuButtonsCount = 8;
        mainMenuButtons = new CustomButton[mainMenuButtonsCount];
    }

    @FXML
    void initialize() throws IOException{
        loadButtonLabels();
        initializeButtonEventHandler();
        initializeMainMenu();
        initializeChangeLangMenu();
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

    private void handleButton(CustomButton btn){

        int buttonID = btn.getButtonID();

        switch(buttonID){

            case 5:
            switchMenuVBox(changeLangMenuVBox);
            System.out.println("Menu VBox has been changed");
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

    private void initializeMainMenu(){
        mainMenuVBox = new VBox();
        initializeMainMenuButtons();
    }

    private void switchMenuVBox(VBox VBox){
        VBox newVBox = new VBox(VBox);
        menuVBox.getChildren().clear();
        menuVBox.getChildren().addAll(newVBox.getChildren());
        menuVBox.setAlignment(Pos.CENTER);
    }

    private void switchLanguage(String lang){
        setLANG(lang);
        changeButtonsLang();
    }

    private void changeButtonsLang(){
        for(CustomButton btn : mainMenuButtons){
            btn.setText(buttonLabelsDict.get(btn.getButtonID()).get(LANG));
        }
    }

    private void initializeMainMenuButtons(){
        CustomButton newButton;
        for(int i=0; i < mainMenuButtonsCount; i++){
            newButton = new CustomButton(i);
            newButton.setText(buttonLabelsDict.get(i).get(LANG));
            newButton.setPrefSize(200, 50);
            newButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            mainMenuButtons[i] = newButton;
            mainMenuVBox.getChildren().add(mainMenuButtons[i]);
        }
    }

    private void initializeChangeLangMenu(){
        changeLangMenuVBox = new VBox();
        List<String> availableLangsList = buttonLabelsDict.get(0).getParser().getHeaderNames();
        ArrayList<String> availableLangsArrayList = new ArrayList<String>(availableLangsList);
        availableLangsArrayList.remove(0);                                  //Usuwanie komórki "ButtonID"
        int availableLangsCount = availableLangsArrayList.size();

        CustomButton selectLangButton[] = new CustomButton[availableLangsCount];
        CustomButton newCustomButton;
        for(int i=0; i < availableLangsCount; i++){
            newCustomButton = new CustomButton(99);                         //id=99 zarezerwowane dla buttonow do zmiany jezyka
            newCustomButton.setText(availableLangsArrayList.get(i));
            newCustomButton.setPrefSize(200, 50);
            newCustomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            selectLangButton[i] = newCustomButton;
            changeLangMenuVBox.getChildren().add(selectLangButton[i]);
        }
    }

    public void setLANG(String lang){
        LANG = lang;
    }
}
