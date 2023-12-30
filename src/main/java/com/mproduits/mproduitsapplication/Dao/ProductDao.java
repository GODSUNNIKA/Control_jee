package com.mproduits.mproduitsapplication.Dao;

import com.mproduits.mproduitsapplication.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Produit,Integer> {

}
