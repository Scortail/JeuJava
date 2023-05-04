// chaque joeur vont pouvoir avoir des questions de base peut etre il faut quil y reponde dabord mais en cas de bonne reponse a une question elle sajoute a sa liste des questions.
import java.util.ArrayList;
// import java.util.Scanner;

public class Question {
    private String intitule;
    private ArrayList<String> choixQuestion;
    private int nbPoints;
    private String reponseValide;

    public Question(String intitule, ArrayList<String> choixQuestion, int nbPoints, String reponseValide) {
        this.intitule = intitule;
        this.choixQuestion = choixQuestion;
        this.nbPoints = nbPoints;
        this.reponseValide = reponseValide;


        // boolean choixReponses = true;
        // while (choixReponses) {

        //     Scanner choixReponse = new Scanner(System.in);  // Create a Scanner object
        //     System.out.println("Entrez un mot pour ajouter une reponse a la question puis taper entrez ou entrez ok apres avoir mis a moins 2 reponses différentes! ");

        //     String reponse = choixReponse.nextLine();  // Read user input
        //     if (reponse == "ok" && choixQuestion.size() > 1) {

        //     }

        //     System.out.println("Username is: " + userName);  // Output user input
        // }

    }

    public String getIntitule() {
        return intitule;
    }

    public ArrayList<String> getChoixReponse() {
        return choixQuestion;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public String getReponseValide() {
        return reponseValide;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public void setReponseValide(String reponseValide) {
        this.reponseValide = reponseValide;
    }

    public boolean verifReponse(String reponse) {
        if (reponse.equals(reponseValide)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.intitule + " Choix : " + this.choixQuestion + " Nb Point: " + this.nbPoints;
    }

    public boolean equals(Question question) {
        if (this.intitule == question.intitule && this.choixQuestion == question.choixQuestion && this.nbPoints == question.nbPoints && this.reponseValide == question.reponseValide) {
            return true;
        }
        return false;
    }
}