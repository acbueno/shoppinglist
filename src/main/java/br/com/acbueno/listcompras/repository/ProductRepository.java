package br.com.acbueno.listcompras.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.acbueno.listcompras.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	List<Product> findByDateBuy(Date dateBuy);
	
	List<Product> findByProductName(String name);

}
