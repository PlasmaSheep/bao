import java.util.Scanner;

public class UserIO {
    Scanner in;
    
    public UserIO() {
        in = new Scanner(System.in);
    }
    
    public boolean getBoolean(String q) {
        String ans = "";
        while(true) {
            System.out.println(q + "(y/n)");
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
        while(!(r > -1 && r < 4) || !(c > -1 && c < 8)) {
            System.out.println("Row: ");
            int r = in.nextInt() - 1;
            System.out.println("Column: ");
            int c = in.nextInt() - 1;
        }
        return new Loc(r, c);
    }

    public Loc getCapLoc(String q, int p, BaoGame g) {
        System.out.println(q);
        Loc ans = new Loc(0, 0);
        while(ans.whosePit() != p || !g.pitCanCapture(ans)) {
            System.out.println("Row: ");
            int r = in.nextInt() - 1;
            System.out.println("Column: ");
            int c = in.nextInt() - 1;
            ans = new Loc(r, c);
        }
        return ans;
    }

    public int getDir(String q) {
        System.out.println(q);
        String ans = "";
        while(!ans.equalsIgnoreCase("cw") && !ans.equalsIgnoreCase("ccw")) {
            System.out.println("CW or CCW?");
            ans = in.next();
        }
        if(ans.equalsIgnoreCase("cw")) {
            return 1;
        } else {
            return -1;
        }
    }
            
}