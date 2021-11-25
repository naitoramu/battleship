package controller;

import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

import org.apache.commons.csv.CSVRecord;

import javafx.fxml.FXML;
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

    public MainMenuController(){
        rootPath = System.getProperty("user.dir");
        LANG = "PL";
        menuButtonsCount = 8;
        menuButtons = new CustomButton[menuButtonsCount];
    }

    @FXML
    void initialize() throws IOException, CsvException{
        loadButtonLabels();
        initializeButtons();
    }

    private void loadButtonLabels() throws IOException, CsvException{
        
        String csvFilePath = rootPath + "/src/lang/button-labels.csv";

        try {
            buttonLabelsDict = CSVDictReader.parseCSV(csvFilePath);
        }catch(Exception IOException){
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    private void initializeButtons(){
        CustomButton newButton;
        for(int i=0; i < menuButtonsCount; i++){
            newButton = new CustomButton(i);
            newButton.setText(buttonLabelsDict.get(i).get(LANG));
            newButton.setPrefSize(200, 50);
            menuButtons[i] = newButton;
            menuVBox.getChildren().add(menuButtons[i]);
        }
    }
}
