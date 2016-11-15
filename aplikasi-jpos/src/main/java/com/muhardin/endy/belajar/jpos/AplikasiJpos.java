package com.muhardin.endy.belajar.jpos;

import org.jpos.q2.Q2;

public class AplikasiJpos {
    public static void main(String[] args) {
        System.out.println("Hello Jpos");
        
        Q2 aplikasiJpos = new Q2();
        aplikasiJpos.start();
    }
}
