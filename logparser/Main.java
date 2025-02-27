package logparser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // TODO: 파일명 입력 받기 (경로 포함/미포함 따로)
        // TODO: 결과 파일명에 datetime(현재시간) 추가 (결과 파일 경로: result 폴더)
        final String LOG_SRC = "C:\\Users\\USER\\Desktop\\logs.txt";
        final String RESULT_LOG_SRC = "C:\\Users\\USER\\Desktop\\250227_soneunsil_logs_result.txt";

        Main main = new Main();
        main.logDataFileParsing(LOG_SRC, RESULT_LOG_SRC);
    }

    public void logDataFileParsing(String LOG_SRC, String RESULT_LOG_SRC) {
        File file = new File();
        HexByteConverter hexByteConverter = new HexByteConverter();
        Parser parser = new Parser();

        List<String> hexLogs = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        file.read(LOG_SRC, hexLogs); // 1. 파일 읽기

        for (String hexLog : hexLogs) {
            List<String> hexByteList = hexByteConverter.splitHexByByte(hexLog); // 2. 바이트 단위 파싱
            file.write(RESULT_LOG_SRC, parser.parsing(hexByteList, sb)); // 3. 최종 데이터 파일 출력
            sb.setLength(0);
        }
    }
}
