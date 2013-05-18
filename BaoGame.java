import java.util.Arrays;

public class BaoGame {
    Pit[][] board;
    int[] players;
    int turn;
   
    public BaoGame() {
        board = new Pit[4][8];
        players = new int[2]; //Player 1 on top, player 2 not
        Arrays.fill(players, 22);
        turn = 1;
        
        board[1][3].setSeeds(6);
        board[1][2].setSeeds(2);
        board[1][1].setSeeds(2);
       
        board[2][4].setSeeds(6);
        board[1][5].setSeeds(2);
        board[1][6].setSeeds(2);
    }
   
    private boolean placeSeed(int row, int col, int player) {
        if(players[player] > 0) {
            board[row][col].addSeeds(1);
            players[player]--;
            return true;
        }
        return false;
    }
    
    private boolean hasPlayerLost(int player) {
        if(!canPlayerMove(player) || isRowEmpty(player + 1)) {
            return true;
        }
        return false;
    }
    
    private boolean canPlayerMove(int player) {
        //Player is either 0 or 1
        
    }
    
    private boolean isRowEmpty(int row) {
        for(int col = 0; col <= 8; col++) {
            if(board[row][col].getSeeds() != 0) {
                return false;
            }
        }
        return true;
    }
    
    private void sow(Location loc) {
        Loc across = loc.getLocAcross();
        Pit apit = board[across.getRow()][across.getCol()];
        if(apit.getSeeds() > 0) {
            //Capture is possible, therefore capture
            int seeds = apit.getSeeds();
            apit.setSeeds(0);
        }
    }
    
    private void stockEnter(Location loc, int player) {
        //Introduce a seed from the player's stock to the board
        board[loc.getRow()][loc.getCol()].addSeeds(1);
        players[player]--;
        sow(loc);
    }
    
    public Loc getMove(int player) {
        //Should get a pit to add a seed in, then make sure it's okay: must
        //already have seeds etc.
    }
    
    public void play() {
        while(!hasPlayerLost(turn % 2)) {
            if(players[turn % 2] > 0) { //Player has seeds in hand
                Loc loc = getMove(turn % 2); //pit the player chose, should be
                //a legal move
                //Pit pit = board[loc.getRow()][loc.getCol()];
                stockEnter(loc, turn % 2);
            }
            turn++;
        }
    }
    
    private Pit getPit(Loc loc) {
        return board[loc.getRow()][loc.getCol()];
    }
}

