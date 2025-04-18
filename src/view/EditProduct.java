package view;

import controller.ApplicationController;
import controller.FieldIsEmpty;
import controller.WrongDate;
import controller.WrongType;
import model.Category;
import model.Vat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class EditProduct extends JPanel {
    // Attributes
    private JPanel titlePanel;
    private JLabel titleLabel;
    private JPanel formPanel;
    private JPanel barcodePanel;
    private JLabel barcodeLabel;
    private JTextField barcodeField;
    private JButton barcodeButton;
    private boolean fieldsIsVisible;
    private JPanel namePanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel descriptionPanel;
    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JPanel pricePanel;
    private JLabel priceLabel;
    private JTextField priceField;
    private JPanel amountPanel;
    private JLabel amountLabel;
    private JSpinner amountSpinner;
    private JPanel vatTypePanel;
    private JLabel vatTypeLabel;
    private JComboBox<String> vatTypeComboBox;
    private JPanel categoryPanel;
    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;
    private JPanel brandPanel;
    private JLabel brandLabel;
    private JTextField brandField;
    private JPanel startDatePanel;
    private JPanel startDateLabelSubPanel;
    private JLabel startDateLabel;
    private JPanel startDateFieldsSubPanel;
    private JTextField startDateDayField;
    private JTextField startDateMonthField;
    private JTextField startDateYearField;
    private JPanel availablePanel;
    private JPanel AvailablelabelSubPanel;
    private JLabel availableLabel;
    private JPanel AvailableRadioButtonSubPanel;
    private ButtonGroup availabilityGroup;
    private JRadioButton availableRadioButtonYes;
    private JRadioButton availableRadioButtonNo;
    private JPanel buttonPanel;
    private JButton addButton;
    
    // Constructors
    public EditProduct() {
        setLayout(new BorderLayout(0, 100));
        setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        setBackground(Color.white);

        // Title
        titlePanel = new JPanel();
        titlePanel.setBackground(Color.white);
        
        titleLabel = new JLabel("Modifier un article");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 60));

        titlePanel.add(titleLabel);

        // Form
        formPanel = new JPanel(new GridLayout(5, 2, 50, 100));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 250));
        formPanel.setBackground(Color.white);
        
        // Barcode
        barcodePanel = new JPanel(new GridLayout(1, 3, 20, 0));
        barcodePanel.setBackground(Color.white);
        
        barcodeLabel = new JLabel("Code-barres", SwingConstants.RIGHT);
        barcodeLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));
        
        barcodeField = new JTextField();
        barcodeField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        barcodeField.setBackground(Color.white);
        barcodeField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        barcodeField.setPreferredSize(new Dimension(20, 25));
        
        barcodeButton = new JButton("Charger");
        barcodeButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        barcodeButton.setBackground(Color.white);
        barcodeButton.setFocusPainted(false);
        
        fieldsIsVisible = false;
        barcodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!fieldsIsVisible) {
                    setVisibleAll(true);
                    barcodeButton.setText("Décharger");
                    addButton.setEnabled(true);
                    barcodeField.setEnabled(false);
                }
                else {
                    setVisibleAll(false);
                    barcodeButton.setText("Charger");
                    addButton.setEnabled(false);
                    barcodeField.setEnabled(true);
                }
                fieldsIsVisible = !fieldsIsVisible;
            }
        });
        
        barcodePanel.add(barcodeLabel);
        barcodePanel.add(barcodeField);
        barcodePanel.add(barcodeButton);
        
        // Name
        namePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        namePanel.setBackground(Color.white);
        namePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        nameLabel = new JLabel("Nom", SwingConstants.RIGHT);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        nameField.setBackground(Color.white);
        nameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        nameField.setPreferredSize(new Dimension(20, 25));
        
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Description
        descriptionPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        descriptionPanel.setBackground(Color.white);
        descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        descriptionLabel = new JLabel("Description", SwingConstants.RIGHT);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        descriptionField = new JTextField();
        descriptionField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        descriptionField.setBackground(Color.white);
        descriptionField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);
        
        // Price
        pricePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        pricePanel.setBackground(Color.white);
        pricePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        priceLabel = new JLabel("Prix", SwingConstants.RIGHT);
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        priceField = new JTextField();
        priceField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        priceField.setBackground(Color.white);
        priceField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        priceField.setPreferredSize(new Dimension(20, 50));
        
        pricePanel.add(priceLabel);
        pricePanel.add(priceField);
        
        // Amount
        amountPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        amountPanel.setBackground(Color.white);
        amountPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        amountLabel = new JLabel("Quantité", SwingConstants.RIGHT);
        amountLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        amountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        amountSpinner.setFont(new Font("SansSerif", Font.PLAIN, 20));
        amountSpinner.setBackground(Color.white);
        
        amountPanel.add(amountLabel);
        amountPanel.add(amountSpinner);
        
        // Vat Type
        vatTypePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        vatTypePanel.setBackground(Color.white);
        vatTypePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        vatTypeLabel = new JLabel("Type de TVA", SwingConstants.RIGHT);
        vatTypeLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));
        
        vatTypeComboBox = new JComboBox<String>();
        vatTypeComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        vatTypeComboBox.setBackground(Color.white);
        vatTypeComboBox.setPreferredSize(new Dimension(20, 25));
        
        ArrayList<Vat> vats = ApplicationController.getVats();
        for (Vat vat : vats) {
            vatTypeComboBox.addItem(vat.getType() + " (" + vat.getRate() + "%)");
        }
        
        vatTypePanel.add(vatTypeLabel);
        vatTypePanel.add(vatTypeComboBox);
        
        // Category
        categoryPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        categoryPanel.setBackground(Color.white);
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        categoryLabel = new JLabel("Catégorie", SwingConstants.RIGHT);
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        categoryComboBox = new JComboBox<String>();
        categoryComboBox.setFont(new Font("SansSerif", Font.PLAIN, 20));
        categoryComboBox.setBackground(Color.white);
        categoryComboBox.setPreferredSize(new Dimension(20, 25));
        
        ArrayList<Category> categories = ApplicationController.getCategories();
        for (Category category : categories) {
            categoryComboBox.addItem(category.getName());
        }
        
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);

        // Brand
        brandPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        brandPanel.setBackground(Color.white);
        brandPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        
        brandLabel = new JLabel("Marque", SwingConstants.RIGHT);
        brandLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        brandField = new JTextField();
        brandField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        brandField.setBackground(Color.white);
        brandField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        brandField.setPreferredSize(new Dimension(20, 25));
        
        brandPanel.add(brandLabel);
        brandPanel.add(brandField);
        
        // Start date
        startDatePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        startDatePanel.setBackground(Color.white);
        
        startDateLabelSubPanel = new JPanel(new GridLayout(1, 1, 0, 0));
        startDateLabelSubPanel.setBackground(Color.white);
        startDateFieldsSubPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        startDateFieldsSubPanel.setBackground(Color.white);
        
        startDateLabel = new JLabel("Date de mise en rayon", SwingConstants.RIGHT);
        startDateLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));
        
        startDateDayField = new JTextField();
        startDateDayField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        startDateDayField.setBackground(Color.white);
        startDateDayField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        startDateDayField.setText("Jour");
        startDateDayField.setForeground(Color.GRAY);

        backgroundText(startDateDayField, "Jour");
        
        startDateMonthField = new JTextField();
        startDateMonthField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        startDateMonthField.setBackground(Color.white);
        startDateMonthField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        startDateMonthField.setText("Mois");
        startDateMonthField.setForeground(Color.GRAY);
        
        backgroundText(startDateMonthField, "Mois");

        startDateYearField = new JTextField();
        startDateYearField.setFont(new Font("SansSerif", Font.PLAIN, 20));
        startDateYearField.setBackground(Color.white);
        startDateYearField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        startDateYearField.setText("Année");
        startDateYearField.setForeground(Color.GRAY);
        
        backgroundText(startDateYearField, "Année");
        
        startDateLabelSubPanel.add(startDateLabel);

        startDateFieldsSubPanel.add(startDateDayField);
        startDateFieldsSubPanel.add(startDateMonthField);
        startDateFieldsSubPanel.add(startDateYearField);
        
        startDatePanel.add(startDateLabelSubPanel);
        startDatePanel.add(startDateFieldsSubPanel);
        
        // Available
        availablePanel = new JPanel(new GridLayout(1, 2, 50, 0));
        availablePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0 ,0));
        availablePanel.setBackground(Color.white);
        
        AvailablelabelSubPanel = new JPanel(new GridLayout(1, 1, 0, 0));
        AvailablelabelSubPanel.setBackground(Color.white);
        AvailableRadioButtonSubPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        AvailableRadioButtonSubPanel.setBackground(Color.white);
        
        availableLabel = new JLabel("Disponible", SwingConstants.RIGHT);
        availableLabel.setFont(new Font("SansSerif", Font.PLAIN, 25));

        availableRadioButtonYes = new JRadioButton("oui");
        availableRadioButtonYes.setFont(new Font("SansSerif", Font.PLAIN, 20));
        availableRadioButtonYes.setSelected(true);
        availableRadioButtonNo = new JRadioButton("non");
        availableRadioButtonNo.setFont(new Font("SansSerif", Font.PLAIN, 20));

        availabilityGroup = new ButtonGroup();
        availabilityGroup.add(availableRadioButtonYes);
        availabilityGroup.add(availableRadioButtonNo);

        AvailablelabelSubPanel.add(availableLabel);
        AvailableRadioButtonSubPanel.add(availableRadioButtonYes);
        AvailableRadioButtonSubPanel.add(availableRadioButtonNo);
        
        availablePanel.add(AvailablelabelSubPanel);
        availablePanel.add(AvailableRadioButtonSubPanel);
        
        // Button
        buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 500, 0, 500));
        
        addButton = new JButton("Modifier");
        addButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        addButton.setBackground(Color.white);
        addButton.setFocusPainted(false);
        addButton.setEnabled(false);
        
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ApplicationController.productIsValid(nameField.getText(), descriptionField.getText(), priceField.getText(), 
                            (Integer) amountSpinner.getValue(), (String)vatTypeComboBox.getSelectedItem(), (String) categoryComboBox.getSelectedItem(), brandField.getText(), 
                            startDateDayField.getText(), startDateMonthField.getText(), startDateYearField.getText(), 
                            availableRadioButtonYes.isSelected());
                    removeAllField();
                } catch (FieldIsEmpty | WrongType | WrongDate ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        buttonPanel.add(addButton);
        
        // Add
        formPanel.add(barcodePanel);
        
        setVisibleAll(false);
        
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
    
    // Methods
    private void removeAllField() {
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        amountSpinner.setValue(0);
        vatTypeComboBox.setSelectedIndex(0);
        categoryComboBox.setSelectedIndex(0);
        brandField.setText("");
        startDateDayField.setText("Jour");
        startDateDayField.setForeground(Color.GRAY);
        startDateMonthField.setText("Mois");
        startDateMonthField.setForeground(Color.GRAY);
        startDateYearField.setText("Année");
        startDateYearField.setForeground(Color.GRAY);
        availableRadioButtonYes.setSelected(true);
        
        repaint();
        revalidate();
    }
    
    private void backgroundText(JTextField jTextField, String text) {
        jTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (jTextField.getText().equals(text)) {
                    jTextField.setText("");
                    jTextField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (jTextField.getText().isEmpty()) {
                    jTextField.setForeground(Color.GRAY);
                    jTextField.setText(text);
                }
            }
        });
    }
    
    private void setVisibleAll(boolean status) {
        namePanel.setVisible(status);
        descriptionPanel.setVisible(status);
        pricePanel.setVisible(status);
        amountPanel.setVisible(status);
        brandPanel.setVisible(status);
        vatTypePanel.setVisible(status);
        categoryPanel.setVisible(status);
        startDatePanel.setVisible(status);
        availablePanel.setVisible(status);
    }
}
