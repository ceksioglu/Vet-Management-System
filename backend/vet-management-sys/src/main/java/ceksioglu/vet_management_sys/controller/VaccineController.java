package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.dto.VaccineDTO;
import ceksioglu.vet_management_sys.service.abstracts.VaccineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Controller for managing vaccines.
 */
@RestController
@RequestMapping("/api/vaccines")
@Tag(name = "Vaccine", description = "Vaccine management APIs")
public class VaccineController {

    private final VaccineService vaccineService;

    /**
     * Constructor for VaccineController.
     *
     * @param vaccineService the vaccine service
     */
    @Autowired
    public VaccineController(VaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    /**
     * Creates a new vaccine.
     *
     * @param vaccineDTO the vaccine DTO
     * @return the response entity with the saved vaccine DTO and CREATED status
     */
    @Operation(summary = "Create a new vaccine", description = "Creates a new vaccine with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vaccine created successfully",
                    content = @Content(schema = @Schema(implementation = VaccineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Vaccine already exists")
    })
    @PostMapping
    public ResponseEntity<VaccineDTO> createVaccine(@RequestBody VaccineDTO vaccineDTO) {
        VaccineDTO savedVaccine = vaccineService.saveVaccine(vaccineDTO);
        return new ResponseEntity<>(savedVaccine, HttpStatus.CREATED);
    }

    /**
     * Updates an existing vaccine.
     *
     * @param id the vaccine ID
     * @param vaccineDTO the vaccine DTO
     * @return the response entity with the updated vaccine DTO and OK status
     */
    @Operation(summary = "Update a vaccine", description = "Updates an existing vaccine's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaccine updated successfully",
                    content = @Content(schema = @Schema(implementation = VaccineDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Vaccine not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VaccineDTO> updateVaccine(
            @Parameter(description = "ID of the vaccine to update") @PathVariable Long id,
            @RequestBody VaccineDTO vaccineDTO) {
        VaccineDTO updatedVaccine = vaccineService.updateVaccine(id, vaccineDTO);
        return ResponseEntity.ok(updatedVaccine);
    }

    /**
     * Deletes a vaccine by its ID.
     *
     * @param id the vaccine ID
     * @return the response entity with no content and NO_CONTENT status
     */
    @Operation(summary = "Delete a vaccine", description = "Deletes a vaccine by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vaccine deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vaccine not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(
            @Parameter(description = "ID of the vaccine to delete") @PathVariable Long id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a vaccine's details by its ID.
     *
     * @param id the vaccine ID
     * @return the response entity with the vaccine DTO and OK status
     */
    @Operation(summary = "Get a vaccine by ID", description = "Retrieves a vaccine's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vaccine found",
                    content = @Content(schema = @Schema(implementation = VaccineDTO.class))),
            @ApiResponse(responseCode = "404", description = "Vaccine not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VaccineDTO> getVaccineById(
            @Parameter(description = "ID of the vaccine to retrieve") @PathVariable Long id) {
        VaccineDTO vaccine = vaccineService.getVaccineById(id);
        return ResponseEntity.ok(vaccine);
    }

    /**
     * Retrieves a list of all vaccines.
     *
     * @return the response entity with the list of vaccine DTOs and OK status
     */
    @Operation(summary = "Get all vaccines", description = "Retrieves a list of all vaccines")
    @ApiResponse(responseCode = "200", description = "List of vaccines retrieved successfully",
            content = @Content(schema = @Schema(implementation = VaccineDTO.class)))
    @GetMapping
    public ResponseEntity<List<VaccineDTO>> getAllVaccines() {
        List<VaccineDTO> vaccines = vaccineService.getAllVaccines();
        return ResponseEntity.ok(vaccines);
    }

    /**
     * Retrieves a list of vaccines for a specific animal.
     *
     * @param animalId the ID of the animal
     * @return the response entity with the list of vaccine DTOs and OK status
     */
    @Operation(summary = "Get vaccines by animal ID", description = "Retrieves a list of vaccines for a specific animal")
    @ApiResponse(responseCode = "200", description = "List of vaccines retrieved successfully",
            content = @Content(schema = @Schema(implementation = VaccineDTO.class)))
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<VaccineDTO>> getVaccinesByAnimalId(
            @Parameter(description = "ID of the animal") @PathVariable Long animalId) {
        List<VaccineDTO> vaccines = vaccineService.getVaccinesByAnimalId(animalId);
        return ResponseEntity.ok(vaccines);
    }

    /**
     * Retrieves a list of vaccines within a specific protection end date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return the response entity with the list of vaccine DTOs and OK status
     */
    @Operation(summary = "Get vaccines by protection end date range", description = "Retrieves a list of vaccines within a specific protection end date range")
    @ApiResponse(responseCode = "200", description = "List of vaccines retrieved successfully",
            content = @Content(schema = @Schema(implementation = VaccineDTO.class)))
    @GetMapping("/protection-end-date")
    public ResponseEntity<List<VaccineDTO>> getVaccinesByProtectionEndDateRange(
            @Parameter(description = "Start date of the range") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @Parameter(description = "End date of the range") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        List<VaccineDTO> vaccines = vaccineService.getVaccinesByProtectionEndDateRange(startDate, endDate);
        return ResponseEntity.ok(vaccines);
    }
}
