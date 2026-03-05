package net.holdengopper.core.button;

public record ButtonEvent(Button source) {

    public Button getSource() {
        return source;
    }
}
