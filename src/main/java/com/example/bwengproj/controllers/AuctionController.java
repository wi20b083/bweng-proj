package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AuctionController {
    /*// Annotation
    @Autowired private DepartmentService departmentService;
*/
    //@Autowired
    private AuctionService auctionService;

    /*

    // Save operation
    @PostMapping("/departments")
    public Department saveDepartment(
        @Valid @RequestBody Department department)
    {

        return departmentService.saveDepartment(department);
    }
    */

    @PostMapping("/auctions")
    public Auction saveAuction(@RequestBody Auction auction){

        //Insert in DB
        return auctionService.saveAuction(auction);
    }

    /*

    // Read operation
    @GetMapping("/departments")
    public List<Department> fetchDepartmentList()
    {

        return departmentService.fetchDepartmentList();
    }

    // Update operation
    @PutMapping("/departments/{id}")
    public Department
    updateDepartment(@RequestBody Department department,
                     @PathVariable("id") Long departmentId)
    {

        return departmentService.updateDepartment(
            department, departmentId);
    }

    // Delete operation
    @DeleteMapping("/departments/{id}")
    public String deleteDepartmentById(@PathVariable("id")
                                       Long departmentId)
    {

        departmentService.deleteDepartmentById(
            departmentId);
        return "Deleted Successfully";
    }*/

    // - get all auctions GET
    @GetMapping("/auctions")
    public Iterable<Auction> getAuctions(){
        List<Auction> auctions = new ArrayList<>();

        //DB abfrage hier

        return auctions;
    }

    // - get specific auction GET
    @GetMapping("/auctions/{id}")
    public Auction getAuctionByID(@PathVariable int id){
        Auction auction = new Auction();
        //DB Abfrage
        return auction;
    }




    // - edit/update auction PUT
    @PutMapping("/auctions/{id}")
    public void updateAuction(@PathVariable int id){

    }

    // - delete auction DELETE
    @DeleteMapping("/auctions/{id}")
    public void deleteAuction(@PathVariable int id){

    }
}
