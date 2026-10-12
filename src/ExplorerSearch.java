import java.util.*;


public class ExplorerSearch {

    /**
     * creates a Integer point pair for traversing the island matrix
     * it will need to be reset if a new graph is created because it is class level
     * @param row
     * @param col
     */
    public record Point(int row, int col) {};

    public static Point start; // Starting Point for the traversal


    /**
     * Returns how much land area an explorer can reach on a rectangular island.
     * 
     * The island is represented by a rectangular int[][] that contains
     * ONLY the following nunbers:
     * 
     * '0': represents the starting location of the explorer
     * '1': represents a field the explorer can walk through
     * '2': represents a body of water the explorer cannot cross
     * '3': represents a mountain the explorer cannot cross
     * 
     * The explorer can move one square at a time: up, down, left, or right.
     * They CANNOT move diagonally.
     * They CANNOT move off the edge of the island.
     * They CANNOT move onto a a body of water or mountain.
     * 
     * This method should return the total number of spaces the explorer is able
     * to reach from their starting location. It should include the starting
     * location of the explorer.
     * 
     * For example
     * 
     * @param island the locations on the island
     * @return the number of spaces the explorer can reach
     */
    public static int reachableArea(int[][] island) {

        //verify island is not empty
        if(island.length == 0){
            throw  new IllegalArgumentException("island is empty");
        }

        // matrixSize = island.length * island[0].length; // total matrix size
        // col = island[0].length; // column length
        // row = island.length; // row length
        start = null; // starting point

        //Create Graph
        Map<Point, ArrayList<Point>> graph = CreateGraph( island, new HashMap<>());

        
        // Begin Traversal of graph to get reachable area
        Set<Point> visited = new HashSet<>();
        Stack<Point> stack = new Stack<>();
        
        //Verify a start node exist before traversal
        if(start == null){
            throw new IllegalArgumentException("no start node");
        }

        stack.push(start);

        while(!stack.isEmpty()){
            Point cur = stack.pop();
            visited.add(cur);

            for(Point curNeighbor : graph.get(cur)){
                if(!visited.contains(curNeighbor)){
                    stack.push(curNeighbor);
                }
            }
        }

        return visited.size();
    }

    /**
     * sets the neighbors for a current point and exludes any untraversable points
     * @param cur current Point being processed
     * @param island the provided matrix "island" for the problem
     * @param graph the current graph associated with the island 
     */
    public static void SetNeighbors(Point cur, int[][]island, Map<Point, ArrayList<Point>> graph){

        int col = island[0].length; // column length
        int row = island.length; // row length

        //up
        if( cur.row -1 >= 0 && island[cur.row -1 ][cur.col ] != 2 && island[cur.row -1][cur.col] != 3 ){
            graph.get(cur).add(new Point(cur.row -1, cur.col));
        }
        //down
        if( cur.row + 1 < row && island[cur.row + 1 ][cur.col ] != 2 && island[cur.row + 1][cur.col] != 3){
            graph.get(cur).add(new Point(cur.row + 1, cur.col));
        }
        //left
        if( cur.col - 1 >= 0 && island[cur.row][cur.col - 1  ] != 2 && island[cur.row][cur.col - 1 ] != 3){
            graph.get(cur).add(new Point(cur.row , cur.col - 1));
        }
        //right
        if( cur.col + 1 < col && island[cur.row][ cur.col + 1] != 2 && island[cur.row][ cur.col + 1] != 3){
            graph.get(cur).add(new Point(cur.row , cur.col + 1));
        }
        //System.out.println("Cur: " + cur+ "| Neighbors: " + graph.get(cur));
    }

   
    /**
     * Constructs an graph representing the traversable cells of the provided island matrix.
     *
     * This method identifies the starting position (value 0)
     * and ensures that no more than one starting position exists.
     * The resulting graph includes all traversable cells, regardless
     * of whether they are reachable from the starting position.
     *
     * @param island the 2D matrix representing the island
     * @param graph the adjacency-list map to populate
     * @return the populated adjacency-list graph
     * @throws IllegalArgumentException if multiple starting positions exist
     */
    public static Map<Point, ArrayList<Point>> CreateGraph(int[][] island, Map<Point, ArrayList<Point>> graph ){

        int matrixSize = island.length * island[0].length; // total matrix size
        int col = island[0].length; // column length

        for(int i = 0; i < matrixSize; i++){
            int nRow= i/col;
            int nCol = i%col;

            Point cur = new Point(nRow, nCol);

            //skip adding nodes that arent traversable 
            if(island[nRow][nCol] == 2 || island[nRow][nCol] == 3){
                continue;
            }
            // create the start point if found during loop
            if(island[nRow][nCol] == 0){
                //test to make sure there is only 1 starting point
                if(start != null){
                    throw  new IllegalArgumentException("Cannot have multiple starting points");
                }
                start = cur;
            }
            // add a key for the current point to the graph
            graph.putIfAbsent(cur, new ArrayList<>());
            
            //sets current neighbors in loop
            SetNeighbors(cur, island, graph);
        }
        return graph;
    }




    public static void main(String[] args) {

        int[][] island = {
            {1,1,1,3,1,1},
            {3,2,3,1,3,1},
            {1,1,1,1,3,3},
            {3,1,2,1,0,1},
            {1,1,1,2,1,1},
        };
        
        //PrettyPrint.prettyPrintln(island);
        int answer = reachableArea(island);
        System.out.println("answer: " + answer );
    }

}
