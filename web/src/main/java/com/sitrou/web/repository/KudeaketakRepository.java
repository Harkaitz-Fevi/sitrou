package com.sitrou.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitrou.web.model.Kudeaketa;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface KudeaketakRepository extends JpaRepository<Kudeaketa, Integer> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Gelak guztiak zerrendan lortzeko.
       - save(gelak): Gelak berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez gelak bilatzeko.*/
       /*- deleteById(id): Gelak ezabatzeko.*/
    
}