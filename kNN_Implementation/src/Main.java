import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        loadTrainingData("..\\kNN_Implementation\\iris_training.txt");
    }

    public static void loadTrainingData(String path) {
        List<List<Double>> trainingValues = new ArrayList<>();
        List<String> trainingFlowerNames = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dataParts = line.split("\\s+");
                List<Double> helpingList = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    helpingList.add(Double.parseDouble(dataParts[i]));
                }
                trainingValues.add(helpingList);
                trainingFlowerNames.add(dataParts[dataParts.length - 1]);
            }
            System.out.println(trainingValues);
            System.out.println(trainingFlowerNames);

        } catch (Exception e) {
            System.out.println("There is no File");
        }
    }
}
