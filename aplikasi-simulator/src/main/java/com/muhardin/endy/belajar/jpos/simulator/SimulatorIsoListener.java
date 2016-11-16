package com.muhardin.endy.belajar.jpos.simulator;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;

public class SimulatorIsoListener implements ISORequestListener{

    @Override
    public boolean process(ISOSource sender, ISOMsg request) {
        try {
            
            String mti = request.getMTI();
            String processingCode = request.getString(3);
            
            ISOMsg response = (ISOMsg) request.clone();
            response.setMTI("0210");
            
            // handle account inquiry
            if(processingCode.startsWith("34")) {
                response.set(104, "Dummy Account");
                
                String rekeningTujuan = request.getString(103);
                if("123".equals(rekeningTujuan)) {  // happy scenario
                    response.set(39, "00");
                } else if ("456".equals(rekeningTujuan)) {  // invalid account
                    response.set(39, "14");
                } else if ("789".equals(rekeningTujuan)) {  // late response
                    Thread.sleep(40 * 1000);
                    response.set(39, "00");
                } else {
                    response.set(39, "99");  // error lain-lain
                }
            }
            
            // handle transfer
            if(processingCode.startsWith("41")) {
                response.set(104, "Dummy Account");
                
                String rekeningTujuan = request.getString(103);
                if("123".equals(rekeningTujuan)) {  // happy scenario
                    response.set(39, "00");
                } else if ("456".equals(rekeningTujuan)) {  // invalid account
                    response.set(39, "14");
                } else if ("789".equals(rekeningTujuan)) {  // late response
                    Thread.sleep(40 * 1000);
                    response.set(39, "00");
                } else {
                    response.set(39, "99");  // error lain-lain
                }
            }
            
            sender.send(response);
            
        } catch (Exception err){
            err.printStackTrace();
        }
        
        return true;
    }
    
}
