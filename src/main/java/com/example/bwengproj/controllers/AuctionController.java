package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Auction saveAuction(@Valid @RequestBody Auction auction){

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
    */
    @GetMapping("/auctions")
    public Iterable<Auction> fetchAuctionList(){
        return auctionService.fetchAuctionList();
    }

    /*

    // Update operation
    @PutMapping("/departments/{id}")
    public Department
    updateDepartment(@RequestBody Department department,
                     @PathVariable("id") Long departmentId)
    {

        return departmentService.updateDepartment(
            department, departmentId);
    }*/
    @PutMapping("/auctions/{id}")
    public Auction updateAuction(@RequestBody Auction auction, @PathVariable("id") int auctionId){
        return auctionService.updateAuction(auction, auctionId);
    }


    /*

    // Delete operation
    @DeleteMapping("/departments/{id}")
    public String deleteDepartmentById(@PathVariable("id")
                                       Long departmentId)
    {

        departmentService.deleteDepartmentById(
            departmentId);
        return "Deleted Successfully";
    }*/

    // - get specific auction GET
    @GetMapping("/auctions/{id}")
    public Auction getAuctionByID(@PathVariable int id){
        Auction auction = new Auction();
        //DB Abfrage
        return auction;
    }


    // - delete auction DELETE
    @DeleteMapping("/auctions/{id}")
    public void deleteAuction(@PathVariable int id){

    }
}
