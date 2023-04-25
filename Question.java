// chaque joeur vont pouvoir avoir des questions de base peut etre il faut quil y reponde dabord mais en cas de bonne reponse a une question elle sajoute a sa liste des questions.
import java.util.ArrayList;
// import java.util.Scanner;

public class Question {
    private String question;
    private ArrayList<String> listeReponse;
    private int nbPoints;
    private String reponseValide;

    public Question(String question, ArrayList<String> listeReponse, int nbPoints, String reponseValide) {
        this.question = question;
        this.listeReponse = listeReponse;
        this.nbPoints = nbPoints;
        this.reponseValide = reponseValide;


        // boolean choixReponses = true;
        // while (choixReponses) {

        //     Scanner choixReponse = new Scanner(System.in);  // Create a Scanner object
        //     System.out.println("Entrez un mot pour ajouter une reponse a la question puis taper entrez ou entrez ok apres avoir mis a moins 2 reponses différentes! ");

        //     String reponse = choixReponse.nextLine();  // Read user input
        //     if (reponse == "ok" && listeReponse.size() > 1) {

        //     }

        //     System.out.println("Username is: " + userName);  // Output user input
        // }

    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getListeReponse() {
        return listeReponse;
    }

    public int getNbPoints() {
        return nbPoints;
    }

    public String getReponseValide() {
        return reponseValide;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setNbPoints(int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public void setreponseValide(String reponseValide) {
        this.reponseValide = reponseValide;
    }


    public String toString() {
        return "Question : " + this.question + "Choix : " + this.listeReponse + " Nb Point: " + this.nbPoints + " Reponse : " + this.reponseValide;
    }
}