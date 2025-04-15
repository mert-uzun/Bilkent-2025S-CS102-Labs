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

        //Set the start and end cells by default
        resetToInitial();
        
        pack();
        setVisible(true);
    }

    private boolean isValidCell(int currentCellX, int currentCellY, int prevCellX, int prevCellY, int currentLengthOfPath){
        if(currentCellX < 0 || currentCellX >= 5 || currentCellY < 0 || currentCellY >= 5){
            return false;
        }
        else if(maze[currentCellX][currentCellY].isWall()){
            return false;
        }
        else if(maze[currentCellX][currentCellY].getShortestPathLength() != Integer.MAX_VALUE){
            return false;
        }
        else {
            if(maze[currentCellX][currentCellY].getShortestPathLength() <= currentLengthOfPath){
                return false;
            }
            else {
                maze[currentCellX][currentCellY].setShortestPathLengthAndVisitedFrom(currentLengthOfPath, prevCellX, prevCellY);
                return true;
            }
        }
    }    

    public void findShortestPath(){
        // Completely reset path data
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setIsPartOfShortestPath(false);
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, -1, -1);
                maze[i][j].repaint();
            }
        }
        
        isShortestPathFound = false;
        shortestPathNumberOfTries = 0;
        currentLengthOfPath = 0;
        currentCellX = startCellCoords[0];
        currentCellY = startCellCoords[1];
        
        // Set the start cell's path length to 0
        maze[startCellCoords[0]][startCellCoords[1]].setShortestPathLengthAndVisitedFrom(0, -1, -1);
        
        // Use each strategy in sequence - this gives better coverage
        System.out.println("Starting search with " + MAX_TRIES + " max tries");
        dfsSearch(startCellCoords[0], startCellCoords[1], 0);
        
        // After all search attempts, paint the path (if found)
        paintTheShortestPath();
    }
    
    // Improved recursive search that tries movements in different orders
    private boolean dfsSearch(int x, int y, int pathLength) {
        // Too many tries or already found path, exit
        if(shortestPathNumberOfTries >= MAX_TRIES || isShortestPathFound) {
            return true;
        }
        
        // Check if we reached the end
        if(x == endCellCoords[0] && y == endCellCoords[1]) {
            System.out.println("Found end! Path length: " + pathLength);
            isShortestPathFound = true;
            return true;
        }
        
        shortestPathNumberOfTries++;
        if(shortestPathNumberOfTries % 1000 == 0) {
            System.out.println("Tries: " + shortestPathNumberOfTries);
        }
        
        // Instead of fixed direction order, determine direction based on where the end is
        int xDiff = endCellCoords[0] - x;
        int yDiff = endCellCoords[1] - y;
        
        // Create an array of directions to try in priority order
        int[][] directions = new int[4][2];
        
        // Prioritize directions leading toward the end point
        if(Math.abs(xDiff) > Math.abs(yDiff)) {
            // End is more horizontal than vertical distance
            if(xDiff > 0) {
                // End is to the right
                directions[0] = new int[]{1, 0};  // right
                if(yDiff > 0) {
                    directions[1] = new int[]{0, 1};  // down
                    directions[2] = new int[]{0, -1}; // up
                } else {
                    directions[1] = new int[]{0, -1}; // up
                    directions[2] = new int[]{0, 1};  // down
                }
                directions[3] = new int[]{-1, 0}; // left
            } else {
                // End is to the left
                directions[0] = new int[]{-1, 0}; // left
                if(yDiff > 0) {
                    directions[1] = new int[]{0, 1};  // down
                    directions[2] = new int[]{0, -1}; // up
                } else {
                    directions[1] = new int[]{0, -1}; // up
                    directions[2] = new int[]{0, 1};  // down
                }
                directions[3] = new int[]{1, 0};  // right
            }
        } else {
            // End is more vertical than horizontal distance
            if(yDiff > 0) {
                // End is below
                directions[0] = new int[]{0, 1};  // down
                if(xDiff > 0) {
                    directions[1] = new int[]{1, 0};  // right
                    directions[2] = new int[]{-1, 0}; // left
                } else {
                    directions[1] = new int[]{-1, 0}; // left
                    directions[2] = new int[]{1, 0};  // right
                }
                directions[3] = new int[]{0, -1}; // up
            } else {
                // End is above
                directions[0] = new int[]{0, -1}; // up
                if(xDiff > 0) {
                    directions[1] = new int[]{1, 0};  // right
                    directions[2] = new int[]{-1, 0}; // left
                } else {
                    directions[1] = new int[]{-1, 0}; // left
                    directions[2] = new int[]{1, 0};  // right
                }
                directions[3] = new int[]{0, 1};  // down
            }
        }
        
        // Try each direction according to priority
        for(int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            
            // Check if valid move
            if(newX >= 0 && newX < 5 && newY >= 0 && newY < 5 && 
               !maze[newX][newY].isWall() && 
               maze[newX][newY].getShortestPathLength() > pathLength + 1) {
                
                // Mark this cell
                maze[newX][newY].setShortestPathLengthAndVisitedFrom(pathLength + 1, x, y);
                
                // Recursive call
                if(dfsSearch(newX, newY, pathLength + 1)) {
                    return true;
                }
            }
        }
        
        // If we get here, this path didn't work - backtrack
        return false;
    }

    public void reset(){
        resetWalls();
        resetStart();
        resetEnd();
        resetToNewPath();
        resetShortestPathLengths();
        resetToInitial();
    }

    public void resetToNewPath(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setIsPartOfShortestPath(false);
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, -1, -1);
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
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, -1, -1);
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
