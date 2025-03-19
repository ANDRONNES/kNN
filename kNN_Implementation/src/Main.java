import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static List<Flower> trainingDataBase;
    public static List<Flower> testingData;
    public static List<Flower> testingDataWithAnswers;


    public static void main(String[] args) {
        //Data Loading
        trainingDataBase = loadTrainingData("kNN_Implementation/iris_training.txt");
        testingData = loadTestingDataWitoutAnswers("kNN_Implementation/iris_test.txt");
        testingDataWithAnswers = loadTrainingData("kNN_Implementation/iris_test.txt");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number k(number of neighbours)");
        int k = scanner.nextInt();

        classifyFLowers(trainingDataBase, testingData, k);
        String[] parts = compareResultWithCorrectAnswers(testingDataWithAnswers, testingData).split(";");
        System.out.println("Number of correct ANSWERS: "+parts[1] + "\n" + "The percent of experiment accuracy: " + parts[0]);
        System.out.println("Now you can enter yours flower to check the type of this flower" +
                "\n" + "Enter parameters(sizes) into console one-by-one" + "\n" +
                "Do you want to continue? type \"yes\" or \"no\"");
        while(!scanner.nextLine().equals("!")){
            double[] newVec = new double[4];
            String s1 = scanner.nextLine();
            if(s1.equals("no")) break;
            else {
                System.out.println("Start entering the parameters");
                for (int i = 0; i < newVec.length; i++) {
                        newVec[i] = scanner.nextDouble();
                }
                Flower f = new Flower(newVec);
                classifyOneFlower(f, k, trainingDataBase);
                System.out.println("Yours iris type is " + f.getFlowerName());
                System.out.println("Do you want to continue? type \"yes\" or \"no\"");
            }
        }
    }

    private static void classifyOneFlower(Flower f, int k,List<Flower> trainingDataBase) {
        Map<Double,String> KNearestNeighbours = new TreeMap<>();
        for (int i = 0; i < trainingDataBase.size(); i++) {
            KNearestNeighbours.put(euclideanDistance(trainingDataBase.get(i).getParameters(),f.getParameters()),
                    trainingDataBase.get(i).getFlowerName());
        }
        int counter = 0;
        List<String> firstKAnswers = new ArrayList<>();
        for (Map.Entry<Double, String> entry : KNearestNeighbours.entrySet()) {
            if (counter < k) {
                firstKAnswers.add(entry.getValue());
//                    System.out.println(entry.getKey() + " -> " +entry.getValue());
            } else {
                break;
            }
            counter++;
        }
        f.setFlowerName(theMostFrequentAnswer(firstKAnswers));
    }

    private static String compareResultWithCorrectAnswers(List<Flower> testingDataWithAnswers, List<Flower> testingData) {
        int numberOfAnswers = testingDataWithAnswers.size();
        int numberOfCorrectAnswers = 0;
        for (int i = 0; i < testingData.size(); i++) {
            if (testingData.get(i).compareTo(testingDataWithAnswers.get(i)) == 1) {
                numberOfCorrectAnswers += 1;
            }
        }
        return ((double) numberOfCorrectAnswers / numberOfAnswers) * 100 + "%;" + numberOfCorrectAnswers;
    }

    private static void classifyFLowers(List<Flower> trainingDataBase, List<Flower> testingData, int k) {
        Map<Double, String> KNearestNeighbours;
        for (int i = 0; i < testingData.size(); i++) {
            KNearestNeighbours = new TreeMap<>();
            for (int j = 0; j < trainingDataBase.size(); j++) {
                KNearestNeighbours.put(euclideanDistance(trainingDataBase.get(j).getParameters(),
                        testingData.get(i).getParameters()), trainingDataBase.get(j).getFlowerName());
            }
            int counter = 0;
            List<String> firstKAnswers = new ArrayList<>();
            for (Map.Entry<Double, String> entry : KNearestNeighbours.entrySet()) {
                if (counter < k) {
                    firstKAnswers.add(entry.getValue());
//                    System.out.println(entry.getKey() + " -> " +entry.getValue());
                } else {
                    break;
                }
                counter++;
            }
            testingData.get(i).setFlowerName(theMostFrequentAnswer(firstKAnswers));
        }
    }


    public static String theMostFrequentAnswer(List<String> list) {
        Collections.sort(list);
        String theMostFrequentWord = list.get(0);
        int maxCount = 1;
        int currCount = 1;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).equals(list.get(i))) {
                currCount += 1;
            } else {
                if (currCount > maxCount) {
                    maxCount = currCount;
                    theMostFrequentWord = list.get(i - 1);
                }
                currCount = 1;
            }
        }
        if (currCount > maxCount) {
            maxCount = currCount;
            theMostFrequentWord = list.get(list.size() - 1);
        }
            /*String theMostFrequentWord = list.stream()
                    .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);*/
        return theMostFrequentWord;
    }

    public static List<Flower> loadTestingDataWitoutAnswers(String path) {
        List<Flower> flowersTestingDataBase = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] dataParts = line.split("\\s+");
                double[] params = new double[4];
                for (int i = 0; i < 4; i++) {

                    params[i] = Double.parseDouble(dataParts[i].replace(",", "."));
                }
                flowersTestingDataBase.add(new Flower(params));

            }
        } catch (Exception e) {
            System.out.println("There is no File");
        }
        return flowersTestingDataBase;
    }

    public static List<Flower> loadTrainingData(String path) {
        List<Flower> flowersDataBase = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(path));
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] dataParts = line.split("\\s+");
                double[] params = new double[4];
                for (int i = 0; i < 4; i++) {
                    params[i] = Double.parseDouble(dataParts[i].replace(",", "."));
                }
                flowersDataBase.add(new Flower(params, dataParts[dataParts.length - 1]));
            }
        } catch (Exception e) {
            System.out.println("There is no File");
        }
        return flowersDataBase;
    }

    public static double euclideanDistance(double[] trainingData, double[] testingData) {
        double finalDistance = 0;
        for (int i = 0; i < trainingData.length; i++) {
            finalDistance += Math.pow(testingData[i] - trainingData[i], 2);
        }
        return Math.sqrt(finalDistance);
    }
}
