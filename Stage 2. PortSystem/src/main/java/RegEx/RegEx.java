package RegEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that defines expressions to check.
 *
 * @author Yauheni
 * @version 1.0
 */
public class RegEx {
    public static Pattern numberPattern = Pattern.compile("^[1-9][0-9]{0,5}");
    public static Pattern namePattern = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9\\s]*");
    public static Pattern numberOfPiersPattern = Pattern.compile("[1-6]");

    public static boolean checkNum(String num) {
        Matcher matcher = numberPattern.matcher(num);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkName(String name) {
        Matcher matcher = namePattern.matcher(name);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean checkNumOfPiers(String num) {
        Matcher matcher = numberOfPiersPattern.matcher(num);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
