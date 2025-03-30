package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.ClubsDTO.*;
import com.connectbundle.connect.dto.EventsDTO.EventResponseDTO;
import com.connectbundle.connect.dto.UserDTO.SimplifiedUserResponseDTO;
import com.connectbundle.connect.exception.ResourceAlreadyExistsException;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Club;
import com.connectbundle.connect.model.ClubMember;
import com.connectbundle.connect.model.Event;
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

import java.util.ArrayList;
import java.util.Collection;
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
        
        // Create new DTO instead of using ModelMapper to avoid PersistentBag conversion issues
        ClubResponseDTO clubResponse = new ClubResponseDTO();
        
        // Manually map simple properties
        clubResponse.setId(club.getId());
        clubResponse.setName(club.getName());
        clubResponse.setBanner(club.getBanner());
        clubResponse.setLogo(club.getLogo());
        clubResponse.setDescription(club.getDescription());
        clubResponse.setDepartment(club.getDepartment());
        clubResponse.setActive(club.isActive());
        
        // Handle tags list
        if (club.getTags() != null) {
            clubResponse.setTags(safelyConvertCollection(club.getTags()));
        }
        
        // Manually map createdBy
        clubResponse.setCreatedBy(modelMapper.map(club.getCreatedBy(), SimplifiedUserResponseDTO.class));

        // Manually map advisor if exists
        if (club.getAdvisor() != null) {
            clubResponse.setAdvisor(modelMapper.map(club.getAdvisor(), SimplifiedUserResponseDTO.class));
        }

        // Get fresh club from DB to ensure all collections are loaded
        club = clubsRespository.findById(club.getId()).orElse(club);
        
        // Manually map members collection
        List<ClubMemberResponseDTO> members = safelyConvertCollection(club.getMembers()).stream()
                .map(this::mapClubMemberToDTO)
                .toList();
        clubResponse.setMembers(members);

        // Manually map events collection
        if (club.getEvents() != null) {
            List<Event> eventList = safelyConvertCollection(club.getEvents());
            List<EventResponseDTO> eventDTOs = eventList.stream()
                    .map(this::mapEventToDTO)
                    .toList();
            clubResponse.setEvents(eventDTOs);
        } else {
            clubResponse.setEvents(new ArrayList<>());
        }

        // Map PlanOfAction if exists
        if (club.getPlanOfAction() != null) {
            clubResponse.setPlanOfAction(modelMapper.map(club.getPlanOfAction(), PlanOfActionDTO.class));
        }

        setUserMembershipDetails(clubResponse, club, user);
        
        return BaseResponse.success(clubResponse, "Club saved successfully", 1);
    }

    public ResponseEntity<BaseResponse<List<ClubResponseDTO>>> getAllClubs() {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String username = authentication.getName();
            currentUser = userRepository.findByUsername(username).orElse(null);
        }
        
        final User finalCurrentUser = currentUser; // Need final reference for lambda

        List<Club> clubs = clubsRespository.findAll();
        List<ClubResponseDTO> clubResponseDTOS = clubs.stream()
                .map(club -> {
                    // Create new DTO instead of using ModelMapper to avoid PersistentBag conversion issues
                    ClubResponseDTO dto = new ClubResponseDTO();
                    
                    // Manually map simple properties
                    dto.setId(club.getId());
                    dto.setName(club.getName());
                    dto.setBanner(club.getBanner());
                    dto.setLogo(club.getLogo());
                    dto.setDescription(club.getDescription());
                    dto.setDepartment(club.getDepartment());
                    dto.setActive(club.isActive());
                    
                    // Handle tags list
                    if (club.getTags() != null) {
                        dto.setTags(safelyConvertCollection(club.getTags()));
                    }
                    
                    // Manually map object properties
                    if (club.getCreatedBy() != null) {
                        dto.setCreatedBy(modelMapper.map(club.getCreatedBy(), SimplifiedUserResponseDTO.class));
                    }
                    
                    if (club.getAdvisor() != null) {
                        dto.setAdvisor(modelMapper.map(club.getAdvisor(), SimplifiedUserResponseDTO.class));
                    }
                    
                    // Manually map members collection
                    List<ClubMemberResponseDTO> members = safelyConvertCollection(club.getMembers()).stream()
                            .map(this::mapClubMemberToDTO)
                            .toList();
                    dto.setMembers(members);
                    
                    // Manually map events collection
                    if (club.getEvents() != null) {
                        List<Event> eventList = safelyConvertCollection(club.getEvents());
                        List<EventResponseDTO> eventDTOs = eventList.stream()
                                .map(this::mapEventToDTO)
                                .toList();
                        dto.setEvents(eventDTOs);
                    } else {
                        dto.setEvents(new ArrayList<>());
                    }
                    
                    // Map PlanOfAction if exists
                    if (club.getPlanOfAction() != null) {
                        dto.setPlanOfAction(modelMapper.map(club.getPlanOfAction(), PlanOfActionDTO.class));
                    }
                    
                    // Set user membership details
                    setUserMembershipDetails(dto, club, finalCurrentUser);
                    
                    return dto;
                })
                .toList();

        return BaseResponse.success(clubResponseDTOS, "Clubs fetched successfully", clubResponseDTOS.size());
    }

    public ResponseEntity<BaseResponse<ClubResponseDTO>> getClubById(Long id) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser")) {
            String username = authentication.getName();
            currentUser = userRepository.findByUsername(username).orElse(null);
        }

        Club club = clubsRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", id));

        // Create new DTO instead of using ModelMapper to avoid PersistentBag conversion issues
        ClubResponseDTO dto = new ClubResponseDTO();
        
        // Manually map simple properties
        dto.setId(club.getId());
        dto.setName(club.getName());
        dto.setBanner(club.getBanner());
        dto.setLogo(club.getLogo());
        dto.setDescription(club.getDescription());
        dto.setDepartment(club.getDepartment());
        dto.setActive(club.isActive());
        
        // Handle tags list
        if (club.getTags() != null) {
            dto.setTags(safelyConvertCollection(club.getTags()));
        }
        
        // Manually map object properties
        if (club.getCreatedBy() != null) {
            dto.setCreatedBy(modelMapper.map(club.getCreatedBy(), SimplifiedUserResponseDTO.class));
        }
        
        if (club.getAdvisor() != null) {
            dto.setAdvisor(modelMapper.map(club.getAdvisor(), SimplifiedUserResponseDTO.class));
        }
        
        // Manually map members collection
        List<ClubMemberResponseDTO> members = safelyConvertCollection(club.getMembers()).stream()
                .map(this::mapClubMemberToDTO)
                .toList();
        dto.setMembers(members);
        
        // Manually map events collection
        if (club.getEvents() != null) {
            List<Event> eventList = safelyConvertCollection(club.getEvents());
            List<EventResponseDTO> eventDTOs = eventList.stream()
                    .map(this::mapEventToDTO)
                    .toList();
            dto.setEvents(eventDTOs);
        } else {
            dto.setEvents(new ArrayList<>());
        }
        
        // Map PlanOfAction if exists
        if (club.getPlanOfAction() != null) {
            dto.setPlanOfAction(modelMapper.map(club.getPlanOfAction(), PlanOfActionDTO.class));
        }
        
        // Set user membership details
        setUserMembershipDetails(dto, club, currentUser);
        
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

    /**
     * Utility method to safely convert any collection (including Hibernate collections) to a regular ArrayList
     * to avoid Hibernate proxy-related issues
     */
    private <T> List<T> safelyConvertCollection(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        
        // Safely detach from any Hibernate proxies by creating a new ArrayList
        return new ArrayList<>(collection);
    }

    /**
     * Maps an event to an EventResponseDTO with safe handling of collections
     */
    private EventResponseDTO mapEventToDTO(Event event) {
        if (event == null) {
            return null;
        }
        
        EventResponseDTO dto = modelMapper.map(event, EventResponseDTO.class);
        
        // Manually map collections to avoid Hibernate proxy issues
        if (event.getTheme() != null) {
            dto.setTheme(safelyConvertCollection(event.getTheme()));
        }
        
        if (event.getOtherSpeakers() != null) {
            dto.setOtherSpeakers(safelyConvertCollection(event.getOtherSpeakers()));
        }
        
        return dto;
    }

    /**
     * Maps a ClubMember to a ClubMemberResponseDTO with safe handling of collections
     */
    private ClubMemberResponseDTO mapClubMemberToDTO(ClubMember member) {
        if (member == null) {
            return null;
        }
        
        ClubMemberResponseDTO dto = new ClubMemberResponseDTO();
        
        // Map fields explicitly since ModelMapper might have issues
        dto.setClubId(member.getClub().getId());
        dto.setClubName(member.getClub().getName());
        dto.setRollNo(member.getRollNo());
        dto.setRole(member.getRole());
        dto.setUserName(member.getUser().getUsername());
        
        return dto;
    }

    private void setUserMembershipDetails(ClubResponseDTO dto, Club club, User currentUser) {
        // Default values
        dto.setUserMember(false);
        dto.setUserRole(null);
        dto.setCanEdit(false);
        
        if (currentUser == null) {
            return;
        }
        
        // Find if the current user is a member of this club
        ClubMember userMembership = clubMemberRepository.findByClubAndUser(club, currentUser).orElse(null);
        
        if (userMembership != null) {
            dto.setUserMember(true);
            dto.setUserRole(userMembership.getRole());
            
            // Check if user can edit (PRESIDENT, VICE_PRESIDENT, SECRETARY)
            ClubRoleEnum role = userMembership.getRole();
            boolean canEdit = role == ClubRoleEnum.PRESIDENT || 
                               role == ClubRoleEnum.VICE_PRESIDENT || role == ClubRoleEnum.ADMIN ||
                               
                               role == ClubRoleEnum.SECRETARY;
            dto.setCanEdit(canEdit);
        }
    }

}
