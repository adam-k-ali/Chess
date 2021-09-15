package com.kaneras.chess;

public class Properties {
    public static class StaticProperties {
        public static final int MIN_WIDTH = 500;
        public static final int MIN_HEIGHT = 500;
    }

    public static class DynamicProperties {
        public static int canvasWidth = StaticProperties.MIN_WIDTH;
        public static int canvasHeight = StaticProperties.MIN_HEIGHT;
    }
}
