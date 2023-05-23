import java.util.*;


public class seriousGame {

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
        Scanner sc = new Scanner(System.in) ;

        System.out.println("Choix : ");
        String choix = sc.nextLine();


        if (!choix.equals("1") && !choix.equals("2") && !choix.equals("Q") && !choix.equals("q"))
            throw new WrongOptionChoice();
        return choix;
    }

    // Permet de trouver un avatar
    public Avatar getChoixAvatar() throws AvatarInexistantException{
        Scanner sc = new Scanner(System.in) ;

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
        Scanner sc = new Scanner(System.in) ;

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
        if (questionsChoisies.size() == 0) {
            throw new NoValidQuestionException();
        }
        return questionsChoisies;
    }


    

    public void play(Login login) {
        Avatar avatar = login.getEtudiant().getAvatar();
        boolean play = true;
        while (play) {
            Boolean valide = false;
            String choix = null;
            while (!valide) {
                try {
                    System.out.println("Bonjour " + avatar.getPseudo());
                    System.out.println("Taper 1 pour voir les défis en cours");
                    System.out.println("Taper 2 pour defier un avatar");
                    System.out.println("Taper Q pour quitter");
                    choix = userChoice();
                    valide = true;
                } catch (WrongOptionChoice woc) {
                    woc.printStackTrace();
                }
            }
            if (choix.equals("Q") || choix.equals("q")) {
                play = false;
            }
            else if(choix.equals("2")) {
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

    
    public Login menu_principal() {
        Login login = null;
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
            }

            else if(choix.equals("2")) {
                login = new Login();
                login.inscription();  
            }

            else if(choix.equals("1")) {
                login = new Login();
                login.connexion();
            } 
        }
        return login;
    }
}
