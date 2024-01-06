import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Author: Armin Owrak
 * Date: 6/11/2021
 * This is the SoundEffect Object for playing sound effects in the main GUI
 */

public class SoundEffect extends fileNotFoundError {
    //Creating the clip
    Clip clip;

    //Creating the main construction
    public SoundEffect(String fileName){
        try {
            //Importing the file determined by the user
            File file = new File(fileName);

            //If the file doesn't exist
            if (!file.exists()){
                //Displaying error message
                displayError();
            }

            //Creating the main AudioSteam
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            //Adding values to the clip
            clip = AudioSystem.getClip();
            clip.open(sound);
        }
        //If an exception is thrown
        catch (Exception e){
            //Do nothing
        }
    }

    //Creating the SFX playing method
    public void play(){
        //Applying the clip to the frame
        clip.setFramePosition(0);
        //Playing the audio clip
        clip.start();
    }
}
