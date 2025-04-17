package dataAccess;

import model.Customer;
import java.util.ArrayList;

public interface ICustomerDAO {
    int create(Customer customer) throws InsertionFailedException, DAORetrievalFailedException;
    int edit(Customer customer) throws UpdateFailedException, DAORetrievalFailedException;
    Customer findByLoyaltyCardNumber(int loyaltyCardNumber) throws NotFoundException, DAORetrievalFailedException;
    void deleteByLoyaltyCardNumber(int loyaltyCardNumber) throws DeleteFailedException, DAORetrievalFailedException;
    ArrayList<Customer> findAll() throws DAORetrievalFailedException;
}
