package controller;

import business.EmployeeManager;
import exceptions.HashFailedException;
import exceptions.*;
import model.*;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;

public class EmployeeController {
    private EmployeeManager manager;

    public EmployeeController() {
        setManager(new EmployeeManager());
    }

    public void setManager(EmployeeManager manager) {
        this.manager = manager;
    }

    public int create(String firstName, String lastName, char[] password, Boolean isActive, String street, String streetNumber, String unitNumberAsString, String roleLabel, Date date, String managerIdAsString, String zipCodeAsString, String cityName, String countryName) throws HashFailedException, InsertionFailedException, DAORetrievalFailedException, WrongTypeException, ProhibitedValueException {
        return manager.create(
                new Employee(
                        firstName,
                        lastName,
                        stringToPassword(password),
                        isActive,
                        street,
                        streetNumber,
                        stringToUnitNumber(unitNumberAsString),
                        roleLabel,
                        date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                        stringToId(managerIdAsString),
                        stringToZipCode(zipCodeAsString),
                        cityName
                )
        );
    }

    public int remove(String idAsString) throws DeleteFailedException, DAORetrievalFailedException, WrongTypeException, ProhibitedValueException {
        return manager.remove(stringToId(idAsString));
    }

    public int update(int id, String firstName, String lastName, char[] password, Boolean isActive, String street, String streetNumber, String unitNumberAsString, String roleLabel, Date date, String managerIdAsString, String zipCodeAsString, String cityName, String countryName) throws HashFailedException, DAORetrievalFailedException, WrongTypeException, ProhibitedValueException, UpdateFailedException {
        return manager.update(
                new Employee(
                        id,
                        firstName,
                        lastName,
                        stringToPassword(password),
                        isActive,
                        street,
                        streetNumber,
                        stringToUnitNumber(unitNumberAsString),
                        roleLabel,
                        date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                        stringToId(managerIdAsString),
                        stringToZipCode(zipCodeAsString),
                        cityName
                )
        );
    }

    private Integer stringToUnitNumber(String unitNumberAsString) throws ProhibitedValueException, WrongTypeException {
        if (!unitNumberAsString.isEmpty()) {
            int unitNumber;

            try {
                unitNumber = Integer.parseInt(unitNumberAsString);

                if (unitNumber < 0) {
                    throw new ProhibitedValueException("Numéro d'unité");
                }
                return unitNumber;
            } catch (NumberFormatException numberFormatException) {
                throw new WrongTypeException("Numéro d'unité");
            }
        }
        return null;
    }

    private byte[] stringToPassword(char[] password) throws HashFailedException {
        if (password != null) {
            return manager.hashPassword(new String(password));
        }
        return null;
    }

    private Integer stringToId(String idAsString) throws ProhibitedValueException, WrongTypeException {
        if (!idAsString.isEmpty()) {
            try {
                return Integer.parseInt(idAsString);
            } catch (NumberFormatException numberFormatException) {
                throw new WrongTypeException("Identifiant");
            }
        }
        return null;
    }

    private Integer stringToZipCode(String zipCodeAsString) throws ProhibitedValueException, WrongTypeException {
        if (!zipCodeAsString.isEmpty()) {
            int zipCode;

            try {
                zipCode = Integer.parseInt(zipCodeAsString);

                if (zipCode < 0) {
                    throw new ProhibitedValueException("Code postal");
                }
                return zipCode;
            } catch (NumberFormatException numberFormatException) {
                throw new WrongTypeException("Code postal");
            }
        }
        return null;
    }

    public Employee getEmployeeById(String idAsString) throws NotFoundException, DAORetrievalFailedException, WrongTypeException, ProhibitedValueException {
        return manager.getById(stringToId(idAsString));
    }

    public ArrayList<Employee> getAllEmployees() throws DAORetrievalFailedException {
        return manager.getAll();
    }

    public boolean isPasswordCorrect(String username, char[] passwordAttempt) throws HashFailedException, DAORetrievalFailedException, NotFoundException {
        return manager.checkPassword(passwordAttempt, Integer.parseInt(username));
    }

    public ArrayList<String> getAllCountries() throws DAORetrievalFailedException {
        return manager.getAllCountries();
    }

    public ArrayList<String> getAllRoles() throws DAORetrievalFailedException {
        return manager.getAllRoles();
    }

    public DefaultTableModel infoTableModel() throws DAORetrievalFailedException {
        String[] columnNames = {
                "Matricule",
                "Prénom",
                "Nom",
                "est actif",
                "Rue",
                "Numéro de rue",
                "Numéro de boîte",
                "Rôle",
                "Date d'embauche",
                "Identifiant du manager",
                "Code postal de la ville",
                "Nom de la ville"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        ArrayList<Employee> employees = getAllEmployees();
        Object[] employeeInfos = new Object[columnNames.length];
        
        for (Employee employee : employees) {
            
            employeeInfos[0] = employee.getId();
            employeeInfos[1] = employee.getFirstName();
            employeeInfos[2] = employee.getLastName();
            employeeInfos[3] = employee.getActive();
            employeeInfos[4] = employee.getStreet();
            employeeInfos[5] = employee.getStreetNumber();
            employeeInfos[6] = employee.getUnitNumber();
            employeeInfos[7] = employee.getRoleLabel();
            employeeInfos[8] = employee.getHireDate();
            employeeInfos[9] = employee.getManagerId();
            employeeInfos[10] = employee.getCityZipCode();
            employeeInfos[11] = employee.getCityName();
            
            model.addRow(employeeInfos);
        }

        return model;
    }
}
