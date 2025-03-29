package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ClubsDTO.*;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.service.ClubsService;
import com.connectbundle.connect.service.ClubsService.ClubServiceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/clubs")
@Tag(name = "Clubs", description = "Clubs Endpoints")
public class ClubsController {

    @Autowired
    ClubsService clubsService;

    @GetMapping()
    @Operation(summary = "Get All Clubs", description = "Retrieve a list of all clubs")
    public ResponseEntity<BaseResponse<List<ClubResponseDTO>>> getAllClubs() {
         return clubsService.getAllClubs();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Club By ID", description = "Retrieve a club by its ID")
    public ResponseEntity<BaseResponse<Club>> getClubById(@PathVariable Long id) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.getClubById(id);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(), 1);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create Club", description = "Create a new club")
    public ResponseEntity<BaseResponse<ClubResponseDTO>> createClub(@Valid @RequestBody CreateClubDTO club) {
        return clubsService.createClub(club);
    }

    @PostMapping("/members")
    public ResponseEntity<BaseResponse<Void>> addMember(@RequestBody AddClubMemberDTO dto) {
        return clubsService.addClubMember(dto);
    }

    @PatchMapping("/members")
    public ResponseEntity<BaseResponse<Void>> updateMemberRole(@RequestBody @Valid UpdateClubMemberDTO dto) {
        return clubsService.updateClubMemberRole(dto);
    }

    @DeleteMapping("/members")
    public ResponseEntity<BaseResponse<Void>> removeMember(@RequestBody @Valid RemoveClubMemberDTO dto) {
        return clubsService.removeClubMember(dto);
    }

    @DeleteMapping("/deleteMember")
    @Operation(summary = "Delete Member", description = "Remove a member from a club")
    public ResponseEntity<BaseResponse<Club>> deleteMember(
            @Valid @RequestBody RemoveClubMemberDTO removeClubMemberDTO) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.removeMemberFromClub(removeClubMemberDTO);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(), 1);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
