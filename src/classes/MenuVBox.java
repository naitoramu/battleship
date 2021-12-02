package classes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MenuVBox  extends VBox{

    private int buttonWidth;
    private int buttonHeight;
    private String defaultLabelLang;
    private CustomButton[] menuButtons;
    private List<CSVRecord> buttonLabelsList;

    public MenuVBox(List<CSVRecord> buttonLabelsList){
        this.buttonWidth = 300;
        this.buttonHeight = 50;

        this.buttonLabelsList = buttonLabelsList;
        this.defaultLabelLang = "EN";
    }

    public void initializeMenuButtons(int buttonsCount, EventHandler<MouseEvent> buttonEventHandler, int firstID){
        this.menuButtons = new CustomButton[buttonsCount];
        CustomButton newCustomButton;
        int buttonID = firstID;
        for(int i=0; i < buttonsCount; i++){
            newCustomButton = new CustomButton(buttonID);
            newCustomButton.setText(buttonLabelsList.get(buttonID).get(defaultLabelLang));
            newCustomButton.setPrefSize(buttonWidth, buttonHeight);
            newCustomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            this.menuButtons[i] = newCustomButton;
            this.getChildren().add(this.menuButtons[i]);

            buttonID++;
        }
    }

    public void initializeSelectLangButtons(EventHandler<MouseEvent> buttonEventHandler){

        List<String> availableLangsList = buttonLabelsList.get(0).getParser().getHeaderNames();
        ArrayList<String> availableLangsArrayList = new ArrayList<String>(availableLangsList);
        availableLangsArrayList.remove(0);                                  //Usuwanie komórki "ButtonID"
        int availableLangsCount = availableLangsArrayList.size();

        this.menuButtons = new CustomButton[availableLangsCount];

        CustomButton newCustomButton;
        for(int i=0; i < availableLangsCount; i++){
            newCustomButton = new CustomButton(99);                         //id=99 zarezerwowane dla buttonow do zmiany jezyka
            newCustomButton.setText(availableLangsArrayList.get(i));
            newCustomButton.setPrefSize(buttonWidth, buttonHeight);
            newCustomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            menuButtons[i] = newCustomButton;
            this.getChildren().add(menuButtons[i]);
        }
    }

    public CustomButton[] getMenuButtons(){
        return menuButtons;
    }
}
