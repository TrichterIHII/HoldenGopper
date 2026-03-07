package net.holdengopper.core.button;

public class ButtonEvent {
    private final Button source;

    public ButtonEvent(Button source) {
        this.source = source;
    }

    public Button getSource() {
        return source;
    }
}
