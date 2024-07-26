package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.dto.DoctorDTO;
import ceksioglu.vet_management_sys.service.abstracts.DoctorService;
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

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "Doctor", description = "Doctor management APIs")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Create a new doctor", description = "Creates a new doctor with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor created successfully",
                    content = @Content(schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Doctor already exists")
    })
    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        DoctorDTO savedDoctor = doctorService.saveDoctor(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a doctor", description = "Updates an existing doctor's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated successfully",
                    content = @Content(schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(
            @Parameter(description = "ID of the doctor to update") @PathVariable Long id,
            @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(id, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    @Operation(summary = "Delete a doctor", description = "Deletes a doctor by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Doctor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(
            @Parameter(description = "ID of the doctor to delete") @PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a doctor by ID", description = "Retrieves a doctor's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = @Content(schema = @Schema(implementation = DoctorDTO.class))),
            @ApiResponse(responseCode = "404", description = "Doctor not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(
            @Parameter(description = "ID of the doctor to retrieve") @PathVariable Long id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @Operation(summary = "Get all doctors", description = "Retrieves a list of all doctors")
    @ApiResponse(responseCode = "200", description = "List of doctors retrieved successfully",
            content = @Content(schema = @Schema(implementation = DoctorDTO.class)))
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctors);
    }
}