package com.muhardin.endy.belajar.jpos.dao;

import com.muhardin.endy.belajar.jpos.entity.Rekening;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RekeningDao extends PagingAndSortingRepository<Rekening, String>{
    public Rekening findByNomor(String nomor);
}
