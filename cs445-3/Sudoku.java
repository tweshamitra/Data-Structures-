package cs445.a3;

import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {
    static boolean isFullSolution(int[][] board) {
        if(reject(board))
        	return false;
        for(int i=0; i<9; i++)
        {
        	for (int j=0; j<9; j++)
        	{
        		if(board[i][j]==0)
        			return false;
        	}
        }
        return true;	
    }
   	
    static boolean isRowValid(int i, int j, int[][] board)
    {
    	for (int row=0; row<9; row++)
    	if(row!=i && board[row][j]==board[i][j])
   				return false; //invalid row
   		return true; //valid row
   	}
   	
   	static boolean isColValid(int i, int j, int[][] board)
   	{
   		for(int col=0; col<9; col++)
   			if(col!=j && (board[i][col]==board[i][j]))
   				return false;
   		return true;
   	}
   	static boolean isSquareValid(int i, int j, int[][] board)
   	{
   		for(int row=(i/3)*3; row<(i/3)*3 +3; row++)
   			for(int col=(j/3)*3; col<(j/3)*3+3; col++)
   				if(row !=i && col!=j && (board[row][col]==board[i][j]))
   					return false;
   		return true;
   	}

	static boolean reject(int [][] board)
	{
		for (int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				if(board[i][j]<0 || board[i][j]>9 || (board[i][j]!=0 && (!isRowValid(i,j,board) || !isColValid(i,j,board) || !isSquareValid(i,j,board))))
					return true;
		return false;
	}
    static int[][] next(int[][] board, int[]position) 
    {
    	int temp[][]= new int[9][9];
    	int r=position[0];
    	int c=position[1];
    	for(int i=0; i<9; i++)
    		for(int j=0; j<9; j++)
    			temp[i][j]=board[i][j];
    	if (temp[r][c]==9)
    		return null;
    	else 	
    		{
    		temp[r][c]++;
    		return temp;
    		}
    }
    
	static int[][] extend(int[][] board,int[]position)
	{	
		int[][]temp= new int[9][9];
		for(int i=0; i<9; i++)
			for(int j=0; j<9; j++)
				temp[i][j]=board[i][j];
		for (int i=0; i<9; i++){
			for(int j=0; j<9; j++){
				if(board[i][j]==0){
					temp[i][j]=1;
					position[0]=i;
					position[1]=j;
					return temp;
					}
			}
		}
		return null;
	}
		
    static void testIsFullSolutionUnit(int[][] test) {
		if(isFullSolution(test))
		{
			System.err.println("\nFull solution:\t");
			printBoard(test);
		}
		else 
		{
			System.err.println("\nNot full solution:\t");
			printBoard(test);
    	}
    }
    static void testIsFullSolution()
    {
    	System.out.println("\nTesting isFullSolution()");
    	//not full solutions:
    	testIsFullSolutionUnit(new int[][]{
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
    	testIsFullSolutionUnit(new int[][]{
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
		{1,1,9,9,9,9,9,9,9},
		{1,1,9,9,9,9,9,9,9} });
		testIsFullSolutionUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,2,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7} });
		testIsFullSolutionUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,0} });
    	//full solutions:
    	testIsFullSolutionUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7} });
	
    }	
	static void testRejectUnit(int[][] test)
	{
		if (reject(test))
		{
			System.err.println("\nRejected:\t" );
			printBoard(test);
		}
		else if(!reject(test))
		{
			System.err.println("\nNot rejected:\t");
			printBoard(test);
		}
	}
    static void testReject() {
    	System.err.println("\nTesting reject()");
    	//should be rejected
    	testRejectUnit(new int[][]{
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
		{1,1,9,9,9,9,9,9,9},
		{1,1,9,9,9,9,9,9,9} });
		testRejectUnit(new int[][]{
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,-1,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		testRejectUnit(new int[][]{
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,11,0},
		{0,0,0,0,0,0,0,0,0} });
		testRejectUnit(new int[][]{
		{1,0,0,0,0,0,0,0,0},
		{0,1,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		testRejectUnit(new int[][]{
		{1,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		//should not be rejected
		testRejectUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7} });
		testRejectUnit(new int[][]{
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		testRejectUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,0} });
		
    }
	static void testExtendUnit(int [][]test)
	{
		System.err.println("\nExtended ");
		printBoard(test);
		System.out.println("\nto");
		int [] position= new int[2];
		printBoard(extend(test,position));
		//printBoard(test);
	}
    static void testExtend() 
    {	
    	System.err.println("\nTesting extend()");
		//can be extended
		testExtendUnit(new int[][] {
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		testExtendUnit(new int[][] {
		{0,1,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,5,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,9,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		testExtendUnit(new int[][] {
		{1,2,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,5,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,9,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		testExtendUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,0} });
		
		//cannot be extended
		testExtendUnit(new int[][]{
		{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
    	{1,1,9,9,9,9,9,9,9},
		{1,1,9,9,9,9,9,9,9},
		{1,1,9,9,9,9,9,9,9} });
		testExtendUnit(new int[][]{
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7} });
		
    }
	static void testNextUnit(int[][] board, int[]position)
	{
		if(next(board,position)!=null)
		{
			System.err.println("\nNexted:");
			printBoard(board);
		}
		else 
		{
			System.err.println("Cannot be nexted");
			printBoard(board);
		}
		
	}
    static void testNext() 
    {
    	System.out.println("\nTesting next()");
    	//can be nexted
    	System.out.println("Original Board:\n");
    	
    	printBoard(new int[][] {
		{1,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		
		System.out.println("\nExtended board:");
		int [] position=new int[2];
		int temp[][]=extend(new int[][] {
		{1,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} }, position);
		
		if(temp!=null)
			printBoard(temp);
		else 
			System.out.println("Could not extend board");
		
		System.out.println("\nNexted board:");
		try{
			printBoard(next(temp,position));
		}catch(NullPointerException e3){
			System.out.println("Could not next board");
		}
			
		
		System.out.println("\nOriginal Board:\n");
    	
    	printBoard(new int[][] {
		{1,2,0,4,5,6,7,8,9},
		{0,0,3,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} });
		
		System.out.println("\nExtended board:");
		position=new int[2];
		temp=extend(new int[][] {
		{1,2,0,4,5,6,7,8,9},
		{0,0,3,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0} }, position);
		
		if(temp!=null)
			printBoard(temp);
		else 
			System.out.println("Could not extend board");
		
		System.out.println("\nNexted board:");
		try{
			printBoard(next(temp,position));
		} catch (NullPointerException e){
			System.out.println("Could not next board");
		}
		
		System.out.println("\nOriginal Board:\n");
    	
    	printBoard(new int[][] {
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,8} });
		
		System.out.println("\nExtended board:");
		position=new int[2];
		temp=extend(new int[][] {
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,9},
		{9,9,9,9,9,9,9,9,8} }, position);
		
		if(temp!=null)
			printBoard(temp);
		else
			System.out.println("Could not extend board");
		
		System.out.println("\nNexted board:");
		try{
			printBoard(next(temp,position));
		}catch(NullPointerException e2){
			System.out.println("Cannot next board\n");
		}
		
		System.out.println("\nOriginal Board:\n");
    	
    	printBoard(new int[][] {
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7} });
		
		System.out.println("\nExtended board:");
		position=new int[2];
		temp=extend(new int[][] {
		{8,2,7,1,5,4,3,9,6},
		{9,6,5,3,2,7,1,4,8},
		{3,4,1,6,8,9,7,5,2},
		{5,9,3,4,6,8,2,7,1},
		{4,7,2,5,1,3,6,8,9},
		{6,1,8,9,7,2,4,3,5},
		{7,8,6,2,3,5,9,1,4},
		{1,5,4,7,9,6,8,2,3},
		{2,3,9,8,4,1,5,6,7} }, position);
		
		if(temp!=null)
			printBoard(temp);
		else
			System.out.println("Could not extend board");
		
		System.out.println("\nNexted board:");
		try{
			printBoard(next(temp,position));
		}catch(NullPointerException e2){
			System.out.println("Cannot next board\n");
		}

		
	}

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("null");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) return board;
        //int r=0, c=0;
        int position[]=new int[2]; 
        int[][] attempt = extend(board,position);
        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt,position);
        }
        return null;
    }
    
    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
            int[][] board = readBoard(args[0]);
            System.out.println("Unsolved Sudoku:");
            printBoard(board);
           	System.out.println("");
            if(solve(board)!=null)
            	{
           		System.out.println("Solved Sudoku:");
           		printBoard(solve(board));
           		}
           	else
           		System.out.println("\nCould not be solved\n");
        }
    }
}