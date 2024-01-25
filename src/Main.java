import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger count3 = new AtomicInteger(0);
    private static final AtomicInteger count4 = new AtomicInteger(0);
    private static final AtomicInteger count5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread threadPalindrome = new Thread(() -> {
            for (String text : texts) {
                if (palindrome(text)) {
                    if (text.length() == 3) {
                        count3.incrementAndGet();
                    } else if (text.length() == 4) {
                        count4.incrementAndGet();
                    } else if (text.length() == 5) {
                        count5.incrementAndGet();
                    }
                }
            }
        });

        Thread threadDecreasing = new Thread(() -> {
            for (String text : texts) {
                if (decreasing(text)) {
                    if (text.length() == 3) {
                        count3.incrementAndGet();
                    } else if (text.length() == 4) {
                        count4.incrementAndGet();
                    } else if (text.length() == 5) {
                        count5.incrementAndGet();
                    }
                }
            }
        });

        Thread threadIncreasing = new Thread(() -> {
            for (String text : texts) {
                if (increasing(text)) {
                    if (text.length() == 3) {
                        count3.incrementAndGet();
                    } else if (text.length() == 4) {
                        count4.incrementAndGet();
                    } else if (text.length() == 5) {
                        count5.incrementAndGet();
                    }
                }
            }
        });

        threadPalindrome.start();
        threadDecreasing.start();
        threadIncreasing.start();
        threadPalindrome.join();
        threadDecreasing.join();
        threadIncreasing.join();
        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт");
    }

    private static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static boolean palindrome(String text) {
        int start = 0;
        int end = text.length() - 1;
        while (start < end) {
            if (text.charAt(start) != text.charAt(end)) {
                return false;
            }
            start++;
            end--;
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
