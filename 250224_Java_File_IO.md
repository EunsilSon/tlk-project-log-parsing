작성 일자: 2025-02-24
# Java File I/O
# 파일 생성 + 데이터 작성
## FileOutputStream
```java
try {
    FileOutputStream out = new FileOutputStream("C:\\Users\\USER\\Desktop\\out.txt");
    out.close(); // 생략 가능; 자바 프로그램 종료 시 사용한 파일 객체를 자동으로 닫음
} catch (IOException e) {
    e.getStackTrace();
}
``` 
- `close()` : 사용했던 파일을 닫지 않은 상태에서 재사용하려고 할 때 오류가 발생하므로 항상 닫아주는 것이 좋음

```java
try {
    FileOutputStream out = new FileOutputStream("C:\\Users\\USER\\Desktop\\out.txt");

    for (int i = 1; i <= 10; i++) {
        String data = i + "번째 줄입니다.\r\n";
        out.write(data.getBytes());
    }
    
    out.close();
} catch (IOException e) {
    e.getStackTrace();
}
```
- `data.getBytes()` : FileOutputStream에 값을 쓸 때는 byte 단위여야 하므로 String을 **byte 배열로 변환**

## FileWriter
- byte 배열로 변환 할 필요 없이 **바로 문자열 사용 가능**
```java
try {
    FileWriter fw = new FileWriter("C:\\Users\\USER\\Desktop\\out.txt");

    for (int i = 1; i <= 10; i++) {
        String data = i + "번째 줄입니다. 야호\r\n";
        fw.write(data);
    }

    fw.close();
} catch (IOException e) {
    e.getStackTrace();
}
```

## PrintWriter
- `\r\n` 없이 띄어쓰기 가능
```java
try {
    PrintWriter pw = new PrintWriter("C:\\Users\\USER\\Desktop\\out.txt");

    for (int i = 1; i <= 10; i++) {
        String data = i + "번째 줄입니다.";
        pw.println(data);
    }

    pw.close();
} catch (IOException e) {
    e.getStackTrace();
}
```

# 파일 내용 추가
## FileWriter
- 이미 작성된 파일을 **추가 모드**로 열어 내용 작성
```java
try {
    FileWriter fw1 = new FileWriter("C:\\Users\\USER\\Desktop\\out.txt"); // default: false

    for (int i = 1; i <= 10; i++) {
        String data = i + "번째 줄입니다.\r\n";
        fw1.write(data);
    }
    fw1.close();

    FileWriter fw2 = new FileWriter("C:\\Users\\USER\\Desktop\\out.txt", true);

    for (int i = 11; i <= 20; i++) {
        String data = i + "번째 줄입니다.\r\n";
        fw2.write(data);
    }
    fw2.close();
} catch (IOException e) {
    e.getStackTrace();
}   
```

# 파일 읽기
## FileInputStream
- byte 배열을 문자열로 변경할 때는 new String(byte 배열)처럼 사용하여 변경
```java
byte[] b = new byte[1024];
FileInputStream input = new FileInputStream("C:\\Users\\USER\\Desktop\\out.txt");
input.read(b);
System.out.println(new String(b));
input.close();
```

## FileReader + BufferedReader = 한 줄 단위로 읽기
```java
BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\USER\\Desktop\\out.txt"));
while(true) {
    String line = br.readLine();
    if (line == null) {
        break;  // 더 이상 읽을 라인이 없을 경우 while문 종료
    }
    System.out.println(line);
}
br.close();
```