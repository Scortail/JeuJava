import java.util.Scanner;

public class Test_test {
    public static void main(String[] args) {
        // Création d'un joueur
        Avatar joueur = new Avatar("test", 10);

        // Création d'un test avec le joueur
        Test test = new Test(joueur);

        // Affichage des questions du test
        System.out.println("Questions du test :");
        for (Question question : test.getListeQuestions()) {
            System.out.println(question);
        }

        // Scanner pour lire les réponses du joueur
        Scanner scanner = new Scanner(System.in);

        // Répondre aux questions du test
        for (Question question : test.getListeQuestions()) {
            test.repondreQuestion(question, joueur, scanner);
        }

        // Affichage du score final
        System.out.println("Score final : " + test.getScore());
    }
}

