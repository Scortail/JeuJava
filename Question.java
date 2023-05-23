import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;

public class Question implements Serializable{
    private String intitule;
    private ArrayList<String> choixQuestion;
    private int difficulte; // 1 = 5 points, 2 = 10 points, 3 = 20 points
    private String reponseValide;


    // Quand on cree une question verifier la difficulte
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

    public String getIntitule() {
        return intitule;
    }

    public ArrayList<String> getChoixReponse() {
        return choixQuestion;
    }

    public int getDifficulte() {
        return difficulte;
    }

    public String getReponseValide() {
        return reponseValide;
    }

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

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setNbPoints(int difficulte) {
        this.difficulte = difficulte;
        sauvegarderQuestion();
        
    }

    public void setReponseValide(String reponseValide) {
        this.reponseValide = reponseValide;
        sauvegarderQuestion();
    }

    public boolean verifReponse(String reponse) {
        if (reponse.equals(reponseValide)) {
            return true;
        }
        return false;
    }

    // Retourne la question rechercher
    public Question chercherQuestion(ArrayList<Question> questions, String intitule) {
        for (Question question : questions) {
            if (intitule.equals(question.getIntitule())) {
                return question;
            }
        }
        return null;
    }

    public String toString() {
        return this.intitule + " Choix : " + this.choixQuestion + " Difficulte : " + this.difficulte;
    }

    public boolean equals(Question question) {
        if (this.intitule.equals(question.intitule)) {
            return true;
        }
        return false;
    }

    // Écrit toutes les questions dans un nouveau fichier
    public static void sauvegarderQuestions(ArrayList<Question> questions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("questions.ser"))) {
            for (Question question : questions) {
                oos.writeObject(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Charge tous les questions depuis le fichier
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

    // Nous permet de sauvegarder une question
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