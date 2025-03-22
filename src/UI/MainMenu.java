package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
  private JButton PlayOnlineButton;
  private JButton PlayerVsBotButton;
  private JButton PlayerVsPlayerButton;
  private JButton ExitButton;

  private Font titleFont = new Font("SansSerif", Font.BOLD, 64);
  private Font buttonFont = new Font("SansSerif", Font.BOLD, 32);

  public MainMenu() {
    setTitle("Tic Tac Toe");
    setSize(800, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);


    // Main Screen
    JPanel MainPanel = new JPanel(new GridBagLayout());
    MainPanel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0; // Selected First Column
    gbc.gridy = 0; // Selected First Row


    // Title
    JLabel Title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
    Title.setFont(titleFont);
    gbc.insets = new Insets(10, 0, 20, 0); // Vertical Padding
    MainPanel.add(Title, gbc);


    // Buttons
    JPanel ButtonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    ButtonPanel.setOpaque(false);

    PlayOnlineButton     = StyledButton("Play Online");
    PlayerVsBotButton    = StyledButton("Player Vs Bot");
    PlayerVsPlayerButton = StyledButton("Player Vs Player");
    ExitButton           = StyledButton("Exit");

    PlayOnlineButton.addActionListener(this);
    PlayerVsBotButton.addActionListener(this);
    PlayerVsPlayerButton.addActionListener(this);
    ExitButton.addActionListener(this);

    ButtonPanel.add(PlayOnlineButton);
    ButtonPanel.add(PlayerVsBotButton);
    ButtonPanel.add(PlayerVsPlayerButton);
    ButtonPanel.add(ExitButton);

    gbc.gridy = 1; // Move to the next row of the Main Panel
    MainPanel.add(ButtonPanel, gbc);
    

    add(MainPanel);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    Object eventSource = e.getSource();
    
    if (eventSource == PlayOnlineButton) {
      PlayOnline po = new PlayOnline(3);
      setVisible(false);
      po.setVisible(true);
    }
    else if (eventSource == PlayerVsBotButton) {
      PlayerVsBot pvb = new PlayerVsBot(3);
      setVisible(false);
      pvb.setVisible(true);
    }
    else if (eventSource == PlayerVsPlayerButton) {
      PlayerVsPlayer pvp = new PlayerVsPlayer(3);
      setVisible(false);
      pvp.setVisible(true);
    }
  }

  private JButton StyledButton(String text) {
    JButton button = new JButton(text);

    button.setFont(buttonFont);
    button.setFocusPainted(false); // Disable Square on focus
    
    button.setBackground(new Color(50, 150, 250));
    button.setForeground(Color.WHITE);
    
    return button;
  }
}
