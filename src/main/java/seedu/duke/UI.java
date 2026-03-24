package seedu.duke;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {

    public Scanner in;
    public String lineSeparator = "________________________________________________________________";

    public UI() {
        this.in = new Scanner(System.in);
    }

    public void showWelcome() {
        String logo = " ______                                 _               _          _\n"
                + "|  ____|                               (_)             | |        | |\n"
                + "| |__  __  __  _ __   ___  _ __   ___  _ __   __ ___  | |     ___| |__\n"
                + "|  __| \\ \\/ / | '_ \\ / _ \\| '_ \\ / __|| \\ \\ / // _ \\ | |    / _ \\ '_ \\\n"
                + "| |____       | |_) |  __/| | | |\\__ \\| |\\ V /|  __/ | |___|  __/ | | |\n"
                + "|______/_/\\_\\ | .__/ \\___||_| |_||___/|_| \\_/  \\___| |______\\___|_| |_|\n"
                + "              | |\n"
                + "              |_|\n";

        System.out.println("Hello from\n" + logo);
        System.out.println(lineSeparator);
        System.out.println("Hello! I am an expenses tracker aimed to help you better organise your spending habits! " +
                "Let's get started!\n" + lineSeparator);
    }

    public void showLine() {
        System.out.println(lineSeparator);
    }

    public String readCommand() {
        return in.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showMessage(String message) {
        System.out.println(lineSeparator);
        System.out.println("ExpensiveLeh says -> " + message);
        System.out.println(lineSeparator);
    }

    private String generateBar(double categoryAmount, double highestAmount) {
        int maxBarLength = 20;

        if (highestAmount == 0) {
            return "";
        }

        int numberOfBlocks = (int) ((categoryAmount / highestAmount) * maxBarLength);

        String blockCharacter = "█";

        return blockCharacter.repeat(numberOfBlocks);
    }

    public void showRanking(List<Map.Entry<String, Double>> rankedList) {
        System.out.println(lineSeparator);
        System.out.println("ExpensiveLeh says -> Here is your spending ranked, by category: ");
        System.out.println();

        double highestAmount = rankedList.get(0).getValue();

        for (int i = 0; i < rankedList.size(); i++) {
            Map.Entry<String, Double> entry = rankedList.get(i);
            String category = entry.getKey();
            double amount = entry.getValue();

            String visualBar = generateBar(amount, highestAmount);

            System.out.printf("  %d. %-15s [$%8.2f] %s\n", (i + 1), category, amount, visualBar);
        }
        System.out.println(lineSeparator);
    }

}
