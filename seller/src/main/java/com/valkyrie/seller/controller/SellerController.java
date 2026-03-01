package com.valkyrie.seller.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.valkyrie.seller.model.Seller;
import com.valkyrie.seller.model.SellerDTO;
import com.valkyrie.seller.service.SellerService;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerService service;
    @Autowired
    private void setService(SellerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody Seller seller) {
        return service.save(seller);
    }

    @PostMapping("/update-img")
    public ResponseEntity<Boolean> updateImg(@RequestParam String username, @RequestParam MultipartFile img) throws IOException {
        return service.updateImage(username, img);
    }

    @PostMapping("/update-seller")
    public ResponseEntity<Boolean> updateSeller(@RequestParam String username, @RequestParam String fieldNo, @RequestParam String value) {
        return service.updateSeller(username, fieldNo, value);
    }

    @GetMapping("/get-seller")
    public ResponseEntity<SellerDTO> getSeller(@RequestParam String username) {
        return service.find(username);
    }

    @DeleteMapping("/delete-seller")
    public ResponseEntity<Boolean> removeSeller(@RequestParam String username) {
        return service.remove(username);
    }
}
