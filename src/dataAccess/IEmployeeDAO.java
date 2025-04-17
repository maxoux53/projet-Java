package dataAccess;

import model.City;
import model.Employee;
import java.util.ArrayList;

public interface IEmployeeDAO {
    int create(Employee employee, City city) throws InsertionFailedException, DAORetrievalFailedException; // void? boolean? Employee? with exception?
    void deleteById(int id) throws DeleteFailedException, DAORetrievalFailedException;
    int edit(Employee employee, City city) throws UpdateFailedException, DAORetrievalFailedException;

    Employee findById(int id) throws NotFoundException, DAORetrievalFailedException;
    ArrayList<Employee> findAll() throws DAORetrievalFailedException;

    byte[] getPasswordHash(int id) throws NotFoundException, DAORetrievalFailedException;

    ArrayList<String> getAllCountries() throws DAORetrievalFailedException;

    ArrayList<String> getAllRoles() throws DAORetrievalFailedException;

}
