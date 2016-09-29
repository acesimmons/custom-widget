package edu.jsu.mcis;

public class ShapeEvent {
    private boolean hexagonSelected;
    private boolean octagonSelected;


    public ShapeEvent() {
        this(false, false);
    }
    public ShapeEvent(boolean hexagonSelected, boolean octagonSelected) {
        this.hexagonSelected = hexagonSelected;
        this.octagonSelected = octagonSelected;
    }

    public boolean isHexagonSelected() { return hexagonSelected || !octagonSelected; }
    public boolean isOctagonSelected() { return octagonSelected || !hexagonSelected; }
}