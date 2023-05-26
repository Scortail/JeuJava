import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.io.*;

public class Defi implements Serializable{
    private static final int MAX_DAYS = 2;
    private Avatar joueur1;
    private Avatar joueur2;
    private int etat; // -1 : refusé, 0 : en attente, 1 : accepté
    private ArrayList<Question> listeQuestionsJoueur1;
    private ArrayList<Question> listeQuestionsJoueur2;
    private int etat_jeu; // -1 : personne n'a joué, 0 : J1 a joué, 1 : J2 a joué, 2 : defi terminé
    private LocalDateTime creationTime;

    public Defi(Avatar joueur1, Avatar joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.etat = 0;
        this.etat_jeu = -1;
        this.listeQuestionsJoueur1 = new ArrayList<Question>();
        this.listeQuestionsJoueur2 = new ArrayList<Question>();
        this.creationTime = LocalDateTime.now();
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

    public int getEtat_jeu() {
        return this.etat_jeu;
    }

    public String getEtatDefi() {
        switch (etat) {
            case -1:
                return "Refusé";
            case 0:
                return "En attente";
            case 1:
                switch (etat_jeu) {
                    case -1:
                        return "Accepté";
                    case 0:
                        return joueur1.getPseudo() + " a joué, en attente de " + joueur2.getPseudo();
                    case 1:
                        return joueur2.getPseudo() + " a joué, en attente de " + joueur1.getPseudo();
                    case 2:
                        return "Terminé";
                                        
                    default:
                        return "Inconnue";
                }
            default:
                return "Inconnu";
        }
    }

    public void afficherTempsRestant() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = creationTime.plusDays(MAX_DAYS);
        long minutesRemaining = ChronoUnit.MINUTES.between(currentTime, expirationTime);
    
        if (minutesRemaining <= 0) {
            System.out.println("Le défi a expiré.");
        } else {
            long hours = minutesRemaining / 60;
            long minutes = minutesRemaining % 60;
            System.out.println("Temps restant : " + hours + " heures " + minutes + " minutes");
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

    public void setEtat_jeu(int newEtat) {
        this.etat_jeu = newEtat;
        sauvegarderDefi();
    }
    
    class WrongChoiceQuestion extends Exception{

        public String toString(){
            return "Erreur: Ce n'est pas une réponse possible";
        }
    }
    
    public String getJoueurReponse(Question question, Scanner sc) throws WrongChoiceQuestion{

        System.out.println("Votre réponse : ");
        String reponse = sc.nextLine();
        for (int i=1; i<=question.getChoixReponse().size(); i++)
            if (!(reponse.equals(Integer.toString(i))))
                return reponse;
        throw new WrongChoiceQuestion();
        
    }

    public void ajouterQuestionJoueur1(Question question) {
        this.listeQuestionsJoueur1.add(question);
        sauvegarderDefi();
    }

    public void ajouterQuestionJoueur2(Question question) {
        this.listeQuestionsJoueur2.add(question);
        sauvegarderDefi();
    }

    // Permet a un joueur de repondre a une question du defi
    public void repondreQuestion(Question question, Avatar joueur, Scanner sc) {
        String reponse = null;
        boolean valide = false;
        int numeroReponseValide = question.getChoixReponse().indexOf(question.getReponseValide()) + 1;

        while( !valide ) {
            try {
                System.out.println(question);
                reponse = getJoueurReponse(question, sc);
                valide = true;
            }
            catch(WrongChoiceQuestion wcq){
                wcq.printStackTrace();
            }
        }
        // Le joueur a repondu juste
        if (reponse.equals(Integer.toString(numeroReponseValide))) {
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
    
    public boolean isExpired() {
        LocalDateTime currentTime = LocalDateTime.now();
        long daysElapsed = ChronoUnit.DAYS.between(creationTime, currentTime);
        return daysElapsed > MAX_DAYS;
    }

    public void verifierExpiration() {
        if (isExpired()) {
            // Defi non accepté
            if (etat == 0) {
                System.out.println("Defi expiré!");
                joueur2.refuserDefi(this);
            }
            // J2 n'a pas joué
            else if (etat_jeu == 0) {
                System.out.println(joueur2.getPseudo() + " n'a pas joué dans les temps.");
                double malus = 0;
                for (Question question : listeQuestionsJoueur1) {
                    malus += question.getNbPoints();
                }
                joueur2.retirerVie(malus*0.2);
            }
            // J1 n'a pas joué
            else if (etat_jeu == 1) {
                System.out.println(joueur1.getPseudo() + " n'a pas joué dans les temps.");
                double malus = 0;
                for (Question question : listeQuestionsJoueur2) {
                    malus += question.getNbPoints();
                }
                joueur1.retirerVie(malus*0.2);
            }
        }
    }

    public String toString() {
        return "Joueur 1 : " + joueur1.getPseudo() +
               ", Joueur 2 : " + joueur2.getPseudo() +
               ", État du défi : " + getEtatDefi();
    }
}
