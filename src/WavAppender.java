import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;

public class WavAppender {
    public static void main(String[] args) {

        try {
            String[] listAudip ={"out20.wav", "out21.wav", "out22.wav", "out23.wav"};
            AudioInputStream result = null;
            for (int i = 0; i< listAudip.length; i++) {
                if (result == null) {
                    result = AudioSystem.getAudioInputStream(new File(listAudip[i]));
                    continue;
                }
                AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(listAudip[i]));
                AudioInputStream appendFile = appentResult(result, clip2);
                result = appendFile;
            }
            AudioSystem.write(result,
                    AudioFileFormat.Type.WAVE,
                    new File("wavAppended.wav"));

        } catch (Exception e) {

        }
    }

    public static AudioInputStream appentResult(AudioInputStream file1, AudioInputStream file2) throws IOException, UnsupportedAudioFileException {


        AudioInputStream clip1 = file1;
        AudioInputStream clip2 = file2;
        AudioInputStream appendedFiles =
                new AudioInputStream(
                        new SequenceInputStream(clip1, clip2),
                        clip1.getFormat(),
                        clip1.getFrameLength() + clip2.getFrameLength());

        return appendedFiles;
    }

}