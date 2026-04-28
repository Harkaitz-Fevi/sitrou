package com.sitrou.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitrou.web.model.Eraikinak;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface EraikinakRepository extends JpaRepository<Eraikinak, String> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Eraikinak guztiak zerrendan lortzeko.
       - save(Eraikinak): Eraikinak berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez eraikinak bilatzeko.*/
       /*- deleteById(id): Eraikinak ezabatzeko.*/
    
}

