package com.sitrou.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sitrou.web.model.Erabiltzailea;
import com.sitrou.web.model.Eraikinak;
import com.sitrou.web.model.Gailua;
import com.sitrou.web.model.Gelak;
import com.sitrou.web.model.Kudeaketa;
import com.sitrou.web.model.Solairuak;
import com.sitrou.web.repository.ErabiltzaileRepository;
import com.sitrou.web.repository.EraikinakRepository;
import com.sitrou.web.repository.GailuaRepository;
import com.sitrou.web.repository.GelakRepository;
import com.sitrou.web.repository.KudeaketakRepository;
import com.sitrou.web.repository.SolairuakRepository;

@Controller
public class HasieraController {

    private final ErabiltzaileRepository erabiltzaileRepository;
    private final GailuaRepository gailuaRepository;
    private final GelakRepository gelakRepository;
    private final EraikinakRepository eraikinakRepository;
    private final SolairuakRepository solairuakRepository; // <--- ESTA LÍNEA TE FALTA

    @Autowired
    private KudeaketakRepository kudeaketakRepository;

    @Autowired
    public HasieraController(ErabiltzaileRepository erabiltzaileRepository,
            GailuaRepository gailuaRepository,
            GelakRepository gelakRepository,
            EraikinakRepository eraikinakRepository,
            SolairuakRepository solairuakRepository) {
        this.erabiltzaileRepository = erabiltzaileRepository;
        this.gailuaRepository = gailuaRepository;
        this.gelakRepository = gelakRepository;
        this.eraikinakRepository = eraikinakRepository;
        this.solairuakRepository = solairuakRepository;
    }

    @GetMapping("/")
    public String kaixo(Model model) {
        model.addAttribute("mezua", "Ongi etorri Miguel Altunako Gailuen Kudeatzailera!");
        model.addAttribute("erabiltzaileak", erabiltzaileRepository.findAll());
        return "index";
    }

    @PostMapping("/erregistratu")
    public String erregistratu(@RequestParam String username, @RequestParam String password) {
        Erabiltzailea berria = new Erabiltzailea();
        berria.setUsuarioa(username);
        berria.setPasahitza(password);
        erabiltzaileRepository.save(berria);
        return "redirect:/?success";
    }

    @PostMapping("/erabiltzailea-gehitu")
    public String erabiltzaileaGehitu(@RequestParam String usuarioa, @RequestParam String pasahitza, @RequestParam String rola) {
        Erabiltzailea berria = new Erabiltzailea();
        berria.setUsuarioa(usuarioa);
        berria.setPasahitza(pasahitza);
        berria.setRola(rola);
        erabiltzaileRepository.save(berria);
        return "redirect:/erabiltzaileak";
    }

    @PostMapping("/erabiltzailea-eguneratu")
    public String erabiltzaileaEguneratu(@RequestParam Integer id, @RequestParam String usuarioa, @RequestParam String pasahitza, @RequestParam String rola) {
        Erabiltzailea e = erabiltzaileRepository.findById(id).orElse(null);
        if (e != null) {
            e.setUsuarioa(usuarioa);
            e.setPasahitza(pasahitza);
            e.setRola(rola);
            erabiltzaileRepository.save(e);
        }
        return "redirect:/erabiltzaileak";
    }

    @PostMapping("/erabiltzailea-ezabatu")
    public String erabiltzaileaEzabatu(@RequestParam Integer id) {
        erabiltzaileRepository.deleteById(id);
        return "redirect:/erabiltzaileak";
    }

    @PostMapping("/login")
    public String saioaHasi(@RequestParam("username") String usuarioa,
            @RequestParam("password") String pasahitza,
            Model model) {

        if (usuarioa.equals("sitrou") && pasahitza.equals("123456789")) {
            model.addAttribute("isAdmin", true);
            model.addAttribute("gailuak", new ArrayList<Gailua>());
            return "gailuen_kontsulta";
        }

        Erabiltzailea erabiltzailea = erabiltzaileRepository.findByUsuarioa(usuarioa);

        if (erabiltzailea != null && erabiltzailea.getPasahitza().equals(pasahitza)) {
            model.addAttribute("usuarioLogueado", erabiltzailea);
            List<Gailua> gailuak = gailuaRepository.findAll();
            model.addAttribute("gailuak", gailuak);

            if (usuarioa.equals("sitrou") && pasahitza.equals("123456789")) {
                model.addAttribute("isAdmin", true);
            }
            return "gailuen_kontsulta";
        } else {
            model.addAttribute("error", "Erabiltzaile izena edo pasahitza okerrak dira");
            return "index";
        }
    }

    @GetMapping("/gailuen_kontsulta")
    public String gailuenKontsulta(Model model) {
        model.addAttribute("isAdmin", true);
        List<Gailua> gailuak = gailuaRepository.findAll();
        model.addAttribute("gailuak", gailuak);
        return "gailuen_kontsulta";
    }

    @GetMapping("/gailuak")
    public String gailuak(Model model) {
        List<Gailua> gailuak = gailuaRepository.findAll();
        //Eraikin guztiak lortzeko beharrezko lerroa da hau
        List<Gelak> gelak = gelakRepository.findAll();
        model.addAttribute("gailuak", gailuak);
        model.addAttribute("gelak", gelak);
        return "gailuak";
    }

    @GetMapping("/gelak")
    public String gelak(Model model) {
        List<Gelak> gelak = gelakRepository.findAll();
        //Eraikin guztiak lortzeko beharrezko lerroa da hau
        List<Eraikinak> eraikinak = eraikinakRepository.findAll();
        model.addAttribute("gelak", gelak);
        model.addAttribute("eraikinak", eraikinak);
        return "gelak";
    }

    @GetMapping("/erabiltzaileak")
    public String erabiltzaileak(Model model) {
        List<Erabiltzailea> erabiltzaileak = erabiltzaileRepository.findAll();
        model.addAttribute("erabiltzaileak", erabiltzaileak);
        return "erabiltzaileak";
    }

    @GetMapping("/historikoa")
    public String verHistorikoa(Model model) {
        List<Kudeaketa> listaMovimientos = kudeaketakRepository.findAll();
        model.addAttribute("mugimenduak", listaMovimientos);
        return "historikoa";
    }

    // ==========================================
    // ERAIKINAK KONTROLATZEKO METODOAK (CORREGIDOS)
    // ==========================================
    @GetMapping("/eraikinak")
    public String eraikinak(Model model) {
        List<Eraikinak> eraikinakList = eraikinakRepository.findAll();
        model.addAttribute("eraikinak", eraikinakList);
        return "eraikinak";
    }

    @PostMapping("/eraikinak-gehitu")
    public String eraikinakGehitu(@RequestParam String id_eraikina,
            @RequestParam String izena,
            @RequestParam String deskribapena,
            @RequestParam String ekintza) {

        Eraikinak er = new Eraikinak();
        er.setIdEraikina(id_eraikina);  // Usando el setter en camelCase
        er.setIzena(izena);
        er.setDeskribapena(deskribapena);
        er.setEkintza(ekintza);

        eraikinakRepository.save(er);
        return "redirect:/eraikinak";
    }

    @PostMapping("/eraikinak-eguneratu")
    public String eraikinakEguneratu(@RequestParam String id_eraikina,
            @RequestParam String izena,
            @RequestParam String deskribapena,
            @RequestParam String ekintza) {

        Eraikinak er = eraikinakRepository.findById(id_eraikina).orElse(null);
        if (er != null) {
            er.setIzena(izena);
            er.setDeskribapena(deskribapena);
            er.setEkintza(ekintza);
            eraikinakRepository.save(er);
        }
        return "redirect:/eraikinak";
    }

    @PostMapping("/eraikinak-ezabatu")
    public String eraikinakEzabatu(@RequestParam String id_eraikina) {
        Eraikinak er = eraikinakRepository.findById(id_eraikina).orElse(null);

        if (er != null) {
            eraikinakRepository.deleteById(id_eraikina);
        }
        return "redirect:/eraikinak";
    }

    // ==========================================
    // GAILUAK KONTROLATZEKO METODOAK
    // ==========================================
    @PostMapping("/gailua-gehitu")
    public String gailuaGehitu(@RequestParam String id_gailua,
            @RequestParam String id_gela,
            @RequestParam String izena,
            @RequestParam String mota,
            @RequestParam String serie,
            @RequestParam String egoera) {

        Gailua g = new Gailua();
        g.setId_gailua(id_gailua);
        g.setId_gela(id_gela);
        g.setIzena(izena);
        g.setGailu_mota(mota);
        g.setSerie_zenbakia(serie);
        g.setEgoera(egoera);

        gailuaRepository.save(g);
        return "redirect:/gailuak";
    }

    @PostMapping("/gailua-eguneratu")
    public String gailuaEguneratu(@RequestParam String id_gailua,
            @RequestParam String id_gela,
            @RequestParam String izena,
            @RequestParam String mota,
            @RequestParam String serie,
            @RequestParam String egoera) {

        Gailua g = gailuaRepository.findById(id_gailua).orElse(null);
        if (g != null) {
            g.setId_gela(id_gela);
            g.setIzena(izena);
            g.setGailu_mota(mota);
            g.setSerie_zenbakia(serie);
            g.setEgoera(egoera);
            gailuaRepository.save(g);
        }
        return "redirect:/gailuak";
    }

    @PostMapping("/gailua-ezabatu")
    public String gailuaEzabatu(@RequestParam String id_gailua) {
        if (gailuaRepository.existsById(id_gailua)) {
            gailuaRepository.deleteById(id_gailua);
        }
        return "redirect:/gailuak";
    }

    @PostMapping("/alokatu-gailua")
    public String alokatuGailua(@RequestParam String id_gailua,
            @RequestParam Integer id_erabiltzailea,
            Model model) {

        Gailua gailua = gailuaRepository.findById(id_gailua).orElse(null);
        Erabiltzailea erabiltzailea = erabiltzaileRepository.findById(id_erabiltzailea).orElse(null);

        if (gailua != null && erabiltzailea != null) {
            gailua.setEgoera("Mailegatuta");
            gailuaRepository.save(gailua);

            model.addAttribute("usuarioLogueado", erabiltzailea);
            model.addAttribute("gailuak", gailuaRepository.findAll());
            model.addAttribute("gelak", gelakRepository.findAll());
        }
        return "gailuen_kontsulta";
    }

    // ==========================================
    // GELAK KONTROLATZEKO METODOAK
    // ==========================================
    @PostMapping("/gela-gehitu")
    public String gelaGehitu(@RequestParam String id_gela,
            @RequestParam String id_eraikina,
            @RequestParam String izena,
            @RequestParam String deskribapena,
            @RequestParam String ekintza) {

        Gelak ge = new Gelak();
        ge.setId_gela(id_gela);
        ge.setId_eraikina(id_eraikina);
        ge.setIzena(izena);
        ge.setDeskribapena(deskribapena);
        ge.setEkintza(ekintza);

        gelakRepository.save(ge);
        return "redirect:/gelak";
    }

    @PostMapping("/gela-eguneratu")
    public String gelaEguneratu(@RequestParam String id_gela,
            @RequestParam String id_eraikina,
            @RequestParam String izena,
            @RequestParam String deskribapena,
            @RequestParam String ekintza) {

        Gelak ge = gelakRepository.findById(id_gela).orElse(null);
        if (ge != null) {
            ge.setId_eraikina(id_eraikina);
            ge.setIzena(izena);
            ge.setDeskribapena(deskribapena);
            ge.setEkintza(ekintza);
            gelakRepository.save(ge);
        }
        return "redirect:/gelak";
    }

    @PostMapping("/gela-ezabatu")
    public String gelaEzabatu(@RequestParam String id_gela) {
        Gelak ge = gelakRepository.findById(id_gela).orElse(null);
        if (ge != null) {
            gelakRepository.deleteById(id_gela);
        }
        return "redirect:/gelak";
    }

    // ==========================================
    // SOLAIRUAK KONTROLATZEKO METODOAK
    // ==========================================
    // ==========================================
    // SOLAIRUAK KONTROLATZEKO METODOAK
    // ==========================================
    @GetMapping("/solairuak")
    public String solairuak(Model model) {
        List<Solairuak> solairuakList = solairuakRepository.findAll();
        model.addAttribute("solairuak", solairuakList);
        return "solairuak";
    }

    @PostMapping("/solairua-gehitu")
    public String solairuaGehitu(@RequestParam String id_solairua,
            @RequestParam String id_eraikina,
            @RequestParam String deskribapena,
            @RequestParam Integer solairu_zenb) { // Solo los campos del SQL

        Solairuak s = new Solairuak();
        s.setId_solairua(id_solairua);
        s.setId_eraikina(id_eraikina);
        s.setDeskribapena(deskribapena);
        s.setSolairu_zenb(solairu_zenb);

        solairuakRepository.save(s);
        return "redirect:/solairuak";
    }

    @PostMapping("/solairua-eguneratu")
    public String solairuaEguneratu(@RequestParam String id_solairua,
            @RequestParam String id_eraikina,
            @RequestParam String deskribapena,
            @RequestParam Integer solairu_zenb) {

        Solairuak s = solairuakRepository.findById(id_solairua).orElse(null);
        if (s != null) {
            s.setId_eraikina(id_eraikina);
            s.setDeskribapena(deskribapena);
            s.setSolairu_zenb(solairu_zenb);
            solairuakRepository.save(s);
        }
        return "redirect:/solairuak";
    }

    @PostMapping("/solairua-ezabatu")
    public String solairuaEzabatu(@RequestParam String id_solairua) {
        if (solairuakRepository.existsById(id_solairua)) {
            solairuakRepository.deleteById(id_solairua);
        }
        return "redirect:/solairuak";
    }
}
