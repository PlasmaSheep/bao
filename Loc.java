public class Loc {
    private int r;
    private int c;
    
    public Loc(int rin, int cin) {
        if(rin > -1 && rin < 4 && cin > -1 && cin < 8) {
            r = rin;
            c = cin;
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

    public int getKichwaSowDir() {
        //1 is from left kichwas from perspective of player 2,
        //-1 is from right kichwas
        if(c == 0) {
            return 1;
        } if(c == 7) {
            return -1;
        }
        return 0;
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
    
    public boolean isKichwa() {
        if((r == 1 || r == 2) && (c == 0 || c == 7)) {
            return true;
        }
        return false;
    }
    
    public boolean isKimbi() {
        if((r == 1 || r == 2) && (c == 1 || c == 6)) {
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

    public boolean isNyumba() {
        if((r == 1 && c == 3) || (r == 2 && c == 4)) {
            return true;
        }
        return false;
    }

    public boolean isNyumba(int p) {
        if(r == p + 1 && c == p + 3) {
            return true;
        }
        return false;
    }
    
    public int whosePit() {
        if(r == 0 || r == 1) {
            return 0;
        } else if(r == 2 || r == 3) {
            return 1;
        } else {
            return -1;
        }
    }
    
    
    /*
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
        }*/
                    
            
}