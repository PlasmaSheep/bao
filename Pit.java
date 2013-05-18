public class Pit {
    private int seeds;
    
    public Pit(int numseeds) {
        seeds = numseeds;
    }
    
    public Pit() {
        seeds = 0;
    }
    
    public int getSeeds() {
        return seeds;
    }
    
    public boolean setSeeds(int num) {
        if(num > -1) {
            seeds = num;
            return true;
        }
        return false;
    }
    
    public boolean addSeeds(int num) {
        if(seeds + num > -1) {
            seeds += num;
            return true;
        }
        return false;
    }
}

