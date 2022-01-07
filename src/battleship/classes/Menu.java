package battleship.classes;

import java.io.IOException;

import battleship.Main;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Menu {

    private String rootPath;

    private CSVDictReader buttonLabels;
    private CSVDictReader promptLabels;
    private EventHandler<MouseEvent> buttonEventHandler;
    private MenuVBox mainMenuVBox;
    private MenuVBox changeLangMenuVBox;
    private MenuVBox logInOrRegisterMenuVBox;
    private MenuVBox registerMenuVBox;
    private MenuVBox logInMenuVBox;
    private MenuVBox changePasswdMenuVBox;

    public Menu(EventHandler<MouseEvent> buttonEventHandler, String rootPath) throws IOException {

        this.buttonEventHandler = buttonEventHandler;
        this.rootPath = rootPath;

        loadButtonLabels();
        loadTextFieldPromptLabels();
        initializeMainMenu();
        initializeChangeLangMenu();
        initializeLogInOrRegisterMenu();
        initializeRegisterMenu();
        initializeLogInMenu();
        initializeChangePasswdMenu();
    }

    private void initializeMainMenu() {
        mainMenuVBox = new MenuVBox(buttonLabels, promptLabels);
        mainMenuVBox.addButton("pvc", buttonEventHandler);
        mainMenuVBox.addButton("pvp", buttonEventHandler);
        mainMenuVBox.addButton("cvc", buttonEventHandler);
        mainMenuVBox.addButton("rank", buttonEventHandler);
        mainMenuVBox.addButton("change-lang", buttonEventHandler);
        mainMenuVBox.addButton("log-or-reg", buttonEventHandler);

        mainMenuVBox.addSpaceBeforeLastButton();

        mainMenuVBox.addButton("exit", buttonEventHandler);

    }

    private void initializeChangeLangMenu() {
        changeLangMenuVBox = new MenuVBox(buttonLabels, promptLabels);
        changeLangMenuVBox.addSelectLangButtons(buttonEventHandler);
    }

    private void initializeLogInOrRegisterMenu() {
        logInOrRegisterMenuVBox = new MenuVBox(buttonLabels, promptLabels);
        logInOrRegisterMenuVBox.addButton("login", buttonEventHandler);
        logInOrRegisterMenuVBox.addButton("registration", buttonEventHandler);

        logInOrRegisterMenuVBox.addSpaceBeforeLastButton();
        logInOrRegisterMenuVBox.addButton("back", buttonEventHandler);
    }

    private void initializeRegisterMenu() {
        registerMenuVBox = new MenuVBox(buttonLabels, promptLabels);
        registerMenuVBox.addTextField("username");
        registerMenuVBox.addPasswordField("password");
        registerMenuVBox.addButton("register", buttonEventHandler);

        registerMenuVBox.addSpaceBeforeLastButton();
        registerMenuVBox.addButton("back", buttonEventHandler);
    }

    private void initializeLogInMenu() {
        logInMenuVBox = new MenuVBox(buttonLabels, promptLabels);
        logInMenuVBox.addTextField("username");
        logInMenuVBox.addPasswordField("password");
        logInMenuVBox.addButton("log-in", buttonEventHandler);

        logInMenuVBox.addSpaceBeforeLastButton();
        logInMenuVBox.addButton("back", buttonEventHandler);
    }

    private void initializeChangePasswdMenu() {
        changePasswdMenuVBox = new MenuVBox(buttonLabels, promptLabels);
        changePasswdMenuVBox.addPasswordField("old-password");
        changePasswdMenuVBox.addPasswordField("new-password");
        changePasswdMenuVBox.addButton("submit-passwd-change", buttonEventHandler);

        changePasswdMenuVBox.addSpaceBeforeLastButton();
        changePasswdMenuVBox.addButton("back", buttonEventHandler);
    }

    private void loadButtonLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/button-labels.csv";
        try {
            buttonLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    private void loadTextFieldPromptLabels() throws IOException {

        String csvFilePath = rootPath + "/src/battleship/lang/textField-prompt-labels.csv";
        try {
            promptLabels = new CSVDictReader(csvFilePath);
        } catch (Exception IOException) {
            System.out.println("Cannot load file: " + csvFilePath);
        }
    }

    public void changeInterfaceLanguage(String language) {

        mainMenuVBox.setInterfaceLanguage(language);
        logInOrRegisterMenuVBox.setInterfaceLanguage(language);
        registerMenuVBox.setInterfaceLanguage(language);
        logInMenuVBox.setInterfaceLanguage(language);

    }

    public void refresh() {
        if(Main.isUserLogedIn()) {
            addButton("pvc", 0);
            addButton("pvp", 1);
            addButton("my-account", 5);
            swapLogOutButton();
        } else {
            removeButton("pvc");
            removeButton("pvp");
            removeButton("my-account");
            swapLogInButton();
        }

        mainMenuVBox.setButtonLabels();
        logInOrRegisterMenuVBox.setButtonLabels();
        registerMenuVBox.setButtonLabels();
        logInMenuVBox.setButtonLabels();
    }

    private void addButton(String buttonName, int buttonPosition) {
        if(!mainMenuVBox.isButtonAlreadyAdded(buttonName)) {
            mainMenuVBox.addButton(buttonName, buttonEventHandler, buttonPosition);
        }
    }

    private void removeButton(String buttonName) {
        if(mainMenuVBox.isButtonAlreadyAdded(buttonName)) {
            mainMenuVBox.removeButton(buttonName);
        }
    }

    public void swapLogOutButton() {
        mainMenuVBox.replaceButton("log-or-reg", "log-out");
    }
    public void swapLogInButton() {
        mainMenuVBox.replaceButton("log-out", "log-or-reg");
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

    public MenuVBox getLogInMenuVBox() {
        return logInMenuVBox;
    }

    public MenuVBox getChangePasswdMenuVBox() {
        return changePasswdMenuVBox;
    }

}
