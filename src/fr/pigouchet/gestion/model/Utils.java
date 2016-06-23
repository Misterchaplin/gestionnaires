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
import javax.persistence.Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
@Table(name="Utils")
public class Utils implements Externalizable{
	private IntegerProperty id;
	private int _id;

	private StringProperty nom;
	private String _nom;

	private StringProperty data;
	private String _data;

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

    @Column(name="data")
    public final String getData() {
        if (this.data == null) {
            return _data ;
        } else {
            return data.get();
        }
    }
    public final void setData(String data) {
        if (this.data == null) {
            _data = data ;
        } else {
            this.data.set(data);
        }
    }
    public StringProperty dataProperty() {
        if (data == null) {
        	data = new SimpleStringProperty(this, "data", _data);
        }
        return data ;
    }

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		  out.writeInt(getId());
	      out.writeObject(getNom());
	      out.writeObject(getData());
	}
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		 setId(in.readInt());
	     setNom((String)in.readObject());
	     setData((String)in.readObject());
	}

	@Override
	public String toString() {
		return _nom;
	}
}
