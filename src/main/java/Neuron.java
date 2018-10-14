import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lollipop on 13.10.2018.
 */
public class Neuron implements Serializable{
    private List<Neuron> previousLayer;
    private List<Double> weights;
    private List<Neuron> nextLayer;
    private Double currentValue;
    private Double sigmoidValue;


    public Neuron(List<Neuron> previousLayer, List<Double> weights, List<Neuron> nextLayer) {
        this.previousLayer = previousLayer;
        this.weights = weights;
        this.nextLayer = nextLayer;
    }

    public List<Neuron> getPreviousLayer() {
        return previousLayer;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public List<Neuron> getNextLayer() {
        return nextLayer;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public Double getSigmoidValue() {
        return sigmoidValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public void sigmoid() {
        sigmoidValue =  1 / (1 + Math.pow(Math.E, -currentValue));
    }

    public void setPreviousLayer(List<Neuron> previousLayer) {
        this.previousLayer = previousLayer;
    }

    public void setNextLayer(List<Neuron> nextLayer) {
        this.nextLayer = nextLayer;
    }

    public void setSigmoidValue(Double sigmoidValue) {
        this.sigmoidValue = sigmoidValue;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                ", weights=" + weights +
                ", currentValue=" + currentValue +
                ", sigmoidValue=" + sigmoidValue +
                '}';
    }
}
