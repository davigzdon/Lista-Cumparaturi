package com.example.cumparaturi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final String FILE_NAME = "produs.dat";

    // Încărcarea listei de produse dintr-un fișier binar
    @SuppressWarnings("unchecked")
    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_NAME);

        // Dacă fișierul nu există, returnăm o listă goală
        if (!file.exists()) {
            System.out.println("File not found. A new one will be created after saving.");
            return products;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            products = (List<Product>) ois.readObject();
        } catch (EOFException e) {
            System.out.println("File is empty or corrupt. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Salvarea listei de produse într-un fișier binar
    public void saveProducts(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
            System.out.println("Produsele au fost salvate în fișierul binar.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Popularea inițială a fișierului cu produse (doar pentru prima dată)
    public void initializeData() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            // Dacă fișierul există deja, nu mai inițializăm datele
            return;
        }

        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(new Product(1, "Samsung", "Galaxy S22", 10));
        initialProducts.add(new Product(2, "Apple", "iPhone 14", 15));
        initialProducts.add(new Product(3, "LG", "Smart TV", 0));
        initialProducts.add(new Product(4, "Sony", "PlayStation 5", 3));
        initialProducts.add(new Product(5, "Bosch", "Vacuum Cleaner", 8));

        saveProducts(initialProducts);
    }
}