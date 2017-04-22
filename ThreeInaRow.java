/**
 * ThreeInaRow.java
 * Programming Assignment 2     
 * @author Sanjiv Karki
 * Due Date: April 13, 2017
 */
import java.util.*;
import java.io.*;

/**
 * This ThreeInaRow class uses arraylist, array, 2D array and private methods that uses backtracking algorithm to
 * solve 3 in a row puzzle.
 */
public class ThreeInaRow{

   /**
    * Initializes arraylist, array, 2D array and other necessary variables.
    */
   public int orderOfMatrix;
   public String filename;
   ArrayList<String> arrList = new ArrayList<String>();
   public String [][] matArr;
   public int [] bRnum;
   public int [] bCnum;
   public boolean flag;
   public int rowCounter=0;
   public int colCounter=0;
   public int bRow;
   public int bCol;
   
   /**
    * Constructor to create N*N puzzle with the given N size
    * @param filename the name of the file
    * @param N the order of the matrix
    */
   public ThreeInaRow(String filename, int N){
      orderOfMatrix = N;
      this.filename = filename;
      matArr = new String [getOrder()] [getOrder()];
      bRnum = new int[getOrder()];
      bCnum = new int[getOrder()];
      fileHandling();
      flag = backtrack();
      
   }
   
   /**
    * This method returns the solution as a String representing the contents of the grid in row-major order.
    * @return Returns string in row-major order.
    */

   public String solution(){
      if(flag){
         String str = "|";
         for(int x=0;x<getOrder();x++){
            for(int y=0;y<getOrder();y++){
               str = str + matArr[x][y];
            }
            str = str +"|";
         }
         return str;
      }
      else{
         return "NONE" ;
      }
   }
   
   /**
    * This method returns the solution as a two dimensional grid in properly formatted order
    * @return Returns string in properly formatted order.
    */

   public String toString(){
      String str = "";
      System.out.println("Solution of 3-in-a-row puzzle of order "+getOrder()+" in two dimension order is given below; \n");
      for(int i=0; i<getOrder(); i++){
         for(int j=0; j<getOrder(); j++){
            str += matArr[i][j];
         }
         str +="\n";
      }
         return str;
     }

   /**
    * This method returns the name of the file.
    * @returns return the file name.
    */
   private String getFile(){
      return this.filename;
   }
   
   /**
    * This method returns the order of the square matrix.
    * @return returns the order of the square matrix.
    */
   private int getOrder(){
      return orderOfMatrix;
   }
   
   /**
    * This method reads from the file and writes to the two dimensional matrix.
    */
   private void fileHandling(){
      try{
         File input = new File(getFile());
         Scanner in = new Scanner (input);
         
         while(in.hasNextLine()){
            arrList.add(in.nextLine());
         }
         in.close();
         
         for(int i=0; i<getOrder(); i++){
            for(int j=0; j<getOrder(); j++){
               matArr[i][j]= Character.toString((arrList.get(i)).charAt(j)); //converts character to String and place it in matrix
            }
            
         }
      }
      catch (FileNotFoundException e){
         System.out.println("Text file does not exist.");
      }
   }
   
   /**
    * Private class that helps to create abstraction for array indices.
    */  
   private class Point{
      int x;
      int y;
      public Point(int a, int b){
         x=a;
         y=b;
      }
      public int getX(){
         return x;
         }
      public int getY(){
         return y;
         }
      }
   /**
    * This method is the main algorithm of this program where promising method is determined, location from
    * where algorithm starts is evaluated and finally backtracking algorithm is completed and returns true 
    * if solution is found and false otherwise.
    * @return Returns a boolean.
    */
    private boolean backtrack(){
   
      if(endOfGame()){
         return true;
         }
      else{
         String Color;
         Point point = currentLoc();
         for(int x=0;x<2;x++){
         if(x==0){
            Color="B";
            }
         else{
            Color="W";
            }
         if (promising(point.getX(),point.getY(),Color)){
         matArr[point.getX()][point.getY()]=Color;
         if(endOfGame()){
         return true;
         }
         backtrack();
         if(endOfGame())
         {
            return true;
         }
         matArr[point.getX()][point.getY()]=" ";
         }
         }
      }
      return false;
      }
      
  
      /**
       * This method iterates through the whole two dimensional matrix and if blank space is found 
       * that location returned 
       * @return returns the index of that location
       */
      private Point currentLoc(){
      for(int x=0;x<getOrder();x++){
         for(int y=0;y<getOrder();y++){
            if(matArr[x][y].equals(" ")){
               Point point = new Point(x,y);
               return point;
            }
         }
      }
      return new Point(0,0);
      }
      
    /**
     * This private method determines the end of the puzzle and returns true if end of the puzzle is reached without
     * enounter of blank space.
     * @return returns a boolean.
     */

      private boolean endOfGame(){
      for(int x=0;x<getOrder();x++){
         for(int y=0;y<getOrder();y++){
            if(matArr[x][y].equals(" ")){
               return false;}
            }
         }
         return true;
      }


   /**
    * This method checks whether it is promising to place particular color and returns true if it is promising
    * or false otherwise.
    * @param row the row size of the matrix
    * @param col the column size of the matrix
    * @param Color the color of the square 
    * @return Returns a boolean.
    */
   private boolean promising(int row, int col, String Color){
      return divide(row,col,Color)&&leftCheck(row,col,Color)&&rightCheck(row,col,Color)&&upCheck(row,col,Color)&&downCheck(row,col,Color)&& middleCheck(row,col,Color) && midUpCheck(row,col,Color);   
   
   }
   
  /**
    * This method checks whether the left two rows are of same color or not and returns false if they are of same
    * color or true otherwise.
    * @param row the row size of matrix
    * @param col the column size of matrix
    * @return Returns a boolean.
    */
   private boolean leftCheck(int row, int col,String Color){
   int i=row;
   int j=col;   
   if( j-1 >= 0 && j-2 >= 0 ){
      if( (matArr[i][j-1]).equals(Color) && (matArr[i][j-2]).equals(Color) )
         return false;
      }
        return true; 
   }
   
   /**
    * This method checks whether the right two rows are of same color or not and returns false if they are of same
    * color or true otherwise.
    * @param row the row size of matrix
    * @param col the column size of matrix
    * @return Returns a boolean.
    */

   private boolean rightCheck(int row, int col, String Color){
   int i=row;
   int j=col;
   if( j+1 < getOrder()-1 && j+2 < getOrder()-1 ){
      if( (matArr[i][j+1]).equals(Color) && (matArr[i][j+2]).equals(Color) )
         return false;
   }
        return true; 
   }
   
   /**
    * This method checks whether the up two columns are of same color or not and returns false if they are of same
    * color or true otherwise.
    * @param row the row size of matrix
    * @param col the column size of matrix
    * @param Color the color of the square 
    * @return Returns a boolean.
    */

   private boolean upCheck(int row, int col, String Color){
   int i=row;
   int j=col;
   if( i-1 >=0 && i-2 >= 0){
      if( (matArr[i-1][j]).equals(Color) && (matArr[i-2][j]).equals(Color) )
         return false;
   }
      return true;
   }
   
   /**
    * This method checks whether the down two columns are of same color or not and returns false if they are of same
    * color or true otherwise.
    * @param row the row size of matrix
    * @param col the column size of matrix
    * @param Color the color of the square
    * @return Returns a boolean.
    */

   private boolean downCheck(int row, int col, String Color){
   int i=row;
   int j=col;
   if( i+1 < getOrder()-1 && i+2< getOrder()- 1){
      if( (matArr[i+1][j]).equals(Color) && (matArr[i+2][j]).equals(Color) )
         return false;
   }
      return true;
   }
   
   /**
    * This method checks whether the left and right rows are of same color or not and returns false if they are of same
    * color or true otherwise.
    * @param row the row size of matrix
    * @param col the column size of matrix
    * @param Color the color of the square
    * @return Returns a boolean.
    */
   private boolean middleCheck(int row, int col, String Color){
   int i=row;
   int j=col;
   
   if( j-1 >= 0 && j+1 <= getOrder()-1){
      if( (matArr[i][j-1]).equals(Color) && (matArr[i][j+1]).equals(Color) )
         return false;
   }
      return true;

   }
   
    /**
    * This method checks whether the up column and down column are of same color or not and returns false if they are of same
    * color or true otherwise.
    * @param row the row size of matrix
    * @param col the column size of matrix
    * @param Color the color of the square
    * @return Returns a boolean.
    */

   private boolean midUpCheck(int row, int col, String Color){
      int i=row;
      int j=col;
      
      if( i-1 >=0 && i+1 <= getOrder()-1){
         if( (matArr[i-1][j]).equals(Color) && (matArr[i+1][j]).equals(Color))
            return false;
      }
      return true;
   }
   /**
    * This private method counts the number of same color in the row.
    * @param row the row size of matrix.
    * @param Color the color of the square
    * @return returns the no of same color in the row
    */
   private int rowCount(int row,String Color){
      bRow=0;
      for(int j=0; j<getOrder(); j++){
         if ((matArr[row][j]).equals(Color)){
            bRow++;
        }
            
      }
      return bRow;
   }
   
   /**
    * This private method counts the number of same color in the column.
    * @param col the column size of matrix.
    * @param Color the color of the square
    * @return returns the no of same color in the column
    */

   private int colCount(int col,String Color){
      bCol=0;
      for(int j=0; j<getOrder(); j++){
         if ((matArr[j][col]).equals(Color)){
            bCol++;
         }
      }
      return bCol;

   }
   
  /**
    * This private method determines whether the number of same color in the row and column is less than half of the size of matrix and returns true or false otherwise.
    * @param row the row size of matrix.
    * @param col the column size of matrix
    * @param Color the color of the square
    * @return returns a boolean.
    */

   private boolean divide(int row, int col,String Color){
      if(colCount(col,Color)>=getOrder()/2){
         return false;
         }
       if(rowCount(row,Color)>=getOrder()/2){
         return false;
         }
         return true;
   
      }          
}