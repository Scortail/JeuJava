import java.util.ArrayList;
// Lors dun defi les joueur senvoi des question 
// a tour de role on pose une question, on a loption arreter defi regle a definir exemple: meme nb de question chacun ? des quun arrete, tout sarrete ou il faut le meme nb de question repondu

public class Defi {
    private Avatar joueur1;
    private Avatar joueur2;
    private int etat; // -1 : refusé, 0 : en attente, 1 : accepté
    private ArrayList<Question> listeQuestions;

    public Defi(Avatar joueur1, Avatar joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.etat = 0;
        this.listeQuestions = new ArrayList<Question>();
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

    public void setJoueur1(Avatar joueur1) {
        this.joueur1 = joueur1;
    }

    public void setJoueur2(Avatar joueur2) {
        this.joueur2 = joueur2;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    public ArrayList<Question> getListeQuestions() {
        return this.listeQuestions;
    }

    public void ajouterQuestion(Question question) {
        this.listeQuestions.add(question);
    }

    public void refuserDefi(Avatar avatar) {
        if (avatar.getPseudo().equals(joueur2.getPseudo())) {
            this.etat = -1;
            System.out.println("Le défi a été refusé par " + joueur2.getPseudo());
        } else {
            System.out.println("Vous ne pouvez pas refusé ce défi car vous n'êtes pas le joueur défié.");
        }
    }

    public String affichageReponseJuste() {
        return "La réponse est correct";
    }

    public String affichageReponseFausse() {
        return "La réponse est Fausse";
    }

    public String affichageWrongPlayer() {
        return "Désoler ce n'est pas ton tour";
    }

    public String affichageAToiDeJouer() {
        return "C'est ton tour mon reuf";
    }

}
