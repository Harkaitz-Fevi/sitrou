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
@Table(name = "Solairuak")
public class Solairuak {
    
    @Id
    @Column(name = "Id_solairua")
    private String id_solairua;

    @Column(name = "Id_eraikina")
    private String id_eraikina;

    @Column(name = "Deskribapena")
    private String deskribapena;

    @Column(name = "Solairu_zenb")
    private Integer solairu_zenb; // En SQL es INT

    // Getters y Setters
    public String getId_solairua() { 
        return id_solairua; 
    }
    public void setId_solairua(String id_solairua) { 
        this.id_solairua = id_solairua; 
    }

    public String getId_eraikina() { 
        return id_eraikina; 
    }
    public void setId_eraikina(String id_eraikina) { 
        this.id_eraikina = id_eraikina; 
    }

    public String getDeskribapena() { 
        return deskribapena; 
    }
    public void setDeskribapena(String deskribapena) { 
        this.deskribapena = deskribapena; 
    }

    public Integer getSolairu_zenb() { 
        return solairu_zenb; 
    }
    public void setSolairu_zenb(Integer solairu_zenb) { 
        this.solairu_zenb = solairu_zenb; 
    }
}