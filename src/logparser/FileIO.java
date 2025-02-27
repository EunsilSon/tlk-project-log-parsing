package logparser;

import exception.CustomException;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class FileIO {
    Logger logger = Logger.getLogger("DataParserLogger");

    public void read(String logSrc, List<String> hexLogList) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(logSrc));

            boolean fileIsEmpty = true;
            while (true) {
                String line = br.readLine();
                if (line == null || line.isBlank()) {
                    if (fileIsEmpty) {
                        throw new CustomException("빈 파일입니다.");
                    }
                    break; // 예외 던지고 while 종료
                }
                hexLogList.add(line);
                fileIsEmpty = false;
            }

            br.close();
        } catch (IOException | CustomException e) {
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

    public String[] getDiskSpace() {
        File root;
        try {
            root = new File("/");
            String[] list = new String[2];
            list[0] = toMB(root.getTotalSpace());
            list[1] = toMB(root.getUsableSpace());
            return list;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    public String toMB(long size) {
        return String.valueOf((int) (size / (1024 * 1024)));
    }
}