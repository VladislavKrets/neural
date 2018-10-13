import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 13.10.2018.
 */
public class NeuralNetwork {
    private List<Neuron> firstLayer;
    private List<Neuron> secondLayer;
    private List<Neuron> thirdLayer;
    private double learnRate;


    public NeuralNetwork(double learnRate) {
        this.learnRate = learnRate;
        firstLayer = new ArrayList<Neuron>();
        secondLayer = new ArrayList<Neuron>();
        thirdLayer = new ArrayList<Neuron>();
        for (int i = 0; i < 3; i++) {
            firstLayer.add(new Neuron(null, null, secondLayer));
        }
        for (int i = 0; i < 2; i++) {
            secondLayer.add(new Neuron(firstLayer, randomWeights(firstLayer.size()), thirdLayer));
        }
        for (int i = 0; i < 1; i++) {
            thirdLayer.add(new Neuron(secondLayer, randomWeights(secondLayer.size()), null));
        }

    }

    public List<Double> getPrediction(List<Double> startValues) {
        if (startValues.size() != firstLayer.size()) throw new IllegalArgumentException();
        List<Double> prediction = new ArrayList<Double>();
        for (int i = 0; i < firstLayer.size(); i++) {
            firstLayer.get(i).setCurrentValue(startValues.get(i));
        }
        double value = 0;
        for (Neuron secondLayerNeuron : secondLayer) {
            for (int i = 0; i < firstLayer.size(); i++) {
                value += firstLayer.get(i).getCurrentValue() * secondLayerNeuron.getWeights().get(i);
            }
            secondLayerNeuron.setCurrentValue(value);
            secondLayerNeuron.sigmoid();
            value = 0;
        }
        value = 0;
        for (Neuron thirdLayerNeuron : thirdLayer) {
            for (int i = 0; i < secondLayer.size(); i++) {
                value += secondLayer.get(i).getSigmoidValue() * thirdLayerNeuron.getWeights().get(i);
            }
            thirdLayerNeuron.setCurrentValue(value);
            thirdLayerNeuron.sigmoid();
            prediction.add(thirdLayerNeuron.getSigmoidValue()); //getting predictions values
            value = 0;
        }

        return prediction;
    }

    public void train(List<Double> startsValues, List<Double> expectedPrediction) {
        if (expectedPrediction.size() != thirdLayer.size()) throw new IllegalArgumentException();
        List<Double> prediction = getPrediction(startsValues);
        System.out.println(Math.abs(expectedPrediction.get(0) - prediction.get(0)));
        List<Double> thirdLayerWeights;
        List<Double> secondLayerWeights;
        double thirdError = 0;
        double secondError = 0;
        double thirdWeightsDelta = 0;
        double secondWeightsDelta = 0;
        double newWeight = 0;
        for (int i = 0; i < thirdLayer.size(); i++) {
            thirdError = prediction.get(i) - expectedPrediction.get(i);
            thirdWeightsDelta = thirdError * sigmoidDerivative(thirdLayer.get(i).getSigmoidValue());
            thirdLayerWeights = thirdLayer.get(i).getWeights();
            for (int j = 0; j < secondLayer.size(); j++) {
                newWeight = thirdLayerWeights.get(j) - secondLayer.get(j).getSigmoidValue() * thirdWeightsDelta * learnRate;
                thirdLayerWeights.set(j, newWeight);
                secondError = newWeight * thirdWeightsDelta;
                secondWeightsDelta = secondError * sigmoidDerivative(secondLayer.get(j).getSigmoidValue());
                secondLayerWeights = secondLayer.get(i).getWeights();
                newWeight = 0;
                for (int k = 0; k < firstLayer.size(); k++) {
                    newWeight = secondLayerWeights.get(k) - firstLayer.get(k).getCurrentValue() * secondWeightsDelta * learnRate;
                    secondLayerWeights.set(k, newWeight);
                    newWeight = 0;
                }
            }

        }
    }

    private double sigmoidDerivative(double value) {
        double sigmoid = sigmoid(value);
        return sigmoid * (1 - sigmoid);
    }

    private double sigmoid(double value) {
        return 1 / (1 + Math.pow(Math.E, -value));
    }

    private List<Double> randomWeights(int count) {
        List<Double> weights = new ArrayList<Double>();
        for (int i = 0; i < count; i++) {
            weights.add(-2 + Math.random() * 3);
        }
        return weights;
    }

    public List<Neuron> getFirstLayer() {
        return firstLayer;
    }

    public List<Neuron> getSecondLayer() {
        return secondLayer;
    }

    public List<Neuron> getThirdLayer() {
        return thirdLayer;
    }
}
