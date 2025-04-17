package dataAccess;

import model.Customer;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerDBAccess extends DBAccess implements ICustomerDAO {
    private static final String objectClassName;

    static {
        objectClassName = Customer.class.getSimpleName().toLowerCase();
    }

    public int create(Customer customer) throws InsertionFailedException, DAORetrievalFailedException {
        sqlInstruction = "INSERT INTO customer (first_name, last_name, birth_date, email, phone, vat_number, loyalty_points) VALUES(?,?,?,?,?,?,?);";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setDate(3, Date.valueOf(customer.getBirthDate()));
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setInt(5, customer.getPhone());
            preparedStatement.setInt(6, customer.getVatNumber());
            preparedStatement.setInt(7, customer.getLoyaltyPoints());

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new InsertionFailedException(objectClassName, -1, e.getMessage());
            }
            
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(e.getMessage());
        }
    }

    public int edit(Customer customer) throws UpdateFailedException, DAORetrievalFailedException {
        sqlInstruction = "UPDATE customer SET first_name = ?, last_name = ?, birth_date = ?, email = ?, phone = ?, vat_number = ?, loyalty_points = ? WHERE loyalty_card_number = ?;";
        int loyaltyCardNumber = customer.getLoyaltyCardNumber();

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setDate(3, Date.valueOf(customer.getBirthDate()));
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setInt(5, customer.getPhone());
            preparedStatement.setInt(6, customer.getVatNumber());
            preparedStatement.setInt(7, customer.getLoyaltyPoints());
            preparedStatement.setInt(8, loyaltyCardNumber);

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new UpdateFailedException(objectClassName, loyaltyCardNumber, e.getMessage());
            }
            
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public Customer findByLoyaltyCardNumber(int loyaltyCardNumber) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM customer WHERE loyalty_card_number = ?;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, loyaltyCardNumber);
            
            data = preparedStatement.executeQuery();

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
                
                Integer phone = data.getInt("phone");
                if (!data.wasNull()) {
                    customer.setPhone(phone);
                }
                
                Integer vatNumber = data.getInt("vat_number");
                if (!data.wasNull()) {
                    customer.setVatNumber(vatNumber);
                }
                
                Integer loyaltyPoints = data.getInt("loyalty_points");
                if (!data.wasNull()) {
                    customer.setLoyaltyPoints(loyaltyPoints);
                }

                return customer;
            } else {
                throw new NotFoundException(objectClassName, loyaltyCardNumber, DBRetrievalFailure.NO_ROW.toString());
            }
            
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public void deleteByLoyaltyCardNumber(int loyaltyCardNumber) throws DeleteFailedException, DAORetrievalFailedException {
        sqlInstruction = "DELETE FROM customer WHERE loyalty_card_number = ?;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, loyaltyCardNumber);

            try {
                preparedStatement.executeQuery();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new DeleteFailedException(objectClassName, loyaltyCardNumber, e.getMessage());
            }
            
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(e.getMessage());
        }
    }

    public ArrayList<Customer> findAll() throws DAORetrievalFailedException {
        sqlInstruction = "SELECT * FROM customer;";
        
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            
            data = preparedStatement.executeQuery();
            
            ArrayList<Customer> customers = new ArrayList<>();
            Customer customer;
            String firstName;
            String lastName;
            Date birthDate;
            String email;
            int phone;
            int vatNumber;
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
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }
}
