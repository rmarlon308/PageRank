import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomWalker {
    private final float [][] matrix;
    private float [] rank;
    private int count = 0;
    private final int limit;
    private int spiderTraps = 0;
    private int deadEnds = 0;

    public RandomWalker(float [][] matrix){
        this.matrix = matrix;
        rank = new float[matrix.length];
        this.limit = matrix.length * 50;
    }

    public void start(){
        Random rand = new Random();
        int randomNode = rand.nextInt(matrix.length);
        iterate(randomNode);
    }

    public void iterate(int randomNode){
        if(count == limit){
            float sum = 0;
            for(int i = 0; i < rank.length; i++){
                rank[i] = rank[i] / limit;
                sum += rank[i];
            }

            System.out.println(Arrays.toString(rank));
            System.out.println("Suma total: " + sum);
            System.out.println("# de Caidas en DeadEnds: " + deadEnds);
            System.out.println("# de Caidas en SpiderTraps: " + spiderTraps);
            System.out.println("\n");
            return;
        }
        count++;
        rank[randomNode] += 1;
        System.out.println(count +"\t" + Arrays.toString(rank));
        if(deadEnd(randomNode)){
            deadEnds++;
            //System.out.println("Estamos en un deadEnd");
            //System.out.println(randomNode + "  []");
            Random rand = new Random();
            randomNode = rand.nextInt(matrix.length);
            iterate(randomNode);
            return;
        }

        if(spiderTrap(count)){
            spiderTraps++;
            //System.out.println("Estamos en un Spider Trap");
            //System.out.println(randomNode + "  []");
            Random rand = new Random();
            randomNode = rand.nextInt(matrix.length);
            iterate(randomNode);
            return;
        }

        ArrayList<Integer> out = getOutEdges(randomNode);
        //System.out.println(randomNode + " " + out);
        Random rand = new Random();
        randomNode = rand.nextInt(out.size());

        iterate(out.get(randomNode));
    }

    public boolean spiderTrap(int count){
        float [] sort = new float[rank.length];
        for (int i = 0; i < rank.length; i++) {
            sort[i] = rank[i]/count;
        }
        Arrays.sort(sort);
        float sum1 = 0;
        float sum2 = 0;

        for(int i = 0; i < sort.length; i++){
            if((float)i/sort.length < 0.5){
                sum1 += sort[i];
            } else{
                sum2 += sort[i];
            }
        }

        if(Math.abs(sum1 - sum2) >= 0.3){
            return true;
        }

        return false;
    }

    public boolean deadEnd(int randomNode){
        if(getOutEdges(randomNode).size() == 0){
            return true;
        }
        return false;
    }

    public ArrayList<Integer> getOutEdges(int node){
        ArrayList<Integer> edges = new ArrayList<Integer>();
        for(int i = 0; i < matrix.length; i++){
            if(matrix[node][i] != 0){
                edges.add(i);
            }
        }
        return edges;
    }
}
