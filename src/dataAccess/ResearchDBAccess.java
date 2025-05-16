package dataAccess;

import exceptions.DAORetrievalFailedException;
import exceptions.NotFoundException;
import interfaces.ResearchDAO;
import model.EmployeePlace;
import model.ProductInformations;
import model.PurchaseInformations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ResearchDBAccess extends DBAccess implements ResearchDAO {

    public ArrayList<PurchaseInformations> getPurchaseInformationsByDate(LocalDate date) throws DAORetrievalFailedException {
        sqlInstruction = "SELECT purchase.id as \"purchase_id\",customer.first_name as \"customer_first_name\", customer.last_name as \"customer_last_name\", employee.first_name as \"employee_first_name\", employee.last_name as \"employee_last_name\" " +
                "FROM purchase " +
                "JOIN customer ON purchase.customer_card_number = customer.loyalty_card_number " +
                "JOIN employee ON purchase.employee_id = employee.id " +
                "WHERE purchase.date = ?;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setDate(1, Date.valueOf(date));

            ResultSet data = preparedStatement.executeQuery();
            
            ArrayList<PurchaseInformations> purchaseInformations = new ArrayList<>();
            
            while (data.next()) {
                purchaseInformations.add(new PurchaseInformations(
                        data.getLong("purchase_id"),
                        data.getString("customer_first_name"),
                        data.getString("customer_last_name"),
                        data.getString("employee_first_name"),
                        data.getString("employee_last_name")
                        )
                );
            }
            
            return purchaseInformations;
            
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }
    
    
    public ArrayList<EmployeePlace> getEmployeePlaceByEmployee(int employeeId) throws DAORetrievalFailedException {
        sqlInstruction = "SELECT employee.first_name as \"employee_first_name\", employee.last_name as \"employee_last_name\", city.name \"city_name\", city.zip_code as \"city_zip_code\", country.name \"country_name\" " +
                "FROM employee " +
                "JOIN city ON employee.city_name = city.name AND employee.city_zip_code = city.zip_code " +
                "JOIN country ON city.country = country.name " +
                "WHERE employee.id = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, employeeId);

            ResultSet data = preparedStatement.executeQuery();

            ArrayList<EmployeePlace> employeePlaces = new ArrayList<>();

            if (data.next()) {
                employeePlaces.add(new EmployeePlace(
                        data.getString("employee_first_name"),
                        data.getString("employee_last_name"),
                        data.getString("city_name"),
                        data.getInt("city_zip_code"),
                        data.getString("country_name")
                        )
                );
            }
            
            return employeePlaces;

        } catch (SQLTimeoutException exception) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, exception.getMessage());
        } catch (SQLException exception) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, exception.getMessage());
        }
    }

    public ArrayList<ProductInformations> getProductInformationsByBrand(int brandId) throws DAORetrievalFailedException {
        sqlInstruction = "SELECT product.name as \"product_name\", category.name as \"category_name\", vat.rate \"vat_rate\" " +
                "FROM product " +
                "JOIN category ON product.category_id = category.id " +
                "JOIN vat ON product.vat_type = vat.type " +
                "WHERE product.brand_id = ?;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, brandId);
            
            ResultSet data = preparedStatement.executeQuery();
            
            ArrayList<ProductInformations> productInformations = new ArrayList<>();
            
            while (data.next()) {
                productInformations.add(new ProductInformations(
                        data.getString("product_name"),
                        data.getString("category_name"),
                        data.getInt("vat_rate")
                        )
                );
            }
            
            return productInformations;
            
        } catch (SQLTimeoutException exception) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, exception.getMessage());
        } catch (SQLException exception) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, exception.getMessage());
        }
    }
}
