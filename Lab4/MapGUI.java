import java.awt.*;
import javax.swing.*;

/**
 * Represents the 5x5 Monopoly board layout with a 3x3 "Budget Panel" in the middle.
 */
public class MapGUI extends JPanel {
    private static MapGUI instance;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    private final PropertiesGUI[][] table = new PropertiesGUI[5][5];

    private final PropertiesGUI[] properties = new PropertiesGUI[17];

    private JPanel centerPanel;
    private JLabel[] playerLabels;

    private MapGUI() {
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        for (int i = 0; i < properties.length; i++) {
            if (i == 16) {
                properties[i] = new PropertiesGUI(-1);
            }
            properties[i] = new PropertiesGUI(i);
        }

        int[] order = {  0,  1,  2,  3,  4,
                        15, -1, -1, -1,  5,  
                        14, -1, -2, -1,  6,  
                        13, -1, -1, -1,  7,  
                        12, 11, 10,  9,  8};

        int index = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gbc.gridx = j;
                gbc.gridy = i;

                if (order[index] == -1 || order[index] == -2) {
                    index++;
                    continue;
                }

                this.add(properties[order[index]], gbc);
                table[i][j] = properties[order[index]];
                index++;
            }
        }

        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1));
        centerPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centerPanel.setPreferredSize(new Dimension(240, 240));

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        this.add(centerPanel, gbc);

        playerLabels = new JLabel[4];
        for (int m = 0; m < 4; m++) {
            JLabel label = new JLabel();
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            playerLabels[m] = label;
            centerPanel.add(label);
        }

        updateBudgetDisplay();
    }

    /**
     * Singleton accessor for MapGUI.
     */
    public static MapGUI getInstance() {
        if (instance == null) {
            instance = new MapGUI();
        }
        return instance;
    }

    /**
     * Retrieve a tile by its "tileNum" property.
     *
     * @param num The tileNum of the desired property
     * @return The matching PropertiesGUI object, or null if not found
     */
    public PropertiesGUI getTileByNum(int num) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                // table[i][j] might be null if we didn't place a tile there
                if (table[i][j] != null && table[i][j].getTileNum() == num) {
                    return table[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Repaint all property tiles on the board.
     */
    public void redraw() {
        for (PropertiesGUI propertiesGUI : properties) {
            propertiesGUI.repaint();
        }

        updateBudgetDisplay();
    }

    /**
     * Update the center panel to show each player's name and budget. 
     * Meant to be called whenever player budgets change.
     */
    public void updateBudgetDisplay() {
        if (MainGUI.players.isEmpty()) {
            return;
        }

        centerPanel.removeAll();

        for (PlayersGUI player : MainGUI.players) {
            JLabel label;

            if (player.isBankrupt()) {
                label = new JLabel(player.getName() + ": BANKRUPT");
            }
            else{
                label = new JLabel(player.getName() + ": " + player.getBudget());
            }
            
            label.setForeground(player.getColor());
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(label);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }
}
