package br.com.acbueno.listcompras.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.acbueno.listcompras.model.ListShop;

public interface ListShopRepository extends JpaRepository<ListShop, Integer> {

  List<ListShop> findByListDate(Date listDate);

  List<ListShop> findByName(String name);

}
