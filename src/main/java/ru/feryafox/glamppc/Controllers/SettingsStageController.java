package ru.feryafox.glamppc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import ru.feryafox.glamppc.DataStorage.DataStorage;
import ru.feryafox.glamppc.GLampWrapper.GLampWrapper;
import ru.feryafox.glamppc.NetAdapters.NetAdapters;

import java.util.ArrayList;

public class SettingsStageController {

    private DataStorage ds;
    private GLampWrapper gLampWrapper;
    private NetAdapters netAdapters;

    @FXML
    private ChoiceBox<String> netInterfaceChoiceBox;

    public void init() {
        fillNetInterfaceChoiceBox();
    }

    private void fillNetInterfaceChoiceBox() {
        ArrayList<String> netAdaptersArrayList = netAdapters.getNetAdapters();
        netInterfaceChoiceBox.getItems().addAll(netAdaptersArrayList);
        netInterfaceChoiceBox.setValue(netAdaptersArrayList.get(ds.appData.appSettings.selectedNetAdapter));
    }

    public void setDs(DataStorage ds) {
        this.ds = ds;
    }

    public void setgLampWrapper(GLampWrapper gLampWrapper) {
        this.gLampWrapper = gLampWrapper;
    }

    public void setNetAdapters(NetAdapters netAdapters) {
        this.netAdapters = netAdapters;
    }

    @FXML
    protected void netInterfaceChoiceBoxChange() {
        int currentSelectedNetAdaptor = netInterfaceChoiceBox.getSelectionModel().getSelectedIndex();
        if (currentSelectedNetAdaptor != -1) {
            ds.appData.appSettings.selectedNetAdapter = currentSelectedNetAdaptor;
            netAdapters.setCurrentSelected(currentSelectedNetAdaptor);
            ds.serialize();
            gLampWrapper.updateLocalIp(netAdapters.getCurrentIpAddress());
        }
    }
}
