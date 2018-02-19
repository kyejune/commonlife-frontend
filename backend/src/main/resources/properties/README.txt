Properties 구조

/properties 경로 아래에 다수의 폴더가 위치합니다.
먼저, _iok_ 폴더에 포함된 프로퍼티 파일은 IOK 마스터 서버에서 가져온 것 입니다. 마스터 서버와의 통합으로 인해 포함되었습니다.
그리고, 나머지 폴더는 각각 사용자 또는 시스템 별로 사용자가 지정한 프로퍼티가 위치합니다.
_iok_(IOK 마스터 서버) 프로퍼티는 공통으로 사용됩니다. 공통으로 사용되는 예외 메시지가 정의되어 있으므로 항상 로딩해야 합니다.
나머지 폴더의 프로퍼티는 context 생성시(구동시), 사용자가 선택할 수 있습니다. Java 옵션에 스프링 프로필로 이름을 설정하여 로딩할 수 있습니다.
프로필 설정을 위해 "-Dspring.profiles.active" 옵션을 이용하시기 바랍니다.

예제)
$ # /properties/dev 폴더의 프로퍼티를 로딩하게 됩니다.
$ export JAVA_OPTS=-Dspring.profiles.active=dev
$ tomcat/bin/startup.sh


자세한 내용은 context-common.xml을 참고하시기 바랍니다.