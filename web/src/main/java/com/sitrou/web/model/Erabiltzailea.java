package com.sitrou.web.model;

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
    private String Usuarioa;
    private String Pasahitza;
    private String Rola;
   
    public Integer getId_erabiltzailea() {
        return Id_erabiltzailea;
    }
    public void setId_erabiltzailea(Integer Id_erabiltzailea) {
        this.Id_erabiltzailea = Id_erabiltzailea;
    }
    public String getUsuarioa() {
        return Usuarioa;
    }
    public void setUsuarioa(String Usuarioa) {
        this.Usuarioa = Usuarioa;
    }
    public String getPasahitza() {
        return Pasahitza;
    }
    public void setPasahitza(String Pasahitza) {
        this.Pasahitza = Pasahitza;
    }
    public String getRola() {
        return Rola;
    }
    public void setRola(String Rola) {
        this.Rola = Rola;
    }

    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)
}

