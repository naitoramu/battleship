package battleship.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import battleship.Main;
import battleship.classes.CustomButton;
import battleship.classes.Menu;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private VBox menuVBox;
    private Menu menu;

    private String rootPath;
    private String LANG;
    private EventHandler<MouseEvent> buttonEventHandler;

    public MainMenuController(){
        rootPath = System.getProperty("user.dir");
        LANG = "EN";
    }

    @FXML
    void initialize() throws IOException{
        initializeButtonEventHandler();
        initializeMenu();
    }

    private void initializeButtonEventHandler(){
        buttonEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CustomButton btn = (CustomButton) event.getSource();
                try {
                    handleButton(btn);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
    }

    private void initializeMenu() throws IOException{
        menu = new Menu(buttonEventHandler, rootPath);
        switchMenuVBox(menu.getMainMenuVBox());
    }

    private void handleButton(CustomButton btn) throws IOException{

        int buttonID = btn.getButtonID();

        switch(buttonID){

            case 0:
            startGame(btn);

            case 5:
            switchMenuVBox(menu.getChangeLangMenuVBox());
            System.out.println("Menu VBox has been changed");
            break;

            case 6:
            switchMenuVBox(menu.getLogOrSignMenuVBox());
            break;

            case 7:
            Platform.exit();
            break;

            case 9:
            switchMenuVBox(menu.getRegisterMenuVBox());
            break;

            case 10:
            System.out.println(Main.getUsers());
            break;

            case 99:
            switchLanguage(btn.getText());
            switchMenuVBox(menu.getMainMenuVBox());
            System.out.println("Application interface language has been changed");
            break;

            default:
            System.out.println("You pressed button nr. " + buttonID);
            break;
        }
    }

    private void startGame(CustomButton btn) throws IOException{
        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/gameView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/game.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);
    }

    private void switchMenuVBox(VBox VBox){
        VBox newVBox = new VBox(VBox);
        menuVBox.getChildren().clear();
        menuVBox.getChildren().addAll(newVBox.getChildren());
    }

    private void switchLanguage(String lang){
        setLANG(lang);
        menu.changeButtonsLang(lang);
    }

    public void setLANG(String lang){
        LANG = lang;
    }
}
