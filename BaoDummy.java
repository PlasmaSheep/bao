import java.util.ArrayList;
import java.util.Random;
public class BaoDummy {
    int diff;
    BaoGame g;
    UserIO io;
    Random select;
    
    public BaoDummy(int difficulty, BaoGame game) {
        diff = difficulty;
        g = game;
        io = new UserIO();
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
            if(g.getPit(cur).getSeeds > 0) {
                pits.add(cur);
            }
        }
        return pits;
    }

    private Loc mostSeeds(ArrayList <Loc> locs) {
        int max = 0;
        for(int i = 0; i < pits.size(); i++) {
            if(g.getPit(locs.get(i)).getSeeds() >
                g.getPit(locs.get(max)).getSeeds()) {
                max = i;
            }
        }
        return locs.get(max);
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
                return caps.get(select.nextInt(caps.size());
            }                    
        } else {
            ArrayList <Loc> pits = getFilledPits(1);
            if(onlyNyumbaHasSeeds(1)) { //Can only move to nyumba
                ArrayList.clear(pits);
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
                int max = 0;
                for(int i = 0;
                
    }
    
    private Loc mtajiMove() {
        return new Loc(0,0); //TODO fix this
    }
    
    public void check() {
        io.printBoard(g.getBoard());
    }
    
}