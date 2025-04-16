package Lab5;

import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame{
    private final int MAX_TRIES = 10000;
    public MazeCell[][] maze;
    public int[] startCellCoords = {0, 0};
    public int[] endCellCoords = {4, 4};
    private int currentCellX;
    private int currentCellY;
    private int currentLengthOfPath = 0;
    private int shortestPathNumberOfTries = 0;
    private boolean isShortestPathFound = false;
    public int lengthOfShortestPathPossible = Math.abs(endCellCoords[0] - startCellCoords[0]) + Math.abs(endCellCoords[1] - startCellCoords[1]);

    public MazeFrame(){
        setTitle("Maze");
        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5,5));

        maze = new MazeCell[5][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j] = new MazeCell(j, i);
                add(maze[i][j]);
            }
        }

        resetToInitial();
        
        pack();
        setVisible(true);
    } 

    public void findShortestPath(){
        boolean foundStart = false;
        boolean foundEnd = false;
        
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setIsPartOfShortestPath(false);
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, 0, 0);
                
                if(maze[i][j].isStart()){
                    startCellCoords[0] = i;
                    startCellCoords[1] = j;
                    foundStart = true;
                }
                else if(maze[i][j].isEnd()){
                    endCellCoords[0] = i;
                    endCellCoords[1] = j;
                    foundEnd = true;
                }
                
                maze[i][j].repaint();
            }
        }
        
        if(!foundStart || !foundEnd){
            JOptionPane.showMessageDialog(null, "Please set both start and end points before finding a path.");
            return;
        }
        
        lengthOfShortestPathPossible = Math.abs(endCellCoords[0] - startCellCoords[0]) + 
                         Math.abs(endCellCoords[1] - startCellCoords[1]);
        
        isShortestPathFound = false;
        shortestPathNumberOfTries = 0;
        currentLengthOfPath = 0;
        currentCellX = startCellCoords[0];
        currentCellY = startCellCoords[1];
    
        maze[startCellCoords[0]][startCellCoords[1]].setShortestPathLengthAndVisitedFrom(0, startCellCoords[0], startCellCoords[1]);
        
        dfsSearch(startCellCoords[0], startCellCoords[1], 0);
        
        paintTheShortestPath();
    }
    
    private boolean dfsSearch(int x, int y, int pathLength) {
        if(shortestPathNumberOfTries >= MAX_TRIES || isShortestPathFound) {
            return true;
        }
        
        if(x == endCellCoords[0] && y == endCellCoords[1]) {
            isShortestPathFound = true;
            return true;
        }
        
        shortestPathNumberOfTries++;
    
        int xDiff = endCellCoords[0] - x;
        int yDiff = endCellCoords[1] - y;
        
        int[][] directions = new int[4][2];
        
        if(Math.abs(xDiff) > Math.abs(yDiff)) {
            if(xDiff > 0) {
                directions[0] = new int[]{1, 0}; 
                if(yDiff > 0) {
                    directions[1] = new int[]{0, 1};  
                    directions[2] = new int[]{0, -1}; 
                } 
                else {
                    directions[1] = new int[]{0, -1}; 
                    directions[2] = new int[]{0, 1};  
                }
                directions[3] = new int[]{-1, 0}; 
            } 
            else {
                directions[0] = new int[]{-1, 0}; 
                if(yDiff > 0) {
                    directions[1] = new int[]{0, 1};  
                    directions[2] = new int[]{0, -1}; 
                } 
                else {
                    directions[1] = new int[]{0, -1}; 
                    directions[2] = new int[]{0, 1};  
                }
                directions[3] = new int[]{1, 0};  
            }
        } 
        else {
            if(yDiff > 0) {
                directions[0] = new int[]{0, 1};  
                if(xDiff > 0) {
                    directions[1] = new int[]{1, 0};  
                    directions[2] = new int[]{-1, 0}; 
                } 
                else {
                    directions[1] = new int[]{-1, 0}; 
                    directions[2] = new int[]{1, 0};  
                }
                directions[3] = new int[]{0, -1}; 
            } 
            else {
                directions[0] = new int[]{0, -1}; 
                if(xDiff > 0) {
                    directions[1] = new int[]{1, 0};  
                    directions[2] = new int[]{-1, 0}; 
                } 
                else {
                    directions[1] = new int[]{-1, 0}; 
                    directions[2] = new int[]{1, 0};  
                }
                directions[3] = new int[]{0, 1};  
            }
        }
        
        for(int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            
            if(newX >= 0 && newX < 5 && newY >= 0 && newY < 5 && !maze[newX][newY].isWall() && maze[newX][newY].getShortestPathLength() > pathLength + 1) {
                
                maze[newX][newY].setShortestPathLengthAndVisitedFrom(pathLength + 1, x, y);
                
                if(dfsSearch(newX, newY, pathLength + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void reset(){
        resetWalls();
        resetStart();
        resetEnd();
        resetToNewPath();
        resetShortestPathLengths();
        
        maze[0][0].setCell(true, false, false);
        maze[4][4].setCell(false, true, false);
        startCellCoords[0] = 0;
        startCellCoords[1] = 0;
        endCellCoords[0] = 4;
        endCellCoords[1] = 4;
        lengthOfShortestPathPossible = Math.abs(endCellCoords[0] - startCellCoords[0]) + 
                         Math.abs(endCellCoords[1] - startCellCoords[1]);
    }

    public void resetToNewPath(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setIsPartOfShortestPath(false);
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, 0, 0);
                maze[i][j].repaint();
            }
        }

        isShortestPathFound = false;
        shortestPathNumberOfTries = 0;
        currentLengthOfPath = 0;
        currentCellX = startCellCoords[0];
        currentCellY = startCellCoords[1];
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

    public void resetShortestPathLengths(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, 0, 0);
                maze[i][j].repaint();
            }
        }
    }

    public void resetToInitial(){
        maze[0][0].setCell(true, false, false);
        maze[4][4].setCell(false, true, false);
        startCellCoords[0] = 0;
        startCellCoords[1] = 0;
        endCellCoords[0] = 4;
        endCellCoords[1] = 4;
        lengthOfShortestPathPossible = Math.abs(endCellCoords[0] - startCellCoords[0]) + 
                         Math.abs(endCellCoords[1] - startCellCoords[1]);
        isShortestPathFound = false;
        shortestPathNumberOfTries = 0;
        currentLengthOfPath = 0;
    }

    public void resetToStartTile(){
        currentCellX = startCellCoords[0];
        currentCellY = startCellCoords[1];
        currentLengthOfPath = 0;
        shortestPathNumberOfTries++;
        System.err.println(shortestPathNumberOfTries);
    }

    private void paintTheShortestPath(){
        int[] visitedFromCoords = maze[endCellCoords[0]][endCellCoords[1]].getVisitedFrom();
        int lastPaintedCellX = visitedFromCoords[0];
        int lastPaintedCellY = visitedFromCoords[1];

        if (maze[endCellCoords[0]][endCellCoords[1]].getShortestPathLength() == Integer.MAX_VALUE) {
            JOptionPane.showMessageDialog(null, "No path found");
        }
        else {
            while (lastPaintedCellX != startCellCoords[0] || lastPaintedCellY != startCellCoords[1]) {
                maze[lastPaintedCellX][lastPaintedCellY].setIsPartOfShortestPath(true);
                visitedFromCoords = maze[lastPaintedCellX][lastPaintedCellY].getVisitedFrom();
                lastPaintedCellX = visitedFromCoords[0];
                lastPaintedCellY= visitedFromCoords[1];
            }
        }
    }
}
