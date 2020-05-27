package MineSweeper;

public class Box {
    private Boolean _type;    //1 for safe, 0 for bomb
    private boolean _covered;
    private int _pos;
    public Box(Boolean type, int pos){
        _type = type;
        _covered = true;
        _pos = pos;
    }

    public boolean is_covered() {
        return _covered;
    }

    public void Uncover(){
        if(_covered){
            _covered = false;
        }
    }

    public boolean Safe(){
        return _type;
    }

    public Boolean get_type() {
        return _type;
    }

    public void set_type(Boolean type) {
        _type = type;
    }

    public int get_pos() {
        return _pos;
    }

    public void set_pos(int _pos) {
        this._pos = _pos;
    }

}
