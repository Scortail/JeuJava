import java.io.*;

public class Matiere implements Serializable{
    private String nomMatiere;

    public Matiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }


    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String toString() {
        return this.nomMatiere;
    }
}
