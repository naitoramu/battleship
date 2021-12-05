package battleship.classes;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MenuVBox  extends VBox{

    protected int buttonWidth;
    protected int buttonHeight;
    protected String defaultLabelLang;
    protected CustomButton[] buttons;
    protected List<CSVRecord> buttonLabelsList;

    public MenuVBox(List<CSVRecord> buttonLabelsList){
        this.buttonWidth = 300;
        this.buttonHeight = 50;

        this.buttonLabelsList = buttonLabelsList;
        this.defaultLabelLang = "EN";
    }

    public CustomButton[] getMenuButtons(){
        return buttons;
    }

}
