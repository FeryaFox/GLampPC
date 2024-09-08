package ru.feryafox.glamppc.DataStorage.AppData;


import com.google.gson.annotations.Expose;

public class AppData {
    public final String version = "1.0";
    public GroupItems groups;
    public AppSettings appSettings;
    public int selectedGroup;

    public String getVersion() {
        return version;
    }
}

