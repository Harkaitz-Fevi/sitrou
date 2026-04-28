package com.sitrou.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitrou.web.model.Solairuak;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface SolairuakRepository extends JpaRepository<Solairuak, String> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Solairuak guztiak zerrendan lortzeko.
       - save(solairuak): Solairuak berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez solairuak bilatzeko.*/
       /*- deleteById(id): Solairuak ezabatzeko.*/
    
}