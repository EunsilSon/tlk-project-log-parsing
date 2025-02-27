package logparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("불러올 파일의 절대 경로와 파일명을 함께 입력하세요."); // C:\Users\USER\Desktop\logs.txt
        String logSrc = br.readLine();

        Main main = new Main();
        main.parseHexLogAndWriteToFile(logSrc, main.getResultFileSrcAndName());
    }

    private String getResultFileSrcAndName() {
        String path = System.getProperty("user.dir") + "\\result\\";
        String name = String.format("log_result_%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm")));

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        return path + name;
    }

    private void parseHexLogAndWriteToFile(String logSrc, String resultLogSrc) {
        FileIO fileIO = new FileIO();
        HexByteConverter hexByteConverter = new HexByteConverter();
        Parser parser = new Parser();

        List<String> hexLogs = new ArrayList<>();
        fileIO.read(logSrc, hexLogs); // 1. 파일 읽기

        for (String hexLog : hexLogs) {
            List<String> hexByteList = hexByteConverter.splitHexByByte(hexLog); // 2. 바이트 단위 파싱
            fileIO.write(resultLogSrc, parser.parseHexString(hexByteList)); // 3. 최종 데이터 파일 출력
        }
    }
}
