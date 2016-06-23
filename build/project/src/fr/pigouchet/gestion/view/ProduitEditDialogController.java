package fr.pigouchet.gestion.view;

import fr.pigouchet.gestion.model.Categorie;
import fr.pigouchet.gestion.model.Produit;
import fr.pigouchet.gestion.model.SubCategorie;
import fr.pigouchet.gestion.util.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a produit.
 *
 * @author Marco Jakob
 */
public class ProduitEditDialogController {

	    @FXML
	    private TextField codeField;
	    @FXML
	    private TextField nomField;
	    @FXML
	    private TextField priceAchatField;
	    @FXML
	    private TextField priceArtisantField;
	    @FXML
	    private TextField priceDetailField;
	    @FXML
	    private ComboBox<Categorie> categorieCombo;
	    @FXML
	    private ComboBox<SubCategorie> subCategorieCombo;

	    private Stage dialogStage;
	    private Produit produit;
	    private Integer choice;
	    private boolean okClicked = false;
	    private ObservableList<Categorie> categorieData = FXCollections.observableArrayList();
	    private ObservableList<SubCategorie> subCategorieData = FXCollections.observableArrayList();
	    private ObservableList<SubCategorie> categorieToSubCategorieData = FXCollections.observableArrayList();
	    private Categorie categorieChoose;
	    private SubCategorie subCategorieChoose;

	    public ObservableList<Categorie> getCategorieData() {
	        return categorieData;
	    }

	    public ObservableList<SubCategorie> getSubCategorieData() {
	        return subCategorieData;
	    }
	    public ObservableList<SubCategorie> getCategorieToSubCategorieData() {
	        return categorieToSubCategorieData;
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {

	    }

	    /**
	     * Sets the stage of this dialog.
	     *
	     * @param dialogStage
	     */
	    public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	    }

	    /**
	     * Sets the produit to be edited in the dialog.
	     *
	     * @param produit
	     */
	    public void setproduit(Produit produit, Integer choice) {
	    	this.choice = choice;
	        this.produit = produit;
	        categorieData.addAll(Connection.getAllCategory());
	        subCategorieData.addAll(Connection.getAllSubCategory());


	        codeField.setText(produit.getCode());
	        nomField.setText(produit.getNom());
	        priceAchatField.setText(produit.getPriceAchat());
	        priceArtisantField.setText(produit.getPriceArtisant());
	        priceDetailField.setText(produit.getPriceDetail());
	        categorieCombo.setItems(getCategorieData());

	        if(choice==1){
	        	categorieCombo.getSelectionModel().select(produit.getSubCategorie().getCategorie());
	        	categorieToSubCategorieData.addAll(Connection.listCategoryToSubCategory(produit.getSubCategorie().getCategorie()));
	        	subCategorieCombo.setItems(getCategorieToSubCategorieData());
	        	subCategorieCombo.getSelectionModel().select(produit.getSubCategorie());
	        }
	    }

	    @FXML
	    private void addSubCategorie(){
	    	if(categorieCombo.getSelectionModel().getSelectedItem() instanceof Categorie){
	    		subCategorieCombo.getItems().clear();
	    		subCategorieCombo.getEditor().setText("");
		    	Categorie selectedPerson = categorieCombo.getSelectionModel().getSelectedItem();
	            categorieToSubCategorieData.addAll(Connection.listCategoryToSubCategory(selectedPerson));
	            subCategorieCombo.setItems(getCategorieToSubCategorieData());
	            if(choice==1){
		        	subCategorieCombo.getSelectionModel().select(produit.getSubCategorie());
		        }
	    	}
	    }

	    /**
	     * Returns true if the user clicked OK, false otherwise.
	     *
	     * @return
	     */
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() {
	    	Produit aProduit = null;
	        if (isInputValid()) {
	            produit.setCode(codeField.getText());
	            produit.setNom(nomField.getText());
	            produit.setPriceAchat(priceAchatField.getText());
	            produit.setPriceArtisant(priceArtisantField.getText());
	            produit.setPriceDetail(priceDetailField.getText());
	            Categorie cateCombo = new Categorie();
	            cateCombo.setNom(categorieCombo.getEditor().getText());
	            SubCategorie subCateCombo = new SubCategorie();
	            subCateCombo.setNom(subCategorieCombo.getEditor().getText());
	            boolean checkValCate=false;
	            boolean checkValSubCate=false;
	            for (Categorie categorie : categorieData) {
					if(categorie.getNom().equals(cateCombo.getNom())){
						categorieChoose=categorie;
						checkValCate=true;
					}
				}
	            for (SubCategorie subcategorie : subCategorieData) {
					if(subcategorie.getNom().equals(subCateCombo.getNom())){
						subCategorieChoose=subcategorie;
						checkValSubCate=true;
					}
				}

	            if(!checkValSubCate){
					SubCategorie subCat=new SubCategorie();
					subCat.setNom(subCategorieCombo.getEditor().getText());
					System.out.println("the   "+subCat);
					subCategorieChoose=Connection.subCat(subCat);

				}
	            if(!checkValCate){
					Categorie cat=new Categorie();
					cat.setNom(categorieCombo.getEditor().getText());
					categorieChoose=Connection.cat(cat);
				}

	            if(subCategorieChoose != null){
	            	subCategorieChoose.setCategorie(categorieChoose);
	            	subCategorieChoose=Connection.updateSubCategory(subCategorieChoose);
	            	produit.setSubCategorie(subCategorieChoose);
	            }

	            if(choice.equals(0)){
	            	aProduit = Connection.insertProduct(produit);
	            }
	            else if (choice.equals(1)) {
	            	aProduit = Connection.updateProduct(produit);
				}

	            if(Integer.valueOf(aProduit.getId())==null){
	            	Alert alert = new Alert(AlertType.ERROR);
		            alert.initOwner(dialogStage);
		            alert.setTitle("Enregistrement");
		            alert.setHeaderText("Erreur");
		            alert.setContentText("Un problème est survenu pendant l'enregistrement.");

		            alert.showAndWait();
	            }
	            okClicked = true;
	            dialogStage.close();
	        }
	    }

	    /**
	     * Called when the user clicks cancel.
	     */
	    @FXML
	    private void handleCancel() {
	        dialogStage.close();
	    }

	    /**
	     * Validates the user input in the text fields.
	     *
	     * @return true if the input is valid
	     */
	    private boolean isInputValid() {
	        String errorMessage = "";

	        if (codeField.getText() == null || codeField.getText().length() == 0) {
	            errorMessage += "Erreur sur le champ Code!\n";
	        }
	        if (nomField.getText() == null || nomField.getText().length() == 0) {
	            errorMessage += "Erreur sur le champ Nom!\n";
	        }
	        if (priceAchatField.getText() == null || priceAchatField.getText().length() == 0) {
	            errorMessage += "Erreur sur le champ Achat!\n";
	        }else {
	            // try to parse the postal code into an int.
	            try {
	                Float.parseFloat(priceAchatField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Erreur sur le prix Achat (doit être un entier ou à virgule)!\n";
	            }
	        }

	        if (priceArtisantField.getText() == null || priceArtisantField.getText().length() == 0) {
	            errorMessage += "Erreur sur le champ Artisant!\n";
	        } else {
	            // try to parse the postal code into an int.
	            try {
	                Float.parseFloat(priceArtisantField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Erreur sur le prix Artisant (doit être un entier ou à virgule)!\n";
	            }
	        }

	        if (priceDetailField.getText() == null || priceDetailField.getText().length() == 0) {
	            errorMessage += "Erreur sur le champ Detail!\n";
	        }else {
	            // try to parse the postal code into an int.
	            try {
	                Float.parseFloat(priceDetailField.getText());
	            } catch (NumberFormatException e) {
	                errorMessage += "Erreur sur le prix Detail (doit être un entier ou à virgule)!\n";
	            }
	        }

	        if (subCategorieCombo.getEditor().getText().length()==0){
	            errorMessage += "Erreur sur le champ Sous Catégorie!\n";
	        }

	        if (categorieCombo.getEditor().getText().length()==0){
	            errorMessage += "Erreur sur le champ Catégorie!\n";
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Champs invalide");
	            alert.setHeaderText("Veuillez correctement remplir les champs.");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	    }


	}