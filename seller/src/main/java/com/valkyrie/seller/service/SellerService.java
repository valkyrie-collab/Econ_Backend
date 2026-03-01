package com.valkyrie.seller.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.valkyrie.seller.repository.SellerImageRepository;
import com.valkyrie.seller.model.Image;
import com.valkyrie.seller.model.ImageDTO;
import com.valkyrie.seller.model.Seller;
import com.valkyrie.seller.model.SellerDTO;
import com.valkyrie.seller.repository.SellerRepository;



@Service
public class SellerService {
    private SellerRepository repo;
    @Autowired
    private void setRepo(SellerRepository repo) {
        this.repo = repo;
    }

    private SellerImageRepository imgRepo;
    @Autowired
    private void setImgRepo(SellerImageRepository imgRepo) {
        this.imgRepo = imgRepo;
    }

    private String decode(String wrd) {
        return new String(Base64.getDecoder().decode(wrd.getBytes()));
    }

    public ResponseEntity<Boolean> save(Seller seller) {
        boolean isExist = repo.existsById(seller.getUsername());

        if (isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(true);
        }

        repo.save(seller);

        isExist = repo.existsById(seller.getUsername());

        return isExist? ResponseEntity.status(HttpStatus.ACCEPTED).body(true) : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @Transactional
    public ResponseEntity<Boolean> updateImage(String username, MultipartFile imgFile) throws IOException{
        username = decode(username);
        boolean isExist = repo.existsById(username);

        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        Image img = new Image().setData(imgFile.getBytes()).setName(imgFile.getOriginalFilename())
            .setType(imgFile.getContentType()).setUsername(username);

        if (imgRepo.isExistByUsername(username)) {
            imgRepo.removeSellerDp(username);
        }

        imgRepo.save(img);
        isExist = imgRepo.existsById(img.getId());

        return isExist? ResponseEntity.status(HttpStatus.ACCEPTED).body(true) : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @Transactional
    public ResponseEntity<Boolean> updateSeller(String username, String fNo, String updateValue) {
        username = decode(username);
        updateValue = decode(updateValue);
        int fieldNo = Integer.parseInt(decode(fNo));
        boolean isExist = repo.existsById(username);
        int upVal = 0;

        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        switch(fieldNo) {
            case 1: {
                upVal = repo.updateNameField(updateValue);
                break;
            }
            case 2: {
                upVal = repo.updateAddressField(updateValue);
                break;
            }
            case 3: {
                upVal = repo.updateNumberField(Long.parseLong(updateValue));
                break;
            }
            default: {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }
        }

        return upVal > 0? ResponseEntity.status(HttpStatus.ACCEPTED).body(true) : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @Transactional
    public ResponseEntity<SellerDTO> find(String username) {
        username = decode(username);
        boolean isExist = repo.existsById(username);

        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Seller seller = repo.findById(username).orElse(null);
        Image img = imgRepo.findByUsername(username);
        SellerDTO sellerDTO = seller != null? new SellerDTO().setAddress(seller.getAddress()).setImage(
            img != null? new ImageDTO().setData(img.getData()).setId(img.getId()).setName(img.getName()).setType(img.getType()) : null
        ).setName(seller.getName()).setNumber(seller.getNumber()).setUsername(username) : null;

        return ResponseEntity.status(sellerDTO != null? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(sellerDTO);
    }

    @Transactional
    public ResponseEntity<Boolean> remove(String username) {
        username = decode(username);
        boolean isExist = repo.existsById(username);

        if (isExist) {
            repo.deleteById(username);
            imgRepo.removeSellerDp(username);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        isExist = repo.existsById(username) || imgRepo.isExistByUsername(username);

        return ResponseEntity.status(isExist? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(isExist);
    }
}
