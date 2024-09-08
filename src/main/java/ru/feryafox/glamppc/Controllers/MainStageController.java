package ru.feryafox.glamppc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ru.feryafox.GLamp.GLampData.GLampMode;
import ru.feryafox.GLamp.GLampData.GLampModes;
import ru.feryafox.glamppc.DataStorage.DataStorage;
import ru.feryafox.glamppc.GLampWrapper;
import ru.feryafox.glamppc.MainApplication;

import java.io.IOException;
import java.util.stream.Collectors;

public class MainStageController {
    private MainApplication mainApp;
    private GLampWrapper glampWrapper;
    private DataStorage dataStorage;

    @FXML
    private ChoiceBox<String> groupChoice;

    @FXML
    private ChoiceBox<String> modeChoice;

    public void init() {
        fillGroupChoiceBox();
        fillModeChoiceBox();
    }

    public void fillGroupChoiceBox(){
        groupChoice.getItems().clear();
        groupChoice.setValue(glampWrapper.getChoiceGroup());
        groupChoice.getItems().addAll(glampWrapper.getNotDisableGroup());
    }

    public void fillModeChoiceBox(){
        modeChoice.getItems().clear();
        modeChoice.setValue(glampWrapper.getCurrentModeName());
        modeChoice.getItems().addAll(glampWrapper.getModesName());
        glampWrapper.updateModes();
    }

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void setGlampWrapper(GLampWrapper glampWrapper) {
        this.glampWrapper = glampWrapper;
    }

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    protected void handleGroupChoiceChange(){
        int groupChoiceIndex = dataStorage.appData.groups.getGroupIndexByName(groupChoice.getSelectionModel().getSelectedItem());
        if (groupChoiceIndex == -1) return;

        glampWrapper.setCurrentGroupId(groupChoiceIndex);
        dataStorage.appData.selectedGroup = groupChoiceIndex;
        dataStorage.serialize();
        fillModeChoiceBox();
    }

    @FXML
    protected void handleModeChoiceChange(){
        int modeChoiceIndex = modeChoice.getSelectionModel().getSelectedIndex();
        if (modeChoiceIndex == -1) return;

        glampWrapper.setMode(modeChoiceIndex + 1);
        // STOP когда меняю режим ничего не происходит

    }

    @FXML
    protected void onSyncBtnClick() {
        System.out.println(123);
    }

    @FXML
    protected void onEditGroupBtnClick() throws IOException {
        mainApp.openGroupEditorStage();
    }

    @FXML
    protected void onConfigBtnClick() throws IOException {
        mainApp.openConfigStage();
    }

    @FXML
    protected void onServiceBtnClick() throws IOException {
        mainApp.openServiceStage();
    }

    @FXML
    protected void onModeEditBtnClick() throws IOException {
        mainApp.openModeEditStage();
    }

    @FXML
    protected void onPaletteBtnClick() throws IOException {
        mainApp.openPaletteStage();
    }

    @FXML
    protected void onSettingsBtnClick() throws IOException {
        mainApp.openSettingStage();
    }

    @FXML
    protected void timerBtnClick() throws IOException {
        mainApp.openTimerStage();
    }

    @FXML
    protected void onBtnClick() {
        glampWrapper.onLamp();
    }

    @FXML
    protected void offBtnClick(){
        glampWrapper.offLamp();
    }

    @FXML
    protected void prevModeBtnClick(){
        glampWrapper.prevMode();
    }

    @FXML
    protected void nextModeBtnClick(){
        glampWrapper.nextMode();
    }
}