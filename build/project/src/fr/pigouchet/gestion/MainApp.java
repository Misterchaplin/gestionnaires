package fr.pigouchet.gestion;

import java.io.IOException;

import fr.pigouchet.gestion.model.Produit;
import fr.pigouchet.gestion.util.Connection;
import fr.pigouchet.gestion.view.CategorieEditDialogController;
import fr.pigouchet.gestion.view.ProduitEditDialogController;
import fr.pigouchet.gestion.view.ProduitOverviewController;
import fr.pigouchet.gestion.view.SousCategorieEditDialogController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Produit> produitData = FXCollections.observableArrayList();
    private ObservableList<Produit> produitDataTime = FXCollections.observableArrayList();

    public MainApp() {
       produitData.addAll(Connection.getAllProduct());
    }


    /**
     * Returns the data as an observable list of Produit.
     * @return
     */
    public ObservableList<Produit> getProduitData() {
        return produitData;
    }

    public ObservableList<Produit> getProduitDataTime() {
        return produitDataTime;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion des produits");
        this.primaryStage.getIcons().add(new Image("file:resources/images/gestion.png"));

        initRootLayout();
        showProduitOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Produit overview inside the root layout.
     */
    public void showProduitOverview() {
        try {
            // Load Produit overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GestionOverview.fxml"));
            AnchorPane ProduitOverview = (AnchorPane) loader.load();

            // Set Produit overview into the center of root layout.
            rootLayout.setCenter(ProduitOverview);

            ProduitOverviewController controller = loader.getController();
            controller.setMainApp(this, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Produit produit, Integer choice) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ProduitEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editer Produit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ProduitEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setproduit(produit, choice);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCategoryEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/CategorieEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editer Catégorie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CategorieEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategory();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showSubCategoryEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SousCategorieEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editer Sous Catégorie");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            SousCategorieEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSubCategory();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}