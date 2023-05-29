import java.util.*;

/**
 * La classe Admin représente un administrateur, qui est une sous-classe d'Avatar.
 */
public class Admin extends Avatar {
    private static ArrayList<Question> listeQuestionProposer = new ArrayList<Question>();
    private static Map<String, String> dicoModifierPseudo = new HashMap<String, String>();

    /**
     * Constructeur de la classe Admin.
     *
     * @param pseudo Le pseudo de l'administrateur.
     * @param life   Le niveau de vie de l'administrateur.
     */
    public Admin(String pseudo, double life) {
        super(pseudo, life);
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs(); // charger les admins existants
        for (Object utilisateur : utilisateurs) { // On empêche la création multiple d'admins notamment lors des tests
            if (utilisateur instanceof Admin) {
                Admin admin = (Admin) utilisateur;
                if (admin.equals(this)) {
                    return;
                }
            }
        }
        utilisateurs.add(this); // ajouter le nouvel étudiant à la liste
        Etudiant.sauvegarderUtilisateurs(utilisateurs); // sauvegarder la liste complète dans le fichier
    }

    /**
     * Retourne la liste des questions proposées.
     *
     * @return La liste des questions proposées.
     */
    public ArrayList<Question> getListeQuestionProposer() {
        return listeQuestionProposer;
    }

    /**
     * Retourne le dictionnaire de modification des pseudos.
     *
     * @return Le dictionnaire de modification des pseudos.
     */
    public Map<String, String> getDicoModifierPseudo() {
        return dicoModifierPseudo;
    }

    /**
     * Exception levée lorsque aucune question valide n'est entrée.
     */
    class NoValidQuestionException extends Exception {
        public String toString() {
            return "Erreur : vous n'avez entré aucune question valide.";
        }
    }

    /**
     * Exception levée lorsque le pseudo saisi n'est pas valide.
     */
    class NoValidPseudoException extends Exception {
        public String toString() {
            return "Erreur : le numéro de pseudo saisi n'est pas valide.";
        }
    }

    /**
     * Permet de choisir un pseudo à modifier.
     *
     * @param sc Le Scanner utilisé pour la saisie.
     * @return Le pseudo choisi à modifier.
     * @throws NoValidPseudoException Si le pseudo saisi n'est pas valide.
     */
    public String pseudoChoisie(Scanner sc) throws NoValidPseudoException {
        String pseudoChoisi = "";
        afficherListeModifierPseudo();
        System.out.println("Pseudo : ");
        sc.nextLine();
        if (dicoModifierPseudo.containsValue(pseudoChoisi))
            return pseudoChoisi;
        else {
            throw new NoValidPseudoException();
        }
    }

    /**
     * Permet de choisir une modification d'avatar.
     *
     * @param sc Le Scanner utilisé pour la saisie.
     */
    public void choixModificationAvatar(Scanner sc) {
        try {
            String pseudoChoisi = pseudoChoisie(sc);
            if (pseudoChoisi.equalsIgnoreCase("q")) {
                return;
            }
            String nouveauPseudo = dicoModifierPseudo.get(pseudoChoisi);
            setPseudo(nouveauPseudo);
            System.out.println("Le pseudo a été modifié avec succès. Nouveau pseudo : " + nouveauPseudo);
        } catch (NoValidPseudoException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Récupère le numéro de question saisi par l'utilisateur.
     *
     * @param sc Le Scanner utilisé pour la saisie.
     * @return Le numéro de question saisi.
     * @throws NoValidQuestionException Si le numéro de question saisi n'est pas valide.
     */
    public int getNumQuestion(Scanner sc) throws NoValidQuestionException {
        int questionIndex;
        try {
            questionIndex = sc.nextInt() - 1;
            sc.nextLine();
        } catch (InputMismatchException e) {
            throw new NoValidQuestionException();
        }
        if (questionIndex >= 0 && questionIndex < listeQuestionProposer.size()) {
            return questionIndex;
        } else {
            throw new NoValidQuestionException();
        }
    }

    /**
     * Affiche la liste des questions proposées.
     */
    public void afficherListeQuestionProposer() {
        int cpt = 1;
        for (Question question : listeQuestionProposer) {
            System.out.println(cpt + " " + question);
            cpt += 1;
        }
    }

    /**
     * Affiche la liste des pseudos à modifier.
     */
    public void afficherListeModifierPseudo() {
        int cpt = 1;
        for (Map.Entry<String, String> entry : dicoModifierPseudo.entrySet()) {
            System.out.println(cpt + " " + entry.getValue() + " veut s'appeler : " + entry.getKey());
            cpt += 1;
        }
    }

    /**
     * Permet de choisir une question à traiter.
     *
     * @param sc Le Scanner utilisé pour la saisie.
     */
    public void choisirQuestion(Scanner sc) {
        afficherListeQuestionProposer();
        boolean valide = false;
        while (!valide) {
            System.out.println("Numéro de question : ");
            try {
                int questionIndex = getNumQuestion(sc);
                Question selectedQuestion = listeQuestionProposer.get(questionIndex);

                System.out.println("Question sélectionnée : " + selectedQuestion);
                System.out.println("Voulez-vous l'accepter ? (O/N)");
                String response = sc.nextLine();

                if (response.equalsIgnoreCase("O")) {
                    // Ajouter la question avec la méthode sauvegarderQuestion()
                    selectedQuestion.sauvegarderQuestion();
                    System.out.println("La question a été acceptée et ajoutée.");
                } else if (response.equalsIgnoreCase("n")) {
                    // Supprimer la question de la liste
                    listeQuestionProposer.remove(questionIndex);
                    System.out.println("La question a été refusée et supprimée.");
                } else {
                    System.out.println("Réponse invalide. Veuillez répondre par 'Oui' ou 'Non'.");
                }

                valide = true;
            } catch (NoValidQuestionException e) {
                System.out.println(e.toString());
                return;
            }
        }
    }

    /**
     * Supprime un avatar.
     *
     * @param avatar_supprimer L'avatar à supprimer.
     */
    public void supprimerAvatar(Avatar avatar_supprimer) {
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (!(avatar == null)) {
                    if (avatar.equals(avatar_supprimer)) {
                        // On supprime les identifiants
                        Login login = new Login();
                        login.supprimerLogin(avatar_supprimer.getPseudo());
                        // On supprime l'avatar
                        etudiant.setAvatar(null);
                        Etudiant.sauvegarderUtilisateurs(utilisateurs);
                        System.out.println("Avatar supprimé.");
                        return;
                    }
                }
            }
        }
        System.out.println("Avatar non trouvé !");
    }
}
