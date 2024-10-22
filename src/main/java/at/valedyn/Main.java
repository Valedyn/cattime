package at.valedyn;

import java.net.Inet4Address;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException, SocketException {

        String timeServer = ""; // TODO replace with time server configuration
        try {
            Inet4Address inet4Address = (Inet4Address) Inet4Address.getByName(timeServer);
            Packet packet = new Packet(inet4Address, 123);
            PortChecker portChecker = new PortChecker();
            System.out.println(portChecker.checkTime(packet));
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host: " + e.getMessage());
        } catch (SocketException e) {
            System.out.println("Socket Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}