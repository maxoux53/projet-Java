package view.product;

import controller.ProductController;
import exceptions.*;
import model.Product;
import view.FontPreferences;
import view.Window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductManagement extends JPanel {
    // Attributes
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel listingPanel;
    private JTable listingTable;
    private JScrollPane scrollPane;
    private JPanel buttonsPanel;
    private JButton editButton;
    private JButton deleteButton;
    private Product selectedProduct;
    private ProductController controller;
    
    // Constructors
    public ProductManagement(Window window) {
        setController(new ProductController());

        setLayout(new BorderLayout(0, 50));
        setBorder(BorderFactory.createEmptyBorder(20, 100, 50, 100));
        new GridLayout(3, 1, 0, 0);
        setBackground(Color.white);

        // Title
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        
        titleLabel = new JLabel("Gestion des articles", SwingConstants.CENTER);
        titleLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.BOLD, FontPreferences.TITLE_SIZE));
        
        titlePanel.add(titleLabel);
        
        // Listing
        listingPanel = new JPanel(new BorderLayout());
        listingPanel.setBackground(Color.white);

        try {
            listingTable = new JTable(infoTableModel());
        } catch (DAORetrievalFailedException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
        scrollPane = new JScrollPane(listingTable);
        
        listingPanel.add(scrollPane);
        
        // Buttons
        buttonsPanel = new JPanel(new GridLayout(1, 2, 50,  0));
        buttonsPanel.setBackground(Color.white);
        
        editButton = new JButton("Modifier");
        editButton.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.BOLD, FontPreferences.MID_SIZE));
        editButton.setBackground(Color.white);
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSelectedProductLoaded()) {
                    window.getEditProduct().fillAllFields(selectedProduct);
                    window.showEditProduct();
                }
            }
        });

        deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.BOLD, FontPreferences.MID_SIZE));
        deleteButton.setBackground(Color.white);
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSelectedProductLoaded()) {
                    if (JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir supprimer l'aticle : " + selectedProduct.getName() + " ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
                                    == JOptionPane.YES_OPTION) {
                        try {
                            if (controller.remove(selectedProduct.getBarcode()) > 0) {
                                JOptionPane.showMessageDialog(null, "L'article a été supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "L'article n'a pas pu être supprimé !", "Échec", JOptionPane.ERROR_MESSAGE);
                            }

                        } catch (WrongTypeException | DeleteFailedException | DAORetrievalFailedException | NullPointerException | FieldIsEmptyException ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        
        // Add Main
        add(titlePanel, BorderLayout.NORTH);
        add(listingPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    
    // Methods
    private boolean isSelectedProductLoaded() {
        if (listingTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Aucune donnée séléctionnée !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            try {
                selectedProduct = controller.getByBarcode(Long.valueOf(listingTable.getValueAt(listingTable.getSelectedRow(), 0).toString()));
            } catch (DAORetrievalFailedException | WrongTypeException | NullPointerException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (NotFoundException | FieldIsEmptyException exception) {
                JOptionPane.showMessageDialog(null, "Article inconnu !", "Erreur", JOptionPane.ERROR_MESSAGE);
            } 
        }
        return true;
    }

    private DefaultTableModel infoTableModel() throws DAORetrievalFailedException, NotFoundException {
        String[] columnNames = {
                "Code-barres",
                "Nom",
                "Description",
                "Quantité",
                "Disponible",
                "Type de TVA",
                "Catégorie",
                "Marque",
                "Prix (€)",
                "Date de mise en rayon"
        };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        ArrayList<Product> products = controller.getAll();
        Object[] productInfos = new Object[columnNames.length];

        for (Product product : products) {
            productInfos[0] = product.getBarcode();
            productInfos[1] = product.getName();
            productInfos[2] = product.getDescription();
            productInfos[3] = product.getAmount();
            productInfos[4] = product.getAvailable();
            productInfos[5] = product.getVatType();
            productInfos[6] = controller.getCategoryById(product.getCategoryId());
            productInfos[7] = controller.getBrandById(product.getBrandId());
            productInfos[8] = product.getExclVatPrice();
            productInfos[9] = product.getStartDate();

            model.addRow(productInfos);
        }

        return model;
    }

    public void setController(ProductController productController) {
        controller = productController;
    }
}
