package ru.feryafox.glamppc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.feryafox.glamppc.Controllers.MainStageController;
import ru.feryafox.glamppc.Controllers.SettingsStageController;
import ru.feryafox.glamppc.Controllers.TimerStageController;
import ru.feryafox.glamppc.DataStorage.DataStorage;
import ru.feryafox.glamppc.GLampWrapper.GLampWrapper;
import ru.feryafox.glamppc.NetAdapters.Exceptions.CurrentAdapterIsOutsideArrayException;
import ru.feryafox.glamppc.NetAdapters.NetAdapters;

import java.io.IOException;


public class GLampPCApplication extends Application {
    private boolean isGroupEditorStageOpen = false;
    private boolean isConfigStageOpen = false;
    private boolean isServiceStageOpen = false;
    private boolean isModeEditStageOpen = false;
    private boolean isPaletteStageOpen = false;
    private boolean isSettingsStageOpen = false;
    private boolean isTimerStageOpen = false;
    private DataStorage ds;
    private GLampWrapper gLampWrapper;
    private NetAdapters netAdapters;

    private Stage primaryStage;
    private Stage groupEditorStage = new Stage();
    private Stage configStage = new Stage();
    private Stage serviceStage = new Stage();
    private Stage modeEditStage = new Stage();
    private Stage paletteStage = new Stage();
    private Stage settingsStage = new Stage();
    private Stage timerStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws IOException {

        ds = new DataStorage();
        ds.deserialize();

        if (ds.appData.appSettings.selectedNetAdapter != -1) {
            try {
                netAdapters = new NetAdapters(ds.appData.appSettings.selectedNetAdapter);
            } catch (CurrentAdapterIsOutsideArrayException e) {
                netAdapters = new NetAdapters(0);
                ds.appData.appSettings.selectedNetAdapter = 0;
                ds.serialize();
            }
        }
        else {
            netAdapters = new NetAdapters(0);
            ds.appData.appSettings.selectedNetAdapter = 0;
            ds.serialize();
        }
        System.out.println(netAdapters.getCurrentSelected());
        System.out.println(netAdapters.getCurrentIpAddress());
        gLampWrapper = new GLampWrapper(ds, netAdapters.getCurrentIpAddress());

        FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/main-stage.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 450);
        this.primaryStage = primaryStage;
        primaryStage.setTitle("GLampPC");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(event -> {
            this.settingsStage.close();
            this.timerStage.close();
            this.paletteStage.close();
            this.serviceStage.close();
            this.groupEditorStage.close();
            this.configStage.close();
            this.modeEditStage.close();
        });

        primaryStage.show();

        MainStageController controller = fxmlLoader.getController();
        controller.setMainApp(this);
        controller.setDataStorage(ds);
        controller.setGlampWrapper(gLampWrapper);
        controller.init();
    }

    public static void main(String[] args) {
        launch();
    }

    public void openGroupEditorStage() throws IOException {
        if (!isGroupEditorStageOpen) {
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/group-editor-stage.fxml"));

            Scene groupEditorScene = new Scene(fxmlLoader.load(), 380, 420);
            Stage groupEditorStage = new Stage();

            groupEditorStage.setResizable(false);
            groupEditorStage.setOnCloseRequest(event -> isGroupEditorStageOpen = false);
            groupEditorStage.setTitle("Редактирование групп");
            groupEditorStage.setScene(groupEditorScene);

            groupEditorStage.show();
            isGroupEditorStageOpen = true;

            this.groupEditorStage = groupEditorStage;
        }
    }

    public void openConfigStage() throws IOException {
        if (!isConfigStageOpen) {
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/config-stage.fxml"));

            Scene configScene = new Scene(fxmlLoader.load(), 450, 400);
            Stage configStage = new Stage();
            configStage.setResizable(false);

            configStage.setOnCloseRequest(event -> isConfigStageOpen = false);
            configStage.setTitle("Конфиг");
            configStage.setScene(configScene);

            configStage.show();
            isConfigStageOpen = true;

            this.configStage = configStage;
        }
    }

    public void openServiceStage() throws IOException {
        if (!isServiceStageOpen) {
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/service-stage.fxml"));

            Scene serviceScene = new Scene(fxmlLoader.load(), 400, 400);
            Stage serviceStage = new Stage();
            serviceStage.setResizable(false);

            serviceStage.setOnCloseRequest(event -> isServiceStageOpen = false);
            serviceStage.setTitle("Сервис");
            serviceStage.setScene(serviceScene);

            serviceStage.show();
            isServiceStageOpen = true;

            this.serviceStage = serviceStage;
        }
    }

    public void openModeEditStage() throws IOException {
        if (!isModeEditStageOpen){
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/modes-editor-stage.fxml"));

            Scene modeEditScene = new Scene(fxmlLoader.load(), 400, 350);
            Stage modeEdirStage = new Stage();
            modeEdirStage.setResizable(false);

            modeEdirStage.setOnCloseRequest(event -> isModeEditStageOpen = false);
            modeEdirStage.setTitle("Редактирование режимов");
            modeEdirStage.setScene(modeEditScene);

            modeEdirStage.show();
            isModeEditStageOpen = true;

            this.modeEditStage = modeEdirStage;
        }
    }

    public void openPaletteStage() throws IOException {
        if (!isPaletteStageOpen){
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/palette-stage.fxml"));

            Scene palatteScene = new Scene(fxmlLoader.load(), 400, 400);
            Stage paletteStage = new Stage();
            paletteStage.setResizable(false);

            paletteStage.setOnCloseRequest(event -> isPaletteStageOpen = false);
            paletteStage.setTitle("Палитра");
            paletteStage.setScene(palatteScene);

            paletteStage.show();
            isPaletteStageOpen = true;

            this.paletteStage = paletteStage;
        }
    }

    public void openSettingStage() throws IOException {
        if (!isSettingsStageOpen){
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/settings-stage.fxml"));

            Scene settingsScene = new Scene(fxmlLoader.load(), 400, 400);
            Stage settingsStage = new Stage();
            settingsStage.setResizable(false);

            settingsStage.setOnCloseRequest(event -> isSettingsStageOpen = false);
            settingsStage.setTitle("Палитра");
            settingsStage.setScene(settingsScene);

            SettingsStageController controller = fxmlLoader.getController();
            controller.setDs(ds);
            controller.setgLampWrapper(gLampWrapper);
            controller.setNetAdapters(netAdapters);
            controller.init();

            settingsStage.show();
            isSettingsStageOpen = true;

            this.settingsStage = settingsStage;
        }
    }

    public void openTimerStage() throws IOException {
        if (!isTimerStageOpen){
            FXMLLoader fxmlLoader = new FXMLLoader(GLampPCApplication.class.getResource("stage/timer-stage.fxml"));

            Scene timerScene = new Scene(fxmlLoader.load(), 250, 200);
            Stage timerStage = new Stage();
            timerStage.setResizable(false);

            timerStage.setOnCloseRequest(event -> isTimerStageOpen = false);
            timerStage.setTitle("Таймер");
            timerStage.setScene(timerScene);
            timerStage.initModality(Modality.WINDOW_MODAL);
            timerStage.initOwner(primaryStage);

            TimerStageController controller = fxmlLoader.getController();
            controller.setMainApp(this);
            controller.setDataStorage(ds);
            controller.setGlampWrapper(gLampWrapper);

            timerStage.show();
            isTimerStageOpen = true;

            this.timerStage = timerStage;
        }
    }

    public void openErrorStage(String title, String header, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
}