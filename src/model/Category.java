package model;

public class Category {
    private Integer id;
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters
    public String getName() {
        return name;
    }
}
