import java.util.ArrayList;
public class BaoDummy {
    int diff;
    BaoGame g;
    UserIO io;
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
                
    private Loc namuaMove() {
        if(g.playerCanCapture(0)) {
            ArrayList <Loc> caps = getCaptures();
            //if
        }
        return new Loc(0,0); //TODO fix this
    }
    private Loc mtajiMove() {
        return new Loc(0,0); //TODO fix this
    }
    
    public void check() {
        io.printBoard(g.getBoard());
    }
    
}