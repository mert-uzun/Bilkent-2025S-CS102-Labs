package Lab5;

import javax.swing.*;
import java.awt.*;

public class ButtonsFrame extends JFrame{
    private JPanel rootPanel;
    private JButton SetStart;
    private JButton SetEnd;
    private JButton AddWall;
    private JButton RemoveWall;
    private JButton FindPath;
    private JButton Reset;

    public ButtonsFrame(){
        setTitle("Control Panel");
        setPreferredSize(new Dimension(300, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));

        rootPanel = new JPanel();
        rootPanel.setPreferredSize(new Dimension(300, 200));
        rootPanel.setLayout(new GridLayout(1, 5));

        SetStart = new JButton("Set Start");
        SetEnd = new JButton("Set End");
        AddWall = new JButton("Add Wall");
        RemoveWall = new JButton("Remove Wall");
        FindPath = new JButton("Find Path");
        Reset = new JButton("Reset");

        rootPanel.add(SetStart);
        rootPanel.add(SetEnd);
        rootPanel.add(AddWall);
        rootPanel.add(RemoveWall);
        rootPanel.add(FindPath);
        rootPanel.add(Reset);

        add(rootPanel);
        pack();
        setVisible(true);
    }

}