Admin Properties 구조


Java 옵션에 스프링 프로필로 이름을 설정하여 로딩할 수 있습니다.
프로필 설정을 위해 "-Dspring.profiles.active" 옵션을 이용하시기 바랍니다.

예제)
$ # /properties/dev 폴더의 프로퍼티를 로딩하게 됩니다.
$ export JAVA_OPTS=-Dspring.profiles.active=dev
$ tomcat/bin/startup.sh


자세한 내용은 context-common.xml을 참고하시기 바랍니다.