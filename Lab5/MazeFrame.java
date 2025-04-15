package Lab5;

import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame{
    private JPanel rootPanel;
    private MazeCell[][] maze;

    public MazeFrame(){
        setTitle("Maze");
        setPreferredSize(new Dimension(400, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));

        rootPanel = new JPanel();
        rootPanel.setPreferredSize(new Dimension(400, 400));
        rootPanel.setLayout(new GridLayout(5, 5));

        maze = new MazeCell[5][5];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                maze[i][j] = new MazeCell(i, j, false, false, false);
                maze[i][j].setPreferredSize(new Dimension(80, 80));
                maze[i][j].setBackground(Color.WHITE);
                rootPanel.add(maze[i][j]);
            }
        }

        //Set the start and end cells by default
        maze[0][0].setStart(true);
        maze[4][4].setEnd(true);

        add(rootPanel);
    }
    
}
