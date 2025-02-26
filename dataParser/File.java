package dataParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class File {
    Logger logger = Logger.getLogger("DataParserLogger");

    public void read(String logSrc, List<String> hexLogList) {
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
            logger.warning(ioException.getMessage());
        }
    }

    public void write(String RESULT_LOG_SRC, String parsingResult) {
        try {
            FileWriter fw = new FileWriter(RESULT_LOG_SRC, true); // default: false
            fw.write(parsingResult + "\r\n");
            fw.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
