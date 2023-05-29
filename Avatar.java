import java.util.*;
import java.io.*;


/**
 * La classe Avatar représente un avatar d'un joueur dans le jeu. Un avatar possède un pseudo,
 * un certain nombre de vies, une liste de défis, une liste de questions, une date de dernière
 * participation et le nombre de tests joués.
 */
public class Avatar implements Serializable {
    
    private String pseudo;
    private double life;
    private ArrayList<Defi> listeDefi = new ArrayList<Defi>();
    private ArrayList<Question> listeQuestion = new ArrayList<Question>();
    private Date derniereParticipation;
    private int testsJoues;
    
    /**
     * Constructeur de la classe Avatar.
     *
     * @param pseudo le pseudo de l'avatar
     * @param life   le nombre de vies de l'avatar
     */
    public Avatar(String pseudo, double life) {
        this.pseudo = pseudo;
        this.life = life;
        this.derniereParticipation = null;
        this.testsJoues = 0;
        // On ajoute des questions aléatoires lors de sa création
        ArrayList<Question> questionsDispo = Question.chargerQuestions();
        Random random = new Random();
        int totalQuestions = questionsDispo.size();
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(totalQuestions);
            Question question = questionsDispo.get(randomIndex);
            listeQuestion.add(question);
            questionsDispo.remove(randomIndex);
            totalQuestions--;
        }
    }

    /**
     * Retourne le pseudo de l'avatar.
     *
     * @return le pseudo de l'avatar
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Retourne le nombre de vies de l'avatar.
     *
     * @return le nombre de vies de l'avatar
     */
    public double getLife() {
        return this.life;
    }

    /**
     * Retourne la liste des défis de l'avatar.
     *
     * @return la liste des défis de l'avatar
     */
    public ArrayList<Defi> getListeDefi() {
        return this.listeDefi;
    }

    /**
     * Retourne la liste des questions de l'avatar.
     *
     * @return la liste des questions de l'avatar
     */
    public ArrayList<Question> getListeQuestion() {
        return this.listeQuestion;
    }

    /**
     * Retourne la question correspondant à un numéro donné.
     *
     * @param numero le numéro de la question
     * @return la question correspondant au numéro donné, ou null si le numéro est invalide
     */
    public Question getQuestion(int numero) {
        if (numero <= listeQuestion.size() && numero > 0) {
            return listeQuestion.get(numero - 1);
        }
        return null;
    }

    /**
     * Modifie le pseudo de l'avatar.
     *
     * @param pseudo le nouveau pseudo de l'avatar
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Modifie le nombre de vies de l'avatar.
     *
     * @param life le nouveau nombre de vies de l'avatar
     */
    public void setLife(double life) {
        this.life = life;
    }

    /**
     * Définit la liste des défis auxquels l'avatar participe.
     *
     * @param listeDefi La liste des défis.
     */
    public void setListeDefi(ArrayList<Defi> listeDefi) {
        this.listeDefi = listeDefi;
    }

    /**
     * Définit la liste des questions disponibles pour l'avatar.
     *
     * @param listeQuestion La liste des questions.
     */
    public void setListeQuestion(ArrayList<Question> listeQuestion) {
        this.listeQuestion = listeQuestion;
    }
    
    /**
     * Ajoute des vies à l'avatar.
     *
     * @param life Le nombre de vies à ajouter.
     */
    public void ajouterVie(double life) {
        this.life += life;
    }
    
    /**
     * Retire des vies à l'avatar.
     *
     * @param life Le nombre de vies à retirer.
     */
    public void retirerVie(double life) {
        this.life -= life;
        if(this.life < 0) {
            this.life = 0;
            System.out.println(pseudo + " n'a plus de vies.");
        }
    }

    /**
     * Ajoute une question à l'avatar.
     *
     * @param question La question à ajouter.
     */
    public void ajouterQuestion(Question question) {
        listeQuestion.add(question);
        sauvegarderAvatar();
    }

    /**
     * Crée un défi avec un autre joueur.
     *
     * @param joueurDefier L'avatar du joueur à défier.
     * @param questions    Les questions du défi.
     */
    public void creerDefi(Avatar joueurDefier, Question ... questions) {
        if (life == 0) {
            System.out.println("Vous n'avez plus de vie, vous ne pouvez donc pas creer de défi.\nEssayez de participez a des tests ou attendez vos prochaine note pour défier vos camarades");
        }
        Defi defi = new Defi(this, joueurDefier);
        this.listeDefi.add(defi);
        joueurDefier.listeDefi.add(defi);
        for (Question question : questions) {
            defi.ajouterQuestionJoueur1(question);
        }
    }

    /**
     * Retourne une représentation des questions disponibles pour l'avatar.
     *
     * @return Une chaîne de caractères représentant les questions disponibles.
     */
    public String afficherQuestionsDispo() {
        String questions = "";
        int numero = 0;
        for (Question question : listeQuestion) {
            numero += 1;
            questions += numero + " : " + question + "\n";
        }
        return questions;
    }

    /**
     * Recherche et retourne l'avatar ayant le pseudo spécifié.
     *
     * @param pseudo Le pseudo de l'avatar recherché.
     * @return L'avatar correspondant au pseudo, ou null s'il n'est pas trouvé.
     */
    public Avatar chercherAvatar(String pseudo) {
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (!(avatar == null)) {
                    if (avatar.getPseudo().equals(pseudo)) {
                        return avatar;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retourne une représentation des avatars existants, excluant l'avatar courant.
     *
     * @return Une chaîne de caractères représentant les pseudos des avatars existants.
     */
    public String afficherAvatars() {
        String affichage = "";
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (!(avatar == null)) {
                    if (!(avatar.equals(this)))
                        affichage += avatar.getPseudo();
                }
            }
        }
        return affichage;
    }

    /**
     * Accepte un défi spécifié et ajoute les questions fournies au défi.
     *
     * @param defi      Le défi à accepter.
     * @param questions Les questions à ajouter au défi.
     */
    public void accepterDefi(Defi defi, Question ... questions) {
        if (this == defi.getJoueur2()) {
            defi.setEtat(1);
            defi.setEtatJeu(0);
            for ( Question question : questions) {
                defi.ajouterQuestionJoueur2(question);
            }
            System.out.println("Le défi a été accepté");
        } else {
            System.out.println("Vous ne pouvez pas accepter ce défi car vous n'êtes pas le joueur défié.");
        }
    }

    /**
     * Refuse un défi spécifié et applique un malus de 20% des points des questions du défi.
     *
     * @param defi Le défi à refuser.
     */
    public void refuserDefi(Defi defi) {
        if (this == defi.getJoueur2()) {
            defi.setEtat(-1);
            // On applique un malus de 20%
            double malus = 0;
            for (Question question : defi.getListeQuestionsJoueur1()) {
                malus += question.getNbPoints();
            }
            retirerVie(malus*0.2);
            System.out.println("Le défi a été refusé");
            System.out.println("Vous avez perdu " + malus*0.2 + " vies.");
        }
        else {
            System.out.println("Vous ne pouvez pas accepter ce défi car vous n'êtes pas le joueur défié.");
        }
    }
    
    /**
     * Joue un défi spécifié en répondant aux questions fournies.
     *
     * @param defi Le défi à jouer.
     * @param sc   Le scanner pour la saisie des réponses.
     */
    public void jouer(Defi defi, Scanner sc) {
        ArrayList<Question> listeQuestions;
        if (listeDefi.contains(defi)) {
            if (defi.getEtatJeu() == 2) {
                System.out.println("Ce defi est terminé");
            }
            else {
                if (this.equals(defi.getJoueur1())) {
                    if (defi.getEtatJeu() == 0) {
                        System.out.println("Vous avez déjà jouer");
                        return;
                    }
                    listeQuestions = defi.getListeQuestionsJoueur2();
                    defi.setEtatJeu(0);                
                }
                else {
                    if (defi.getEtatJeu() == 1) {
                        System.out.println("Vous avez déjà jouer");
                        return;
                    }
                    listeQuestions = defi.getListeQuestionsJoueur1();
                    defi.setEtatJeu(1); 
                }
                int i = 1;
                System.out.println(listeQuestions);
                for (Question question : listeQuestions) {
                    System.out.println("Question " + i + " : ");
                    System.out.println(life);
                    defi.repondreQuestion(question, this, sc);
                }
                listeQuestions.clear();
            }
        }
        else {
            System.out.println("Vous n'etes pas concerné par ce defi !");
        }
    }
    
    /**
     * Joue un test en répondant aux questions du test.
     *
     * @param sc Le scanner pour la saisie des réponses.
     */
    public void jouerTest(Scanner sc) {
        // Vérifier si l'avatar a déjà joué deux tests au cours des trois derniers jours
        if (testsJoues >= 2 && derniereParticipation != null) {
            Date dateActuelle = new Date();
            long difference = dateActuelle.getTime() - derniereParticipation.getTime();
            long troisJoursEnMillisecondes = 3 * 24 * 60 * 60 * 1000; // Trois jours en millisecondes
    
            if (difference < troisJoursEnMillisecondes) {
                System.out.println("Vous avez déjà joué deux tests au cours des trois derniers jours. Veuillez attendre avant de participer à un nouveau test.");
                return;
            }
        }
        // L'avatar peut participer au test
        Test test = new Test(this);
        ArrayList<Question> listeQuestions = test.getListeQuestions();
    
        for (Question question : listeQuestions) {
            test.repondreQuestion(question, this, sc);
        }
        // Mettre à jour les informations de participation au test
        testsJoues++;
        derniereParticipation = new Date();
        System.out.println("Pourcentage de réussite : " + test.getScore()*100/3);
        if (test.getScore() == 3) {
            // Choisissez une question aléatoire parmi les questions répondues avec succès
            Random random = new Random();
            int indexQuestionAleatoire = random.nextInt(listeQuestions.size());
            Question questionAleatoire = listeQuestions.get(indexQuestionAleatoire);
    
            // Ajoutez la question aléatoire à la liste des questions répondues
            listeQuestion.add(questionAleatoire);
            System.out.println("Félicitations ! Une question supplémentaire a été ajoutée à votre base.");
        }
    }

    /**
     * Soumet une nouvelle question en demandant les informations nécessaires à l'utilisateur.
     * La question est ensuite ajoutée à la liste des questions proposées par l'administrateur.
     *
     * @param sc Le scanner pour la saisie des informations.
     */
    public void soumettreQuestion(Scanner sc) {
        System.out.println("Saisissez le texte de la question :");
        String texteQuestion = sc.nextLine();
    
        ArrayList<String> choixReponses = new ArrayList<>();
        int indiceReponseCorrecte = 0;
    
        for (int i = 1; i <= 4; i++) {
            System.out.println("Saisissez le choix de réponse " + i + " :");
            String choix = sc.nextLine();
            choixReponses.add(choix);
    
            System.out.println("Est-ce la réponse correcte ? (O/N) :");
            String reponseCorrecte = sc.nextLine();
    
            if (reponseCorrecte.equalsIgnoreCase("O")) {
                indiceReponseCorrecte = i;
            }
        }
        int difficulte;
        boolean valide = false;
        do {
            System.out.println("Saisissez la difficulté de la question (1 - Facile, 2 - Moyenne, 3 - Difficile) :");
            difficulte = sc.nextInt();
    
            if (difficulte >= 1 && difficulte <= 3) {
                valide = true;
            } else {
                System.out.println("La difficulté doit être entre 1 et 3. Veuillez réessayer.");
            }
        } while (!valide);
    
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        Iterator<Object> iterator = utilisateurs.iterator();
        while (iterator.hasNext()) {
            Object utilisateur = iterator.next();
            if (utilisateur instanceof Admin) {
                Admin admin = (Admin) utilisateur;
                iterator.remove(); // Supprimer l'élément de la liste en utilisant l'itérateur
                admin.getListeQuestionProposer().add(new Question(texteQuestion, choixReponses, difficulte, choixReponses.get(indiceReponseCorrecte)));
                utilisateurs.add(admin);
                Etudiant.sauvegarderUtilisateurs(utilisateurs);
                break; // Sortir de la boucle une fois que vous avez traité un administrateur
            }
        }
    }

    /**
     * Exception levée lorsque le pseudo spécifié n'est pas valide.
     */
    class PseudoNonValideException extends Exception {
        public String toString() {
            return "Erreur : Le pseudo doit contenir entre 3 et 20 caractères.";
        }
    }
    
    /**
     * Exception levée lorsque le pseudo spécifié est déjà utilisé.
     */
    class PseudoDejaUtiliseException extends Exception {
        public String toString() {
            return "Erreur : Le pseudo est déjà utilisé.";
        }
    }
    
    /**
     * Permet de demander un changement de pseudo de l'avatar en demandant le nouveau pseudo à l'utilisateur.
     *
     * @param sc Le scanner pour la saisie du nouveau pseudo.
     */
    public void changerPseudo(Scanner sc) {
        System.out.println("Changement de pseudo");
        String nouveauPseudo = null;
        try {
            // Demander à l'utilisateur d'entrer le nouveau pseudo
            System.out.println("Entrez le nouveau pseudo (entre 3 et 20 caractères) :");
            nouveauPseudo = sc.nextLine();
    
            // Vérifier si le pseudo respecte les contraintes
            if (nouveauPseudo.length() < 3 || nouveauPseudo.length() > 20) {
                throw new PseudoNonValideException();
            }
    
            // Vérifier si le pseudo est déjà utilisé
            ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
            Avatar avatar;
            for (Object utilisateur : utilisateurs) {
                if (utilisateur instanceof Etudiant) {
                    Etudiant etudiant = (Etudiant) utilisateur;
                    avatar = etudiant.getAvatar();
                }
                else {
                    avatar = (Avatar) utilisateur;
                }
                if (avatar.getPseudo().equals(nouveauPseudo)) {
                    throw new PseudoDejaUtiliseException();
            }
        }
        } catch (PseudoNonValideException e) {
            System.out.println(e.toString());
        } catch (PseudoDejaUtiliseException e) {
            System.out.println(e.toString());
        }

        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        Iterator<Object> iterator = utilisateurs.iterator();
        while (iterator.hasNext()) {
            Object utilisateur = iterator.next();
            if (utilisateur instanceof Admin) {
                Admin admin = (Admin) utilisateur;
                iterator.remove(); // Supprimer l'élément de la liste en utilisant l'itérateur
                // Ajouter le pseudo changé au dictionnaire d'administration
                admin.getDicoModifierPseudo().put(nouveauPseudo, this.pseudo);
                utilisateurs.add(admin);
                Etudiant.sauvegarderUtilisateurs(utilisateurs);
                break; // Sortir de la boucle une fois que vous avez traité un administrateur
            }
        }
    }

    /**
     * Sauvegarde l'avatar en remplaçant l'avatar existant avec le même pseudo dans la liste des utilisateurs.
     */
    public void sauvegarderAvatar() {
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (!(avatar == null)) {
                    if (avatar.getPseudo().equals(pseudo)) {
                        utilisateurs.remove(etudiant);
                        etudiant.setAvatar(this);
                        utilisateurs.add(etudiant);
                        Etudiant.sauvegarderUtilisateurs(utilisateurs);
                    }
                }
            }
        }
    }

    /**
     * Vérifie si l'avatar est égal à un autre avatar spécifié en comparant les pseudos.
     *
     * @param avatar L'avatar à comparer.
     * @return true si les avatars sont égaux, false sinon.
     */

    public boolean equals(Avatar avatar) {
        if (this.pseudo.equals(avatar.getPseudo())) {
            return true;
        }
        return false;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'avatar,
     * contenant le pseudo et le nombre de vies.
     *
     * @return La représentation de l'avatar en tant que chaîne de caractères.
     */
    public String toString() {
        return "Pseudo : " + pseudo + " Nb vie : " + life;
    }
}

