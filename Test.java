import java.util.*;

public class Test {
    private Avatar joueur;
    private ArrayList<Question> listeQuestions;
    private int score;

    public Test(Avatar joueur) {
        this.joueur = joueur;
        this.listeQuestions = new ArrayList<Question>();
        genererQuestionsAleatoires(joueur, listeQuestions, 3);
        this.score = 0;
    }

    public Avatar getJoueur() {
        return joueur;
    }

    public ArrayList<Question> getListeQuestions() {
        return listeQuestions;
    }

    public int getScore() {
        return score;
    }

    public void ajouterQuestions(List<Question> questions) {
        listeQuestions.addAll(questions);
    }

    // Genere des questions aleatoire que l'avatar ne possède pas
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
    

    public void jouerTest(Scanner sc) {

        for (Question question : listeQuestions) {
            System.out.println(question);
            System.out.print("Votre réponse : ");
            String reponse = sc.nextLine();

            if (question.verifReponse(reponse)) {
                score += question.getNbPoints();
                System.out.println("Réponse correcte !");
            } else {
                System.out.println("Réponse incorrecte !");
            }
            System.out.println();
        }

        double pourcentageReussite = (double) score / (listeQuestions.size() * Question.getNbPoints()) * 100;
        System.out.println("Test terminé !");
        System.out.println("Votre score : " + score + "/" + (listeQuestions.size() * Question.getNbPointsMax()));
        System.out.println("Pourcentage de réussite : " + pourcentageReussite + "%");
    }
}

