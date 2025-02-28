package logparser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class FileIO {
    Logger logger = Logger.getLogger(FileIO.class.getName());

    public void read(String logSrc, List<String> hexLogList) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(logSrc));

        boolean fileIsEmpty = true;
        while (true) {
            String line = br.readLine();
            if (line == null || line.isBlank()) {
                if (fileIsEmpty) {
                    throw new FileNotFoundException("Empty File");
                }
                break;
            } else {
                hexLogList.add(line);
                fileIsEmpty = false;
            }
        }
        br.close();
    }

    public void write(String parsingResultStr) {
        try {
            FileWriter fw = new FileWriter(getResultFileSrcAndName(), true); // true = 수정 모드
            fw.write(parsingResultStr + "\r\n");
            fw.close();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    public String getResultFileSrcAndName() {
        String dir = "result";
        String path = System.getProperty("user.dir")
                + File.separator
                + dir
                + File.separator;
        String name = String.format("log_result_%s.txt", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm")));

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        return path + name;
    }
}