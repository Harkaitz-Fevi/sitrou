package com.sitrou.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sitrou.web.model.Erabiltzailea;
import com.sitrou.web.model.Gailua;
import com.sitrou.web.model.Gelak;
import com.sitrou.web.repository.ErabiltzaileRepository;
import com.sitrou.web.repository.GailuaRepository;
import com.sitrou.web.repository.GelakRepository;


@Controller // Spring-i esaten dio klase honek HTTP eskariak (URLak) jasoko dituela
public class HasieraController {


    // 'final' jartzen dugu behin esleituta ez dela aldatuko ziurtatzeko
    private final ErabiltzaileRepository erabiltzaileRepository;

    private final GailuaRepository gailuaRepository;

    private final GelakRepository GelakRepository;

 

     


    // @Autowired // Lotura automatikoa.  Spring-ek automatikoki bilatuko du Repository-aren inplementazioa
    // public HasieraController(ErabiltzaileRepository erabiltzaileRepository) {
    //     this.erabiltzaileRepository = erabiltzaileRepository;
    // }



    
    @Autowired // Lotura automatikoa.  Spring-ek automatikoki bilatuko du Repository-aren inplementazioa
    public HasieraController(ErabiltzaileRepository erabiltzaileRepository, GailuaRepository gailuaRepository, GelakRepository gelakRepository) {
        this.erabiltzaileRepository = erabiltzaileRepository;
        this.gailuaRepository = gailuaRepository;
        this.GelakRepository = gelakRepository;
    }


    @GetMapping("/") // Nabigatzailean http://localhost:8080/ idaztean (GET eskaria)
    public String kaixo(Model model) {
        // Model-a "motxila" bat bezalakoa da: Javan sartzen ditugu datuak HTML-an erabili ahal izateko
       
        // GAKOA: "mezua" izena HTML-an ${mezua} bidez deituko dugu
        model.addAttribute("mezua", "Ongi etorri Miguel Altunako Gailuen Kudeatzailera!");
       
        // Datu-baseko erabiltzaile guztiak zerrenda batean lortu eta HTMLra pasatu
        // HTML-an "erabiltzaileak" erabiliko dugu (th:each bidez normalean)
        model.addAttribute("erabiltzaileak", erabiltzaileRepository.findAll());
       
        // GAKOA: "index" hitzak esaten dio Spring-i templates/index.html fitxategia bilatzeko
        return "index";
    }
    @PostMapping("/erregistratu")
    public String erregistratu(@RequestParam String username, @RequestParam String password) {
        Erabiltzailea berria = new Erabiltzailea();
        berria.setUsuarioa(username);
        berria.setPasahitza(password); 
        
        //Gordetzerakoan MySQL sortzen du Id-a eta gordetzen du rola.
        erabiltzaileRepository.save(berria); 
        
        return "redirect:/?success"; 
    }
    @PostMapping("/login")
    public String saioaHasi(@RequestParam String usuarioa, 
                            @RequestParam String pasahitza, 
                            Model model) {
        
        Erabiltzailea erabiltzailea = erabiltzaileRepository.findByUsuarioa(usuarioa);

        if (erabiltzailea != null && erabiltzailea.getPasahitza().equals(pasahitza)) {
            //Datuak modelora zuzenean pasatu
            // HTML-a bakar batean jasotzeko
            model.addAttribute("usuarioLogueado", erabiltzailea);
            // model.addAttribute("mensaje", "Kaixo, " + erabiltzailea.getUsuarioa() + "!");
            List<Gailua> gailuak = gailuaRepository.findAll();
            model.addAttribute("gailuak", gailuak);
            
            // Lista berriro kargatzen dugu hutsik ez geratzeko 
            // model.addAttribute("erabiltzaileak", erabiltzaileRepository.findAll());
            
            return "gailuen_kontsulta"; 
        } else {
            model.addAttribute("error", "Erabiltzaile izena edo pasahitza okerrak dira");
            // model.addAttribute("erabiltzaileak", erabiltzaileRepository.findAll());
            return "index"; 
        }
    }

    // GAILUAK KONTROLATZEKO METODOA
    @PostMapping("/gailua-gehitu")
    public String gailuaGehitu(@RequestParam String id, 
                               @RequestParam String gela,
                               @RequestParam String izena, 
                               @RequestParam String mota, 
                               @RequestParam String serie) {
        
        Gailua g = new Gailua();
        g.setId_gailua(id);   // Admina, sortzerakoan nahi duen Id-a sortuko du 
        g.setId_gela(gela);   // foreign key
        g.setIzena(izena);
        g.setGailu_mota(mota);
        g.setSerie_zenbakia(serie);
        g.setEgoera("Alokagarri"); // Sortzean, horrela geratuko da
        
        gailuaRepository.save(g); // 'gailuak'
        
        return "redirect:/"; 
    }
    
    @PostMapping("/gailua-eguneratu")
    public String gailuaEguneratu(@RequestParam String id_gailua, 
                                  @RequestParam String id_gela,
                                  @RequestParam String izena, 
                                  @RequestParam String mota, 
                                  @RequestParam String serie,
                                  @RequestParam String egoera) {
        
        // Bilatzen dugu gailua
        Gailua g = gailuaRepository.findById(id_gailua).orElse(null);
        
        if (g != null) {
            // Datuak aldatzen ditugu
            g.setId_gela(id_gela);
            g.setIzena(izena);
            g.setGailu_mota(mota);
            g.setSerie_zenbakia(serie);
            g.setEgoera(egoera);
            
            // save() egiterako orduan existitzen duen ID batekin, MySQL-ek UPDATE bat egiten du
            gailuaRepository.save(g);
        }
        return "redirect:/"; 
    }

    @PostMapping("/gailua-ezabatu")
    public String gailuaEzabatu(@RequestParam String id_gailua) {
        
        // Gailua bilatzen dugu
        Gailua g = gailuaRepository.findById(id_gailua).orElse(null);
        
        if (g != null) {
            // Sortzen dugu historikoen registroa
            /*
            Historikoa h = new Historikoa();
            h.setJatorrizko_id(g.getId_gailua());
            h.setIzena(g.getIzena());
            h.setTaula("Gailuak");
            h.setEzabatze_data(LocalDate.now());
            historikoaRepository.save(h);
            */

            // Taula originaletik borratzen dugu
            gailuaRepository.deleteById(id_gailua);
        }
        return "redirect:/";
    }

    

    // GAILUAK KONTROLATZEKO METODOA
        @PostMapping("/gela-gehitu")
    public String gelaGehitu(@RequestParam String id_gela, 
                             @RequestParam String id_eraikina,
                             @RequestParam String izena, 
                             @RequestParam String deskribapena, 
                             @RequestParam String ekintza) {
        
        Gelak ge = new Gelak();
        ge.setId_gela(id_gela);   // Admina, sortzerakoan nahi duen Id-a sortuko du 
        ge.setId_eraikina(id_eraikina);   // foreign key
        ge.setIzena(izena);
        ge.setDeskribapena(deskribapena);
        ge.setEkintza(ekintza);
        
        GelakRepository.save(ge);
        
        return "redirect:/"; 
     }
     @PostMapping("/gela-eguneratu")
    public String gelaEguneratu(@RequestParam String id_gela, 
                                @RequestParam String id_eraikina,
                                @RequestParam String izena, 
                                @RequestParam String deskribapena, 
                                @RequestParam String ekintza) {
        
        Gelak ge = GelakRepository.findById(id_gela).orElse(null);
        if (ge != null) {
            ge.setId_eraikina(id_eraikina);
            ge.setIzena(izena);
            ge.setDeskribapena(deskribapena);
            ge.setEkintza(ekintza);
            GelakRepository.save(ge); // UPDATE
        }
        return "redirect:/"; 
    }

    @PostMapping("/gela-ezabatu")
    public String gelaEzabatu(@RequestParam String id_gela) {
        
        Gelak ge = GelakRepository.findById(id_gela).orElse(null);
        
        if (ge != null) {
            // Hisotrikoan gorde
            /*
            Historikoa h = new Historikoa();
            h.setJatorrizko_id(ge.getId_gela());
            h.setIzena(ge.getIzena());
            h.setTaula("Gelak");
            h.setEzabatze_data(LocalDate.now());
            historikoaRepository.save(h);
            */

            // Taula originaletik borratu
            GelakRepository.deleteById(id_gela);
        }
        return "redirect:/";
    }


@PostMapping("/alokatu-gailua")
public String alokatuGailua(@RequestParam String id_gailua, 
                            @RequestParam Integer id_erabiltzailea,
                            Model model) {
    
    // 1. Buscamos el dispositivo en la base de datos
    Gailua gailua = gailuaRepository.findById(id_gailua).orElse(null);
    Erabiltzailea erabiltzailea = erabiltzaileRepository.findById(id_erabiltzailea).orElse(null);
    
    if (gailua != null && erabiltzailea != null) {
        // 2. Cambiamos su estado a "Mailegatuta" (Alquilado)
        gailua.setEgoera("Mailegatuta");
        gailuaRepository.save(gailua);
        
        // (Opcional) Aquí también podrías hacer un INSERT en tu tabla 'Mailegatutakoak'
        // para guardar la fecha en la que se lo llevó.
        
        // 3. Volvemos a meter al usuario y las listas actualizadas en la mochila para recargar la página
        model.addAttribute("usuarioLogueado", erabiltzailea);
        model.addAttribute("gailuak", gailuaRepository.findAll());
        model.addAttribute("gelak", GelakRepository.findAll());
    }
    
    return "gailuen_kontsulta";
}
}



