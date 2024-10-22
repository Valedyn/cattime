package at.valedyn;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.Inet4Address;

@Data @AllArgsConstructor
public class Packet {
    private Inet4Address ipAddress;
    private int port;
}
