package com.example.pawcare.controllers;
import com.example.pawcare.entities.Accessory;
import com.example.pawcare.services.accessory.AccessoryServices;
import com.example.pawcare.services.fileUpload.FileUploadServices;
import com.itextpdf.io.exceptions.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/accessory")
public class AccessoryController {
    @Autowired
    ServletContext context;

    @Autowired
    AccessoryServices accessoryServices;
    @Autowired
    FileUploadServices fileUploadServices;
      @GetMapping("/accessories")
      public List<Accessory> listaccessories() {
          return accessoryServices.retrieveAllAccessories();
      }
    @GetMapping("/listaccessories")
    public ResponseEntity<Page<Accessory>> getAccessoriesPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {
        // Retrieve the paginated and sorted list of accessories
        Page<Accessory> accessoryPage = accessoryServices.findAll(page, size, sortField, sortDir);
        // Return the list of accessories with HTTP status 200 OK
        return ResponseEntity.ok().body(accessoryPage);
    }

    @PostMapping("/addAccessory")
    public Accessory addAccessory(@RequestBody Accessory accessory) {
        return accessoryServices.addAccessory(accessory);

    }

   /* @PutMapping(value = "/updateAccessory/{idAccessory}")

    public Accessory updateAccessory(@PathVariable Long idAccessory, @RequestBody Accessory accessory) {
        return accessoryServices.updateAccessory(idAccessory, accessory);

    }*/
   @PutMapping("/updateAccessory/{idAccessory}")
   @ResponseBody
   public Map<String, Object> updateAccessory(@PathVariable("idAccessory") Long idAccessory,
                                              @RequestParam("name") String name,
                                              @RequestParam("price") Float price,
                                              @RequestParam("description") String description,
                                              @RequestParam(value = "image") MultipartFile image)
           throws IllegalStateException, IOException,java.io.IOException, ParseException {
       Accessory accessory = accessoryServices.retrieveAccessoryById(idAccessory);
       if (accessory == null) {
           throw new IllegalArgumentException("Invalid accessory ID: " + idAccessory);
       }

       accessory.setName(name);
       accessory.setPrice(price);
       accessory.setDescription(description);

       if (image != null) {
           String imageUrl = fileUploadServices.uploadfile1(image);
           accessory.setImage(image.getOriginalFilename());
       }

       accessoryServices.updateAccessory(idAccessory,accessory);

       Map<String, Object> response = new HashMap<>();
       response.put("accessory", accessory);
       return response;
   }



    @DeleteMapping("/deleteAccessory/{idAccessory}")

    public void deleteAccessory(@PathVariable("idAccessory") Long idAccessory) {
        accessoryServices.deleteAccessory(idAccessory);
    }

    @GetMapping("/getAccessoryById/{idAccessory}")
    public Accessory GetAccessoryById(@PathVariable("idAccessory") Long idAccessory) {
        return accessoryServices.retrieveAccessoryById(idAccessory);
    }


    @GetMapping("/searchAccessories")
    public ResponseEntity<List<Accessory>> searchAccessories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float price) {
        List<Accessory> accessories = accessoryServices.searchAccessories(name, price);
        return new ResponseEntity<>(accessories, HttpStatus.OK);
    }

    @GetMapping( "/AccessoriesToCsv")
    public void ExportAccessoriesToCsv(HttpServletResponse servletResponse) throws java.io.IOException {
        accessoryServices.ExportAccessoriesToCsv(servletResponse);
    }
    @PostMapping("/addAccessoryUpload1")
    @ResponseBody
    public Map<String, Object> addAccessoryUpload1(@RequestParam("name") String name,
                                                   @RequestParam("price") Float price,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("image") MultipartFile image)
            throws IllegalStateException, IOException,java.io.IOException, ParseException {
        Accessory accessory = new Accessory();
        accessory.setName(name);
        accessory.setPrice(price);
        accessory.setDescription(description);
        String imageUrl = fileUploadServices.uploadfile1(image);
        accessory.setImage(image.getOriginalFilename());

        accessoryServices.addAccessory(accessory);

        Map<String, Object> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        response.put("accessory", accessory);
        return response;
    }


}