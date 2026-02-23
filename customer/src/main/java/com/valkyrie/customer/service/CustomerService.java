package com.valkyrie.customer.service;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.valkyrie.customer.model.Customer;
import com.valkyrie.customer.model.CustomerDTO;
import com.valkyrie.customer.model.Image;
import com.valkyrie.customer.model.ImageDTO;
import com.valkyrie.customer.repository.CustomerImageRepository;
import com.valkyrie.customer.repository.CustomerRepository;

@Service
public class CustomerService {
    private CustomerRepository repo;
    @Autowired
    private void setRepo(CustomerRepository repo) {
        this.repo = repo;
    }

    private CustomerImageRepository imgRepo;
    @Autowired
    private void setImgRepo(CustomerImageRepository imgRepo) {
        this.imgRepo = imgRepo;
    }

    private String decode(String wrd) {
        return new String(Base64.getDecoder().decode(wrd.getBytes()));
    }

    public ResponseEntity<Boolean> save(Customer customer) {
        boolean isExist = repo.existsById(customer.getUsername());

        if (isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(true);
        }

        repo.save(customer);

        isExist = repo.existsById(customer.getUsername());

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
            imgRepo.removeCustomerDp(username);
        }

        imgRepo.save(img);
        isExist = imgRepo.existsById(img.getId());

        return isExist? ResponseEntity.status(HttpStatus.ACCEPTED).body(true) : 
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @Transactional
    public ResponseEntity<Boolean> updateCustomer(String username, String fNo, String updateValue) {
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
    public ResponseEntity<CustomerDTO> find(String username) {
        username = decode(username);
        boolean isExist = repo.existsById(username);

        if (!isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Customer customer = repo.findById(username).orElse(null);
        Image img = imgRepo.findByUsername(username);
        CustomerDTO customerDTO = customer != null? new CustomerDTO().setAddress(customer.getAddress()).setImage(
            img != null? new ImageDTO().setData(img.getData()).setId(img.getId()).setName(img.getName()).setType(img.getType()) : null
        ).setName(customer.getName()).setNumber(customer.getNumber()).setUsername(username) : null;

        return ResponseEntity.status(customerDTO != null? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(customerDTO);
    }

    @Transactional
    public ResponseEntity<Boolean> remove(String username) {
        username = decode(username);
        boolean isExist = repo.existsById(username);

        if (isExist) {
            repo.deleteById(username);
            imgRepo.removeCustomerDp(username);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }

        isExist = repo.existsById(username) || imgRepo.isExistByUsername(username);

        return ResponseEntity.status(isExist? HttpStatus.BAD_REQUEST : HttpStatus.OK).body(isExist);
    }
}
