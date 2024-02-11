package io.javabrain.wordsearchapi.controllers;

import io.javabrain.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

@RestController("/")
public class WordSearchController {

   @Autowired
   WordGridService wordGridService;

   @GetMapping("/wordgrid")
   public String createWordGrid(@RequestParam int gridSize,@RequestParam String words){

      List<String> wordsList = Arrays.asList(words.split(","));

      char[][] grid = wordGridService.generateGrid(gridSize, wordsList);

      String gridToString = "";
      for(int i = 0; i <gridSize; i++){
         for(int j = 0; j < gridSize; j++){
            gridToString += grid[i][j] + " ";
         }
         gridToString += "\r\n";
      }

      return gridToString;

   }


}
