# Go #2

공공 데이터 포털의 [전국공중화장실표준데이터](https://www.data.go.kr/subMain.jsp?param=REFUQUdSSURAMTUwMTI4OTI=#/L2NvbW0vY29tbW9uU2VhcmNoL2RhdGFzZXREZXRhaWwkQF4wMTJtMSRAXnB1YmxpY0RhdGFQaz0xNTAxMjg5MiRAXmJybUNkPU9DMDAwMyRAXm9yZ0luZGV4PURBVEFTRVQ=)에 대해 사각형 영역을 인자로 하여 해당 영역 내에 포함되는 공중화장실을 찾는 RESTful API를 구현한다.
 
## 알고리즘

* 공간 검색을 위한 알고리즘은 Antonin Guttman이 저술한 [R-Tree](http://www.cs.uml.edu/~cchen/580-S06/reading/Gut84.pdf)을 참고하여 구현하였다.
* 알고리즘 내에서 M=4, m=2를 사용한다.
* 알고리즘의 구현 중 Quadratic-split의 경우, [davidmoten/rtree](https://github.com/davidmoten/rtree)의 SplitterQuadratic.java를 일부 참고하였다.
* 구현은 JTS에 의존성을 가지며, MBR(Minimal Bounding Rectangle)와 Point 등의 구현을 빌리기 위해 사용하였다.

## RESTful API 구현

* RESTful API를 제공하기 위해 Spring Framework를 사용한다.
* Stand-alone을 만족시키기 위해 spring-boot를 사용한다.
* 임베디드 H2를 사용하며, persistence 제공을 위해 JPA 기반의 QueryDSL를 사용한다(H2의 데이터 파일은 data 디렉토리 내에 존재한다).
  
## 빌드

`gradle build`

## RESTful API

검색 API의 URL은 `/toilet/[사각형 좌표]`다.

* [사각형 좌표]는 (좌표1의 경도),(좌표1의 위도),(좌표2의 경도),(좌표2의 위도)로 이루어지며, path-variable이다.
* 경도와 위도는 모두 `Double` 타입을 사용하므로, 경도와 위도는 floating-point로 표현되어야 한다.
* 예를 들어, `/toilet/127.172,37.53895,127.176,37.571`로 서울 강동구 일대의 공중 화장실을 검색할 수 있다.
