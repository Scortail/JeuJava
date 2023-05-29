import java.util.ArrayList;
import java.io.*;


/**
 * Représente une question avec son intitulé, les choix de réponse possibles, la difficulté et la réponse valide.
 */
public class Question implements Serializable{
    private String intitule;
    private ArrayList<String> choixQuestion;
    private int difficulte; // 1 = 5 points, 2 = 10 points, 3 = 20 points
    private String reponseValide;


    /**
     * Constructeur de la classe Question.
     *
     * @param intitule        l'intitulé de la question.
     * @param choixQuestion   les choix de réponse possibles.
     * @param difficulte      la difficulté de la question.
     * @param reponseValide   la réponse valide.
     */
    public Question(String intitule, ArrayList<String> choixQuestion, int difficulte, String reponseValide) {
        this.intitule = intitule;
        this.choixQuestion = choixQuestion;
        this.difficulte = difficulte;
        this.reponseValide = reponseValide;
        ArrayList<Question> questions = chargerQuestions(); // charger les questions existants
        for (Question question : questions) { // On empeche la creation multiple de questions notamment lors des tests
            if (question.equals(this)) {
                return;
            }
        }
        questions.add(this); // ajouter la nouvelle question à la liste
        sauvegarderQuestions(questions); // sauvegarder la liste complète dans le fichier
    }

    /**
     * Retourne l'intitulé de la question.
     *
     * @return l'intitulé de la question.
     */
    public String getIntitule() {
        return intitule;
    }

    /**
     * Retourne les choix de réponse possibles.
     *
     * @return les choix de réponse possibles.
     */
    public ArrayList<String> getChoixReponse() {
        return choixQuestion;
    }

    /**
     * Retourne la difficulté de la question.
     *
     * @return la difficulté de la question.
     */
    public int getDifficulte() {
        return difficulte;
    }

    /**
     * Retourne la réponse valide.
     *
     * @return la réponse valide.
     */
    public String getReponseValide() {
        return reponseValide;
    }

    /**
     * Retourne le nombre de points associé à la difficulté de la question.
     *
     * @return le nombre de points associé à la difficulté de la question.
     */
    public int getNbPoints() {
        switch (difficulte) {
        case 1:
            return 5;
            
        case 2 :
            return 10;
        case 3:
            return 20;  

        default:
            return 0;        
        }
    }

    /**
     * Modifie l'intitulé de la question.
     *
     * @param intitule le nouvel intitulé de la question.
     */
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    /**
     * Modifie la difficulté de la question.
     *
     * @param difficulte la nouvelle difficulté de la question.
     */
    public void setNbPoints(int difficulte) {
        this.difficulte = difficulte;
        sauvegarderQuestion();
        sauvegarderQuestion();
    }

    /**
     * Modifie la réponse valide de la question.
     *
     * @param reponseValide la nouvelle réponse valide de la question.
     */
    public void setReponseValide(String reponseValide) {
        this.reponseValide = reponseValide;
        sauvegarderQuestion();
    }

    /**
     * Vérifie si la réponse donnée correspond à la réponse valide de la question.
     *
     * @param reponse la réponse donnée.
     * @return true si la réponse est valide, false sinon.
     */
    public boolean verifReponse(String reponse) {
        if (reponse.equals(reponseValide)) {
            return true;
        }
        return false;
    }

    /**
     * Cherche une question dans une liste de questions en utilisant son intitulé.
     *
     * @param questions la liste de questions.
     * @param intitule  l'intitulé de la question à rechercher.
     * @return la question recherchée, ou null si elle n'est pas trouvée.
     */
    public Question chercherQuestion(ArrayList<Question> questions, String intitule) {
        for (Question question : questions) {
            if (intitule.equals(question.getIntitule())) {
                return question;
            }
        }
        return null;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la question.
     *
     * @return la représentation de la question en tant que chaîne de caractères.
     */
    @Override
    public String toString() {
        String choix = "";
        for (int i=1; i<=getChoixReponse().size(); i++) { 
            choix += i + " : " + getChoixReponse().get(i-1)+ "\n";
        }

        return this.intitule + " Difficulte : " + this.difficulte + "\n" + choix;
    }

    /**
     * Vérifie si une question est égale à cette question en comparant leurs intitulés.
     *
     * @param question la question à comparer.
     * @return true si les questions sont égales, false sinon.
     */
    public boolean equals(Question question) {
        if (this.intitule.equals(question.intitule)) {
            return true;
        }
        return false;
    }

    /**
     * Sauvegarde toutes les questions dans un fichier.
     *
     * @param questions la liste de questions à sauvegarder.
     */
    public static void sauvegarderQuestions(ArrayList<Question> questions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("questions.ser"))) {
            for (Question question : questions) {
                oos.writeObject(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Charge toutes les questions à partir du fichier.
     *
     * @return la liste des questions chargées.
     */
    public static ArrayList<Question> chargerQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("questions.ser"))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Question) {
                        questions.add((Question) obj);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Sauvegarde la question actuelle en remplaçant une question existante avec le même intitulé, ou l'ajoute à la liste
     * des questions si elle n'existe pas encore.
     */
    public void sauvegarderQuestion() {
        ArrayList<Question> listeQuestions = chargerQuestions();
        Question question = chercherQuestion(listeQuestions, intitule);
        if (!(question == null)) {
            listeQuestions.remove(question);
            listeQuestions.add(this);
            sauvegarderQuestions(listeQuestions);
        }
    }
}