package UI;
import TicTacToe.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayerVsPlayer extends JFrame implements ActionListener {
  private JButton[][] grid;
  private Label turnOf;

  private JButton redo;
  private JButton undo;
  private JButton reset;

  private Color RED = new Color(244, 67, 54);
  private Color BLUE = new Color(0, 140, 226);

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
        grid[i][j].setBackground(Color.WHITE);

        grid[i][j].setPreferredSize(dimension);
        grid[i][j].setMaximumSize(dimension);
        grid[i][j].setMinimumSize(dimension);
        
        grid[i][j].setFont(new Font("SansSerif", Font.BOLD, 64));
        grid[i][j].setFocusPainted(false);
        
        grid[i][j].addActionListener(this);
        ButtonGrid.add(grid[i][j]);
      }
    }

    gbc.gridx = 0;
    gbc.gridy = 0;
    MainPanel.add(ButtonGrid, gbc);


    JPanel InfoPanel = new JPanel(new GridLayout(2, 1));
    turnOf = new Label("Turn of X");
    turnOf.setFont(new Font("SansSerif", Font.BOLD, 52));
    turnOf.setAlignment(Label.CENTER);
    InfoPanel.add(turnOf);

    JPanel ButtonPanel = new JPanel(new GridLayout(1, 3));
    undo = StyledButton("Undo");
    redo = StyledButton("Redo");
    reset = StyledButton("Reset");
    ButtonPanel.add(undo);
    ButtonPanel.add(redo);
    ButtonPanel.add(reset);
    InfoPanel.add(ButtonPanel);

    gbc.gridx = 1;
    MainPanel.add(InfoPanel, gbc);

    
    add(MainPanel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    for (int i = 0; i < game.board.size; i++) {
      for (int  j = 0; j < game.board.size; j++) {
        if (grid[i][j] == e.getSource()) {
          game.move(i, j);
          Update();
          break;
        }
      }
    }
  }

  private void Update() {
    for (int i = 0; i < game.board.size; i++) {
      for (int j = 0; j < game.board.size; j++) {
        String text = " ";
        boolean isWinningCell = game.board.winnerCell[i][j];

        if (game.board.grid[i][j] == 1) {
          grid[i][j].setForeground(RED);
          text = "X";
          
          if (isWinningCell) {
            grid[i][j].setBackground(RED);
            grid[i][j].setForeground(Color.WHITE);
          }
        }
        else if (game.board.grid[i][j] == 2) {
          grid[i][j].setForeground(BLUE);
          text = "O";

          if (isWinningCell) {
            grid[i][j].setBackground(BLUE);
            grid[i][j].setForeground(Color.WHITE);
          }
        }
        
        grid[i][j].setText(text);
      }
    }

    turnOf.setText(game.board.xTurn ? "Turn of X" : "Turn of O");
  }

  private JButton StyledButton(String text) {
    JButton button = new JButton(text);

    button.setFont(new Font("SansSerif", Font.BOLD, 24));
    button.setFocusPainted(false); // Disable Square on focus
    
    button.setBackground(new Color(171, 191, 224));
    button.setForeground(Color.WHITE);
    
    return button;
  }
}
