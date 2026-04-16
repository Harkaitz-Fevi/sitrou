package com.sitrou.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sitrou.web.model.Gailua;


@Repository // Spring-i esaten dio klase honek datu-basearekin komunikazioa kudeatzen duela
public interface GailuaRepository extends JpaRepository<Gailua, String> {
   
    /* Hemen ez dugu metodorik idatzi behar momentuz.
       JpaRepository luzatzean, Spring-ek automatikoki ematen dizkigu:
       - findAll(): Erabiltzaile guztiak zerrendan lortzeko.
       - save(erabiltzailea): Erabiltzaile berri bat gordetzeko edo eguneratzeko.
       - findById(id): Id baten bidez erabiltzailea bilatzeko.*/
       /*- deleteById(id): Erabiltzailea ezabatzeko.*/
    
}