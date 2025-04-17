package business;

import dataAccess.*;
import model.Customer;
import java.util.ArrayList;

public class CustomerManager {
    private static ICustomerDAO dao;

    static {
        dao = new CustomerDBAccess();
    }

    public static void add(Customer customer) throws InsertionFailedException, DAORetrievalFailedException {
        dao.create(customer);
    }

    public static void remove(int loyaltyCardNumber) throws DeleteFailedException, DAORetrievalFailedException {
        dao.deleteByLoyaltyCardNumber(loyaltyCardNumber);
    }

    public static Customer getByLoyaltyCardNumber(int loyaltyCardNumber) throws NotFoundException, DAORetrievalFailedException {
        return dao.findByLoyaltyCardNumber(loyaltyCardNumber);
    }

    public static void update(Customer customer) throws UpdateFailedException, DAORetrievalFailedException {
        dao.edit(customer);
    }
    
    public static ArrayList<Customer> getAll() throws DAORetrievalFailedException {
        return dao.findAll();
    }
}
