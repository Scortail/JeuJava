public class Admin_test {
    public static void main(String[] args) {
        Admin admin = new Admin("Admin", 10000);
        Etudiant.verifierEcritureEtudiant();
        admin.modifierAvatar("Scortail");
        Etudiant.verifierEcritureEtudiant();
    }
    
}
