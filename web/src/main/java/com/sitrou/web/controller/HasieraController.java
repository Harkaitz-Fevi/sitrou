package com.sitrou.web.controller;

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

import jakarta.servlet.http.HttpSession;

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
        berria.setRola("admin".equalsIgnoreCase(rola) ? "admin" : "arrunta");
        erabiltzaileRepository.save(berria);
        return "redirect:/erabiltzaileak";
    }

    @PostMapping("/erabiltzailea-eguneratu")
    public String erabiltzaileaEguneratu(@RequestParam("id") Integer id, @RequestParam String usuarioa, @RequestParam String pasahitza, @RequestParam String rola) {
        Erabiltzailea e = erabiltzaileRepository.findById(id).orElse(null);
        if (e != null) {
            e.setUsuarioa(usuarioa);
            e.setPasahitza(pasahitza);
            e.setRola("admin".equalsIgnoreCase(rola) ? "admin" : "arrunta");
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
            HttpSession session,
            Model model) {

        // 1. Caso especial: Admin maestro (opcional)
        if (usuarioa.equals("sitrou") && pasahitza.equals("12345678")) {
            Erabiltzailea adminFake = new Erabiltzailea();
            adminFake.setId_erabiltzailea(1);
            adminFake.setUsuarioa("sitrou");
            adminFake.setRola("admin");
            session.setAttribute("usuarioLogueado", adminFake);
            return "redirect:/gailuen_kontsulta";
        }

        // 2. Verificación en Base de Datos
        Erabiltzailea erabiltzailea = erabiltzaileRepository.findByUsuarioa(usuarioa);

        if (erabiltzailea != null && erabiltzailea.getPasahitza().equals(pasahitza)) {
            session.setAttribute("usuarioLogueado", erabiltzailea);
            return "redirect:/gailuen_kontsulta";
        } else {
            model.addAttribute("error", "Erabiltzaile izena edo pasahitza okerrak dira");
            return "index";
        }
    }

    @GetMapping("/gailuen_kontsulta")
    public String gailuenKontsulta(
            @RequestParam(name = "eraikina", required = false) String eraikina,
            @RequestParam(name = "gela", required = false) String gela,
            @RequestParam(name = "query", required = false) String query,
            Model model,
            HttpSession session) {
        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLogueado", usuarioLogueado);
        model.addAttribute("isAdmin", "admin".equalsIgnoreCase(usuarioLogueado.getRola()));

        List<Eraikinak> eraikinak = eraikinakRepository.findAll().stream()
                .filter(e -> e.getEkintza() != null && e.getEkintza().equalsIgnoreCase("Aktiboa"))
                .toList();
        model.addAttribute("eraikinak", eraikinak);

        List<Gelak> gelak = gelakRepository.findAll();
        if (eraikina != null && !eraikina.isBlank()) {
            gelak = gelak.stream()
                    .filter(g -> g.getId_eraikina() != null && g.getId_eraikina().equals(eraikina))
                    .toList();
        }
        model.addAttribute("gelak", gelak);

        List<Gailua> gailuak = gailuaRepository.findAll();
        if (eraikina != null && !eraikina.isBlank()) {
            List<String> allowedGelaIds = gelak.stream()
                    .map(Gelak::getId_gela)
                    .toList();
            gailuak = gailuak.stream()
                    .filter(g -> g.getId_gela() != null && allowedGelaIds.contains(g.getId_gela()))
                    .toList();
        }
        if (gela != null && !gela.isBlank()) {
            gailuak = gailuak.stream()
                    .filter(g -> g.getId_gela() != null && g.getId_gela().equals(gela))
                    .toList();
        }
        if (query != null && !query.isBlank()) {
            String lowerQuery = query.toLowerCase();
            gailuak = gailuak.stream()
                    .filter(g -> (g.getId_gailua() != null && g.getId_gailua().toLowerCase().contains(lowerQuery))
                            || (g.getIzena() != null && g.getIzena().toLowerCase().contains(lowerQuery))
                            || (g.getGailu_mota() != null && g.getGailu_mota().toLowerCase().contains(lowerQuery))
                            || (g.getSerie_zenbakia() != null && g.getSerie_zenbakia().toString().contains(lowerQuery)))
                    .toList();
        }
        model.addAttribute("gailuak", gailuak);

        model.addAttribute("selectedEraikina", eraikina);
        model.addAttribute("selectedGela", gela);
        model.addAttribute("query", query);
        return "gailuen_kontsulta";
    }

    @GetMapping("/gailuak")
    public String gailuak(Model model, HttpSession session) {
        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"admin".equals(usuarioLogueado.getRola())) {
            return "redirect:/";
        }

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
    public String erabiltzaileak(
            @RequestParam(name = "query", required = false) String query,
            Model model,
            HttpSession session) {
        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"admin".equalsIgnoreCase(usuarioLogueado.getRola())) {
            return "redirect:/gailuen_kontsulta";
        }
        List<Erabiltzailea> erabiltzaileak = erabiltzaileRepository.findAll();
        if (query != null && !query.isBlank()) {
            String lowerQuery = query.toLowerCase();
            erabiltzaileak = erabiltzaileak.stream()
                    .filter(e -> (e.getId_erabiltzailea() != null && e.getId_erabiltzailea().toString().contains(lowerQuery))
                            || (e.getUsuarioa() != null && e.getUsuarioa().toLowerCase().contains(lowerQuery))
                            || (e.getPasahitza() != null && e.getPasahitza().toLowerCase().contains(lowerQuery))
                            || (e.getRola() != null && e.getRola().toLowerCase().contains(lowerQuery)))
                    .toList();
        }
        model.addAttribute("erabiltzaileak", erabiltzaileak);
        model.addAttribute("query", query);
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
            @RequestParam String egoera,
            HttpSession session) {

        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"admin".equals(usuarioLogueado.getRola())) {
            return "redirect:/";
        }

        Gailua g = new Gailua();
        g.setId_gailua(id_gailua);
        g.setId_gela(id_gela);
        g.setIzena(izena);
        g.setGailu_mota(mota);
        try {
            g.setSerie_zenbakia(Integer.parseInt(serie));
        } catch (NumberFormatException e) {
            g.setSerie_zenbakia(0); // Default value if parsing fails
        }
        g.setEgoera(egoera);
        g.setId_erabiltzailea(null);

        gailuaRepository.save(g);

        // Log to historikoak
        Kudeaketa historikoa = new Kudeaketa();
        historikoa.setIdGailua(g.getId_gailua());
        historikoa.setEkintza("gehitu");
        historikoa.setKudeaketaData(java.time.LocalDate.now());
        historikoa.setIdErabiltzailea(usuarioLogueado.getId_erabiltzailea());
        kudeaketakRepository.save(historikoa);

        return "redirect:/gailuak";
    }

    @PostMapping("/gailua-eguneratu")
    public String gailuaEguneratu(@RequestParam String id_gailua,
            @RequestParam String id_gela,
            @RequestParam String izena,
            @RequestParam String mota,
            @RequestParam String serie,
            @RequestParam String egoera,
            HttpSession session) {

        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"admin".equals(usuarioLogueado.getRola())) {
            return "redirect:/";
        }

        Gailua g = gailuaRepository.findById(id_gailua).orElse(null);
        if (g != null) {
            g.setId_gela(id_gela);
            g.setIzena(izena);
            g.setGailu_mota(mota);
            try {
                g.setSerie_zenbakia(Integer.parseInt(serie));
            } catch (NumberFormatException e) {
                // Keep existing value if parsing fails
            }
            g.setEgoera(egoera);
            gailuaRepository.save(g);

            // Log to historikoak
            Kudeaketa historikoa = new Kudeaketa();
            historikoa.setIdGailua(g.getId_gailua());
            historikoa.setEkintza("editatu");
            historikoa.setKudeaketaData(java.time.LocalDate.now());
            historikoa.setIdErabiltzailea(usuarioLogueado.getId_erabiltzailea());
            kudeaketakRepository.save(historikoa);
        }
        return "redirect:/gailuak";
    }

    @PostMapping("/gailua-ezabatu")
    public String gailuaEzabatu(@RequestParam String id_gailua, HttpSession session) {
        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"admin".equals(usuarioLogueado.getRola())) {
            return "redirect:/";
        }

        // 1. Buscamos el dispositivo
        Gailua gailua = gailuaRepository.findById(id_gailua).orElse(null);

        if (gailua != null) {
            // 2. Insertar en Histórico
            Kudeaketa historikoa = new Kudeaketa();
            historikoa.setIdGailua(gailua.getId_gailua());
            historikoa.setEkintza("ezabatuta");
            historikoa.setKudeaketaData(java.time.LocalDate.now());
            historikoa.setIdErabiltzailea(usuarioLogueado.getId_erabiltzailea());
            kudeaketakRepository.save(historikoa);

            // 3. Borrar el dispositivo
            gailuaRepository.deleteById(id_gailua);
        }

        return "redirect:/gailuak";
    }

    @PostMapping("/alokatu")
    public String alokatu(@RequestParam String id_gailua, HttpSession session) {
        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        Gailua g = gailuaRepository.findById(id_gailua).orElse(null);
        if (g != null && "Erabilgarri".equals(g.getEgoera())) {
            g.setEgoera("Mailegatuta");
            g.setId_erabiltzailea(usuarioLogueado.getId_erabiltzailea());
            gailuaRepository.save(g);

            Kudeaketa historikoa = new Kudeaketa();
            historikoa.setIdGailua(g.getId_gailua());
            historikoa.setEkintza("mailegatuta");
            historikoa.setKudeaketaData(java.time.LocalDate.now());
            historikoa.setIdErabiltzailea(usuarioLogueado.getId_erabiltzailea());
            kudeaketakRepository.save(historikoa);
        }
        return "redirect:/gailuen_kontsulta";
    }

    @PostMapping("/itzuli")
    public String itzuli(@RequestParam String id_gailua, HttpSession session) {
        Erabiltzailea usuarioLogueado = (Erabiltzailea) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/";
        }

        Gailua g = gailuaRepository.findById(id_gailua).orElse(null);
        if (g != null && usuarioLogueado.getId_erabiltzailea() != null
                && usuarioLogueado.getId_erabiltzailea().equals(g.getId_erabiltzailea())) {
            g.setEgoera("Erabilgarri");
            g.setId_erabiltzailea(null);
            gailuaRepository.save(g);

            Kudeaketa historikoa = new Kudeaketa();
            historikoa.setIdGailua(g.getId_gailua());
            historikoa.setEkintza("itzuli");
            historikoa.setKudeaketaData(java.time.LocalDate.now());
            historikoa.setIdErabiltzailea(usuarioLogueado.getId_erabiltzailea());
            kudeaketakRepository.save(historikoa);
        }
        return "redirect:/gailuen_kontsulta";
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
    @GetMapping("/solairuak")
    public String solairuak(Model model) {
        List<Solairuak> solairuakList = solairuakRepository.findAll();
        List<Eraikinak> eraikinakList = eraikinakRepository.findAll();
        model.addAttribute("solairuak", solairuakList);
        model.addAttribute("eraikinak", eraikinakList);
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
