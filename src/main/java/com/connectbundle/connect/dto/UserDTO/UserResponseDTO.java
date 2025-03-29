package com.connectbundle.connect.dto.UserDTO;

import com.connectbundle.connect.dto.ClubsDTO.ClubMemberResponseDTO;
import com.connectbundle.connect.dto.PostsDTO.PostResponseDTO;
import com.connectbundle.connect.model.User.FacultyDetails;
import com.connectbundle.connect.model.User.SocialLinks;
import com.connectbundle.connect.model.User.StudentDetails;
import com.connectbundle.connect.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private String about;
    private String email;
    private String name;
    private String profilePicture;
    private String headline;
    private Role role;
    private boolean isActive;
    private List<PostResponseDTO> post;
    private SocialLinks socialLinks;
    private StudentDetails  studentDetails;
    private FacultyDetails facultyDetails;
    private List<String> achievement;
    private List<String> interest;
    private List<ClubMemberResponseDTO> clubMemberships;


}
