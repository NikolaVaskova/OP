package org.example.logic;

public class ChecklistItem {
    private Integer id;
    private String label;
    private boolean done;

    public ChecklistItem(Integer id, String label) {
        this.id = id;
        this.label = label;
        this.done = false;
    }
    public String getLabel() {
        return label;
    }
    public Integer getId() {
        return id;
    }
    // Implement the isDone method
    public boolean isDone() {
        return done;
    }
    // Implement the setDone method
    public void setDone(boolean done) {
        this.done = done;
    }
}