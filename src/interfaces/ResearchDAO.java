package interfaces;

import exceptions.DAORetrievalFailedException;
import exceptions.NotFoundException;
import model.EmployeePlace;
import model.ProductInformations;
import model.PurchaseInformations;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ResearchDAO {
    public ArrayList<PurchaseInformations> getPurchaseInformationsByDate(LocalDate date) throws DAORetrievalFailedException;

    public ArrayList<EmployeePlace> getEmployeePlaceByEmployee(int employeeId) throws DAORetrievalFailedException;
    
    public ArrayList<ProductInformations> getProductInformationsByBrand(int brandId) throws DAORetrievalFailedException;
}
