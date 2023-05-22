
import java.util.Scanner;

import static java.util.Arrays.*;
import static java.util.function.Predicate.*;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        while (true) {
            String line = in.nextLine();
            if (line.equals("q")) break;

            Command com;
            try {
                 com = Command.of(line);
            } catch (NumberFormatException nfe) {
                System.out.println("Неверный формат. Введите 2 числа:");
                continue;
            }
            String result = "";
            switch(com.sign) {
                case '+' -> result = String.valueOf(calc.add(com.numbers[0], com.numbers[1]));
                case '-' -> result = String.valueOf(calc.sub(com.numbers[0], com.numbers[1]));
                case '*' -> result = String.valueOf(calc.multiply(com.numbers[0], com.numbers[1]));
                case ':' -> result = String.valueOf(calc.floatDivision(com.numbers[0], com.numbers[1]));
                case '/' -> result = String.valueOf(calc.intDivision(com.numbers[0], com.numbers[1]));
            }
            System.out.println(result);
        }
    }

    private record Command(char sign, int[] numbers) {

        public static Command of(String lineToParse) throws NumberFormatException {

            String[] numbersToParse = lineToParse.split("[+*/:-]");
            String[] signToParse = lineToParse.split("\\d+");

            char sign = stream(signToParse)
                    .filter(not(String::isEmpty))
                    .map(String::strip)
                    .reduce(' ',(ch, s) -> s.charAt(0), (c1, c2) -> c1);

            int[] numbers = stream(numbersToParse)
                    .map(String::strip)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            return new Command(sign, numbers);
        }
    }
}
