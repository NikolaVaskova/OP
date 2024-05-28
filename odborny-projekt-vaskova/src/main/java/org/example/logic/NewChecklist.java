package org.example.logic;

import java.util.ArrayList;
import java.util.List;

public class NewChecklist {

    private List<ChecklistItem> items;

    public NewChecklist() {
        this.items = new ArrayList<>();
    }

    public void addItem(ChecklistItem item) {
        this.items.add(item);
    }

    public List<ChecklistItem> getItems() {
        return items;
    }

}