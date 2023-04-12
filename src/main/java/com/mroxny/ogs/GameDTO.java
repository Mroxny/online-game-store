package com.mroxny.ogs;

import java.util.List;

public class GameDTO {
    private String name;
    private String cover;
    private String smallDesc;
    private String bigDesc;
    private String premiere;
    private float price;
    private int studio;
    private int requirements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSmallDesc() {
        return smallDesc;
    }

    public void setSmallDesc(String smallDesc) {
        this.smallDesc = smallDesc;
    }

    public String getBigDesc() {
        return bigDesc;
    }

    public void setBigDesc(String bigDesc) {
        this.bigDesc = bigDesc;
    }

    public String getPremiere() {
        return premiere;
    }

    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStudio() {
        return studio;
    }

    public void setStudio(int studio) {
        this.studio = studio;
    }

    public int getRequirements() {
        return requirements;
    }

    public void setRequirements(int requirements) {
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        return name+","+cover+","+smallDesc+","+bigDesc+","+premiere+","+price+","+studio+","+requirements;
    }
}
