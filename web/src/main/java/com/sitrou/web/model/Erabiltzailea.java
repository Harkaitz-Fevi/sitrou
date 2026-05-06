package com.sitrou.web.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "erabiltzaileak")
public class Erabiltzailea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id_erabiltzailea;

    @Column(name = "Usuarioa")
    private String usuarioa;

    @Column(name = "Pasahitza")
    private String pasahitza;

    @Column(name = "Rola")
    private String rola;
   
    public Integer getId_erabiltzailea() {
        return Id_erabiltzailea;
    }
    public void setId_erabiltzailea(Integer Id_erabiltzailea) {
        this.Id_erabiltzailea = Id_erabiltzailea;
    }
    public String getUsuarioa() {
        return usuarioa;
    }
    public void setUsuarioa(String usuarioa) {
        this.usuarioa = usuarioa;
    }
    public String getPasahitza() {
        return pasahitza;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }
    public String getRola() {
        return rola;
    }
    public void setRola(String rola) {
        this.rola = rola;
    }

    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)
}

