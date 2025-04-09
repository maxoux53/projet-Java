package dataAccess;

import model.Employee;
import java.util.ArrayList;

public interface IEmployeeDAO {
    void create(Employee employee) throws InsertionFailedException, DAORetrievalFailedException; // void? boolean? Employee? with exception?
    void deleteById(int id) throws DeleteFailedException, DAORetrievalFailedException;
    Employee findById(int id) throws NotFoundException, DAORetrievalFailedException;
    ArrayList<Employee> findAll() throws DAORetrievalFailedException;
    void edit(Employee employee) throws UpdateFailedException;

}
