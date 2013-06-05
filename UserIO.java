import java.util.Scanner;

public class UserIO {
    Scanner in;
    
    public UserIO() {
        in = new Scanner(System.in);
    }
    
    public boolean getBoolean(String q) {
        String ans = "";
        while(true) {
            System.out.print(q + "(y/n): ");
            ans = in.next();
            if(ans.equalsIgnoreCase("y")) {
                return true;
            } else if(ans.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }
    
    public Loc getLoc(String q) {
        System.out.println(q);
        int r = -1;
        int c = -1;
        while(!(r > -1 && r < 4) || !(c > -1 && c < 8)) {
            System.out.print("Row: ");
            r = in.nextInt() - 1;
            System.out.print("Column: ");
            c = in.nextInt() - 1;
        }
        return new Loc(r, c);
    }

    public Loc getCapLoc(String q, int p, BaoGame g) {
        System.out.println(q);
        Loc ans = new Loc(0, 0);
        while(ans.whosePit() != p || !g.pitCanCapture(ans)) {
            System.out.print("Row: ");
            int r = in.nextInt() - 1;
            System.out.print("Column: ");
            int c = in.nextInt() - 1;
            ans = new Loc(r, c);
        }
        return ans;
    }

    public int getDir(String q) {
        System.out.println(q);
        String ans = "";
        while(!ans.equalsIgnoreCase("cw") && !ans.equalsIgnoreCase("ccw")) {
            System.out.print("CW or CCW?");
            ans = in.next();
        }
        if(ans.equalsIgnoreCase("cw")) {
            return 1;
        } else {
            return -1;
        }
    }

    private void printHB(int r) {
        System.out.print("+");
        for(int i = 0; i < 8; i++) {
            if(((r == 1 || r == 2) && i == 3) ||
                ((r == 2 || r == 3) && i == 4)) {
               System.out.print("xxxx");
            } else {
                System.out.print("----");
            }
            System.out.print("+");
        }
        System.out.print("\n");
    }

    public void printBoard(Pit[][] b) {
        printHB(0);
        for(int r = 0; r < b.length; r++) {
            System.out.print("| ");
            for(int c = 0; c < b[0].length; c++) {
                System.out.printf("%2d", b[r][c].getSeeds());
                if((r == 1 && (c == 2 || c == 3)) ||
                    (r == 2 && (c == 3 || c == 4))) {
                    System.out.print(" x ");
                } else {
                    System.out.print(" | ");
                }
            }
            System.out.print("\n");
            printHB(r + 1);
        }
    }

    public int printTitle() {
        System.out.println("****************SWAHILIAN***************");
        System.out.println("     ___           ___           ___     ");
        System.out.println("    /\\  \\         /\\  \\         /\\  \\    ");
        System.out.println("   /::\\  \\       /::\\  \\       /::\\  \\   ");
        System.out.println(
            "  /:/\\:\\  \\     /:/\\:\\  \\     /:/\\:\\  \\  ");
        System.out.println(
            " /::\\~\\:\\__\\   /::\\~\\:\\  \\   /:/  \\:\\  \\ ");
        System.out.println(
            "/:/\\:\\ \\:|__| /:/\\:\\ \\:\\__\\ /:/__/ \\:\\__\\");
        System.out.println(
            "\\:\\~\\:\\/:/  / \\/__\\:\\/:/  / \\:\\  \\ /:/  /");
        System.out.println(" \\:\\ \\::/  /       \\::/  /   \\:\\  /:/  / ");
        System.out.println("  \\:\\/:/  /        /:/  /     \\:\\/:/  /  ");
        System.out.println("   \\::/__/        /:/  /       \\::/  /   ");
        System.out.println("    ~~            \\/__/         \\/__/    ");
        System.out.println("Select game mode:");
        System.out.println("1. PvP");
        System.out.println("2. AI");
        int mode = -1;
        while(mode != 1 && mode != 2) {
            System.out.print("Mode: ");
            mode = in.nextInt();
        }
        if(mode == 1) {
            return 1;
        } else {
            System.out.println("Select AI difficulty:");
            System.out.println("1. Stupid");
            System.out.println("2. Slightly less stupid");
            mode = -1;
            while(mode != 1 && mode != 2) {
                System.out.print("Difficulty: ");
                mode = in.nextInt();
            }
            return mode + 1;
        }
    }
}