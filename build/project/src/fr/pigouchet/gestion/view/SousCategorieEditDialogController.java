package fr.pigouchet.gestion.view;

import fr.pigouchet.gestion.model.SubCategorie;
import fr.pigouchet.gestion.util.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SousCategorieEditDialogController {
	@FXML
	 private ComboBox<SubCategorie> sousCategorieField;
	 @FXML
	 private TextField nomField;
	 private ObservableList<SubCategorie> subCategorieData = FXCollections.observableArrayList();
	 private Stage dialogStage;
	 private boolean okClicked = false;
	 private SubCategorie cate;

	 public ObservableList<SubCategorie> getSubCategorieData() {
	        return subCategorieData;
	    }

	 /**
	  * Sets the stage of this dialog.
	  *
	  * @param dialogStage
	  */
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	 }

	 public void setSubCategory() {
		 subCategorieData.addAll(Connection.getAllSubCategory());
	    // categorieData.addAll(tempCategorie);
		 sousCategorieField.setItems(getSubCategorieData());
	 }

	 /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() {

	        if (isInputValid()) {
	        	System.out.println(sousCategorieField.getSelectionModel().getSelectedItem().getId()+ " "+sousCategorieField.getSelectionModel().getSelectedItem().getNom());
	          //  cate.setId(categorieField.getSelectionModel().getSelectedItem().getId());
	           // cate.setNom(categorieField.getSelectionModel().getSelectedItem().getNom());
	           // cate.setProduit(categorieField.getSelectionModel().getSelectedItem().getProduit());
	            cate=sousCategorieField.getSelectionModel().getSelectedItem();
	            cate.setNom(nomField.getText());
	            SubCategorie aSubCategory = Connection.updateSubCategory(cate);

	            if(Integer.valueOf(aSubCategory.getId())==null){
	            	Alert alert = new Alert(AlertType.ERROR);
		            alert.initOwner(dialogStage);
		            alert.setTitle("Enregistrement");
		            alert.setHeaderText("Erreur");
		            alert.setContentText("Un problème est survenu pendant l'enregistrement.");

		            alert.showAndWait();
	            }else{
	            	Alert alert = new Alert(AlertType.INFORMATION);
	            	alert.setTitle("Information");
	            	alert.setHeaderText("Changement");
	            	alert.setContentText("Le changement va être pris en compte après le redémarrage de l'application.");

	            	alert.showAndWait();

	            }
	            okClicked = true;
	            dialogStage.close();
	        }
	    }

	 public boolean isOkClicked() {
		 return okClicked;
	 }

	 /**
	  * Called when the user clicks cancel.
	  */
	 @FXML
	 private void handleCancel() {
	 	dialogStage.close();
	 }

	 private boolean isInputValid() {
	        String errorMessage = "";

	        if (nomField.getText() == null || nomField.getText().length() == 0) {
	            errorMessage += "No valid code!\n";
	        }

	        if (sousCategorieField.selectionModelProperty().getValue().getSelectedItem() == null){
	            errorMessage += "No valid Categorie Field!\n";
	        }

	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.initOwner(dialogStage);
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(errorMessage);

	            alert.showAndWait();

	            return false;
	        }
	    }
}
