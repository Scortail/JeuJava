import java.util.*;
import java.io.*;

/**
 * Représente un étudiant avec son nom, prénom, ses notes par matière et son avatar.
 */
public class Etudiant implements Serializable{
    private String nom;
    private String prenom;
    private Map<Matiere, ArrayList<Note>> Notes = new HashMap<Matiere, ArrayList<Note>>();
    private Avatar avatar = null;

    /**
     * Constructeur de la classe Etudiant.
     * Crée un nouvel étudiant avec le nom et le prénom spécifiés.
     * Enregistre également l'étudiant dans la liste des utilisateurs.
     * @param nom le nom de l'étudiant
     * @param prenom le prénom de l'étudiant
     */
    public Etudiant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        ArrayList<Object> utilisateurs = chargerUtilisateurs(); // charger les etudiants existants
        for (Object utilisateur : utilisateurs) { // On empeche la creation multiple d'etudiant notamment lors des tests
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                if (etudiant.equals(this)) {
                    return;
                }
            }
        }
        utilisateurs.add(this); // ajouter le nouvel etudiant à la liste
        sauvegarderUtilisateurs(utilisateurs); // sauvegarder la liste complète dans le fichier
    }

    /**
     * Retourne le nom de l'étudiant.
     * @return le nom de l'étudiant
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Retourne le prénom de l'étudiant.
     * @return le prénom de l'étudiant
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Retourne les notes de l'étudiant par matière.
     * @return les notes de l'étudiant par matière
     */
    public Map<Matiere, ArrayList<Note>> getNotes() {
        return this.Notes;
    }

    /**
     * Retourne l'avatar de l'étudiant.
     * @return l'avatar de l'étudiant
     */
    public Avatar getAvatar() {
        return this.avatar;
    }

    /**
     * Modifie le nom de l'étudiant.
     * @param nom le nouveau nom de l'étudiant
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifie le prénom de l'étudiant.
     * @param prenom le nouveau prénom de l'étudiant
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Modifie l'avatar de l'étudiant.
     * @param avatar le nouvel avatar de l'étudiant
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    /**
     * Inscrit l'étudiant à une matière.
     * Si la matière n'existe pas déjà pour l'étudiant, elle est ajoutée.
     * @param matiere la matière à ajouter
     */
    public void ajouterMatiere(Matiere matiere) {
        if (!Notes.containsKey(matiere)) {
            Notes.put(matiere, new ArrayList<Note>());
        }
        else {
            System.out.println("La matiere est déjà existante");
        }
        sauvegarderEtudiant();
    }

    /**
     * Ajoute une note à l'étudiant pour une matière donnée avec un coefficient spécifié.
     * @param matiere la matière de la note
     * @param note la note à ajouter
     * @param coef le coefficient de la note
     */
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

    /**
     * Ajoute une note à l'étudiant pour une matière donnée avec un coefficient de 1.
     * @param matiere la matière de la note
     * @param note la note à ajouter
     */
    public void ajouterNote(Matiere matiere, double note){
        ajouterNote(matiere, note, 1);
        sauvegarderEtudiant();
    }

    /**
     * Affiche le bulletin de l'étudiant avec ses notes par matière.
     */
    public void afficherBulletin() {
        for (Map.Entry<Matiere, ArrayList<Note>> entry : Notes.entrySet()) {
            ArrayList<Note> notes = entry.getValue();
            System.out.println("" + entry.getKey() + notes);
        }
    }

    /**
     * Crée un avatar pour l'étudiant avec un pseudo spécifié.
     * Le calcul de la vie de l'avatar est basé sur les notes de l'étudiant.
     * @param pseudo le pseudo de l'avatar
     */
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

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'étudiant.
     * @return la représentation de l'étudiant
     */
    public String toString() {
        return "Nom : " + this.nom + " Prenom : " + this.prenom + " Notes : " + this.Notes + " Avatar : " +this.avatar;
    }

    /**
     * Vérifie si l'objet spécifié est égal à l'étudiant.
     * Deux étudiants sont considérés égaux si leur nom et leur prénom sont identiques.
     * @param obj l'objet à comparer
     * @return true si l'objet est égal à l'étudiant, sinon false
     */
    public boolean equals(Etudiant etudiant) {
        if (this.nom.equals(etudiant.getNom()) && this.prenom.equals(etudiant.getPrenom())) {
            return true;
        }
        return false;
    }

    /**
     * Écrit tous les utilisateurs (étudiants et administrateurs) dans un nouveau fichier.
     * @param utilisateurs la liste des utilisateurs à sauvegarder
     */
    public static void sauvegarderUtilisateurs(ArrayList<Object> utilisateurs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("utilisateurs.ser"))) {
            for (Object utilisateur : utilisateurs) {
                oos.writeObject(utilisateur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Charge tous les utilisateurs (étudiants et administrateurs) depuis le fichier.
     * @return la liste des utilisateurs chargés
     */
    public static ArrayList<Object> chargerUtilisateurs() {
        ArrayList<Object> utilisateurs = new ArrayList<>();
        File file = new File("utilisateurs.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        Object obj = ois.readObject();
                        utilisateurs.add(obj);
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return utilisateurs;
    }

    /**
     * Sauvegarde l'étudiant actuel en remplaçant sa version précédente dans la liste des utilisateurs.
     * La méthode recherche l'étudiant dans la liste par son nom et prénom, puis le remplace par l'étudiant actuel.
     */
    public void sauvegarderEtudiant() {
        ArrayList<Object> utilisateurs = chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                if (etudiant.equals(this)) {
                    utilisateurs.remove(utilisateur);
                    utilisateurs.add(this);
                    sauvegarderUtilisateurs(utilisateurs);
                    return;
                }
            }
        }
    }

    /**
     * Recherche et retourne l'étudiant ayant le nom et prénom spécifiés dans une liste d'étudiants.
     * @param etudiants la liste d'étudiants à rechercher
     * @param nom le nom de l'étudiant recherché
     * @param prenom le prénom de l'étudiant recherché
     * @return l'étudiant correspondant au nom et prénom recherchés, ou null si aucun étudiant n'est trouvé
     */
    public Etudiant chercherEtudiant(ArrayList<Etudiant> etudiants, String nom, String prenom) {
        for (Etudiant etudiant : etudiants) {
            if (nom.equals(etudiant.getNom()) && prenom.equals(etudiant.getPrenom())) {
                return etudiant;
            }
        }
        return null;
    }
}

