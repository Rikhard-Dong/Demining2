package io.ride.util;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-26
 * Time: 下午1:47
 * 点击音效效果
 */
public class PlaySoundsUtil {
    public static final int SOUND_CLICK1 = 0;       // 点击重新开始按钮状态
    public static final int SOUND_CLICK2 = 1;       // 点击游戏按钮状态
    public static final int SOUND_SUCCESS = 2;      // 游戏成功状态
    public static final int SOUND_BOOM = 3;         // 游戏失败状态

    private static AudioStream clickAudioStream1 = null;
    private static AudioStream clickAudioStream2 = null;
    private static AudioStream successAudioStream = null;
    private static AudioStream boomAudioStream = null;

    /**
     * 根据状态播放相应的音效
     *
     * @param status 状态
     */
    public static void playSounds(int status) {
        try {
            switch (status) {
                case SOUND_CLICK1:
                    clickAudioStream1 = new AudioStream(new FileInputStream("src/res/sounds/sound_click1.wav"));
                    AudioPlayer.player.start(clickAudioStream1);
                    break;
                case SOUND_CLICK2:
                    clickAudioStream2 = new AudioStream(new FileInputStream("src/res/sounds/sound_click2.wav"));
                    AudioPlayer.player.start(clickAudioStream2);
                    break;
                case SOUND_SUCCESS:
                    successAudioStream = new AudioStream(new FileInputStream("src/res/sounds/sound_success.wav"));
                    AudioPlayer.player.start(successAudioStream);
                    break;
                case SOUND_BOOM:
                    boomAudioStream = new AudioStream(new FileInputStream("src/res/sounds/sound_boom.wav"));
                    AudioPlayer.player.start(boomAudioStream);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (clickAudioStream1 != null) {
                try {
                    clickAudioStream1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clickAudioStream2 != null) {
                try {
                    clickAudioStream2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (successAudioStream != null) {
                try {
                    successAudioStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (boomAudioStream != null) {
                try {
                    boomAudioStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
