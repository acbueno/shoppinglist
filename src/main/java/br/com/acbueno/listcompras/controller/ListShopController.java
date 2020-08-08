package br.com.acbueno.listcompras.controller;

import java.util.ArrayList;
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
import br.com.acbueno.listcompras.repository.ListShopRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shoplist")
public class ListShopController {

  @Autowired
  ListShopRepository listShopRepository;

  @GetMapping("list/all")
  public ResponseEntity<List<ListShop>> getAllList() {

    try {
      List<ListShop> listShop = new ArrayList<>();

      listShopRepository.findAll().forEach(listShop::add);

      if (listShop.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(listShop, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("list/{dateBuy}")
  public ResponseEntity<List<ListShop>> getListShopByDateBuy(
      @PathVariable("dateBuy") Date dateBuy) {

    try {
      List<ListShop> listShops = new ArrayList<>();

      listShopRepository.findByListDate(dateBuy).forEach(listShops::add);

      if (listShops.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(listShops, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("list/{id}")
  public ResponseEntity<ListShop> getListShopById(@PathVariable("id") int id) {

    try {
      Optional<ListShop> shopListData = listShopRepository.findById(id);

      if (shopListData.isPresent()) {
        return new ResponseEntity<>(shopListData.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("list/{name}")
  public ResponseEntity<ListShop> getListShopByName(@PathVariable("name") String name) {

    try {
      Optional<List<ListShop>> listShopData =
          Optional.ofNullable(listShopRepository.findByName(name));

      if (listShopData.isPresent()) {
        return new ResponseEntity<>(listShopData.get().get(0), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("create")
  public ResponseEntity<ListShop> createListShop(@RequestBody ListShop listShop) {

    try {
      ListShop listShopData =
          listShopRepository.save(new ListShop(listShop.getName(), listShop.getListDate()));
      return new ResponseEntity<>(listShopData, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
    }
  }

  @PutMapping("update/{id}")
  public ResponseEntity<ListShop> updateListShop(@PathVariable("id") int id,
      @RequestBody ListShop listShop) {

    Optional<ListShop> listShopData = listShopRepository.findById(id);

    if (listShopData.isPresent()) {
      ListShop listShopUpdate = listShopData.get();

      listShopUpdate.setName(listShop.getName());
      listShopUpdate.setListDate(listShop.getListDate());

      return new ResponseEntity<>(listShopRepository.save(listShopUpdate), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") int id) {
    try {
      listShopRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

  @DeleteMapping("delete/all")
  public ResponseEntity<HttpStatus> deleteListAll() {
    try {
      listShopRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }

}
