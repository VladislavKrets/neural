import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 13.10.2018.
 */
public class AbstractNeuralNetwork {
    private List<Neuron> firstLayer;
    private List<Neuron> lastLayer;
    private List<List<Neuron>> layers;
    private double learnRate;


    public AbstractNeuralNetwork(double learnRate, int... countOfNeuronsInEveryLayer) {
        this.learnRate = learnRate;
        layers = new ArrayList<List<Neuron>>();
        firstLayer = new ArrayList<Neuron>();
        List<Neuron> middleLayer;
        lastLayer = new ArrayList<Neuron>();
        for (int i = 0; i < countOfNeuronsInEveryLayer[0]; i++) {
            firstLayer.add(new Neuron(null, null, null));
        }
        layers.add(firstLayer);
        for (int i = 1; i < countOfNeuronsInEveryLayer.length - 1; i++) {
            middleLayer = new ArrayList<Neuron>();
            for (int j = 0; j < countOfNeuronsInEveryLayer[i]; j++) {
                middleLayer.add(new Neuron(layers.get(i - 1), randomWeights(layers.get(i - 1).size()), null));
            }
            layers.add(middleLayer);
            for (Neuron neuron : layers.get(i - 1)) {
                neuron.setNextLayer(middleLayer);
            }
        }
        for (int i = 0; i < countOfNeuronsInEveryLayer[countOfNeuronsInEveryLayer.length - 1]; i++) {
            lastLayer.add(new Neuron(layers.get(layers.size() - 1), randomWeights(layers.get(layers.size() - 1).size()), null));
        }
        layers.add(lastLayer);

    }

    public List<Double> getPrediction(List<Double> startValues) {
        if (startValues.size() != firstLayer.size()) throw new IllegalArgumentException();
        List<Double> prediction = new ArrayList<Double>();
        for (int i = 0; i < firstLayer.size(); i++) {
            firstLayer.get(i).setCurrentValue(startValues.get(i));
            firstLayer.get(i).setSigmoidValue(startValues.get(i));
        }
        for (int i = 1; i < layers.size(); i++) {
            oneLayerWork(layers.get(i - 1), layers.get(i));
        }
        for (Neuron neuron : layers.get(layers.size() - 1)) {
            prediction.add(neuron.getSigmoidValue());
        }
        return prediction;

    }

    private void oneLayerWork(List<Neuron> previousLayer, List<Neuron> currentLayer) {
        double value = 0;
        for (Neuron current : currentLayer) {
            for (int i = 0; i < previousLayer.size(); i++) {
                value += previousLayer.get(i).getSigmoidValue() * current.getWeights().get(i);
            }
            current.setCurrentValue(value);
            current.sigmoid();
            value = 0;
        }
    }

    public void train(List<Double> startsValues, List<Double> expectedPrediction) {
        if (expectedPrediction.size() != lastLayer.size()) throw new IllegalArgumentException();
        List<Double> prediction = getPrediction(startsValues);
        System.out.println(expectedPrediction.get(0) + " - " + prediction.get(0) + " = " + Math.abs(expectedPrediction.get(0) - prediction.get(0)));
        List<Double> lastLayerWeights;
        List<Double> secondLayerWeights;
        double lastError = 0;
        double secondError = 0;
        double lastWeightsDelta = 0;
        double secondWeightsDelta = 0;
        double newWeight = 0;
        for (int i = 0; i < lastLayer.size(); i++) {
            lastError = prediction.get(i) - expectedPrediction.get(i);
            lastWeightsDelta = lastError * sigmoidDerivative(lastLayer.get(i).getSigmoidValue());
            lastLayerWeights = lastLayer.get(i).getWeights();
            trainingLayerWork(lastLayerWeights, lastWeightsDelta, layers.size() - 2);
        }

    }

    private void trainingLayerWork(List<Double> previousLayerWeights, double previousWeightsDelta, int index) {
        double newWeight;
        double currentError;
        double currentWeightsDelta = 0;
        List<Double> currentLayerWeights = null;
        for (int j = 0; j < layers.get(index).size(); j++) {
            newWeight = previousLayerWeights.get(j) - layers.get(index).get(j).getSigmoidValue() * previousWeightsDelta * learnRate;
            previousLayerWeights.set(j, newWeight);
            currentError = newWeight * previousWeightsDelta;
            currentWeightsDelta = currentError * sigmoidDerivative(layers.get(index).get(j).getSigmoidValue());
            currentLayerWeights = layers.get(index).get(index).getWeights();
            newWeight = 0;
            if (index != 1) {
                trainingLayerWork(currentLayerWeights, currentWeightsDelta, index - 1);
            }
            if (index == 1) {
                for (int k = 0; k < firstLayer.size(); k++) {
                    newWeight = currentLayerWeights.get(k) - firstLayer.get(k).getCurrentValue() * currentWeightsDelta * learnRate;
                    currentLayerWeights.set(k, newWeight);
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

    public List<Neuron> getLastLayer() {
        return lastLayer;
    }
}
