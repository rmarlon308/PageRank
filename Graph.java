import java.io.*;
import java.util.Arrays;

public class Graph {

    private int nodes;
    private String fileName;
    private float matrix[][];

    public  Graph(int nodes, String fileName){
        setNodes(nodes);
        this.fileName = fileName;
        matrix = new float[nodes][nodes];
        fillWithZeros();
    }

    public void fillWithZeros(){
        for (int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                matrix[i][j] = 0;
            }
        }
    }

    public void loadMatrixWithFile() throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null){
                if(!line.contains("#") && !line.equals("")){
                    String[] splitLine = line.split(",");
                    int v1 = Integer.parseInt(splitLine[0]);
                    int v2 = Integer.parseInt(splitLine[1]) ;

                    addEdge(v1,v2,1);
                }
                line = reader.readLine();
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void toStochasticMatrix(){
        for(int i = 0; i < matrix.length; i++){
            float count = 0;
            for(int j = 0; j < matrix.length; j++){
                if(matrix[i][j] != 0){
                    count++;
                }
            }
            for(int j = 0; j < matrix.length; j++){
                if(matrix[i][j] != 0 && count > 0){
                    modifyNode(i, j, 1/count);
                }
            }
        }
    }

    public void addEdge(int v1, int v2, float value){
        matrix[v1][v2] = value;
    }

    public void printMatrix(){
        for (float[] floats : matrix) {
            for (float aFloat : floats) {
                System.out.printf("%f\t", aFloat);
            }
            System.out.println();
        }
    }

    public void modifyNode(int v1, int v2, float value){
        matrix[v1][v2] = value;
    }

    public void setNodes(int nodes){
        if(nodes < 1 || nodes > 26){
            System.out.println("El numero de nodos no es correcto");
        } else{
            this.nodes = nodes;
        }
    }

    public float[][] getMatrix() {
        return matrix;
    }
}
