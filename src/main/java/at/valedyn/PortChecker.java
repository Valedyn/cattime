package at.valedyn;

import lombok.*;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Setter @Getter @ToString
public class PortChecker {

    private DatagramSocket socket;
    private static final int MAX_RETRIES = 3;
    private static final int TIMEOUT = 3000;


    public PortChecker() throws SocketException {
        socket = new DatagramSocket(null);
        socket.bind(new InetSocketAddress(0));
    }

    public String checkTime(Packet packet) {
        Inet4Address address = packet.getIpAddress();
        // everything else unnecessary for client. specifications yoinked from https://www.rfc-editor.org/rfc/rfc5905#page-17

        /*
        LI Leap Indicator (leap): 2-bit integer warning of an impending leap
       second to be inserted or deleted in the last minute of the current
       month with values defined in Figure 9.

           +-------+----------------------------------------+
           | Value | Meaning                                |
           +-------+----------------------------------------+
           | 0     | no warning                             |
           | 1     | last minute of the day has 61 seconds  |
           | 2     | last minute of the day has 59 seconds  |
           | 3     | unknown (clock unsynchronized)         |
           +-------+----------------------------------------+
         */
        String li = "00";

        // VN Version Number (version): 3-bit integer representing the NTP version number, currently 4.
        String version = "100";
        /*
           Mode (mode): 3-bit integer representing the mode, with values defined
            in Figure 10.

                      +-------+--------------------------+
                      | Value | Meaning                  |
                      +-------+--------------------------+
                      | 0     | reserved                 |
                      | 1     | symmetric active         |
                      | 2     | symmetric passive        |
                      | 3     | client                   |
                      | 4     | server                   |
                      | 5     | broadcast                |
                      | 6     | NTP control message      |
                      | 7     | reserved for private use |
                      +-------+--------------------------+
         */
        String mode = "011";


        byte firstSection = (byte) Integer.parseInt(li+version+mode, 2);
        byte empty = (byte) Integer.parseInt("00000000", 2);

        byte[] fullPackage = new byte[48];
        fullPackage[0] = firstSection;

        DatagramPacket timePacket = new DatagramPacket(fullPackage, fullPackage.length, address, 123);

        try{
            socket.send(timePacket);

            fullPackage[0] = empty;
            DatagramPacket responsePacket = new DatagramPacket(fullPackage, fullPackage.length);
            socket.receive(responsePacket);

            // TODO parse correctly
            return new String(responsePacket.getData());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
