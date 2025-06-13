package Day_3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    // regex is awesome!
    public static int sumValidInstructions(String input) {
        input = "do()" + input;

        Pattern commandPattern = Pattern.compile("(do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\))");
        Matcher matcher = commandPattern.matcher(input);

        boolean mulEnabled = false;
        int total = 0;

        while (matcher.find()) {
            String command = matcher.group(1);

            if (command.equals("do()")) {
                mulEnabled = true;
            } else if (command.equals("don't()")) {
                mulEnabled = false;
            } else if (matcher.group(2) != null && matcher.group(3) != null) {
                if (mulEnabled) {
                    int x = Integer.parseInt(matcher.group(2));
                    int y = Integer.parseInt(matcher.group(3));
                    total += x * y;
                }
            }
        }

        return total;
    }

    public static void main(String[] args) throws IOException {
        // load the data
        String fileContent = Files.readString(Paths.get("src/Day_3/data.txt"));
        System.out.println(sumValidInstructions(fileContent));
    }
}