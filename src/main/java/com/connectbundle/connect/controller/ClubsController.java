package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ClubsDTO.AddClubMemberDTO;
import com.connectbundle.connect.dto.ClubsDTO.CreateClubDTO;
import com.connectbundle.connect.dto.ClubsDTO.RemoveClubMemberDTO;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.service.ClubsService;
import com.connectbundle.connect.service.ClubsService.ClubServiceResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController()
@RequestMapping("/api/clubs")
@Tag(name = "Clubs", description = "Clubs Endpoints")
public class ClubsController {

    @Autowired
    ClubsService clubsService;

    @GetMapping()
    @Operation(summary = "Get All Clubs", description = "Retrieve a list of all clubs")
    public ResponseEntity<BaseResponse<List<Club>>> getAllClubs() {
        try {
            ClubServiceResponse<List<Club>> clubServiceResponse = clubsService.getAllClubs();
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(), clubServiceResponse.getData().size());
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    public ResponseEntity<BaseResponse<Club>> createClub(@Valid @RequestBody CreateClubDTO club) {
        return clubsService.createClub(club);
    }

    @PostMapping("/addMember")
    @Operation(summary = "Add Member", description = "Add a member to a club")
    public ResponseEntity<BaseResponse<Club>> addMember(@Valid @RequestBody AddClubMemberDTO addClubMemberDTO) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.addMemberToClub(addClubMemberDTO);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(), 1);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
