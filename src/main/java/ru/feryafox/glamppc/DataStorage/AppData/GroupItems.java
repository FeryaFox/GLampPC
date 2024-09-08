package ru.feryafox.glamppc.DataStorage.AppData;

import ru.feryafox.GLamp.GLampData.GLampMode;
import ru.feryafox.GLamp.GLampData.GLampModes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class GroupItems implements Iterable<GroupItem> {
    public ArrayList<GroupItem> groupItems;

    public void setGroupItems(ArrayList<GroupItem> groupItems) {
        this.groupItems = groupItems;
    }

    @Override
    public Iterator<GroupItem> iterator() {
        return new GroupItemIterator();
    }

    public GroupItem get(int index) {
        return groupItems.get(index);
    }

    public ArrayList<GroupItem> getGroupItems() {
        return groupItems;
    }

    public GroupItems(ArrayList<GroupItem> groupItems) {
        this.groupItems = groupItems;
    }

    public GroupItems(){
        groupItems = new ArrayList<>();
    }

    public void add(GroupItem groupItem){
        groupItems.add(groupItem);
    }

    private class GroupItemIterator implements Iterator<GroupItem> {
        private int currentIndex = 0;

        public boolean hasNext() {
            return currentIndex < groupItems.size();
        }

        @Override
        public GroupItem next() {
            if (hasNext()) {
                return groupItems.get(currentIndex++);
            }
            throw new NoSuchElementException();
        }
    }

    public int getGroupIndexByName(String groupName){
        int i = 0;
        for (GroupItem groupItem : groupItems) {
            if (Objects.equals(groupItem.name, groupName)) return i;
            i++;
        }
        return -1;
    }

//    public ArrayList<String> getModesName(int groupIndex){
//        ArrayList<String> modesName = new ArrayList<>();
//        for (GLampMode groupItem : groupItems.get(groupIndex).modes) {
//            modesName.add(groupItem.);
//        }
//        return modesName;
//    }
}
