package battleship.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import battleship.Main;
import battleship.classes.CSVDictReader;
import battleship.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MyAccountController {

    @FXML
    private Button backButton;
    @FXML
    private Button changePasswdButton;
    @FXML
    private Label myAccountLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView avatarImageView;

    private String rootPath;
    private CSVDictReader buttonLabels;
    private Image avatar;
    private User user;

    public MyAccountController() throws IOException {
        rootPath = System.getProperty("user.dir");
        user = Main.getLogedUser();
        loadButtonLabels();
        loadAvatar();
    }
    
    @FXML
    void initialize() {
        backButton.setText(buttonLabels.getLabelByName("back").get(Main.getInterfaceLanguage()));
        changePasswdButton.setText(buttonLabels.getLabelByName("change-passwd").get(Main.getInterfaceLanguage()));
        myAccountLabel.setText(buttonLabels.getLabelByName("my-account").get(Main.getInterfaceLanguage()));
        avatarImageView.setImage(avatar);
        usernameLabel.setText(user.getUsername());
    }

    private void loadAvatar() throws FileNotFoundException {
        FileInputStream input = new FileInputStream(rootPath + "/src/battleship/avatar/avatar-0.png");
        avatar = new Image(input);
    }

    private void loadButtonLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/button-labels.csv";
        try {
            buttonLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    public void changePasswdButtonPressed(ActionEvent actionEvent) throws IOException {
        Main.setMenuStartPage("change-passwd-menu");
        backToMainMenu((Button) actionEvent.getSource());
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        backToMainMenu((Button) actionEvent.getSource());
    }

    private void backToMainMenu(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

}
