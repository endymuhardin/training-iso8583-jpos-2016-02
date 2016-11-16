package com.muhardin.endy.belajar.jpos.controller;

import com.muhardin.endy.belajar.jpos.dao.MutasiDao;
import com.muhardin.endy.belajar.jpos.dao.RekeningDao;
import com.muhardin.endy.belajar.jpos.entity.Mutasi;
import com.muhardin.endy.belajar.jpos.entity.Rekening;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BackendApiController {
    
    private static final String DATE_FORMAT_BIT_7 = "MMddHHmmss";
    private static final String DATE_FORMAT_BIT_12 = "HHmmss";
    private static final String DATE_FORMAT_BIT_13 = "MMdd";
    
    @Autowired private RekeningDao rekeningDao;
    @Autowired private MutasiDao mutasiDao;
    
    @Value("${isoserver.host}") String host;
    @Value("${isoserver.port}") Integer port;
    
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
        m.setRekening(r);
        r.setSaldo(r.getSaldo().add(m.getNilai()));
        rekeningDao.save(r);
        mutasiDao.save(m);
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");
    }
    
    @RequestMapping(value = "/rekening/{nomor}/inquiry/", method = RequestMethod.POST)
    public ResponseEntity<String> inquiry(@PathVariable("nomor") Rekening r, @RequestParam("tujuan") String tujuan){
        if(r == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nomor Rekening tidak valid");
        }
        
        LocalDateTime sekarang = LocalDateTime.now();
        
        String bit7 = sekarang.format(DateTimeFormatter.ofPattern(DATE_FORMAT_BIT_7));
        String bit12 = sekarang.format(DateTimeFormatter.ofPattern(DATE_FORMAT_BIT_12));
        String bit13 = sekarang.format(DateTimeFormatter.ofPattern(DATE_FORMAT_BIT_13));
        
        String bit11 = String.format("%6s", generateStan()).replace(' ', '0');
        String lengthRekeningTujuan = String.format("%2s", tujuan.length()).replace(' ', '0');
        
        StringBuilder isomsg = new StringBuilder("0200E23A400A00000000000000000200000003001341000");
        isomsg.append(bit7);
        isomsg.append(bit11);
        isomsg.append(bit12);
        isomsg.append(bit13);
        isomsg.append(bit13);
        isomsg.append("6012C00000000C00000000");
        isomsg.append(lengthRekeningTujuan);
        isomsg.append(tujuan);
        
        System.out.println("ISO MSG Inquiry Request : "+isomsg);
        
        try {
            String response = isoRequest(isomsg.toString());
            
            // todo : parse dulu bit 39, handle errornya
            String responseCode = response.substring(100,102);
            System.out.println("Response code : "+responseCode);
            
            String nama = response.substring(108);
            return ResponseEntity.status(HttpStatus.OK).body("Nama : "+nama);
        } catch (Exception err){
            err.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }
    
    @RequestMapping(value = "/rekening/{nomor}/transfer/", method = RequestMethod.POST)
    public ResponseEntity<String> transfer(@PathVariable("nomor") Rekening r, 
            @RequestParam("tujuan") String tujuan, 
            @RequestParam("nilai") BigDecimal nilai){
     
        if(r == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nomor Rekening tidak valid");
        }
        
        LocalDateTime sekarang = LocalDateTime.now();
        
        String bit4 = String.format("%12s", nilai.toString()).replace(' ', '0');
        
        String bit7 = sekarang.format(DateTimeFormatter.ofPattern(DATE_FORMAT_BIT_7));
        String bit12 = sekarang.format(DateTimeFormatter.ofPattern(DATE_FORMAT_BIT_12));
        String bit13 = sekarang.format(DateTimeFormatter.ofPattern(DATE_FORMAT_BIT_13));
        
        String bit11 = String.format("%6s", generateStan()).replace(' ', '0');
        
        String lengthRekeningAsal = String.format("%2s", r.getNomor().length()).replace(' ', '0');
        String lengthRekeningTujuan = String.format("%2s", tujuan.length()).replace(' ', '0');
        
        StringBuilder isomsg = new StringBuilder("0200F23A400A00000000000000000600000003001411000");
        isomsg.append(bit4);
        isomsg.append(bit7);
        isomsg.append(bit11);
        isomsg.append(bit12);
        isomsg.append(bit13);
        isomsg.append(bit13);
        isomsg.append("6012C00000000C00000000");
        
        isomsg.append(lengthRekeningAsal);
        isomsg.append(r.getNomor());
        isomsg.append(lengthRekeningTujuan);
        isomsg.append(tujuan);
        
        System.out.println("ISO MSG Transfer Request : "+isomsg);
        
        try {
            String response = isoRequest(isomsg.toString());
            String responseCode = response.substring(111, 113);
            System.out.println("Response Code : "+responseCode);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception err){
            err.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }
    
    private Integer generateStan(){
        return new Random().nextInt(999999);
    }
    
    private String isoRequest(String message) throws Exception {
        String length = String.format("%4s", message.length()).replace(' ', '0');
        
        Socket clientSocket = new Socket(host, port);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        System.out.println("Sending : "+length+message);
        
        out.println(length + message);
        
        char[] responseLengthChar = new char[4];
        in.read(responseLengthChar);
        
        String responseLengthStr = new String(responseLengthChar);
        System.out.println("Response length : "+responseLengthStr);
        
        Integer responseLength = Integer.valueOf(responseLengthStr);
        char[] responseDataChar = new char[responseLength];
        in.read(responseDataChar);
        String responseData = new String(responseDataChar);
        
        System.out.println("Response Data : "+responseData);
        
        out.close();
        in.close();
        clientSocket.close();
        
        return responseData;
    }
}
