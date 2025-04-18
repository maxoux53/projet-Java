package controller;

import model.Brand;
import model.Category;
import model.Product;
import model.Vat;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ApplicationController {
    // Static Methods
    
    // Getters
    public static ArrayList<Vat> getVats() { // Will change
        ArrayList<Vat> vats = new ArrayList<Vat>();
        vats.add(new Vat('A', 21));
        vats.add(new Vat('B', 12));
        vats.add(new Vat('C', 6));
        vats.add(new Vat('D', 0));
        
        return vats;
    }
    
    public static ArrayList<Category> getCategories() { // Will change
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(new Category(1, "Électronique"));
        categories.add(new Category(2, "Alimentation"));
        categories.add(new Category(3, "Vêtements"));
        categories.add(new Category(4, "Jardinage"));
        categories.add(new Category(5, "Librairie"));
        
        return categories;
    }
    
    public static ArrayList<Brand> getBrands() {
        ArrayList<Brand> brands = new ArrayList<Brand>();
        brands.add(new Brand(1, "Nike"));
        brands.add(new Brand(2, "Boni"));
        brands.add(new Brand(3, "Moulinex"));
        brands.add(new Brand(4, "Stihl"));
        brands.add(new Brand(5, "Spinelle"));

        return brands;
    }
    
    public static Product getProductByBarcode(int barcode) {
        // return méthodes pour récupérer un produit
        Product product = new Product(12345);
        product.setName("Chocolat");
        product.setDescription("C'est du chocolat");
        product.setAmount(3);
        product.setVatType('A');
        product.setCategoryId(2);
        product.setBrandId(2);
        product.setAvailable(true);
        
        return product;
    }
    
    // Verifications
    public static void productIsValid(String name, String description, String stringPrice, int amount, String vat, String categoryName, String brand, String stringDay, String stringMonth, String stringYear, boolean available) throws FieldIsEmpty, WrongType, WrongDate {
        double price;
        int day;
        int month;
        int year;
        
        if (name.isEmpty()) {
            throw new FieldIsEmpty("Nom");
        }
        
        try {
            price = Double.parseDouble(stringPrice);
        } catch (NumberFormatException numberFormatException) {
            throw new WrongType("Prix");
        }
        if (price < 0) throw new WrongType("Prix");

        try {
            day = Integer.parseInt(stringDay);
        } catch (NumberFormatException numberFormatException) {
            throw new WrongType("Jour");
        }

        try {
            month = Integer.parseInt(stringMonth);
        } catch (NumberFormatException numberFormatException) {
            throw new WrongType("Mois");
        }

        try {
            year = Integer.parseInt(stringYear);
        } catch (NumberFormatException numberFormatException) {
            throw new WrongType("Année");
        }
        
        try {
            LocalDate date = LocalDate.of(year, month, day);
        }
        catch (DateTimeException dateTimeException) {
            throw new WrongDate(null);
        }
        
        Character vatType = vat.charAt(0);
        
        ArrayList<Category> categories = getCategories();
        int iCategory = 0;
        while (!Objects.equals(categories.get(iCategory).getName(), categoryName)) {
            iCategory++;
        }
        
        Integer categoryId = categories.get(iCategory).getId();
        
        if (brand.isEmpty()) {
            throw new FieldIsEmpty("Marque");
        }

        //Product product = new Product()
    }
    
}
