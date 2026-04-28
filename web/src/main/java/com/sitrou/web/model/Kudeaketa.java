package com.sitrou.web.model;

import java.time.LocalDate;

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
@Table(name = "Kudeaketak")
public class Kudeaketa {

@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_kudeaketa;

    @Column(name = "Id_gailua")
    private String idGailua; // Datu basean null denez, hemen ere izan ahal da Null

    @Column(name = "Id_erabiltzailea")
    private Integer idErabiltzailea;

    @Column(name = "Kudeaketa_data")
    private LocalDate kudeaketaData;

    @Column(name = "Ekintza")
    private String ekintza;

    // Eraikitzailea
    public Kudeaketa() {}

    // Getter eta Setter-ak
    public Integer getId_kudeaketa() { 
        return id_kudeaketa; 
    }
    public void setId_kudeaketa(Integer id_kudeaketa) { 
        this.id_kudeaketa = id_kudeaketa; 
    }

    public String getIdGailua() { 
        return idGailua; 
    }
    public void setIdGailua(String idGailua) { 
        this.idGailua = idGailua; 
    }

    public Integer getIdErabiltzailea() { 
        return idErabiltzailea; 
    }
    public void setIdErabiltzailea(Integer idErabiltzailea) { 
        this.idErabiltzailea = idErabiltzailea; 
    }

    public LocalDate getKudeaketaData() { 
        return kudeaketaData; 
    }
    public void setKudeaketaData(LocalDate kudeaketaData) { 
        this.kudeaketaData = kudeaketaData; 
    }

    public String getEkintza() { 
        return ekintza; 
    }
    public void setEkintza(String ekintza) { 
        this.ekintza = ekintza; 
    }
}