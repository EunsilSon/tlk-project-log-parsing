import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class DataParsing {
    static List<String> hexLogList = new ArrayList<>();

    public static void main(String[] args) {
        // 파일 읽기
        String logSrc = "C:\\Users\\USER\\Desktop\\logs.txt";
        readFile(logSrc);

        // hex 문자열 -> byte 배열
        //for (String hexLog : hexLogList) {
            byte[] byteLog = hexStringToByteArray(hexLogList.get(0)); // test

            // byte 배열을 N byte 만큼 자르기 + hex 변환
            List<String> hexStringList = new ArrayList<>();
            int[] byteInfo = {2, 2, 4, 2, 2, 1, 1};
            int srcPos = 0;

            for (int byteNum : byteInfo) {
                byte[] byteArr = new byte[byteNum];
                System.arraycopy(byteLog, srcPos, byteArr, 0, byteNum);
                hexStringList.add(byteArrayToHexString(byteArr));
                srcPos += byteNum;
            }

            for (String s : hexStringList) {
                System.out.println(s);
            }
        //}

        // 자른 바이트 변환

        // 최종 파일 작성

    }

    public static void readFile(String logSrc) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(logSrc));

            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                } else {
                    hexLogList.add(line);
                }
            }

            br.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static byte[] hexStringToByteArray(String hexLog) {
        int length = hexLog.length();
        byte[] byteLog = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            byteLog[i / 2] = (byte) ((Character.digit(hexLog.charAt(i), 16) << 4)
                    + Character.digit(hexLog.charAt(i + 1), 16));
        }
        return byteLog;
    }

    public static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        for (byte b : byteArray) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


}
