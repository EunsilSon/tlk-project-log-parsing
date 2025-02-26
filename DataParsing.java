import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataParsing {
    public static void main(String[] args) {
        final String LOG_SRC = "C:\\Users\\USER\\Desktop\\logs.txt";
        final String RESULT_LOG_SRC = "C:\\Users\\USER\\Desktop\\log_parsing_result.txt";
        List<String> hexLogs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        /* 1. 파일 읽기 */
        readFile(LOG_SRC, hexLogs);

        /* 2. 바이트 단위 파싱 */
        for (String log : hexLogs) {
            List<String> splitHexLog = splitHexStrByByte(log);
            sb.setLength(0);
            String parsingResult = parsingHexData(splitHexLog, sb);

            /* 3. 최종 파일 생성 */
            writeFile(RESULT_LOG_SRC, parsingResult);
        }
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

    public static void writeFile(String RESULT_LOG_SRC, String parsingResult) {
        try {
            FileWriter fw = new FileWriter(RESULT_LOG_SRC, true); // default: false
            fw.write(parsingResult + "\r\n");
            fw.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static List<String> splitHexStrByByte(String hexLog) {
        byte[] byteLog = hexStrToByteArr(hexLog); // byte 배열을 N byte 만큼 자르기 + hex 변환
        List<String> hexStringList = new ArrayList<>();
        int[] byteInfo = {2, 2, 4, 2, 2, 1, 1};
        int srcPos = 0;

        for (int byteNum : byteInfo) {
            byte[] divideArr = new byte[byteNum];
            System.arraycopy(byteLog, srcPos, divideArr, 0, byteNum);
            hexStringList.add(byteArrayToHexString(divideArr));
            srcPos += byteNum;
        }
        return hexStringList;
    }

    public static byte[] hexStrToByteArr(String hexLog) {
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

    public static String parsingHexData(List<String> splitHexLog, StringBuilder sb) {
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

    public static String millisToDate(long millis) {
        long millisTime = millis * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +SS:SS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date timeInDate = new Date(millisTime);
        return sdf.format(timeInDate);
    }

    public static int hexToSignedInt(String hex) {
        int num = Integer.parseInt(hex, 16);

        if ((num & 0x8000) != 0) {
            num = num - (1 << 16);
        }

        return num;
    }
}