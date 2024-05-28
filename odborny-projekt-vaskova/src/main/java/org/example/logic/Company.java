package org.example.logic;

import java.util.ArrayList;
//Tohle je hlavni logika trida, ktera bude pouzivana k spusteni logiky programu
public class Company {
    private ArrayList <NewChecklist> newChecklists;
    private ArrayList <ChecklistItem> checklistItems;


    public Company() {
        newChecklists = new ArrayList<>();
        checklistItems = new ArrayList<>();
    }
}
