package com.educorreia.hero.world;

public class Tile extends Element {
    Tile(int x, int y, String character, String color) {
        super(x, y, character, color);
    }

    Tile(int x, int y, String character) {
        super(x, y, character);
    }
}
