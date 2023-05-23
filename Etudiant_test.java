import java.util.ArrayList;

public class Etudiant_test {
    public static void main(String[] args) {
        Etudiant etudiant1 = new Etudiant("Beaurain", "Tom");
        Etudiant etudiant2 = new Etudiant("Devaux", "Clement");
        Matiere maths = new Matiere("Maths");
        Matiere physique = new Matiere("Physique");
        etudiant1.ajouterMatiere(maths);
        etudiant1.ajouterMatiere(physique);
        etudiant2.ajouterMatiere(maths);
        etudiant2.ajouterMatiere(physique);
        etudiant1.ajouterNote(maths, 15);
        etudiant2.ajouterNote(maths, 17);
        etudiant2.ajouterNote(physique, 12);
        // System.out.println(etudiant2.getNotes());

        // System.out.println(etudiant1.getNotes());
        // etudiant1.creerAvatar("Scortail");
        // Etudiant.verifierEcritureEtudiant();
        // System.out.println("TEST");
        // etudiant1.getAvatar().retirerVie(10);
        // Etudiant.verifierEcritureEtudiant();
        // System.out.println(etudiant1.getAvatar());
        // etudiant2.creerAvatar("Zedraal");
        // System.out.println(etudiant2.getAvatar().getLife());

        // System.out.println(etudiant1.getAvatar().getLife());
        // ArrayList<String> choix_reponse = new ArrayList<String>();
        // choix_reponse.add("1939-1945");
        // choix_reponse.add("58");

        // ArrayList<String> choix_reponse2 = new ArrayList<String>();
        // choix_reponse2.add("Le chat");
        // choix_reponse2.add("Le chien");
        // Question question2 = new Question("le meilleur ami de l'homme ?", choix_reponse2, 1, "le chien");

        // ArrayList<String> choix_reponse3 = new ArrayList<String>();
        // choix_reponse3.add("Blanc");
        // choix_reponse3.add("Noir");
        // Question question3 = new Question("quel est le couleur de l'interface de VS code ?", choix_reponse3, 2, "Noir");

        // ArrayList<String> choix_reponse4 = new ArrayList<String>();
        // choix_reponse4.add("Radiant");
        // choix_reponse4.add("Acendent");
        // Question question4 = new Question("Le rang max de valorant ?", choix_reponse4, 2, "Radiant");

        // etudiant1.getAvatar().creerDefi(etudiant2.getAvatar(), question4);
        // Defi defi1 = etudiant2.getAvatar().getListeDefi().get(0);
        // etudiant2.getAvatar().accepterDefi(defi1);
        // System.out.println(etudiant1.getAvatar().getLife());
        // System.out.println(etudiant2.getAvatar().getLife());
        // etudiant2.getAvatar().jouer(defi1);
        // // System.out.println(etudiant1.getAvatar().getLife());
        // // System.out.println(etudiant2.getAvatar().getLife());
        // etudiant1.verifierEcritureAvatar();
        // etudiant1.afficherBulletin();
    }
}