public class Nyumba extends Pit {
    private boolean safari;
    
    public boolean tax() {
        if(!isFunctional()) {
            return false;
        } else {
            super.addSeeds(-2);
            return true;
        }
    }
    
    public Nyumba(int seeds) {
        super(seeds);
        safari = false;
    }
    
    public Nyumba() {
        super();
        safari = false;
    }
    
    public boolean isFunctional() {
        if(!safari && super.getSeeds() > 5) {
            return true;
        }
        return false;
    }
    
    public int setSeeds(int seeds) {
        int ret = super.setSeeds(seeds);
        if(seeds == 0) {
            safari = true;
        }
        return ret;
    }
}