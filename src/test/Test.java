package test;

import exception.ParsingLogException;
import logparser.FileIO;
import logparser.HexByteConverter;
import logparser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Test {
    static Logger logger = Logger.getLogger(Test.class.getName());

    public static void main(String[] args) throws ParsingLogException {
        fileInputTest();
        filePathTest();
        hexStrToByteArrTest();
        parsingTest();
    }

    private static void fileInputTest() {
        String logSrc = "C:\\Users\\USER\\Desktop\\not_existed.txt";
        FileIO fileIO = new FileIO();
        try {
            fileIO.read(logSrc, new ArrayList<>());
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private static void filePathTest() {
        FileIO fileIO = new FileIO();
        logger.info("최종 파일 생성 경로: " + fileIO.getResultFileSrcAndName());
    }

    private static void hexStrToByteArrTest() throws ParsingLogException {
        String wrongHex = "0004000164eea31a010f01dc005f000x";
        String shortHex = "0004000164eea31a010f01dc005f000";

        HexByteConverter hexByteConverter = new HexByteConverter();
        hexByteConverter.splitHexByByte(wrongHex);
        //hexByteConverter.splitHexByByte(shortHex);
    }

    private static void parsingTest() {
        List<String> testList = new ArrayList<>();
        testList.add("number"); // hex string -> string
        testList.add("number"); // integer -> integer
        testList.add("number"); // long * 1000  -> millisecond to date
        testList.add("number"); // signed integer / 10 -> float
        testList.add("number"); // integer / 10 -> float
        testList.add("number"); // integer / 10 -> float
        testList.add("number"); // integer -> integer

        Parser p = new Parser();
        p.parseHexString(testList);
    }
}
