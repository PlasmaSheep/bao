public class Loc {
    private int r;
    private int c;
    
    public Loc(int xin, int yin) {
        if(xin > -1 && xin < 8 && yin > -1 && yin < 4) {
            r = xin;
            c = yin;
        }
    }
    
    public boolean isInner() {
        if(r == 1 || r == 2) {
            return true;
        }
        return false;
    }
    
    public Loc getLocAcross() {
        if(r == 1) {
            return new Loc(2, c);
        } else if(r == 2) {
            return new Loc(1, c);
        } else {
            return null;
        }
    }
    
    public int getRow() {
        return r;
    }
    
    public int getCol() {
        return c;
    }
    
    public Loc getNearestKichwa() {
        if(c < 4) {
            return new Loc(r, 0);
        } else {
            return new Loc(r, 7);
        }
    }

    public boolean isKichwa(int player) {
        if(player + 1 == r && (c == 0 || c == 7)) {
            return true;
        }
        return false;
    }

    public boolean isKimbi(int player) {
        if(player + 1 == r && (c == 1 || c == 6)) {
            return true;
        }
        return false;
    }
    
    public Loc getNextLoc(int dir) { //-1 is player's left kichwa, 1 is right kichwa
        int add = 1;
        int nr = r;
        int nc = c;
        if(nr < 2) { //Player 1
            if(dir == 1) { //CW
                nc += add;
                if(nc > 7 || nc < 0) {
                    add *= -1;
                    nc--;
                    nr--;
                }
                
                
        } else { //Player 2
        }
                    
            
}