package com.letter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Main extends JPanel implements KeyListener{
    JFrame frame;
    char[][] maze = new char[30][60];
    int size2d = 18;
    int size3d = 50;
    int sight = 0;
    boolean draw3D = false;
    Explorer hero;
    Location start;
    ArrayList<Wall> walls = new ArrayList<>();
    int charIndex = 0;
    int storyNum = 0;
    String draw = "";
    String choice = "";
    String[] story;
    String[] collection;
    Timer timer;
    public Main(){
        frame = new JFrame("A-Mazing Program");
        frame.add(this);
        frame.setSize(1150,700);
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        setBoard();
        hero = new Explorer(start,1, size2d,Color.WHITE);
        story = new String[]{"Hello?", "Anybody there?", "Where am I?", "Why is everything blue?","How do I get out of this place?","I hope my candle doesn't run out of oil."};
        collection = new String[]{"Oh no! Dead End :(", "I don't think this is the right way.", "I can feel it, we're on the right path.", "So close","Wow, we made it :)","I found some oil!"};
        choice = story[storyNum];
        timer = new Timer(100, e -> {
            String text = choice+"     [Enter]";
            if (charIndex >= text.length()) {
                ((Timer) e.getSource()).stop();
            }
            else
                draw+=text.charAt(charIndex);
            charIndex++;
            repaint();
        });
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);//giant eraser
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0,0,frame.getWidth(),frame.getHeight());
        g2.setFont(new Font("Arial",Font.ITALIC,15));

        if(!draw3D){
            g2.setColor(Color.BLUE);
            for(int r = 1; r<maze.length-1; r++)
                for (int c = 1; c < maze[r].length-1; c++)
                    if (maze[r][c] == '#')
                        g2.drawRect((c-1) *  size2d +  size2d, (r-1) *  size2d +  size2d,  size2d,  size2d);
                    else
                        g2.fillRect((c-1) *  size2d +  size2d, (r-1) *  size2d +  size2d,  size2d,  size2d);
            g2.setColor(hero.getColor());
            g2.fill(hero.getRect());
            g2.setColor(Color.GREEN);
            g2.drawString("Steps: "+hero.getSteps(),100,600);
            g2.drawString(draw,300,600);
        }
        else{
            walls = new ArrayList<>();
            boolean square = false;
            int r = hero.getLoc().getR();
            int c = hero.getLoc().getC();
            for(int i = 0; i<5; i++){
                if(square)
                    break;
                switch(hero.getDir()){
                    case 0:
                        if (maze[r - i][c - 1] == '#')
                            walls.add(getLeft(i));
                        else{
                            walls.add(getTopLeft(i));
                            walls.add(getBotLeft(i));
                            walls.add(getLeftPath(i));
                        }
                        if (maze[r - i][c + 1] == '#')
                            walls.add(getRight(i));
                        else{
                            walls.add(getTopRight(i));
                            walls.add(getBotRight(i));
                            walls.add(getRightPath(i));
                        }
                        if (maze[r - i][c] == '#') {
                            walls.add(getSquare(i));
                            square = true;
                        }
                        else {
                            walls.add(getTop(i));
                            walls.add(getBot(i));
                        }
                        break;
                    case 1:
                        if(maze[r-1][c+i]=='#')
                            walls.add(getLeft(i));
                        else{
                            walls.add(getTopLeft(i));
                            walls.add(getBotLeft(i));
                            walls.add(getLeftPath(i));
                        }
                        if(maze[r+1][c+i]=='#')
                            walls.add(getRight(i));
                        else{
                            walls.add(getTopRight(i));
                            walls.add(getBotRight(i));
                            walls.add(getRightPath(i));
                        }
                        if(maze[r][c+i]=='#') {
                            walls.add(getSquare(i));
                            square = true;
                        }
                        else{
                            walls.add(getTop(i));
                            walls.add(getBot(i));
                        }
                        break;
                    case 2:
                        if(maze[r+i][c-1]=='#')
                            walls.add(getRight(i));
                        else{
                            walls.add(getTopRight(i));
                            walls.add(getBotRight(i));
                            walls.add(getRightPath(i));
                        }
                        if(maze[r+i][c+1]=='#')
                            walls.add(getLeft(i));
                        else{
                            walls.add(getTopLeft(i));
                            walls.add(getBotLeft(i));
                            walls.add(getLeftPath(i));
                        }
                        if(maze[r+i][c]=='#') {
                            walls.add( getSquare(i));
                            square = true;
                        }
                        else{
                            walls.add(getTop(i));
                            walls.add(getBot(i));
                        }
                        break;
                    case 3:
                        if(maze[r-1][c-i]=='#')
                            walls.add(getRight(i));
                        else{
                            walls.add(getTopRight(i));
                            walls.add(getBotRight(i));
                            walls.add(getRightPath(i));
                        }
                        if(maze[r+1][c-i]=='#')
                            walls.add(getLeft(i));
                        else{
                            walls.add(getTopLeft(i));
                            walls.add(getBotLeft(i));
                            walls.add(getLeftPath(i));
                        }
                        if(maze[r][c-i]=='#') {
                            walls.add(getSquare(i));
                            square = true;
                        }
                        else{
                            walls.add(getTop(i));
                            walls.add(getBot(i));
                        }
                        break;
                }
            }
            for(Wall i : walls){
                g2.setPaint(i.getPaint());
                g2.fillPolygon(i.getCols(),i.getRows(),i.getCols().length);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
                g2.drawPolygon(i.getCols(),i.getRows(),i.getCols().length);
            }
            g2.setColor(Color.GREEN);
            g2.drawString("Steps: "+hero.getSteps(),760,100);
            g2.drawString(draw,760,300);
        }
    }
    public void setBoard(){
        File file = new File("src/MazeFile");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String text;
            int r = 0;
            while((text=br.readLine())!=null){
                for(int c = 0; c<text.length(); c++) {
                    maze[r][c] = text.charAt(c);
                    if(text.charAt(c)=='S')
                        start = new Location(r,c);
                }
                r++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {
        int r = hero.getLoc().getR();
        int c = hero.getLoc().getC();
        if(e.getKeyCode()==32)
            draw3D = !draw3D;
        else if(e.getKeyCode()==KeyEvent.VK_ENTER && !timer.isRunning()) {
            draw = "";
            charIndex = 0;
            if(storyNum<story.length-1) {
                storyNum++;
                playText(story[storyNum]);
            }
        }
        else if(!timer.isRunning() && storyNum==story.length-1 && draw=="") {
            hero.move(e.getKeyCode(), maze);
            if(r!=hero.getLoc().getR() || c!=hero.getLoc().getC()) {
                switch (maze[hero.getLoc().getR()][hero.getLoc().getC()]) {
                    case 'D':
                        playText(collection[0]);
                        break;
                    case 'W':
                        playText(collection[1]);
                        break;
                    case 'R':
                        playText(collection[2]);
                        break;
                    case 'C':
                        playText(collection[3]);
                        break;
                    case 'E':
                        playText(collection[4]);
                        break;
                    case 'O':
                        playText(collection[5]);
                        if(sight>0)
                            sight--;
                        break;
                }
                if(hero.getSteps()%25==0 && sight<5)
                    sight++;
            }
        }
        repaint();
    }
    public void keyReleased(KeyEvent e) {

    }
    public  Wall getLeft(int i){
        int[] cols = {i*size3d+size3d,i*size3d+size3d,i*size3d+100,i*size3d+100};
        int[] rows = {i*size3d,650-size3d*i,600-size3d*i,i*size3d+size3d};
        return new Wall(cols,rows,i,"Left",size3d,sight);
    }
    public  Wall getRight(int i){
        int[] cols = {i*-size3d+650,i*-size3d+650,i*-size3d+700,i*-size3d+700};
        int[] rows = {i*size3d+size3d,i*-size3d+600,i*-size3d+650,i*size3d};
        return new Wall(cols,rows,i,"Right",size3d,sight);
    }
    public Wall getLeftPath(int i){
        int[] cols = {i*size3d+size3d,i*size3d+size3d,i*size3d+100,i*size3d+100};
        int[] rows = {i*size3d+size3d,600-size3d*i,600-size3d*i,i*size3d+size3d};
        return new Wall(cols,rows,i,"Left",size3d,sight);
    }
    public Wall getRightPath(int i){
        int[] cols = {i*-size3d+650,i*-size3d+650,i*-size3d+700,i*-size3d+700};
        int[] rows = {i*size3d+size3d,i*-size3d+600,i*-size3d+600,i*size3d+size3d};
        return new Wall(cols,rows,i,"Right",size3d,sight);
    }
    public Wall getSquare(int i){
        int[] cols = {size3d+i*size3d,size3d+i*size3d,700-i*size3d,700-i*size3d};
        int[] rows = {i*size3d,650-i*size3d,650-i*size3d,i*size3d};
        return new Wall(cols,rows,i,"Mid",size3d,sight);
    }
    public Wall getBot(int i){
        int[] cols = {size3d+i*size3d,100+i*size3d,650-i*size3d,700-i*size3d};
        int[] rows = {650-i*size3d,600-i*size3d,600-i*size3d,650-i*size3d};
        return new Wall(cols,rows,i,"Bot",size3d,sight);
    }
    public Wall getTop(int i){
        int[] cols = {size3d+i*size3d,100+i*size3d,650-i*size3d,700-i*size3d};
        int[] rows = {i*size3d,size3d+i*size3d,size3d+i*size3d,i*size3d};
        return new Wall(cols,rows,i,"Top",size3d,sight);
    }
    public Wall getTopRight(int i){
        int[] cols = {650-size3d*i,700-size3d*i,700-size3d*i};
        int[] rows = {size3d+size3d*i,size3d+size3d*i,size3d*i};
        return new Wall(cols,rows,i,"Right",size3d,sight);
    }
    public Wall getTopLeft(int i){
        int[] cols = {size3d+size3d*i,size3d+size3d*i,100+size3d*i};
        int[] rows = {size3d*i,size3d+size3d*i,size3d+size3d*i};
        return new Wall(cols,rows,i,"Left",size3d,sight);
    }
    public Wall getBotRight(int i){
        int[] cols = {650-size3d*i,700-size3d*i,700-size3d*i};
        int[] rows = {600-size3d*i,650-size3d*i,600-size3d*i};
        return new Wall(cols,rows,i,"Right",size3d,sight);
    }
    public Wall getBotLeft(int i){
        int[] cols = {size3d+size3d*i,size3d+size3d*i,100+size3d*i};
        int[] rows = {600-size3d*i,650-size3d*i,600-size3d*i};
        return new Wall(cols,rows,i,"Left",size3d,sight);
    }
    public void playText(String str){
        choice = str;
        timer.restart();
    }
    public static void main(String[] args){
        Main app = new Main();
    }
}
