package fr.pigouchet.gestion.view;

import java.util.List;
import java.util.Optional;

import fr.pigouchet.gestion.MainApp;
import fr.pigouchet.gestion.model.Categorie;
import fr.pigouchet.gestion.model.Produit;
import fr.pigouchet.gestion.model.SubCategorie;
import fr.pigouchet.gestion.util.Connection;
import fr.pigouchet.gestion.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class ProduitOverviewController {
	@FXML
    private TableView<Produit> produitTable;

	@FXML
    private TableColumn<Produit, String> codeColumn;
    @FXML
    private TableColumn<Produit, String> nomColumn;
    @FXML
    private TableColumn<Produit, String> priceAchatColumn;
    @FXML
    private TableColumn<Produit, String> priceArtisantColumn;
    @FXML
    private TableColumn<Produit, String> priceDetailColumn;

    @FXML
    private TextField search;
    @FXML
    private Label codeLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label priceAchatLabel;
    @FXML
    private Label priceArtisantLabel;
    @FXML
    private Label priceDetailLabel;
    @FXML
    private Button btDeleteCategory;
    @FXML
    private Label lblInfoProduit;
    @FXML
    private Label lblInfoSousCategorie;
    @FXML
    private Label lblInfoCategorie;
    @FXML
    private Tooltip tlpInfoSousCategorie;

    private Stage dialogStage;

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ProduitOverviewController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the Produit table with the two columns.
        codeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        priceAchatColumn.setCellValueFactory(cellData -> cellData.getValue().priceAchatProperty());
        priceArtisantColumn.setCellValueFactory(cellData -> cellData.getValue().priceArtisantProperty());
        priceDetailColumn.setCellValueFactory(cellData -> cellData.getValue().priceDetailProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        produitTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp, boolean level) {
        this.mainApp = mainApp;

        if(level==false){
	        // Add observable list data to the table
	        produitTable.setItems(mainApp.getProduitData());
        }else{
        	produitTable.setItems(mainApp.getProduitDataTime());
        }
    }

    public TableView<Produit> getProduitTable() {
		return produitTable;
	}

	/**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Produit produit) {
        if (produit != null) {
            // Fill the labels with info from the person object.
        	//System.out.println(produit.getCode());
            lblInfoProduit.setText(produit.getNom());
            lblInfoSousCategorie.setText(produit.getSubCategorie().getNom());
            lblInfoCategorie.setText(produit.getSubCategorie().getCategorie().getNom());
        } else {
            // Person is null, remove all the text.
        	 lblInfoProduit.setText("");
             lblInfoSousCategorie.setText("");
             lblInfoCategorie.setText("");
        }
    }

    @FXML
    private void handleDeleteProduit() {
        int selectedIndex = produitTable.getSelectionModel().getSelectedIndex();
        Produit selectedPerson = produitTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
	        Produit aProduit = Connection.deleteProduct(produitTable.getSelectionModel().getSelectedItem());
	        if(Integer.valueOf(aProduit.getId())==null){
	        	Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Suppression");
	            alert.setHeaderText("Erreur");
	            alert.setContentText("Un problème est survenu pendant la suppression.");

	            alert.showAndWait();
	        }else{
	        	Alert alert = new Alert(AlertType.CONFIRMATION);
	        	alert.setTitle("Confirmation de suppression");
	        	alert.setHeaderText("Êtes-vous sûr de vouloir supprimer : "+produitTable.getSelectionModel().getSelectedItem().getNom());
	        //	alert.setContentText("Choose your option.");

	        	ButtonType buttonTypeOne = new ButtonType("Oui");
	        	ButtonType buttonTypeCancel = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

	        	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

	        	Optional<ButtonType> result = alert.showAndWait();
	        	if (result.get() == buttonTypeOne){
	        		 produitTable.getItems().remove(selectedIndex);
	        	}else {
	        		alert.close();
	        	}
	        }
        }else{
        	 Alert alert = new Alert(AlertType.WARNING);
             alert.initOwner(mainApp.getPrimaryStage());
             alert.setTitle("Pas de sélection");
             alert.setHeaderText("Pas de produit sélectionné.");
             alert.setContentText("Veuillez sélectionner un produit.");

             alert.showAndWait();
        }
    }


    @FXML
    private void handleDeleteCategorie() {
       List<Categorie> allCategory = Connection.getAllCategory();
       if(!allCategory.isEmpty()){
	       ChoiceDialog<Categorie> dialog = new ChoiceDialog<>(allCategory.get(0), allCategory);
	       dialog.setTitle("Supprimer une category");
	       dialog.setHeaderText("Veuillez choisir une catégorie");

	       // Traditional way to get the response value.
	       Optional<Categorie> result = dialog.showAndWait();
	       int data = Connection.categoryToProduct(result.get());
	       int dataSub = Connection.categoryToSubCategory(result.get());
	       List<Produit> lesProduits = Connection.listCategoryToProduct(result.get());
	       List<SubCategorie> lesSousCategorie = Connection.listCategoryToSubCategory(result.get());

	       if (result.isPresent() && data==0 && dataSub==0){
	            Alert alert = new Alert(AlertType.CONFIRMATION);
		       	alert.setTitle("Confirmation de suppression");
		       	alert.setHeaderText("Êtes-vous sûr de vouloir supprimer : "+result.get().getNom());

		       	ButtonType buttonTypeOne = new ButtonType("Oui");
		       	ButtonType buttonTypeCancel = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

		       	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		       	Optional<ButtonType> resultChoix = alert.showAndWait();
		       	if (resultChoix.get() == buttonTypeOne){
		       		 System.out.println(Connection.deleteCategorie(result.get()));
		       	}else {
		       		alert.close();
		       	}
	       }else{
	    	   if(data!=0){
		    	   Alert alert = new Alert(AlertType.CONFIRMATION);
		           alert.initOwner(mainApp.getPrimaryStage());
		           alert.setTitle("Erreur");
		           alert.setHeaderText("Impossible de supprimer la catégorie");
		           alert.setContentText("La catégorie est utilisée par un ou plusieurs produits.\nVoulez-vous quand même supprimer ?\n(Les produits et sous catégories vont être supprimés.)");
		           Optional<ButtonType> resultConfProduit = alert.showAndWait();
		           if (resultConfProduit.get() == ButtonType.OK){
		        	   for (Produit produit : lesProduits) {
		        		   int size=0;
			        	   boolean check=false;
			        	   while (size < produitTable.getItems().size() || check==false) {
			        		   System.out.println("ici");
			        		   if(produitTable.getItems().get(size).getId()==produit.getId()){
			        			   System.out.println("sup : "+Connection.deleteProduct(produit).getNom());
			        			   produitTable.getItems().remove(produitTable.getItems().indexOf(produitTable.getItems().get(size)));
			        			   check=true;
			        		   }
			        		   size++;
			        	   }
		               }
		              /* for (Produit produit : lesProduits) {
		            	   System.out.println(Connection.deleteProduct(produit));
		            	   for (Produit lesProd : produitTable.getItems()) {
		            		   System.out.println(lesProd.getNom());
		            		   if(lesProd.getId()==produit.getId()){
		            			   produitTable.getItems().remove(produitTable.getSelectionModel().getSelectedIndex());
		            		   }
		            	   }
		               }*/

		               for (SubCategorie subCategorie : lesSousCategorie) {
		            	   System.out.println(Connection.SubCategorie(subCategorie));
		               }
		               System.out.println(Connection.deleteCategorie(result.get()));
		           } else {
		        	   alert.close();
		           }
	    	   }else if (dataSub!=0) {
	    		   Alert alert = new Alert(AlertType.CONFIRMATION);
		           alert.initOwner(mainApp.getPrimaryStage());
		           alert.setTitle("Erreur");
		           alert.setHeaderText("Impossible de supprimer la catégorie");
		           alert.setContentText("La catégorie est utilisée par une ou plusieurs sous catégories.\nVoulez-vous quand même supprimer ?\n(Les sous catégories vont être supprimés.)");
		           Optional<ButtonType> resultConfProduit = alert.showAndWait();
		           if (resultConfProduit.get() == ButtonType.OK){
		               for (SubCategorie subCategorie : lesSousCategorie) {
		            	   System.out.println("subCategorie : "+ subCategorie);
		            	   System.out.println(Connection.SubCategorie(subCategorie));
		               }
		           }else {
		        	   alert.close();
		           }
	    	   }else{
	    		   Alert alert = new Alert(AlertType.WARNING);
		           alert.initOwner(mainApp.getPrimaryStage());
		           alert.setTitle("Erreur");
		           alert.setHeaderText("Impossible de supprimer la catégorie");
		           alert.setContentText("Mauvaise sélection.");
		           alert.showAndWait();
	    	   }
	       }
       }else{
    	   Alert alert = new Alert(AlertType.WARNING);
           alert.initOwner(mainApp.getPrimaryStage());
           alert.setTitle("Pas de sélection");
           alert.setHeaderText("Pas de catégorie disponible");

           alert.showAndWait();
       }
   }

    @FXML
    private void handleDeleteSousCategorie() {
       List<SubCategorie> allSubCategory = Connection.getAllSubCategory();
       if(!allSubCategory.isEmpty()){
	       ChoiceDialog<SubCategorie> dialog = new ChoiceDialog<>(allSubCategory.get(0), allSubCategory);
	       dialog.setTitle("Supprimer une sous category");
	       dialog.setHeaderText("Veuillez choisir une sous catégorie");

	       // Traditional way to get the response value.
	       Optional<SubCategorie> result = dialog.showAndWait();
	       int dataProd = Connection.SubCategoryToProduit(result.get());
	       List<Produit> lesProduits = Connection.listSubCategoryToProduit(result.get());

	       if (result.isPresent() && dataProd==0){
	            Alert alert = new Alert(AlertType.CONFIRMATION);
		       	alert.setTitle("Confirmation de suppression");
		       	alert.setHeaderText("Êtes-vous sûr de vouloir supprimer : "+result.get().getNom());

		       	ButtonType buttonTypeOne = new ButtonType("Oui");
		       	ButtonType buttonTypeCancel = new ButtonType("Non", ButtonData.CANCEL_CLOSE);

		       	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		       	Optional<ButtonType> resultChoix = alert.showAndWait();
		       	if (resultChoix.get() == buttonTypeOne){
		       		System.out.println("le resultat : "+result.get());
		       		 System.out.println(Connection.SubCategorie(result.get()));
		       	}else {
		       		alert.close();
		       	}
	       }else{
	    	   if(dataProd!=0){
		    	   Alert alert = new Alert(AlertType.CONFIRMATION);
		           alert.initOwner(mainApp.getPrimaryStage());
		           alert.setTitle("Erreur");
		           alert.setHeaderText("Impossible de supprimer la sous catégorie");
		           alert.setContentText("La catégorie est utilisée par un ou plusieurs produits.\nVoulez-vous quand même supprimer ?\n(Les produits vont être supprimés.)");
		           Optional<ButtonType> resultConfProduit = alert.showAndWait();
		           if (resultConfProduit.get() == ButtonType.OK){
		        	   for (Produit produit : lesProduits) {
		        		   int size=0;
			        	   boolean check=false;
			        	   while (size < produitTable.getItems().size() || check==false) {
			        		   if(produitTable.getItems().get(size).getId()==produit.getId()){
			        			   System.out.println("sup : "+Connection.deleteProduct(produit).getNom());
			        			   produitTable.getItems().remove(produitTable.getItems().indexOf(produitTable.getItems().get(size)));
			        			   check=true;
			        		   }
			        		   size++;
			        	   }
		               }
		        	   System.out.println(Connection.SubCategorie(result.get()));
		           } else {
		        	   alert.close();
		           }
	    	   }else{
	    		   Alert alert = new Alert(AlertType.WARNING);
		           alert.initOwner(mainApp.getPrimaryStage());
		           alert.setTitle("Erreur");
		           alert.setHeaderText("Impossible de supprimer la catégorie");
		           alert.setContentText("Mauvaise sélection.");
		           alert.showAndWait();
	    	   }
	       }
       }else{
    	   Alert alert = new Alert(AlertType.WARNING);
           alert.initOwner(mainApp.getPrimaryStage());
           alert.setTitle("Pas de sélection");
           alert.setHeaderText("Pas de catégorie disponible");

           alert.showAndWait();
       }
   }


    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewProduit() {
    	Produit tempProduit = new Produit();
        boolean okClicked = mainApp.showPersonEditDialog(tempProduit,0);
        if (okClicked) {
            mainApp.getProduitData().add(tempProduit);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditProduit() {
        Produit selectedPerson = produitTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson,1);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Pas de sélection");
            alert.setHeaderText("Pas de produit sélectionné.");
            alert.setContentText("Veuillez sélectionner un produit.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditCategory() {
    	//List<Categorie> tempCategorie = Connection.getAllCategory();
        boolean okClicked = mainApp.showCategoryEditDialog();
        if (okClicked) {
           // mainApp.getProduitData().add(tempCategorie);
        	System.out.println("bon");
        }
    }

    @FXML
    private void handleEditSubCategory() {
    	//List<SubCategorie> tempSousCategorie = Connection.getAllSubCategory();
        boolean okClicked = mainApp.showSubCategoryEditDialog();
        if (okClicked) {
           // mainApp.getProduitData().add(tempCategorie);
        	System.out.println("bon");
        }
    }

    /**
     * Display information when cursor stay on label product
     */
    @FXML
    private void getInfoStationnaryProduct(){
    	Tooltip tooltip = new Tooltip();
    	tooltip.setText(lblInfoProduit.getText());
    	lblInfoProduit.setTooltip(tooltip);
    }

    /**
     * Display information when cursor stay on label sub category
     */
    @FXML
    private void getInfoStationnarySubCategory(){
    	//System.out.println(lblInfoSousCategorie.getText());
    	Tooltip tooltip = new Tooltip();
    	tooltip.setText(lblInfoSousCategorie.getText());
    	lblInfoSousCategorie.setTooltip(tooltip);
    }

    /**
     * Display information when cursor stay on label category
     */
    @FXML
    private void getInfoStationnaryCategory(){
    	Tooltip tooltip = new Tooltip();
    	tooltip.setText(lblInfoCategorie.getText());
    	lblInfoCategorie.setTooltip(tooltip);
    }

    /**
     * Moteur de recherche
     *
     * @param String
     */
    @FXML
    public void searchEngine() {
       String movie=search.getText().toLowerCase();
       mainApp.getProduitDataTime().clear();

       if(movie.length() == 0){
    	   setMainApp(mainApp, false);
       }else{
	       for (Produit o : mainApp.getProduitData()) {
	    	   try{
	    		   String toProduct = Utils.translate(o.getNom().toLowerCase());
	    		   String toSearch = Utils.translate(movie.toLowerCase());

		    	   if(toProduct.indexOf(toSearch) != -1 || o.getCode().toLowerCase().indexOf(movie.toLowerCase()) != -1){
		    		   System.out.println("product : "+toProduct);
		    		   mainApp.getProduitDataTime().add(o);
	    			  // mainApp.getProduitDataTime().add(o);
	    		   }
	    	   }catch (Exception e) {
					// TODO: handle exception
				}
	       }

	       setMainApp(mainApp, true);
       }
    }
}