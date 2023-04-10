package com.mroxny.ogs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameStoreController {

    @GetMapping("/getGames")
    public Game getGames(){
        return new Game();
    }
}
