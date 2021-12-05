package battleship.classes;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Menu {

    private int buttonIterator = 0;
    private int mainMenuButtonsCount = 8;

    private String rootPath;

    private List<CSVRecord> buttonLabelsList;
    private List<CSVRecord> textFieldPromptLabelsList;
    private EventHandler<MouseEvent> buttonEventHandler;
    private RegularMenuVBox mainMenuVBox;
    private SpecialMenuVBox changeLangMenuVBox;
    private RegularMenuVBox logInOrRegisterMenuVBox;
    private SpecialMenuVBox registerMenuVBox;
    


    public Menu(EventHandler<MouseEvent> buttonEventHandler, String rootPath) throws IOException {
        this.mainMenuButtonsCount = 8;
        this.buttonEventHandler = buttonEventHandler;
        this.rootPath = rootPath;
        loadButtonLabels();
        loadTextFieldPromptLabels();
        initializeMainMenu();
        initializeChangeLangMenu();
        initializeLogInOrRegisterMenu();
        initializeRegisterMenu();
    }

    private void initializeMainMenu(){
        mainMenuVBox = new RegularMenuVBox(buttonLabelsList);
        mainMenuVBox.initializeButtons(mainMenuButtonsCount, buttonEventHandler, buttonIterator);
    }

    private void initializeChangeLangMenu(){
        changeLangMenuVBox = new SpecialMenuVBox(buttonLabelsList, textFieldPromptLabelsList);
        changeLangMenuVBox.initializeSelectLangButtons(buttonEventHandler);
    }

    private void initializeLogInOrRegisterMenu(){
        logInOrRegisterMenuVBox = new RegularMenuVBox(buttonLabelsList);
        logInOrRegisterMenuVBox.initializeButtons(2, buttonEventHandler, 8);
    }

    private void initializeRegisterMenu(){
        registerMenuVBox = new SpecialMenuVBox(buttonLabelsList, textFieldPromptLabelsList);
        registerMenuVBox.initializeTextFields(2, 0);
        registerMenuVBox.initializeButtons(1, buttonEventHandler, 10);
    }

    private void loadButtonLabels() throws IOException{
        
        String csvFilePath = rootPath + "/src/battleship/lang/button-labels.csv";
        try {
            buttonLabelsList= CSVDictReader.parseCSV(csvFilePath);
        }catch(Exception IOException){
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    private void loadTextFieldPromptLabels() throws IOException{
        
        String csvFilePath = rootPath + "/src/battleship/lang/textField-prompt-labels.csv";
        try {
            textFieldPromptLabelsList= CSVDictReader.parseCSV(csvFilePath);
        }catch(Exception IOException){
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    public void changeButtonsLang(String lang){
        for(CustomButton btn : mainMenuVBox.getMenuButtons()){
            btn.setText(buttonLabelsList.get(btn.getButtonID()).get(lang));
        }

        for(CustomButton btn : logInOrRegisterMenuVBox.getMenuButtons()){
            btn.setText(buttonLabelsList.get(btn.getButtonID()).get(lang));
        }
    }

    public MenuVBox getMainMenuVBox() {
        return mainMenuVBox;
    }
    public MenuVBox getChangeLangMenuVBox() {
        return changeLangMenuVBox;
    }
    public MenuVBox getLogOrSignMenuVBox() {
        return logInOrRegisterMenuVBox;
    }
    public MenuVBox getRegisterMenuVBox() {
        return registerMenuVBox;
    }
    
}
