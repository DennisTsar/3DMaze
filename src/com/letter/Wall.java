package com.letter;

import java.awt.*;

public class Wall {
    private int[] rows;
    private int[] cols;
    private int n;
    private String type;
    private int size;
    private int sight;
    public Wall(int[] cols, int[] rows, int n, String type, int size, int sight){
        this.rows = rows;
        this.cols = cols;
        this.n = n;
        this.type = type;
        this.size = size;
        this.sight = sight;
    }

    public GradientPaint getPaint() {
        int num1 = 255-size*n-50*sight;
        if(num1<0)
            num1 = 0;
        Color start = new Color(num1);
        int num2 = 255-size*n-50-50*sight;
        if(num2<0)
            num2 = 0;
        Color end = new Color(num2);
        GradientPaint paint = null;
        if(type.equals("Left"))
            paint = new GradientPaint(cols[0],rows[0],start,cols[2],rows[0],end);
        else if(type.equals("Right"))
            paint = new GradientPaint(cols[2],rows[0],start,cols[0],rows[0],end);
        else if(type.equals("Bot"))
            paint = new GradientPaint(cols[0],rows[0],start,cols[0],rows[1],end);
        else if(type.equals("Top"))
            paint = new GradientPaint(cols[0],rows[0],start,cols[0],rows[1],end);
        else if(type.equals("Mid"))
            paint = new GradientPaint(cols[0],rows[0],start,cols[0],rows[0],end);
        return paint;
    }

    public int[] getCols() {
        return cols;
    }

    public int[] getRows() {
        return rows;
    }
}
