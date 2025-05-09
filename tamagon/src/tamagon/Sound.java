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
	public AudioClip titlescreen, intro, world1, levelEnd;
	
	/**
	 * Effects
	 */
	public AudioClip cursor, jump, fire, hit, walk, egg, sapphire, ruby, emerald, diamond, knightWalk, knightRun, hurt, dead, arrow, shield, dagger;
	
	/**
	 * Loads game Sounds
	 */
	public Sound() {
		//Songs
		titlescreen = load("titlescreen.wav");
		intro = load("intro.wav");
		world1 = load("world1.wav");
		levelEnd = load("levelEnd.wav");
		
		//SFX
		cursor = load("cursor.wav");
		jump = load("jump.wav");
		fire = load("fire.wav");
		hit = load("hit.wav");
		walk = load("walk.wav");
		egg = load("egg.wav");
		sapphire = load("sapphire.wav");
		ruby = load("ruby.wav");
		emerald = load("emerald.wav");
		diamond = load("diamond.wav");
		knightWalk = load("knightWalk.wav");
		knightRun = load("knightRun.wav");
		hurt = load("hurt.wav");
		dead = load("dead.wav");
		arrow = load("arrow.wav");
		shield = load("shield.wav");
		dagger = load("dagger.wav");
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
