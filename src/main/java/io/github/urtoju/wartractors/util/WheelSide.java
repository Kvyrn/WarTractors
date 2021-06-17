package io.github.urtoju.wartractors.util;

public enum WheelSide {
    FRONT_LEFT,
    FRONT_RIGHT,
    BACK_LEFT,
    BACK_RIGHT;

    public boolean isFront() {
        return this == FRONT_LEFT || this == FRONT_RIGHT;
    }

    public boolean isBack() {
        return this == BACK_LEFT || this == BACK_RIGHT;
    }

    public boolean isLeft() {
        return this == BACK_LEFT || this == FRONT_LEFT;
    }

    public boolean isRight() {
        return this == BACK_RIGHT || this == FRONT_RIGHT;
    }
}
