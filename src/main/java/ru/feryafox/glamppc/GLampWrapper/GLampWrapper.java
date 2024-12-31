package ru.feryafox.glamppc.GLampWrapper;

import ru.feryafox.GLamp.GLamp;
import ru.feryafox.GLamp.GLampData.GLampMode;
import ru.feryafox.GLamp.GLampData.GLampModes;
import ru.feryafox.glamppc.DataStorage.AppData.GroupItem;
import ru.feryafox.glamppc.DataStorage.DataStorage;

import java.util.ArrayList;
import java.util.List;

public class GLampWrapper {
    private DataStorage dataStorage;
    private ArrayList<GLamp> gLampList;
    private int currentGroupId;

    public GLampWrapper(DataStorage dataStorage, String localIp) {
        this.dataStorage = dataStorage;
        createGLampList(localIp);
        this.currentGroupId = dataStorage.appData.selectedGroup;
    }

    private void createGLampList(String localIp) {
        gLampList = new ArrayList<>();

        int groupId = 1;

        for (GroupItem gi : dataStorage.appData.groups) {
            var lampBuilder = new GLamp.Builder();
            lampBuilder.setLocalIp(localIp);
            lampBuilder.setKey(gi.glKey);
            lampBuilder.setGroupId(groupId++);
            gLampList.add(lampBuilder.build());
        }
    }

    public void updateLocalIp(String localIp) {
        for (GLamp glamp : gLampList) {
            glamp.setLocalIp(localIp);
        }
    }

    GLamp getGLamp(int groupId) {
        return gLampList.get(groupId);
    }

    public void setCurrentGroupId(int currentGroupId) {
        this.currentGroupId = currentGroupId;
    }

    public void updateSettings() {
        gLampList.get(currentGroupId).updateSettings(dataStorage.appData.groups.get(currentGroupId).settings);
    }

    public void updateDawn() {
        gLampList.get(currentGroupId).setDawn(dataStorage.appData.groups.get(currentGroupId).dawn);
    }

    public void updatePalette() {
        gLampList.get(currentGroupId).setPalette(dataStorage.appData.groups.get(currentGroupId).palette);
    }

    public void updateModes() {
        gLampList.get(currentGroupId).uploadModes(dataStorage.appData.groups.get(currentGroupId).modes);
    }

    public void updateKey(String key) {
        GLamp lamp = getGLamp(currentGroupId);
        lamp.setKey(key);
    }

    public ArrayList<String> getNotDisableGroup(){
        ArrayList<String> notDisableGroup = new ArrayList<>();
        for (GroupItem gi : dataStorage.appData.groups) {
            if (!gi.is_disabled) notDisableGroup.add(gi.name);
        }
        return notDisableGroup;
    }

    public String getChoiceGroup(){
        return dataStorage.appData.groups.get(currentGroupId).name;
    }

    public GLamp getCurrentGLamp(){
        return gLampList.get(currentGroupId);
    }

    public void onLamp(){
        getCurrentGLamp().turnOn();
    }

    public void offLamp(){
        getCurrentGLamp().turnOff();
    }

    public void setTimer(Integer minute){
        getCurrentGLamp().turnOffTimer(minute);
    }

    public void nextMode() {
        getCurrentGLamp().nextMode();
        dataStorage.appData.groups.get(currentGroupId).modes.nextMode();
    }

    public void prevMode() {
        getCurrentGLamp().prevMode();
        dataStorage.appData.groups.get(currentGroupId).modes.prevMode();
    }

    public int getCurrentModeId() {
        return dataStorage.appData.groups.get(currentGroupId).modes.getCurrentMode();
    }

    public GLampMode getCurrentMode() {
        return dataStorage.appData.groups.get(currentGroupId).modes.getMode(getCurrentModeId() - 1);
    }

    public String getCurrentModeName() {
        return GLampModes.Converter.getEffectTypeNameById(getCurrentMode().getEffectType());
    }

    public ArrayList<GLampMode> getModes() {
        return dataStorage.appData.groups.get(currentGroupId).modes.getModes();
    }

    public List<String> getModesName() {
        List<String> names =  getModes().stream().
                map(GLampMode::getEffectType).
                map(GLampModes.Converter::getEffectTypeNameById)
                .toList();
        return names;
    }

    public void setMode(int modeId) {
        getCurrentGLamp().setMode(modeId);
        dataStorage.appData.groups.get(currentGroupId).modes.setCurrentMode(modeId);
    }
}
