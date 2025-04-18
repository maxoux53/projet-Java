package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JPanel {
    // Attributes
    private Window window;
    private JMenuBar menuBar;
    private JMenu application;
    private JMenuItem home;
    private JMenuItem leave;
    private JMenuItem signOut;
    private JMenu product;
    private JMenuItem addProduct;
    private JMenuItem editProduct;
    
    // Constructors
    public Menu(Window window) {
        this.window = window;
        menuBar = new JMenuBar();

        setBackground(Color.WHITE);

        // Menu
        application = new JMenu("Application");
        product = new JMenu("Article");

        // Application
        
        // Home
        home = new JMenuItem("Accueil");
        
        home.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                window.showHome();
            }
        });
        
        // SignOut
        signOut = new JMenuItem("Se déconnecter");

        signOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grayOut();
                window.showLogin();
            }
        });
        
        // Leave
        leave = new JMenuItem("Quitter");

        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        application.add(home);
        application.add(signOut);
        application.add(leave);

        // CRUD
        
        // Product
        
        // Add
        addProduct = new JMenuItem("Ajouter un article");
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.showAddProduct();
            }
        });
        product.add(addProduct);
        
        // Edit
        editProduct = new JMenuItem("Modifier un article");
        editProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.showEditProduct();
            }
        });
        product.add(editProduct);
        
        // Add to menu
        menuBar.add(application);
        menuBar.add(product);
    }

    // Getters
    public JMenuBar getMenuBar() {
        return menuBar;
    }

    // Methods
    public void toggleMenu(boolean status) {
        home.setEnabled(status);
        
        addProduct.setEnabled(status);

        signOut.setEnabled(status);

        menuBar.repaint();
    }

    public void grayOut() {
        toggleMenu(false);
    }

    public void activate() {
        toggleMenu(true);
    }
}    
