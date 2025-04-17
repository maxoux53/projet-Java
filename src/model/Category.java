package model;

public class Category {
    private Integer id;
    private String label;

    public Category(Integer id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }
}
