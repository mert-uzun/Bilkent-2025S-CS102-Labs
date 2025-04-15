import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class MainGUI {
    private static boolean gameOver = false;
    public static ArrayList<PlayersGUI> players = new ArrayList<>();
    public static int currentTurnIndex = -1;
    public static int playedTurnCount = 1;
    
    private static JFrame welcomeFrame; 

    private static JTextField redPlayerName;
    private static JTextField bluePlayerName;
    private static JTextField yellowPlayerName;
    private static JTextField greenPlayerName;

    private static JFrame gameFrame;
    public static MapGUI mapPanel;
    public static LogArea logArea;
    public static JScrollPane scrollLogArea;
    private static JPanel actionPanel;

    private static JButton rollButton;
    private static JButton buyButton;
    private static JButton sellButton;
    private static JButton buildButton;
    private static JButton endTurnButton;
    

    public static void main(String[] args) {
        //Construct the welcome frame
        welcomeFrame = new JFrame();
        welcomeFrame.setTitle("Monopoly Game");
        welcomeFrame.setLayout(new GridLayout(5, 2));
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setSize(400, 400);

        //Initialize the log in for red player
        JLabel redPlayerLabel = new JLabel("Red Player: ");
        redPlayerLabel.setForeground(Color.RED);
        welcomeFrame.add(redPlayerLabel);

        redPlayerName = new JTextField("");
        welcomeFrame.add(redPlayerName);

        //Initialize the log in for blue player
        JLabel bluePlayerLabel = new JLabel("Blue Player: ");
        bluePlayerLabel.setForeground(new Color(0, 0, 255));
        welcomeFrame.add(bluePlayerLabel);

        bluePlayerName = new JTextField("");
        welcomeFrame.add(bluePlayerName);

        //Initialize the log in for yellow player
        JLabel yellowPlayerLabel = new JLabel("Yellow Player: ");
        yellowPlayerLabel.setForeground(Color.YELLOW);
        welcomeFrame.add(yellowPlayerLabel);

        yellowPlayerName = new JTextField("");
        welcomeFrame.add(yellowPlayerName);

        //Initialize the log in for green player
        JLabel greenPlayerLabel = new JLabel("Green Player: ");
        greenPlayerLabel.setForeground(Color.GREEN);
        welcomeFrame.add(greenPlayerLabel);

        greenPlayerName = new JTextField("");
        welcomeFrame.add(greenPlayerName);

        //Initialize the start button
        ActionListener startTheGame = new StartTheGame();
        JButton startButton = new JButton("Start!");
        startButton.addActionListener(startTheGame);
        welcomeFrame.add(startButton);

        welcomeFrame.setVisible(true);
    }

    public static class StartTheGame implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent event){
            JTextField[] nameFields = {redPlayerName, bluePlayerName, yellowPlayerName, greenPlayerName};
            Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

            //Create players
            for (int i = 0; i < 4; i++) {
                String enteredName = nameFields[i].getText().trim();
                if (enteredName.isEmpty()) {
                    players.add(new PlayersGUI(false, "Bot" + (i + 1), colors[i]));
                } 
                else {
                    players.add(new PlayersGUI(true, enteredName, colors[i]));
                }
            }


            //Set the playing order by randomizing the players
            for (int i = 0; i < 100; i++) {
                int randomIndex1 = (int) (Math.random() * players.size());
                int randomIndex2 = (int) (Math.random() * players.size());
                PlayersGUI temp = players.get(randomIndex1);
                players.set(randomIndex1, players.get(randomIndex2));
                players.set(randomIndex2, temp);
            }

            //Initialize the game frame
            gameFrame = new JFrame("Monopoly Game");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setSize(1200, 400);
            gameFrame.setLayout(new GridLayout(1, 3));

            mapPanel = MapGUI.getInstance();
            gameFrame.add(mapPanel);

            logArea = new LogArea();
            logArea.setEditable(false);

            DefaultCaret caret = (DefaultCaret) logArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            scrollLogArea = new JScrollPane(logArea);
            scrollLogArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            gameFrame.add(scrollLogArea);

            logArea.append("The game is starting!");
            logArea.append("Playing order is determined: ");

            for (PlayersGUI player : players) {
                logArea.append(player.getName());
            }

            actionPanel = new JPanel();
            actionPanel.setLayout(new GridLayout(5, 1));

            rollButton = new JButton("Roll");
            Roll rollAction = new Roll();
            rollButton.addActionListener(rollAction);
            actionPanel.add(rollButton);

            buyButton = new JButton("Buy");
            Buy buyAction = new Buy();
            buyButton.addActionListener(buyAction);
            actionPanel.add(buyButton);

            sellButton = new JButton("Sell");
            Sell sellAction = new Sell();
            sellButton.addActionListener(sellAction);
            actionPanel.add(sellButton);

            buildButton = new JButton("Build");
            Build buildAction = new Build();
            buildButton.addActionListener(buildAction);
            actionPanel.add(buildButton);

            endTurnButton = new JButton("End Turn");
            EndTurn endTurnAction = new EndTurn();
            endTurnButton.addActionListener(endTurnAction);
            actionPanel.add(endTurnButton);

            gameFrame.add(actionPanel);

            //Change the frame
            welcomeFrame.setVisible(false);

            //Initialize the players into the starting tile.
            for(PlayersGUI player : players){
                mapPanel.getTileByNum(0).addPeopleOnThisTile(player);
            }

            mapPanel.updateBudgetDisplay();
            mapPanel.redraw();
            
            //Coanstruct the game frame
            gameFrame.setVisible(true);
            
            nextTurn();
        }
    }

    public static void nextTurn(){
        if(gameOver){
            return;
        }

        int previousTurnIndex = currentTurnIndex;
        do {
            currentTurnIndex = (currentTurnIndex + 1) % players.size();
        } while (players.get(currentTurnIndex).isBankrupt());
    
        if (previousTurnIndex >= currentTurnIndex) {
            playedTurnCount++;
            if (playedTurnCount >= 100) {
                endViaTurnLimit();
            }
            logArea.setText("");
            logArea.append("Turn " + playedTurnCount + "!");
            
            Timer delayTimer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((Timer)e.getSource()).stop();
                    continueNextTurn();
                }
            });
            delayTimer.setRepeats(false);
            delayTimer.start();
            return;  
        }
        
        continueNextTurn();
    }
    
    private static void continueNextTurn(){
        if(gameOver){
            return;
        } 

        rollButton.setEnabled(false);
        buyButton.setEnabled(false);
        sellButton.setEnabled(false);
        buildButton.setEnabled(false);
        endTurnButton.setEnabled(false);
        
        PlayersGUI currentPlayer = players.get(currentTurnIndex);
        logArea.append("\nNow it's " + currentPlayer.getName() + "'s turn!");
        
        if (!currentPlayer.isPlayer) {
            Timer botTimer = new Timer(50, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    ((Timer)e.getSource()).stop();
                    currentPlayer.takeTurn();
                    nextTurn();
                }
            });
            botTimer.setRepeats(false);
            botTimer.start();
        } else {
            rollButton.setEnabled(true);
        }
        
        long activePlayers = players.stream().filter(p -> !p.isBankrupt()).count();
        if (activePlayers == 1) {
            gameOver = true;
            JOptionPane.showMessageDialog(null, "Game Over! " + currentPlayer.getName() + " wins!");
            System.exit(0);
        }
    
        endViaTurnLimit();
    }
    

    public static class Roll implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            PlayersGUI currentPlayer = players.get(MainGUI.currentTurnIndex);

            currentPlayer.takeTurn();

            rollButton.setEnabled(false);
            buyButton.setEnabled(currentPlayer.getBudget() >= mapPanel.getTileByNum(currentPlayer.getCurrentTile()).getPropertyPrice() 
                                    && !mapPanel.getTileByNum(currentPlayer.getCurrentTile()).isOwned()
                                    && mapPanel.getTileByNum(currentPlayer.getCurrentTile()).getPropertyPrice() != 0);
            sellButton.setEnabled(!currentPlayer.properties.isEmpty());
            buildButton.setEnabled(!currentPlayer.properties.isEmpty());
            endTurnButton.setEnabled(true);
        }
    }

    public static class Buy implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            mapPanel.getTileByNum(players.get(currentTurnIndex).getCurrentTile()).own(players.get(currentTurnIndex));

            rollButton.setEnabled(false);
            buyButton.setEnabled(false);
            sellButton.setEnabled(false);
            buildButton.setEnabled(false);
            endTurnButton.setEnabled(true);
        }
    }

    public static class Sell implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JFrame sellFrame = new JFrame("Sell Property");
            sellFrame.setSize(400, 400);
            sellFrame.setLayout(new GridLayout(players.get(currentTurnIndex).properties.size() + 1, 1));

            for(int i = 0; i < players.get(currentTurnIndex).properties.size(); i++){
                final int index = i;

                JButton insideSellButton = new JButton("Sell " + players.get(currentTurnIndex).properties.get(i).getStringRepresentationOfTileNum() + 
                                                        " for " + players.get(currentTurnIndex).properties.get(i).getPropertyPrice());
                                                    
                insideSellButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){ 
                        JOptionPane.showMessageDialog(null, players.get(currentTurnIndex).properties.get(index).getStringRepresentationOfTileNum() + " sold for "
                                                                            + players.get(currentTurnIndex).properties.get(index).getPropertyPrice());
                        players.get(currentTurnIndex).properties.get(index).sellProperty();
                        sellFrame.setVisible(false);

                        rollButton.setEnabled(false);
                        buyButton.setEnabled(false);
                        sellButton.setEnabled(false);
                        buildButton.setEnabled(false);
                        endTurnButton.setEnabled(true);
                    }
                });
                sellFrame.add(insideSellButton);
            }

            JButton returnButton = new JButton("Return");
            returnButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    sellFrame.setVisible(false);
                }
            });

            sellFrame.add(returnButton);
            sellFrame.setVisible(true);
        }
    }

    public static class Build implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JFrame buildFrame = new JFrame("Build House");
            buildFrame.setSize(400, 400);
            buildFrame.setLayout(new GridLayout(players.get(currentTurnIndex).properties.size() + 1, 1));

            for(int i = 0; i < players.get(currentTurnIndex).properties.size(); i++){
                final int index = i;

                if (players.get(currentTurnIndex).properties.get(i).getHousePrice() <= players.get(currentTurnIndex).getBudget()
                        && players.get(currentTurnIndex).properties.get(i).getBuildedHouses() != 4) {
                    JButton insideBuildButton = new JButton("Build House on " + players.get(currentTurnIndex).properties.get(i).getStringRepresentationOfTileNum() + 
                    " for " + players.get(currentTurnIndex).properties.get(i).getHousePrice());
                        
                    insideBuildButton.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){ 
                            players.get(currentTurnIndex).properties.get(index).buildHouses();
                            JOptionPane.showMessageDialog(null, "House built on " + players.get(currentTurnIndex).properties.get(index).getStringRepresentationOfTileNum()
                                                                                    + " for " + players.get(currentTurnIndex).properties.get(index).getHousePrice());
                            buildFrame.setVisible(false);

                            rollButton.setEnabled(false);
                            buyButton.setEnabled(false);
                            sellButton.setEnabled(false);
                            buildButton.setEnabled(false);
                            endTurnButton.setEnabled(true);
                        }
                    });
                    buildFrame.add(insideBuildButton);
                }    
            }

            JButton returnButton = new JButton("Return");
            returnButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    buildFrame.setVisible(false);
                }
            });

            buildFrame.add(returnButton);
            buildFrame.setVisible(true);
        }
    }

    public static class EndTurn implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            nextTurn();
        }
    }

    public static void endViaTurnLimit(){
        if (playedTurnCount == 100) {
            gameOver = true;
            String result = "Game Over! The game has ended in a draw between: ";
            for (PlayersGUI player : players) {
                if (!player.isBankrupt()) {
                    result += player.getName() + " ";
                }
            }
            JOptionPane.showMessageDialog(null, result);
            System.exit(0); // End the game
        }
    }

}