package ProyectoFamilia.Controller;

import ProyectoFamilia.Model.Evento;
import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
import ProyectoFamilia.Service.ServiceFamilia;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventoController 
{

    @Autowired
    private ServiceFamilia serviceFamilia;

    @GetMapping("/altaEvento")
    public String MostrarFormularioDeAltaEvento(Model model, HttpSession session)
    {
       Evento evento = new Evento();
       model.addAttribute("evento", evento);
       Miembro miembro = (Miembro) session.getAttribute("miembro");
       Familia familia = miembro.getFamilia();
       List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
       model.addAttribute("listaMiembros", listaMiembros);
       return "registroEvento";
    }
    
    @PostMapping("/guardarEvento")
    public String guardarEvento(@ModelAttribute ("evento") Evento evento, @RequestParam(required = false, name = "idMiembro") Long idMiembro, HttpServletRequest request, Model model)
    {
        HttpSession session = request.getSession();
        Familia familia = (Familia)session.getAttribute("familia");
        evento.setFamilia(familia);
        if(idMiembro != null)    
        {
            Miembro miembro = serviceFamilia.buscarMiembroPorId(idMiembro);
            evento.setMiembro(miembro);
        }
        
        serviceFamilia.guardarEvento(evento);
        return "redirect:/gestionEvento";
    }
    
    @GetMapping("/gestionEvento")
    public String mostrarListaEventos(HttpSession session, Model model)
    {
        Familia familia = (Familia) session.getAttribute("familia");
        System.out.println(familia);
        if(familia != null)
        {
            System.out.println("HOLA");
            List<Evento> listaEventos = serviceFamilia.ListarEventos(familia.getId());
            System.out.println(listaEventos);
            model.addAttribute("familia", familia);
            model.addAttribute("listaEventos", listaEventos);
            return "gestionEvento";
        }
        return "registroEvento";
    }
    
    @GetMapping("/modificarEvento/{id}")
    public String modificarEvento(@PathVariable("id") Long id, Model model)
    {
        Evento evento = serviceFamilia.buscarEventoPorId(id);
        model.addAttribute("evento", evento);
        
        Familia familia = evento.getFamilia(); // se obtiene la familia a partir del evento
        List<Miembro> listaMiembros = serviceFamilia.ListarMiembrosPorFamilia(familia.getId());
        model.addAttribute("listaMiembros", listaMiembros);
        return "modificarEvento";
    }
    
    @GetMapping("/eliminarEvento/{id}")
    public String eliminarEvento(@PathVariable("id") Long id)
    {
        serviceFamilia.eliminarEvento(id);
        return "redirect:/gestionEvento";
    }
}
