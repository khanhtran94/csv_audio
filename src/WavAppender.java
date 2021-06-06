import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

public class WavAppender {
    public static void main(String[] args) {

        try {
            List<String> paths = new ArrayList<>();
            paths.add("out20.wav");
            paths.add("out21.wav");
            paths.add("out22.wav");
            paths.add("out23.wav");
            AudioInputStream clip1 = null;
            for (String path : paths)
            {
                if(clip1 == null)
                {
                    clip1 = AudioSystem.getAudioInputStream(new File(path));
                    continue;
                }
                AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File(path));
                AudioInputStream appendedFiles = new AudioInputStream(
                        new SequenceInputStream(clip1, clip2),
                        clip1.getFormat(),
                        clip1.getFrameLength() + clip2.getFrameLength());
                clip1 = appendedFiles;
            }
            AudioSystem.write(clip1, AudioFileFormat.Type.WAVE, new File("exported.wav"));

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