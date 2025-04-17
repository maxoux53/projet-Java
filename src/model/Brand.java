package model;

public class Brand {
    private Integer id;
    private String name;

    public Brand(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }
}
