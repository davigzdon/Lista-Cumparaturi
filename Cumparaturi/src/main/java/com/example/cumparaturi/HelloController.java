package com.example.cumparaturi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.util.ArrayList;

public class HelloController {
    @FXML
    private TextField brandField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField filterField;
    @FXML
    private ListView<Product> productList;

    private final ObservableList<Product> observableProducts = FXCollections.observableArrayList();
    private final ProductService productService = new ProductService();
    private int nextId = 6; // ID-ul începe de la 6, după cele 5 produse inițiale

    @FXML
    private void initialize() {
        // Inițializăm datele predefinite (doar prima rulare)
        productService.initializeData();

        // Încărcăm produsele existente din fișier
        List<Product> loadedProducts = productService.loadProducts();
        observableProducts.setAll(loadedProducts);

        // Obținem cel mai mare ID deja folosit
        if (!loadedProducts.isEmpty()) {
            nextId = loadedProducts.stream().mapToInt(Product::getId).max().orElse(5) + 1;
        }

        // Setăm lista în ListView-ul din interfață
        productList.setItems(observableProducts);
    }

    @FXML
    private void onAddProductButtonClick() {
        try {
            // Citim input-urile de la utilizator
            String marca = brandField.getText();
            String nume = nameField.getText();
            String quantityText = quantityField.getText();

            // Validare pentru input-uri
            if (marca == null || marca.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Brand cannot be empty.");
                return;
            }

            if (nume == null || nume.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Name cannot be empty.");
                return;
            }

            if (quantityText == null || quantityText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Quantity cannot be empty.");
                return;
            }

            int cantitate = Integer.parseInt(quantityText);

            // Creăm un nou produs
            Product product = new Product(nextId++, marca, nume, cantitate);

            // Adăugăm produsul la listă și salvăm
            observableProducts.add(product);
            productService.saveProducts(new ArrayList<>(observableProducts));

            // Curățăm câmpurile de input
            brandField.clear();
            nameField.clear();
            quantityField.clear();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Please provide a valid number for quantity.");
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please provide valid product details.");
        }
    }

    @FXML
    private void onFilterButtonClick() {
        String filterText = filterField.getText().toLowerCase();

        // Filtrăm lista pe baza textului introdus
        List<Product> filteredProducts = observableProducts.stream()
                .filter(product -> (product.getNume() != null && product.getNume().toLowerCase().contains(filterText))
                        || (product.getMarca() != null && product.getMarca().toLowerCase().contains(filterText)))
                .toList();

        // Actualizăm lista afișată în ListView
        productList.setItems(FXCollections.observableArrayList(filteredProducts));
    }


    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}