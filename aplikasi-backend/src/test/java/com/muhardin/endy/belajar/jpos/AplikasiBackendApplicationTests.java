package com.muhardin.endy.belajar.jpos;

import com.muhardin.endy.belajar.jpos.dao.MutasiDao;
import com.muhardin.endy.belajar.jpos.dao.RekeningDao;
import com.muhardin.endy.belajar.jpos.entity.Mutasi;
import com.muhardin.endy.belajar.jpos.entity.Rekening;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(
    scripts = {"/data/clean.sql", "/data/rekening.sql", "/data/mutasi.sql"}
)
public class AplikasiBackendApplicationTests {
    
    @Autowired
    private RekeningDao rekeningDao;
    @Autowired
    private MutasiDao mutasiDao;

	@Test
	public void testCariRekeningByNomor() {
            String nomor = "001";
            Rekening r = rekeningDao.findByNomor(nomor);
            Assert.assertNotNull(r);
            Assert.assertEquals(new BigDecimal("100000.00"), r.getSaldo());
            Assert.assertNull(rekeningDao.findByNomor("999"));
	}
        
        @Test
        public void testCariMutasiByRekening(){
            Rekening r = rekeningDao.findByNomor("001");
            Assert.assertNotNull(r);
            Page<Mutasi> daftarMutasi = mutasiDao.findByRekening(r, new PageRequest(0, 10));
            Assert.assertNotNull(daftarMutasi);
            Assert.assertEquals(Long.valueOf(1), Long.valueOf(daftarMutasi.getTotalElements()));
            Mutasi m = daftarMutasi.getContent().get(0);
            Assert.assertEquals(LocalDateTime.of(2016, Month.NOVEMBER, 1, 8, 0, 0), m.getWaktuTransaksi());
            Assert.assertEquals("Setoran Awal", m.getKeterangan());
            Assert.assertEquals(new BigDecimal("100000.00"), m.getNilai());
        }

}
