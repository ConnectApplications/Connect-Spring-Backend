package com.connectbundle.connect.service;

import java.util.List;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.exception.ResourceAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.connectbundle.connect.dto.ClubsDTO.AddClubMemberDTO;
import com.connectbundle.connect.dto.ClubsDTO.CreateClubDTO;
import com.connectbundle.connect.dto.ClubsDTO.RemoveClubMemberDTO;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.model.ClubMember;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.model.enums.ClubRoleEnum;
import com.connectbundle.connect.repository.ClubMemberRepository;
import com.connectbundle.connect.repository.ClubsRespository;
import com.connectbundle.connect.repository.UserRepository;

import lombok.Getter;

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

    public ResponseEntity<BaseResponse<Club>> createClub(CreateClubDTO clubDTO) {
        if (clubsRespository.findByName(clubDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Club", "name", clubDTO.getName());
        }
        User advisor = clubDTO.getAdvisor() != null
                ? userRepository.findByUsername(clubDTO.getAdvisor()).orElse(null)
                : null;

        User clubHead = userRepository.findByUsername(clubDTO.getClubHead())
                .orElseThrow(() -> new RuntimeException("Club head not found"));

        Club club = modelMapper.map(clubDTO, Club.class);
        club.setAdvisor(advisor);
        club.setClubHead(clubHead);
        club.setMembers_count(0);
        clubsRespository.save(club);
        return BaseResponse.success(club, "Club saved successfully", 1);
    }

    public ClubServiceResponse<List<Club>> getAllClubs() {
        try {
            return new ClubServiceResponse<>(true, "Clubs fetched successfully", clubsRespository.findAll());
        } catch (Exception e) {
            return new ClubServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public ClubServiceResponse<Club> getClubById(Long id) {
        try {
            Club club = clubsRespository.findById(id).orElse(null);
            if (club != null) {
                return new ClubServiceResponse<>(true, "Club fetched successfully", club);
            } else {
                return new ClubServiceResponse<>(false, "Club not found", null);
            }
        } catch (Exception e) {
            return new ClubServiceResponse<>(false, e.getMessage(), null);
        }
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
                club.setMembers_count(club.getMembers_count() + 1);
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
                    club.setMembers_count(club.getMembers_count() - 1);
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
