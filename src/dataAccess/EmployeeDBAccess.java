package dataAccess;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDBAccess implements IEmployeeDAO {
    private String sqlInstruction;
    private PreparedStatement preparedStatement;
    private ResultSet data;

    public void create(Employee employee) throws InsertionFailedException, DataRetrievalFailureException {
        sqlInstruction = "INSERT INTO employee (first_name, last_name, password, is_active, street, street_number, unit_number, role_label, hire_date, manager_id, city_zip_code, city_name) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setBytes(3, employee.getPassword());
            preparedStatement.setBoolean(4, employee.getActive());
            preparedStatement.setString(5, employee.getStreet());
            preparedStatement.setInt(6, employee.getStreetNumber());
            preparedStatement.setInt(7, employee.getUnitNumber());
            preparedStatement.setString(8, employee.getRoleLabel());
            preparedStatement.setDate(9, Date.valueOf(employee.getHireDate()));
            preparedStatement.setInt(10, employee.getManagerId());
            preparedStatement.setInt(11, employee.getCityZipCode());
            preparedStatement.setString(12, employee.getCityName());

            try {
                int updatedLinesNb = preparedStatement.executeUpdate();         // should return int ?
            } catch (SQLException e) {
                throw new InsertionFailedException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DataRetrievalFailureException(e.getMessage());
        }
    }

    public void delete(Employee employee) throws DeleteFailedException {

    }

    public Employee findById(int id) throws NotFoundException, DataRetrievalFailureException {
        sqlInstruction = "SElECT * FROM employee WHERE id = ?;";
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);                                          // useless with int ??
            data = preparedStatement.executeQuery();

            boolean employeeExists;

            try {
                employeeExists = data.next();
            } catch (SQLException e) {
                throw new NotFoundException("Employee", id, e.getMessage()); // ability to use .class.getSimpleName()
            }

            if (employeeExists) {
                String firstName;
                String lastName;
                byte[] password;
                boolean isActive;
                String street;
                int streetNumber;
                int unitNumber;
                String roleLabel;
                Date hireDate;
                int managerId;
                int cityZipCode;
                String cityName;

                Employee employee = new Employee(data.getInt("id"));

                firstName = data.getString("first_name");
                if (!data.wasNull()) {
                    employee.setFirstName(firstName);
                }

                lastName = data.getString("last_name");
                if (!data.wasNull()) {
                    employee.setLastName(lastName);
                }

                password = data.getBytes("password");
                if (!data.wasNull()) {
                    employee.setPassword(password);
                }

                isActive = data.getBoolean("is_active");
                if (!data.wasNull()) {
                    employee.setActive(isActive);
                }

                street = data.getString("street");
                if (!data.wasNull()) {
                    employee.setStreet(street);
                }

                streetNumber = data.getInt("street_number");
                if (!data.wasNull()) {
                    employee.setStreetNumber(streetNumber);
                }

                unitNumber = data.getInt("unit_number");
                if (!data.wasNull()) {
                    employee.setUnitNumber(unitNumber);
                }

                roleLabel = data.getString("role_label");
                if (!data.wasNull()) {
                    employee.setRoleLabel(roleLabel);
                }

                hireDate = data.getDate("hire_date");
                if (!data.wasNull()) {
                    employee.setHireDate(hireDate.toLocalDate()); // where to handle date type conversion... here or in the class itself with a dedicated setter?
                }

                managerId = data.getInt("manager_id");
                if (!data.wasNull()) {
                    employee.setManagerId(managerId);
                }

                cityZipCode = data.getInt("city_zip_code");
                if (!data.wasNull()) {
                    employee.setCityZipCode(cityZipCode);
                }

                cityName = data.getString("city_name");
                if (!data.wasNull()) {
                    employee.setCityName(cityName);
                }

                return employee;
            }

            return null;
        } catch (SQLTimeoutException e) {
            throw new DataRetrievalFailureException("Database query timed out. " + e.getMessage());
        } catch (SQLException e) {
            throw new DataRetrievalFailureException("SQL data access failed, see: " + e.getMessage());
        }
    }

    public ArrayList<Employee> findAll() throws DataRetrievalFailureException {
        sqlInstruction = "SElECT * FROM employee;";
        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            data = preparedStatement.executeQuery();


            ArrayList<Employee> employees = new ArrayList<>();
            Employee employee;
            String firstName;
            String lastName;
            byte[] password;
            boolean isActive;
            String street;
            int streetNumber;
            int unitNumber;
            String roleLabel;
            Date hireDate;
            int managerId;
            int cityZipCode;
            String cityName;

            while (data.next()) {
                employee = new Employee(data.getInt("id"));

                firstName = data.getString("first_name");
                if (!data.wasNull()) {
                    employee.setFirstName(firstName);
                }

                lastName = data.getString("last_name");
                if (!data.wasNull()) {
                    employee.setLastName(lastName);
                }

                password = data.getBytes("password");
                if (!data.wasNull()) {
                    employee.setPassword(password);
                }

                isActive = data.getBoolean("is_active");
                if (!data.wasNull()) {
                    employee.setActive(isActive);
                }

                street = data.getString("street");
                if (!data.wasNull()) {
                    employee.setStreet(street);
                }

                streetNumber = data.getInt("street_number");
                if (!data.wasNull()) {
                    employee.setStreetNumber(streetNumber);
                }

                unitNumber = data.getInt("unit_number");
                if (!data.wasNull()) {
                    employee.setUnitNumber(unitNumber);
                }

                roleLabel = data.getString("role_label");
                if (!data.wasNull()) {
                    employee.setRoleLabel(roleLabel);
                }

                hireDate = data.getDate("hire_date");
                if (!data.wasNull()) {
                    employee.setHireDate(hireDate.toLocalDate()); // where to handle date type conversion... here or in the class itself with a dedicated setter?
                }

                managerId = data.getInt("manager_id");
                if (!data.wasNull()) {
                    employee.setManagerId(managerId);
                }

                cityZipCode = data.getInt("city_zip_code");
                if (!data.wasNull()) {
                    employee.setCityZipCode(cityZipCode);
                }

                cityName = data.getString("city_name");
                if (!data.wasNull()) {
                    employee.setCityName(cityName);
                }

                employees.add(employee);
            }

            return employees;
        } catch (SQLTimeoutException e) {
            throw new DataRetrievalFailureException("Database query timed out. " + e.getMessage());
        } catch (SQLException e) {
            throw new DataRetrievalFailureException("SQL data access failed, see: " + e.getMessage());
        }
    }

    public void edit(Employee employee) throws UpdateFailedException {

    }
}
