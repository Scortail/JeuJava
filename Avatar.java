import java.util.Map;
import java.util.ArrayList;

public class Avatar {
    private String pseudo;
    private int life;
    private Etudiant etudiant;
    private ArrayList<Defi> listeDefi;

    public Avatar(String pseudo, Etudiant etudiant) {
        this.pseudo = pseudo;
        this.etudiant = etudiant;
        startingLife();
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public int getLife() {
        return this.life;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public ArrayList<Defi> getListeDefi() {
        return this.listeDefi;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setLife(int life) {
        this.life = life;
    }

    // On initialise la vie de l'avatar à partir des notes actuels de l'étudiant
    public void startingLife() {
    this.life = 0;
    ArrayList<Note> listeNote;
    for (Map.Entry<Matiere, ArrayList<Note>> entry : getEtudiant().getNotes().entrySet()) {
        listeNote = entry.getValue();
        for (Note note : listeNote) {
            this.life += note.getNote() * note.getCoefficient();
            }
        }
    }

    // Nous permet de défier un adversaire
    public void creerDefi(Avatar avatarDefier, ArrayList<Question> listeQuestions) {
        listeDefi.add(new Defi(this, avatarDefier, listeQuestions));
        avatarDefier.getListeDefi().add(new Defi(this, avatarDefier, listeQuestions));
    }



}
