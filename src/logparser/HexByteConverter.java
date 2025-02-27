package logparser;

import exception.CustomException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HexByteConverter {
    Logger logger = Logger.getLogger("DataParserLogger");

    public List<String> splitHexByByte(String hexLog) {
        byte[] byteLog = hexStrToByteArr(hexLog);
        List<String> hexByteList = new ArrayList<>();

        int[] byteInfo = {2, 2, 4, 2, 2, 1, 1}; // 바이트 분할 단위
        int srcPos = 0;

        for (int byteNum : byteInfo) {
            byte[] divideArr = new byte[byteNum];
            System.arraycopy(byteLog, srcPos, divideArr, 0, byteNum);
            hexByteList.add(byteArrToHexStr(divideArr)); // 추후 형변환을 위해 다시 hex string 으로 변환
            srcPos += byteNum;
        }
        return hexByteList;
    }

    private byte[] hexStrToByteArr(String hexLog) {
        byte[] byteLog = null;
        try {
            int length = hexLog.length();

            if (length % 2 != 0) {
                throw new CustomException("유효한 16진수 문자열이 아닙니다.");
            }

            byteLog = new byte[length / 2];
            for (int i = 0; i < length; i += 2) {
                byteLog[i / 2] = (byte) ((Character.digit(hexLog.charAt(i), 16) << 4) + Character.digit(hexLog.charAt(i + 1), 16));
            }
        } catch (CustomException e) {
            logger.warning(e.getMessage());
        }
        return byteLog;
    }

    private String byteArrToHexStr(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
