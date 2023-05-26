import java.util.*;

public class Admin extends Avatar {

    public Admin(String pseudo, double life) {
        super(pseudo, life);
    }

    class PseudoDejaUtilise extends Exception{

        public String toString(){
            return "Erreur: Ce pseudo est deja utilise.";
        }
    }

    class PseudoTropLong extends Exception{

        public String toString(){
            return "Erreur: La longueur maximum du pseudo est de 20 caractères.";
        }
    }

    public String getNewPseudo() throws PseudoDejaUtilise, PseudoTropLong{
        Scanner sc = new Scanner(System.in) ;

        System.out.println("Nouveau pseudo : ");
        String newPseudo = sc.nextLine();
        sc.close();

        if (chercherAvatar(newPseudo) != null)
            throw new PseudoDejaUtilise();

        if (newPseudo.length() > 20)
            throw new PseudoTropLong();

        return newPseudo;
    }

    // On peut modifier le pseudo d'un avatar
    public void modifierAvatar(String pseudo) {
        ArrayList<Etudiant> etudiants = Etudiant.chargerEtudiant();
        for (Etudiant etudiant : etudiants) {
            Avatar avatar = etudiant.getAvatar();
            if (!(avatar == null)) {
                if (avatar.getPseudo().equals(pseudo)) {
                    etudiants.remove(etudiant);
                    String newPseudo = null;
                    boolean valide = false;
                    while( !valide ) {
                        try {
                            newPseudo = getNewPseudo();
                            valide = true;
                        }
                        catch(PseudoDejaUtilise pdu){
                            pdu.printStackTrace();
                        }
                        catch(PseudoTropLong ptl) {
                            ptl.printStackTrace();
                        }
                    }
                    etudiant.getAvatar().setPseudo(newPseudo);
                    System.out.println("Pseudo modifié.");
                    etudiants.add(etudiant);
                    Etudiant.sauvegarderEtudiants(etudiants);
                    return;
                }
            }
        }
    }

    // On peut supprimer un avatar
    public void supprimerAvatar(String pseudo) {
        ArrayList<Etudiant> etudiants = Etudiant.chargerEtudiant();
        for (Etudiant etudiant : etudiants) {
            Avatar avatar = etudiant.getAvatar();
            if (!(avatar == null)) {
                if (avatar.getPseudo().equals(pseudo)) {
                    etudiant.setAvatar(null);
                    Etudiant.sauvegarderEtudiants(etudiants);
                    System.out.println("Avatar supprimé.");
                    return;
                }
            }
        }
        System.out.println("Avatar non trouvé !");
    }
}
