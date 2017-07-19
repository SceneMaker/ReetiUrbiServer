package main;

import cereprocTts.CereprocHandle;
import java.net.DatagramPacket;
import static main.Main.client;

/**
 *
 * @author reeti
 */
public class VoiceHandle extends Thread {

    private static CereprocHandle voice;
    private String voice_name;
    private String license_name;
    private String message;
    private DatagramPacket mDatagramPacket;
    private UdpServer udpServer;

    public VoiceHandle(String voice_name, String license_name, String message,
            DatagramPacket mDatagramPacket, UdpServer udpServer) {

        this.voice_name = voice_name;
        this.license_name = license_name;
        this.message = message;
        this.mDatagramPacket = mDatagramPacket;
        this.udpServer = udpServer;
    }

    @Override
    public void run() {
        
        voice = new CereprocHandle(voice_name, license_name, message, Main.client);

        voice.voiceGenarate();

        udpServer.sendString(mDatagramPacket, "finish");
    }

    public void stopVoice() {
        voice.stopVoice();
    }
}
