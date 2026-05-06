package com.sitrou.web.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


// Jakarta Persistence API. Hibernatek datu-basearekin lan egiteko erabiltzen dituen arau multzoak.


// import jakarta.persistence.Entity;
// @Entity etiketa erabili ahal izateko. EZ da klase arrunt bat, datu-baseko taula baten irudikapena da".
// Hibernatek hau ikusten duenean, badaki klase horrekin MySQL taula bat kudeatu behar duela.


// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// GenerationType eta GeneratedValue batera doaz. Erabiltzaile berri bat sortzen den bakoitzean balio bat sortuko du.


// import jakarta.persistence.Id; @Id etiketa erabili ahal izateko. Gako nagusia zein den adierazten da.


// import jakarta.persistence.Table; @Table etiketa erabili ahal izateko.
// Datu baseko zein taula erabili behar duen adieraziko zaio.

@Entity
@Table(name = "gailuak")
public class Gailua {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) //Kentzen dugu, IDa eskuz sartuko duelako gailua gehitzen duen pertsonak.
    @Column(name = "Id_gailua")
    private String Id_gailua;

    @Column(name = "Id_gela")
    private String Id_gela;

    @Column(name = "Id_erabiltzailea")
    private Integer Id_erabiltzailea;

    @Column(name = "Serie_zenbakia")
    private Integer Serie_zenbakia;

    @Column(name = "Izena")
    private String Izena;

    @Column(name = "Gailu_mota")
    private String Gailu_mota;

    @Column(name = "Egoera")
    private String Egoera;
  
   
    public String getId_gailua() {
        return Id_gailua;
    }
    public void setId_gailua(String Id_gailua) {
        this.Id_gailua = Id_gailua;
    }
     public String getId_gela() {
        return Id_gela;
    }
    public void setId_gela(String Id_gela) {
        this.Id_gela = Id_gela;
    }
    public Integer getId_erabiltzailea() {
        return Id_erabiltzailea;
    }
    public void setId_erabiltzailea(Integer Id_erabiltzailea) {
        this.Id_erabiltzailea = Id_erabiltzailea;
    }
    public Integer getSerie_zenbakia() {
        return Serie_zenbakia;
    }
    public void setSerie_zenbakia(Integer Serie_zenbakia) {
        this.Serie_zenbakia = Serie_zenbakia;
    }
    public String getIzena() {
        return Izena;
    }
    public void setIzena(String Izena) {
        this.Izena = Izena;
    }
    public String getGailu_mota() {
        return Gailu_mota;
    }
    public void setGailu_mota(String Gailu_mota) {
        this.Gailu_mota = Gailu_mota;
    }
    public String getEgoera() {
        return Egoera;
    }
    public void setEgoera(String Egoera) {
        this.Egoera = Egoera;
    }

    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)
}

