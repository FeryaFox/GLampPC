package ru.feryafox.glamppc.NetAdapters.Exceptions;

public class CurrentAdapterIsOutsideArrayException extends NetAdaptersException {
    public CurrentAdapterIsOutsideArrayException(int currentAdapter, int adapterCounts) {
        super("Введенный номер сетевого интерфейса (" + currentAdapter + ") находится за границами кол-ва интерфейсов (" + adapterCounts + ").");
    }
}
