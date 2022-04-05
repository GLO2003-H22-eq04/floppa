package ulaval.glo2003;

import java.util.regex.Pattern;

public class IntegrationUtils {

    public static boolean isUrl(String sequence) {
        try {
            var pattern = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
            var matcher = pattern.matcher(sequence);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
