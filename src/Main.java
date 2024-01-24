import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger count3 = new AtomicInteger();
    private static final AtomicInteger count4 = new AtomicInteger();
    private static final AtomicInteger count5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        String[] texts = generateTexts(100_000);

        Thread thread3 = new Thread(() -> countBeautiful(texts, 3, count3));
        Thread thread4 = new Thread(() -> countBeautiful(texts, 4, count4));
        Thread thread5 = new Thread(() -> countBeautiful(texts, 5, count5));
        thread3.start();
        thread4.start();
        thread5.start();
        thread3.join();
        thread4.join();
        thread5.join();
        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт");
    }

    private static String[] generateTexts(int count) {
        Random random = new Random();
        String[] texts = new String[count];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        return texts;
    }

    private static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void countBeautiful(String[] texts, int length, AtomicInteger count) {
        for (String text : texts) {
            if (text.length() == length && beautiful(text)) {
                count.incrementAndGet();
            }
        }
    }

    private static boolean beautiful(String text) {
        return palindrome(text) || decreasing(text) || increasing(text);
    }

    private static boolean palindrome(String text) {
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean decreasing(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(0)) {
                return false;
            }
        }
        return true;
    }

    private static boolean increasing(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
}