
public class Nyumba extends Pit {
    public boolean tax() {
        if(super.addSeeds(-2)) {
            return true;
        }
        return false;
    }
    
    public Nyumba(int seeds) {
        super(seeds);
    }
    
    public Nyumba() {
        super();
    }
}