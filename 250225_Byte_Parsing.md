# file명 입력
- FileReader + BufferedReader = 한 줄 단위로 읽기
public void readFile() {
    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\USER\\Desktop\\logs.txt"));
    while(true) {
        String line = br.readLine();
        if (line == null) {
            break;  // 더 이상 읽을 라인이 없을 경우 while문 종료
        }
        System.out.println(line);
    }
    br.close();
}

# 읽은 파일 데이터 가공
- 예시 파일은 100개 레코드로 구성
- 레코드는 총 32자(16바이트)로 되어있고, hex값을 각 자리수 및 형식에 맞게 변형 후 translated.txt로 저장
try {
    PrintWriter pw = new PrintWriter("C:\\Users\\USER\\Desktop\\translated.txt");

    for (int i = 1; i <= 10; i++) {
        String data = "결과";
        pw.println(data);
    }

    pw.close();
} catch (IOException e) {
    e.getStackTrace();
}

- 바이트 기준으로
예시 : 0004 0001 64eea31a 010f 01dc 00 5f 0000

1. 2바이트(0004) : hexstring => `string`
2. 2바이트(0001) : integer => `integer`
3. 4바이트(64eea31a) : long * 1000  => `millisecond to date`
4. 2바이트(010f) : signed integer / 10 => `float`
5. 2바이트(01dc) : integer / 10 => `float`
6. 1바이트(00) : integer / 10 => `float`
    - (float) Integer.parseInt("00", 16) / 10
7. 1바이트(5f) : integer => `integer`
    - Integer.parseInt("5f", 16)


- 메인 파싱 메서드에서 8개 순차적으로 호출
    - 1번 메서드는 16진수 -> 바이트 배열로 변환
    - 바이트 배열에서 위에 byteInfo 순서대로 자르기


- 7개 메서드에서 각 타입으로 파싱 + 예외 처리 
바이트 기준으로 잘려지지 않을 때, 받은 로그 값이 잘못됐을 때

# 최종 데이터 예시
'0004', 1, '2023-08-30 02:02:02 +00:00', 27.1, 47.6, 0.0, 95 를 각 로우(열)로 변환해서 기록 후 파일 생성