package com.mroxny.ogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/store")
public class GameStoreController {

    private StoreDBInterface dbManager;

    @Autowired
    public GameStoreController(StoreDBInterface storeDBInterface){
        this.dbManager = storeDBInterface;
    }

    @GetMapping("/games")
    public ResponseEntity<Object> getAllGames(@RequestParam(required = false) String order, Pageable pageable){
        //can't find any classes responsible for pages, so I'll just leave it here

        if(order == null){
            order = "name";
        }
        ResponseDTO response = dbManager.getAllGames(order);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }

        return ResponseEntity.ok(response.getContent());
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<Object> getGameById(@PathVariable int id){
        ResponseDTO response = dbManager.getGameById(id);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent().get(0));
    }

    @GetMapping("/games/{genre}")
    public ResponseEntity<Object> getGamesByGenre(@PathVariable String genre, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResponseDTO response = dbManager.getGamesByGenre(genre, order);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @GetMapping("/findGame")
    public ResponseEntity<Object> findGames(@RequestParam String query, @RequestParam(required = false) String order){
        if(order == null){
            order = "name";
        }
        ResponseDTO response = dbManager.getGamesByName(query, order);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @PostMapping("/insertGame")
    public ResponseEntity<Object> insertGame(@RequestParam GameDTO game){
        ResponseDTO response = dbManager.insertGame(game);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @PutMapping("/updateGame/{id}")
    public ResponseEntity<Object> updateGame(@PathVariable int id, @RequestParam GameDTO game){
        ResponseDTO response = dbManager.updateGame(id,game);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }

    @DeleteMapping("/deleteGame/{id}")
    public ResponseEntity<Object> deleteGame(@PathVariable int id){
        ResponseDTO response = dbManager.deleteGame(id);
        if (response.getCode() != HttpStatus.OK) {
            return new ResponseEntity<>(response.getMessage(), response.getCode());
        }
        return ResponseEntity.ok(response.getContent());
    }
}
