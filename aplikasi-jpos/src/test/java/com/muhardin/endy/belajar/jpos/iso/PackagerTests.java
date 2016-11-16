package com.muhardin.endy.belajar.jpos.iso;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.junit.Test;

public class PackagerTests {
    @Test
    public void testAccountInquiry() throws Exception {
        ISOMsg msg = new ISOMsg("0200");
        msg.set(2, "001");
        msg.set(3, "341000");
        msg.set(7, "1115112500");
        msg.set(11, "123456");
        msg.set(12, "112500");
        msg.set(13, "1115");
        msg.set(15, "1115");
        msg.set(18, "6012");
        msg.set(29, "C00000000");
        msg.set(31, "C00000000");
        msg.set(103, "001");
        
        msg.setPackager(new GenericPackager("cfg/altopackager.xml"));
        String msgString = new String(msg.pack());
        System.out.println("Account Inquiry : "+msgString);
    }
    
    @Test
    public void testTopupRequest() throws Exception {
        ISOMsg msg = new ISOMsg("0200");
        msg.set(2, "001");
        msg.set(3, "411000");
        msg.set(4, "75000");
        msg.set(7, "1116102500");
        msg.set(11, "123456");
        msg.set(12, "102500");
        msg.set(13, "1116");
        msg.set(15, "1116");
        msg.set(18, "6012");
        msg.set(29, "C00000000");
        msg.set(31, "C00000000");
        msg.set(102, "9876543210");  // akun sumber dana biasanya di 102
        msg.set(103, "001");     // akun penerima dana biasanya di 103
        
        msg.setPackager(new GenericPackager("cfg/altopackager.xml"));
        String msgString = new String(msg.pack());
        System.out.println("Topup Request : "+msgString);
    }
}
