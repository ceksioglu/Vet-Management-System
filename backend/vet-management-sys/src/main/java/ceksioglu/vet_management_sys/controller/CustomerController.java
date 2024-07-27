package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.dto.CustomerDTO;
import ceksioglu.vet_management_sys.service.abstracts.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing customers.
 */
@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Customer management APIs")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructor for CustomerController.
     *
     * @param customerService the customer service
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a new customer.
     *
     * @param customerDTO the customer DTO
     * @return the response entity with the saved customer DTO and CREATED status
     */
    @Operation(summary = "Create a new customer", description = "Creates a new customer with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Customer already exists")
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    /**
     * Updates an existing customer.
     *
     * @param id the customer ID
     * @param customerDTO the customer DTO
     * @return the response entity with the updated customer DTO and OK status
     */
    @Operation(summary = "Update a customer", description = "Updates an existing customer's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "ID of the customer to update") @PathVariable Long id,
            @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * Deletes a customer by its ID.
     *
     * @param id the customer ID
     * @return the response entity with no content and NO_CONTENT status
     */
    @Operation(summary = "Delete a customer", description = "Deletes a customer by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete") @PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a customer's details by its ID.
     *
     * @param id the customer ID
     * @return the response entity with the customer DTO and OK status
     */
    @Operation(summary = "Get a customer by ID", description = "Retrieves a customer's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve") @PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return the response entity with the list of customer DTOs and OK status
     */
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers")
    @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully",
            content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    /**
     * Retrieves a list of customers with the given name.
     *
     * @param name the name of the customers to retrieve
     * @return the response entity with the list of customer DTOs and OK status
     */
    @Operation(summary = "Get customers by name", description = "Retrieves a list of customers with the given name")
    @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully",
            content = @Content(schema = @Schema(implementation = CustomerDTO.class)))
    @GetMapping("/name/{name}")
    public ResponseEntity<List<CustomerDTO>> getCustomersByName(
            @Parameter(description = "Name of the customers to retrieve") @PathVariable String name) {
        List<CustomerDTO> customers = customerService.getCustomersByName(name);
        return ResponseEntity.ok(customers);
    }
}
