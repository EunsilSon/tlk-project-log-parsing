package logparser;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class FileIO {
    Logger logger = Logger.getLogger("DataParserLogger");

    public void read(String logSrc, List<String> hexLogList) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(logSrc));

            while (true) {
                String line = br.readLine();
                if (line != null) {
                    hexLogList.add(line);
                } else {
                    break;
                }
            }
            br.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public void write(String resultLogSrc, String parsingResultStr) {
        try {
            FileWriter fw = new FileWriter(resultLogSrc, true); // true = 수정 모드
            fw.write(parsingResultStr + "\r\n");
            fw.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
