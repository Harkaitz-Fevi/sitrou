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
@Table(name = "Eraikinak")
public class Eraikinak {
    
    @Id
    @Column(name = "Id_eraikina")
    private String idEraikina; 

    @Column(name = "Izena")
    private String izena;

    @Column(name = "Deskribapena")
    private String deskribapena;

    @Column(name = "Ekintza")
    private String ekintza;
    
    // Beharrezkoa da eraikitzailea, Hibernatek klase hau sortu behar duenean erabiltzen du.
    public Eraikinak() {}

    // Getter eta Setter-ak
    public String getIdEraikina() { 
        return idEraikina; 
    }
    public void setIdEraikina(String idEraikina) { 
        this.idEraikina = idEraikina; 
    }

    public String getIzena() { 
        return izena; 
    }
    public void setIzena(String izena) { 
        this.izena = izena; 
    }

    public String getDeskribapena() { 
        return deskribapena; 
    }
    public void setDeskribapena(String deskribapena) { 
        this.deskribapena = deskribapena; 

    }

    public String getEkintza() { 
        return ekintza; 
    }
    public void setEkintza(String ekintza) { 
        this.ekintza = ekintza; 
    }
}