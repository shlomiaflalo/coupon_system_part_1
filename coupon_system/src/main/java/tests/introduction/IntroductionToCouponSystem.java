package tests.introduction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * The IntroductionToCouponSystem class provides a runnable task that concurrently plays an introduction audio
 * while simulating the typing of the introductory text to the console.
 *
 * The class is designed to be executed in a separate thread. When the run method is invoked,
 * it starts a TypingThread to simulate typing the introductory text and simultaneously
 * plays an audio file (IntroToProject.wav) located in the specified directory.
 *
 * This class is typically used in the context of providing an introductory explanation of the
 * Coupon System project, where it presents the project introduction both visually (through simulated typing)
 * and audibly (through the playback of an audio clip).
 *
 * It handles the necessary exceptions, including LineUnavailableException, IOException,
 * UnsupportedAudioFileException, and InterruptedException, which may be encountered during the
 * audio playback and thread operations. If any of these exceptions are thrown, an appropriate
 * message is printed indicating that the file was not found for playing the introduction.
 */
public class IntroductionToCouponSystem implements Runnable {

    @Override
    public void run() {
        try {
                TypingThread TypingThread = new TypingThread(); //Here we're using a tread that have one task: typing the
                //text of the voice project introduction concurrently with the clip.start();
                Thread thread = new Thread(TypingThread);
                thread.start();

                File file = new File("src/main/java/tests/introduction/IntroToProject.wav");
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.setFramePosition(0);
                clip.start();
                //Clip using microseconds and thread using milliseconds - therefor we need a formula to divide the time value by 1000
                //Our file length is 32 seconds, For the thread the length is: 32000 milliseconds and for clip the length is: 32000000
                Thread.sleep(clip.getMicrosecondLength()/1000);

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException
                 | InterruptedException e) {
            System.out.println("File not found for playing the introduction");
        }

    }
}
