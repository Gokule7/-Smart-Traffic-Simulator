package com.example.trafficSim.ml;

import java.util.Random;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class TrafficPredictor {
    private MultiLayerNetwork model;
    private double[][] inputs;
    private double[][] outputs;

    public TrafficPredictor() {
        initModel();
    }

    private void initModel() {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(123)
            .updater(new Adam(0.001))
            .list()
            .layer(new DenseLayer.Builder().nIn(3).nOut(8).activation(Activation.RELU).build()) // Increased nodes
            .layer(new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                .nIn(8).nOut(1).activation(Activation.SIGMOID).build())
            .build();
        model = new MultiLayerNetwork(conf);
        model.init();
    }

    public void generateData() {
        // Step 1: Data Collection (Synthetic data)
        double[][] inputs = new double[1000][3];  // Increased to 1000 samples
        double[][] outputs = new double[1000][1];
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            double vehicleCount = rand.nextInt(50) + 1; // 1 to 50 vehicles
            double avgSpeed = rand.nextDouble() * 2;    // 0 to 2
            double isGreen = rand.nextBoolean() ? 1.0 : 0.0;
            // Normalize inputs
            inputs[i] = new double[]{vehicleCount / 50.0, avgSpeed / 2.0, isGreen};
            double congestion = Math.min(1.0, vehicleCount / 50.0 + (2 - avgSpeed) / 2.0 + (1 - isGreen) * 0.3);
            outputs[i] = new double[]{congestion};
        }
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void train() {
        // Step 2: Data Preprocessing (Split into train/test)
        int trainSize = (int)(inputs.length * 0.8);
        double[][] trainInputs = new double[trainSize][3];
        double[][] trainOutputs = new double[trainSize][1];
        double[][] testInputs = new double[inputs.length - trainSize][3];
        double[][] testOutputs = new double[inputs.length - trainSize][1];
        System.arraycopy(inputs, 0, trainInputs, 0, trainSize);
        System.arraycopy(outputs, 0, trainOutputs, 0, trainSize);
        System.arraycopy(inputs, trainSize, testInputs, 0, inputs.length - trainSize);
        System.arraycopy(outputs, trainSize, testOutputs, 0, outputs.length - trainSize);

        // Step 3: Training the Model
        INDArray trainInput = Nd4j.create(trainInputs);
        INDArray trainOutput = Nd4j.create(trainOutputs);
        DataSet trainDataSet = new DataSet(trainInput, trainOutput);
        for (int i = 0; i < 100; i++) {
            model.fit(trainDataSet);
        }

        // Step 4: Evaluating the Model
        INDArray testInput = Nd4j.create(testInputs);
        INDArray testOutput = Nd4j.create(testOutputs);
        INDArray predictions = model.output(testInput);
        double mse = 0;
        for (int i = 0; i < testOutputs.length; i++) {
            double error = predictions.getDouble(i) - testOutputs[i][0];
            mse += error * error;
        }
        mse /= testOutputs.length;
        System.out.println("Mean Squared Error: " + mse);
    }

    public double predict(double vehicleCount, double speed, double isGreen) {
        // Step 5: Prediction (Normalize inputs)
        INDArray input = Nd4j.create(new double[][]{{vehicleCount / 50.0, speed / 2.0, isGreen}});
        INDArray output = model.output(input);
        double congestion = output.getDouble(0);
        // Ensure congestion is within valid range
        return Math.max(0.0, Math.min(1.0, congestion));
    }
}