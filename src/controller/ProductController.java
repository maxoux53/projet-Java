package controller;

import business.ProductManager;
import exceptions.*;
import model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ProductController {
    private ProductManager manager;

    public ProductController() {
        setManager(new ProductManager());
    }

    public void setManager(ProductManager manager) {
        this.manager = manager;
    }

    public void create(Product product) throws InsertionFailedException, DAORetrievalFailedException {
        manager.create(product);
    }

    public int update(Product product) throws UpdateFailedException, DAORetrievalFailedException {
        return manager.update(product);
    }

    public int remove(Long barcode) throws DeleteFailedException, WrongTypeException, FieldIsEmptyException, DAORetrievalFailedException {
        return manager.remove(barcode);
    }

    public Product getByBarcode(Long barcode) throws DAORetrievalFailedException, NotFoundException, WrongTypeException, FieldIsEmptyException {
        return manager.getByBarcode(barcode);
    }
    
    public Category getCategoryById(Integer categoryId) throws DAORetrievalFailedException, NotFoundException {
        return manager.getCategoryById(categoryId);
    }
    
    public Brand getBrandById(Integer brandId) throws DAORetrievalFailedException, NotFoundException {
        return manager.getBrandById(brandId);
    }

    public int getOrCreateBrand(String name) throws DAORetrievalFailedException {
        return manager.getOrCreateBrand(name);
    }

    public ArrayList<Category> getAllCategories() throws DAORetrievalFailedException {
        return manager.getAllCategories();
    }

    public ArrayList<Vat> getAllVats() throws DAORetrievalFailedException {
        return manager.getAllVats();
    }

    public int getCurrentStock(Long barcode) throws NotFoundException, DAORetrievalFailedException, WrongTypeException, FieldIsEmptyException {
        return manager.getCurrentStock(barcode);
    }

    public ArrayList<Product> getOutOfStock() throws NotFoundException, DAORetrievalFailedException {
        return manager.getOutOfStock();
    }
    
    public ArrayList<Product> getAll() throws DAORetrievalFailedException {
        return manager.getAll();
    }
}
