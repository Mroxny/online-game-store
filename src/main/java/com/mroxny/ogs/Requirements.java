package com.mroxny.ogs;

public class Requirements {
    private int id;
    private String system;
    private String processor;
    private String memory;
    private String graphics;
    private String storage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * Makes new Requirements object based on the given line from CSV file
     * @param line line from source CSV file
     * @return new Studio object
     */
    public static Requirements getFromCSV(String line){
        String[] vals = line.split(",");
        Requirements requirements = new Requirements();

        requirements.setId(Integer.parseInt(vals[0]));
        requirements.setSystem(vals[1]);
        requirements.setProcessor(vals[2]);
        requirements.setMemory(vals[3]);
        requirements.setGraphics(vals[4]);
        requirements.setStorage(vals[5]);

        return requirements;
    }
}
