package MineSweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game implements ActionListener {
    private int _rows;
    private int _columns;
    private Box[][] _boxes;
    private JButton[][] _buttons;
    private JLabel _pointsLabel;
    private int _maxBombs;
    private int _counter;
    private int _points;
    private ImageIcon clearIcon = new ImageIcon("./src/Images/ClearBox.png");
    private ImageIcon safeIcon = new ImageIcon("./src/Images/SafeBox.png");
    private ImageIcon bombIcon = new ImageIcon("./src/Images/BombBox.png");

    public Game(int rows, int columns){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JPanel pointsPanel = new JPanel();


        _rows = rows;
        _columns = columns;
        _boxes = new Box[_rows][_columns];
        _buttons = new JButton[_rows][_columns];
        _pointsLabel = new JLabel();
        _maxBombs = _rows;
        _counter = 0;
        _points = 0;
        _pointsLabel.setText("Points: " + _points);

        pointsPanel.add(_pointsLabel);

        for (int i = 0; i <_rows; i++){
            for (int j = 0; j < _columns; j++){
                _buttons[i][j] = new JButton();
                _buttons[i][j].setName("btn" + _counter);
                //System.out.println(_buttons[i][j].getName());
                _buttons[i][j].setIcon(clearIcon);
                _buttons[i][j].setBorder(BorderFactory.createEmptyBorder());
                panel.add(_buttons[i][j]);

                _boxes[i][j] = new Box(true,_counter);

                _buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton aux = (JButton)e.getSource();

                        String aux2 = aux.getName().replaceAll("[^0-9]","");

                        int x = Integer.parseInt(aux2) / _rows;
                        int y = Integer.parseInt(aux2) % _rows;

                        boolean type = _boxes[x][y].get_type();
                        if(type){
                            if(_boxes[x][y].is_covered()) {
                                _boxes[x][y].Uncover();
                                _buttons[x][y].setIcon(safeIcon);
                                _points++;
                                _pointsLabel.setText("Points: " + _points);
                                if (_points == (_rows * _columns) - _maxBombs) {
                                    JOptionPane.showMessageDialog(null, "You Won! Score: " + _points);
                                }
                            }
                        }
                        else{
                            _buttons[x][y].setIcon(bombIcon);
                            UncoverAll();
                            JOptionPane.showMessageDialog(null, "You lost! Score: " + _points);
                            new MainMenu();
                            frame.dispose();
                        }
                    }
                });
                _counter++;
            }
        }
        panel.validate();
        SpawnBombs();
        WriteMatrix();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(_rows,_columns));

        frame.add(panel, BorderLayout.CENTER);
        frame.add(pointsPanel, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Mine Sweeper");
        frame.setSize(_rows * 100 , _columns * 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Game(5,5);
    }

    public void SpawnBombs(){
        int bombsToSpawn = _maxBombs;
        Random rnd = new Random();
        int x, y;
        while(bombsToSpawn > 0){
            do{
                x = rnd.nextInt(_rows);
                y = rnd.nextInt(_columns);
            }while(!_boxes[x][y].get_type());
            _boxes[x][y].set_type(false);
            bombsToSpawn--;
        }
    }

    public void UncoverAll(){
        for(int i = 0; i < _rows; i++){
            for (int j = 0; j < _columns; j++){

                if(_boxes[i][j].get_type()){
                    _buttons[i][j].setIcon(safeIcon);
                }
                else{
                        _buttons[i][j].setIcon(bombIcon);
                }
            }
        }
    }

    public void WriteMatrix(){
        for(int i = 0; i < _rows; i++){
            for (int j = 0; j < _columns; j++){
                if(!_boxes[i][j].get_type())
                    System.out.print("0");
                else
                    System.out.print("1");
            }
            System.out.println();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
