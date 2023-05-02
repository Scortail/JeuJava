import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;;

public class Etudiant {
    private String nom;
    private String prenom;
    private Map<Matiere, ArrayList<Note>> Notes = new HashMap<Matiere, ArrayList<Note>>();
    private Avatar avatar = null;


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

    public Avatar getAvatar() {
        return this.avatar;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    // Inscrit l'etudiant a un cours
    public void ajouterMatiere(Matiere matiere) {
        if (!Notes.containsKey(matiere)) {
            Notes.put(matiere, new ArrayList<Note>());
        }
        else {
            System.out.println("La matiere est déjà existante");
        }
    }

    // Ajoute une note à l'étudiant avec son coef 
    public void ajouterNote(Matiere matiere, double note, double coef){
        Note newNote = new Note(note,coef);
        if (Notes.containsKey(matiere)) {
            Notes.get(matiere).add(newNote);
            if (this.avatar != null) {
                this.avatar.ajouterVie(note * coef);
            }
        }
        else {
            System.out.println("Cette matiere n'existe pas");
        }
    }


    // Ajoute une note à l'étudiant avec un coef de 1
    public void ajouterNote(Matiere matiere, double note){
        ajouterNote(matiere, note, 1);
    }

    // Création d'avatar
    public void creerAvatar(String pseudo) {
        if (this.avatar == null) {
            double life = 0;
            for (Map.Entry<Matiere, ArrayList<Note>> entry : Notes.entrySet()) {
                ArrayList<Note> notes = entry.getValue();
                for (Note note : notes) {
                    life += note.getNote() * note.getCoefficient();
                    }
                }
            Avatar avatar = new Avatar(pseudo, life);
            this.avatar = avatar;
            }
        else {
            System.out.println("Vous posséder déjà un avatar");
        }
    }    

    public String toString() {
        return "Nom : " + this.nom + " Prenom : " + this.prenom + " Notes : " + this.Notes;
    }
}

