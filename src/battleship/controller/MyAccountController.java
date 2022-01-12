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
    private Button logOutButton;
    @FXML
    private Label myAccountLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView avatarImageView;

    private CSVDictReader dictionary;
    private Image avatar;
    private User user;
    private static final String ROOT_PATH = Main.getRootPath();

    public MyAccountController() throws IOException {
        dictionary = Main.getDictionary();
        user = Main.getLogedUser();
        loadAvatar();
    }

    @FXML
    void initialize() {
        backButton.setText(dictionary.getLabelByName("back").get(Main.getInterfaceLanguage()));
        changePasswdButton.setText(dictionary.getLabelByName("change-passwd").get(Main.getInterfaceLanguage()));
        logOutButton.setText(dictionary.getLabelByName("log-out").get(Main.getInterfaceLanguage()));
        myAccountLabel.setText(dictionary.getLabelByName("my-account").get(Main.getInterfaceLanguage()));
        avatarImageView.setImage(avatar);
        usernameLabel.setText(user.getUsername());
    }

    private void loadAvatar() throws FileNotFoundException {
        FileInputStream input = new FileInputStream(ROOT_PATH + "/src/battleship/avatar/human-avatar.png");
        avatar = new Image(input);
    }

    public void changePasswdButtonPressed(ActionEvent actionEvent) throws IOException {
        Main.setMenuStartPage("change-passwd-menu");
        backToMainMenu((Button) actionEvent.getSource());
    }

    public void logOutButtonPressed(ActionEvent actionEvent) throws IOException {
        logOutUser();
        backToMainMenu((Button) actionEvent.getSource());
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        backToMainMenu((Button) actionEvent.getSource());
    }

    private void logOutUser() {
        Main.setUserLogedIn(false);
        Main.setLogedUser(null);
    }

    private void backToMainMenu(Button btn) throws IOException {

        Parent newRoot = FXMLLoader.load(getClass().getResource("/battleship/view/mainMenuView.fxml"));
        Scene scene = new Scene(newRoot);
        Stage stageTheButtonBelongs = (Stage) btn.getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("/battleship/view/stylesheet/mainMenu.css").toExternalForm());
        stageTheButtonBelongs.setScene(scene);

    }

}
