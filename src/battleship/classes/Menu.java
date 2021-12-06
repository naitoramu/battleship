package battleship.classes;

import java.io.IOException;
import java.util.ArrayList;
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
    private MenuVBox mainMenuVBox;
    private MenuVBox changeLangMenuVBox;
    private MenuVBox logInOrRegisterMenuVBox;
    private MenuVBox registerMenuVBox;
    


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
        mainMenuVBox = new MenuVBox(buttonLabelsList, textFieldPromptLabelsList);
        for(int i = 0; i < 8; i++){
            mainMenuVBox.addButton(i, buttonEventHandler);
        }

    }

    private void initializeChangeLangMenu(){
        changeLangMenuVBox = new MenuVBox(buttonLabelsList, textFieldPromptLabelsList);
        changeLangMenuVBox.addSelectLangButtons(buttonEventHandler);
    }

    private void initializeLogInOrRegisterMenu(){
        logInOrRegisterMenuVBox = new MenuVBox(buttonLabelsList, textFieldPromptLabelsList);
        logInOrRegisterMenuVBox.addButton(8, buttonEventHandler);
        logInOrRegisterMenuVBox.addButton(9, buttonEventHandler);
    }

    private void initializeRegisterMenu(){
        registerMenuVBox = new MenuVBox(buttonLabelsList, textFieldPromptLabelsList);
        registerMenuVBox.addTextField(0);
        registerMenuVBox.addTextField(1);
        registerMenuVBox.addButton(10, buttonEventHandler);
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
