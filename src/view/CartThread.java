package view;

import javax.swing.*;

public class CartThread extends Thread {
    // Attributes
    private Home homePage;
    private boolean running = false; 
    
    // Constructors
    public CartThread(Home homePage) {
        this.homePage = homePage;
    }

    public synchronized void setRunning(boolean isHomePageActive) {
        this.running = isHomePageActive;
        if (running) {
            notify();
        }
    }

    @Override
    public void run() {
        Cart cart = homePage.getCart();
        
        while (true) {
            synchronized (this) {
                if (!running) {
                    try {
                        wait();
                    } catch (InterruptedException interruptedException) {
                        System.exit(0);
                    }
                }
            }
                
            cart.move();
            homePage.repaint();

            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Le temps d'attente de l'animation du chariot a échoué.\nErreur : " + e.getMessage(), "Critical Thread Error", JOptionPane.ERROR_MESSAGE); // should have a parent component?
                System.exit(1); // DEBUG ONLY, SHOULD BE REMOVED IN THE NEAR FUTURE!! ⚠️
            }
        }
    }
}
