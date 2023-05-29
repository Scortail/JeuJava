import java.io.Serializable;

/**
 * La classe Note représente une note associée à un coefficient.
 * Elle implémente l'interface Serializable pour permettre la sérialisation des objets de cette classe.
 */
public class Note implements Serializable {
    private double note;
    private double coefficient;



    /**
     * Constructeur de la classe Note.
     * 
     * @param note        La note à attribuer.
     * @param coefficient Le coefficient associé à la note.
     */
    public Note(double note, double coefficient) {
        if (coefficient <= 0) {
            this.coefficient = 1;
            System.out.println("Coefficient nul ou négatif, le coefficient a été modifié à 1.");
        } else {
            this.coefficient = coefficient;
        }
        this.note = note;
    }

    /**
     * Constructeur de la classe Note avec un coefficient par défaut de 1.
     * 
     * @param note La note à attribuer.
     */
    public Note(double note) {
        this.note = note;
        this.coefficient = 1;
    }

    /**
     * Exception levée lorsqu'une note est en dehors de la plage autorisée (0-20).
     */
    class WrongNoteException extends Exception {
        @Override
        public String toString() {
            return "Erreur: La note n'est pas comprise entre 0 et 20.";
        }
    }

    /**
     * Retourne la note.
     * 
     * @return La note.
     */
    public double getNote() {
        return note;
    }

    /**
     * Retourne le coefficient.
     * 
     * @return Le coefficient.
     */
    public double getCoefficient() {
        return coefficient;
    }

    /**
     * Définit la note.
     * 
     * @param note La note à définir.
     */
    public void setNote(double note) {
        this.note = note;
    }

    /**
     * Définit le coefficient.
     * 
     * @param coefficient Le coefficient à définir.
     */
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la note et du coefficient.
     * 
     * @return La représentation sous forme de chaîne de caractères de la note et du coefficient.
     */
    @Override
    public String toString() {
        return note + " (Coef " + coefficient + ")";
    }
}
