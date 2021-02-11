public class RepoUtils {
    public static String getBranch(String ref){
        String [] str = ref.split("/");
        return str[2];
    }
}
