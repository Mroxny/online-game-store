package com.mroxny.ogs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class GameStoreController {

    @GetMapping("/getGames")
    public Game getGames(){
        return new Game();
    }
}
