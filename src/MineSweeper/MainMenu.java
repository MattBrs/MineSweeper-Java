package MineSweeper;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu implements ActionListener {
                                           //create frame
    private JButton _easyBtn;
    private JButton _mediumBtn;                                                 //create frame objects
    private JButton _hardBtn;
    private JButton _closeBtn;
    private JLabel _infoLabel;

    public MainMenu(){
        JPanel menuPanel = new JPanel();                                    //create panel
        JFrame menu = new JFrame();

        _easyBtn = new JButton("Easy");
        _mediumBtn = new JButton("Medium");                                 //create frame objects
        _hardBtn = new JButton("Hard");
        _closeBtn = new JButton("Close window");
        _infoLabel = new JLabel("Select a difficulty");
        //JSplitPane spacer = new JSplitPane();


        _easyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Action to start a new easy game and close this window
                new Game(5,5);
                menu.dispose();
            }
        });

        _mediumBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Action to start a new medium game and close this window
                new Game(10,10);
                menu.dispose();
            }
        });

        _hardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Action to start a new hard game and close this window
                new Game(15,15);
                menu.dispose();
            }
        });

        _closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                    //Action to close current window with button press
                System.exit(0);
            }
        });

        menuPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));    //set dimensions

        menuPanel.setLayout(new GridLayout(0,1));                                          //set layout
        menuPanel.add(_infoLabel);
        menuPanel.add(_easyBtn);
        menuPanel.add(_mediumBtn);
        menuPanel.add(_hardBtn);
        //menuPanel.add(spacer);
        menuPanel.add(_closeBtn);


        menu.add(menuPanel, BorderLayout.CENTER);                                                    //set position
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setTitle("Main menu");
        menu.pack();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }


    public static void main(String[] args) {
        new MainMenu();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //not useful in this project.
    }
}



