package logparser;

import exception.ParsingLogException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class Main {
    Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Input Log File Source: ");
        String logSrc = br.readLine();

        Main main = new Main();
        main.parseHexLogAndWriteToFile(logSrc);
    }

    private void parseHexLogAndWriteToFile(String logSrc) {
        try {
            FileIO fileIO = new FileIO();
            HexByteConverter hexByteConverter = new HexByteConverter();
            Parser parser = new Parser();

            List<String> hexLogs = new ArrayList<>();
            fileIO.read(logSrc, hexLogs); // 1. 파일 읽기

            for (String hexLog : hexLogs) {
                List<String> hexByteList = hexByteConverter.splitHexByByte(hexLog); // 2. 바이트 단위 분할
                fileIO.write(parser.parseHexString(hexByteList)); // 3. 파싱 + 파일 출력
            }
            logger.info("Success parsing hex log");
        } catch (IOException | ParsingLogException e) {
            logger.warning(e.getMessage());
        }
    }

}