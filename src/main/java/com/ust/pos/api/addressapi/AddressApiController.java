package com.ust.pos.api.addressapi;

import com.ust.pos.address.service.AddressService;
import com.ust.pos.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressApiController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public List<AddressDto> home() {
        return addressService.findAll();
    }

    @PostMapping("/add")
    public AddressDto addPost( @RequestBody AddressDto addressDto) {
        return addressService.save(addressDto);
    }

    @GetMapping("/get")
    public AddressDto update(@RequestParam String identifier) {
        return addressService.findByIdentifier(identifier);
    }

    @PostMapping("/update")
    public AddressDto updatePost(@RequestBody AddressDto addressDto) {
        return addressService.update(addressDto);
    }

    @GetMapping("/delete")
    public boolean delete(@RequestParam String identifier) {
        try{ addressService.delete(identifier);}
        catch(Exception e){
            return false;
        }
        return true;
    }

    @GetMapping("/findByAllPhoneNo")
    public List<AddressDto> findAllByPhoneNo(@RequestParam String phoneNo ) {
        return addressService.findAllByPhoneNo(phoneNo);
    }

}
