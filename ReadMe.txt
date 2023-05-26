[마이바티스 SQL과 VO 객체 매핑 프레임워크]

1. 마이바티스 구성요소
 - sqlSession / sqlSessionTemplate
 - 매퍼.xml : 쿼리문
 - 매퍼 인터페이스 : Service layer와 매퍼.xml연결해주는 역할
 - SqlMapConfig.xml : 마이바티스 환경설정파일(root-context.xml 사용추세)
 -  

1. 프로젝트 생성
 - chap41_sql_log 복사해서

2. Dao단 개편

 1) BoardMapperInterface 인터페이스 생성
  - BoardDao에서 인터페이스 부분 추출(메소드 시그너처만 분리)
 
 2) 매퍼 XML(쿼리문) 보관 파일
 
  - resource / mapper 폴더 생성
  - BoardMapper.xml 생성
  - 다음 부분 추가
    <!DOCTYPE mapper
		 PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		 "http://mybatis.org/dtd/mybatis-3-mapper.dtd"> 
	 
 2) 매퍼 XML 본격 편집
  	(1) 먼저 이 부분으로 대강의 윤곽을 잡는다.
  	
	<mapper namespace="com.javalab.logging.dao.BoardMapperInterface">
	
		<!-- 게시물 리스트 -->
		<select id="" resultType="BoardVo"> 
		</select>
	
		<!-- 게시물 한개(상세보기) -->
		<select id="" parameterType="int" resultType="BoardVo"> 
		</select>
	
		<!-- 게시물 등록 -->
		<insert id = "" parameterType="BoardVo">
		</insert>
	
		<!-- 게시물 수정 -->
		<update id = "" parameterType="BoardVo"> 
		</update>	
		
		<!-- 게시물 삭제 -->
		<delete id = "" parameterType="int"> <!-- deleteDao -->
		</delete>
	</mapper>	 	  
	
	(2) 쿼리문하나가 하나의 메소드와 같다. 메소드는 유일 id값을 갖는다
		인터페이스에서 메소드명을 복사해서 "" 안에 넣는다. 
	
		<!-- 게시물 리스트 -->
		<select id="selectBoardList" resultType="BoardVo"> 
		</select>
	
		<!-- 게시물 한개(상세보기) -->
		<select id="getBoardById" parameterType="int" resultType="BoardVo"> 
		</select>
	
		<!-- 게시물 등록 -->
		<insert id = "insertBoard" parameterType="BoardVo">
		</insert>
	
		<!-- 게시물 수정 -->
		<update id = "modifyBoard" parameterType="BoardVo"> 
		</update>	
		
		<!-- 게시물 삭제 -->
		<delete id = "deleteBoard" parameterType="int"> <!-- deleteDao -->
		</delete>

	(3) 쿼리문 넣기
	- BoardDao에서 쿼리문을 추출해서 쿼리 메소드 내에 삽입

    (4) BoardServiceImpl 수정
    //private BoardDao boardDao; 의존성 주입 주석처리하고 다음 매퍼인터페이스 의존성 주입
    private BoardMapperInterface boardDao;	// BoardDao 객체 자동주입
 
 
3. pom.xml 의존성 추가

 [주의] pom.xml을 전체를 아예 제공할것.
 
 	[Mybatis 관련 의존성]
	<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
	<dependency>
	    <groupId>org.mybatis</groupId>
	    <artifactId>mybatis</artifactId>
	    <version>3.5.6</version>
	</dependency>
	<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
	<dependency>
	    <groupId>org.mybatis</groupId>
	    <artifactId>mybatis-spring</artifactId>
	    <version>2.0.6</version>
	</dependency>

4. 	web.xml에 한글 인코딩 부분 추가
	web.xml에 한글 인코딩 필터 추가
    <!-- 한글 인코딩 필터 -->
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter
		</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

		
5. root-context.xml에 환경설정 수정
 - 경로 확인 철저	
	<!-- 마이바티스 관련 설정 사항 추가 -->
	
	<mybatis-spring:scan base-package="com.javalab.mybatis.dao"
						annotation="org.apache.ibatis.annotations.Mapper"/>

	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<property name="dataSource" ref="dataSource"/>
 		<!-- 매퍼.xml(SQL쿼리문) 위치 설정 --> 
		<property name="mapperLocations" value="classpath:mapper/*Mapper.xml" />
		<!-- SQL문 실행결과를 받아오거나 파라미터 전달할 때 사용할 vo들의 위치 -->
		<property name="typeAliasesPackage" value="com.javalab.mybatis.domain"></property>
		<!-- vo의 필드명과 SQL문의 select절의 컬럼명이 다를 경우의 처리 -->
		<property name="configuration">
			<bean class="org.apache.ibatis.session.Configuration">
				<property name="mapUnderscoreToCamelCase" value="true"/>
			</bean>
		</property>
	</bean>	
	
	[주의] 추가하면 <mybatis-spring:scan>태그 오류남
	 -> Namespace 추가할것.
	
6. servlet-context.xml	

7. BoardDao 삭제


9. 단위테스트
 - 혹시 단위테스트 오류나면 전체 실행후 단위테스트 할것.
 
 

8. database.properties 파일 수정
 - borad계정으로 변경 
 	
9. 로그 파일도 확인 

		