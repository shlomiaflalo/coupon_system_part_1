package tests.introduction;

/**
 * The TypingThread class implements Runnable and is responsible for simulating typing text
 * to the console. Each character in the text is printed to the console with a delay,
 * imitating natural typing speed.
 */
public class TypingThread implements Runnable {

    private String text(){
        return "I would like to present the Coupon System Project." +
                "We will begin by demonstrating the Admin test, first without exceptions and then with exceptions to ensure they are handled correctly." +
                "The objective is to test methods with valid parameters to verify proper functionality, as well as with invalid parameters to ensure the appropriate exceptions are triggered when needed." +
                "Next, we will showcase the Customer test, followed by the final demonstration of the Company test." +
                "Thank you, Idan";
    }


    @Override
    public void run() {
            String [] textSentences = text().split("\\.");
            int counter=0;
            for (String textSentence : textSentences) {
                while (counter < textSentence.length()) {
                    System.out.print(textSentence.charAt(counter));
                    counter++;
                    try {
                        Thread.sleep(60);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println();
                counter = 0;
            }


    }
}
