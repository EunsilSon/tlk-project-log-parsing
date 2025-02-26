package dataParser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final String LOG_SRC = "C:\\Users\\USER\\Desktop\\logs.txt";
        final String RESULT_LOG_SRC = "C:\\Users\\USER\\Desktop\\log_parsing_result.txt";

        Main main = new Main();
        main.logDataFileParsing(LOG_SRC, RESULT_LOG_SRC);
    }

    public void logDataFileParsing(String LOG_SRC, String RESULT_LOG_SRC) {
        File file = new File();
        Parsing parsing = new Parsing();

        List<String> hexLogs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        /* 1. 파일 읽기 */
        file.read(LOG_SRC, hexLogs);

        /* 2. 바이트 단위 파싱 */
        for (String hexLog : hexLogs) {
            List<String> splitHexLog = parsing.splitHexStrByByte(hexLog);
            sb.setLength(0);
            String parsingResult = parsing.typeCasting(splitHexLog, sb);

            /* 3. 최종 데이터 파일에 출력 */
            file.write(RESULT_LOG_SRC, parsingResult);
        }
    }
}
