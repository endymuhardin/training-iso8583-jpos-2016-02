package com.muhardin.endy.belajar.jpos.dao;

import com.muhardin.endy.belajar.jpos.entity.Mutasi;
import com.muhardin.endy.belajar.jpos.entity.Rekening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MutasiDao extends PagingAndSortingRepository<Mutasi, String>{
    public Page<Mutasi> findByRekening(Rekening r, Pageable page);
}
