import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class PowerIteration {
    private float beta;
    private final float[][] matrix;
    private final float epsilon;
    private int counter = 0;
    private final int MAX_ITERATIONS = 100;

    public PowerIteration(float[][] matrix, float beta){
        setBeta(beta);
        this.matrix = matrix;
        epsilon = (float)0.0001;
    }

    public void iterate(){
        float [] r_0 = new float[matrix.length];

        Arrays.fill(r_0, 1/(float)matrix.length);

        start(r_0, matrix);
    }

    public void start(float [] r_0, float [][] matrix){
        counter++;

        float[] r_1 = multiplyVector(r_0, matrix);

        System.out.println(Arrays.toString(r_1));

        float s = 0;
        for(int i = 0; i < r_1.length; i++){
            s += r_1[i];
        }
        System.out.println("Suma: " + s);

        if(deadEnd(r_1)){
            System.out.println("Estamos en un dead End, desea continuar? 1: segir, otro: salir");
            Scanner input = new Scanner(System.in);
            String in = input.nextLine();
            if(in.equals("1")) {
                alternativeTeleport();
            } else{
                System.exit(1);
            }
            //alternativeTeleport();
            return;
        }

        if(spiderTrap(r_1)){

            System.out.println("Estamos en un Spider Trap, desea continuar? 1: segir, otro: salir");
            Scanner input = new Scanner(System.in);
            String in = input.nextLine();
            if(in.equals("1")) {
                teleport();
            } else{
                System.exit(1);
            }
            //teleport();
            return;
        }


        if(converge(r_0,r_1) || counter == MAX_ITERATIONS){
            System.out.println(Arrays.toString(r_0));
            float sum = 0;
            for (float v : r_1) {
                sum += v;
            }
            System.out.println("Suma Total: " + sum + "\n\n");
            return;
        }
        start(r_1, matrix);
    }

    public void alternativeTeleport(){

        float [][] M = new float[matrix.length][matrix.length];

        for(int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length;j++) {
                M[i][j] = matrix[i][j];
            }
        }


        for(int i = 0; i < matrix.length; i++){
            int count = 0;
            for(int j = 0; j < matrix.length; j++){
                if(M[i][j] == 0){
                    count++;
                }
            }
            if(count == matrix.length) {
                for (int j = 0; j < matrix.length; j++) {
                    M[i][j] = (float) 1 / matrix.length;
                }
            }
        }
        float [] r_0 = new float[matrix.length];
        Arrays.fill(r_0, 1/(float)matrix.length);

        start(r_0, M);
    }

    public void teleport(){
        float alpha = 1 - beta;

        float [][] fair_matrix  = new float[matrix.length][matrix.length];
        float [][] M = new float[matrix.length][matrix.length];

        for(int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length;j++) {
                M[i][j] = matrix[i][j];
            }
        }


        for(int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length;j++){
                fair_matrix[i][j] = (float)1/ matrix.length * alpha;
                M[i][j] = M[i][j] * beta;
            }
        }

        float [][] add = addMatrices(M,fair_matrix);
        float [] r_0 = new float[matrix.length];
        Arrays.fill(r_0, 1/(float)matrix.length);

        start(r_0, add);
    }

    public boolean deadEnd(float [] r){
        float sum = 0;
        for(int i = 0; i < r.length; i++){
            float v  = (float)Math.round(r[i] * 1000) / 1000;
            sum += v;
        }
        if(sum > 0.5){
            return false;
        }
        return(true);
    }

    public boolean spiderTrap(float [] r){
        for(int i = 0; i < r.length; i++) {
            float v  = (float)Math.round(r[i] * 1000) / 1000;
            //System.out.println(v);
            if(v < epsilon){
                return true;
            }
        }
        return(false);
    }

    public boolean converge(float [] r_0, float [] r_1){
        for(int i = 0; i < r_0.length; i++){
            //r_1[i] - r_0[i]
            //System.out.println(v + "\t" + v1);
            if(Math.abs(r_1[i] - r_0[i]) > epsilon){
                return(false);
            }
        }
        return(true);
    }

    public float[] multiplyVector(float[] vector, float [][] matrix){
        float[] result = new float[matrix.length];

        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix.length; j++){
                result[i] += matrix[j][i] * vector[j];
            }
        }
        return result;
    }

    public float[][] addMatrices(float[][] firstMatrix, float[][] secondMatrix) {
        float[][] result = new float[matrix.length][matrix.length];
        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                result[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }
        return result;
    }


    public void setBeta(float beta){
        if(beta < 0 || beta > 1){
            System.out.println("Error en beta");
        }
        else{
            this.beta = beta;
        }
    }
}
