package com.muhardin.endy.belajar.jpos.controller;

import com.muhardin.endy.belajar.jpos.dao.MutasiDao;
import com.muhardin.endy.belajar.jpos.dao.RekeningDao;
import com.muhardin.endy.belajar.jpos.entity.Mutasi;
import com.muhardin.endy.belajar.jpos.entity.Rekening;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BackendApiController {
    
    @Autowired private RekeningDao rekeningDao;
    @Autowired private MutasiDao mutasiDao;
    
    @RequestMapping("/rekening/")
    public Page<Rekening> semuaRekening(Pageable page){
        return rekeningDao.findAll(page);
    }
    
    @RequestMapping("/rekening/{nomor}/")
    public Rekening semuaRekening(@PathVariable String nomor){
        return rekeningDao.findByNomor(nomor);
    }
    
    @RequestMapping("/rekening/{nomor}/mutasi/")
    public Page<Mutasi> mutasiRekening(@PathVariable("nomor") String nomor, Pageable page){
        return mutasiDao.findByRekening(rekeningDao.findByNomor(nomor), page);
    }
    
    @RequestMapping(value = "/rekening/{nomor}/", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<String> tambahMutasi(@RequestBody @Valid Mutasi m, @PathVariable("nomor") String nomor){
        Rekening r = rekeningDao.findByNomor(nomor);
        if(r == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nomor Rekening tidak valid");
        }
        m.setId(null);
        r.setSaldo(r.getSaldo().add(m.getNilai()));
        rekeningDao.save(r);
        mutasiDao.save(m);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
}
