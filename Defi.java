import java.util.*;
import java.io.*;

public class Defi implements Serializable{
    private Avatar joueur1;
    private Avatar joueur2;
    private int etat; // -1 : refusé, 0 : en attente, 1 : accepté
    private ArrayList<Question> listeQuestionsJoueur1;
    private ArrayList<Question> listeQuestionsJoueur2;

    public Defi(Avatar joueur1, Avatar joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.etat = 0;
        this.listeQuestionsJoueur1 = new ArrayList<Question>();
        this.listeQuestionsJoueur2 = new ArrayList<Question>();
        sauvegarderDefi();
    }

    public Avatar getJoueur1() {
        return joueur1;
    }

    public Avatar getJoueur2() {
        return joueur2;
    }

    public int getEtat() {
        return this.etat;
    }

    public String getEtatDefi() {
        switch (etat) {
            case -1:
                return "Refusé";
            case 0:
                return "En attente";
            case 1:
                return "Accepté";
            default:
                return "Inconnu";
        }
    }

    public ArrayList<Question> getListeQuestionsJoueur1() {
        return this.listeQuestionsJoueur1;
    }

    public ArrayList<Question> getListeQuestionsJoueur2() {
        return this.listeQuestionsJoueur2;
    }

    public void setJoueur1(Avatar joueur1) {
        this.joueur1 = joueur1;
        sauvegarderDefi();
    }

    public void setJoueur2(Avatar joueur2) {
        this.joueur2 = joueur2;
        sauvegarderDefi();
    }

    public void setEtat(int etat) {
        this.etat = etat;
        sauvegarderDefi();
    }
    
    class WrongChoiceQuestion extends Exception{

        public String toString(){
            return "Erreur: Ce n'est pas une réponse possible";
        }
    }
    
    public String getJoueurReponse(Question question) throws WrongChoiceQuestion{
        Scanner sc = new Scanner(System.in) ;

        System.out.println("Votre réponse : ");
        String reponse = sc.nextLine();
        sc.close();


        if (!question.getChoixReponse().contains(reponse))
            throw new WrongChoiceQuestion();
        return reponse;
    }

    public void ajouterQuestionJoueur1(Question question) {
        this.listeQuestionsJoueur1.add(question);
        sauvegarderDefi();
    }

    public void ajouterQuestionJoueur2(Question question) {
        this.listeQuestionsJoueur2.add(question);
        sauvegarderDefi();
    }

    public void repondreQuestion(Question question, Avatar joueur) {
        String reponse = null;

        boolean valide = false;

        while( !valide ) {
            try {
                System.out.println(question);
                reponse = getJoueurReponse(question);
                valide = true;
            }
            catch(WrongChoiceQuestion wcq){
                wcq.printStackTrace();
            }
        }
        // Le joueur a repondu juste
        if (reponse.equals(question.getReponseValide())) {
            joueur.ajouterVie(question.getNbPoints());
            if (joueur.equals(joueur1)) {
                joueur2.retirerVie(question.getNbPoints());
            }
            else {
                joueur1.retirerVie(question.getNbPoints());
            }
            System.out.println("Réponse juste!");
            System.out.println("Vous avez gagné " + question.getNbPoints() + " vies");
        }

        // Le joueur a repondu faux
        else {
            joueur.retirerVie(question.getNbPoints());
            if (joueur.equals(joueur1)) {
                joueur2.ajouterVie(question.getNbPoints());
            }
            else {
                joueur1.ajouterVie(question.getNbPoints());
            }
            System.out.println("Réponse fausse!");
            System.out.println("Vous avez perdu " + question.getNbPoints() + " vies");
        }
        sauvegarderDefi();
    }

    // Nous permet de sauvegarder un defi
    public void sauvegarderDefi() {
        ArrayList<Etudiant> listeEtudiant = Etudiant.chargerEtudiant();
        ArrayList<Etudiant> listeEtudiantModifiee = new ArrayList<>();
    
        for (Etudiant etudiant : listeEtudiant) {
            Avatar avatar = etudiant.getAvatar();
            if (avatar != null) {
                if (avatar.equals(joueur1)) {
                    etudiant.setAvatar(joueur1);
                } else if (avatar.equals(joueur2)) {
                    etudiant.setAvatar(joueur2);
                }
            }
            listeEtudiantModifiee.add(etudiant);
        }
        Etudiant.sauvegarderEtudiants(listeEtudiantModifiee);
    }
    

    public String toString() {
        return "Joueur 1 : " + joueur1.getPseudo() +
               ", Joueur 2 : " + joueur2.getPseudo() +
               ", État du défi : " + getEtatDefi();
    }
}
