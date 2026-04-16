package com.sitrou.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitrou.web.model.Erabiltzailea;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface ErabiltzaileRepository extends JpaRepository<Erabiltzailea, Long> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Erabiltzaile guztiak zerrendan lortzeko.
       - save(erabiltzailea): Erabiltzaile berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez erabiltzailea bilatzeko.*/
        Erabiltzailea findByUsuarioa(String usuarioa);
       /*- deleteById(id): Erabiltzailea ezabatzeko.*/
    
}