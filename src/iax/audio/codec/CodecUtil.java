package iax.audio.codec;

import org.apache.commons.io.FileUtils;
import org.restcomm.media.core.spi.dsp.Codec;
import org.restcomm.media.core.spi.memory.Frame;
import org.restcomm.media.core.spi.memory.Memory;


import javax.sound.sampled.AudioFormat;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by C on 2018/3/30.
 */
public class CodecUtil {

    private AudioFormat.Encoding encoding;
    private Codec encoder;
    private Codec decoder;

    public CodecUtil(AudioFormat.Encoding encoding) {
        updateEncoding(encoding);
    }

    private void updateEncoding(AudioFormat.Encoding encoding) {
        this.encoding = encoding;
        encoder = getEncoder(encoding);
        decoder = getDecoder(encoding);
    }

    public static Codec getEncoder(AudioFormat.Encoding encoding) {
        Codec encoder = null;
        if (encoding.equals(AudioFormat.Encoding.ALAW)) {
            encoder = new org.restcomm.media.core.codec.g711.alaw.Encoder();
        } else if (encoding.equals(AudioFormat.Encoding.ULAW)) {
            encoder = new org.restcomm.media.core.codec.g711.ulaw.Encoder();
        } else if (encoding.equals(new AudioFormat.Encoding("G729"))) {
            encoder = new org.restcomm.media.core.codec.g729.Encoder();
        }
        return encoder;
    }

    public static Codec getDecoder(AudioFormat.Encoding encoding) {
        Codec decoder = null;
        if (encoding.equals(AudioFormat.Encoding.ALAW)) {
            decoder = new org.restcomm.media.core.codec.g711.alaw.Decoder();
        } else if (encoding.equals(AudioFormat.Encoding.ULAW)) {
            decoder = new org.restcomm.media.core.codec.g711.ulaw.Decoder();
        } else if (encoding.equals(new AudioFormat.Encoding("G729"))) {
            decoder = new org.restcomm.media.core.codec.g729.Decoder();
        }
        return decoder;
    }

    public byte[] encode(byte[] data) {
        byte[] target = data;
        if (encoding.equals(AudioFormat.Encoding.ALAW)) {
            target = pcm2alaw(encoder, data);
        } else if (encoding.equals(AudioFormat.Encoding.ULAW)) {
            target = pcm2ulaw(encoder, data);
        } else if (encoding.equals(new AudioFormat.Encoding("G729"))) {
            target = pcm2g729(encoder, data);
        }
        return target;
    }

    public byte[] decode(byte[] data) {
        byte[] target = data;
        if (encoding.equals(AudioFormat.Encoding.ALAW)) {
            target = alaw2pcm(decoder, data);
        } else if (encoding.equals(AudioFormat.Encoding.ULAW)) {
            target = ulaw2pcm(decoder, data);
        } else if (encoding.equals(new AudioFormat.Encoding("G729"))) {
            target = g7292pcm(decoder, data);
        }
        return target;
    }

    public static byte[] pcm2alaw(Codec encoder, byte[] data) {
        Frame buffer = Memory.allocate(data.length);
        byte[] src = buffer.getData();
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            src[j++] = data[i];
        }
        buffer.setLength(data.length);
        return encoder.process(buffer).getData();
    }

    public static byte[] alaw2pcm(Codec decoder, byte[] data) {
        Frame buffer = Memory.allocate(data.length);
        byte[] src = buffer.getData();
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            src[j++] = data[i];
        }
        buffer.setLength(data.length);
        return decoder.process(buffer).getData();
    }

    public static byte[] pcm2ulaw(Codec encoder, byte[] data) {
        Frame buffer = Memory.allocate(data.length);
        byte[] src = buffer.getData();
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            src[j++] = data[i];
        }
        buffer.setLength(data.length);
        return encoder.process(buffer).getData();
    }

    public static byte[] ulaw2pcm(Codec decoder, byte[] data) {
        Frame buffer = Memory.allocate(data.length);
        byte[] src = buffer.getData();
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            src[j++] = data[i];
        }
        buffer.setLength(data.length);
        return decoder.process(buffer).getData();
    }

    public static byte[] pcm2g729(Codec encoder, byte[] data) {
        ByteArrayOutputStream dstBuffer = new ByteArrayOutputStream();
        try {
            Frame buffer = Memory.allocate(320);
            byte[] src = buffer.getData();
            int readLen = 0;
            while (readLen < data.length) {
                int remainLen = data.length - readLen;
                int onceLen = 320 < remainLen ? 320 : remainLen;
                System.arraycopy(data, readLen, src, 0, onceLen);
                readLen += onceLen;
                buffer.setLength(onceLen);
                byte[] encodeBytes = encoder.process(buffer).getData();
                dstBuffer.write(encodeBytes);
            }
        } catch (Exception e) {

        }
        return dstBuffer.toByteArray();
    }

    public static byte[] g7292pcm(Codec decoder, byte[] data) {
        ByteArrayOutputStream dstBuffer = new ByteArrayOutputStream();
        try {
            Frame buffer = Memory.allocate(20);
            byte[] src = buffer.getData();
            int readLen = 0;
            while (readLen < data.length) {
                int remainLen = data.length - readLen;
                int onceLen = 20 < remainLen ? 20 : remainLen;
                System.arraycopy(data, readLen, src, 0, onceLen);
                readLen += onceLen;
                buffer.setLength(onceLen);
                byte[] encodeBytes = decoder.process(buffer).getData();
                dstBuffer.write(encodeBytes);
            }
        } catch (Exception e) {

        }
        return dstBuffer.toByteArray();
    }

    public static void main(String[] args) throws IOException {

        AudioFormat.Encoding encoding = AudioCodec.G711_ALAW.getAudioFormat().getEncoding();
        CodecUtil codecUtil = new CodecUtil(encoding);
        String pcmFile = "D:\\data\\voice\\jlqchf01.raw";
        String encodeFile = "D:\\data\\voice\\jlqchf01_en.ULAW";
        String decoderFile = "D:\\data\\voice\\jlqchf01_de.raw";

        byte[] src = FileUtils.readFileToByteArray(new File(pcmFile));
        byte[] data = codecUtil.encode(src);
        FileUtils.writeByteArrayToFile(new File(encodeFile), data);

        byte[] decodeData = codecUtil.decode(data);
        FileUtils.writeByteArrayToFile(new File(decoderFile), decodeData);

    }

}
