package chapter2.s1_graphs;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/*

 ID: mihirsh1
 LANG: JAVA
 TASK: castle

 */
public class castle {

    public static PrintWriter out;
    public static int COUNTER;
    public static ArrayList<Coordinate> already = new ArrayList<>();
    public static ArrayList<Integer> roomSizes = new ArrayList<>();

    public static class Coordinate implements Comparable {

        public int X;
        public int Y;

        public Coordinate(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }

        public Coordinate incrX(int amt) {
            X += amt;
            return this;
        }

        public Coordinate incrY(int amt) {
            Y += amt;
            return this;
        }

        public boolean equals(Coordinate other) {
            return this.X == other.X && this.Y == other.Y;
        }
        
        public int[] findWall(Coordinate other)
        {
            if(this.X - other.X == 1 && this.Y == other.Y)
            {
                int[] output = new int[3];
                
                output[0] = other.X;
                output[1] = other.Y;
                output[2] = 69; // W
                
                return output;

            } else if (other.X - this.X == 1 && this.Y == other.Y) {
                    
                int[] output = new int[3];
                
                output[0] = this.X;
                output[1] = this.Y;
                output[2] = 69; // E
                
                return output;

            } else if (this.Y - other.Y == 1 && this.X == other.X) {
                
                int[] output = new int[3];
                
                output[0] = this.X;
                output[1] = this.Y;
                output[2] = 78; // N
                
                return output;
                
            } else if (other.Y - this.Y == 1 && this.X == other.X) {
            
                int[] output = new int[3];
                
                output[0] = other.X;
                output[1] = other.Y;
                output[2] = 78; // S
                
                return output;
                
            }
            
            return null;
        }
        
        @Override
        public int compareTo(Object other)
        {
            Coordinate o = (Coordinate) other;
            return roomSizes.get(o.X) + roomSizes.get(o.Y) - roomSizes.get(this.X) - roomSizes.get(this.Y);
        }
        
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // "C:\\Users\\mihir\\Documents\\Backup\\Stuff\\NetBeansProjects\\USACO\\src\\ariprogOUT.txt"

        out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
        BufferedReader f = new BufferedReader(new FileReader("castle.in"));

        String[] initial = f.readLine().split(" ");

        int M = Integer.parseInt(initial[0]);
        int N = Integer.parseInt(initial[1]);

        int[][] wallMap = new int[N][M];

        for (int i = 0; i < N; i++) {
            String[] line = f.readLine().split(" ");

            for (int j = 0; j < M; j++) {
                wallMap[i][j] = Integer.parseInt(line[j]);
            }
        }

        Coordinate c = new Coordinate(0, 0);

        int NUM_ROOMS = 0;
        int MAX_ROOM = 0;
        
        int[][] rooms = new int[N][M];
        
        if(!isSame(wallMap) || M != N) {
            ArrayList<ArrayList<Coordinate>> visited = new ArrayList<>();

            do {
                ArrayList<Coordinate> next = new ArrayList<>();

                recur(c, wallMap, rooms, NUM_ROOMS, next);
                visited.add(next);

                c = findNext(rooms, M, N);

                if (COUNTER + 1> MAX_ROOM) {
                    MAX_ROOM = COUNTER + 1;
                }

                roomSizes.add(COUNTER + 1);

                NUM_ROOMS++;
                COUNTER = 0;
            } while (c != null);

            for (int i = 0; i < roomSizes.size() - 1; i++) {
                for (int j = i + 1; j < roomSizes.size(); j++) {
                    already.add(new Coordinate(i, j));
                }
            }

            Collections.sort(already);
            int[] wallInfo = new int[3];        

            do {
                if(!already.isEmpty())
                {
                    Coordinate next = already.remove(0);
                    wallInfo = wallExists(visited.get(next.X), visited.get(next.Y));
                } else {
                    break;
                }
            } while (wallInfo == null);

            out.println(NUM_ROOMS);
            out.println(MAX_ROOM);
            out.println(wallInfo[3]);
            out.println((wallInfo[1] + 1) + " " + (wallInfo[0] + 1) + " " + (char) wallInfo[2]);

            //printRooms(rooms);
        } else {
            out.println(M*N);
            out.println(1);
            out.println(2);
            out.println(M + " 1 N");
        }
        out.close();
                
        
    }
    
    public static void printRooms(int[][] rooms)
    {
        for(int i = 0; i<rooms.length; i++)
        {
            for(int j = 0; j<rooms[0].length; j++)
            {
                System.out.print(rooms[i][j] + " ");
            }
            
            System.out.println();
        }
    }

    public static boolean isSame(int[][] wallInfo)
    {
        int init = wallInfo[0][0];
        
        for(int[] i: wallInfo)
            for(int j: i)
                if(j != init)
                    return false;
        
        return true;
    }
    
    public static int[] wallExists(ArrayList<Coordinate> room1, ArrayList<Coordinate> room2) {

        ArrayList<int[]> solutions = new ArrayList<>();
        
        for (Coordinate room11 : room1) {
            for (Coordinate room21 : room2) {
                int[] array = room11.findWall(room21);
                if(array == null)
                    continue;
                solutions.add(array);
            } 
        }
        
        if(solutions.isEmpty())
            return null;
        
        int[] finSol = new int[4];
        int[] sol = findSmallest(solutions);
        
        System.arraycopy(sol, 0, finSol, 0, 3);
        finSol[3] = room1.size() + room2.size();
        
        return finSol;
    }

    public static int[] findSmallest(ArrayList<int[]> solutions)
    {
        int[] optimum = {Integer.MAX_VALUE, Integer.MAX_VALUE, 1};
        
        for(int[] i: solutions)
        {
            if(i[0] < optimum[0] || i[0] == optimum[0] && i[1] > optimum[1])
            {
                optimum = i;
            }
        }
        
        return optimum;
    }
    
    public static void sortAlready(ArrayList<Integer> roomSize) {
        for (int i = 0; i < already.size() - 1; i++) {
            for (int j = i + 1; j < already.size(); j++) {
                
                Coordinate t1 = already.get(i);
                Coordinate t2 = already.get(j);
                
                if (roomSize.get(t1.X) + roomSize.get(t1.Y) < roomSize.get(t2.X) + roomSize.get(t2.Y)) {
                    already.set(i, t2);
                    already.set(j, t1);
                }
            }
        }
    }

    public static void recur(Coordinate c, int[][] wallMap, int[][] visited, int NUM_ROOMS, ArrayList<Coordinate> next) {
        int init = wallMap[c.Y][c.X];
        Coordinate cS = new Coordinate(c.X, c.Y + 1);
        Coordinate cN = new Coordinate(c.X, c.Y - 1);
        Coordinate cW = new Coordinate(c.X - 1, c.Y);
        Coordinate cE = new Coordinate(c.X + 1, c.Y);

        visited[c.Y][c.X] = NUM_ROOMS + 1;
        next.add(c);

        if ((init & 1) == 0 && !(visited[cW.Y][cW.X] == NUM_ROOMS + 1)) {
            //visited.add(c);
            COUNTER++;
            recur(cW, wallMap, visited, NUM_ROOMS, next);
        }
        if ((init & 2) == 0 && !(visited[cN.Y][cN.X] == NUM_ROOMS + 1)) {
            //visited.add(c);
            COUNTER++;
            recur(cN, wallMap, visited, NUM_ROOMS, next);
        }
        if ((init & 4) == 0 && !(visited[cE.Y][cE.X] == NUM_ROOMS + 1)) {
            //visited.add(c);
            COUNTER++;
            recur(cE, wallMap, visited, NUM_ROOMS, next);
        }
        if ((init & 8) == 0 && !(visited[cS.Y][cS.X] == NUM_ROOMS + 1)) {
            //visited.add(c);
            COUNTER++;
            recur(cS, wallMap, visited, NUM_ROOMS, next);
        }
    }

    public static boolean containsSP(ArrayList<ArrayList<Coordinate>> visited, Coordinate c) {
        for (ArrayList<Coordinate> i : visited) {
            for (Coordinate d : i) {
                if (d.equals(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Coordinate findNext(int[][] rooms, int M, int N) {

        for (int i = 0; i < N; i++) 
            for (int j = 0; j < M; j++) 
                if(rooms[i][j] == 0)
                    return new Coordinate(j, i);

        return null;

    }

}
