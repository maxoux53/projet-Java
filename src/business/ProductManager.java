package business;

import dataAccess.*;
import model.Category;
import model.Product;
import model.Vat;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProductManager {                           // static?
    private static IProductDAO productDataAccess;

    static {
        productDataAccess = new ProductDBAccess();
    }

    public static void add(Product product) throws InsertionFailedException, DAORetrievalFailedException {
        productDataAccess.create(product);
    }

    public static void remove(int barcode) throws DeleteFailedException, DAORetrievalFailedException {
        productDataAccess.deleteByBarcode(barcode);                                          // todo: handle returned int
    }

    public static Product getByBarcode(int barcode) throws NotFoundException, DAORetrievalFailedException {
        return productDataAccess.findByBarcode(barcode);
    }

    public static ArrayList<Product> getAll() throws DAORetrievalFailedException {
        return productDataAccess.findAll();
    }

    public static void update(Product product) throws DAORetrievalFailedException, UpdateFailedException {
        productDataAccess.edit(product);
    }

    public static int getCurrentStock(int barcode) throws NotFoundException, DAORetrievalFailedException {
        return productDataAccess.currentStock(barcode);
    }

    public static ArrayList<Integer> getOutOfStock() throws NotFoundException, DAORetrievalFailedException {
        return productDataAccess.outOfStock();
    }

    public static ArrayList<Product> searchByName(String name) throws NotFoundException, DAORetrievalFailedException {
        return productDataAccess.findByName(name);
    }

    public static ArrayList<Category> getAllCategories() throws DAORetrievalFailedException {
        return productDataAccess.getAllCategories();
    }

    public static ArrayList<Vat> getAllVats() throws DAORetrievalFailedException {
        return productDataAccess.getAllVatTypes();
    }

    public static int setBrand(String name) throws DAORetrievalFailedException {
        return productDataAccess.brand(name);
    }
}
