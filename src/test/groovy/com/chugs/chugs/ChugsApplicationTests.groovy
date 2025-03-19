package com.chugs.chugs

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest

class ChugsApplicationTests extends Specification {

	void contextLoads() {
		expect: "the application context loads successfully"
		true
	}

}
