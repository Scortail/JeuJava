import java.util.ArrayList;



public class Avatar {
    private String pseudo;
    private double life;
    private ArrayList<Defi> listeDefi = new ArrayList<Defi>();
    private ArrayList<Question> listeQuestion = new ArrayList<Question>();


    public Avatar(String pseudo, double life) {
        this.pseudo = pseudo;
        this.life = life;
    }


    public String getPseudo() {
        return this.pseudo;
    }


    public double getLife() {
        return this.life;
    }


    public ArrayList<Defi> getListeDefi() {
        return this.listeDefi;
    }


    public ArrayList<Question> getListeQuestion() {
        return this.listeQuestion;
    }


    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    public void setLife(float life) {
        this.life = life;
    }
    


    // On peut ajouter de la vie à notre avatar
    public void ajouterVie(double life) {
        this.life += life;
    }


    // On peut retirer de la vie à notre avatar
    public void retirerVie(double life) {
        this.life -= life;
    }


    public Defi creerDefi(Avatar joueurDefier, Question question) {
        Defi defi = new Defi(this, joueurDefier);
        this.listeDefi.add(defi);
        joueurDefier.listeDefi.add(defi);
        defi.ajouterQuestion(question);
        return defi;
    }


    public void accepterDefi(Defi defi) {
        if (this == defi.getJoueur2()) {
            defi.setEtat(1);
            System.out.println("Le défi a été accepté par " + pseudo);
        } else {
            System.out.println("Vous ne pouvez pas accepter ce défi car vous n'êtes pas le joueur défié.");
        }
    }


    public void refuserDefi(Defi defi) {
        if (this == defi.getJoueur2()) {
            defi.setEtat(-1);
            System.out.println("Le défi a été refusé par " + pseudo);
        }
        else {
            System.out.println("Vous ne pouvez pas accepter ce défi car vous n'êtes pas le joueur défié.");
        }
    }
    
    // Joueur joue, repond a une question puis choisi de sarreter la en den envoyer une
    public void jouer(Defi defi, String reponse, Question newQuestion) {
        int nQuestion = defi.getListeQuestions().size();
        Question question = defi.getListeQuestions().get(nQuestion-1);
        if (nQuestion % 2 == 0 && this.equals(defi.getJoueur1())) {
            defi.ajouterQuestion(newQuestion);
            if (question.verifReponse(reponse)) {
                System.out.println(defi.affichageReponseJuste());
            }
            else {
                System.out.println(defi.affichageReponseFausse());
            }
        }

        if (nQuestion % 2 == 1 && this.equals(defi.getJoueur2())) {
            defi.ajouterQuestion(newQuestion);
            if (question.verifReponse(reponse)) {
                System.out.println(defi.affichageReponseJuste());
            }
            else {
                System.out.println(defi.affichageReponseFausse());
            }
        }

        else {
            System.out.println(defi.affichageWrongPlayer());
        }
    }
        


}

