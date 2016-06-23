package fr.pigouchet.gestion.view;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import fr.pigouchet.gestion.MainApp;
import fr.pigouchet.gestion.model.Categorie;
import fr.pigouchet.gestion.util.Connection;
import fr.pigouchet.gestion.util.GeneratePdf;
import fr.pigouchet.gestion.util.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceDialog;


/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Reference to the main application
    @SuppressWarnings("unused")
	private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Générer tous les PDF
     */
    @FXML
    private void handleGenerateAllPdf() {
    	List<Categorie> cate = Connection.getAllCategory();
    	if(cate.size()>0){
	    	fr.pigouchet.gestion.model.Utils link = Utils.searchDataUtil("pdf");
	    	if(link instanceof fr.pigouchet.gestion.model.Utils && link.getNom()==null){
		    	Stage stageFileChooser = new Stage();
		    	DirectoryChooser chooser = new DirectoryChooser();
		    	chooser.setTitle("Choisir un répertoire");

		    	File selectedDirectory = chooser.showDialog(stageFileChooser);
		    	if(selectedDirectory != null){
		    		fr.pigouchet.gestion.model.Utils theObject=new fr.pigouchet.gestion.model.Utils();
		    		theObject.setNom("pdf");
		    		theObject.setData(selectedDirectory.getAbsolutePath());
		    		if(!Connection.insertPath(theObject).equals(null)){
		    			System.out.println("Insertion OK");
		    			try {
		    		    	   GeneratePdf.createFullPage();
		    		       } catch (IOException e) {
		    		    	   e.printStackTrace();
		    		       }
		    		}
		    	}
	    	}else{
	    		try {
	 	    	   GeneratePdf.createFullPage();
	 	       } catch (IOException e) {
	 	    	   e.printStackTrace();
	 	       }
	    	}
    	}else{
    		messageAlert();
    	}
    }

    /**
     * Générer  les PDF par categorie
     */
    @FXML
    private void handleGenerateByCategoryPdf() {
    	List<Categorie> lesCategory = Connection.getAllCategory();
    	if(lesCategory.size()>0){
	    	ChoiceDialog<Categorie> dialog = new ChoiceDialog<>(lesCategory.get(0), lesCategory);
	    	dialog.setTitle("Choisir Catégorie");
	    	dialog.setHeaderText("Choisir la catégorie à générer");

	    	Optional<Categorie> result = dialog.showAndWait();
	    	if (result.isPresent()){
	    	   // System.out.println("Your choice: " + result.get());
	    		fr.pigouchet.gestion.model.Utils link = Utils.searchDataUtil("pdf");
	    		if(link instanceof fr.pigouchet.gestion.model.Utils && link.getNom()==null){
		    		Stage stageFileChooser = new Stage();
			    	DirectoryChooser chooser = new DirectoryChooser();
			    	chooser.setTitle("Choisir un répertoire");

			    	File selectedDirectory = chooser.showDialog(stageFileChooser);
			    	if(selectedDirectory != null){
			    		fr.pigouchet.gestion.model.Utils theObject=new fr.pigouchet.gestion.model.Utils();
			    		theObject.setNom("pdf");
			    		theObject.setData(selectedDirectory.getAbsolutePath());
			    		if(!Connection.insertPath(theObject).equals(null)){
			    			System.out.println("Insertion OK");
			    			GeneratePdf.createPageByCategory(result.get());
			    		}
			    	}
	    		}else{
	    			GeneratePdf.createPageByCategory(result.get());
	    		}
	    	}
    	}else{
    		messageAlert();
    	}
    }

    /**
     * Opens a FileChooser to let the user select an path pdf to load.
     */
    @FXML
    private void handleOpen(){
    	Stage stageFileChooser = new Stage();
    	DirectoryChooser chooser = new DirectoryChooser();
    	chooser.setTitle("Choisir un répertoire");
    	fr.pigouchet.gestion.model.Utils link = Utils.searchDataUtil("pdf");
    	if(link instanceof fr.pigouchet.gestion.model.Utils && link.getNom()!=null){
    		File linkFile = new File(link.getData());
    		chooser.setInitialDirectory(linkFile);
    	}
    	File selectedDirectory = chooser.showDialog(stageFileChooser);
    	if(selectedDirectory != null){
    		fr.pigouchet.gestion.model.Utils theObject=new fr.pigouchet.gestion.model.Utils();
    		theObject.setNom("pdf");
    		theObject.setData(selectedDirectory.getAbsolutePath());
    		if(!Connection.insertPath(theObject).equals(null)){
    			Alert alert = new Alert(AlertType.INFORMATION);
    	        alert.setTitle("Information");
    	        alert.setHeaderText("Répertoire de destination");
    	        alert.setContentText("Le répertoire à bien été ajouté");

    	        alert.showAndWait();
    		}
    	}
    }


    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Gestion");
        alert.setHeaderText("About");
        alert.setContentText("Auteur: Beaugrand Charly");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    private void messageAlert(){
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setTitle("Erreur");
    	alert.setHeaderText(null);
    	alert.setContentText("Aucune données à traiter.");

    	alert.showAndWait();
    }

}