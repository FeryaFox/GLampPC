package ru.feryafox.glamppc.NetAdapters;

import javafx.util.Pair;
import ru.feryafox.glamppc.NetAdapters.Exceptions.CurrentAdapterIsOutsideArrayException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class NetAdapters {
    private int currentSelected;
    private ArrayList<NetAdapter> netAdapters;

    public NetAdapters(int currentSelected) {
        this.currentSelected = currentSelected;
        updateNetAdapters();
    }

    public void updateNetAdapters(){
        netAdapters = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp()) { // Проверяем, является ли интерфейс активным
                    String interfaceName = networkInterface.getDisplayName();

                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.getHostAddress().contains(":")) {
                            String ipAddress = inetAddress.getHostAddress();
                            netAdapters.add(new NetAdapter(interfaceName, ipAddress));

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNetAdapters(){
        ArrayList<String> list = new ArrayList<>();

        for (NetAdapter netAdapter : netAdapters) {
            list.add(netAdapter.name() + ":" + netAdapter.ip());
        }

        return list;
    }

    public int getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        if (currentSelected > netAdapters.size() - 1) {
            throw new CurrentAdapterIsOutsideArrayException(currentSelected, netAdapters.size());
        }
        this.currentSelected = currentSelected;
    }

    public String getCurrentSelectedAdapterString(){
        NetAdapter netAdapter = getCurrentSelectedAdapter();
        return netAdapter.name() + ":" + netAdapter.ip();
    }

    public NetAdapter getCurrentSelectedAdapter(){
        return netAdapters.get(currentSelected);
    }

    public String getCurrentIpAddress(){
        return getCurrentSelectedAdapter().ip();
    }
}
