package MineSweeper;

public class Box {
    private Boolean _type;    //1 for safe, 0 for bomb
    private boolean _covered;
    private int _pos;
    private int _nearBombs;
    public Box(Boolean type, int pos){
        _type = type;
        _covered = true;                   //constructor
        _pos = pos;
        _nearBombs = 0;
    }

    public boolean is_covered() {
        return _covered;                                //returns if the box is covered
    }

    public void Uncover(){
        if(_covered){
            _covered = false;       //if box is covered, uncovers
        }
    }

    public int get_nearBombs() {                            //properties
        return _nearBombs;
    }

    public void set_nearBombs(int _nearBombs) {        //properties
        this._nearBombs = _nearBombs;
    }

    public boolean Safe(){
        return _type;
    }                           //properties

    public Boolean get_type() {
        return _type;
    }                               //properties

    public void set_type(Boolean type) {
        _type = type;
    }                   //properties

    public int get_pos() {
        return _pos;
    }                           //properties

    public void set_pos(int _pos) {
        this._pos = _pos;
    }               //properties

}
