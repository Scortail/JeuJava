import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.io.*;

/**
 * La classe Defi représente un défi entre deux avatars (joueurs).
 * Elle gère l'état du défi, les questions posées à chaque joueur, le suivi du temps restant
 * et les actions liées aux réponses des joueurs.
 */
public class Defi implements Serializable{
    private static final int MAX_DAYS = 2;
    private Avatar joueur1;
    private Avatar joueur2;
    private int etat; // -1 : refusé, 0 : en attente, 1 : accepté
    private ArrayList<Question> listeQuestionsJoueur1;
    private ArrayList<Question> listeQuestionsJoueur2;
    private int etatJeu; // -1 : personne n'a joué, 0 : J1 a joué, 1 : J2 a joué, 2 : defi terminé
    private LocalDateTime creationTime;

    /**
     * Construit une instance de la classe Defi avec les avatars des deux joueurs.
     *
     * @param joueur1 le premier joueur
     * @param joueur2 le deuxième joueur
     */
    public Defi(Avatar joueur1, Avatar joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.etat = 0;
        this.etatJeu = -1;
        this.listeQuestionsJoueur1 = new ArrayList<Question>();
        this.listeQuestionsJoueur2 = new ArrayList<Question>();
        this.creationTime = LocalDateTime.now();
        sauvegarderDefi();
    }

    /**
     * Retourne le premier joueur du défi.
     *
     * @return le premier joueur
     */
    public Avatar getJoueur1() {
        return joueur1;
    }

    /**
     * Retourne le deuxième joueur du défi.
     *
     * @return le deuxième joueur
     */
    public Avatar getJoueur2() {
        return joueur2;
    }

    /**
     * Retourne l'état du défi.
     *
     * @return l'état du défi (-1: refusé, 0: en attente, 1: accepté)
     */
    public int getEtat() {
        return this.etat;
    }

    /**
     * Retourne l'état du jeu du défi.
     *
     * @return l'état du jeu (-1: personne n'a joué, 0: J1 a joué, 1: J2 a joué, 2: défi terminé)
     */
    public int getEtatJeu() {
        return this.etatJeu;
    }

    /**
     * Retourne l'état du défi sous forme d'une chaîne de caractères.
     *
     * @return l'état du défi sous forme de texte
     */
    public String getEtatDefi() {
        switch (etat) {
            case -1:
                return "Refusé";
            case 0:
                return "En attente";
            case 1:
                switch (etatJeu) {
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

    /**
     * Affiche le temps restant pour le défi.
     */
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

    /**
     * Renvoie la liste des questions pour le joueur 1.
     * @return la liste des questions pour le joueur 1
     */
    public ArrayList<Question> getListeQuestionsJoueur1() {
        return this.listeQuestionsJoueur1;
    }

    /**
     * Renvoie la liste des questions pour le joueur 2.
     * @return la liste des questions pour le joueur 2
     */
    public ArrayList<Question> getListeQuestionsJoueur2() {
        return this.listeQuestionsJoueur2;
    }

    /**
     * Définit le joueur 1 du défi.
     * @param joueur1 le joueur 1 à définir
     */
    public void setJoueur1(Avatar joueur1) {
        this.joueur1 = joueur1;
        sauvegarderDefi();
    }

    /**
     * Définit le joueur 2 du défi.
     * @param joueur2 le joueur 2 à définir
     */
    public void setJoueur2(Avatar joueur2) {
        this.joueur2 = joueur2;
        sauvegarderDefi();
    }

    /**
     * Définit l'état du défi.
     * @param etat l'état à définir
     */
    public void setEtat(int etat) {
        this.etat = etat;
        sauvegarderDefi();
    }

    /**
     * Définit l'état du jeu du défi.
     * @param newEtat le nouvel état du jeu à définir
     */
    public void setEtatJeu(int newEtat) {
        this.etatJeu = newEtat;
        sauvegarderDefi();
    }
    
    /**
     * Exception levée lorsque la réponse du joueur à une question est incorrecte.
     */
    class WrongChoiceQuestion extends Exception{

        public String toString(){
            return "Erreur: Ce n'est pas une réponse possible";
        }
    }
    
    /**
     * Demande au joueur la réponse à une question.
     * @param question la question à laquelle le joueur doit répondre
     * @param sc le Scanner utilisé pour la saisie de la réponse
     * @return la réponse du joueur
     * @throws WrongChoiceQuestion si la réponse du joueur n'est pas valide
     */
    public String getJoueurReponse(Question question, Scanner sc) throws WrongChoiceQuestion{
        System.out.println("Votre réponse : ");
        String reponse = sc.nextLine();
        for (int i=1; i<=question.getChoixReponse().size(); i++)
            if (!(reponse.equals(Integer.toString(i))))
                return reponse;
        throw new WrongChoiceQuestion();
    }

    /**
     * Ajoute une question à la liste des questions pour le joueur 1.
     * @param question la question à ajouter
     */
    public void ajouterQuestionJoueur1(Question question) {
        this.listeQuestionsJoueur1.add(question);
        sauvegarderDefi();
    }

    /**
     * Ajoute une question à la liste des questions pour le joueur 2.
     * @param question la question à ajouter
     */
    public void ajouterQuestionJoueur2(Question question) {
        this.listeQuestionsJoueur2.add(question);
        sauvegarderDefi();
    }

    /**
     * Permet à un joueur de répondre à une question du défi.
     * @param question la question à laquelle le joueur doit répondre
     * @param joueur le joueur qui répond à la question
     * @param sc le Scanner utilisé pour la saisie de la réponse
     */
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

    /**
     * Sauvegarde le défi en mettant à jour les avatars des joueurs dans la liste des utilisateurs.
     */
    public void sauvegarderDefi() {
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        ArrayList<Object> listeUtilisateurModifiee = new ArrayList<>();
    
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (avatar != null) {
                    if (avatar.equals(joueur1)) {
                        etudiant.setAvatar(joueur1);
                    } else if (avatar.equals(joueur2)) {
                        etudiant.setAvatar(joueur2);
                    }
                }
                listeUtilisateurModifiee.add(etudiant);
            }
        }
        Etudiant.sauvegarderUtilisateurs(listeUtilisateurModifiee);
    }

    /**
     * Vérifie si le défi a expiré en fonction de la date de création et de la durée maximale.
     * @return true si le défi a expiré, sinon false
     */
    public boolean isExpired() {
        LocalDateTime currentTime = LocalDateTime.now();
        long daysElapsed = ChronoUnit.DAYS.between(creationTime, currentTime);
        return daysElapsed > MAX_DAYS;
    }

    /**
     * Vérifie l'expiration du défi et applique les actions appropriées en fonction de l'état du défi et du jeu.
     */
    public void verifierExpiration() {
        if (isExpired()) {
            // Defi non accepté
            if (etat == 0) {
                System.out.println("Defi expiré!");
                joueur2.refuserDefi(this);
            }
            // J2 n'a pas joué
            else if (etatJeu == 0) {
                System.out.println(joueur2.getPseudo() + " n'a pas joué dans les temps.");
                double malus = 0;
                for (Question question : listeQuestionsJoueur1) {
                    malus += question.getNbPoints();
                }
                joueur2.retirerVie(malus*0.2);
            }
            // J1 n'a pas joué
            else if (etatJeu == 1) {
                System.out.println(joueur1.getPseudo() + " n'a pas joué dans les temps.");
                double malus = 0;
                for (Question question : listeQuestionsJoueur2) {
                    malus += question.getNbPoints();
                }
                joueur1.retirerVie(malus*0.2);
            }
        }
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères du défi.
     * @return la représentation du défi
     */
    public String toString() {
        return "Joueur 1 : " + joueur1.getPseudo() +
               ", Joueur 2 : " + joueur2.getPseudo() +
               ", État du défi : " + getEtatDefi();
    }
}
