import java.util.*;

/**
 * Cette classe représente un serious game.
 * Elle permet de gérer les différentes fonctionnalités du jeu, telles que l'administration, le jeu lui-même, la sélection d'avatar, etc.
 */
public class seriousGame {
    private static Scanner sc = new Scanner(System.in);

    /**
     * Exception levée lorsque le choix de l'utilisateur n'est pas valide.
     */
    class WrongOptionChoice extends Exception {
        public String toString() {
            return "Ce choix n'est pas possible";
        }
    }

    /**
     * Exception levée lorsqu'un avatar recherché n'existe pas.
     */
    class AvatarInexistantException extends Exception {
        public String toString() {
            return "Le pseudo recherché n'existe pas, veuillez réessayez.";
        }
    }

    /**
     * Exception levée lorsqu'aucune question valide n'est entrée.
     */
    class NoValidQuestionException extends Exception {
        public String toString() {
            return "Erreur : vous n'avez entrez aucune question valide.";
        }
    }

    /**
     * Méthode permettant à l'utilisateur de faire un choix parmi une liste d'options possibles.
     * @param choixPossibles La liste des options possibles.
     * @return Le choix de l'utilisateur.
     * @throws WrongOptionChoice Exception levée si le choix de l'utilisateur n'est pas valide.
     */
    public String userChoice(List<String> choixPossibles) throws WrongOptionChoice {
        System.out.println("Choix : ");
        String choix = sc.nextLine();

        if (!choixPossibles.contains(choix))
            throw new WrongOptionChoice();

        return choix;
    }

    /**
     * Méthode permettant de choisir un avatar parmi ceux disponibles.
     * @param joueur L'avatar du joueur actuel.
     * @return L'avatar choisi par le joueur.
     * @throws AvatarInexistantException Exception levée si le pseudo recherché n'existe pas.
     */
    public Avatar getChoixAvatar(Avatar joueur) throws AvatarInexistantException{
        System.out.println(joueur.afficherAvatars());
        System.out.println("Pseudo : ");
        String pseudo = sc.nextLine();
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (!(etudiant.getAvatar() == null)) {
                    if (avatar.getPseudo().equals(pseudo)) {
                        return avatar;
                    }
                }    
            }      
        }
        throw new AvatarInexistantException();
        }

    /**
     * Méthode permettant de choisir les questions à poser.
     * @param avatar L'avatar pour lequel choisir les questions.
     * @return La liste des questions choisies.
     * @throws NoValidQuestionException Exception levée si aucune question valide n'est entrée.
     */
    public ArrayList<Question> getChoixQuestions(Avatar avatar) throws NoValidQuestionException{

        System.out.println("Quelle question voulez vous poser : (mettre une virgule entre les numéro)");
        System.out.println(avatar.afficherQuestionsDispo());
        System.out.println("Numéro questions : ");
        String numQuestions = sc.nextLine();
        String[] numQuestionsArray = numQuestions.split(",");
        ArrayList<Question> questionsChoisies = new ArrayList<>();

        for (String numQuestion : numQuestionsArray) {
            try {
                int numero = Integer.parseInt(numQuestion.trim());
                Question question = avatar.getQuestion(numero);
                if (question != null) {
                    questionsChoisies.add(question);
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : le numéro de question \"" + numQuestion + "\" n'est pas un chiffre valide.");
            }
        }
        if (questionsChoisies.isEmpty()) {
            throw new NoValidQuestionException();
        }
        return questionsChoisies;
    }

    /**
     * Méthode permettant d'afficher les défis en cours d'un avatar.
     * @param avatar L'avatar pour lequel afficher les défis en cours.
     */
    private void afficherDefisEnCours(Avatar avatar) {
    System.out.println("Liste des défis en cours :");
    ArrayList<Defi> defis = avatar.getListeDefi();
    Defi defiChoisi = null;
    
    // Affichage des défis en cours
    for (int i = 0; i < defis.size(); i++) {
        Defi defi = defis.get(i);
        System.out.println("Défi " + (i + 1));
        if (avatar.equals(defi.getJoueur1())) {
            System.out.println("Joueur adversaire : " + defi.getJoueur2().getPseudo());
        }
        else {
            System.out.println("Joueur adversaire : " + defi.getJoueur1().getPseudo());
        }
        System.out.println("État du défi : " + defi.getEtat());
        defi.afficherTempsRestant();
        System.out.println();
    }
    
    boolean choisirDefi = true;
    while (choisirDefi) {
        System.out.println("Taper le numéro du défi auquel vous souhaitez participer (ou Q pour quitter) : ");
        String choixDefi = sc.nextLine();
        
        if (choixDefi.equalsIgnoreCase("Q")) {
            choisirDefi = false;
        } else {
            try {
                int numeroDefi = Integer.parseInt(choixDefi) - 1;
                if (numeroDefi >= 0 && numeroDefi < defis.size()) {
                    defiChoisi = defis.get(numeroDefi);
                    // Si on a créé le defi mais qu'il nest pas encore accepter
                    if (defiChoisi.getEtat() == 0 && defiChoisi.getJoueur1().equals(avatar)) {
                        System.out.println("En attente d'une réponse de l'adversaire");
                    }
                    // Si on est defier
                    else if (defiChoisi.getEtat() == 0) { // Défi en attente d'acceptation
                        System.out.println("Voulez-vous accepter ou refuser le défi ? (A pour accepter, R pour refuser) : ");
                        String choixAccepter = sc.nextLine();
                        
                        if (choixAccepter.equalsIgnoreCase("A")) { // Accepter le défi
                            Boolean valide = false;
                            ArrayList<Question> listeQuestions = null;
                            while (!valide) {
                                try {
                                    listeQuestions = getChoixQuestions(avatar);
                                    valide = true;
                                } catch (NoValidQuestionException nvqe) {
                                    nvqe.printStackTrace();
                                }
                            }
                            System.out.println(listeQuestions);
                            avatar.accepterDefi(defiChoisi, listeQuestions.toArray(new Question[listeQuestions.size()]));
                            choisirDefi = false;
                        } else if (choixAccepter.equalsIgnoreCase("R")) { // Refuser le défi
                            avatar.refuserDefi(defiChoisi);
                            System.out.println("Vous avez refusé le défi.");
                            choisirDefi = false;
                        } else {
                            System.out.println("Choix invalide. Veuillez réessayer.");
                        }

                    } else if (defiChoisi.getEtat() == 1) { // Défi accepté
                        System.out.println("Voulez-vous participer à ce défi ? (O/N) : ");
                        String choixParticiper = sc.nextLine();
                        
                        if (choixParticiper.equalsIgnoreCase("O")) { // Participer au défi
                            avatar.jouer(defiChoisi, sc);
                            choisirDefi = false;
                        } else if (choixParticiper.equalsIgnoreCase("N")) { // Ne pas participer au défi
                            choisirDefi = false;
                        } else {
                            System.out.println("Choix invalide. Veuillez réessayer.");
                        }
                    }
                } else {
                    System.out.println("Numéro de défi invalide. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Numéro de défi invalide. Veuillez réessayer.");
            }
        }
    }
    }

    /**
     * Méthode gérant le menu d'administration du jeu.
     * @param admin L'administrateur du jeu.
     */
    public void menuAdministration(Admin admin) {
        boolean play = true;
        while (play) {
            Boolean valide = false;
            String choix = null;
            while (!valide) {
                try {
                    List<String> optionsMenu = Arrays.asList("1", "2", "Q", "q");
                    System.out.println("Taper 1 pour administrer");
                    System.out.println("Taper 2 pour jouer");
                    System.out.println("Taper Q pour quitter");
                    choix = userChoice(optionsMenu);
                    valide = true;
                } catch (WrongOptionChoice woc) {
                    woc.printStackTrace();
                }
            }
            if (choix.equals("Q") || choix.equals("q")) {
                play = false;

            } else if (choix.equals("1")) { // Condition pour pouvoir administrer le jeu
                administration(admin);

            } else if (choix.equals("2")) { // Condition pour pouvoir jouer au jeu
                play(admin);
            }
        }
    }

    /**
     * Méthode gérant l'administration du jeu.
     * @param admin L'administrateur du jeu.
     */
    public void administration(Admin admin) {
        boolean play = true;
        while (play) {
            Boolean valide = false;
            String choix = null;
            while (!valide) {
                try {
                    List<String> optionsMenu = Arrays.asList("1", "2", "3", "Q", "q");
                    System.out.println("Taper 1 pour voir les nouvelles questions proposer");
                    System.out.println("Taper 2 acceder au demande de modification de pseudo");
                    System.out.println("Taper 3 pour supprimer un avatar");
                    System.out.println("Taper Q pour quitter");
                    choix = userChoice(optionsMenu);
                    valide = true;
                } catch (WrongOptionChoice woc) {
                    woc.printStackTrace();
                }
            }
            if (choix.equals("Q") || choix.equals("q")) {
                play = false;

            } else if (choix.equals("1")) { // Gerer les nouvelles questions
                admin.choisirQuestion(sc);

            } else if (choix.equals("2")) { // Modifier un pseudo
                admin.choixModificationAvatar(sc);
                

            } else if (choix.equals("3")) { // Supprimer un avatar
                Avatar avatar_supprimer = null;
                valide = false;
                while (!valide) {
                    try {
                        System.out.println("Qui voulez vous supprimer ?");
                        avatar_supprimer = getChoixAvatar(admin);
                        valide = true;
                    } catch (AvatarInexistantException aie) {
                        aie.printStackTrace();
                }
                admin.supprimerAvatar(avatar_supprimer);
                }
            }
        }
    }

    /**
     * Méthode gérant le déroulement du jeu pour un avatar donné.
     * @param avatar L'avatar pour lequel jouer.
     */
    public void play(Avatar avatar) {
        System.out.println("Bonjour " + avatar.getPseudo());
        boolean play = true;
        while (play) {
            Boolean valide = false;
            String choix = null;
            while (!valide) {
                try {
                    List<String> optionsMenu = Arrays.asList("1", "2", "3", "Q", "q");
                    System.out.println("Taper 1 pour voir les défis en cours");
                    System.out.println("Taper 2 pour défier un avatar");
                    System.out.println("Taper 3 pour passer un test");
                    System.out.println("Taper 4 pour proposer de nouvelles questions");
                    System.out.println("Taper 5 pour changer de pseudo");
                    System.out.println("Taper Q pour quitter");
                    choix = userChoice(optionsMenu);
                    valide = true;
                } catch (WrongOptionChoice woc) {
                    woc.printStackTrace();
                }
            }
            if (choix.equals("Q") || choix.equals("q")) {
                play = false;

            } else if (choix.equals("1")) { // Ajout de la condition pour afficher les défis en cours
                afficherDefisEnCours(avatar);

            } else if (choix.equals("2")) { // Defier un avatar
                valide = false;
                Avatar avatar_defier = null;
                ArrayList<Question> listeQuestions = null;
                while (!valide) {
                    try {
                        System.out.println("Qui voulez vous défier?");
                        avatar_defier = getChoixAvatar(avatar);
                        listeQuestions = getChoixQuestions(avatar);
                        valide = true;
                    } catch (AvatarInexistantException aie) {
                        aie.printStackTrace();
                    } catch (NoValidQuestionException nvqe) {
                        nvqe.printStackTrace();
                    }
                }
                avatar.creerDefi(avatar_defier, listeQuestions.toArray(new Question[listeQuestions.size()]));
            } else if (choix.equals("3")) {
                avatar.jouerTest(sc);
            }
            else if (choix.equals("4")) {
                avatar.soumettreQuestion(sc);
            }
            else if (choix.equals("5")) {
                avatar.changerPseudo(sc);
            }
        }
    }
    
    /**
     * Méthode gérant le menu principal du jeu.
     */
    public void menu_principal() {
        Login login = null;
        boolean start = true;
        while (start) {
            boolean menu = true;
            while (menu) {
                boolean valide = false;
                String choix = null;
                while (!valide) {
                    try {
                        List<String> optionsMenu = Arrays.asList("1", "2", "Q", "q");
                        System.out.println("Bienvenue dans le serious game");
                        System.out.println("Taper 1 pour vous identifier");
                        System.out.println("Taper 2 pour vous inscrire");
                        System.out.println("Taper Q pour quitter");
                        choix = userChoice(optionsMenu);
                        valide = true;
                    } catch (WrongOptionChoice woc) {
                        woc.printStackTrace();
                    }
                }
                if (choix.equals("Q") || choix.equals("q")) {
                    return;
                }
                else if(choix.equals("2")) {
                    login = new Login();
                    login.inscription(sc);
                    menu = false;
                }
                else if(choix.equals("1")) {
                    login = new Login();
                    login.connexion(sc);
                    menu = false;
                }
            }
            // admin connecté
            if (login.getEtudiant() == null) {
                // On récupère l'admin
                ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
                for (Object utilisateur : utilisateurs) {
                    if (utilisateur instanceof Admin) {
                        Admin admin = (Admin) utilisateur;
                        if (login.getLogin().equals(admin.getPseudo()))
                            menuAdministration(admin);
                    }
                }
            }
            // Joueur connecté
            else {
                Avatar avatar = login.getEtudiant().getAvatar();
                play(avatar);
            }      
            menu = true;
        }
    }
}
