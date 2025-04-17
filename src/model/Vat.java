package model;

public class Vat {
    private Character type;
    private Integer rate;

    public Vat(Character type) {
        this.type = type;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
