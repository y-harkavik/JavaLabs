package RegEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static Pattern numberPattern = Pattern.compile("^[1-9][0-9]{0,5}");
    public static Pattern namePattern = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-0\\s]*[a-zA-Z0-9]$");

    public static boolean checkNum(String num) {
        Matcher matcher = numberPattern.matcher(num);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkName(String name) {
        Matcher matcher = namePattern.matcher(name);
        if(matcher.matches()) {
            return true;
        }
        return false;
    }
}
