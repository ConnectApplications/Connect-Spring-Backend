package com.connectbundle.connect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ClubsDTO.AddClubMemberDTO;
import com.connectbundle.connect.dto.ClubsDTO.CreateClubDTO;
import com.connectbundle.connect.dto.ClubsDTO.RemoveClubMemberDTO;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.service.ClubsService;
import com.connectbundle.connect.service.ClubsService.ClubServiceResponse;

import jakarta.validation.Valid;

@RestController("/api/clubs")
public class ClubsController {

    @Autowired
    ClubsService clubsService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<Club>>> getAllClubs() {
        try {
            ClubServiceResponse<List<Club>> clubServiceResponse = clubsService.getAllClubs();
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(),
                        HttpStatus.OK, clubServiceResponse.getData().size());
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/:id")
    public ResponseEntity<BaseResponse<Club>> getClubById(Long id) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.getClubById(id);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(),
                        HttpStatus.OK,
                        1);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createClub")
    public ResponseEntity<BaseResponse<Club>> createClub(@Valid @RequestBody CreateClubDTO club) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.createClub(club);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(),
                        HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMember")
    public ResponseEntity<BaseResponse<Club>> addMember(@Valid @RequestBody AddClubMemberDTO addClubMemberDTO) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.addMemberToClub(addClubMemberDTO);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(),
                        HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMember")
    public ResponseEntity<BaseResponse<Club>> deleteMember(
            @Valid @RequestBody RemoveClubMemberDTO removeClubMemberDTO) {
        try {
            ClubServiceResponse<Club> clubServiceResponse = clubsService.removeMemberFromClub(removeClubMemberDTO);
            if (clubServiceResponse.isSuccess()) {
                return BaseResponse.success(clubServiceResponse.getData(), clubServiceResponse.getMessage(),
                        HttpStatus.OK, 0);
            } else {
                return BaseResponse.error(clubServiceResponse.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return BaseResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
