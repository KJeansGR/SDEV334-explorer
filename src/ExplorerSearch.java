import java.util.*;


public class ExplorerSearch {

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
    

    /*
        creates a Integer point pair 
    */
    public record Point(int row, int col) {};


    public static int reachableArea(int[][] island) {
        // Implement your method here!
        // Please also make more test cases
        // I STRONGLY RECOMMEND testing some helpers you might make too

        int matrixSize = island.length * island[0].length; // total matrix size
        int col = island[0].length; // column length
        int row = island.length; // row length

        Point start = null; // starting point

        //make a graph
        Map<Point, ArrayList<Point>> graph = new HashMap<>();

        //populates the graph and connections
        //flatten matrix for single loop and add all traversable values as keys 
        //this code does not yet determine whats traversable from start, just "traversable" points
        for(int i = 0; i < matrixSize; i++){
            int nRow= i/col;
            int nCol = i%col;

            Point cur = new Point(nRow, nCol);
            // System.out.println(cur.toString());
            //skip adding nodes that arent traversable 
            if(island[nRow][nCol] == 2 || island[nRow][nCol] == 3){
                continue;
            }
            // create the start point if found during loop
            if(island[nRow][nCol] == 0){
                start = cur;
            }

            // add a key for the current point to the graph
            graph.putIfAbsent(cur, new ArrayList<>());
            
            //up
            //dont add invalid points to neighbor list
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

            ///WRONG adds diagonals
            // add neighbors of the current point 
            // for(int x = -1; x < 1; x++){
            //     for(int y = -1; y < 1; y++){
            //         int neighborY = (cur.col + y);
            //         int neighborX = (cur.row + x);

            //         //if neighbor is in valid bounds add it to the current nodes nighbor list
            //         if(neighborY >= 0 && neighborY < row  && neighborX >= 0 && neighborX < col){

            //             //dont add invalid points to neighbor list
            //             if(island[neighborX][neighborY] != 2 && island[neighborX][neighborY] != 3){
            //                 graph.get(cur).add(new Point(neighborY, neighborX));
            //             }
            //         }
            //     }
            // }
            System.out.println("Cur: " + cur+ "| Neighbors: " + graph.get(cur));
        }

        /////////////////////////////////////
        //Begin Traversal of graph to get reachable area
        /////////////////////////////////////
        Set<Point> visited = new HashSet<>();
        Stack<Point> stack = new Stack<>();
        
        if(start == null){
            throw new IllegalArgumentException("no start node");
        }

        stack.push(start);

        System.out.println("start point: " + start);
        System.out.println("start neighbors: " + graph.get(start));

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
