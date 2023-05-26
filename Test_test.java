public class Main {
    public static void main(String[] args) {
        // Création d'un avatar pour le joueur
        Avatar joueur = new Avatar("Nom du joueur");

        // Chargement des questions depuis la base existante
        ArrayList<Question> baseQuestions = Question.chargerQuestions();

        // Création d'un test
        Test test = new Test(joueur);

        // Génération de 5 questions aléatoires à partir de la base
        test.genererQuestionsAleatoires(baseQuestions, 5);

        // Jouer le test
        test.jouerTest();
    }
}
