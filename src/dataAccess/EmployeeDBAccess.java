package dataAccess;

import model.Employee;
import java.sql.*;
import java.util.ArrayList;

public class EmployeeDBAccess extends DBAccess implements IEmployeeDAO {
    private static final String objectClassName;

    static {
        objectClassName = Employee.class.getSimpleName().toLowerCase();
    }

    public int create(Employee employee) throws InsertionFailedException, DAORetrievalFailedException {
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
                return preparedStatement.executeUpdate();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new InsertionFailedException(objectClassName, -1, e.getMessage()); // '-1' => because auto generated by postgres SERIAL?
            }

        } catch (SQLException e) {
            throw new DAORetrievalFailedException(e.getMessage());
        }
    }

    public void deleteById(int id) throws DeleteFailedException, DAORetrievalFailedException {
        nullifyEmployeeReferencesFromPurchases(id);
        nullifyEmployeeReferencedAsManager(id);

        sqlInstruction = "DELETE FROM employee WHERE id = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);

            try {
                preparedStatement.executeQuery();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new DeleteFailedException(objectClassName, id, e.getMessage());
            }

        } catch (SQLException e) {
            throw new DAORetrievalFailedException(e.getMessage());
        }
    }

    private void nullifyEmployeeReferencesFromPurchases(int employeeId) throws DAORetrievalFailedException {
        sqlInstruction = "UPDATE purchase SET employee_id = NULL WHERE employee_id = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    private void nullifyEmployeeReferencedAsManager(int employeeId) throws DAORetrievalFailedException {
        sqlInstruction = "UPDATE employee SET manager_id = NULL WHERE manager_id = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public int edit(Employee employee) throws UpdateFailedException, DAORetrievalFailedException {
        sqlInstruction = "UPDATE employee SET first_name = ?, last_name = ?, password = ?, is_active = ?, street = ?, street_number = ?, unit_number = ?, role_label = ?, hire_date = ?, manager_id = ?, city_zip_code = ?, city_name = ? WHERE id = ?;";
        int id = employee.getId();

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

            preparedStatement.setInt(13, id);

            try {
                return preparedStatement.executeUpdate();
            } catch (SQLTimeoutException e) {
                throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
            } catch (SQLException e) {
                throw new UpdateFailedException(objectClassName, id, e.getMessage());
            }

        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public Employee findById(int id) throws NotFoundException, DAORetrievalFailedException {
        sqlInstruction = "SElECT * FROM employee WHERE id = ?;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, id);

            data = preparedStatement.executeQuery();

            if (data.next()) {
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
                    employee.setHireDate(hireDate.toLocalDate());
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
            } else {
                throw new NotFoundException(objectClassName, id, DBRetrievalFailure.NO_ROW.toString()); // EXCEPTION MESSAGE
            }
        } catch (SQLTimeoutException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }

    public ArrayList<Employee> findAll() throws DAORetrievalFailedException {
        sqlInstruction = "SElECT * FROM employee;";

        try {
            preparedStatement = SingletonConnection.getInstance().prepareStatement(sqlInstruction);

            data = preparedStatement.executeQuery(); // add try-catch if notfoundexception changes

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
                    employee.setHireDate(hireDate.toLocalDate());
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
            throw new DAORetrievalFailedException(DBRetrievalFailure.TIMEOUT.toString(), e.getMessage());
        } catch (SQLException e) {
            throw new DAORetrievalFailedException(DBRetrievalFailure.ACCESS_ERROR.toString(), e.getMessage());
        }
    }
}
