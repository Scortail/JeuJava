import java.util.*;
import java.io.*;


public class Avatar implements Serializable{
    private String pseudo;
    private double life;
    private ArrayList<Defi> listeDefi = new ArrayList<Defi>();
    private ArrayList<Question> listeQuestion = new ArrayList<Question>();


    public Avatar(String pseudo, double life) {
        this.pseudo = pseudo;
        this.life = life;

        // On ajoute des questions aléatoire lors de sa création
        ArrayList<Question> questionsDispo = Question.chargerQuestions();
        Random random = new Random();
        int totalQuestions = questionsDispo.size();
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(totalQuestions);
            Question question = questionsDispo.get(randomIndex);
            listeQuestion.add(question);
            questionsDispo.remove(randomIndex);
            totalQuestions--;
        }
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

    public Question getQuestion(int numero) {
        if (numero <= listeQuestion.size() && numero > 0){
            return listeQuestion.get(numero-1);
        }
        return null;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public void setListeDefi(ArrayList<Defi> listeDefi) {
        this.listeDefi = listeDefi;
    }

    public void setListeQuestion(ArrayList<Question> listeQuestion) {
        this.listeQuestion = listeQuestion;
    }
    
    // On peut ajouter de la vie à notre avatar
    public void ajouterVie(double life) {
        this.life += life;
        sauvegarderAvatar();
    }
    
    // On peut retirer de la vie à notre avatar
    public void retirerVie(double life) {
        this.life -= life;
        if(this.life < 0) {
            this.life = 0;
            System.out.println("Vous n'avez plus de vies.");
        }
        sauvegarderAvatar();
    }

    // Ajoute des questions à l'avatar
    public void ajouterQuestion(Question question) {
        listeQuestion.add(question);
        sauvegarderAvatar();
    }

    // Permet de defier un joueur
    public void creerDefi(Avatar joueurDefier, Question ... questions) {
        Defi defi = new Defi(this, joueurDefier);
        this.listeDefi.add(defi);
        joueurDefier.listeDefi.add(defi);
        for (Question question : questions) {
            defi.ajouterQuestionJoueur1(question);
        }
        sauvegarderAvatar();
    }

    public String afficherQuestionsDispo() {
        String questions = "";
        int numero = 0;
        for (Question question : listeQuestion) {
            numero += 1;
            questions += numero + " : " + question;
        }
        return questions;
    }

    // Retourne l'avatar possédant le pseudo rechercher
    public Avatar chercherAvatar(String pseudo) {
        ArrayList<Etudiant> etudiants = Etudiant.chargerEtudiant();
        for (Etudiant etudiant : etudiants) {
            Avatar avatar = etudiant.getAvatar();
            if (!(avatar == null)) {
                if (avatar.getPseudo().equals(pseudo)) {
                    return avatar;
                }
            }
        }
        return null;
    }

    public static String afficherAvatars() {
        String affichage = "";
        ArrayList<Etudiant> listeEtudiant = Etudiant.chargerEtudiant();
        for (Etudiant etudiant : listeEtudiant) {
            Avatar avatar = etudiant.getAvatar();
            if (!(avatar == null)) {
                affichage += avatar.getPseudo();
            }
        }
        return affichage;
    }

    public void accepterDefi(Defi defi, Question ... questions) {
        if (this == defi.getJoueur2()) {
            defi.setEtat(1);
            System.out.println("Le défi a été accepté par " + pseudo);
        } else {
            System.out.println("Vous ne pouvez pas accepter ce défi car vous n'êtes pas le joueur défié.");
        }
        sauvegarderAvatar();
    }


    public void refuserDefi(Defi defi) {
        if (this == defi.getJoueur2()) {
            defi.setEtat(-1);
            System.out.println("Le défi a été refusé par " + pseudo);
        }
        else {
            System.out.println("Vous ne pouvez pas accepter ce défi car vous n'êtes pas le joueur défié.");
        }
        sauvegarderAvatar();
    }
    
    // 2 jours pour accepter puis 20 minutes pour jouer
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
                sauvegarderAvatar();
            }
        }
        else {
            System.out.println("Vous n'etes pas concerné par ce defi !");
        }
    }

    // Nous permet de sauvegarder un avatar
    public void sauvegarderAvatar() {
        ArrayList<Etudiant> listeEtudiant = Etudiant.chargerEtudiant();
        for (Etudiant etudiant : listeEtudiant) {
            Avatar avatar = etudiant.getAvatar();
            if (!(avatar == null)) {
                if (avatar.getPseudo().equals(pseudo)) {
                    listeEtudiant.remove(etudiant);
                    etudiant.setAvatar(this);
                    listeEtudiant.add(etudiant);
                    Etudiant.sauvegarderEtudiants(listeEtudiant);
                }
            }
        }
    }

    public boolean equals(Avatar avatar) {
        if (this.pseudo.equals(avatar.getPseudo())) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Pseudo : " + pseudo + " Nb vie : " + life;
    }
}

