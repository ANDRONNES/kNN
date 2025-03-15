import java.util.Arrays;

public class Flower implements Comparable<Flower>{
    private double[] parameters;
    private String flowerName;

    public Flower(double[] parameters,String flowerName){
        this.parameters = parameters;
        this.flowerName = flowerName;
    }

    public Flower(double[] parameters) {
        this.parameters = parameters;
    }

    public double[] getParameters() {
        return parameters;
    }

    public void setParameters(double[] parameters) {
        this.parameters = parameters;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "parameters=" + Arrays.toString(parameters) +
                ", flowerName='" + flowerName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Flower o) {
        if(sum(this.getParameters()) == sum(o.getParameters()) && flowerName.equals(o.getFlowerName())){
            return 1;
        } else return 0;
    }

    public double sum(double[] params){
        double sum = 0;
        for(Double d : params){
            sum+=d;
        }
        return sum;
    }
}
