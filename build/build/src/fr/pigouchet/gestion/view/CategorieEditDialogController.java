package fr.pigouchet.gestion.view;

import fr.pigouchet.gestion.MainApp;
import fr.pigouchet.gestion.model.Categorie;
import fr.pigouchet.gestion.util.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CategorieEditDialogController {
	 @FXML
	 private ComboBox<Categorie> categorieField;
	 @FXML
	 private TextField nomField;
	 private ObservableList<Categorie> categorieData = FXCollections.observableArrayList();
	 private Stage dialogStage;
	 private boolean okClicked = false;
	 private Categorie cate;
	 @SuppressWarnings("unused")
	private MainApp mainApp;

	 public ObservableList<Categorie> getCategorieData() {
	        return categorieData;
	    }

	 /**
	  * Sets the stage of this dialog.
	  *
	  * @param dialogStage
	  */
	 public void setDialogStage(Stage dialogStage) {
	        this.dialogStage = dialogStage;
	 }

	 public void setCategory() {
		 categorieData.addAll(Connection.getAllCategory());
	    // categorieData.addAll(tempCategorie);
	     categorieField.setItems(getCategorieData());
	 }

	 /**
	     * Called when the user clicks ok.
	     */
	    @FXML
	    private void handleOk() {

	        if (isInputValid()) {
	        	System.out.println(categorieField.getSelectionModel().getSelectedItem().getId()+ " "+categorieField.getSelectionModel().getSelectedItem().getNom());
	          //  cate.setId(categorieField.getSelectionModel().getSelectedItem().getId());
	           // cate.setNom(categorieField.getSelectionModel().getSelectedItem().getNom());
	           // cate.setProduit(categorieField.getSelectionModel().getSelectedItem().getProduit());
	            cate=categorieField.getSelectionModel().getSelectedItem();
	            cate.setNom(nomField.getText());
	            Categorie aCategory = Connection.updateCategory(cate);

	            if(Integer.valueOf(aCategory.getId())==null){
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

	        if (categorieField.selectionModelProperty().getValue().getSelectedItem() == null){
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
