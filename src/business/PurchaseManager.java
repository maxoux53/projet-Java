package business;

import exceptions.DAORetrievalFailedException;
import interfaces.PurchaseDAO;
import dataAccess.PurchaseDBAccess;
import model.LoyalCustomerPurchases;
import model.Purchase;
import model.SalesInfo;

import java.util.ArrayList;

public class PurchaseManager {
    private PurchaseDAO dao;

    public PurchaseManager() {
        setDao(new PurchaseDBAccess());
    }

    public void setDao(PurchaseDAO dao) {
        this.dao = dao;
    }

    public ArrayList<Purchase> getAll() throws DAORetrievalFailedException {
        return dao.getAll();
    }

    public ArrayList<SalesInfo> salesRanking(String categoryLabel) throws DAORetrievalFailedException {
        return dao.salesRanking(categoryLabel);
    }

    public ArrayList<LoyalCustomerPurchases> loyalCustomerPurchasesRankingByEmployee(Integer employeeId) throws DAORetrievalFailedException {
        return dao.loyalCustomerPurchasesRankingByEmployee(employeeId);
    }
}
