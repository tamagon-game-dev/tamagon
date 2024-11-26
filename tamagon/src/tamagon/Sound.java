package tamagon;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	/**
	 * Songs
	 */
	public AudioClip titlescreen;
	
	/**
	 * Effects
	 */
	public AudioClip cursor, jump1, jump2, jump3, fire, hit, walk;
	
	/**
	 * Loads game Sounds
	 */
	public Sound() {
		//Songs
		titlescreen = load("titlescreen.wav");
		
		//SFX
		cursor = load("cursor.wav");
		jump1 = load("jump1.wav");
		jump2 = load("jump2.wav");
		jump3 = load("jump3.wav");
		fire = load("fire.wav");
		hit = load("hit.wav");
		walk = load("walk.wav");
		
	}
	
	/**
	 * Loads an audio file
	 * @param name - audio file name
	 * @return
	 */
	private AudioClip load(String name) {	
		
		try {
			//reading audio file
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			DataInputStream data = new DataInputStream(Sound.class.getResourceAsStream("/" + name));
			
			//One kilobyte buffer
			byte[] buffer = new byte[1024];
			
			//byte array length
			int length = 0;
			
			//if there is data to read
			while((length = data.read(buffer)) >= 0) {
				
				//Write its binary data
				bytes.write(buffer, 0, length);
			}
			
			//Done reading audio file
			data.close();
			
			//File data
			byte[] audioData = bytes.toByteArray();
			return new AudioClip(audioData);
		} catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
			return null;
		}
	}
}
