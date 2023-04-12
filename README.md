# online-game-store
Small online store backend APi using Spring Boot

Description
----

This repo is the backend for the web application of the online game store. Users can browse and sort games in various ways. The application has the ability to manage individual games by administrators by entering the game id. There are tests available in the repository to check the functionality of the service

How to use
----
The application contains test data that helps you understand how the application works. Here are examples of available functionality:

* `/store/games?order=Name` - Returns all games available in the database. The order parameter is optional. You can sort by *name*, *price* and *date*. You can also reverse the sort order by adding *:desc* to the value.
* `/store/game/{id}` - Returns the game by given id
* `/store/games/{genre}?order=Name` Returns games based on the given genre
* `/store/findGame?order=Name` Returns games based on the given name
* `/store/insertGame?game={}&apiKey=""` Inserts the given game into the database and automatically assigns a new Id. (Optional for now) Contains a method of verification by providing an Api key
* `/store/insertStudio?studio={}&apiKey=""` Inserts the given studio into the database and automatically assigns a new Id.
* `/store/insertRequirements?requirements={}&apiKey=""` Inserts the given requirements into the database and automatically assigns a new Id.
* `/store/updateGame?id=1&game={}&apiKey=""` Updates a game with given Id.
* `/store/updateGame?id=1&apiKey=""` Deletes a game with given Id.

Database
----
As mentioned, the project comes with a sample database that contains CSV files. Service is prepared to work with CSV files, but in the future ability to connect to any database can be easily added (thanks to the implementation of the interface). An example of MySQL script with a table diagram has been attached to the project:

![](https://raw.githubusercontent.com/Mroxny/online-game-store/main/sqlDB/DB%20model.png "Example diagram")

Assumptions
----
* The store's in a beautiful dimension where user data is not collected in any way
* Functionality should not be tested on empty data
* Games cannot be fully edited by admins (like genres, tags and photos), this may be easly implemented in the future though
* The method of authorizing admins is only a suggestion. This is not fully implemented yet



Summary
----
Working on this project was pure pleasure. I tried to make the functions clear and easy to understand. I have also included descriptions for more complex functions. My only concern is the use of CSV files as a database. My goal was to make the database mobile and easy to use. Unfortunately, it turned out to be more difficult than I thought and it took longer to implement than I expected. As a result, some of the program's functions were cut. Nevertheless, I believe that the main assumptions of the task have been met.

*Cheers*</br>
*Mroxny*
