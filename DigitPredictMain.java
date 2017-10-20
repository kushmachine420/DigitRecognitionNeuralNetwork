
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import javax.imageio.ImageIO;
import neuralnetwork.NeuralNetwork2;

public class DigitPredictor {
    private static int[][] exampleSet;
    private static int[] labelSet;
    static BufferedImage img;
    static File f;

    public static void main(String[] args) throws IOException {
        // Get example data from .csv file
        initTrainingExamples("mnist_train.csv");

        // Split the example and label set into training sets and testing sets
        double percentTrain = 0.2;
        
        
        int trainSetSize = (int) Math.round(exampleSet.length * percentTrain);
        int testSetSize = exampleSet.length - trainSetSize;
        System.out.println(exampleSet.length);
        System.out.println(trainSetSize);
        System.out.println(testSetSize);
        System.out.println(labelSet.length);
        
        int[][] trainingExampleSet = new int[trainSetSize][];
        int[] trainingLabelSet = new int[trainSetSize];
        int[][] testingExampleSet = new int[testSetSize][];
        int[] testingLabelSet = new int[testSetSize];
        int inputSize = exampleSet[0].length;
        
        

        // Initialize the training and testing sets
        for(int exampleIndex = 0; exampleIndex < (trainSetSize + testSetSize); exampleIndex++) {
            if(exampleIndex < trainSetSize) {
                trainingExampleSet[exampleIndex] = exampleSet[exampleIndex];
                trainingLabelSet[exampleIndex] = labelSet[exampleIndex];
            } else {
                int index = exampleIndex - trainSetSize;
                testingExampleSet[index] = exampleSet[index];
                testingLabelSet[index] = labelSet[index];
            }
        }

        // Initialize structure of neural network
        int hiddenLayerSizes = 30;
        int numClasses = getNumClasses();
        float a=(float) 0.1;
        float lam=(float) 1;

        // Create instance of a neural network
        NeuralNetwork2 neuralNetwork = new NeuralNetwork2(inputSize, hiddenLayerSizes, numClasses);
       try{
        
            f=new File("D:\\Images\\Output3.jpg");
            img=ImageIO.read(f);
        
        }catch(IOException e){
        System.out.println("Error"+e);
        }

        int size=(int)Math.sqrt(inputSize);
        int i=0,black=0;
        for(int y=0; y<size;y++){
            for(int x=0;x<size;x++){
                      if(testingExampleSet[9][i]>0){
                      int rgb=(black<<24)| (black<<16) | (black<<8) | black;
                      img.setRGB(x, y, rgb);}
                      i++;
                      
            }
        }
        
        try{
                        f=new File("D:\\Images\\Output4.jpg");
                        ImageIO.write(img, "jpg", f);
             
                    }catch(IOException e){
                        System.out.println("Error"+e);
                    }
        
        // Train it and get the accuracy of the algorithm
       // int iterationsOfTraining = 10;
        neuralNetwork.train(trainingExampleSet, trainingLabelSet, 50, a, lam);
        //double accuracy = neuralNetwork.checkAccuracy(testingExampleSet, testingLabelSet);
        //System.out.println("Accuracy: " + accuracy);
        System.out.println(testingExampleSet.length);
        System.out.println(testingLabelSet.length);
       //System.out.println(neuralNetwork.predict(testingExampleSet[4]));
       // System.out.println(testingLabelSet[9]);
        System.out.println(neuralNetwork.Accuracy(testingExampleSet, testingLabelSet));
        System.out.println(neuralNetwork.Accuracy(trainingExampleSet, trainingLabelSet));
        
        
    }

    // Return the number of different classes from the training labels
    private static int getNumClasses() {
        int numClasses = 0;

        for(int label: labelSet)
            if(label > numClasses)
                numClasses = label;

        return numClasses + 1;
    }

    // Read the training examples from the .csv file and initialize them
    private static void initTrainingExamples(String filePath) throws IOException {
        // Read .csv file containing training examples
        String basePath = new File("").getAbsolutePath();
        System.out.println(basePath);
        File file = new File(basePath.concat("\\" + filePath));
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        
        lines.remove(0);
        //System.out.println(lines);
        
        // Randomize data
        long seed = System.nanoTime();
        Collections.shuffle(lines, new Random(seed));
        int lineCount = lines.size();
        

        // Initialize variables for training records
        exampleSet = new int[lineCount][];
        labelSet = new int[exampleSet.length];
        
        // Read through every line of the file
        int lineIndex = 0;
        for(String line : lines) {
            if(lineIndex != 0 &&lineIndex < lineCount) {
                // Parse training example and label into an array of characters
                String[] array = line.split(",");
                //System.out.println(array.length);
                
                // First integer is the class (the written integer)
                labelSet[lineIndex - 1] = Integer.parseInt(array[0]);

                // The remaining integers are features (pixel brightness)
                exampleSet[lineIndex - 1] = new int[array.length - 1];
                for (int i = 1; i < array.length; i++)
                    exampleSet[lineIndex - 1][i - 1] = Integer.parseInt(array[i]);
                
            }

            lineIndex++;
        }
    }
}