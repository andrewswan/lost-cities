package com.andrewswan.lostcities.ui.view;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import java.io.FileInputStream;
//import java.io.InputStream;
//
//import sun.audio.AudioData;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;
//import sun.audio.ContinuousAudioDataStream;
//
public class MusicPlayer {

	// Constants
	protected static final Log LOGGER = LogFactory.getLog(MusicPlayer.class);

	private static final String MUSIC_FILE = "trippygaia1.mid";

	// Properties
//	private final ContinuousAudioDataStream cas;

	/**
	 * Constructor
	 */
	public MusicPlayer() {
		final URL musicURL = getClass().getResource(MUSIC_FILE);
		LOGGER.debug("Music URL = " + musicURL);
//		// Open an input stream to the audio file.
//		InputStream in = new FileInputStream(MUSIC_FILE);
//		// Create an AudioStream object from the input stream.
//		AudioStream as = new AudioStream(in);
//		// Create audio stream as discussed previously.
//		// Create AudioData source.
//		AudioData data = as.getData();
//		// Create ContinuousAudioDataStream.
//		cas = new ContinuousAudioDataStream(data);
	}

	public void play() {
		// TODO AudioPlayer.player.play(cas);
	}

	public void stop() {
		// TODO AudioPlayer.player.stop(cas);
	}
}
