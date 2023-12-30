package com.mproduits.mproduitsapplication.Controller;

import com.mproduits.mproduitsapplication.Configurations.ApplicationPropertiesConfiguration;
import com.mproduits.mproduitsapplication.Dao.ProductDao;
import com.mproduits.mproduitsapplication.Exceptions.ProductNotFoundException;
import com.mproduits.mproduitsapplication.Model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@RestController
public class ProductController implements HealthIndicator {
@Autowired
    ProductDao productDao;
   @Autowired
    ApplicationPropertiesConfiguration appProperties;
    @GetMapping(value = "/Produits")
    public List<Produit> listeDesProduits() throws ProductNotFoundException {
        System.out.println(" ********* ProductController listeDesProduits() ");
        List<Produit> products = productDao.findAll();
        if (products.isEmpty())
            throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
                    List<Produit> listeLimitee = products.subList(0,
                            appProperties.getLimitDeProduits());
        return listeLimitee;
    }
    @GetMapping(value = "/Produits/{id}")
    public Optional<Produit> recupererUnProduit(@PathVariable int id) throws ProductNotFoundException {
        System.out.println(" ********* ProductController recupererUnProduit(@PathVariable int id) ");
        Optional<Produit> product = productDao.findById(id);

        if (!product.isPresent())
            throw new ProductNotFoundException("Le produit correspondant à l'id "
                    + id + " n'existe pas");
        return product;
    }
    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Produit> products = productDao.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
