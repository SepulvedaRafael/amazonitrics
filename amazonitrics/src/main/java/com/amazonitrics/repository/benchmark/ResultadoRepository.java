package com.amazonitrics.repository.benchmark;

import com.amazonitrics.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    List<Resultado> findTop10ByOrderByDataExecucaoDesc();
}