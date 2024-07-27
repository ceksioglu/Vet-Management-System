package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.dto.AnimalDTO;
import ceksioglu.vet_management_sys.service.abstracts.AnimalService;
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
 * Controller for managing animals.
 */
@RestController
@RequestMapping("/api/animals")
@Tag(name = "Animal", description = "Animal management APIs")
public class AnimalController {

    private final AnimalService animalService;

    /**
     * Constructor for AnimalController.
     *
     * @param animalService the animal service
     */
    @Autowired
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    /**
     * Creates a new animal.
     *
     * @param animalDTO the animal DTO
     * @return the response entity with the saved animal DTO and CREATED status
     */
    @Operation(summary = "Create a new animal", description = "Creates a new animal with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Animal created successfully",
                    content = @Content(schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Animal already exists")
    })
    @PostMapping
    public ResponseEntity<AnimalDTO> createAnimal(@RequestBody AnimalDTO animalDTO) {
        AnimalDTO savedAnimal = animalService.saveAnimal(animalDTO);
        return new ResponseEntity<>(savedAnimal, HttpStatus.CREATED);
    }

    /**
     * Updates an existing animal.
     *
     * @param id the animal ID
     * @param animalDTO the animal DTO
     * @return the response entity with the updated animal DTO and OK status
     */
    @Operation(summary = "Update an animal", description = "Updates an existing animal's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal updated successfully",
                    content = @Content(schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AnimalDTO> updateAnimal(
            @Parameter(description = "ID of the animal to update") @PathVariable Long id,
            @RequestBody AnimalDTO animalDTO) {
        AnimalDTO updatedAnimal = animalService.updateAnimal(id, animalDTO);
        return ResponseEntity.ok(updatedAnimal);
    }

    /**
     * Deletes an animal by its ID.
     *
     * @param id the animal ID
     * @return the response entity with no content and NO_CONTENT status
     */
    @Operation(summary = "Delete an animal", description = "Deletes an animal by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Animal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(
            @Parameter(description = "ID of the animal to delete") @PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves an animal's details by its ID.
     *
     * @param id the animal ID
     * @return the response entity with the animal DTO and OK status
     */
    @Operation(summary = "Get an animal by ID", description = "Retrieves an animal's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal found",
                    content = @Content(schema = @Schema(implementation = AnimalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Animal not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimalById(
            @Parameter(description = "ID of the animal to retrieve") @PathVariable Long id) {
        AnimalDTO animal = animalService.getAnimalById(id);
        return ResponseEntity.ok(animal);
    }

    /**
     * Retrieves a list of all animals.
     *
     * @return the response entity with the list of animal DTOs and OK status
     */
    @Operation(summary = "Get all animals", description = "Retrieves a list of all animals")
    @ApiResponse(responseCode = "200", description = "List of animals retrieved successfully",
            content = @Content(schema = @Schema(implementation = AnimalDTO.class)))
    @GetMapping
    public ResponseEntity<List<AnimalDTO>> getAllAnimals() {
        List<AnimalDTO> animals = animalService.getAllAnimals();
        return ResponseEntity.ok(animals);
    }

    /**
     * Retrieves a list of animals with the given name.
     *
     * @param name the name of the animals to retrieve
     * @return the response entity with the list of animal DTOs and OK status
     */
    @Operation(summary = "Get animals by name", description = "Retrieves a list of animals with the given name")
    @ApiResponse(responseCode = "200", description = "List of animals retrieved successfully",
            content = @Content(schema = @Schema(implementation = AnimalDTO.class)))
    @GetMapping("/name/{name}")
    public ResponseEntity<List<AnimalDTO>> getAnimalsByName(
            @Parameter(description = "Name of the animals to retrieve") @PathVariable String name) {
        List<AnimalDTO> animals = animalService.getAnimalsByName(name);
        return ResponseEntity.ok(animals);
    }

    /**
     * Retrieves a list of animals belonging to a specific customer.
     *
     * @param customerId the ID of the customer whose animals to retrieve
     * @return the response entity with the list of animal DTOs and OK status
     */
    @Operation(summary = "Get animals by customer ID", description = "Retrieves a list of animals belonging to a specific customer")
    @ApiResponse(responseCode = "200", description = "List of animals retrieved successfully",
            content = @Content(schema = @Schema(implementation = AnimalDTO.class)))
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AnimalDTO>> getAnimalsByCustomerId(
            @Parameter(description = "ID of the customer whose animals to retrieve") @PathVariable Long customerId) {
        List<AnimalDTO> animals = animalService.getAnimalsByCustomerId(customerId);
        return ResponseEntity.ok(animals);
    }
}
