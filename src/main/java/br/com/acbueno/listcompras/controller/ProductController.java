package br.com.acbueno.listcompras.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.acbueno.listcompras.model.ListShop;
import br.com.acbueno.listcompras.model.Product;
import br.com.acbueno.listcompras.repository.ListShopRepository;
import br.com.acbueno.listcompras.repository.ProductRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ListShopRepository listShopRepository;

  @GetMapping("list")
  public ResponseEntity<List<Product>> getAllProduct() {
    try {

      List<Product> product = productRepository.findAll();
      if (product.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(product, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("list/{id}")
  public ResponseEntity<Product> getProducById(@PathVariable int id) {

    try {
      Optional<Product> productData = productRepository.findById(id);

      if (productData.isPresent()) {
        return new ResponseEntity<>(productData.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("list{dateBuy}")
  public ResponseEntity<List<Product>> getProductByDate(@PathVariable Date dateBuy) {

    try {

      List<Product> productData = productRepository.findByDateBuy(dateBuy);

      if (!productData.isEmpty()) {
        return new ResponseEntity<>(productData, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("name/{nameProduct}")
  public ResponseEntity<Product> getProdocutByName(@PathVariable String nameProduct) {
    try {

      Optional<List<Product>> productData =
          Optional.ofNullable(productRepository.findByProductName(nameProduct));

      if (productData.isPresent()) {
        return new ResponseEntity<>(productData.get().get(0), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("create")
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {

    try {

      Optional<ListShop> listShopDate = listShopRepository.findById(product.getListShop().getId());

      product.setListShop(listShopDate.get());

      Product productData = productRepository.save(new Product(product.getProductName(),
          product.getQtd(), product.getDateBuy(), product.getListShop()));

      return new ResponseEntity<>(productData, HttpStatus.OK);

    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("update/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable("id") int id,
      @RequestBody Product product) {

    Optional<Product> productData = productRepository.findById(id);

    if (productData.isPresent()) {
      Product productUpdate = productData.get();
      if (product.getProductName() != null) {
        productUpdate.setProductName(product.getProductName());
      }
      if (product.getQtd() != productData.get().getQtd()) {
        productUpdate.setQtd(product.getQtd());
      }

      if (product.getDateBuy() != null) {
        productUpdate.setDateBuy(product.getDateBuy());
      }

      if (product.getListShop() != null) {
        Optional<ListShop> listShop = listShopRepository.findById(product.getListShop().getId());
        if (listShop.isPresent()) {
          productUpdate.setListShop(listShop.get());
        }

      }

      return new ResponseEntity<>(productRepository.save(productUpdate), HttpStatus.OK);

    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

  }

  @DeleteMapping(path = "delete/{id}")
  public ResponseEntity<?> deleteProductById(@PathVariable("id") int id) {

    return productRepository.findById(id).map(record -> {
      productRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }).orElse(ResponseEntity.notFound().build());

  }

  @DeleteMapping(path = "delete/all")
  public ResponseEntity<HttpStatus> deleteAllProduct() {
    try {
      productRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

}
