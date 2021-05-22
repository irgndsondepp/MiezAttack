package com.irgndsondepp.clone.audio;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import com.irgndsondepp.clone.Game;

/**
 * handles all the sound objects. they all have a path and a boolean play if
 * they are playing
 * 
 * @author Robert
 * 
 */
public class Sound implements Runnable {

	private String path;
	private boolean play;

	// Music
	public static Sound menu_theme = new Sound("/audio/music/menutheme.wav");
	public static Sound level_music = new Sound("/audio/music/level.wav");

	// Announcer
	public static Sound brutal = new Sound("/audio/sounds/brutal.wav");
	public static Sound awesome = new Sound("/audio/sounds/awesome.wav");
	public static Sound incredible = new Sound("/audio/sounds/incredible.wav");
	public static Sound barbaric = new Sound("/audio/sounds/barbaric.wav");
	public static Sound psycho = new Sound("/audio/sounds/psycho.wav");
	public static Sound mauled = new Sound("/audio/sounds/mauled.wav");

	// Item Sounds
	public static Sound healing = new Sound("/audio/sounds/healing.wav");

	// Gun Sounds
	public static Sound fireball = new Sound("/audio/sounds/fireball.wav");
	public static Sound explosion = new Sound("/audio/sounds/explosion.wav");

	public static Sound handgun_pickup = new Sound(
			"/audio/sounds/handgun_pickup.wav");
	public static Sound handgun_shot1 = new Sound(
			"/audio/sounds/handgun_shot1.wav");
	public static Sound handgun_shot2 = new Sound(
			"/audio/sounds/handgun_shot2.wav");
	public static Sound handgun_shot3 = new Sound(
			"/audio/sounds/handgun_shot3.wav");
	public static Sound handgun_shot4 = new Sound(
			"/audio/sounds/handgun_shot4.wav");

	public static Sound shotgun_shot = new Sound(
			"/audio/sounds/shotgun_shot.wav");

	// Mob Sounds
	public static Sound player_mauled = new Sound(
			"/audio/sounds/player_mauled.wav");
	public static Sound player_hit = new Sound("/audio/sounds/player_hit.wav");

	public static Sound cat_die = new Sound("/audio/sounds/cat_die.wav");
	public static Sound cat_meow1 = new Sound("/audio/sounds/cat_meow1.wav");
	public static Sound cat_meow2 = new Sound("/audio/sounds/cat_meow2.wav");
	public static Sound cat_hiss = new Sound("/audio/sounds/cat_hiss.wav");

	// Boss Sounds
	public static Sound cathulhu_scream = new Sound(
			"/audio/sounds/cathulhu_scream.wav");
	public static Sound cathulhu_hit = new Sound(
			"/audio/sounds/cathulhu_hit.wav");
	public static Sound cathulhu_die = new Sound(
			"/audio/sounds/cathulhu_die.wav");

	/**
	 * create a sound or music file
	 * 
	 * @param path
	 *            is the path to the file from the resource folder
	 */
	public Sound(String path) {
		String filePath = new File("").getAbsolutePath();
		this.path = filePath + "/res" + path;
	}

	/**
	 * call the run method and setting the play value to true
	 */
	public void playSound() {
		if (Game.mute) return;
		play = true;
		try {
			Thread thread = new Thread(this);
			thread.start();
		} catch (Exception e) {
			System.err.println("Could not start music thread");
		}
	}

	/**
	 * play the sound creating a new daemon thread and after finishing it
	 * setting play to false. can be stopped by otherwise setting the play value
	 * to false.
	 */
	public void run() {
		File soundFile = new File(path);
		if (!soundFile.exists()){
			return;
		}
		SourceDataLine soundLine = null;
		int BUFFER_SIZE = 64 * 1024;

		try {
			//File soundFile = new File(path);

			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class,
					audioFormat);
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			int nBytesRead = 0;
			byte[] sampledData = new byte[BUFFER_SIZE];

			while (nBytesRead != -1 && play == true) {
				nBytesRead = audioInputStream.read(sampledData, 0,
						sampledData.length);
				if (nBytesRead >= 0) {
					soundLine.write(sampledData, 0, nBytesRead);
				}
			}

		} catch (Exception e) {
			System.err.println("Could not start music!");
		}

		soundLine.drain();
		soundLine.close();
		play = false;
	}

	/**
	 * stop the sound by setting play to false.
	 */
	public void stop() {
		play = false;
	}

	/**
	 * check if a file is playing
	 * 
	 * @return
	 */
	public boolean isPlaying() {
		return play;
	}

	// /**
	// * sometime in the future can be used to set the Volume of the audio file
	// */
	// public void setVolume() {
	// // TODO
	// }
}
