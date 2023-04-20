import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;;

public class Etudiant {
    private String nom;
    private String prenom;
    private Map<Matiere, ArrayList<Note>> Notes = new HashMap<Matiere, ArrayList<Note>>();


    public Etudiant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Map<Matiere, ArrayList<Note>> getNotes() {
        return this.Notes;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void ajouterMatiere(Matiere matiere) {
        if (!Notes.containsKey(matiere)) {
            Notes.put(matiere, new ArrayList<Note>());
        }
        else {
            System.out.println("La matiere est déjà existante");
        }
    }

    public void ajouterNote(Matiere matiere, int note){
        Note newNote = new Note(note);
        if (Notes.containsKey(matiere)) {
            Notes.get(matiere).add(newNote);
        }
        else {
            System.out.println("Cette matiere n'existe pas");
        }
    }

    public String toString() {
        return "Nom : " + this.nom + " Prenom : " + this.prenom + " Notes : " + this.Notes;
    }
}