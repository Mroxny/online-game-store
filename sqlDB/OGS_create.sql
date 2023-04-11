-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2023-04-10 23:58:15.246

-- tables
-- Table: Games
CREATE TABLE Games (
    IdGame int  NOT NULL,
    Name varchar(100)  NOT NULL,
    Cover varchar(300)  NOT NULL,
    Small_desc varchar(500)  NOT NULL,
    Big_desc varchar(5000)  NOT NULL,
    Premiere date  NOT NULL,
    Price float(100,2)  NOT NULL,
    IdStudio int  NOT NULL,
    IdRequirement int  NOT NULL,
    CONSTRAINT Games_pk PRIMARY KEY (IdGame)
);

-- Table: Games_Genres
CREATE TABLE Games_Genres (
    Games_IdGame int  NOT NULL,
    Genres_IdGenre int  NOT NULL,
    CONSTRAINT Games_Genres_pk PRIMARY KEY (Games_IdGame,Genres_IdGenre)
);

-- Table: Games_Tags
CREATE TABLE Games_Tags (
    Games_IdGame int  NOT NULL,
    Tags_IdTag int  NOT NULL,
    CONSTRAINT Games_Tags_pk PRIMARY KEY (Games_IdGame,Tags_IdTag)
);

-- Table: Genres
CREATE TABLE Genres (
    IdGenre int  NOT NULL,
    Name varchar(50)  NOT NULL,
    CONSTRAINT Genres_pk PRIMARY KEY (IdGenre)
);

-- Table: Photos
CREATE TABLE Photos (
    IdPhoto int  NOT NULL,
    Path varchar(300)  NOT NULL,
    IdGame int  NOT NULL,
    CONSTRAINT Photos_pk PRIMARY KEY (IdPhoto)
);

-- Table: Requirements
CREATE TABLE Requirements (
    IdRequirement int  NOT NULL,
    System varchar(30)  NOT NULL,
    Processor varchar(50)  NOT NULL,
    Memory varchar(30)  NOT NULL,
    Graphics varchar(50)  NOT NULL,
    Storage varchar(30)  NOT NULL,
    CONSTRAINT Requirements_pk PRIMARY KEY (IdRequirement)
);

-- Table: Studios
CREATE TABLE Studios (
    IdStudio int  NOT NULL,
    Name varchar(100)  NOT NULL,
    Country varchar(50)  NOT NULL,
    CONSTRAINT Studios_pk PRIMARY KEY (IdStudio)
);

-- Table: Tags
CREATE TABLE Tags (
    IdTag int  NOT NULL,
    Name int  NOT NULL,
    CONSTRAINT Tags_pk PRIMARY KEY (IdTag)
);

-- Table: Trailers
CREATE TABLE Trailers (
    IdTrailer int  NOT NULL,
    path varchar(300)  NOT NULL,
    IdGame int  NOT NULL,
    CONSTRAINT Trailers_pk PRIMARY KEY (IdTrailer)
);

-- foreign keys
-- Reference: Games_Genres_Games (table: Games_Genres)
ALTER TABLE Games_Genres ADD CONSTRAINT Games_Genres_Games FOREIGN KEY Games_Genres_Games (Games_IdGame)
    REFERENCES Games (IdGame);

-- Reference: Games_Genres_Genres (table: Games_Genres)
ALTER TABLE Games_Genres ADD CONSTRAINT Games_Genres_Genres FOREIGN KEY Games_Genres_Genres (Genres_IdGenre)
    REFERENCES Genres (IdGenre);

-- Reference: Games_Requirements (table: Games)
ALTER TABLE Games ADD CONSTRAINT Games_Requirements FOREIGN KEY Games_Requirements (IdRequirement)
    REFERENCES Requirements (IdRequirement);

-- Reference: Games_Studios (table: Games)
ALTER TABLE Games ADD CONSTRAINT Games_Studios FOREIGN KEY Games_Studios (IdStudio)
    REFERENCES Studios (IdStudio);

-- Reference: Games_Tags_Games (table: Games_Tags)
ALTER TABLE Games_Tags ADD CONSTRAINT Games_Tags_Games FOREIGN KEY Games_Tags_Games (Games_IdGame)
    REFERENCES Games (IdGame);

-- Reference: Games_Tags_Tags (table: Games_Tags)
ALTER TABLE Games_Tags ADD CONSTRAINT Games_Tags_Tags FOREIGN KEY Games_Tags_Tags (Tags_IdTag)
    REFERENCES Tags (IdTag);

-- Reference: Photos_Games (table: Photos)
ALTER TABLE Photos ADD CONSTRAINT Photos_Games FOREIGN KEY Photos_Games (IdGame)
    REFERENCES Games (IdGame);

-- Reference: Trailers_Games (table: Trailers)
ALTER TABLE Trailers ADD CONSTRAINT Trailers_Games FOREIGN KEY Trailers_Games (IdGame)
    REFERENCES Games (IdGame);

-- End of file.

