package battleship.classes;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class RegularMenuVBox extends MenuVBox{

    public RegularMenuVBox(List<CSVRecord> buttonLabelsList) {
        super(buttonLabelsList);
        //TODO Auto-generated constructor stub
    }

    public void initializeButtons(int buttonsCount, EventHandler<MouseEvent> buttonEventHandler, int buttonID){
        this.buttons = new CustomButton[buttonsCount];
        CustomButton newCustomButton;
        for(int i=0; i < buttonsCount; i++){
            newCustomButton = new CustomButton(buttonID);
            newCustomButton.setText(buttonLabelsList.get(buttonID).get(defaultLabelLang));
            newCustomButton.setPrefSize(buttonWidth, buttonHeight);
            newCustomButton.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler);
            this.buttons[i] = newCustomButton;
            this.getChildren().add(this.buttons[i]);

            buttonID++;
        }
    }
    
}
