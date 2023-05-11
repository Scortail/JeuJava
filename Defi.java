import java.util.*;

public class Defi {
    private Avatar joueur1;
    private Avatar joueur2;
    private int etat; // -1 : refusé, 0 : en attente, 1 : accepté
    private ArrayList<Question> listeQuestionsJoueur1;
    private ArrayList<Question> listeQuestionsJoueur2;

    public Defi(Avatar joueur1, Avatar joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.etat = 0;
        this.listeQuestionsJoueur1 = new ArrayList<Question>();
        this.listeQuestionsJoueur2 = new ArrayList<Question>();
    }

    public Avatar getJoueur1() {
        return joueur1;
    }

    public Avatar getJoueur2() {
        return joueur2;
    }

    public int getEtat() {
        return this.etat;
    }

    public ArrayList<Question> getListeQuestionsJoueur1() {
        return this.listeQuestionsJoueur1;
    }

    public ArrayList<Question> getListeQuestionsJoueur2() {
        return this.listeQuestionsJoueur2;
    }

    public void setJoueur1(Avatar joueur1) {
        this.joueur1 = joueur1;
    }

    public void setJoueur2(Avatar joueur2) {
        this.joueur2 = joueur2;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    class WrongChoiceQuestion extends Exception{

        public String toString(){
            return "Erreur: Ce n'est pas une réponse possible";
        }
    }
    
    public String getJoueurReponse(Question question) throws WrongChoiceQuestion{
        Scanner sc = new Scanner(System.in) ;

        System.out.println("Votre réponse : ");
        String reponse = sc.nextLine();


        if (!question.getChoixReponse().contains(reponse))
            throw new WrongChoiceQuestion();
        return reponse;
    }

    public void ajouterQuestionJoueur1(Question question) {
        this.listeQuestionsJoueur1.add(question);
    }

    public void ajouterQuestionJoueur2(Question question) {
        this.listeQuestionsJoueur2.add(question);
    }

    public void repondreQuestion(Question question, Avatar joueur) {
        String reponse = null;

        boolean valide = false;

        while( !valide ) {
            try {
                System.out.println(question);
                reponse = getJoueurReponse(question);
                valide = true;
            }
            catch(WrongChoiceQuestion wcq){
                wcq.printStackTrace();
            }
        }
        // Le joueur a repondu juste
        if (reponse.equals(question.getReponseValide())) {
            joueur.ajouterVie(question.getNbPoints());
            if (joueur.equals(joueur1)) {
                joueur2.retirerVie(question.getNbPoints());
            }
            else {
                joueur1.retirerVie(question.getNbPoints());
            }
            System.out.println("Réponse juste!");
            System.out.println("Vous avez gagné " + question.getNbPoints() + " vies");
        }

        // Le joueur a repondu faux
        else {
            joueur.retirerVie(question.getNbPoints());
            if (joueur.equals(joueur1)) {
                joueur2.ajouterVie(question.getNbPoints());
            }
            else {
                joueur1.ajouterVie(question.getNbPoints());
            }
            System.out.println("Réponse fausse!");
            System.out.println("Vous avez perdu " + question.getNbPoints() + " vies");
        }

    }
}
