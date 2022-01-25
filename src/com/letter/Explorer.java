package com.letter;
import javafx.scene.shape.Circle;

import java.awt.*;

public class Explorer {
    private Location loc;
    private int size;
    private Color color;
    private int dir;
    private int steps;
    public Explorer(Location loc, int dir, int size, Color color){
        this.loc = loc;
        this.dir = dir;
        this.size = size;
        this.color = color;
        steps = 0;
    }
    public void move(int key, char[][] maze){
        int r = loc.getR();
        int c = loc.getC();
        switch(key){
            case 38:
                switch(dir){
                    case 0:
                        if(r>0 && maze[r-1][c]!='#')
                            loc.addR(-1);
                        break;
                    case 1:
                        if(c<maze[0].length-1 && maze[r][c+1]!='#')
                            loc.addC(1);
                        break;
                    case 2:
                        if(r<maze.length-1 && maze[r+1][c]!='#')
                            loc.addR(1);
                        break;
                    case 3:
                        if(c>0 && maze[r][c-1]!='#')
                            loc.addC(-1);
                        break;
                }
                break;
            case 37:
                dir=(dir+3)%4;
                break;
            case 39:
                dir=(dir+1)%4;
                break;
        }
        if(r != loc.getR() || c != loc.getC())
            steps++;
    }

    public Color getColor() {
        return color;
    }

    public int getDir() {
        return dir;
    }

    public int getSteps() {//?
        return steps;
    }

    public Location getLoc() {
        return loc;
    }
    public Rectangle getRect(){
        return new Rectangle((loc.getC()-1)*size+size,(loc.getR()-1)*size+size,size,size);
    }

}
