package ProyectoFamilia.Model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "Evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Nombre")
    private String nombre;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @Column(name = "Descripcion")
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Familia_id", referencedColumnName = "id")
    private Familia familia;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Miembro_id", referencedColumnName = "id")
    private Miembro miembro;
}
