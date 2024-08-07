package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.dto.AppointmentDTO;
import ceksioglu.vet_management_sys.service.abstracts.AppointmentService;
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
 * Controller for managing appointments.
 */
@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointment", description = "Appointment management APIs")
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Constructor for AppointmentController.
     *
     * @param appointmentService the appointment service
     */
    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Creates a new appointment.
     *
     * @param appointmentDTO the appointment DTO
     * @return the response entity with the saved appointment DTO and CREATED status
     */
    @Operation(summary = "Create a new appointment", description = "Creates a new appointment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment created successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Appointment conflict")
    })
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO savedAppointment = appointmentService.saveAppointment(appointmentDTO);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    /**
     * Updates an existing appointment.
     *
     * @param id the appointment ID
     * @param appointmentDTO the appointment DTO
     * @return the response entity with the updated appointment DTO and OK status
     */
    @Operation(summary = "Update an appointment", description = "Updates an existing appointment's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully",
                    content = @Content(schema = @Schema(implementation = AppointmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "409", description = "Appointment conflict")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(
            @Parameter(description = "ID of the appointment to update") @PathVariable Long id,
            @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    /**
     * Deletes an appointment by its ID.
     *
     * @param id the appointment ID
     * @return the response entity with no content and NO_CONTENT status
     */
    @Operation(summary = "Delete an appointment", description = "Deletes an appointment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @Parameter(description = "ID of the appointment to delete") @PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves an appointment's details by its ID.
     *
     * @param id the appointment ID
     * @return the response entity with the appointment DTO and OK status
     */
    @Operation(summary = "Get an appointment by ID", description = "Retrieves an appointment's details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found",
                    content = @Content(schema = @Schema(implementation = AppointmentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(
            @Parameter(description = "ID of the appointment to retrieve") @PathVariable Long id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    /**
     * Retrieves a list of all appointments.
     *
     * @return the response entity with the list of appointment DTOs and OK status
     */
    @Operation(summary = "Get all appointments", description = "Retrieves a list of all appointments")
    @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully",
            content = @Content(schema = @Schema(implementation = AppointmentDTO.class)))
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves a list of appointments for a specific animal within a date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @param animalId the ID of the animal
     * @return the response entity with the list of appointment DTOs and OK status
     */
    @Operation(summary = "Get appointments by date range and animal", description = "Retrieves a list of appointments for a specific animal within a date range")
    @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully",
            content = @Content(schema = @Schema(implementation = AppointmentDTO.class)))
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDateRangeAndAnimal(
            @Parameter(description = "Start date of the range") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @Parameter(description = "End date of the range") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @Parameter(description = "ID of the animal") @PathVariable Long animalId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDateRangeAndAnimal(startDate, endDate, animalId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves a list of appointments for a specific doctor within a date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @param doctorId the ID of the doctor
     * @return the response entity with the list of appointment DTOs and OK status
     */
    @Operation(summary = "Get appointments by date range and doctor", description = "Retrieves a list of appointments for a specific doctor within a date range")
    @ApiResponse(responseCode = "200", description = "List of appointments retrieved successfully",
            content = @Content(schema = @Schema(implementation = AppointmentDTO.class)))
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDateRangeAndDoctor(
            @Parameter(description = "Start date of the range") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @Parameter(description = "End date of the range") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
            @Parameter(description = "ID of the doctor") @PathVariable Long doctorId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDateRangeAndDoctor(startDate, endDate, doctorId);
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieves the order of an appointment for a specific date and doctor.
     *
     * @param appointmentDate the date of the appointment
     * @param doctorId the ID of the doctor
     * @return the response entity with the appointment order and OK status
     */
    @Operation(summary = "Get appointment order", description = "Retrieves the order of an appointment for a specific date and doctor")
    @ApiResponse(responseCode = "200", description = "Appointment order retrieved successfully")
    @GetMapping("/order")
    public ResponseEntity<Integer> getAppointmentOrder(
            @Parameter(description = "Date of the appointment") @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date appointmentDate,
            @Parameter(description = "ID of the doctor") @RequestParam Long doctorId) {
        Integer order = appointmentService.getAppointmentOrder(appointmentDate, doctorId);
        return ResponseEntity.ok(order);
    }
}
