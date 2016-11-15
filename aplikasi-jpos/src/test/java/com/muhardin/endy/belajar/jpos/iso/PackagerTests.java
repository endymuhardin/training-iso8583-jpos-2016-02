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
        msg.set(102, "001");
        
        msg.setPackager(new GenericPackager("cfg/altopackager.xml"));
        String msgString = new String(msg.pack());
        System.out.println("ISO MSG : "+msgString);
    }
}
