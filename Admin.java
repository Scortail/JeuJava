import java.util.*;
// admin extend avatar


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

    public String getNewPseudo(ArrayList<Avatar> avatars) throws PseudoDejaUtilise, PseudoTropLong{
        Scanner sc = new Scanner(System.in) ;

        System.out.println("Nouveau pseudo : ");
        String newPseudo = sc.nextLine();

        if (chercherAvatar(avatars ,newPseudo) != null)
            throw new PseudoDejaUtilise();

        if (newPseudo.length() > 20)
            throw new PseudoTropLong();

        return newPseudo;
    }

    // Retourne l'avatar possédant le pseudo rechercher
    public Avatar chercherAvatar(ArrayList<Avatar> avatars, String pseudo) {
        for (Avatar avatar : avatars) {
            if (pseudo.equals(avatar.getPseudo())) {
                return avatar;
            }
        }
        return null;
    }

    // On peut modifier le pseudo d'un avatar
    public void modifierAvatar(String pseudo) {
        ArrayList<Avatar> avatars = chargerAvatars();
        Avatar avatar = chercherAvatar(avatars, pseudo);
        if (avatar == null) {
            System.out.println("Avatar non trouvé !");
            return;
        }
        String newPseudo = null;
        boolean valide = false;
        while( !valide ) {
            try {
                newPseudo = getNewPseudo(avatars);
                valide = true;
            }
            catch(PseudoDejaUtilise pdu){
                pdu.printStackTrace();
            }
            catch(PseudoTropLong ptl) {
                ptl.printStackTrace();
            }
        }
        avatar.setPseudo(newPseudo);
        sauvegarderAvatars(avatars);
        System.out.println("Pseudo modifié.");
    }

    // On peut supprimer un avatar
    public void supprimerAvatar(String pseudo) {
        ArrayList<Avatar> avatars = chargerAvatars();
        Avatar avatar = chercherAvatar(avatars, pseudo);
        if (avatar == null) {
            System.out.println("Avatar non trouvé !");
            return;
        }
        avatars.remove(avatar);
        sauvegarderAvatars(avatars);
        System.out.println("Avatar supprimé.");
    }
}
