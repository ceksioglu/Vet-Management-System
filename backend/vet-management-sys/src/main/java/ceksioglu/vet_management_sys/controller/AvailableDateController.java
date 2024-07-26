package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.dto.AvailableDateDTO;
import ceksioglu.vet_management_sys.service.abstracts.AvailableDateService;
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

@RestController
@RequestMapping("/api/available-dates")
@Tag(name = "Available Date", description = "Available Date management APIs")
public class AvailableDateController {

    private final AvailableDateService availableDateService;

    @Autowired
    public AvailableDateController(AvailableDateService availableDateService) {
        this.availableDateService = availableDateService;
    }

    @Operation(summary = "Create a new available date", description = "Creates a new available date with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Available date created successfully",
                    content = @Content(schema = @Schema(implementation = AvailableDateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Available date already exists")
    })
    @PostMapping
    public ResponseEntity<AvailableDateDTO> createAvailableDate(@RequestBody AvailableDateDTO availableDateDTO) {
        AvailableDateDTO savedAvailableDate = availableDateService.saveAvailableDate(availableDateDTO);
        return new ResponseEntity<>(savedAvailableDate, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an available date", description = "Updates an existing available date's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available date updated successfully",
                    content = @Content(schema = @Schema(implementation = AvailableDateDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Available date not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AvailableDateDTO> updateAvailableDate(
            @Parameter(description = "ID of the available date to update") @PathVariable Long id,
            @RequestBody AvailableDateDTO availableDateDTO) {
        AvailableDateDTO updatedAvailableDate = availableDateService.updateAvailableDate(id, availableDateDTO);
        return ResponseEntity.ok(updatedAvailableDate);
    }

    @Operation(summary = "Delete an available date", description = "Deletes an available date by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Available date deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Available date not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailableDate(
            @Parameter(description = "ID of the available date to delete") @PathVariable Long id) {
        availableDateService.deleteAvailableDate(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get an available date by ID", description = "Retrieves an available date's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available date found",
                    content = @Content(schema = @Schema(implementation = AvailableDateDTO.class))),
            @ApiResponse(responseCode = "404", description = "Available date not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AvailableDateDTO> getAvailableDateById(
            @Parameter(description = "ID of the available date to retrieve") @PathVariable Long id) {
        AvailableDateDTO availableDate = availableDateService.getAvailableDateById(id);
        return ResponseEntity.ok(availableDate);
    }

    @Operation(summary = "Get all available dates", description = "Retrieves a list of all available dates")
    @ApiResponse(responseCode = "200", description = "List of available dates retrieved successfully",
            content = @Content(schema = @Schema(implementation = AvailableDateDTO.class)))
    @GetMapping
    public ResponseEntity<List<AvailableDateDTO>> getAllAvailableDates() {
        List<AvailableDateDTO> availableDates = availableDateService.getAllAvailableDates();
        return ResponseEntity.ok(availableDates);
    }

    @Operation(summary = "Get available dates by doctor ID", description = "Retrieves a list of available dates for a specific doctor")
    @ApiResponse(responseCode = "200", description = "List of available dates retrieved successfully",
            content = @Content(schema = @Schema(implementation = AvailableDateDTO.class)))
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AvailableDateDTO>> getAvailableDatesByDoctorId(
            @Parameter(description = "ID of the doctor") @PathVariable Long doctorId) {
        List<AvailableDateDTO> availableDates = availableDateService.getAvailableDatesByDoctorId(doctorId);
        return ResponseEntity.ok(availableDates);
    }

    @Operation(summary = "Get current appointment count", description = "Retrieves the current appointment count for a specific date and doctor")
    @ApiResponse(responseCode = "200", description = "Current appointment count retrieved successfully")
    @GetMapping("/count")
    public ResponseEntity<Integer> getCurrentAppointmentCount(
            @Parameter(description = "Date to check") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
            @Parameter(description = "ID of the doctor") @RequestParam Long doctorId) {
        Integer count = availableDateService.getCurrentAppointmentCount(date, doctorId);
        return ResponseEntity.ok(count);
    }
}