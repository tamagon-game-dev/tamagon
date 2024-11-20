package tamagon;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioClip {

	/**
	 * Audio clip data
	 */
	public Clip audioClip;

	/**
	 * Manages audio files
	 * 
	 * @param buffer - audio file data
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public AudioClip(byte[] buffer) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

		// Can't process audio if there is no data
		if (buffer == null)
			return;

		// Getting the current audio clip
		audioClip = AudioSystem.getClip();
		// Opening audio clip data
		audioClip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
	}

	/**
	 * Plays the audio clip once
	 */
	public void play() {

		// If there is no audio clip or the clip is already playing, do nothing
		if (audioClip == null || audioClip.isRunning())
			return;

		// stop current audio line
		audioClip.stop();

		// reset audio position to start
		audioClip.setFramePosition(0);

		// plays the audio
		audioClip.start();
	}

	/**
	 * Plays the audio clip until the method stop is called
	 */
	public void loop() {

		// If there is no audio clip or the clip is already playing, do nothing
		if (audioClip == null || audioClip.isRunning())
			return;

		// play audio forever
		audioClip.loop(Clip.LOOP_CONTINUOUSLY);

	}

	/**
	 * Stop the audio
	 */
	public void stop() {
		// If there is no audio clip, do nothing
		if (audioClip == null)
			return;

		// stop current audio line
		audioClip.stop();

		// reset audio position to start
		audioClip.setFramePosition(0);
	}
}
