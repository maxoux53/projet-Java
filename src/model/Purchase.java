package model;

import java.time.LocalDate;

public class Purchase {
    private Integer id;
    private LocalDate date;
    private Integer employeeId;
    private Integer customerCardNumber;

    public Purchase(Integer id, LocalDate date, Integer employeeId, Integer customerCardNumber) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public void setCustomerCardNumber(Integer customerCardNumber) {
        this.customerCardNumber = customerCardNumber;
    }
}
