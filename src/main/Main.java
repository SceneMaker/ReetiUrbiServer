package main;

import java.net.DatagramPacket;
import cereprocTts.CereprocHandle;

//import cereprocTts.CereprocHandle;
import urbi.UClient;
import urbi.urbijava;

public class Main {

    public static UClient client;
    // IP address is used to send commands to reeti
    private static final String IP = "127.0.0.1";

    public static final Object taskLock = new Object();

    // mark Reeti is speaking or not
    public static Boolean SpeakMark = false;
    private static VoiceHandle voiceHandle;

    static {

        LibPathSetting.loadConfig();
    }

    public static void main(String[] args) {

        // Process command line arguments
        final String voice_name = LibPathSetting.cereproc_voice;
        final String license_name = LibPathSetting.cereproc_license;

        // UDP settings (int localPort)
        UdpServer udpServer = new UdpServer(1256);
        udpServer.start();

        System.out.println("connecting ...");
        client = urbijava.connect(IP);

        System.out.println("Waiting for incoming messages...");

        while (true) {

            String message = udpServer.recvString();
            DatagramPacket dataPackage = udpServer.getDatagramPacket();

            if (message.indexOf("Global.servo") != -1) {
                // send the motor command message to reeti
                synchronized (client) {
                    client.send(message);
                }
                // stop speech
            } else if (LibPathSetting.CereproTts_Used.equalsIgnoreCase("true")) {

                if (message.indexOf("stopCereprocTtsSpeech") != -1) {
                    if (CereprocHandle.SpeakFlag == true) {
                        voiceHandle.stopVoice();
                        System.out.println("Speech Stop Executed");
                    }
                    // execute tts function
                } else {
                    voiceHandle = new VoiceHandle(voice_name,
                            license_name, message, dataPackage, udpServer);
                    voiceHandle.start();
                }
            } else {
                udpServer.sendString(dataPackage, "finish");
            }
        }
    }
}
