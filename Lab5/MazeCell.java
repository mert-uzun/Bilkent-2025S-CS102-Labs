package Lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;    
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MazeCell extends JPanel {
    private final int INTERVAL = 80;

    private final MouseListener SET_START = new MouseListener(){
        @Override
        public void mouseClicked(MouseEvent e){
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(e.getX() > j * INTERVAL && e.getX() < (j + 1) * INTERVAL && e.getY() > i * INTERVAL && e.getY() < (i + 1) * INTERVAL){
                        ButtonsFrame.CurrentMaze.resetStart();
                        ButtonsFrame.CurrentMaze.resetToNewPath();
                        setCell(true, false, false);
                        ButtonsFrame.CurrentMaze.startCellCoords[0] = j;
                        ButtonsFrame.CurrentMaze.startCellCoords[1] = i;
                        ButtonsFrame.CurrentMaze.lengthOfShortestPathPossible = Math.abs(ButtonsFrame.CurrentMaze.endCellCoords[0] - ButtonsFrame.CurrentMaze.startCellCoords[0]) + Math.abs(ButtonsFrame.CurrentMaze.endCellCoords[1] - ButtonsFrame.CurrentMaze.startCellCoords[1]);
                        ButtonsFrame.CurrentMaze.disableSetStart();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e){}

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseExited(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e){}
    };

    private final MouseListener SET_END = new MouseListener(){
        @Override
        public void mouseClicked(MouseEvent e){
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(e.getX() > j * INTERVAL && e.getX() < (j + 1) * INTERVAL && e.getY() > i * INTERVAL && e.getY() < (i + 1) * INTERVAL){
                        ButtonsFrame.CurrentMaze.resetEnd();
                        ButtonsFrame.CurrentMaze.resetToNewPath();
                        setCell(false, true, false);
                        ButtonsFrame.CurrentMaze.endCellCoords[0] = j;
                        ButtonsFrame.CurrentMaze.endCellCoords[1] = i;
                        ButtonsFrame.CurrentMaze.lengthOfShortestPathPossible = Math.abs(ButtonsFrame.CurrentMaze.endCellCoords[0] - ButtonsFrame.CurrentMaze.startCellCoords[0]) + Math.abs(ButtonsFrame.CurrentMaze.endCellCoords[1] - ButtonsFrame.CurrentMaze.startCellCoords[1]);
                        ButtonsFrame.CurrentMaze.disableSetEnd();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e){}

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseExited(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e){}
    };

    private final MouseListener ADD_WALL = new MouseListener(){
        @Override
        public void mouseClicked(MouseEvent e){
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(e.getX() > j * INTERVAL && e.getX() < (j + 1) * INTERVAL && e.getY() > i * INTERVAL && e.getY() < (i + 1) * INTERVAL){
                        ButtonsFrame.CurrentMaze.resetToNewPath();
                        addWall();
                        ButtonsFrame.CurrentMaze.disableAddWall();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e){}

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseExited(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e){}
    };

    private final MouseListener REMOVE_WALL = new MouseListener(){
        @Override
        public void mouseClicked(MouseEvent e){
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(e.getX() > j * INTERVAL && e.getX() < (j + 1) * INTERVAL && e.getY() > i * INTERVAL && e.getY() < (i + 1) * INTERVAL){
                        ButtonsFrame.CurrentMaze.resetToNewPath();
                        setCell(false, false, false);
                        ButtonsFrame.CurrentMaze.disableRemoveWall();
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e){}

        @Override
        public void mouseReleased(MouseEvent e){}

        @Override
        public void mouseExited(MouseEvent e){}

        @Override
        public void mouseEntered(MouseEvent e){}
    };

    private int xCoord;
    private int yCoord;
    private int shortestPathLength;
    private int[] visitedFrom;
    private boolean isWall;
    private boolean isStart;
    private boolean isEnd;
    private boolean isPartOfShortestPath;
    private BufferedImage mouse;        
    private BufferedImage cheese;
    private BufferedImage wall;

    public MazeCell(int xCoord, int yCoord){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        try{
            mouse = ImageIO.read(new File("C:\\Users\\Mert\\Desktop\\2025S\\CS102\\Bilkent-2025S-CS102-Labs\\Lab5\\images\\mouse.png"));
            cheese = ImageIO.read(new File("C:\\Users\\Mert\\Desktop\\2025S\\CS102\\Bilkent-2025S-CS102-Labs\\Lab5\\images\\cheese.png"));
            wall = ImageIO.read(new File("C:\\Users\\Mert\\Desktop\\2025S\\CS102\\Bilkent-2025S-CS102-Labs\\Lab5\\images\\wall.png"));
        }catch(IOException e){
            System.out.println(e);
        }

        visitedFrom = new int[2];
        visitedFrom[0] = -1;
        visitedFrom[1] = -1;
        isPartOfShortestPath = false;
        shortestPathLength = Integer.MAX_VALUE;

        setCell(false, false, false);

        setPreferredSize(new Dimension(80, 80));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
        setVisible(true);
    }

    public void enableSetStart(){
        addMouseListener(SET_START);
    }

    public void enableSetEnd(){
        addMouseListener(SET_END);
    }

    public void enableAddWall(){
        addMouseListener(ADD_WALL);
    }

    public void enableRemoveWall(){
        addMouseListener(REMOVE_WALL);
    }

    public void disableSetStart(){
        removeMouseListener(SET_START);
    }

    public void disableSetEnd(){
        removeMouseListener(SET_END);
    }

    public void disableAddWall(){
        removeMouseListener(ADD_WALL);
    }

    public void disableRemoveWall(){
        removeMouseListener(REMOVE_WALL);
    }

    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(isPartOfShortestPath){
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        if(isStart){
            g.drawImage(mouse, 0, 0, 80, 80, null);
        }
        else if(isEnd){
            g.drawImage(cheese, 0, 0, 80, 80, null);
        }
        else if(isWall){
            g.drawImage(wall, 0, 0, 80, 80, null);
        }
    }

    //Getters
    public boolean isStart(){
        return isStart;
    }

    public boolean isEnd(){
        return isEnd;
    }

    public boolean isWall(){
        return isWall;
    }

    public int[] getVisitedFrom(){
        return visitedFrom;
    }

    public int getXCoord(){
        return xCoord;
    }

    public int getYCoord(){
        return yCoord;
    }

    //Setters
    public void addWall(){
        if(!isWall){
            this.isWall = true;
            this.isStart = false;
            this.isEnd = false;
            repaint();
        }
    }

    public void setCell(boolean isStart, boolean isEnd, boolean isWall){
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.isWall = isWall;
        repaint();
    }

    public void resetStart(){
        isStart = false;
        repaint();
    }

    public void resetEnd(){
        isEnd = false;
        repaint();
    }

    public void resetWall(){
        isWall = false;
        repaint();
    }

    public int getShortestPathLength(){
        return shortestPathLength;
    }

    public void setShortestPathLengthAndVisitedFrom(int shortestPathLength, int prevCellX, int prevCellY){
        this.shortestPathLength = shortestPathLength;
        setVisitedFrom(prevCellX, prevCellY);
    }

    public void setVisitedFrom(int x, int y){
        this.visitedFrom[0] = x;
        this.visitedFrom[1] = y;
    }

    public void setIsPartOfShortestPath(boolean isPartOfShortestPath){
        this.isPartOfShortestPath = isPartOfShortestPath;
        repaint();
    }
}
