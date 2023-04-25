import java.util.ArrayList;
// Lors dun defi les joueur senvoi des question 
// a tour de role on pose une question, on a loption arreter defi regle a definir exemple: meme nb de question chacun ? des quun arrete, tout sarrete ou il faut le meme nb de question repondu

public class Defi {
    private Avatar defieur;
    private Avatar defier;
    private ArrayList<Question> listeQuestions;
    private int etat;  // -1 : refusé, 0 : neutre, 1 : accepter

    public Defi(Avatar defieur, Avatar defier, ArrayList<Question> listeQuestions) {
        this.defieur = defieur;
        this.defier = defier;
        this.listeQuestions = listeQuestions;
        this.etat = 0;
    }

    public 

}