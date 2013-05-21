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
    
    public int setSeeds(int num) {
        int oseeds = seeds;
        if(num > -1) {
            seeds = num;
            return oseeds;
        }
        return -1;
    }
    
    public int addSeeds(int num) {
        int oseeds = seeds;
        if(seeds + num > -1) {
            seeds += num;
            return oseeds;
        }
        return -1;
    }
}