import java.util.*;
import java.io.*;

public class Etudiant implements Serializable{
    private String nom;
    private String prenom;
    private Map<Matiere, ArrayList<Note>> Notes = new HashMap<Matiere, ArrayList<Note>>();
    private Avatar avatar = null;

    public Etudiant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        ArrayList<Etudiant> etudiants = chargerEtudiant(); // charger les etudiants existants
        for (Etudiant etudiant : etudiants) { // On empeche la creation multiple d'etudiant notamment lors des tests
            if (etudiant.equals(this)) {
                return;
            }
        }
        etudiants.add(this); // ajouter le nouvel etudiant à la liste
        sauvegarderEtudiants(etudiants); // sauvegarder la liste complète dans le fichier
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
        sauvegarderEtudiant();
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
        sauvegarderEtudiant();
    }


    // Ajoute une note à l'étudiant avec un coef de 1
    public void ajouterNote(Matiere matiere, double note){
        ajouterNote(matiere, note, 1);
        sauvegarderEtudiant();
    }

    // A ameliorer
    public void afficherBulletin() {
        for (Map.Entry<Matiere, ArrayList<Note>> entry : Notes.entrySet()) {
            ArrayList<Note> notes = entry.getValue();
            System.out.println("" + entry.getKey() + notes);
        }
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
            sauvegarderEtudiant();
            }
        else {
            System.out.println("Vous posséder déjà un avatar");
        }
    }    

    public String toString() {
        return "Nom : " + this.nom + " Prenom : " + this.prenom + " Notes : " + this.Notes + " Avatar : " +this.avatar;
    }

    public boolean equals(Etudiant etudiant) {
        if (this.nom.equals(etudiant.getNom()) && this.prenom.equals(etudiant.getPrenom())) {
            return true;
        }
        return false;
    }

    // Écrit tous les etudiants dans un nouveau fichier
    public static void sauvegarderEtudiants(ArrayList<Etudiant> etudiants) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("etudiant.ser"))) {
            for (Etudiant etudiant : etudiants) {
                oos.writeObject(etudiant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Charge tous les etudiants depuis le fichier
    public static ArrayList<Etudiant> chargerEtudiant() {
    ArrayList<Etudiant> etudiants = new ArrayList<>();
    File file = new File("etudiant.ser");
    if (file.exists()) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Etudiant) {
                        etudiants.add((Etudiant) obj);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return etudiants;
    }

    // Nous permet de sauvegarder un etudiant
    public void sauvegarderEtudiant() {
        ArrayList<Etudiant> listeEtudiant = chargerEtudiant();
        Etudiant etudiant = chercherEtudiant(listeEtudiant, nom, prenom);
        if (!(etudiant == null)) {
            listeEtudiant.remove(etudiant);
            listeEtudiant.add(this);
            sauvegarderEtudiants(listeEtudiant);
        }
    }

    // Retourne l'etudiant possédant le nom et prenom rechercher
    public Etudiant chercherEtudiant(ArrayList<Etudiant> etudiants, String nom, String prenom) {
        for (Etudiant etudiant : etudiants) {
            if (nom.equals(etudiant.getNom()) && prenom.equals(etudiant.getPrenom())) {
                return etudiant;
            }
        }
        return null;
    }

    public static void verifierEcritureEtudiant() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("etudiant.ser"))) {
            while (true) {
                Etudiant etudiant = (Etudiant) ois.readObject();
                System.out.println(etudiant);
            }
        } catch (EOFException e) {
            // La fin du fichier a été atteinte, on sort de la boucle
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

