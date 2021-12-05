package battleship.classes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SpecialMenuVBox extends RegularMenuVBox{

    private ArrayList<CustomTextField> textFields = new ArrayList<CustomTextField>();
    private int textFieldHeight = 20;
    private int textFieldWidth = 400;
    protected List<CSVRecord> textFieldPromptLabelsList;

    public SpecialMenuVBox(List<CSVRecord> buttonLabelsList, List<CSVRecord> textFieldPromptLabelsList) {
        super(buttonLabelsList);
        this.textFieldPromptLabelsList = textFieldPromptLabelsList;
        //TODO Auto-generated constructor stub
    }

    public void initializeSelectLangButtons(EventHandler<MouseEvent> buttonEventHandler){

        List<String> availableLangsList = buttonLabelsList.get(0).getParser().getHeaderNames();
        ArrayList<String> availableLangsArrayList = new ArrayList<String>(availableLangsList);
        availableLangsArrayList.remove(0);                                  //Usuwanie komórki "ButtonID"
        int availableLangsCount = availableLangsArrayList.size();

        this.buttons = new CustomButton[availableLangsCount];

        CustomButton newCustomButton;
        for(int i=0; i < availableLangsCount; i++){
            newCustomButton = new CustomButton(99);                         //id=99 zarezerwowane dla buttonow do zmiany jezyka
            newCustomButton.setText(availableLangsArrayList.get(i));
            newCustomButton.setPrefSize(buttonWidth, buttonHeight);
            newCustomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            buttons[i] = newCustomButton;
            this.getChildren().add(buttons[i]);
        }
    }

    public void initializeTextFields(int textFieldsCount, int textFieldID) {
        CustomTextField newTextField;
        for(int i=0; i < textFieldsCount; i++){
            newTextField = new CustomTextField(textFieldID);
            newTextField.setPromptText(textFieldPromptLabelsList.get(textFieldID).get(defaultLabelLang));
            newTextField.setPrefSize(textFieldWidth, textFieldHeight);
            textFields.add(newTextField);
            this.getChildren().add(newTextField);

            textFieldID++;
        }
    }
    
}
