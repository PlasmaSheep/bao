//http://www.fdg.unimaas.nl/educ/donkers/games/Bao/rules.html
//http://mancala.wikia.com/wiki/Bao_la_Kiswahili

import java.util.Arrays;
//TODO: current player should be instance variable
//TODO: replace all dir getting methods with just one: get cw vs ccw
//TODO: ui methods: ask custom boolean question, ask custom cw vs ccw question
public class BaoGame {
    Pit[][] board;
    int[] players;
    int turn;
    boolean captureingMove;

    public BaoGame() {
        board = new Pit[4][8];
        players = new int[2]; //Player 0 on top, player 1 not
        Arrays.fill(players, 22);
        Arrays.fill(board, new Pit());
        turn = 1;
        /*for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 8; c++) {
                if((r == 1 && c == 3) || (r == 2 && c == 4)) {
                    board[r][c] = new Nyumba(6);
                } else if((r == 1 && c == 2) && (r == 1 && c == 2)) {
                    board[r][c] = new Pit(2);
                } else {
                    board[r][c] = new Pit();
                }
            }
        }*/
        capturingMove = false;
        board[1][3] = new Nyumba(6);
        board[2][4] = new Nyumba(6);

        board[1][2].setSeeds(2);
        board[1][1].setSeeds(2);

        board[1][5].setSeeds(2);
        board[1][6].setSeeds(2);
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
            for(int c = 0; c < 9; c++) {
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
        return false;
    }

    /**
     * Return true if the given pit can capture the pit across.
     */
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

    private int getTaxDir(int player) {
        //TODO: user input
        return 1; //or -1 depending
    } 

    private Loc getNyumbaLoc(int player) {
        if(player == 0 || player == 1) {
            return new Loc(player + 1, player + 3);
        } 
        return new Loc(-1, -1);
    }

    private int getSowDir(int player) {
        //TODO: user input
        return 1; //or -1 depending
    }

    private Loc getSowLoc(int player) {
        //TODO: code interaction with player once UI is finished
        if(playerCanCapture(player)) {
            //Player MUST capture
            Loc selection = new Loc(0, 0);
            while(!pitCanCapture(selection) &&
            !(getPit(selection).getSeeds() > 0)) {
                //TODO: Get location from player
            }
            return selection;
        } else {
            //Player can't capture
            Loc selection = new Loc(0, 0);
            boolean onlyNyumbaHasSeeds = true;
            for(int c = 0; c < 8; c++) {
                if(board[getInnerRow(player)][c].getSeeds() > 1) {
                    onlyNyumbaHasSeeds = false;
                    break;
                }
            }
            if(onlyNyumbaHasSeeds) {
                while(selection.getRow() != getInnerRow(player) ||
                    !(getPit(selection).getSeeds() > 1)) {
                    //TODO: get location from player
                }
            } else {
                while(selection.getRow() != getInnerRow(player) ||
                    getPit(selection) instanceof Nyumba ||
                    !(getPit(selection).getSeeds() > 1)) {
                    //TODO: get location from player
                }
            }
                
        }
        return new Loc(0, 0); //TODO: fix thisg
    }

    private Loc getSowKichwa(int player) {
        Loc selection = new Loc(0, 0);
        while(!selection.isKichwa(player)) {
            //TODO: Get selection from player
        }
        return selection;
    }
    
    private boolean getSafariChoice(int player) {
        //TODO: get selection from player
        return true;
    }

    private void sowFrom(Loc start, int seeds, int player) {
        int dir = start.getKichwaSowDir();
        int dir = getSowDir(player); //TODO: write this method
        Loc next = new Loc(start.getRow(), start.getCol());

        while(seeds > 0) {
            int c = next.getCol();
            int r = next.getRow();
            if(c + dir > 7 || c + dir < 0) {
                //Reached the end of row, move in opposite
                //direction on other row
                dir *= -1;
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
            getPit(next).addSeeds(1);
        }

        if(getPit(next.getLocAcross()).getSeeds() > 0 &&
            getPit(next).getSeeds() > 1) {
            //Capture has happened
            int sownum = getPit(next.getLocAcross()).setSeeds(0);
            if(!next.isKichwa() && !next.isKimbi()) {
                //sowFrom(getSowKichwa(next.whosePit()), sownum);
                sowFrom(start, sownum, player);
            } else {
                sowFrom(next.getNearestKichwa(), sownum, player);
            }
        }

        if(getPit(next) instanceof Nyumba && getPit(next).isFunctional()) {
            //TODO: check if user wants to safari or stop
            boolean safari = getSafariChoice(player);
            if(safari && capturingMove) {
                //Nyumba should no longer be functional
                sowFrom(start, getPit(next).setSeeds(0), player);
            }
        }
    }

    public void Play() {
        int winner = 0;
        int player = 0;
        while(true) {
            captureingMove = false;
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
                    capturingMove = true;
                    int seeds = getPit(selection.getLocAcross()).setSeeds(0);
                    players[player]--;
                    if(getPit(selection) instanceof Nyumba &&
                        getPit(selection).isFunctional()) {
                        //TODO: which way to sow from nyumba from player
                        getPit(selection).addSeeds(-1);
                        int dir = getTaxDir(player);
                        for(int i = 1; i >= 2; i++) {
                            Loc taxloc = new Loc(selection.getRow(),
                                selection.getCol() + i * dir);
                            getPit(taxloc).addSeeds(1);
                            if(i == 2 && getPit(taxloc).getSeeds() > 1) {
                                //TODO: fix this
                                //After the nyumba is taxed things are sown from
                                //that next pit
                                sowFrom(taxloc, getPit(taxloc).getSeeds(),
                                    player);
                            }
                        }
                    } else if(!selection.isKichwa(player) &&
                        !selection.isKimbi(player)) {
                        //Player can choose where to start sowing
                        Loc start = getSowKichwa(player);
                        sowFrom(start, seeds, player);
                    } else {
                        //Is a kichwa, player can't choose.
                        sowFrom(selection.getNearestKichwa(), seeds, player);
                    }
                } else {
                    //Takasa
                    getPit(selection).addSeeds(1);
                    if(getPit(selection) instanceof Nyumba) {
                        //TODO: tax the nyumba
                    } else {
                        int seeds = getPit(selection).setSeeds(0);
                        Loc start = getSowKichwa(player);
                        sowFrom(start, seeds, player);
                        //TODO: special takasa rules
                        //not from kichwa
                    }
                }
            } else { //mtaji
                if(playerCanCapture(player)) {
                    captureingMove = true;
                    //TODO: player input
                    Loc capture = getCapturingPit(player);
                    int dir = getDir(player);
                    //TODO: this is better than taxdir, etc.
                    sowFrom(capture, getPit(capture.getLocAcross()).getSeeds(),
                        player);
                } else {
                    if(rowHasNonSingletons(getInnerRow(player)) {
                        //TODO: special rules in that method (row specific)
                        Loc sow = getSowLoc(player, getInnerRow(player));
                    } else {
                        Loc sow = getSowLoc(player, getOuterRow(player));
                    }
                    int seeds = getPit(sow).setSeeds(0);
                    //This is not sown from the kichwa
                    sowFrom(sow, seeds, player);
                    
                }
            }
        }
    }
}