package com.kerust.tankwar;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Audio extends Thread {

    private AudioInputStream audioInputStream = null;

    public Audio(InputStream inputStream) {
        try {
            this.audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        SourceDataLine sourceDataLine = null;
        AudioFormat format = audioInputStream.getFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        try {
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceDataLine.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        int nBytesRead = 0;
        sourceDataLine.start();
        byte[] abData = new byte[1024];

        try {
            while ((nBytesRead = audioInputStream.read(abData, 0, abData.length)) != -1) {
                sourceDataLine.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sourceDataLine.drain();
        sourceDataLine.close();
    }
}
