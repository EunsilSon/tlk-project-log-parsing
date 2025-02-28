package logparser;

import exception.ParsingLogException;

import java.util.ArrayList;
import java.util.List;

public class HexByteConverter {
    final static int LOG_DATA_COUNT = 7;

    public List<String> splitHexByByte(String hexLog) throws ParsingLogException {
        if (!isValidHexLog(hexLog)) { // hex str 유효성
            return null;
        }

        List<byte[]> splitByteList = new ArrayList<>();
        int[] byteInfo = {2, 2, 4, 2, 2, 1, 1};
        byte[] byteLog = hexStrToByteArr(hexLog);
        int srcPos = 0;

        for (int byteNum : byteInfo) {
            byte[] divideArr = new byte[byteNum];
            System.arraycopy(byteLog, srcPos, divideArr, 0, byteNum);
            splitByteList.add(divideArr);
            srcPos += byteNum;
        }

        if (splitByteList.size() != LOG_DATA_COUNT) { // 정해진 byte 단위로 분할했을 때 필요한 데이터 개수와 맞는지
            throw new ParsingLogException("Hex log to byte array size mismatch");
        }

        return byteArrToHexList(splitByteList);
    }

    private boolean isValidHexLog(String hexLog) throws ParsingLogException {
        if (!hexLog.matches("^[a-fA-F0-9]*$")) {
            throw new ParsingLogException("Not valid hex log");
        }

        if (hexLog.length() % 2 != 0) {
            throw new ParsingLogException("Hex log length must be even");
        }

        return true;
    }

    private byte[] hexStrToByteArr(String hexLog) {
        int length = hexLog.length();
        byte[] byteLog = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteLog[i / 2] = (byte) ((Character.digit(hexLog.charAt(i), 16) << 4) + Character.digit(hexLog.charAt(i + 1), 16));
        }
        
        return byteLog;
    }

    private List<String> byteArrToHexList(List<byte[]> splitByteList) {
        List<String> hexByteList = new ArrayList<>();
        for (byte[] divideByte : splitByteList) {
            for (byte b : divideByte) {
                hexByteList.add(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        }
        return hexByteList;
    }
}
