package model;

public class PurchaseInformations {
    // Attributes
    private Long purchaseId;
    private String customerFirstName, customerLastName, employeeFirstName, employeeLastName;
    
    // Constructors
    public PurchaseInformations(Long purchaseId, String customerFirstName, String customerLastName, String employeeFirstName, String employeeLastName) {
        this.purchaseId = purchaseId;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
    }
    
    // Getters
    public Long getPurchaseId() {
        return purchaseId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }
}
