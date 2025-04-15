package Lab5;

import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame{
    private final int MAX_TRIES = 100;
    public MazeCell[][] maze;
    private int[] startCellCoords = {0, 0};
    private int[] endCellCoords = {4, 4};
    private int currentCellX;
    private int currentCellY;
    private int currentLengthOfPath = 0;
    private int shortestPathNumberOfTries = 0;
    private boolean isShortestPathFound = false;
    private int lengthOfShortestPathPossible = endCellCoords[0] - startCellCoords[0] + endCellCoords[1] - startCellCoords[1];

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
        resetToNewPath();
        int xDiff = endCellCoords[0] - startCellCoords[0];
        int yDiff = endCellCoords[1] - startCellCoords[1];

        if(xDiff >= 0 && yDiff >= 0){
            traverseDownRight();
            return;
        }
        else if(xDiff >= 0 && yDiff <= 0){
            traverseUpRight();
            return;
        }
        else if(xDiff < 0 && yDiff >= 0){
            traverseDownLeft();
            return;
        }
        else {
            traverseUpLeft();
            return;
        }
    }

    private void traverseUpLeft(){
        if(isShortestPathFound ||shortestPathNumberOfTries >= MAX_TRIES){
            paintTheShortestPath();
            return;
        }

        this.currentLengthOfPath++;
        if (isValidCell(currentCellX, currentCellY - 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY--;
        }
        else if (isValidCell(currentCellX - 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX--;
        }
        else if (isValidCell(currentCellX, currentCellY + 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY++;
        }
        else if (isValidCell(currentCellX + 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX++;
        }
        else {
            resetToStartTile();
            traverseUpRight();
            return;
        }

        if (currentCellX == endCellCoords[0] && currentCellY == endCellCoords[1]) {
            if(maze[currentCellX][currentCellY].getShortestPathLength() == lengthOfShortestPathPossible){
                isShortestPathFound = true;
            }

            resetToStartTile();
            traverseUpRight();
            return;
        }
        else {
            traverseUpLeft();
        }
    }

    private void traverseUpRight(){
        if(isShortestPathFound || shortestPathNumberOfTries >= MAX_TRIES){
            paintTheShortestPath();
            return;
        }

        this.currentLengthOfPath++;
        if (isValidCell(currentCellX, currentCellY - 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY--;
        }
        else if (isValidCell(currentCellX + 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX++;
        }
        else if (isValidCell(currentCellX, currentCellY + 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY++;
        }
        else if (isValidCell(currentCellX - 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX--;
        }
        else {
            resetToStartTile();
            traverseDownRight();
            return;
        }

        if (currentCellX == endCellCoords[0] && currentCellY == endCellCoords[1]) {
            if(maze[currentCellX][currentCellY].getShortestPathLength() == lengthOfShortestPathPossible){
                isShortestPathFound = true;
            }

            resetToStartTile();
            traverseDownRight();
            return;
        }
        else {
            traverseUpRight();
        }
    }

    private void traverseDownRight(){
        if(isShortestPathFound || shortestPathNumberOfTries >= MAX_TRIES){
            paintTheShortestPath();
            return;
        }

        this.currentLengthOfPath++;
        if (isValidCell(currentCellX, currentCellY + 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY++;
        }
        else if (isValidCell(currentCellX + 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX++;
        }   
        else if (isValidCell(currentCellX, currentCellY - 1, currentCellX, currentCellY, currentLengthOfPath)) {    
            this.currentCellY--;
        }
        else if (isValidCell(currentCellX - 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX--;
        }   
        else {
            resetToStartTile();
            traverseDownLeft();
            return;
        }

        if (currentCellX == endCellCoords[0] && currentCellY == endCellCoords[1]) {
            if(maze[currentCellX][currentCellY].getShortestPathLength() == lengthOfShortestPathPossible){
                isShortestPathFound = true;
            }

            resetToStartTile();
            traverseDownLeft();
            return;
        }
        else {
            traverseDownRight();
        }
    }

    private void traverseDownLeft(){
        if(isShortestPathFound || shortestPathNumberOfTries >= MAX_TRIES){
            paintTheShortestPath();
            return;
        }

        this.currentLengthOfPath++;
        if (isValidCell(currentCellX, currentCellY + 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY++;
        }
        else if (isValidCell(currentCellX - 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX--;
        }
        else if (isValidCell(currentCellX, currentCellY - 1, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellY--;
        }
        else if (isValidCell(currentCellX + 1, currentCellY, currentCellX, currentCellY, currentLengthOfPath)) {
            this.currentCellX++;
        }
        else {
            resetToStartTile();
            traverseUpLeft();
            return;
        }

        if (currentCellX == endCellCoords[0] && currentCellY == endCellCoords[1]) {
            if(maze[currentCellX][currentCellY].getShortestPathLength() == lengthOfShortestPathPossible){
                isShortestPathFound = true;
            }

            resetToStartTile();
            traverseUpLeft();
            return;
        }
        else {
            traverseDownLeft();
        }
    }

    public void reset(){
        resetWalls();
        resetStart();
        resetEnd();
        resetShortestPaths();
        resetToNewPath();
        resetToInitial();
    }

    public void resetToNewPath(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setShortestPathLengthAndVisitedFrom(Integer.MAX_VALUE, -1, -1);
                maze[i][j].repaint();
            }
        }
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

    public void resetShortestPaths(){
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j].setIsPartOfShortestPath(false);
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
        startCellCoords[0] = 0;
        startCellCoords[1] = 0;
        endCellCoords[0] = 4;
        endCellCoords[1] = 4;
        isShortestPathFound = false;
        shortestPathNumberOfTries = 0;
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

        if (maze[endCellCoords[0]][endCellCoords[1]].getShortestPathLength() == 0) {
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
