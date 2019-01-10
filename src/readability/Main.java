package readability;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static double countScore(long character, long words, long sentences) {
        return 4.71*(double) character/words + 0.5*(double) words/sentences - 21.43;
    }

    public static String countSentencesAndWords(String str) {
        String[] strs = str.split("\\s+");
        int words = 0;
        int countOfSentence = 0;
        long charachters = 0;
        for (int k = 0; k < strs.length; k++) {
            words++;
            if (strs[k].contains(",")) {
                charachters--;
            }
            if (strs[k].matches("\\W")) {
                words--;
            }
            if (strs[k].matches(".*[!\\.\\?]") || k == strs.length - 1) {
                if (strs[k].equals(".") || strs[k].equals("!") || strs[k].equals("?")) {
                    words--;
                }
                charachters--;
                countOfSentence++;
            }
            charachters += strs[k].length();
        }
        return countOfSentence + " " + words + " " + charachters;
    }

    public static String findAge(double score) {
        int scoreF = (int) Math.ceil(score);
        switch (scoreF) {
            case 1:
                return "6-7";
            case 2:
                return "7-8";
            case 3:
                return "8-9";
            case 4:
                return "9-10";
            case 5:
                return "10-11";
            case 6:
                return "11-12";
            case 7:
                return "12-13";
            case 8:
                return "13-14";
            case 9:
                return "14-15";
            case 10:
                return "15-16";
            case 11:
                return "16-17";
            default:
                return "17-18";
        }
    }

    public static void main(String[] args) {
        File file = new File(args[0]);
        double score;
        long characters = 0;
        long words = 0;
        long sentences = 0;
        try (Scanner scanner = new Scanner(file)) {
            System.out.println("Opening file " + args[0]);
            System.out.println("The text is:");
            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                System.out.println(string);
                String[] strings = countSentencesAndWords(string).split("\\s+");
                sentences += Long.parseLong(strings[0]);
                words += Long.parseLong(strings[1]);
                characters += Long.parseLong(strings[2]);
                if (scanner.hasNext()) {
                    sentences--;
                }
                NumberFormat formatter = new DecimalFormat("#0.0");
                score = countScore(characters, words, sentences);
                System.out.println("The score is: " + formatter.format(score));
                System.out.println("This text should be understood by "+findAge(score)+" year old.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
