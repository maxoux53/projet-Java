package view.product;

import controller.*;
import exceptions.*;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class AddProduct extends JPanel {
    // Attributes
    private final ProductPanel productPanel;
    private ProductController controller;
    
    // Constructors
    public AddProduct() {
        setController(new ProductController());
        productPanel = new ProductPanel("Ajouter un article", "Ajouter");
        
        productPanel.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.create(new Product(
                            productPanel.stringToBarcode(productPanel.getBarcodeField().getText()),
                            productPanel.nullIfEmptyName(productPanel.getNameField().getText()),
                            productPanel.getDescriptionField().getText(),
                            (int)productPanel.getAmountSpinner().getValue(),
                            productPanel.getAvailableRadioButtonYes().isSelected(),
                            ((String)productPanel.getVatTypeComboBox().getSelectedItem()).charAt(0),
                            controller.getAllCategories().get(productPanel.getCategoryComboBox().getSelectedIndex()).getId(),
                            controller.getOrCreateBrand(productPanel.getBrandField().getText()),
                            productPanel.stringToPrice(productPanel.getPriceField().getText()),
                            (LocalDate)productPanel.getStartDateSpinner().getValue()
                    ));

                    productPanel.removeAllField();
                } catch (WrongTypeException | ProhibitedValueException | InsertionFailedException | DAORetrievalFailedException | NullPointerException | FieldIsEmptyException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.red);
        add(productPanel);
    }

    public void setController(ProductController productController) {
        controller = productController;
    }
}
