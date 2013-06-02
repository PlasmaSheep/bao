import java.util.Scanner;

public class UserIO {
    Scanner in;
    
    public UserIO() {
        in = new Scanner(System.in);
    }
    
    public boolean getYesNo(String q) {
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
    
    public Loc getLoc(String q, int player) {
        Loc ans = new Loc(-1, -1);
        while(ahs.whosePit() != player) {
            System.out.print("Player " + (player + 1) + ": " + q);
            System.out.print("Row: ");
            int r = in.nextInt();
        }
    }
}