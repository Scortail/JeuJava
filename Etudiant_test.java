import java.util.ArrayList;

public class Etudiant_test {
    public static void main(String[] args) {
        Etudiant etudiant1 = new Etudiant("Beaurain", "Tom");
        Matiere maths = new Matiere("Maths");
        Matiere physique = new Matiere("Physique");
        etudiant1.ajouterMatiere(maths);
        etudiant1.ajouterMatiere(physique);
        etudiant1.ajouterNote(maths, 15);
        etudiant1.ajouterNote(maths, 17);
        etudiant1.ajouterNote(physique, 12);
        System.out.println(etudiant1.getNotes());
        Avatar avatar1 = new Avatar("Scortail", etudiant1);
        System.out.println(avatar1.getLife());
        ArrayList<String> choix_reponse = new ArrayList<String>();
        choix_reponse.add("1939-1945");
        choix_reponse.add("58");
        Question question1 = new Question("Date seconde guerre mondial",choix_reponse, 5, "1939-1945");
        System.out.println(question1);
        
    }
}