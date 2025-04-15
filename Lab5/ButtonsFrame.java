package Lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ButtonsFrame extends JFrame{
    private JPanel rootPanel;
    private JButton SetStart;
    private JButton SetEnd;
    private JButton AddWall;
    private JButton RemoveWall;
    private JButton FindPath;
    private JButton Reset;
    public static MazeFrame CurrentMaze;

    public ButtonsFrame(MazeFrame currentMaze){
        setTitle("Control Panel");
        setPreferredSize(new Dimension(300, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        CurrentMaze = currentMaze;

        rootPanel = new JPanel();
        rootPanel.setPreferredSize(new Dimension(300, 200));
        rootPanel.setLayout(new GridLayout(6, 1));

        SetStart = new JButton("Set Start");
        SetStart.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CurrentMaze.enableSetStart();
                CurrentMaze.disableRemoveWall();
                CurrentMaze.disableAddWall();
                CurrentMaze.disableSetEnd();
            }
        });
       
        SetEnd = new JButton("Set End");
        SetEnd.addActionListener(new ActionListener(){  
            @Override
            public void actionPerformed(ActionEvent e){
                CurrentMaze.enableSetEnd();
                CurrentMaze.disableRemoveWall();
                CurrentMaze.disableAddWall();
                CurrentMaze.disableSetStart();
            }
        });

        AddWall = new JButton("Add Wall");
        AddWall.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CurrentMaze.enableAddWall();
                CurrentMaze.disableRemoveWall();
                CurrentMaze.disableSetStart();
                CurrentMaze.disableSetEnd();
            }
        });

        RemoveWall = new JButton("Remove Wall");
        RemoveWall.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CurrentMaze.enableRemoveWall();
                CurrentMaze.disableSetStart();
                CurrentMaze.disableSetEnd();
                CurrentMaze.disableAddWall();
            }
        });

        FindPath = new JButton("Find Path");

        Reset = new JButton("Reset");
        Reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                CurrentMaze.disableRemoveWall();
                CurrentMaze.disableAddWall();
                CurrentMaze.disableSetStart();
                CurrentMaze.disableSetEnd();
                CurrentMaze.reset();  
                CurrentMaze.resetToInitial();
            } 
        });

        rootPanel.add(SetStart);
        rootPanel.add(SetEnd);
        rootPanel.add(AddWall);
        rootPanel.add(RemoveWall);
        rootPanel.add(FindPath);
        rootPanel.add(Reset);

        add(rootPanel);
        pack();
    }
}