package logparser;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class Parser {
    Logger logger = Logger.getLogger(Parser.class.getName());

    public String parseHexString(List<String> hexByteList) {
        StringBuilder result = new StringBuilder();
        try {
            wrapWithQuotes(result, hexByteList.get(0));
            addDelimiter(result);

            result.append(String.format("%03d", Integer.parseInt(hexByteList.get(1), 16)));
            addDelimiter(result);

            wrapWithQuotes(result, millisToDate(Integer.parseInt(hexByteList.get(2), 16)));
            addDelimiter(result);

            short signedInt = (short) Integer.parseInt(hexByteList.get(3), 16);
            result.append(String.format("%4.1f", (float) signedInt / 10));
            addDelimiter(result);

            result.append((float) Integer.parseInt(hexByteList.get(4), 16) / 10);
            addDelimiter(result);

            result.append((float) Integer.parseInt(hexByteList.get(5), 16) / 10);
            addDelimiter(result);

            result.append(Integer.parseInt(hexByteList.get(6), 16));
        } catch (NumberFormatException e) {
            logger.warning("Number Format Exception " + e.getMessage());
        }
        return result.toString();
    }

    private String millisToDate(long millis) {
        long millisTime = millis * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss +SS:SS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date timeInDate = new Date(millisTime);
        return sdf.format(timeInDate);
    }

    private void wrapWithQuotes(StringBuilder sb, String str) {
        String quote = "'";
        sb.append(quote).append(str).append(quote);
    }

    private void addDelimiter(StringBuilder sb) {
        sb.append(", ");
    }
}
