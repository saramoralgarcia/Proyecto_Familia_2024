package ProyectoFamilia.Repository;

import ProyectoFamilia.Model.Evento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEvento extends JpaRepository<Evento, Long>
{
    List<Evento> findByFamiliaId(Long FamiliaId);
}
