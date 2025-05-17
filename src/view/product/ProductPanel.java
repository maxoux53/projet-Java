package view.product;

import controller.ProductController;
import exceptions.*;
import model.Category;
import model.Vat;
import view.FontPreferences;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductPanel extends JPanel {
    // Attributes
    private JPanel titlePanel, formPanel, barcodePanel, namePanel, descriptionPanel, pricePanel, amountPanel, vatTypePanel, categoryPanel, brandPanel, startDatePanel, availablePanel, buttonPanel;
    private JLabel titleLabel, barcodeLabel, nameLabel, descriptionLabel, priceLabel, amountLabel, vatTypeLabel, categoryLabel, brandLabel, startDateLabel, availableLabel;
    private JTextField nameField, barcodeField, descriptionField, priceField, brandField;
    private JComboBox<String> vatTypeComboBox, categoryComboBox;
    private JSpinner amountSpinner, startDateSpinner;
    private JPanel AvailablelabelSubPanel, AvailableRadioButtonSubPanel;
    private JRadioButton availableRadioButtonYes, availableRadioButtonNo;
    private ButtonGroup availabilityGroup;
    private JButton button;
    private ProductController controller;

    // Constructors
    public ProductPanel(String title, String buttonString) {
        setController(new ProductController());

        setLayout(new BorderLayout(0, 100));
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        setBackground(Color.white);

        // Title
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.BOLD, FontPreferences.TITLE_SIZE));

        titlePanel.add(titleLabel);

        // Form
        formPanel = new JPanel(new GridLayout(5, 2, 50, 100));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 250));
        formPanel.setBackground(Color.white);
        
        // Barcode
        barcodePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        barcodePanel.setBackground(Color.white);
        
        barcodeLabel = new JLabel("Code-Barres", SwingConstants.RIGHT);
        barcodeLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));
        
        barcodeField = new JTextField();
        barcodeField.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        barcodeField.setBackground(Color.white);
        barcodeField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        
        barcodePanel.add(barcodeLabel);
        barcodePanel.add(barcodeField);
        
        // Name
        namePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        namePanel.setBackground(Color.white);
        
        nameLabel = new JLabel("Nom", SwingConstants.RIGHT);
        nameLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        nameField = new JTextField();
        nameField.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        nameField.setBackground(Color.white);
        nameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Description
        descriptionPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        descriptionPanel.setBackground(Color.white);
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
        descriptionLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        descriptionField = new JTextField();
        descriptionField.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        descriptionField.setBackground(Color.white);
        descriptionField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);
        
        // Price
        pricePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        pricePanel.setBackground(Color.white);
        pricePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        priceLabel = new JLabel("Prix", SwingConstants.RIGHT);
        priceLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        priceField = new JTextField();
        priceField.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        priceField.setBackground(Color.white);
        priceField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        
        // Amount
        amountPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        amountPanel.setBackground(Color.white);
        amountPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        amountLabel = new JLabel("Quantité", SwingConstants.RIGHT);
        amountLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        amountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        amountSpinner.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        amountSpinner.setBackground(Color.white);
        
        amountPanel.add(amountLabel);
        amountPanel.add(amountSpinner);
        
        // Vat Type
        vatTypePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        vatTypePanel.setBackground(Color.white);
        vatTypePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        vatTypeLabel = new JLabel("Type de TVA", SwingConstants.RIGHT);
        vatTypeLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));
        
        vatTypeComboBox = new JComboBox<String>();
        vatTypeComboBox.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        vatTypeComboBox.setBackground(Color.white);

        final ArrayList<Vat> vats;
        try {
            vats = controller.getAllVats();

            for (Vat vat : vats) {
                vatTypeComboBox.addItem(vat.getType() + " (" + vat.getRate() + "%)");
            }
        } catch (DAORetrievalFailedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            vatTypeComboBox.addItem("Veuillez reessayer");
        }

        vatTypePanel.add(vatTypeLabel);
        vatTypePanel.add(vatTypeComboBox);
        
        // Category
        categoryPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        categoryPanel.setBackground(Color.white);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        categoryLabel = new JLabel("Catégorie", SwingConstants.RIGHT);
        categoryLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        categoryComboBox = new JComboBox<String>();
        categoryComboBox.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        categoryComboBox.setBackground(Color.white);

        final ArrayList<Category> categories = new ArrayList<>();
        try {
            categories.addAll(controller.getAllCategories());

            for (Category category : categories) {
                categoryComboBox.addItem(category.getLabel());
            }
        } catch (DAORetrievalFailedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        // Brand
        brandPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        brandPanel.setBackground(Color.white);
        brandPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        brandLabel = new JLabel("Marque", SwingConstants.RIGHT);
        brandLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        brandField = new JTextField();
        brandField.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        brandField.setBackground(Color.white);
        brandField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        brandPanel.add(brandLabel);
        brandPanel.add(brandField);
        
        // Start date
        startDatePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        startDatePanel.setBackground(Color.white);
        
        startDateLabel = new JLabel("Date de mise en rayon", SwingConstants.RIGHT);
        startDateLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));
        
        startDateSpinner = new JSpinner(new SpinnerDateModel());
        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        startDateSpinner.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        
        startDatePanel.add(startDateLabel);
        startDatePanel.add(startDateSpinner);
        
        // Available
        availablePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        availablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        availablePanel.setBackground(Color.white);
        
        AvailablelabelSubPanel = new JPanel(new GridLayout(1, 1, 0, 0));
        AvailablelabelSubPanel.setBackground(Color.white);
        AvailableRadioButtonSubPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        AvailableRadioButtonSubPanel.setBackground(Color.white);
        
        availableLabel = new JLabel("Disponible", SwingConstants.RIGHT);
        availableLabel.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.MID_SIZE));

        availableRadioButtonYes = new JRadioButton("OUI");
        availableRadioButtonYes.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));
        availableRadioButtonYes.setSelected(true);
        availableRadioButtonNo = new JRadioButton("NON");
        availableRadioButtonNo.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.PLAIN, FontPreferences.NORMAL_SIZE));

        availabilityGroup = new ButtonGroup();
        availabilityGroup.add(availableRadioButtonYes);
        availabilityGroup.add(availableRadioButtonNo);

        AvailablelabelSubPanel.add(availableLabel);
        AvailableRadioButtonSubPanel.add(availableRadioButtonYes);
        AvailableRadioButtonSubPanel.add(availableRadioButtonNo);
        
        availablePanel.add(AvailablelabelSubPanel);
        availablePanel.add(AvailableRadioButtonSubPanel);
        
        // Button
        buttonPanel = new JPanel(new BorderLayout(0, 0));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 450, 0, 450));
        
        button = new JButton(buttonString);
        button.setFont(new Font(FontPreferences.DEFAULT_STYLE, Font.BOLD, FontPreferences.MID_SIZE));
        button.setBackground(Color.white);
        button.setFocusPainted(false);
        
        buttonPanel.add(button);
        
        // Add
        formPanel.add(barcodePanel);
        
        formPanel.add(namePanel);

        formPanel.add(descriptionPanel);
        
        formPanel.add(pricePanel);

        formPanel.add(amountPanel);
        
        formPanel.add(vatTypePanel);

        formPanel.add(categoryPanel);

        formPanel.add(brandPanel);
        
        formPanel.add(startDatePanel);

        formPanel.add(availablePanel);
        
        // Main add
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Getters
    public JRadioButton getAvailableRadioButtonYes() {
        return availableRadioButtonYes;
    }

    public JRadioButton getAvailableRadioButtonNo() {
        return availableRadioButtonNo;
    }

    public JSpinner getStartDateSpinner() {
        return startDateSpinner;
    }

    public JSpinner getAmountSpinner() {
        return amountSpinner;
    }

    public JComboBox<String> getCategoryComboBox() {
        return categoryComboBox;
    }

    public JComboBox<String> getVatTypeComboBox() {
        return vatTypeComboBox;
    }
    
    public JTextField getBrandField() {
        return brandField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JTextField getDescriptionField() {
        return descriptionField;
    }

    public JTextField getBarcodeField() {
        return barcodeField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getButton() {
        return button;
    }

    // Methods
    public void removeAllField() {
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        amountSpinner.setValue(0);
        vatTypeComboBox.setSelectedIndex(0);
        categoryComboBox.setSelectedIndex(0);
        brandField.setText("");
        availableRadioButtonYes.setSelected(true);
        
        repaint();
        revalidate();
    }

    public String nullIfEmptyName(String name) {
        return (name.isEmpty() ? null : name);
    }

    public BigDecimal stringToPrice(String priceAsString) throws WrongTypeException, ProhibitedValueException {
        if (!priceAsString.isEmpty()) {
            BigDecimal price;

            try {
                price = new BigDecimal(priceAsString);

                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    throw new ProhibitedValueException("Prix");
                }

                return price;
            } catch (NumberFormatException numberFormatException) {
                throw new WrongTypeException("Prix");
            }
        }
        return null;
    }

    public Long stringToBarcode(String barcodeAsString) throws WrongTypeException, FieldIsEmptyException {
        if (!barcodeAsString.isEmpty()) {
            try {
                return Long.parseLong(barcodeAsString);
            } catch (NumberFormatException numberFormatException) {
                throw new WrongTypeException("Code-barres");
            }
        } else {
            throw new FieldIsEmptyException("Code-barres");
        }
    }

    public void setController(ProductController productController) {
        controller = productController;
    }
}
