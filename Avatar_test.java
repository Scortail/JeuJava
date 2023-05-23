/**
 * Avatar_test
 */
public class Avatar_test {

    public static void main(String[] args) {
        Avatar avatar = new Avatar("test", 50);
        System.out.println(Avatar.afficherAvatars());
        avatar.getQuestion(5);
    }
}