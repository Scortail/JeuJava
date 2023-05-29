import java.util.*;

/**
 * Représente un test pour l'Avatar, composé d'une liste de questions et d'un score.
 */
public class Test {
    private Avatar joueur;
    private ArrayList<Question> listeQuestions;
    private int score;

    /**
     * Constructeur de la classe Test.
     *
     * @param joueur l'Avatar associé au test.
     */
    public Test(Avatar joueur) {
        this.joueur = joueur;
        this.listeQuestions = new ArrayList<Question>();
        genererQuestionsAleatoires(joueur, Question.chargerQuestions(), 3);
        this.score = 0;
    }

    /**
     * Retourne l'Avatar associé au test.
     *
     * @return l'Avatar associé au test.
     */
    public Avatar getJoueur() {
        return joueur;
    }

    /**
     * Retourne la liste des questions du test.
     *
     * @return la liste des questions du test.
     */
    public ArrayList<Question> getListeQuestions() {
        return listeQuestions;
    }

    /**
     * Retourne le score du test.
     *
     * @return le score du test.
     */
    public int getScore() {
        return score;
    }

    /**
     * Ajoute des questions à la liste des questions du test.
     *
     * @param questions la liste des questions à ajouter.
     */
    public void ajouterQuestions(List<Question> questions) {
        listeQuestions.addAll(questions);
    }

    /**
     * Génère des questions aléatoires que l'Avatar ne possède pas encore.
     *
     * @param avatar          l'Avatar pour lequel générer les questions.
     * @param baseQuestions   la liste de toutes les questions disponibles.
     * @param nombreQuestions le nombre de questions à générer.
     */
    public void genererQuestionsAleatoires(Avatar avatar, ArrayList<Question> baseQuestions, int nombreQuestions) {
        Random random = new Random();
    
        // Créer une copie de la base de questions
        ArrayList<Question> questionsDisponibles = new ArrayList<>(baseQuestions);
    
        // Retirer les questions que l'avatar possède déjà
        questionsDisponibles.removeAll(avatar.getListeQuestion());
    
        // Vérifier si le nombre de questions demandé est supérieur à la taille de la base de questions disponible
        int tailleBaseQuestionsDisponibles = questionsDisponibles.size();
        if (nombreQuestions > tailleBaseQuestionsDisponibles) {
            nombreQuestions = tailleBaseQuestionsDisponibles;
        }
    
        // Sélectionner des questions aléatoires de la base disponible
        ArrayList<Question> questionsAleatoires = new ArrayList<>();
        while (questionsAleatoires.size() < nombreQuestions) {
            int index = random.nextInt(questionsDisponibles.size());
            questionsAleatoires.add(questionsDisponibles.remove(index));
        }
        listeQuestions.addAll(questionsAleatoires);
    }

    /**
     * Exception personnalisée pour une réponse incorrecte.
     */
    class WrongChoiceQuestion extends Exception{

        public String toString(){
            return "Erreur: Ce n'est pas une réponse possible";
        }
    }
    
    /**
     * Demande au joueur la réponse à une question.
     *
     * @param question la question à laquelle le joueur doit répondre.
     * @param sc       le scanner pour lire la réponse du joueur.
     * @return la réponse du joueur.
     * @throws WrongChoiceQuestion si la réponse du joueur n'est pas valide.
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
     * Permet au joueur de répondre à une question du test.
     *
     * @param question la question à laquelle le joueur doit répondre.
     * @param joueur   l'Avatar qui répond à la question.
     * @param sc       le scanner pour lire la réponse du joueur.
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
            System.out.println("Réponse juste!");
            System.out.println("Vous avez gagné " + question.getNbPoints() + " vies");
            score += 1;
        }

        // Le joueur a repondu faux
        else {
            System.out.println("Réponse fausse!");
        }
    }
}

