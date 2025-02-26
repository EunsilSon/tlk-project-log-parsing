import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataParsing {
    public static void main(String[] args) {
        final String LOG_SRC = "C:\\Users\\USER\\Desktop\\logs.txt";
        final String RESULT_LOG_SRC = "C:\\Users\\USER\\Desktop\\log_parsing_result.txt";
        List<String> hexLogList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        // 파일 읽기
        readFile(LOG_SRC, hexLogList);

        //for (int i = 0; i < hexLogList.size(); i++) {
            // hex 문자열 -> byte 배열
            List<String> byteLogList = splitHexStringByByte(hexLogList.get(0));

            // 자른 바이트 파싱
            sb.setLength(0);
            String parsingResult = parsingHexData(byteLogList, sb);

            // 최종 파일 작성
            writeFile(RESULT_LOG_SRC, parsingResult);
        //}
    }

    public static void readFile(String logSrc, List<String> hexLogList) {
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

    public static List<String> splitHexStringByByte(String hexLog) {
        //for (String hexLog : hexLogList) {
            byte[] byteLog = hexStringToByteArray(hexLog);
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
        //}
        return hexStringList;
    }

    public static byte[] hexStringToByteArray(String hexLog) {
        int length = hexLog.length();
        byte[] byteLog = new byte[length / 2]; // 16진수 문자열의 한 문자는 4bit, 8bit = 1byte 따라서 나누기 2를 함

        for (int i = 0; i < length; i += 2) {
            byteLog[i / 2] = (byte) ((Character.digit(hexLog.charAt(i), 16) << 4)
                    + Character.digit(hexLog.charAt(i + 1), 16)); // 문자 2개씩 변환
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

    public static String parsingHexData(List<String> byteLogList, StringBuilder sb) {
        sb.append("'" + byteLogList.get(0) + "'").append(", ");

        sb.append(Integer.parseInt(byteLogList.get(1), 16)).append(", ");

        int data3 = Integer.parseInt(byteLogList.get(2), 16);
        sb.append("'" + millisToDate(data3) + "'").append(", ");

        int data4 = Integer.parseInt(byteLogList.get(3), 16);
        sb.append((float) data4 / 10).append(", ");

        int data5 = Integer.parseInt(byteLogList.get(4), 16);
        sb.append((float) data5 / 10).append(", ");

        int data6 = Integer.parseInt(byteLogList.get(5), 16);
        sb.append((float) data6 / 10).append(", ");

        sb.append(Integer.parseInt(byteLogList.get(6), 16));

        return sb.toString();
    }

    public static String millisToDate(long millis) {
        long millisTime = millis * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +SS:SS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date timeInDate = new Date(millisTime);
        return sdf.format(timeInDate);
    }

    public static void writeFile(String resultSrc, String parsingResult) {
        try {
            PrintWriter pw = new PrintWriter(resultSrc);
            pw.println(parsingResult);
            pw.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


}
