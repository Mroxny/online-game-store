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
        ResultDTO<List<Game>> resultDTO = service.getAllGames("name");
        assertEquals(HttpStatus.OK, resultDTO.getCode());
        assertEquals(10, resultDTO.getContent().size());
    }

    @Test
    public void testGetGameById() {
        StoreCSVService service = new StoreCSVService();
        ResultDTO<Game> resultDTO = service.getGameById(1);

        List<String> lines = service.readCSV("./csvDB/games.csv");
        String testName = lines.get(0).split(",")[1];

        assertEquals(HttpStatus.OK, resultDTO.getCode());
        assertEquals(testName, resultDTO.getContent().getName());
    }

    @Test
    public void testGetGamesByName() {
        StoreCSVService service = new StoreCSVService();
        ResultDTO<List<Game>> resultDTO = service.getGamesByName("Cyberpunk2077", "name");

        assertEquals(HttpStatus.OK, resultDTO.getCode());
        assertEquals(1, resultDTO.getContent().size());
        assertEquals("Cyberpunk2077", resultDTO.getContent().get(0).getName());
    }

    @Test
    public void testGetGamesByGenre() {
        StoreCSVService service = new StoreCSVService();
        ResultDTO<List<Game>> resultDTO = service.getGamesByGenre("RPG", "name");

        assertEquals(HttpStatus.OK, resultDTO.getCode());
        assertEquals(4, resultDTO.getContent().size());
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


        ResultDTO<String> resultDTO = service.insertGame(gameDTO);
        assertEquals(HttpStatus.OK, resultDTO.getCode());

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

        ResultDTO<String> resultDTO = service.updateGame(1,gameDTO);
        assertEquals(HttpStatus.OK, resultDTO.getCode());

        String gameName = service.getGameById(1).getContent().getName();

        assertEquals("GameNew", gameName);
    }

    @Test
    public void testDeleteGame() {
        StoreCSVService service = new StoreCSVService();

        int indexToDelete = 1;
        String gameName = service.getGameById(indexToDelete).getContent().getName();

        ResultDTO<String> resultDTO = service.deleteGame(indexToDelete);
        assertEquals(HttpStatus.OK, resultDTO.getCode());

        List<String> lines = service.readCSV("./csvDB/games.csv");
        assertTrue(lines.stream().anyMatch(l -> !l.contains(gameName)));
    }





}
