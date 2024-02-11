package io.javabrain.wordsearchapi.services;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

   private enum Direction {
      HORIZONTAL,
      VERTICAL,
      DIAGONAL,

      HORIZONTAL_INVERSE,
      VERTICAL_INVERSE,

      DIAGONAL_INVERSE


   }

   private class Coordinate {
      int x, y;

      Coordinate(int x, int y){
         this.x = x;
         this.y = y;
      }
   }

   public char[][] generateGrid(int gridSize, List<String> words){
      List<Coordinate> coordinates = new ArrayList<Coordinate>();
      char[][] contents = new char[gridSize][gridSize];
      for(int i = 0; i <gridSize; i++){
         for(int j = 0; j < gridSize; j++){
            coordinates.add(new Coordinate(i, j));
            contents[i][j] = '-';
         }
      }
      Collections.shuffle(coordinates);
      for(String word : words) {
         for(Coordinate coor: coordinates){
            int x = coor.x;
            int y = coor.y;

            Direction selectedDirection = getDirectionToFit(contents, word, coor);
            if(selectedDirection != null){
               switch (selectedDirection){
                  case HORIZONTAL:
                     for(char c : word.toCharArray()){
                        contents[x][y++] = c;
                     }
                     break;
                  case VERTICAL:
                     for(char c : word.toCharArray()){
                        contents[x++][y] = c;
                     }
                     break;
                  case DIAGONAL:
                     for(char c : word.toCharArray()){
                        contents[x++][y++] = c;
                     }
                     break;
                  case HORIZONTAL_INVERSE:
                     for(char c : word.toCharArray()){
                        contents[x][y--] = c;
                     }
                     break;
                  case VERTICAL_INVERSE:
                     for(char c : word.toCharArray()){
                        contents[x--][y] = c;
                     }
                     break;
                  case DIAGONAL_INVERSE:
                     for(char c : word.toCharArray()){
                        contents[x--][y--] = c;
                     }
                     break;
               }
               break;
            }
         }


      }
      randomFillGrid(contents);

      return contents;
   }
   private Direction getDirectionToFit(char[][] contents, String word, Coordinate cooridinate){
      List<Direction> directions = Arrays.asList(Direction.values());
      Collections.shuffle(directions);
      for(Direction dir: directions){
         if(doesFit(contents, word, cooridinate, dir))
            return dir;
      }
      return null;
   }
   private boolean doesFit(char[][] contents, String word, Coordinate cooridinate, Direction direction){
      int gridSize = contents.length;
      switch (direction){
         case HORIZONTAL:
            if(cooridinate.y + word.length() > gridSize) return false;
            for(int i =0; i< word.length(); i++){
               if(contents[cooridinate.x][cooridinate.y + i] != '-') return false;
            }
            break;
         case VERTICAL:
            if(cooridinate.x + word.length() > gridSize) return false;
            for(int i =0; i< word.length(); i++){
               if(contents[cooridinate.x + i][cooridinate.y] != '-') return false;
            }
            break;
         case DIAGONAL:
            if(cooridinate.y + word.length() > gridSize || cooridinate.x + word.length() > gridSize) return false;
            for(int i =0; i< word.length(); i++){
               if(contents[cooridinate.x + i][cooridinate.y + i] != '-') return false;
            }
            break;
         case HORIZONTAL_INVERSE:
            if(cooridinate.y < word.length()) return false;
            for(int i =0; i< word.length(); i++){
               if(contents[cooridinate.x][cooridinate.y - i] != '-') return false;
            }
            break;
         case VERTICAL_INVERSE:
            if(cooridinate.x < word.length()) return false;
            for(int i =0; i< word.length(); i++){
               if(contents[cooridinate.x - i][cooridinate.y] != '-') return false;
            }
            break;
         case DIAGONAL_INVERSE:
            if(cooridinate.y < word.length() || cooridinate.x < word.length() ) return false;
            for(int i =0; i< word.length(); i++){
               if(contents[cooridinate.x - i][cooridinate.y - i] != '-') return false;
            }
            break;
      }
      return true;
   }

   public void displayGrid(char[][] contents){
      int gridSize = contents.length;
      for(int i = 0; i < gridSize; i++){
         for(int j = 0; j < gridSize; j++){
            System.out.print(contents[i][j] + " ");
         }
         System.out.print("\n");
      }
   }

   private void randomFillGrid(char[][] contents){
      int gridSize = contents.length;
      String allCapLetters = "ABCDEFGHIJKLMNOPQQRSTUVWXYZ";
      for(int i = 0; i <gridSize; i++){
         for(int j = 0; j < gridSize; j++){
            if(contents[i][j] == '-') {
               int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
               contents[i][j] = allCapLetters.charAt(randomIndex);
            }
         }
      }
   }
}
