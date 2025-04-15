package Lab5;

import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame{
    public MazeCell[][] maze;

    public MazeFrame(){
        setTitle("Maze");
        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5,5));

        maze = new MazeCell[5][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j] = new MazeCell();
                add(maze[i][j]);
            }
        }

        //Set the start and end cells by default
        resetToInitial();
        
        pack();
        setVisible(true);
    }

    public void reset(){
        resetWalls();
        resetStart();
        resetEnd();
    }

    public void resetWalls(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].resetWall();
                maze[i][j].repaint();
            }
        }
    }

    public void resetStart(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].resetStart();
                maze[i][j].repaint();
            }
        }
    }

    public void resetEnd(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].resetEnd();
                maze[i][j].repaint();
            }
        }
    }

    public void enableSetStart(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].enableSetStart();
            }
        }
    }

    public void enableSetEnd(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].enableSetEnd();
            }
        }
    }

    public void enableAddWall(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].enableAddWall();
            }
        }
    }

    public void enableRemoveWall(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].enableRemoveWall();
            }
        }
    }

    public void disableRemoveWall(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].disableRemoveWall();
            }
        }
    }

    public void disableAddWall(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].disableAddWall();
            }
        }
    }

    public void disableSetStart(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].disableSetStart();
            }
        }
    }

    public void disableSetEnd(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].disableSetEnd();
            }
        }
    }

    public void resetToInitial(){
        maze[0][0].setCell(true, false, false);
        maze[4][4].setCell(false, true, false);
    }
}
