import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) throws IOException {
        String ruta = "/home/marlon/mainfolder/marlon/USFQ/DataMining/2_PageRank/P2_2/src/";
        while (true){
            Graph graph = new Graph(3, ruta + "basic_node_test.txt");
            System.out.println("Escoja una grafo especifico: \n1. basic_node_test\n2. node_test\n3. node");

            Scanner input = new Scanner(System.in);
            String in = input.nextLine();

            switch (in){
                case "1":
                    graph = new Graph(3, ruta + "basic_node_test.txt");
                    break;
                case "2":
                    graph = new Graph(20, ruta + "node_test.txt");
                    break;
                case "3":
                    graph = new Graph(20, ruta + "node.txt");
                    break;
                default:
                    System.out.println("Opcion Inexistente");
                    System.exit(1);

            }


            float beta = (float) 0.8;

            graph.loadMatrixWithFile();
            graph.toStochasticMatrix();
            graph.printMatrix();
            System.out.println();

            while (true){
                boolean pass = false;
                System.out.println("1. Realizar Power Iteration \n2. Realizar Random Walker\n3. Menu Principal");
                in = input.nextLine();

                switch (in){
                    case "1":
                        PowerIteration powerIteration = new PowerIteration(graph.getMatrix(),beta);
                        powerIteration.iterate();
                        break;
                    case "2":
                        RandomWalker randomWalker = new RandomWalker(graph.getMatrix());
                        randomWalker.start();
                        break;
                    case "3":
                        pass = true;
                        break;
                    default:
                        System.out.println("Opcion Inexistente");
                        System.exit(1);
                }
                if(pass){
                    break;
                }

            }

        }


    }
}
