package UI;
import TicTacToe.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerVsPlayer extends JFrame implements ActionListener {
  private JButton[][] grid;
  private Game game = new Game();
  
  public PlayerVsPlayer(int size) {
    setTitle("Tic Tac Toe");
    setSize(800, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);


    // Main Panel
    JPanel MainPanel = new JPanel(new GridBagLayout());
    MainPanel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(0, 20, 0, 20); // Vertical Padding


    JPanel ButtonGrid = new JPanel(new GridLayout(size, size));
    grid = new JButton[size][size];
    
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        grid[i][j] = new JButton(" ");
        Dimension dimension = new Dimension(100, 100);

        grid[i][j].setPreferredSize(dimension);
        grid[i][j].setMaximumSize(dimension);
        grid[i][j].setMinimumSize(dimension);
        
        grid[i][j].setFont(new Font("SansSerif", Font.BOLD, 48));
        grid[i][j].setFocusPainted(false);
        
        grid[i][j].addActionListener(this);
        ButtonGrid.add(grid[i][j]);
      }
    }

    gbc.gridx = 0;
    gbc.gridy = 0;
    MainPanel.add(ButtonGrid, gbc);


    JPanel InfoPanel = new JPanel(new GridLayout());
    InfoPanel.add(new Label("X's Turn"));

    gbc.gridx = 1;
    MainPanel.add(InfoPanel, gbc);

    
    add(MainPanel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    //
  }
}
