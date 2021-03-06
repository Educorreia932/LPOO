package com.educorreia.hero.world;

public class Player extends Creature {
    Inventory inventory;
    Weapon equippedWeapon = null;

    Player(int x, int y, int z) {
        super(x, y, z, "@");
        inventory = new Inventory();
    }

    public void pickupItem(Item item) {
        if (equippedWeapon == null)
            equippedWeapon = (Weapon) item;

        else
            inventory.addItem(item);
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
}
