package Lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;    
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MazeCell extends JPanel {
    private int x;
    private int y;
    private boolean isWall;
    private boolean isStart;
    private boolean isEnd;
    private BufferedImage mouse;
    private BufferedImage cheese;
    private BufferedImage wall;
    
    public MazeCell(int x, int y, boolean isWall, boolean isStart, boolean isEnd){
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.isStart = isStart;
        this.isEnd = isEnd;

        try{
            mouse = ImageIO.read(new File("mouse.png"));
            cheese = ImageIO.read(new File("cheese.png"));
            wall = ImageIO.read(new File("wall.png"));
        }catch(IOException e){
            System.out.println(e);
        }
    }

    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(isWall){
            g.drawImage(wall, 0, 0, null); 
        }
        else if(isStart){
            g.drawImage(mouse, 0, 0, null);
        }
        else if(isEnd){
            g.drawImage(cheese, 0, 0, null);
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isWall(){
        return isWall;
    }

    //Setters
    public void setWall(boolean isWall){
        this.isWall = isWall;
        repaint();
    }

    public void setStart(boolean isStart){
        this.isStart = isStart;
        repaint();
    }

    public void setEnd(boolean isEnd){
        this.isEnd = isEnd;
        repaint();
    }
    
}
