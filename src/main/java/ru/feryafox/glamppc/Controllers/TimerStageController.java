package ru.feryafox.glamppc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.feryafox.glamppc.DataStorage.DataStorage;
import ru.feryafox.glamppc.GLampPCApplication;
import ru.feryafox.glamppc.GLampWrapper.GLampWrapper;

public class TimerStageController {
    private GLampPCApplication mainApp;
    private GLampWrapper glampWrapper;
    private DataStorage dataStorage;

    @FXML
    TextField timerTextField;

    public void setDataStorage(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    public void setGlampWrapper(GLampWrapper glampWrapper) {
        this.glampWrapper = glampWrapper;
    }

    public void setMainApp(GLampPCApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    protected void setTimerBtnClick(){
        Integer timer = 0;
        try {
            timer = Integer.parseInt(timerTextField.getText());
        }
        catch (NumberFormatException e){
            mainApp.openErrorStage("Ошибка задания таймера", "Ошибка задания таймера", "Вы задали не число");
            return;
        }
        if (timer < 0 || timer > 255){
            mainApp.openErrorStage("Ошибка задания таймера", "Ошибка задания таймера", "Время задержки не может быть меньше 0 или больше 255");
            return;
        }

        glampWrapper.setTimer(timer);
    }
}
