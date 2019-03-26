package iax.audio;

import iax.audio.codec.AudioCodec;
import iax.audio.codec.CodecUtil;
import iax.protocol.call.Call;
import iax.protocol.call.command.send.CallCommandSendFacade;
import iax.protocol.peer.Peer;
import iax.protocol.user.command.UserCommandFacade;
import iax.protocol.util.FileUtils;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by C on 2018/4/2.
 */
public class SendVoiceData implements Runnable {

    private Call call;
    private Peer peer;

    public SendVoiceData (Call call, Peer peer) {
        this.call = call;
        this.peer = peer;
    }

    public void execute() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        File file = new File("D:\\data\\voice\\ivr.raw");
        byte[] src = null;
        System.out.println("--------------------------");
        try {
            src = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AudioFormat.Encoding encoding = AudioCodec.G711_ALAW.getAudioFormat().getEncoding();
        CodecUtil codecUtil = new CodecUtil(encoding);
        byte[] audioBuffer = codecUtil.encode(src);
        int pos = 0;
        int len = 480;
        while (pos < audioBuffer.length) {
            if (audioBuffer.length - pos < len) {
                len = audioBuffer.length - pos;
                if (len == 1) break;
            }
            byte[] bytes = new byte[len];
            System.arraycopy(audioBuffer, pos, bytes, 0, len);
            try {
                TimeUnit.MILLISECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CallCommandSendFacade.sendVoice(call, bytes);
            pos += len;
            if (pos >= audioBuffer.length) {
                pos = audioBuffer.length - 1;
            }
        }
        UserCommandFacade.sendDTMF(peer, call.getCalledNumber(), '#');
        System.out.println("发送按键 # .");
        try {
            TimeUnit.MILLISECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int j = 0; j < 20; j ++) {
            byte[] bytes = codecUtil.encode(new byte[960]);
            CallCommandSendFacade.sendVoice(call, bytes);
            System.out.println("发送空白数据");
            try {
                TimeUnit.MILLISECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        UserCommandFacade.sendDTMF(peer, call.getCalledNumber(), '1');
        System.out.println("发送按键 1 .");
    }
}
