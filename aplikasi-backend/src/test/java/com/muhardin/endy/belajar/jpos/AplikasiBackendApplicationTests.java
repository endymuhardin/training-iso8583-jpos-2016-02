package com.muhardin.endy.belajar.jpos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(
        scripts = {"/data/clean.sql", "/data/rekening.sql", "/data/mutasi.sql"}
)
public class AplikasiBackendApplicationTests {

	@Test
	public void contextLoads() {
	}

}
