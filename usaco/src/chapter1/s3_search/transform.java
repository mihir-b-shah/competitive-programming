package chapter1.s3_search;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**

ID: mihirsh1
LANG: JAVA
TASK: transform

 */

public class transform {
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        // "C:\\Users\\mihir\\Google Drive\\Computer Backup\\NetBeansProjects\\USACO\\src\\transfINPUT2.txt"
        
        BufferedReader f = new BufferedReader(new FileReader("transform.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
    
        int size = Integer.parseInt(f.readLine());
        char[][] matrix = new char[size][size];
        char[][] image = new char[size][size];
        
        //Input
        
        for(int i = 0; i<size; i++)
        {
            String s = f.readLine();
            
            for(int j = 0; j<size; j++)
            {
                matrix[i][j] = s.charAt(j);
            }
        } 
        
        for(int i = 0; i<size; i++)
        {
            String s = f.readLine();
            
            for(int j = 0; j<size; j++)
            {
                image[i][j] = s.charAt(j);
            }
        } 
        
        
        if (sameIdentify(rotateSquare90(matrix), image)) {
            out.println(1);
        } else if (sameIdentify(rotateSquare180(matrix), image)) {
            out.println(2);
        } else if (sameIdentify(rotateSquare270(matrix), image)) {
            out.println(3);
        } else if (sameIdentify(reflectSquare(matrix), image)) {
            out.println(4);
        } else if(sameIdentify(matrix, image)) {
            out.println(6);
        } else {
            out.println(attemptMatch(matrix, image));
        }
                
        out.close();
    }
    
    public static boolean sameIdentify(char[][] matrix, char[][] image)
    {
        if(matrix.length == image.length && matrix[0].length == image[0].length)
        {
            for(int i = 0; i<matrix.length; i++)
            {
                for(int j = 0; j<matrix.length; j++)
                {
                    if(matrix[i][j] != image[i][j])
                        return false;
                }
            }
        }
        
        return true;
    }
    
    public static char[][] reflectSquare(char[][] matrix)
    {
        char[][] nMat = new char[matrix.length][matrix.length];
        
        for(int i = 0; i<matrix.length/2; i++)
        {
            for(int j = 0; j<matrix.length; j++)
            {
                nMat[j][matrix.length-1-i] = matrix[j][i];
                nMat[j][i] = matrix[j][matrix.length-1-i];
            }
        }
        
        if(matrix.length % 2 == 1)
        {
            for(int k = 0; k<matrix.length; k++)
            {
                nMat[k][matrix.length/2] = matrix[k][matrix.length/2];
            }
        }

        return nMat;
    }
    
    public static char[][] rotateSquare270(char[][] matrix)
    {
        return rotateSquare90(rotateSquare90(rotateSquare90(matrix)));
    }
    
    public static char[][] rotateSquare180(char[][] matrix)
    {
        return rotateSquare90(rotateSquare90(matrix));
    }
    
    public static char[][] rotateSquare90(char[][] matrix)
    {
        char[][] nMat = new char[matrix.length][matrix.length];
        
        for(int i = 0; i<matrix.length; i++)
        {
            for(int j = 0; j<matrix.length; j++)
            {
                nMat[j][matrix.length - i - 1] = matrix[i][j];
            }
        }
        
        return nMat;
    }
    
    public static int attemptMatch(char[][] matrix, char[][] image) 
    {     
        char[][] temp = matrix.clone();
  
        temp = reflectSquare(temp);
        int counter = 0;
        
        while(!sameIdentify(temp, image) && counter < 4)
        {
            temp = rotateSquare90(temp);
            counter++;
        }
        
        if(sameIdentify(temp, image))
        {
            return 5;
        } else {
            return 7;
        }
        /*
        
        int p1 = recursiveHelper(temp, image, 0, 0);
        int p2 = recursiveHelper(temp, image, 90, 0);
        int p3 = recursiveHelper(temp, image, 180, 0);
        int p4 = recursiveHelper(temp, image, 270, 0);
        
        if(p1 == 5 || p2 == 5 || p3 == 5 || p4 == 5)
            return 5;
        else
            return 7;
        
        */
    }
    
    /*
    
    public static int recursiveHelper(char[][] temp, char[][] image, int angle, int iterator)
    {
        for(int i = 0; i<=angle/90; i++)
        {
            temp = rotateSquare90(temp);
        }
        
        temp = reflectSquare(temp);
        
        if(!sameIdentify(temp, image) && iterator <= 4)
        {
            int p1 = recursiveHelper(temp, image, 0, iterator + 1);
            int p2 = recursiveHelper(temp, image, 90, iterator + 1);
            int p3 = recursiveHelper(temp, image, 180, iterator + 1);
            int p4 = recursiveHelper(temp, image, 270, iterator + 1);
        } else if (iterator > 4) {
            return 7;
        } else if (sameIdentify(temp, image)) {
            return 5;
        }
        
        return 7;

    }
    
    */
    
    public static void print2DArray(char[][] array)
    {
        for(int i = 0; i<array.length; i++)
        {
            for(int j = 0; j<array[0].length; j++)
            {
                System.out.print(array[i][j]);
            }
            
            System.out.println();
        }
    }
}
