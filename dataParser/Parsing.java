package dataParser;

import java.text.SimpleDateFormat;
import java.util.*;

public class Parsing {
    public List<String> splitHexStrByByte(String hexLog) {
        byte[] byteLog = hexStrToByteArr(hexLog);
        List<String> hexStringList = new ArrayList<>();

        int[] byteInfo = {2, 2, 4, 2, 2, 1, 1}; // 바이트 분할 단위
        int srcPos = 0;

        for (int byteNum : byteInfo) {
            byte[] divideArr = new byte[byteNum];
            System.arraycopy(byteLog, srcPos, divideArr, 0, byteNum);
            hexStringList.add(byteArrToHexStr(divideArr)); // 추후 형변환을 위해 다시 hex string 으로 변환
            srcPos += byteNum;
        }
        return hexStringList;
    }

    public byte[] hexStrToByteArr(String hexLog) {
        int length = hexLog.length();
        byte[] byteLog = new byte[length / 2]; // 16진수 문자열의 한 문자는 4bit, 8bit = 1byte 따라서 나누기 2

        for (int i = 0; i < length; i += 2) {
            byteLog[i / 2] = (byte) ((Character.digit(hexLog.charAt(i), 16) << 4) + Character.digit(hexLog.charAt(i + 1), 16));
        }
        return byteLog;
    }

    public String byteArrToHexStr(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public String typeCasting(List<String> splitHexLog, StringBuilder sb) {
        sb.append("'" + splitHexLog.get(0) + "'").append(", ");

        sb.append(Integer.parseInt(splitHexLog.get(1), 16)).append(", ");

        int data3 = Integer.parseInt(splitHexLog.get(2), 16);
        sb.append("'" + millisToDate(data3) + "'").append(", ");

        sb.append((float) hexToSignedInt(splitHexLog.get(3)) / 10).append(", ");

        int data5 = Integer.parseInt(splitHexLog.get(4), 16);
        sb.append((float) data5 / 10).append(", ");

        int data6 = Integer.parseInt(splitHexLog.get(5), 16);
        sb.append((float) data6 / 10).append(", ");

        sb.append(Integer.parseInt(splitHexLog.get(6), 16));

        return sb.toString();
    }

    public String millisToDate(long millis) {
        long millisTime = millis * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +SS:SS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date timeInDate = new Date(millisTime);
        return sdf.format(timeInDate);
    }

    public int hexToSignedInt(String hex) {
        int num = Integer.parseInt(hex, 16);

        // 2의 보수 계산을 이용한 음수 표현
        if ((num & 0x8000) != 0) { // MSB: Most Significant Bit
            num = num - (1 << 16);
        }

        return num;
    }
}
