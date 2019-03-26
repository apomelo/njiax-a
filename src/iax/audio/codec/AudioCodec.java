package iax.audio.codec;

import iax.protocol.frame.VoiceFrame;

import javax.sound.sampled.AudioFormat;

/**
 * Created by C on 2018/3/30.
 */
public class AudioCodec {

    public static final int DEFAULT_PAYLOAD_SIZE = 160;
    private int payloadType;
    private AudioFormat audioFormat;
    private int packetRate;
    private int payloadSize = DEFAULT_PAYLOAD_SIZE;

    public static final AudioCodec G711_MULAW = new AudioCodec(0, DEFAULT_PAYLOAD_SIZE, new AudioFormat(AudioFormat.Encoding.ULAW, 8000.0F, 16, 1, 160, 50.0F, false));
    public static final AudioCodec G711_ALAW = new AudioCodec(8, DEFAULT_PAYLOAD_SIZE, new AudioFormat(AudioFormat.Encoding.ALAW, 8000.0F, 16, 1, 160, 50.0F, false));
    public static final AudioCodec G729 = new AudioCodec(18, 20, new AudioFormat(new AudioFormat.Encoding("G729"), 8000.0F, 16, 1, 20, 50.0F, false));

    public static AudioCodec getCodec(int codecNum) {
        if (codecNum == VoiceFrame.G711_MULAW) {
            return G711_MULAW;
        } else if (codecNum == VoiceFrame.G711_ALAW) {
            return G711_ALAW;
        } else if (codecNum == VoiceFrame.G729) {
            return G729;
        } else {
            throw new IllegalArgumentException("not support codec, codec = " + codecNum);
        }
    }

    public AudioCodec(int payloadType, int payloadSize, AudioFormat audioFormat) {
        this.payloadType = payloadType;
        this.audioFormat = audioFormat;
        this.payloadSize = payloadSize;
        this.packetRate = packetRate();
    }

    public int packetRate() {
        return Double.valueOf(audioFormat.getFrameRate() * audioFormat.getFrameSize() / payloadSize).intValue();
    }

    public int getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(int payloadType) {
        this.payloadType = payloadType;
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    public int getPacketRate() {
        return packetRate;
    }

    public void setPacketRate(int packetRate) {
        this.packetRate = packetRate;
    }

    public int getPayloadSize() {
        return payloadSize;
    }

    public void setPayloadSize(int payloadSize) {
        this.payloadSize = payloadSize;
    }

    @Override
    public String toString() {
        return "AudioCodec{" +
                "payloadType=" + payloadType +
                ", audioFormat=" + audioFormat +
                ", packetRate=" + packetRate +
                ", payloadSize=" + payloadSize +
                '}';
    }
}
