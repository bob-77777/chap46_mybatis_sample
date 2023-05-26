package com.javalab.logging.datasource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * junit 단위테스트 클래스
 *  - 테스트 클래스는 `BoardDao` 클래스와 동일한 패키지에 위치
 * @Test : 테스트 케이스 작성, 각 메소드가 junit 테스트 클래스임을 명시
 * @Ignore : 이 메소드는 테스트에서 제외
 *  두 개를 같이 쓰면 테스트에서 제외됨(ex. @Test @Ignore)
 * @Before : 테스트를 하기 전에 반드시 거쳐야 하는 부분을 기재
 * assertNotNull(검증 객체) : 괄호 안에 있는 객체가 널이 아닐 것이라고 가정(assert)
 *  - 널이 아니면 true 널이면 false가 됨.
 * @RunWith(SpringJUnit4ClassRunner.class) 
 *	 - 스프링에서 Junit을 사용하기 위해서 스프링 컨테이너 구동(로드)해서
 *	 - @Autowired 와 같은 어노테이션 사용할 수 있게 됨.
 * @ContextConfiguration("classpath:config/root-context.xml")
 *	 - 스프링 컨테이너에서 사용할 빈등록 설정파일 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/root-context.xml")
@Log4j
public class BoardDataSourceTest {

	// DataSource 의존성 주입
	@Autowired
    private DataSource dataSource;

	/*
	 * 핵심 빈(Bean)인 BoardDao 각 정상적으로 의존성 주입되는지 확인
	 */
    @Test @Ignore
    public void testDataSource() {
    	assertNotNull(dataSource);
    }
    
    @Test //@Ignore
    public void testConnection() {
    	try(Connection connection = dataSource.getConnection()){
    		log.info(connection);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
        
}
