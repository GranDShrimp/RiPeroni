import javax.sound.midi.MidiDevice.Info;
import javax.sound.sampled.*;

import java.io.*;
import java.nio.file.Files;

public class Audio 
{
	public static void main(String[] args)
	{
		try
		{
			File soundFile = new File("C:/Users/Kaius/Desktop/test.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat soundFormat = audioStream.getFormat();
			DataLine.Info audioInfo = new DataLine.Info(Clip.class, soundFormat);
			Clip audioClip = (Clip)AudioSystem.getLine(audioInfo);
			
			audioClip.open(audioStream);
			audioClip.start();
			do
			{
				Thread.sleep(1000);
			}
			while(audioClip.isRunning());
			
		}
		catch(IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e)
		{
			System.out.println("Unsupported file type");
		}
	}
}
