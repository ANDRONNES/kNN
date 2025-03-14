import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main {
    public static List<Flower> trainingDataBase;
    public static void main(String[] args) {
        trainingDataBase = loadTrainingData("..\\kNN_Implementation\\iris_training.txt");

    }

    public static List<Flower> loadTrainingData(String path) {
//        List<List<Double>> trainingValues = new ArrayList<>();
//        List<String> trainingFlowerNames = new ArrayList<>();
        List<Flower> flowersDataBase = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] dataParts = line.split("\\s+");
//                List<Double> helpingList = new ArrayList<>();
                double[] params = new double[4];
                for (int i = 0; i < 4; i++) {
//                    helpingList.add(Double.parseDouble(dataParts[i].replace(",",".")));
                    params[i] = Double.parseDouble(dataParts[i].replace(",","."));
                }
                flowersDataBase.add(new Flower(params,dataParts[dataParts.length - 1]));
//                trainingValues.add(helpingList);
//                trainingFlowerNames.add(dataParts[dataParts.length - 1]);
            }
//            System.out.println(trainingValues);
//            System.out.println(trainingFlowerNames);
            System.out.println(flowersDataBase);
        } catch (Exception e) {
            System.out.println("There is no File");
        }
        return flowersDataBase;
    }
    public static double euclideanDistance(List<Double> trainingData, List<Double> testingData){
        double finalDistance = 0;
        for (int i = 0; i < trainingData.size(); i++) {
            finalDistance+=Math.pow(testingData.get(i) - trainingData.get(i),2);
        }
        return Math.sqrt(finalDistance);
    }

    public static void comparing(){

    }
}
