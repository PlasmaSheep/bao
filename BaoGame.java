import java.util.Arrays;

public class BaoGame {
    Pit[][] board;
    int[] players;
    int turn;
   
    public BaoGame() {
        board = new Pit[4][8];
        players = new int[2]; //Player 0 on top, player 1 not
        Arrays.fill(players, 22);
        turn = 1;
        
        board[1][3].setSeeds(6);
        board[1][2].setSeeds(2);
        board[1][1].setSeeds(2);
       
        board[2][4].setSeeds(6);
        board[1][5].setSeeds(2);
        board[1][6].setSeeds(2);
    }
    
    private int getTopRow(int player) {
        if(player == 0) {
            return 0;
        } else if(player == 1) {
            return 2;
        }
        return -1;
    }

    private int getInnerRow(int player) {
        if(player == 0) {
            return 1;
        } else if(player == 1) {
            return 2;
        }
        return -1;
    }

    private Pit getPit(Loc loc) {
        return board[loc.getRow()][loc.getCol()];
    }
    
    private boolean playerHasNonSingletons(int player) {
        for(int r = getTopRow(player); r <= getTopRow(player) + 1; r+) {
            for(int c = 0; c < 9; c++) {
                if(board[r][c].getSeeds() > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean pitCanCapture(Loc loc) {
        //Can only capture if the pit at loc is in an interior row and the
        //pit across has seeds in it
        if(loc.isInner()) {
            if(getPit(loc.getLocAcross()).getSeeds() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean playerCanCapture(int player) {
        int r = player.getInnerRow();
        for(int c = 0; c < 8; c++) {
            if(pitCanCapture(board[r][c])) {
                return true;
            }
        }
        return false;
    }
    
    private Loc getSowLoc(int player) {
        //TODO: code interaction with player once UI is finished
        if(playerCanCapture(player)) {
            //Player MUST capture
            Loc selection = new Loc(0, 0);
            while(!pitCanCapture(selection) &&
                !getPit(selection).getSeeds() > 0) {
                //TODO: Get location from player
            }
            return selection;
        } else {
            //Player can't capture
            //TODO: what regulates this?
        }
    }

    private Loc getSowKichwa(int player) {
        Loc selection = new Loc(0, 0);
        while(!selection.isKichwa(player)) {
            //TODO: Get selection from player
        }
    }

    private void sowFrom(Loc start, int seeds) {
        int dir = start.getKichwaSowDir();
        Loc next = new Loc(start.getRow(), start.getCol());
        
        while(seeds > 0) {
            int c = next.getCol();
            int r = next.getRow();
            if(c + dir > 7 || c + dir < 0) {
                //Reached the end of row, move in opposite direction on other row
                dir *= -1;
                //TODO: change selection to outer row or inner row (mirror)
                if(r == 1 || r == 3) {
                    //next = new Loc(r--, c);
                    r--;
                } else {
                    //next = new Loc(r++, c);
                    r++;
                }
            }
            next = new Loc(c + dir, r);
            
            seeds--;
            getPit(getPit(next).addSeeds(1));
        }
        
        if(getPit(next.getLocAcross()).getSeeds() > 0) {
            //Capture has happened
            int sownum = getPit(next.getLocAcross()).setSeeds(0);
            if(!next.isKichwa() && !next.isKimbi()) {
                sowFrom(getSowKichwa(next.whosePit()), sownum);
            } else {
                sowFrom(getNearestKichwa(next), sownum);
            }
        }
        
        
            
            
    
    public void Play() {
        int winner = 0;
        int player = 0;
        while(winner == 0) {
            if(isRowEmpty(player) || !playerHasNonSingletons(player)) {
                //player's inner row is empty
                winner = player % 2;
                break;
            }
            if(players[player] > 0) { //Namua
                //TODO: User input: where to put the seed
                //restrictions: must capture if can capture, must have
                //seeds already in the selected pit
                Loc selection = getSowLoc(player);
                if(pitCanCapture(selection)) {
                    int seeds = selection.getLocAcross().setSeeds(0);
                    players[player]--;
                    if(!selection.isKichwa(player) &&
                        !selection.isKimba(player)) {
                        //Player can choose where to start sowing
                        Loc start = getSowKichwa(player);
                        sowFrom(start, seeds);
                        

                
                /*Loc loc = getSowLoc(player);
                Loc aloc = loc.getLocAcross();
                players[player]--;
                if(getPit(aloc).getSeeds() > 1) {
                    //Capture happened
                    Loc sowStart = loc.getNearestKichwa();
                    if(!loc.isKichwa() && !loc.isKimbi() {
                        Loc sowStart = getKichwaChoice(player);
                    }*/
                    
                
                
                
    /*private boolean placeSeed(int row, int col, int player) {
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
    }*/
}