package model;

public class ProductInformations {
    // Attributes
    private String productName, categoryName;
    private Integer vatRate;
    
    // Constructors
    public ProductInformations(String productName, String categoryName, Integer vatRate) {
        this.productName = productName;
        this.categoryName = categoryName;
        this.vatRate = vatRate;
    }
    
    // Getters
    public String getProductName() {
        return productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getVatRate() {
        return vatRate;
    }
}
