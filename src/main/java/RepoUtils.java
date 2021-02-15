public class RepoUtils {
    /**
     * Function that returns the branch name from a string in form of "refs/heads/branchname"
     * It split the string around "/" and returns the third part of the string where the
     * breanchname is stored.
     * @param ref String that contain the branchname
     * @return branchname
     */
    public static String getBranch(String ref){
        String [] str = ref.split("/");
        return str[2];
    }
}
