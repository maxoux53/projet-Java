package dataAccess;

import exceptions.DAORetrievalFailedException;
import exceptions.NotFoundException;
import interfaces.CustomerDAO;
import model.Customer;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDBAccess extends DBAccess implements CustomerDAO {
    private static final String objectClassName;

    static {
        objectClassName = Customer.class.getSimpleName().toLowerCase();
    }

    public Customer getByLoyaltyCardNumber(int loyaltyCardNumber) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM customer WHERE loyalty_card_number = ?;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, loyaltyCardNumber);
            
            ResultSet data = preparedStatement.executeQuery();

            if (data.next()) {
                Customer customer = new Customer(data.getInt("loyalty_card_number"));
                
                String firstName = data.getString("first_name");
                if (!data.wasNull()) {
                    customer.setFirstName(firstName);
                }
                
                String lastName = data.getString("last_name");
                if (!data.wasNull()) {
                    customer.setLastName(lastName);
                }
                
                Date birthDate = data.getDate("birth_date");
                if (!data.wasNull()) {
                    customer.setBirthDate(birthDate.toLocalDate());
                }
                
                String email = data.getString("email");
                if (!data.wasNull()) {
                    customer.setEmail(email);
                }
                
                int phone = data.getInt("phone");
                if (!data.wasNull()) {
                    customer.setPhone(phone);
                }
                
                long vatNumber = data.getLong("vat_number");
                if (!data.wasNull()) {
                    customer.setVatNumber(vatNumber);
                }
                
                int loyaltyPoints = data.getInt("loyalty_points");
                if (!data.wasNull()) {
                    customer.setLoyaltyPoints(loyaltyPoints);
                }

                return customer;
            } else {
                throw new NotFoundException(objectClassName, loyaltyCardNumber, DBRetrievalFailure.NO_ROW);
            }
            
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }

    public ArrayList<Customer> getAll() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM customer;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            
            ResultSet data = preparedStatement.executeQuery();
            
            ArrayList<Customer> customers = new ArrayList<>();
            Customer customer;
            String firstName;
            String lastName;
            Date birthDate;
            String email;
            int phone;
            long vatNumber;
            int loyaltyPoints;
            
            while (data.next()) {
                customer = new Customer(data.getInt("loyalty_card_number"));
                
                firstName = data.getString("first_name");
                if (!data.wasNull()) {
                    customer.setFirstName(firstName);
                }
                
                lastName = data.getString("last_name");
                if (!data.wasNull()) {
                    customer.setLastName(lastName);
                }
                
                birthDate = data.getDate("birth_date");
                if (!data.wasNull()) {
                    customer.setBirthDate(birthDate.toLocalDate());
                }
                
                email = data.getString("email");
                if (!data.wasNull()) {
                    customer.setEmail(email);
                }
                
                phone = data.getInt("phone");
                if (!data.wasNull()) {
                    customer.setPhone(phone);
                }
                
                vatNumber = data.getInt("vat_number");
                if (!data.wasNull()) {
                    customer.setVatNumber(vatNumber);
                }
                
                loyaltyPoints = data.getInt("loyalty_points");
                if (!data.wasNull()) {
                    customer.setLoyaltyPoints(loyaltyPoints);
                }
                
                customers.add(customer);
            }
            
            return customers;
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT, e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR, e.getMessage());
        }
    }
}

