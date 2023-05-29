import java.io.*;

/**
 * Représente une matière.
 * Une matière a un nom.
 */
public class Matiere implements Serializable{
    private String nomMatiere;

    /**
     * Constructeur de la classe Matiere.
     *
     * @param nomMatiere le nom de la matière.
     */
    public Matiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    /**
     * Retourne le nom de la matière.
     *
     * @return le nom de la matière.
     */
    public String getNomMatiere() {
        return nomMatiere;
    }

    /**
     * Modifie le nom de la matière.
     *
     * @param nomMatiere le nouveau nom de la matière.
     */
    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la matière.
     *
     * @return la représentation de la matière en tant que chaîne de caractères.
     */
    @Override
    public String toString() {
        return this.nomMatiere;
    }
}
