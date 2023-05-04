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


    public Defi creerDefi(Avatar joueurDefier, Question ... questions) {
        Defi defi = new Defi(this, joueurDefier);
        this.listeDefi.add(defi);
        joueurDefier.listeDefi.add(defi);
        for (Question question : questions) {
            defi.ajouterQuestionJoueur1(question);
        }
        return defi;
    }


    public void accepterDefi(Defi defi, Question ... questions) {
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
    public void jouer(Defi defi) {
        ArrayList<Question> listeQuestions;
        if (listeDefi.contains(defi)) {
            if (this.equals(defi.getJoueur1())) {
                listeQuestions = defi.getListeQuestionsJoueur2();
            }
    
            else {
                listeQuestions = defi.getListeQuestionsJoueur1();
            }
            int i = 1;
            for (Question question : listeQuestions) {
                System.out.println("Question " + i + " : ");
                defi.repondreQuestion(question, this);
            }
        }
        

        else {
            System.out.println("Vous n'etes pas concerné par ce defi!");
        }
    }
}

