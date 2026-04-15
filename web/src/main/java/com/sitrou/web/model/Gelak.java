package com.sitrou.web.model;

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
@Table(name = "gelak")
public class Gelak {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) //Kentzen dugu, IDa eskuz sartuko duelako gailua gehitzen duen pertsonak.
    private String Id_gela;
    private String Id_eraikina;
    private String Izena;
    private String Deskribapena;
    private String Ekintza;
    

     public String getId_gela() {
        return Id_gela;
    }
    public void setId_gela(String Id_gela) {
        this.Id_gela = Id_gela;
    }

    public String getId_eraikina() {
        return Id_eraikina;
    }
    public void setId_eraikina(String Id_eraikina) {
        this.Id_eraikina = Id_eraikina;
    }

    public String getDeskribapena() {
        return Deskribapena;
    }
    public void setDeskribapena(String Deskribapena) {
        this.Deskribapena = Deskribapena;
    }
    public String getIzena() {
        return Izena;
    }
    public void setIzena(String Izena) {
        this.Izena = Izena;
    }
    public String getEkintza() {
        return Ekintza;
    }
    public void setEkintza(String Ekintza) {
        this.Ekintza = Ekintza;
    }

    // Getter-ak eta Setter-ak (Lombok baduzu @Data jarri dezakezu gainean)
}

