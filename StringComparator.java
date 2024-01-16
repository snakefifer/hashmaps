import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    public int compare(String s1, String s2) {
        String s1Split = s1.split("@")[0];
        String s2Split = s2.split("@")[0];
        return s1Split.compareTo(s2Split);
    }

}