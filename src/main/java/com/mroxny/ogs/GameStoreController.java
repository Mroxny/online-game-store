package com.mroxny.ogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/store")
public class GameStoreController {

    private StoreDBInterface dbManager;

    @Autowired
    public GameStoreController(StoreDBInterface storeDBInterface){
        this.dbManager = storeDBInterface;
    }

    @GetMapping("/games")
    public ResponseEntity<Object> getAllGames(@RequestParam(required = false) String order){
        //TODO: add paging system
        //can't find any classes responsible for pages, so I'll just leave it here

        if(order == null){
            order = "name";
        }
        ResultDTO<List<Game>> result = dbManager.getAllGames(order);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }

        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Object> getGameById(@PathVariable int id){
        ResultDTO<Game> result = dbManager.getGameById(id);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/games/{genre}")
    public ResponseEntity<Object> getGamesByGenre(@PathVariable String genre, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResultDTO<List<Game>> result = dbManager.getGamesByGenre(genre, order);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());
    }

    @GetMapping("/findGame")
    public ResponseEntity<Object> findGames(@RequestParam String query, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResultDTO<List<Game>> result = dbManager.getGamesByName(query, order);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());
    }

    @PostMapping("/insertGame")
    public ResponseEntity<Object> insertGame(@RequestParam GameDTO game, @RequestParam(required = false) String apiKey){
        ResultDTO<String> result = dbManager.insertGame(game);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());
    }

    @PostMapping("/insertStudio")
    public ResponseEntity<Object> insertStudio(@RequestParam StudioDTO studio, @RequestParam(required = false) String apiKey){
        ResultDTO<String> result = dbManager.insertStudio(studio);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());
    }

    @PostMapping("/insertRequirements")
    public ResponseEntity<Object> insertStudio(@RequestParam RequirementsDTO requirements, @RequestParam(required = false) String apiKey){
        ResultDTO<String> result = dbManager.insertRequirements(requirements);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());
    }

    @PutMapping("/updateGame/{id}")
    public ResponseEntity<Object> updateGame(@PathVariable int id, @RequestParam GameDTO game, @RequestParam(required = false) String apiKey){
        ResultDTO<String> result = dbManager.updateGame(id,game);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());

    }

    @DeleteMapping("/deleteGame/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable int id, @RequestParam(required = false) String apiKey){
        ResultDTO<String> result = dbManager.deleteGame(id);
        if (result.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(result.getMessage(), result.getCode());
        }
        return ResponseEntity.ok(result.getContent());

    }
}
