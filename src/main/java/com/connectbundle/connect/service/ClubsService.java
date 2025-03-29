package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ClubsDTO.*;
import com.connectbundle.connect.dto.UserDTO.SimplifiedUserResponseDTO;
import com.connectbundle.connect.exception.ResourceAlreadyExistsException;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.model.ClubMember;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ClubRoleEnum;
import com.connectbundle.connect.repository.ClubMemberRepository;
import com.connectbundle.connect.repository.ClubsRespository;
import com.connectbundle.connect.repository.UserRepository;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubsService {

    @Autowired
    ClubsRespository clubsRespository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClubMemberRepository clubMemberRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<BaseResponse<ClubResponseDTO>> createClub(CreateClubDTO clubDTO) {
        if (clubsRespository.findByName(clubDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Club", "name", clubDTO.getName());
        }
        User advisor = clubDTO.getAdvisor() != null
                ? userRepository.findByUsername(clubDTO.getAdvisor()).orElse(null)
                : null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return BaseResponse.error("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

        Club club = modelMapper.map(clubDTO, Club.class);
        club.setAdvisor(advisor);
        club.setCreatedBy(user);
        clubsRespository.save(club);

        ClubResponseDTO clubResponse = modelMapper.map(club, ClubResponseDTO.class);
        clubResponse.setCreatedBy(modelMapper.map(club.getCreatedBy(), SimplifiedUserResponseDTO.class));

        if (club.getAdvisor() != null) {
            clubResponse.setAdvisor(modelMapper.map(club.getAdvisor(), SimplifiedUserResponseDTO.class));
        }


        List<SimplifiedUserResponseDTO> members = club.getMembers().stream()
                .map(member -> modelMapper.map(member.getUser(), SimplifiedUserResponseDTO.class))
                .toList();
        clubResponse.setClubMembers(members);


        if (club.getPlanOfAction() != null) {
            clubResponse.setPlanOfAction(modelMapper.map(club.getPlanOfAction(), PlanOfActionDTO.class));
        }
        return BaseResponse.success(clubResponse, "Club saved successfully", 1);
    }

    public  ResponseEntity<BaseResponse<List<ClubResponseDTO>>> getAllClubs() {
        List<Club> clubs = clubsRespository.findAll();
        List<ClubResponseDTO> clubResponseDTOS = clubs.stream()
                .map(club -> {
                    return modelMapper.map(club, ClubResponseDTO.class);
                })
                .toList();

        return BaseResponse.success(clubResponseDTOS, "Clubs fetched successfully", clubResponseDTOS.size());
    }

    public ResponseEntity<BaseResponse<ClubResponseDTO>> getClubById(Long id) {
        Club club = clubsRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", id));

        ClubResponseDTO dto = modelMapper.map(club, ClubResponseDTO.class);
        return BaseResponse.success(dto, "Club fetched", 1);
    }


    public ResponseEntity<BaseResponse<Void>> addClubMember(AddClubMemberDTO dto) {
        Club club = clubsRespository.findById(dto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", dto.getClubId()));

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", dto.getUsername()));

        if (clubMemberRepository.findByClubAndUser(club, user).isPresent()) {
            throw new ResourceAlreadyExistsException("ClubMember", "user", dto.getUsername());
        }

        ClubMember member = new ClubMember();
        member.setClub(club);
        member.setUser(user);
        member.setRollNo(dto.getRollNo());
        member.setRole(dto.getRole());

        clubMemberRepository.save(member);

        return BaseResponse.successMessage("Member added successfully");
    }

    public ResponseEntity<BaseResponse<Void>> updateClubMemberRole(UpdateClubMemberDTO dto) {
        Club club = clubsRespository.findById(dto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", dto.getClubId()));

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", dto.getUsername()));

        ClubMember member = clubMemberRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new ResourceNotFoundException("ClubMember", "username", dto.getUsername()));

        member.setRole(dto.getNewRole());
        clubMemberRepository.save(member);

        return BaseResponse.successMessage("Member role updated successfully");
    }


    public ResponseEntity<BaseResponse<Void>> removeClubMember(RemoveClubMemberDTO dto) {
        Club club = clubsRespository.findById(dto.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", dto.getClubId()));

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", dto.getUsername()));

        ClubMember member = clubMemberRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new ResourceNotFoundException("ClubMember", "username", dto.getUsername()));

        clubMemberRepository.delete(member);

        return BaseResponse.successMessage("Member removed successfully");
    }


    public ClubServiceResponse<Club> addMemberToClub(AddClubMemberDTO clubMemberDTO) {
        try {
            Club club = clubsRespository.findById(clubMemberDTO.getClubId()).orElse(null);
            User user = userRepository.findByUsername(clubMemberDTO.getUsername()).orElse(null);
            ClubMember existingMember = clubMemberRepository.findByClubAndUser(club, user).orElse(null);
            if (club != null && user != null && existingMember == null) {
                ClubMember clubMember = new ClubMember();
                clubMember.setClub(club);
                clubMember.setUser(user);
                clubMember.setRole(ClubRoleEnum.MEMBER);
                clubMemberRepository.save(clubMember);
                clubsRespository.save(club);
                return new ClubServiceResponse<>(true, "Member added successfully", club);
            } else {
                if (existingMember != null) {
                    return new ClubServiceResponse<>(false, "This user is already a memeber of this club", null);
                } else if (club == null) {
                    return new ClubServiceResponse<>(false, "Club not found", null);
                } else {
                    return new ClubServiceResponse<>(false, "User not found", null);
                }
            }
        } catch (Exception e) {
            return new ClubServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ClubServiceResponse<Club> removeMemberFromClub(RemoveClubMemberDTO clubMemberDTO) {
        try {
            Club club = clubsRespository.findById(clubMemberDTO.getClubId()).orElse(null);
            User user = userRepository.findByUsername(clubMemberDTO.getUsername()).orElse(null);
            if (club != null && user != null) {
                ClubMember clubMember = clubMemberRepository.findByClubAndUser(club, user).orElse(null);
                if (clubMember != null) {
                    clubMemberRepository.delete(clubMember);
                    clubsRespository.save(club);
                    return new ClubServiceResponse<>(true, "Member removed successfully", club);
                } else {
                    return new ClubServiceResponse<>(false, "Member not found", null);
                }
            } else {
                if (club == null) {
                    return new ClubServiceResponse<>(false, "Club not found", null);
                } else {
                    return new ClubServiceResponse<>(false, "User not found", null);
                }
            }
        } catch (Exception e) {
            return new ClubServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // RESPONSE CLASS
    @Getter
    public static class ClubServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public ClubServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }

}
