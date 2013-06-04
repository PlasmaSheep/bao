//http://www.fdg.unimaas.nl/educ/donkers/games/Bao/rules.html
//http://mancala.wikia.com/wiki/Bao_la_Kiswahili

import java.util.Arrays;
public class BaoGame {
    Pit[][] board;
    int[] players;
    int turn;
    boolean capturingMove;
    int currentPlayer;
    boolean namua;
    UserIO io;

    public BaoGame() {
        board = new Pit[4][8];
        players = new int[2]; //Player 0 on top, player 1 not
        io = new UserIO();
        Arrays.fill(players, 22);
        //Arrays.fill(board, new Pit());
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 8; c++) {
                board[r][c] = new Pit();
            }
        }
        turn = 0;
        currentPlayer = 0; //At any moment, current player is turn % 2.
        namua = true;
        capturingMove = false;
        board[1][3] = new Nyumba(6);
        board[2][4] = new Nyumba(6);

        board[1][2].setSeeds(2);
        board[1][1].setSeeds(2);

        board[2][5].setSeeds(2);
        board[2][6].setSeeds(2);
    }
    
    /**
     * Check if the given row has only pits with no stones.
     */
    private boolean isRowEmpty(int row) {
        for(int col = 0; col <= 7; col++) {
            if(board[row][col].getSeeds() != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the row number of a player's inner row (row closest to the middle).
     */
    private int getInnerRow(int player) {
        if(player == 0 || player == 1) {
            return player + 1;
        }
        return -1;
    }

    private int getOuterRow(int player) {
        if(player == 0 || player == 1) {
            return player * 3;
        }
        return -1;
    }

    /**
     * Get the pit described by loc.
     */
    private Pit getPit(Loc loc) {
        return board[loc.getRow()][loc.getCol()];
    }

    /**
     * Return true if a player has at least one pit with more than one stone in
     * it, false otherwise.
     */
    private boolean playerHasNonSingletons(int player) {
        for(int r = player * 2; r <= player * 2 + 1; r++) {
            for(int c = 0; c < 8; c++) {
                if(board[r][c].getSeeds() > 1) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean rowHasNonSingletons(int r) {
        for(int c = 0; c < 8; c++) {
            if(board[r][c].getSeeds() > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if the given pit can capture the pit across.
     */
    public boolean pitCanCapture(Loc loc) {
        //Can only capture if the pit at loc is in an interior row and the
        //pit across has seeds in it
        if(loc.isInner() && ((namua && getPit(loc).getSeeds() > 0) ||
            (!namua && getPit(loc).getSeeds() > 1)) &&
            getPit(loc.getLocAcross()).getSeeds() > 0) {
                return true;
        }
        return false;
    }

    /**
     * Return true if any of the player's pits can capture the pit across.
     */
    private boolean playerCanCapture(int player) {
        int r = getInnerRow(player);
        for(int c = 0; c < 8; c++) {
            if(pitCanCapture(new Loc(r, c))) {
                return true;
            }
        }
        return false;
    }

    private void taxNyumba(int player, int dir) {
        Loc nyumba = getNyumbaLoc(player);
        getPit(nyumba).addSeeds(-1);
        for(int i = 1; i >= 2; i++) {
            Loc taxloc = new Loc(nyumba.getRow(), nyumba.getCol() + i * dir);
            getPit(taxloc).addSeeds(1);
            if(i == 2 && getPit(taxloc).getSeeds() > 1) {
                //After the nyumba is taxed things are sown from that next pit,
                //if necessary
                sowFrom(taxloc, getPit(taxloc).getSeeds(),
                    currentPlayer, dir);
            }
        }
    }

    private Loc getNyumbaLoc(int player) {
        if(player == 0 || player == 1) {
            return new Loc(player + 1, player + 3);
        } 
        return new Loc(-1, -1);
    }

    private Loc getSowLoc(int player) {
        return getSowLoc(player, new int[] {0, 1, 2, 3});
    }

    private Loc getSowLoc(int player, int[] rows) {
        
        if(playerCanCapture(player)) {
            //Player MUST capture
            Loc selection = new Loc(0, 0);
            while(!pitCanCapture(selection) &&
            !(getPit(selection).getSeeds() > 0) &&
            Arrays.binarySearch(rows, selection.getRow()) < 0) {
                selection = io.getLoc(
                "Select a pit to sow. Pit must be able to capture.");
            }
            return selection;
        } else {
            //Player can't capture
            Loc selection = new Loc(0, 0);
            boolean onlyNyumbaHasSeeds = true;
            int r = getInnerRow(player);
            for(int c = 0; c < 8; c++) {
                if(board[r][c].getSeeds() > 1 &&
                    !(r == player + 1 && c == player + 2)) {
                    onlyNyumbaHasSeeds = false;
                    break;
                }
            }
            if(onlyNyumbaHasSeeds) { //Player can only move from nyumba
                return new Loc(player + 1, player + 3);
            } else {
                int minseed = 2;
                if(namua && !rowHasNonSingletons(getInnerRow(player))) {
                    minseed = 1;
                }
                while(selection.getRow() != getInnerRow(player) ||
                    getPit(selection).isFunctional() ||
                    !(getPit(selection).getSeeds() >= minseed) ||
                    Arrays.binarySearch(rows, selection.getRow()) < 0) {
                    System.out.print("Pit must be in your inner row, not a functional nyumba, and have more than 1 seeds.");
                    selection = io.getLoc("Select a pit to sow.");
                }
                return selection;
            }
        }
    }

    private Loc getSowKichwa(int player) {
        Loc selection = new Loc(0, 0);
        while(!selection.isKichwa(player)) {
            selection = io.getLoc("Select one of your kichwas.");
        }
        return selection;
    }
    
    private boolean getSafariChoice(int player) {
        return io.getBoolean("Safari the nyumba?");
    }

    private void sowFrom(Loc start, int seeds, int player) {
        int dir = start.getKichwaSowDir();
        sowFrom(start, seeds, player, dir);
    }

    private void sowFrom(Loc start, int seeds, int player, int dir) {
        //private void sowFrom(Loc start, int seeds) {
        //int dir = io.getDir("Which way to start sowing?");
        Loc next = new Loc(start.getRow(), start.getCol());

        while(seeds > 0) { //Iterate until seeds run out
            disp();
            seeds--;
            getPit(next).addSeeds(1);
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

            //seeds--;
            //getPit(next).addSeeds(1);
        }
        disp();

        if(getPit(next.getLocAcross()).getSeeds() > 0 &&
            getPit(next).getSeeds() > 1 && capturingMove == true) {
            //Capture has happened, is a capturing move, so capture
            int sownum = getPit(next.getLocAcross()).setSeeds(0);
            if(!next.isKichwa() && !next.isKimbi()) {
                //sowFrom(getSowKichwa(next.whosePit()), sownum);
                sowFrom(start, sownum, player);
            } else {
                sowFrom(next.getNearestKichwa(), sownum, player);
            }
        }

        if(getPit(next) instanceof Nyumba && getPit(next).isFunctional()) {
            boolean safari = getSafariChoice(player);
            if(safari && capturingMove) {
                //Nyumba should no longer be functional
                sowFrom(start, getPit(next).setSeeds(0), player);
            }
        }
    }

    public void Play() {
        while(true) {
            disp();
            System.out.println("Player " + (currentPlayer + 1) + "'s move");
            capturingMove = false;
            if(isRowEmpty(getInnerRow(currentPlayer)) ||
                !playerHasNonSingletons(currentPlayer)) {
                //player's inner row is empty, player has lost
                currentPlayer = turn++ % 2;
                break;
            }
            if(players[currentPlayer] > 0) { //Namua
                //restrictions: must capture if can capture, must have
                //seeds already in the selected pit
                Loc selection = getSowLoc(currentPlayer);
                if(pitCanCapture(selection)) { //Namua w/ cap
                    capturingMove = true;
                    int seeds = getPit(selection.getLocAcross()).setSeeds(0);
                    players[currentPlayer]--;
                    if(getPit(selection) instanceof Nyumba &&
                        getPit(selection).isFunctional()) {
                        getPit(selection).addSeeds(-1);
                        int dir = io.getDir("Which way to tax nyumba?");
                        taxNyumba(currentPlayer, dir);
                    } else if(!selection.isKichwa(currentPlayer) &&
                        !selection.isKimbi(currentPlayer)) {
                        //Player can choose where to start sowing
                        Loc start = getSowKichwa(currentPlayer);
                        sowFrom(start, seeds, currentPlayer);
                    } else {
                        //Is a kichwa, player can't choose.
                        sowFrom(selection.getNearestKichwa(), seeds,
                            currentPlayer);
                    }
                } else { //Namua no cap
                    getPit(selection).addSeeds(1);
                    if(getPit(selection).isFunctional()) {
                        getPit(selection).addSeeds(-2);
                        int dir = io.getDir("Which way to sow from namua?");
                        sowFrom(selection, 2, currentPlayer, dir);
                    } else {
                        int seeds = getPit(selection).setSeeds(0);
                        if(selection.isKichwa()) {
                            sowFrom(selection, seeds, currentPlayer);
                        } else {
                            Loc start = getSowKichwa(currentPlayer);
                            sowFrom(start, seeds, currentPlayer);
                        }
                    }
                }
            } else { //mtaji
                namua = false;
                if(playerCanCapture(currentPlayer)) { //Mtaji w/cap
                    capturingMove = true;
                    Loc capture = io.getCapLoc(
                        "Select pit to sow (must capture)", currentPlayer,
                        this);
                    int dir = io.getDir("Which way to sow?");
                    sowFrom(capture, getPit(capture.getLocAcross()).getSeeds(),
                        currentPlayer, dir);
                } else { //Mtaji no cap
                    //TODO: do this better?
                    Loc sow = getSowLoc(currentPlayer);
                    //getOuterRow(currentPlayer));
                    if(rowHasNonSingletons(getInnerRow(currentPlayer))) {
                        sow = getSowLoc(currentPlayer,
                            new int[] {getInnerRow(currentPlayer)});
                        //getInnerRow(currentPlayer));
                    }
                        
                    int seeds = getPit(sow).setSeeds(0);
                    //This is not sown from the kichwa
                    sowFrom(sow, seeds, currentPlayer);
                    
                }
            }
            turn++;
            currentPlayer = turn % 2;
        }
        System.out.print("Winner: " + (currentPlayer + 1));
    }

    public void disp() {
        System.out.print("\n");
        io.printBoard(board);
    }
}