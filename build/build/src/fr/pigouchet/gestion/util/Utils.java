package fr.pigouchet.gestion.util;

import java.text.Normalizer;
import java.util.List;
import fr.pigouchet.gestion.MainApp;
import fr.pigouchet.gestion.model.Produit;

public class Utils {
	private MainApp mainApp;

	private static List<Produit> produitTable;

	public static List<Produit> getProduitTable() {
		return produitTable;
	}

	/*public void setProduitTable(List<Produit> produitTable) {
		Utils.produitTable = produitTable;
	}*/

	public void setMainApp(MainApp mainApp, boolean level) {
		this.mainApp = mainApp;
		System.out.println(mainApp.toString());
		//Utils.produitTable.addAll(mainApp.getProduitData());
		System.out.println(mainApp.getProduitData());
	}

	public static fr.pigouchet.gestion.model.Utils searchDataUtil(String data){
		fr.pigouchet.gestion.model.Utils myData = new fr.pigouchet.gestion.model.Utils();
		fr.pigouchet.gestion.model.Utils map = (fr.pigouchet.gestion.model.Utils) Connection.selectUtils(data);
		if(map instanceof fr.pigouchet.gestion.model.Utils){
			System.out.println(map.getId()+" "+map.getNom()+" "+map.getData());
			myData.setId(map.getId());
			myData.setNom(map.getNom());
			myData.setData(map.getData());
		}
		return myData;
	}

	/*
	 *
	 * Ajouter fonction avec paramètre pour dire ce que l'on veut récupérer dans la List
	 * des données retourné par Connection.selectUtils()
	 *
	 * Si on veut le chemin des pdf:
	 * 		foreach
	 * 			Si champ non vaut "pdf" alors le chemin existe
	 *
	 *
	 */

	/* public static boolean findProduct(Produit product){
	    	boolean check=false;
	    	ObservableList<Produit> lesProduits = getProduitTable();
	    	System.out.println(lesProduits.size());
	    	for (Produit produit : lesProduits) {
	    		if(produit.getCode().equals(product.getCode())){
	    			check=true;
	    		}
			}
			return check;
	  }*/

	 public void test(){
		 System.out.println(this.mainApp.toString());

	 }

	public static String sansAccents(String s) {
			return Normalizer.normalize(s, Normalizer.Form.NFD);
	}

	 public static String translate(String src) {
	        StringBuffer result = new StringBuffer();
	        if(src!=null && src.length()!=0) {
	            int index = -1;
	            char c = (char)0;
	            String chars= "áàâäãåçéèêëíìîïñóòôöõúùûüýÿ";
	            String replace= "aaaaaaceeeeiiiinooooouuuuyy";
	            for(int i=0; i<src.length(); i++) {
	                c = src.charAt(i);
	                if( (index=chars.indexOf(c))!=-1 )
	                    result.append(replace.charAt(index));
	                else
	                    result.append(c);
	            }
	        }
	        return result.toString();
	    }

}
