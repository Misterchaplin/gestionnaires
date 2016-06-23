package fr.pigouchet.gestion.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
@Table(name="SubCategorie")
public class SubCategorie implements Externalizable{
	private IntegerProperty id;
	private int _id;

	private StringProperty nom;
	private String _nom;

	private List<Produit> produit;
	private ObjectProperty<Categorie> categorie;
	private Categorie _categorie;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
    public final int getId() {
        if (id == null) {
            return _id;
        } else {
            return id.get();
        }
    }

    public final void setId(int id) {
        if (this.id == null) {
            _id = id;
        } else {
            this.id.set(id);
        }
    }

    public IntegerProperty idProperty() {
        if (id == null) {
            id = new SimpleIntegerProperty(this, "id", _id);
        }
        return id ;
    }

    @Column(name="nom")
    public final String getNom() {
        if (this.nom == null) {
            return _nom ;
        } else {
            return nom.get();
        }
    }
    public final void setNom(String nom) {
        if (this.nom == null) {
            _nom = nom ;
        } else {
            this.nom.set(nom);
        }
    }
    public StringProperty nomProperty() {
        if (nom == null) {
        	nom = new SimpleStringProperty(this, "nom", _nom);
        }
        return nom ;
    }

    @ManyToOne
    public final Categorie getCategorie() {
		if (categorie == null) {
			return _categorie ;
		} else {
			return categorie.get();
		}
	}
	public final void setCategorie(Categorie categorie) {
		if (this.categorie == null) {
			_categorie = categorie ;
		} else {
			this.categorie.set(categorie);
		}
	}
	public ObjectProperty<Categorie> categorieProperty() {
		if (categorie == null) {
			categorie = new SimpleObjectProperty<>(this, "categorie", _categorie);
		}
		return categorie ;
	}

    @OneToMany(mappedBy="SubCategorie")
    public final List<Produit> getProduit() {
		return produit;
	}

	public final void setProduit(List<Produit> produit) {
		this.produit=produit;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		  out.writeInt(getId());
	      out.writeObject(getNom());
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		 setId(in.readInt());
	     setNom((String)in.readObject());
	}

	@Override
	public String toString() {
		return _nom;
	}

}
