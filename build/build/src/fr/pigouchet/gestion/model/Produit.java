package fr.pigouchet.gestion.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
@Table(name="Produit")
public class Produit implements Externalizable{

	private static final long serialVersionUID = 1L;

	private IntegerProperty id;
	private int _id;

	private StringProperty code;
	private String _code;

	private StringProperty nom;
	private String _nom;

	private StringProperty priceAchat;
	private String _priceAchat;

	private StringProperty priceArtisant;
	private String _priceArtisant;

	private StringProperty priceDetail;
	private String _priceDetail;

	private ObjectProperty<SubCategorie> subcategorie;
	private SubCategorie _subcategorie;

	/*private ObjectProperty<Categorie> categorie;
	private Categorie _categorie;*/

	public Produit() {
		this(null, null);
	}

	public Produit(String code, String nom) {
		this.id=new SimpleIntegerProperty();
		this.code=new SimpleStringProperty(code);
		this.nom = new SimpleStringProperty(nom);
		this.priceAchat = new SimpleStringProperty();
		this.priceArtisant = new SimpleStringProperty();
		this.priceDetail = new SimpleStringProperty();
		this.subcategorie = new SimpleObjectProperty<>();
		//this.categorie = new SimpleObjectProperty<>();
	}

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

    @Column(name="code")
    public final String getCode() {
        if (this.code == null) {
            return _code ;
        } else {
            return code.get();
        }
    }
    public final void setCode(String code) {
        if (this.code == null) {
            _code = code ;
        } else {
            this.code.set(code);
        }
    }
    public StringProperty codeProperty() {
        if (code == null) {
        	code = new SimpleStringProperty(this, "code", _code);
        }
        return code ;
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

    @Column(name="priceAchat")
    public final String getPriceAchat() {
        if (this.priceAchat == null) {
            return _priceAchat ;
        } else {
            return priceAchat.get();
        }
    }
    public final void setPriceAchat(String priceAchat) {
        if (this.priceAchat == null) {
            _priceAchat = priceAchat ;
        } else {
            this.priceAchat.set(priceAchat);
        }
    }
    public StringProperty priceAchatProperty() {
        if (priceAchat == null) {
        	priceAchat = new SimpleStringProperty(this, "priceAchat", _priceAchat);
        }
        return priceAchat ;
    }

    @Column(name="priceArtisant")
    public final String getPriceArtisant() {
        if (this.priceArtisant == null) {
            return _priceArtisant ;
        } else {
            return priceArtisant.get();
        }
    }
    public final void setPriceArtisant(String priceArtisant) {
        if (this.priceArtisant == null) {
            _priceArtisant = priceArtisant ;
        } else {
            this.priceArtisant.set(priceArtisant);
        }
    }
    public StringProperty priceArtisantProperty() {
        if (priceArtisant == null) {
        	priceArtisant = new SimpleStringProperty(this, "priceArtisant", _priceArtisant);
        }
        return priceArtisant ;
    }

    @Column(name="priceDetail")
    public final String getPriceDetail() {
        if (this.priceDetail == null) {
            return _priceDetail ;
        } else {
            return priceDetail.get();
        }
    }
    public final void setPriceDetail(String priceDetail) {
        if (this.priceDetail == null) {
            _priceDetail = priceDetail ;
        } else {
            this.priceDetail.set(priceDetail);
        }
    }
    public StringProperty priceDetailProperty() {
        if (priceDetail == null) {
        	priceDetail = new SimpleStringProperty(this, "priceDetail", _priceDetail);
        }
        return priceDetail ;
    }

    @ManyToOne
    public final SubCategorie getSubCategorie() {
		if (subcategorie == null) {
			return _subcategorie ;
		} else {
			return subcategorie.get();
		}
	}
	public final void setSubCategorie(SubCategorie subcategorie) {
		if (this.subcategorie == null) {
			_subcategorie = subcategorie ;
		} else {
			this.subcategorie.set(subcategorie);
		}
	}
	public ObjectProperty<SubCategorie> subCategorieProperty() {
		if (subcategorie == null) {
			subcategorie = new SimpleObjectProperty<>(this, "subcategorie", _subcategorie);
		}
		return subcategorie ;
	}

   /* @ManyToOne
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
	}*/

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getId());
        out.writeObject(getCode());
        out.writeObject(getNom());
      	out.writeObject(getPriceAchat());
        out.writeObject(getPriceArtisant());
        out.writeObject(getPriceDetail());
        out.writeObject(getSubCategorie());
    }

    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        setId(in.readInt());
        setCode((String)in.readObject());
        setNom((String) in.readObject());
        setPriceAchat((String) in.readObject());
        setPriceArtisant((String) in.readObject());
        setPriceDetail((String) in.readObject());
        setSubCategorie((SubCategorie) in.readObject());
    }

    @Override
	public String toString() {
		return _nom;
	}

}
