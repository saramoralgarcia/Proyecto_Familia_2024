package ProyectoFamilia.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ProyectoFamilia.Model.Familia;
import ProyectoFamilia.Model.Miembro;
import ProyectoFamilia.Service.ServiceFamilia;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MiembroController 
{
    @Autowired
    private ServiceFamilia serviceFamilia;

    @GetMapping("/altaMiembro")
    public String MostrarFormularioDeRegistroMiembro(Model model, @RequestParam("id") Long idFamilia, @ModelAttribute("nombre") String nombre) // le pasamos el modelo y el id por la url 
    {
        Miembro miembro = new Miembro(); // creamos objeto
        model.addAttribute("miembro", miembro); // añadimos al modelo el objeto miembro
        model.addAttribute("idFamilia", idFamilia);// añadimos al modelo el id
        model.addAttribute("nombre", nombre);

        return "registroMiembro"; // retornamos la pagina
    }

    @PostMapping("/guardarMiembro")
    public String guardarMiembro(@ModelAttribute("miembro") Miembro miembro, @ModelAttribute("idFamilia") Long idFamilia, HttpServletRequest request, Model model)// le pasamos el objeto, el id,el request y el modelo
    // http Request -->  mensaje que se envía desde el cliente al servidor para solicitar un recurso.
    {
        Miembro existente = serviceFamilia.buscarMiembroPorEmail(miembro.getEmail());

        if (existente != null) {
            // Si el email ya existe, añadir un atributo al modelo y redirigir a la página de registro
            model.addAttribute("emailExistente", true);
            return "registroMiembro"; // Asegúrate de que esta es la página correcta para el registro
        }
        Familia familia = serviceFamilia.buscarFamiliaPorId(idFamilia); // Buscar la familia por su "id".
        miembro.setFamilia(familia); // se almacena el objeto miembro dentro de esa familia. 
        System.out.println("Miembro recibido: " + miembro.toString());
        
        serviceFamilia.guardarMiembro(miembro);
        HttpSession session = request.getSession();  //se obtiene el objeto miembro dentro de esta familia.
        session.setAttribute("miembro", miembro);// Agregamos el objeto miembro a la session Http(como si fuera una caja.
        return "redirect:/paginaInicialConMiembro?miembro=" + miembro.getNombre();
    }

    @PostMapping("/seleccionarFamilia")
    public String seleccionarFamilia(HttpServletRequest request, @RequestParam Long familiaId)
    {
        Familia familia = serviceFamilia.buscarFamiliaPorId(familiaId);
        HttpSession session = request.getSession();
        session.setAttribute("familia", familia);
        return "redirect:/mostrarMiembros";
    }


    @GetMapping("/altaMiembroConUsuario")
    public String DarDeAltaUnMiembroDentroDeApp(Model model) 
    {
        Miembro miembro = new Miembro();
        model.addAttribute("miembro", miembro);
        return "registroMiembroConUsuario";
    }


    @PostMapping("/guardarMiembroConUsuario")
    public String guardarMiembroConUsuario(HttpServletRequest request, @ModelAttribute("miembro") Miembro miembro)
    {
        System.out.println(miembro.toString());
        HttpSession session = request.getSession(); 
        Familia familia = (Familia) session.getAttribute("familia"); //recuperas la session donde se encuentra "familia".
        miembro.setFamilia(familia); // le asignas un miembro
        serviceFamilia.guardarMiembro(miembro);
        return "redirect:/gestionMiembro";
    }

    @GetMapping("/paginaInicial")
    public String VerPaginaInicial(Model model) 
    {
        return "paginaInicial";
    }
    
    @GetMapping("/modificarMiembro/{id}")
    public String mostrarFormularioModificarMiembro(@PathVariable("id") Long id, Model model)
    {
        Miembro miembro = serviceFamilia.buscarMiembroPorId(id);
        model.addAttribute("miembro", miembro);
        return "modificarMiembro";
    }
    
    @GetMapping("/eliminarMiembro/{id}")
    public String eliminarMiembro(@PathVariable("id") Long id)
    {
        serviceFamilia.eliminarMiembro(id);
        return "redirect:/gestionMiembro";
    }
    
}
