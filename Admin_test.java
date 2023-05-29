import java.util.*;

public class Admin_test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("Admin", 10000);
        Map<String, String> logins = Login.chargerLogins();  // Charger les logins existants
        logins.put("Admin", "Admin");  // Ajouter le nouveau login et mot de passe
        Login.sauvegarderLogins(logins);  // Sauvegarder les logins mis à jour

        // admin.modifierAvatar("Scortail");

        // Ajout de quelques questions à la liste des questions proposées

        System.out.println(admin.getListeQuestionProposer());
        
    }
    
}
