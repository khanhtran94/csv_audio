import java.io.*;
import java.net.URL;
/**
 * Splits WAV-files in multiple parts. 
 * This class splits a big WAV-file in multiple WAV-file, each with a fixed length (SPLIT_FILE_LENGTH_MS).
 * It takes it input file from an embedded resource, and writes a series of out*.wav files.
 * 
 * @author Jeroen De Swaef
 */
public class WaveSplitter {

    public static final String INPUT_FILE_LOCATION = "resources/dai_lo_dong_tay.wav";
    public static final int SPLIT_FILE_LENGTH_MS = 2500;

    public static void main(String[] args) {
        try {
            // Get the wave file from the embedded resources
            URL defaultImage = WaveSplitter.class.getResource(INPUT_FILE_LOCATION);
            File soundFile = new File(defaultImage.toURI());
            WavFile inputWavFile = WavFile.openWavFile(soundFile);

            // Get the number of audio channels in the wav file
            int numChannels = inputWavFile.getNumChannels();
            // set the maximum number of frames for a target file,
            // based on the number of milliseconds assigned for each file
            int maxFramesPerFile = (int) inputWavFile.getSampleRate() * SPLIT_FILE_LENGTH_MS / 1000;

            // Create a buffer of maxFramesPerFile frames
            double[] buffer = new double[maxFramesPerFile * numChannels];

            int framesRead;
            int fileCount = 0;
            do {
                // Read frames into buffer
                framesRead = inputWavFile.readFrames(buffer, maxFramesPerFile);
                WavFile outputWavFile = WavFile.newWavFile(
                        new File("out" + (fileCount + 1) + ".wav"),
                        inputWavFile.getNumChannels(),
                        framesRead,
                        inputWavFile.getValidBits(),
                        inputWavFile.getSampleRate());

                // Write the buffer
                outputWavFile.writeFrames(buffer, framesRead);
                outputWavFile.close();
                fileCount++;
            } while (framesRead != 0);

            // Close the input file
            inputWavFile.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
