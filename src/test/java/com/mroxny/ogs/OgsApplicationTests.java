package com.mroxny.ogs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class OgsApplicationTests {

    @Test
    public void testGetAllGames() {
        StoreCSVService service = new StoreCSVService();
        ResponseDTO responseDTO = service.getAllGames("name");
        assertEquals(HttpStatus.OK, responseDTO.getCode());
        assertEquals(10, responseDTO.getContent().size());
    }

    @Test
    public void testGetGameById() {
        StoreCSVService service = new StoreCSVService();
        ResponseDTO responseDTO = service.getGameById(1);
        assertEquals(HttpStatus.OK, responseDTO.getCode());
        assertEquals("Cyberpunk2077", responseDTO.getContent().get(0).getName());
    }

    @Test
    public void testGetGamesByName() {
        StoreCSVService service = new StoreCSVService();
        ResponseDTO responseDTO = service.getGamesByName("Cyberpunk2077", "name");
        assertEquals(HttpStatus.OK, responseDTO.getCode());
        assertEquals(1, responseDTO.getContent().size());
        assertEquals("Cyberpunk2077", responseDTO.getContent().get(0).getName());
    }

    @Test
    public void testGetGamesByGenre() {
        StoreCSVService service = new StoreCSVService();
        ResponseDTO responseDTO = service.getGamesByGenre("RPG", "name");
        assertEquals(HttpStatus.OK, responseDTO.getCode());
        assertEquals(4, responseDTO.getContent().size());
    }

    @Test
    public void testInsertGame() {
        StoreCSVService service = new StoreCSVService();
        GameDTO gameDTO = new GameDTO();

        gameDTO.setName("NewGame");
        gameDTO.setCover("cover.jpg");
        gameDTO.setSmallDesc("small");
        gameDTO.setBigDesc("BIG");
        gameDTO.setPremiere("2022-01-01");
        gameDTO.setPrice(19.99f);
        gameDTO.setStudio(1);
        gameDTO.setRequirements(2);


        ResponseDTO responseDTO = service.insertGame(gameDTO);
        assertEquals(HttpStatus.OK, responseDTO.getCode());

        List<String> lines = service.readCSV("./csvDB/games.csv");
        assertTrue(lines.stream().anyMatch(l -> l.contains("NewGame")));
    }

    @Test
    public void testUpdateGame() {
        StoreCSVService service = new StoreCSVService();
        GameDTO gameDTO = new GameDTO();

        gameDTO.setName("GameNew");
        gameDTO.setCover("cover2.jpg");
        gameDTO.setSmallDesc("small");
        gameDTO.setBigDesc("BIG");
        gameDTO.setPremiere("2022-01-01");
        gameDTO.setPrice(199.99f);
        gameDTO.setStudio(1);
        gameDTO.setRequirements(3);

        ResponseDTO responseDTO = service.updateGame(1,gameDTO);
        assertEquals(HttpStatus.OK, responseDTO.getCode());

        String gameName = service.getGameById(1).getContent().get(0).getName();

        assertEquals("GameNew", gameName);
    }

    @Test
    public void testDeleteGame() {
        StoreCSVService service = new StoreCSVService();

        int indexToDelete = 1;
        String gameName = service.getGameById(indexToDelete).getContent().get(0).getName();

        ResponseDTO responseDTO = service.deleteGame(indexToDelete);
        assertEquals(HttpStatus.OK, responseDTO.getCode());

        List<String> lines = service.readCSV("./csvDB/games.csv");
        assertTrue(lines.stream().anyMatch(l -> !l.contains(gameName)));
    }





}
