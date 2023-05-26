import java.util.*;

public class seriousGame {
    private static Scanner sc = new Scanner(System.in);


    class WrongOptionChoice extends Exception {
        public String toString() {
            return "Ce choix n'est pas possible";
        }
    }


    class AvatarInexistantException extends Exception {
        public String toString() {
            return "Le pseudo recherché n'existe pas, veuillez réessayez.";
        }
    }

    class NoValidQuestionException extends Exception {
        public String toString() {
            return "Erreur : vous n'avez entrez aucune question valide.";
        }
    }

    public String userChoice() throws WrongOptionChoice{

        System.out.println("Choix : ");
        String choix = sc.nextLine();
        
        if (!choix.equals("1") && !choix.equals("2") && !choix.equals("Q") && !choix.equals("q"))
            throw new WrongOptionChoice();

        return choix;
    }

    // Permet de trouver un avatar
    public Avatar getChoixAvatar() throws AvatarInexistantException{

        System.out.println("Qui voulez vous défier?");
        System.out.println(Avatar.afficherAvatars());
        System.out.println("Pseudo : ");
        String pseudo = sc.nextLine();
        ArrayList<Etudiant> listeEtudiants = Etudiant.chargerEtudiant();
        for (Etudiant etudiant : listeEtudiants) {
            Avatar avatar = etudiant.getAvatar();
            if (!(etudiant.getAvatar() == null)) {
                if (avatar.getPseudo().equals(pseudo)) {
                    return avatar;
                }
            }          
        }
        throw new AvatarInexistantException();
        }

    // Permet de choisir les questions à poser
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

    // Affiche les defis en cours d'un avatar
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


    public void play(Login login) {
        Avatar avatar = login.getEtudiant().getAvatar();
        System.out.println("Bonjour " + avatar.getPseudo());
        boolean play = true;
        while (play) {
            Boolean valide = false;
            String choix = null;
            while (!valide) {
                try {
                    System.out.println("Taper 1 pour voir les défis en cours");
                    System.out.println("Taper 2 pour défier un avatar");
                    System.out.println("Taper Q pour quitter");
                    choix = userChoice();
                    valide = true;
                } catch (WrongOptionChoice woc) {
                    woc.printStackTrace();
                }
            }
            if (choix.equals("Q") || choix.equals("q")) {
                play = false;

            } else if (choix.equals("1")) { // Ajout de la condition pour afficher les défis en cours
                afficherDefisEnCours(avatar);

            } else if (choix.equals("2")) {
                valide = false;
                Avatar avatar_defier = null;
                ArrayList<Question> listeQuestions = null;
                while (!valide) {
                    try {
                        avatar_defier = getChoixAvatar();
                        listeQuestions = getChoixQuestions(avatar);
                        valide = true;
                    } catch (AvatarInexistantException aie) {
                        aie.printStackTrace();
                    } catch (NoValidQuestionException nvqe) {
                        nvqe.printStackTrace();
                    }
                }
                avatar.creerDefi(avatar_defier, listeQuestions.toArray(new Question[listeQuestions.size()]));
            }
        }
    }
    
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
                        System.out.println("Bienvenue dans le serious game");
                        System.out.println("Taper 1 pour vous identifier");
                        System.out.println("Taper 2 pour vous inscrire");
                        System.out.println("Taper Q pour quitter");
                        choix = userChoice();
                        valide = true;
                    } catch (WrongOptionChoice woc) {
                        woc.printStackTrace();
                    }
                }
                if (choix.equals("Q") || choix.equals("q")) {
                    menu = false;
                    start = false;
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
            play(login);
            menu = true;
        }
    }
}
