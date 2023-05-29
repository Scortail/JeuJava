import java.util.*;
import java.io.*;

/**
 * La classe Login représente un système de connexion et d'inscription pour les étudiants.
 * Elle permet aux utilisateurs de s'authentifier, de créer un compte, et de gérer les informations de connexion.
 */
public class Login implements Serializable {
    protected String login;
    protected String password;
    private Etudiant etudiant;

    /**
     * Constructeur de la classe Login.
     * Initialise l'étudiant à null.
     */
    public Login() {
        etudiant = null;
    }

    /**
     * Retourne le login de l'utilisateur connecté.
     *
     * @return Le login de l'utilisateur.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Retourne l'objet Etudiant de l'utilisateur connecté.
     *
     * @return L'objet Etudiant de l'utilisateur.
     */
    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    // Classes d'exception personnalisées

    /**
     * Exception levée lorsque le mot de passe saisi est incorrect.
     */
    class WrongPwdException extends Exception{

        public String toString(){
            return "Erreur: Mauvais mot de passe";
        }
    }

    /**
     * Exception levée lorsque la longueur des données saisies est incorrecte.
     */
    class WrongInputLengthException extends Exception{

        public String toString(){
            return "Erreur: Trop de caractères en entrée";
        }
    }

    /**
     * Exception levée lorsque le login saisi est inexistant.
     */
    class WrongLoginException extends Exception{

        public String toString(){
            return "Erreur: Pseudo inexistant";
        }
    }

    /**
     * Exception levée lorsque le pseudo saisi lors de l'inscription est déjà utilisé.
     */
    class PseudoIndisponibleException extends Exception{

        public String toString(){
            return "Erreur: Pseudo deja existant";
        }
    }

    /**
     * Exception levée lorsque les informations saisies lors de l'inscription sont invalides.
     */
    class WrongInfoException extends Exception{

        public String toString(){
            return "Erreur: Nom ou prenom invalide";
        }
    }

    /**
     * Exception levée lorsque l'utilisateur possède déjà un avatar lors de l'inscription.
     */
    class AvatarExistantException extends Exception {
        public String toString() {
            return "Vous posséder déjà un avatar.";
        }
    }

    /**
     * Retourne l'étudiant connecté correspondant au pseudo de connexion.
     *
     * @return L'objet Etudiant connecté ou null si aucun étudiant correspondant n'est trouvé.
     */
    public Etudiant trouveEtudiantConnecter() {
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                Avatar avatar = etudiant.getAvatar();
                if (avatar != null && avatar.getPseudo().equals(login)) {
                    return etudiant;
                }
            }
        }
        return null;
    }

    /**
 * Permet à l'étudiant de s'identifier lors de l'inscription en demandant le nom et le prénom.
 * Vérifie si un avatar existe déjà pour cet étudiant.
 *
 * @param sc le scanner utilisé pour la saisie utilisateur.
 * @return l'étudiant correspondant aux informations d'identification.
 * @throws WrongInfoException     si les informations fournies ne correspondent à aucun étudiant enregistré.
 * @throws AvatarExistantException si un avatar est déjà associé à cet étudiant.
 */
    public Etudiant getUserInfo(Scanner sc) throws WrongInfoException, AvatarExistantException {
        System.out.println("Quel est votre nom");
        String nom = sc.nextLine();

        System.out.println("Quel est votre prenom");
        String prenom = sc.nextLine();
        ArrayList<Object> utilisateurs = Etudiant.chargerUtilisateurs();
        for (Object utilisateur : utilisateurs) {
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                if (etudiant.getNom().equals(nom)) {
                    if (etudiant.getPrenom().equals(prenom)) {
                        if (!(etudiant.getAvatar() == null)) {
                            throw new AvatarExistantException();
                        }
                        return etudiant;
                    }            
                }
            }
        }
        throw new WrongInfoException();
    }

    /**
     * Demande à l'utilisateur de saisir son pseudo et son mot de passe.
     * Vérifie la longueur des champs de saisie.
     * Vérifie si le pseudo existe dans la liste des logins.
     * Vérifie si le mot de passe correspond au pseudo.
     *
     * @param sc le scanner utilisé pour la saisie utilisateur.
     * @throws WrongLoginException       si le pseudo n'existe pas dans la liste des logins.
     * @throws WrongPwdException         si le mot de passe ne correspond pas au pseudo.
     * @throws WrongInputLengthException si la longueur des champs de saisie dépasse 10 caractères.
     */
    protected void getUserPwd(Scanner sc) throws WrongLoginException, WrongPwdException, WrongInputLengthException {

        System.out.println("Pseudo : ");
        login = sc.nextLine();

        System.out.println("Mot de passe:");
        password = sc.nextLine();

        Map<String, String> logins = chargerLogins();

        if (login.length() > 10 || password.length() > 10)
            throw new WrongInputLengthException();

        if (!logins.containsKey(login))
            throw new WrongLoginException();

        if (!logins.get(login).equals(password))
            throw new WrongPwdException();
    }

    /**
     * Effectue le processus de connexion en demandant à l'utilisateur de saisir son pseudo et son mot de passe.
     * Vérifie la validité des informations d'identification.
     * Si les informations sont valides, recherche l'étudiant connecté et met à jour la variable d'instance "etudiant".
     *
     * @param sc le scanner utilisé pour la saisie utilisateur.
     */
    public void connexion(Scanner sc){
        boolean valide = false;

        while( !valide ){
            try {
                getUserPwd(sc);
                System.out.println("Login/password valides");
                etudiant = trouveEtudiantConnecter();
                valide = true;
            }
            catch(WrongLoginException wle){
                wle.printStackTrace();
            }

            catch(WrongPwdException wpe) {
                wpe.printStackTrace();
            }

            catch(WrongInputLengthException wile){
                wile.printStackTrace();
                System.exit(0); //risque de hacking, on sort du programme
            }
            catch(Exception e){
                e.printStackTrace();
                System.exit(0); //pb inconnu; on sort de la méthode pour limiter les risques
            }
        }
    }

    /**
     * Demande à l'utilisateur de saisir son pseudo et son mot de passe lors de l'inscription.
     * Vérifie la longueur des champs de saisie.
     * Vérifie si le pseudo est déjà pris.
     *
     * @param sc le scanner utilisé pour la saisie utilisateur.
     * @throws WrongInputLengthException   si la longueur des champs de saisie dépasse 10 caractères.
     * @throws PseudoIndisponibleException si le pseudo est déjà pris.
     */
    public void getUserPwdIncrip(Scanner sc) throws WrongInputLengthException, PseudoIndisponibleException {
        System.out.println("Pseudo : ");
        login = sc.nextLine();

        System.out.println("Mot de passe:");
        password = sc.nextLine();

        Map<String, String> logins = chargerLogins();
        System.out.println(login);

        if (logins.containsKey(login))
            throw new PseudoIndisponibleException();

        if (login.length() > 10 || password.length() > 10)
            throw new WrongInputLengthException();
    }

    /**
     * Effectue le processus de création de compte en demandant à l'utilisateur de saisir un pseudo et un mot de passe.
     * Vérifie la validité des informations d'inscription.
     * Si les informations sont valides, crée un compte pour l'étudiant et sauvegarde les informations de connexion.
     *
     * @param sc le scanner utilisé pour la saisie utilisateur.
     */
    public void creerCompte(Scanner sc) {
        boolean valide = false;
        while( !valide ){
            try {
                getUserPwdIncrip(sc);
                System.out.println("Compte créé!");
                        valide = true;
            }
            catch(PseudoIndisponibleException pie){
                pie.printStackTrace();
            }

            catch(WrongInputLengthException wile){
                wile.printStackTrace();
                System.exit(0); //risque de hacking, on sort du programme
            }
            catch(Exception e){
                e.printStackTrace();
                System.exit(0); //pb inconnu; on sort de la méthode pour limiter les risques
            }
        }
    }


    /**
     * Effectue le processus d'inscription en demandant à l'utilisateur ses informations personnelles,
     * de créer un compte et de choisir un avatar.
     * Vérifie la validité des informations d'identification et d'inscription.
     * Si les informations sont valides, crée un compte pour l'étudiant, crée un avatar et sauvegarde les informations de connexion.
     *
     * @param sc le scanner utilisé pour la saisie utilisateur.
     */
    public void inscription(Scanner sc) {
        Boolean inscription = false;
        while (!inscription) {
            try {
                etudiant = getUserInfo(sc);
                System.out.println("Bienvenue");
                creerCompte(sc);
                etudiant.creerAvatar(login);
                Map<String, String> logins = chargerLogins();  // Charger les logins existants
                logins.put(login, password);  // Ajouter le nouveau login et mot de passe
                sauvegarderLogins(logins);  // Sauvegarder les logins mis à jour
                inscription = true;
            } catch (WrongInfoException wie) {
                wie.printStackTrace();
            } catch (AvatarExistantException aee) {
                aee.printStackTrace();
                break;
            }
        }
    }
    

    /**
     * Sauvegarde les logins dans un fichier.
     *
     * @param logins la liste des logins à sauvegarder.
     */
    protected static void sauvegarderLogins(Map<String, String> logins) {
        Map<String, String> serializableMap = new HashMap<>(logins);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Login.ser"))) {
            oos.writeObject(serializableMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Charge les logins à partir d'un fichier.
     *
     * @return la liste des logins chargés.
     */
    protected static Map<String, String> chargerLogins() {
        Map<String, String> map = new HashMap<>();
        File file = new File("Login.ser");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                Object obj = ois.readObject();
                if (obj instanceof Map<?, ?>) {  // Utilisez Map au lieu de List dans la vérification
                    map = (Map<String, String>) obj;  // Cast directement en Map<String, String>
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Supprime une clé de la map des logins.
     * 
     * @param pseudo le pseudo qui doit être supprimée
     */
    public void supprimerLogin(String pseudo) {
        Map<String, String> logins = chargerLogins();
        

        for (Map.Entry<String, String> entry : logins.entrySet()) {
            // Suppression de la clé si elle a été trouvée
            if (entry.getKey().equals(pseudo)) {
                logins.remove(entry.getKey());
                break;
            }
        } 
        sauvegarderLogins(logins);
    }
}
