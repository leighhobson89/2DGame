package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    Clip clipPhone;
    public long length;
    public int lastSong;
    final URL[] soundURL = new URL[50];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/inGameSong1.wav");
        soundURL[1] = getClass().getResource("/sound/keys.wav");
        soundURL[2] = getClass().getResource("/sound/pills.wav");
        soundURL[3] = getClass().getResource("/sound/doorUnlock.wav");
        soundURL[4] = getClass().getResource("/sound/doorInside.wav");
        soundURL[5] = getClass().getResource("/sound/whack.wav");
        soundURL[6] = getClass().getResource("/sound/spiderHitSqueak.wav");
        soundURL[7] = getClass().getResource("/sound/spiderDeadSquelch.wav");
        soundURL[8] = getClass().getResource("/sound/discoverSpiderShriek.wav");
        soundURL[9] = getClass().getResource("/sound/levelUp.wav");
        soundURL[10] = getClass().getResource("/sound/cursor.wav");
        soundURL[11] = getClass().getResource("/sound/select.wav");
        soundURL[12] = getClass().getResource("/sound/sitInChairRelax.wav");
        soundURL[13] = getClass().getResource("/sound/phewKillSpider.wav");
        soundURL[14] = getClass().getResource("/sound/wheelieBinClosing.wav");
        soundURL[15] = getClass().getResource("/sound/wheelieBinDragToLoop.wav");
        soundURL[16] = getClass().getResource("/sound/pickupElectricGuitar.wav");
        soundURL[17] = getClass().getResource("/sound/pickupAcousticGuitar.wav");
        soundURL[18] = getClass().getResource("/sound/collectWearable.wav");
        soundURL[19] = getClass().getResource("/sound/spatula.wav");
        soundURL[20] = getClass().getResource("/sound/squeakyToy.wav");
        soundURL[21] = getClass().getResource("/sound/boneThrow.wav");
        soundURL[22] = getClass().getResource("/sound/coinCollect.wav");
        soundURL[23] = getClass().getResource("/sound/shovelDig.wav");
        soundURL[24] = getClass().getResource("/sound/gameOverMum.wav");
        soundURL[25] = getClass().getResource("/sound/passOutMum.wav");
        soundURL[26] = getClass().getResource("/sound/waspBuzz.wav");
        soundURL[27] = getClass().getResource("/sound/talkTextSound.wav");
        soundURL[28] = getClass().getResource("/sound/phoneRinging.wav");
        soundURL[29] = getClass().getResource("/sound/fastPhoneChatter.wav");
        soundURL[30] = getClass().getResource("/sound/hatchet.wav");
        soundURL[31] = getClass().getResource("/sound/squelch.wav");
        soundURL[32] = getClass().getResource("/sound/showerRunning.wav");
        soundURL[33] = getClass().getResource("/sound/showerDripping.wav");
        soundURL[34] = getClass().getResource("/sound/inGameSong2.wav");
        soundURL[35] = getClass().getResource("/sound/inGameSong3.wav");
        soundURL[36] = getClass().getResource("/sound/flamerSfx.wav");
        soundURL[37] = getClass().getResource("/sound/waterShortClip.wav");
    }

    public void setFile(int i) {
        if (i == 28) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clipPhone = AudioSystem.getClip();
                clipPhone.open(ais);
                fc = (FloatControl) clipPhone.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);
                fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                checkVolume();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopPhoneSfx() {
        clipPhone.stop();
    }

    public void play(long position, boolean pausing) {
        clip.start();
        if (pausing) {
            clip.setMicrosecondPosition(position);
        }
    }

    public void loop(int phone) {
        if (phone == 28) {
            clipPhone.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public long stop(boolean pausing, int phone) {
        if (phone == 28) {
            long position = 0;
            if (pausing) {
                position = clipPhone.getMicrosecondPosition();
            }
            clipPhone.stop();
            return position;
        } else {
            long position = 0;
            if (pausing) {
                position = clip.getMicrosecondPosition();
            }
            clip.stop();
            return position;
        }

    }

    public void checkVolume() { //range runs from -80f to +6f
        switch (volumeScale) {
            case 0 -> volume = -80f;
            //min possible
            case 1 -> volume = -20f;
            case 2 -> volume = -12f;
            case 3 -> volume = -5f;
            case 4 -> volume = 1f;
            case 5 -> volume = 6f;
            //max possible
        }
        fc.setValue(volume);
    }
}
