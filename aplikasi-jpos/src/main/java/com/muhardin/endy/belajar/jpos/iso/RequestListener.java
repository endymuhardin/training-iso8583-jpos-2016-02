package com.muhardin.endy.belajar.jpos.iso;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.springframework.web.client.RestTemplate;

public class RequestListener implements ISORequestListener {

    public boolean process(ISOSource sender, ISOMsg request) {
        try {
            String mti = request.getMTI();
            if ("0800".equals(mti)) {
                ISOMsg response = (ISOMsg) request.clone();
                response.setMTI("0810");
                response.set(39, "00");
                sender.send(response);
                return true;
            }
            
            if("0200".equals(mti)){
                
                String processingCode = request.getString(3);
                
                // todo : handle bila processing code kosong atau null
                
                if(processingCode.startsWith("34")) {
                    ISOMsg response = (ISOMsg) request.clone();
                    response.setMTI("0210");

                    String nomorAkun = request.getString(102);
                    RestTemplate httpClient = new RestTemplate();
                    Map<String, Object> hasil = httpClient.getForObject("https://pelatihan-backend.herokuapp.com/api/rekening/"+nomorAkun+"/", HashMap.class);

                    response.set(39, "00");
                    response.set(104, hasil.get("nama").toString());
                    response.set(4, hasil.get("saldo").toString());

                    sender.send(response);
                    return true;
                }
                
                if(processingCode.startsWith("41")) {
                    ISOMsg response = (ISOMsg) request.clone();
                    response.setMTI("0210");

                    String nilaiTopup = request.getString(4);
                    String rekeningAsal = request.getString(102);
                    String rekeningTujuan = request.getString(103);
                    
                    Map<String, Object> rekening = new HashMap<>();
                    rekening.put("nomor", rekeningTujuan);
                    
                    Map<String, Object> data = new HashMap<>();
                    data.put("rekening", rekening);
                    data.put("waktuTransaksi", LocalDateTime.now());
                    data.put("nilai", new BigDecimal(nilaiTopup));
                    data.put("keterangan", "Topup dari "+rekeningAsal);
                    
                    RestTemplate httpClient = new RestTemplate();
                    String hasil = httpClient.postForObject("https://pelatihan-backend.herokuapp.com/api/rekening/"+rekeningTujuan+"/",
                            data, 
                            String.class);
                    
                    if("OK".equalsIgnoreCase(hasil.trim())) {
                        response.set(39, "00");
                    } else {
                        response.set(39, "14"); // rekening tujuan invalid
                    }
                    
                    // TODO : handle kasus error lainnya, backend harus diimprove supaya mengeluarkan berbagai error type

                    sender.send(response);
                    return true;
                }
                
                // todo : handle bila processing code tidak ada yang sesuai dengan if di atas
                
                return false;
            }
            
            return false;
        } catch (Exception ex) {
            Logger.getLogger(RequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
