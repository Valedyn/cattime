package at.valedyn;

import at.valedyn.Exceptions.InvalidByteException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimePacketContent{

    byte[] fullPackage = new byte[48];

    public TimePacketContent(int leapIndicator, int version, int mode) throws InvalidByteException {
        if(leapIndicator > 3 || leapIndicator < 0){
            leapIndicator = 0;
        }

        if(version > 7 || version < 0){
            version = 4;
        }

        if(mode > 7 || mode < 0){
            mode = 3;
        }

        fullPackage[0] = firstNTPByte(leapIndicator, version, mode);

    }

    private String intToByteString(int i){
        return Integer.toBinaryString(i);
    }

    private byte firstNTPByte(int leapIndicator, int version, int mode) throws InvalidByteException {
        return StringToByte(intToByteString(leapIndicator) + intToByteString(version) + intToByteString(mode));
    }

    private byte StringToByte(String s) throws InvalidByteException {
        if(s.length() > 8){
            throw new InvalidByteException("ERR: The given string is too long to be a byte!");
        }
        return (byte) Integer.parseInt(s, 2);
    }
}
