package main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class UdpServer {
    // local port

    private int mLocalPort;

    // the datagram socket
    private DatagramSocket mSocket;
    private DatagramPacket receivePacket;

    UdpServer(int localPort) {
        mLocalPort = localPort;
    }

    public final void start() {
        // create UDP connection
        try {
            mSocket = new DatagramSocket(mLocalPort);
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Send some string via the socket
    public final boolean sendString(final DatagramPacket mDatagramPacket,
            final String string) {
        try {
            // Create the byte buffer
            final byte[] buffer = string.getBytes("UTF-8");
            // Create the UDP packet
            final DatagramPacket packet = new DatagramPacket(buffer,
                    buffer.length, mDatagramPacket.getAddress(),
                    mDatagramPacket.getPort());
            // And send the UDP packet
            mSocket.send(packet);
            // Print some information
            System.out.println("Sending message: " + string);
            return true;
        } catch (final IOException exc) {
            System.out.println("Sending failed");
            return false;
        }
    }

    // Receive some bytes via the socket
    public final byte[] recvBytes(final int size) {
        try {
            // Construct a byte array
            final byte[] buffer = new byte[size];
            // Construct an UDP packet
            receivePacket = new DatagramPacket(buffer, buffer.length);
            // Receive the UDP packet
            mSocket.receive(receivePacket);
            // Return the buffer now
            return Arrays.copyOf(buffer, receivePacket.getLength());
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("Received Failed");
            return null;
        }
    }

    // Receive some string via the socket
    public final String recvString() {
        try {
            // Receive a byte buffer
            final byte[] buffer = recvBytes(4096);
            // Check the buffer content
            if (buffer != null) {
                // Construct a message
                final String message = new String(buffer, 0, buffer.length,
                        "UTF-8");
                // Print some information
                System.out.println("Message received: " + message);
                // And return message
                return message;
            }
        } catch (final UnsupportedEncodingException exc) {
            // Print some information
            System.out.println("Message not received");
        }
        return null;
    }

    public DatagramPacket getDatagramPacket() {
        return receivePacket;
    }

}
