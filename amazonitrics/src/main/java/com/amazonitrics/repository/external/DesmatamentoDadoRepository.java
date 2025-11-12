package com.amazonitrics.repository.external;

import com.amazonitrics.model.external.DesmatamentoDado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesmatamentoDadoRepository extends JpaRepository<DesmatamentoDado, Long> {
    List<DesmatamentoDado> findByUf(String uf);
    List<DesmatamentoDado> findByYearAndMonth(String year, int month);
}