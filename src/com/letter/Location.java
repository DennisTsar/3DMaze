package com.letter;

public class Location {
    private int c;
    private int r;

    public Location(int r, int c){
        this.r = r;
        this.c = c;
    }
    public int getC() {
        return c;
    }

    public int getR() {
        return r;
    }

    public void addC(int a) {
        c+=a;
    }

    public void addR(int a) {
        r+=a;
    }
}
