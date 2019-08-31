import java.util.*;
import java.io.FileReader;
import java.io.IOException;

public class BDFS {
    //stores discovered nodes when running DFS
    private static ArrayList<Integer> list = new ArrayList<>();
    //stores discovered nodes when running DFS
    private static ArrayList<Integer> discovered = new ArrayList<>();
    private static Queue<Integer> queue = new LinkedList<>();
    //stores the frontiers of each node
    private static int[] frontiers = new int [4039];
    
    public static int[][] readFile(String fileName) throws IOException {
        
        //reading a file
        FileReader fr = new FileReader(fileName);
        Scanner br = new Scanner(fr);
        
        int[][] nodes = new int[4039][4039];
        
        while (br.hasNext()) {
            int nodeOne = br.nextInt();
            int nodeTwo = br.nextInt();
            nodes[nodeOne][nodeTwo] = 1;
            nodes[nodeTwo][nodeOne] = 1;
        }
        
        br.close();
        
        return nodes;
    }
    
    //Depth-First Search Algorithm
    public static void DFS(int[][] fbNodes, int node) {
        list.add(node);
        for (int col = 0; col < fbNodes.length; col++) {
            //check for neighbours
            if (fbNodes[node][col] == 1) {
                if (!(list.contains(col))) {
                    DFS(fbNodes, col);
                }
            }
        }
    }
    
    //Breadth-First Search Algorithm
    public static void BFS(int[][] fbNodes, int node) {
        queue.add(node);
        discovered.add(node);
        while (!queue.isEmpty()) {
            //current node
            int n = queue.poll();
            for (int col = 0; col < fbNodes.length; col++) {
                //check for neighbours
                if (fbNodes[n][col] == 1) {
                    if (!discovered.contains(col)) {
                        queue.add(col);
                        discovered.add(col);
                        frontiers[col] = frontiers[n] + 1;
                    }
                }
            }
        }
    }
    
    /*
     This function determines the max value in an array
    */
    public static int maxValue(int[] array) {
        int max = 0;
        for (int i = 0; i < 4039; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }
    
    public static void main(String[] args) throws IOException {
        int[][] fbNodes = readFile("facebook_combined.txt");
        //q. 2
        DFS(fbNodes, 0);
        //q. 3
        BFS(fbNodes, 0);
        
        //check if the graph is connected (q. 4b)
        if (list.size() == fbNodes.length) {
            System.out.println("The graph is connected");
        } else {
            System.out.println("The graph is not connected");
        }
        
        //number of frontiers (q. 4c)
        for (int i = 0; i < 3; i++) {
            System.out.println();
            frontiers = new int[4039];
            discovered.clear();
            int random = (int) (Math.random() * 4039);
            BFS(fbNodes, random);
            System.out.println("Starting node: " + random);
            System.out.println("Number of frontiers: " + maxValue(frontiers));
        }
        
        //q. 4d
        System.out.println();
        frontiers = new int[4039];
        discovered.clear();
        //keeps track of number of nodes within distance 4 from 1985
        int count = 0;
        BFS(fbNodes, 1985);
        for (int i = 0; i < frontiers.length; i++) {
            if (frontiers[i] <= 4) {
                count++;
            }
        }
        System.out.println("Number of nodes within distance 4 from node 1985: " 
                               + count);
        
        //q. 4a
        frontiers = new int[4039];
        discovered.clear();
        BFS(fbNodes, 10);
        System.out.println("Distance between nodes 10 and 1050: " + 
                           (frontiers[1050]));
    }
}
