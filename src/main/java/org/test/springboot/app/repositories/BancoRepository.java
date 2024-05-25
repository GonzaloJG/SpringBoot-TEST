package org.test.springboot.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.springboot.app.models.Banco;

import java.util.List;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {
    //List<Banco> findAll();
    //Banco findById(Long id);
    //void update(Banco banco);

}
