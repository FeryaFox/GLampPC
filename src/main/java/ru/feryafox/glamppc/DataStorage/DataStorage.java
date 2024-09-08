package ru.feryafox.glamppc.DataStorage;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.feryafox.GLamp.GLampData.*;
import ru.feryafox.glamppc.DataStorage.AppData.AppData;
import ru.feryafox.glamppc.DataStorage.AppData.AppSettings;
import ru.feryafox.glamppc.DataStorage.AppData.GroupItem;
import ru.feryafox.glamppc.DataStorage.AppData.GroupItems;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataStorage {

    public AppData appData;
    private Gson gson;
    private String filename = "GLampPC.json";

    public static void main(String[] args) {
        DataStorage dataStorage = new DataStorage();
        dataStorage.serialize();

    }

    public DataStorage() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void serialize(){
        initAppData();
        String json = gson.toJson(appData);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deserialize(){
        try {
            FileReader reader = new FileReader(filename);
            appData = gson.fromJson(reader, AppData.class);
            reader.close();
        } catch (FileNotFoundException e) {
            initAppData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initAppData(){
        if (appData != null) return;

        appData = new AppData();
        appData.selectedGroup = 0;

        AppSettings settings = new AppSettings();
        settings.disableSync = false;
        settings.startUpSync = false;
        settings.selectedNetAdapter = 0;
        appData.appSettings = settings;

        GroupItems groupItems = new GroupItems();
        for (int i = 0; i < 11; i++){
            GroupItem groupItem = new GroupItem();

            groupItem.name = "Группа " + (i + 1);

            GLampSettings gLampSettings = new GLampSettings.Builder().build();
            GLampDawn gLampDawn = new GLampDawn.Builder().build();
            GLampPalette gLampPalette = new GLampPalette();
            gLampPalette.addBand(0, 0, 0);
            gLampPalette.addBand(0, 0, 0);

            GLampModes gLampModes = new GLampModes();
            var gLampMode = new GLampMode.Builder();
            gLampMode.setEffectType(1);
            gLampMode.setFlagPersonalBrightness(0);
            gLampMode.setPersonalBrightness(74);
            gLampMode.setAdditional(1);
            gLampMode.setSoundReaction(1);
            gLampMode.setMinSignal(0);
            gLampMode.setMaxSignal(255);
            gLampMode.setSpeed(166);
            gLampMode.setPalette(3);
            gLampMode.setScale(97);
            gLampMode.setFromCenter(0);
            gLampMode.setColor(0);
            gLampMode.setRandom(0);

            gLampModes.addMode(gLampMode.build());

            groupItem.settings = gLampSettings;
            groupItem.dawn = gLampDawn;
            groupItem.modes = gLampModes;
            groupItem.palette = gLampPalette;

            groupItem.is_disabled = false;
            groupItem.selected_mode = 0;
            groupItem.glKey = "GL";

            groupItems.add(groupItem);
        }

        appData.groups = groupItems;
    }
}
