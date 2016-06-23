package fr.pigouchet.gestion.util;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import fr.pigouchet.gestion.model.Categorie;
import fr.pigouchet.gestion.model.Produit;
import fr.pigouchet.gestion.model.SubCategorie;

public class Connection {
	  private static final String PERSISTENCE_UNIT_NAME = "gestions";
	  private static EntityManagerFactory factory;

	/* public static void main(String[] args) {
	  /*  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    EntityManager em = factory.createEntityManager();
	    List<Produit> pr = getAllProduct();
	   /* for (Produit produit : pr) {
			System.out.println(produit.getCategorie().getId());
		}*/

	  /*  List<Categorie> d = getAllCategory();
	    for (Categorie categorie : d) {
	    	List<Produit> fu=categoryInProduct(categorie);
			System.out.println("nb "+fu.size());
			for (Produit produit : fu) {
				System.out.println(produit.getNom());
			}
		}
	  //  categoryInProduct()
	  /*  Categorie cat = new Categorie();
	    cat.setNom("Agos");
	    Categorie cato=cat(cat);
	    System.out.println("dddddddddddd "+cato.getId()+" "+cato.getNom());

	    Produit prod = new Produit();
	    prod.setCategorie(cato);
	    prod.setCode("654");
	    prod.setNom("Une test");
	    prod.setPriceAchat("1.25");
	    prod.setPriceArtisant("2.50");
	    prod.setPriceDetail("3.75");
	   Produit po=insertProduct(prod);
	    System.out.println("POPOPOPOOP "+po.getNom()+" "+po.getCategorie().getNom());*/
	    // read the existing entries and write to console
	  /*  Query q = em.createQuery("select p from Categorie p");
	    List<Categorie> todoList = q.getResultList();
	    for (Categorie todo : todoList) {
	    	System.out.println(todo.getId());
	      /*System.out.println(todo.getPriceAchat());
	      System.out.println(todo.getCategorie().getNom());*/
	  /*  }
	    System.out.println("Size: " + todoList.size());

	    // create new todo
	  /*  em.getTransaction().begin();
	    Categorie cat = new Categorie();
	    cat.setNom("Agos");
	    em.persist(cat);
	    em.getTransaction().commit();

	    em.close();*/
	//  }

	  public static SubCategorie subCat(SubCategorie c){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.persist(c);
		  em.getTransaction().commit();

		  return c;
	  }

	  public static Categorie cat(Categorie c){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.persist(c);
		  em.getTransaction().commit();

		  return c;
	  }

	  @SuppressWarnings("unchecked")
	public static List<Produit> getAllProduct(){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query q = em.createQuery("select p from Produit p order by p.subCategorie");
		  List<Produit> product = q.getResultList();

		  return product;
	  }

	  @SuppressWarnings("unchecked")
	public static List<Categorie> getAllCategory(){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query q =  em.createQuery("select c from Categorie c");
		  List<Categorie> categorie = q.getResultList();

		  return categorie;
	  }

	  @SuppressWarnings("unchecked")
	public static List<SubCategorie> getAllSubCategory(){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query q =  em.createQuery("select s from SubCategorie s");
		  List<SubCategorie> subcategorie = q.getResultList();

		  return subcategorie;
	  }

	  public static Produit insertProduct(Produit product){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.persist(product);
		  em.getTransaction().commit();

		  return product;
	  }

	  public static Produit deleteProduct(Produit product){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  Produit toDelete = em.merge(product);
		  em.remove(toDelete);
		  em.getTransaction().commit();

		  return product;
	  }

	  public static Categorie deleteCategorie(Categorie categorie){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  Categorie toDelete = em.merge(categorie);
		  em.remove(toDelete);
		  em.getTransaction().commit();

		  return categorie;
	  }

	  public static SubCategorie SubCategorie(SubCategorie souscategorie){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  SubCategorie toDelete = em.merge(souscategorie);
		  em.remove(toDelete);
		  em.getTransaction().commit();

		  return souscategorie;
	  }

	  public static Produit updateProduct(Produit product){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.merge(product);
		  em.getTransaction().commit();

		  return product;
	  }

	  public static Categorie updateCategory(Categorie category){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.merge(category);
		  em.getTransaction().commit();

		  return category;
	  }

	  public static SubCategorie updateSubCategory(SubCategorie subcategory){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.merge(subcategory);
		  em.getTransaction().commit();

		  return subcategory;
	  }

	  @SuppressWarnings("unchecked")
	public static List<Produit> listCategoryToProduct(Categorie categorie){

		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select p from Produit p join p.subCategorie s where s.categorie.id="+categorie.getId());

		  return query.getResultList();

	  }

	  @SuppressWarnings("unchecked")
	public static List<SubCategorie> listCategoryToSubCategory(Categorie categorie){

		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select s from SubCategorie s where s.categorie.id="+categorie.getId());

		  return query.getResultList();

	  }

	  @SuppressWarnings("unchecked")
	public static List<Produit> listSubCategoryToProduit(SubCategorie souscategorie){

		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select p from Produit p where p.subCategorie.id="+souscategorie.getId());

		  return query.getResultList();

	  }

	  public static int categoryToProduct(Categorie categorie){

		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select p from Produit p join p.subCategorie s where s.categorie.id="+categorie.getId());

		  return query.getResultList().size();

	  }

	  public static int categoryToSubCategory(Categorie categorie){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select s from SubCategorie s where s.categorie.id="+categorie.getId());

		  return query.getResultList().size();
	  }

	  public static int SubCategoryToProduit(SubCategorie souscategorie){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  Query query = em.createQuery("select p from Produit p where p.subCategorie.id="+souscategorie.getId());

		  return query.getResultList().size();
	  }


	  public static Object selectUtils(String nom){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  try{
			  	Query data = em.createQuery("select u from Utils u where u.nom=:name")
				  .setParameter("name", nom);
		  		return data.getSingleResult();
		  } catch(NoResultException e) {
		        return null;
		    }
	  }

	  public static Utils updateUtilsPath(Utils path){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.merge(path);
		  em.getTransaction().commit();

		  return path;
	  }

	  public static fr.pigouchet.gestion.model.Utils insertPath(fr.pigouchet.gestion.model.Utils path){
		  factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		  EntityManager em = factory.createEntityManager();
		  em.getTransaction().begin();
		  em.persist(path);
		  em.getTransaction().commit();

		  return path;
	  }

}
