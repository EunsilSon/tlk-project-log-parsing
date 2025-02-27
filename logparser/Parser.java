package logparser;

import java.text.SimpleDateFormat;
import java.util.*;

public class Parser {
    public String parsing(List<String> splitHexLog, StringBuilder sb) {
        wrapWithQuotes(sb, splitHexLog.get(0));
        addDelimiter(sb);

        sb.append(Integer.parseInt(splitHexLog.get(1), 16));
        addDelimiter(sb);

        wrapWithQuotes(sb, millisToDate(Integer.parseInt(splitHexLog.get(2), 16)));
        addDelimiter(sb);

        sb.append((float) hexToSignedInt(splitHexLog.get(3)) / 10);
        addDelimiter(sb);

        sb.append((float) Integer.parseInt(splitHexLog.get(4), 16) / 10);
        addDelimiter(sb);

        sb.append((float) Integer.parseInt(splitHexLog.get(5), 16) / 10);
        addDelimiter(sb);

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
        if ((num & 0x8000) != 0) { // 최상위 비트
            num = num - (1 << 16);
        }

        return num;
    }

    public void wrapWithQuotes(StringBuilder sb, String str) {
        String quote = "'";
        sb.append(quote).append(str).append(quote);
    }

    public void addDelimiter(StringBuilder sb) { // 구분자 지정
        sb.append(", ");
    }
}
