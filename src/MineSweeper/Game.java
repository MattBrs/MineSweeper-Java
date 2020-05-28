package MineSweeper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game implements ActionListener {
    private int _rows;                                           //COMMENTATO IN ITALIANO PER CAPIRE MEGLIO E PER CARENZA DI TEMPO
    private int _columns;                                        //COMMENTATO IN ITALIANO PER CAPIRE MEGLIO E PER CARENZA DI TEMPO
    private Box[][] _boxes;                                      //COMMENTATO IN ITALIANO PER CAPIRE MEGLIO E PER CARENZA DI TEMPO
    private JButton[][] _buttons;
    private JLabel _pointsLabel;
    private int _maxBombs;                                  //variabili utilizzate e oggetti della gui di java
    private int _counter;
    private int _points;
    private ImageIcon clearIcon = new ImageIcon("./src/Images/ClearBox.png");
    private ImageIcon safeIcon = new ImageIcon("./src/Images/SafeBox.png");           //immagini prese da disco
    private ImageIcon bombIcon = new ImageIcon("./src/Images/BombBox.png");

    public Game(int rows, int columns){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();            //creazione gui java
        JPanel pointsPanel = new JPanel();


        _rows = rows;
        _columns = columns;
        _boxes = new Box[_rows][_columns];
        _buttons = new JButton[_rows][_columns];                //inizializzazione variabili
        _pointsLabel = new JLabel();
        _maxBombs = _rows;
        _counter = 0;
        _points = 0;
        _pointsLabel.setText("Points: " + _points);

        pointsPanel.add(_pointsLabel);          //creazione gui java

        for (int i = 0; i <_rows; i++){
            for (int j = 0; j < _columns; j++){
                _buttons[i][j] = new JButton();
                _buttons[i][j].setName("btn" + _counter);                           //creo bottoni dinamicamente e gli aggiungo alla gui
                _buttons[i][j].setIcon(clearIcon);                                  //cambio il bottono in un immagine per simulare la casella
                _buttons[i][j].setBorder(BorderFactory.createEmptyBorder());
                panel.add(_buttons[i][j]);

                _boxes[i][j] = new Box(true,_counter);

                _buttons[i][j].addActionListener(new ActionListener() {              //quando clicco il "bottone" (che e' la casella) entra qua dentro
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton aux = (JButton)e.getSource();                       //ottengo il source dell'evento cosi' da sapere che bottone l'ha generato

                        String aux2 = aux.getName().replaceAll("[^0-9]","");                //estrapolo il numero del bottone dal nome (es button12 => 12)

                        int x = Integer.parseInt(aux2) / _rows;                     //ottengo le posizioni della matrice dal numero del bottone con questa formula
                        int y = Integer.parseInt(aux2) % _rows;

                        boolean type = _boxes[x][y].get_type();                     //variabile che tiene il tipo della casella. 1 se e' safe e 0 se e' bomba. get_type e' un metodo di boxes
                        if(type){                                                   //se il bottone non e' una bomba
                            if(_boxes[x][y].is_covered()) {                         //controlla se non e' gia' stato cliccato
                                _boxes[x][y].Uncover();
                                _buttons[x][y].setIcon(safeIcon);                                   //setta l'icona a verde (safe)
                                UncoverSafe(x, y);                                                  //scopre tutte le altre caselle che sono safe. ATTENZIONE: FUNZIONA MA NON SEMPRE CON PRECISIONE. SISTEMARE
                                _pointsLabel.setText("Points: " + _points);                         //cambia il testo della label per mostrare il punteggio
                                if (CountBoxes() == (_rows * _columns) - _maxBombs) {               //con il metodo count boxes conto le caselle che sono state scoperte. se sono pari al numero di caselle - n bombe scatta la vincita e si chiude la partita
                                    JOptionPane.showMessageDialog(null, "You Won! Score: " + _points);
                                    new MainMenu();                                                 //apre la finestra main menu
                                    frame.dispose();                                                //chiude quella corrente
                                }
                            }
                        }
                        else{                                                                       //qua ci entra se la casella e' di tipo bomba
                            _buttons[x][y].setIcon(bombIcon);                                       //setta l'icona come bomba
                            UncoverAll();                                                           //richiama il metodo per scoprire tutte le caselle
                            JOptionPane.showMessageDialog(null, "You lost! Score: " + _points);   //viene fuori il messaggio che hai perso
                            new MainMenu();     //apre la finestra main menu
                            frame.dispose();    //chiude quella corrente
                        }
                    }
                });
                _counter++;
            }
        }
        panel.validate();               //creazione gui di java
        SpawnBombs();                   //richiamo il metodo per spaware le bombe.
        WriteMatrix();                  //metodo che uso per stampare in console la matrice

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));          //creazione gui di java
        panel.setLayout(new GridLayout(_rows,_columns));

        frame.add(panel, BorderLayout.CENTER);                          //aggiunge il pannello con bottoni, ecc al frame principale ,ecc
        frame.add(pointsPanel, BorderLayout.NORTH);                     //creazione gui di java
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           //creazione gui di java
        frame.setTitle("Mine Sweeper");                                 //creazione gui di java
        frame.setSize(_rows * 100 , _columns * 100);        //creazione gui di java
        frame.setLocationRelativeTo(null);                              //creazione gui di java
        frame.setVisible(true);                                         //creazione gui di java
    }

    public static void main(String[] args) {
        new Game(5,5);
    }

    public int CountBoxes(){
        int count = 0;
        for (int i = 0; i < _rows; i++){
            for(int j = 0; j < _columns; j++){                  //conta le caselle che sono scoperte per verificare la vincita
                if(!_boxes[i][j].is_covered())
                    count++;
            }
        }
        return count;
    }

    public void SpawnBombs(){
        int bombsToSpawn = _maxBombs;
        Random rnd = new Random();
        int x, y;
        while(bombsToSpawn > 0){
            do{                                             //metodo per spawnare le bombe a caso.
                x = rnd.nextInt(_rows);                     //dopo aver generato le caselle
                y = rnd.nextInt(_columns);                  //trovo una posizione random e sostituisco
            }while(!_boxes[x][y].get_type());               //il tipo della casella da normale a bomba
            _boxes[x][y].set_type(false);
            bombsToSpawn--;
        }
    }

    public void UncoverAll(){                                                 //metodo per scoprire tutte le caselle in un colpo solo
        for(int i = 0; i < _rows; i++){
            for (int j = 0; j < _columns; j++){
                if(_boxes[i][j].is_covered()) {                               //skippo quelle coperte
                    if (_boxes[i][j].get_type()) {
                        _buttons[i][j].setIcon(safeIcon);                     //scorro la matrice. Se la casella e' una bomba metto la casella
                        //rossa altrimenti verde
                    } else {
                        _buttons[i][j].setIcon(bombIcon);
                    }
                }
            }
        }
    }

    private void UncoverSafe(int i, int j)                              //Rielaborato di quel che ha fatto il prof. Rivedere in futuro per personalizzazione e miglioramento.
    {                                                                   //(possibilmente quest'estate

        CalculateNearBombs(i,j);

        if ((i < 0) || (j < 0) || (i > _rows - 1) || (j > _columns - 1))  {;}          //controlla se gli indici escono dalla matrice

        else if (!_boxes[i][j].get_type() )     {;}                                    //se e' una bomba skippa


        else                                                                        //altrimenti continua e fa altri controlli
        {
            _buttons[i][j].setText("" + _boxes[i][j].get_nearBombs());
            _points++;
            _buttons[i][j].setIcon(safeIcon);                                 //imposto l'icona a verda
            _boxes[i][j].Uncover();                                           //imposto la casella come scoperta per evitare casini negli if

            if ( (j + 1 < _columns) && (_boxes[i][j + 1].is_covered()))   //right
                UncoverSafe(i, j + 1);

            if ( (j - 1 > 0) && (_boxes[i][j-1].is_covered()))     //left
                UncoverSafe(i, j - 1);

            if ((i + 1 < _rows) && (_boxes[i+1][j].is_covered()))    //down                questi controlliservono per vedere se la casella a fienco e' scoperta e se gli indici escono dalla matrice
                UncoverSafe(i+1, j );

            if ((i - 1 > 0) && (_boxes[i-1][j].is_covered()))       //up
                UncoverSafe(i-1 , j);

            if ((i - 1 > 0) && (j + 1 < _columns) && (_boxes[i-1][j+1].is_covered()))       //up-right
                UncoverSafe(i - 1, j+1 );

            if ((i + 1 < _rows) && (j + 1 < _columns) && (_boxes[i+1][j+1].is_covered()))  //down-right
                UncoverSafe(i + 1, j + 1);

            if ((i - 1 > 0) && (j - 1 >0) && (_boxes[i - 1][j - 1].is_covered()))       //up-left
                UncoverSafe(i - 1, j - 1);

            if ((i + 1 < _rows) && (j - 1 >0) && (_boxes[i+1][j-1].is_covered()))  //down-left
                UncoverSafe(i + 1, j - 1);
        }


    }

    public void CalculateNearBombs(int i, int j){                   //DA SISTEMARE => POCO OTTIMIZZATO
        int nearBombs = 0;
        int posx, posy;
        for (int c = -1; c <= 1; c++){
            for (int r = -1; r<=1; r++){
                posx = i + c;
                posy = j + r;
                if(posx < _columns && (posy >= 0 && posy < _rows)){
                    if(!_boxes[posx][posy].get_type()){
                        nearBombs++;
                    }
                }
            }
        }
        
        _boxes[i][j].set_nearBombs(nearBombs);

    }

    public void WriteMatrix(){
        for(int i = 0; i < _rows; i++){
            for (int j = 0; j < _columns; j++){
                if(!_boxes[i][j].get_type())                   //metodo di comodo che uso per stampare la matrice del gioco in console
                    System.out.print("0");
                else
                    System.out.print("1");
            }
            System.out.println();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {            //NON UTILE PER QUESTO PROGRAMMA MA OBBLIGATORIO PER IMPLEMENTAZIONE "ActionListener"

    }
}
