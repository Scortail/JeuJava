import java.util.ArrayList;
import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;


public class Avatar implements Serializable{
    private String pseudo;
    private double life;
    private ArrayList<Defi> listeDefi = new ArrayList<Defi>();
    private ArrayList<Question> listeQuestion = new ArrayList<Question>();


    public Avatar(String pseudo, double life) {
        this.pseudo = pseudo;
        this.life = life;
        ArrayList<Avatar> avatars = chargerAvatars(); // charger les avatars existants
        avatars.add(this); // ajouter le nouvel avatar à la liste
        sauvegarderAvatars(avatars); // sauvegarder la liste complète dans le fichier
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

    public void setListeDefi(ArrayList<Defi> listeDefi) {
        this.listeDefi = listeDefi;
    }

    public void setListeQuestion(ArrayList<Question> listeQuestion) {
        this.listeQuestion = listeQuestion;
    }
    
    // On peut ajouter de la vie à notre avatar
    public void ajouterVie(double life) {
        this.life += life;
    }


    // On peut retirer de la vie à notre avatar
    public void retirerVie(double life) {
        this.life -= life;
    }

    // Ajoute des questions à l'avatar
    public void ajouterQuestion(Question question) {
        listeQuestion.add(question);
    }

    // Permet de defier un joueur
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
            }
        }
        

        else {
            System.out.println("Vous n'etes pas concerné par ce defi !");
        }
    }

    // Écrit tous les avatars dans un nouveau fichier
    public void sauvegarderAvatars(ArrayList<Avatar> avatars) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("avatars.ser"))) {
            for (Avatar avatar : avatars) {
                oos.writeObject(avatar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Charge tous les avatars depuis le fichier
    public ArrayList<Avatar> chargerAvatars() {
        ArrayList<Avatar> avatars = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("avatars.ser"))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof Avatar) {
                        avatars.add((Avatar) obj);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avatars;
    }

    public String toString() {
        return "Pseudo : " + pseudo + " Nb vie : " + life;
    }
}

