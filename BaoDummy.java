import java.util.ArrayList;
import java.util.Random;
public class BaoDummy {
    int diff;
    BaoGame g;
    UserIO io;
    Random select;
    Loc nyumba;
    int player;
    
    public BaoDummy(int difficulty, BaoGame game) {
        diff = difficulty;
        g = game;
        io = new UserIO();
        player = 0;
        nyumba = g.getNyumbaLoc(player);
    }
    
    public Loc getNextMove() {
        if(g.getPlayerSeeds(0) > 0) {
            return namuaMove();
        } else {
            return mtajiMove();
        }
    }
    
    private ArrayList <Loc> getCaptures() {
        ArrayList <Loc> caps = new ArrayList <Loc> ();
        for(int c = 0; c < 8; c++) {
            if(g.pitCanCapture(new Loc(1, c))) {
                caps.add(new Loc(1, c));
            }
        }
        return caps;
    }

    private ArrayList <Loc> getFilledPits(int r) {
        ArrayList <Loc> pits = new ArrayList <Loc> ();
        for(int c = 0; c < 8; c++) {
            Loc cur = new Loc(r, c);
            if(g.getPit(cur).getSeeds() > 0) {
                pits.add(cur);
            }
        }
        return pits;
    }

    private Loc mostSeeds(ArrayList <Loc> locs) {
        int max = 0;
        for(int i = 0; i < locs.size(); i++) {
            if(g.getPit(locs.get(i)).getSeeds() >
                g.getPit(locs.get(max)).getSeeds()) {
                max = i;
            }
        }
        return locs.get(max);
    }

    public int getTaxDir() {
        if(diff == 1) {
            double rand = select.nextDouble();
            if(rand > .5) {
                return -1;
            } else {
                return 1;
            }
        } else { //Move to the side that will result in resowing
            int c = nyumba.getCol();
            if(g.getPit(new Loc(g.getInnerRow(player), c - 2)).getSeeds() >
                g.getPit(new Loc(g.getInnerRow(player), c + 2)).getSeeds()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public boolean getSafariChoice() {
        return select.nextBoolean();
    }


    private int[] sowFrom(Loc start, int seeds) {
        int cap = 0;
        int resow = 0;
        Loc next = new Loc(start.getRow(), start.getCol());
        int dir = start.getKichwaSowDir();

        while(seeds > 0) { //Iterate until seeds run out
            seeds--;
            int c = next.getCol();
            int r = next.getRow();
            if(seeds > 0) {
                if(c + dir > 7 || c + dir < 0) {
                    //Reached the end of row, move in opposite
                    //direction on other row
                    dir *= -1;
                    if(r == 1 || r == 3) {
                        r--;
                    } else {
                        r++;
                    }
                }
                next = new Loc(r, c + dir);
            }

        }

        if(g.getPit(next.getLocAcross()).getSeeds() > 0 &&
            g.getPit(next).getSeeds() > 1 && g.isCapturingMove()) {
            cap = g.getPit(next.getLocAcross()).getSeeds();
        }

        if(g.getPit(next).getSeeds() > 0) {
            resow = g.getPit(next).getSeeds();
        }
        return new int[] {cap, resow};
    }

    public int getSowKichwa(int seeds) {
        if(diff == 1) {
            double rand = select.nextDouble();
            if(rand > .5) {
                return 1;
            } else {
                return -1;
            }
        } else {
            int[] l = sowFrom(new Loc(1, 0), seeds);
            int[] r = sowFrom(new Loc(1, 7), seeds);
            if(l[1] > r[1]) {
                return 1;
            } else if(l[1] < r[1]) {
                return -1;
            } else if(r[0] > l[0]) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    
    private Loc namuaMove() {
        if(g.playerCanCapture(0)) {
            ArrayList <Loc> caps = getCaptures();
            if(diff == 2) { //Capture the pit with the most stones
                int max = 0;
                for(int i = 0; i < caps.size(); i++) {
                    if(g.getPit(caps.get(i).getLocAcross()).getSeeds() >
                        g.getPit(caps.get(max).getLocAcross()).getSeeds()) {
                        max = i;
                    }
                }
                return caps.get(max);
            } else {
                return caps.get(select.nextInt(caps.size()));
            }                    
        } else {
            ArrayList <Loc> pits = getFilledPits(1);
            if(g.onlyNyumbaHasSeeds(1)) { //Can only move to nyumba
                pits.clear();
                pits.add(g.getNyumbaLoc(0));
            } else { //Otherwise can't move to nyumba
                for(int i = 0; i < pits.size(); i++) {
                    if(pits.get(i).isNyumba(0)) {
                        pits.remove(i);
                        break;
                    }
                }
            }
                
            if(diff == 2) { //Sow seed in the hole with the most seeds
                return mostSeeds(pits);
            } else {
                //Sow in random hole
                return pits.get(select.nextInt(pits.size()));
            }
        }
    }
    
    private Loc mtajiMove() {
        return new Loc(0,0); //TODO fix this
    }
    
    public void check() {
        io.printBoard(g.getBoard());
    }
    
}