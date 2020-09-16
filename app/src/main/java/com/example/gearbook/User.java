package com.example.gearbook;

import java.util.ArrayList;

public class User {
    private ArrayList<Gear> allGear;

    public User(ArrayList<Gear> allGear) {
        this.allGear = allGear;
    }

    public ArrayList<Gear> getAllGear() {
        return allGear;
    }

    public boolean addGear(Gear gear) {
        return this.allGear.add(gear);
    }

    public boolean deleteGear(Integer gearIndex) {
        return this.allGear.remove(gearIndex);
    }

    public Gear editGear(Integer gearIndex, Gear gear) {
        return this.allGear.set(gearIndex, gear);
    }
}
