import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ExplorerSearchTest {

    ///////////////////////////////////////////////
    /// ReachableArea() TESTS
    ///////////////////////////////////////////////
    @Test
    public void testReachableArea_someUnreachable() {
        int[][] island = {
            {1,1,1,3,1,1},
            {3,2,3,1,3,1},
            {1,1,1,1,3,3},
            {3,1,2,1,0,1},
            {1,1,1,2,1,1},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(14, actual);
    }
    @Test
    public void testReachableArea_All_Unreadchable(){
        int[][] island = {
            {3,3,3,3,3,3},
            {3,3,3,3,3,3},
            {3,3,3,0,3,3},
            {3,3,3,3,3,3},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(1, actual);
    }
    @Test
    public void testReachableArea_All_Reachable() {
        int[][] island = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(9, actual);
    }
    @Test
    public void testReachableArea_NoStartPoint() {
        int[][] island = {
            {1,1,1,3,1,1},
            {3,2,3,1,3,1},
            {1,1,1,1,3,3},
            {3,1,2,1,1,1},
            {1,1,1,2,1,1},
        };
        assertThrows(IllegalArgumentException.class, () -> {ExplorerSearch.reachableArea(island);});
    }

    @Test
    public void testReachableArea_1_Row(){
        int[][] island = {
            {0,1,1,3,3,3}
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(3, actual);
    }
    
    @Test
    public void testReachableArea_1_column() {
        int[][] island = {
            {1},
            {1},
            {0},
            {1},
            {1},
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(5, actual);
    }
    @Test
    public void testReachableArea_Single_Cell() {
        int[][] island = {
            {0}
        };
        int actual = ExplorerSearch.reachableArea(island);
        assertEquals(1, actual);
    }
    @Test
    public void testReachableArea_multiple_starting_points() {
        int[][] island = {
            {1,1,1,3,1,1},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {0,0,0,0,0,0},
            {1,1,1,2,1,1},
        };
        assertThrows(IllegalArgumentException.class, () -> {ExplorerSearch.reachableArea(island);});
    }
    @Test
    public void testReachableArea_Empty_Island() {
        int[][] island = {};

        assertThrows( IllegalArgumentException.class, () -> ExplorerSearch.reachableArea(island) );
    }


    ///////////////////////////////////////////////
    /// SetNeighbors() TESTS
    ///////////////////////////////////////////////
    @Test
    public void testSetNeighbors_NoValidNeighbors_Returns_Empty_Neighbor_ArrayList() {
        int[][] island = {
            {3, 2, 3},
            {3, 0, 2},
            {2, 3, 3}
        };

           ExplorerSearch.Point cur = new ExplorerSearch.Point(1, 1);

            Map<ExplorerSearch.Point, ArrayList<ExplorerSearch.Point>> graph = new HashMap<>();
            graph.put(cur, new ArrayList<>());

            ExplorerSearch.SetNeighbors(cur, island, graph);

            assertTrue(graph.get(cur).isEmpty());
        }


        @Test
        public void testSetNeighbors_ValidNeighbors_Returns_Populated_ArrayList() {
        int[][] island = {
            {3, 1, 3},
            {1, 0, 1},
            {2, 1, 3}
        };

           ExplorerSearch.Point cur = new ExplorerSearch.Point(1, 1);

            Map<ExplorerSearch.Point, ArrayList<ExplorerSearch.Point>> graph = new HashMap<>();
            graph.put(cur, new ArrayList<>());

            ExplorerSearch.SetNeighbors(cur, island, graph);

            assertEquals(4, graph.get(cur).size());
        }


        ///////////////////////////////////////////////
        /// CreateGraph() TESTS
        ///////////////////////////////////////////////
        @Test
        public void testCreateGraph_All_Traversable() {
            int[][] island = {
                {0, 1},
                {1, 1}
            };

            Map<ExplorerSearch.Point, ArrayList<ExplorerSearch.Point>> graph = new HashMap<>();
            ExplorerSearch.start = null;//had to add this because start Point is a class level value and has to be reset when a new graph is created
            Map<ExplorerSearch.Point, ArrayList<ExplorerSearch.Point>> result = ExplorerSearch.CreateGraph(island, graph);

            assertEquals(4, result.size());

            assertTrue(result.containsKey(new ExplorerSearch.Point(0, 0)));
            assertTrue(result.containsKey(new ExplorerSearch.Point(0, 1)));
            assertTrue(result.containsKey(new ExplorerSearch.Point(1, 0)));
            assertTrue(result.containsKey(new ExplorerSearch.Point(1, 1)));

            assertEquals(2, result.get(new ExplorerSearch.Point(0, 0)).size());
        }

        @Test
        public void testCreateGraph_Exclude_Blocked_Cells() {
            int[][] island = {
                {0, 2, 1},
                {3, 1, 3},
                {1, 2, 1}
            };

            Map<ExplorerSearch.Point, ArrayList<ExplorerSearch.Point>> graph = new HashMap<>();
            
            ExplorerSearch.start = null;//had to add this because start Point is a class level value and has to be reset when a new graph is 
            Map<ExplorerSearch.Point, ArrayList<ExplorerSearch.Point>> result = ExplorerSearch.CreateGraph(island, graph);

            assertEquals(5, result.size());

            assertFalse(result.containsKey(new ExplorerSearch.Point(0, 1)));
            assertFalse(result.containsKey(new ExplorerSearch.Point(1, 0)));
            assertFalse(result.containsKey(new ExplorerSearch.Point(1, 2)));
            assertFalse(result.containsKey(new ExplorerSearch.Point(2, 1)));

        }
}
