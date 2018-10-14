import java.io.*;
import java.util.*;

/**
 * Created by lollipop on 13.10.2018.
 */
public class Main {
    public static void main(String[] args) {
        /*AbstractNeuralNetwork neuralNetwork = loadWeights();
        Map<List<Double>, List<Double>> train = new HashMap<List<Double>, List<Double>>();
        train.put(Arrays.asList(0.0, 0.0, 0.0), Arrays.asList(0.0));
        train.put(Arrays.asList(0.0, 0.0, 1.0), Arrays.asList(1.0));
        train.put(Arrays.asList(0.0, 1.0, 0.0), Arrays.asList(0.0));
        train.put(Arrays.asList(0.0, 1.0, 1.0), Arrays.asList(0.0));
        train.put(Arrays.asList(1.0, 0.0, 0.0), Arrays.asList(1.0));
        train.put(Arrays.asList(1.0, 0.0, 1.0), Arrays.asList(1.0));
        train.put(Arrays.asList(1.0, 1.0, 0.0), Arrays.asList(0.0));
        train.put(Arrays.asList(1.0, 1.0, 1.0), Arrays.asList(0.0));
        List<Double> pred;
        for (Map.Entry<List<Double>, List<Double>> entry : train.entrySet()) {
            pred = neuralNetwork.getPrediction(entry.getKey());
            System.out.println("For values " + entry.getKey() + ", prediction is "
                    + String.format("%.4f", pred.get(0)) + ", expected value is " + entry.getValue());
        }*/

    }

    private static void sinus(){
        AbstractNeuralNetwork neuralNetwork = new AbstractNeuralNetwork(0.1, 1, 10, 10, 10, 1);
        Map<List<Double>, List<Double>> train = new HashMap<List<Double>, List<Double>>();
        for (double i = 0; i < 3; i += 0.3) {
            train.put(Arrays.asList(i), Arrays.asList(Math.sin(i)));
        }
        int epochs = 120000;
        for (int i = 0; i < epochs; i++) {
            for (Map.Entry<List<Double>, List<Double>> entry : train.entrySet()) {
                neuralNetwork.train(entry.getKey(), entry.getValue());
            }
        }
        List<Double> pred;
        for (Map.Entry<List<Double>, List<Double>> entry : train.entrySet()) {
            pred = neuralNetwork.getPrediction(entry.getKey());
            System.out.println("For values " + String.format("%.4f", entry.getKey().get(0))
                    + ", prediction is " + String.format("%.4f", pred.get(0))
                    + ", expected value is " + String.format("%.4f", entry.getValue().get(0)));
        }
        pred = neuralNetwork.getPrediction(Arrays.asList(3.1415));
        System.out.println("For values " + 3.1415
                + ", prediction is " + String.format("%.4f", pred.get(0))
                + ", expected value is " + Math.sin(3.1415));
        saveWeights(neuralNetwork);
    }

    private static void partyNN() {
        //NeuralNetwork neuralNetwork = new NeuralNetwork(0.08);
        AbstractNeuralNetwork neuralNetwork = new AbstractNeuralNetwork(0.08, 3, 2, 1);
        Map<List<Double>, List<Double>> train = new HashMap<List<Double>, List<Double>>();
        train.put(Arrays.asList(0.0, 0.0, 0.0), Arrays.asList(0.0));
        train.put(Arrays.asList(0.0, 0.0, 1.0), Arrays.asList(1.0));
        train.put(Arrays.asList(0.0, 1.0, 0.0), Arrays.asList(0.0));
        train.put(Arrays.asList(0.0, 1.0, 1.0), Arrays.asList(0.0));
        train.put(Arrays.asList(1.0, 0.0, 0.0), Arrays.asList(1.0));
        train.put(Arrays.asList(1.0, 0.0, 1.0), Arrays.asList(1.0));
        train.put(Arrays.asList(1.0, 1.0, 0.0), Arrays.asList(0.0));
        train.put(Arrays.asList(1.0, 1.0, 1.0), Arrays.asList(0.0));
        int epochs = 10000;
        for (int i = 0; i < epochs; i++) {
            for (Map.Entry<List<Double>, List<Double>> entry : train.entrySet()) {
                neuralNetwork.train(entry.getKey(), entry.getValue());
            }
        }
        List<Double> pred;
        for (Map.Entry<List<Double>, List<Double>> entry : train.entrySet()) {
            pred = neuralNetwork.getPrediction(entry.getKey());
            System.out.println("For values " + entry.getKey() + ", prediction is " + String.format("%.4f", pred.get(0)) + ", expected value is " + entry.getValue());
        }
        saveWeights(neuralNetwork);
    }
    private static void saveWeights(AbstractNeuralNetwork abstractNeuralNetwork) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("neural.tmp");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(abstractNeuralNetwork);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static AbstractNeuralNetwork loadWeights() {
        try {
            FileInputStream fileInputStream = new FileInputStream("neural.tmp");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            AbstractNeuralNetwork abstractNeuralNetwork = (AbstractNeuralNetwork) objectInputStream.readObject();
            return abstractNeuralNetwork;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
