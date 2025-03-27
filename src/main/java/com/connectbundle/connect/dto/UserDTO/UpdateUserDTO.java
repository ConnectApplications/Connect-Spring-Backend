package com.connectbundle.connect.dto.UserDTO;

import com.connectbundle.connect.model.User.FacultyDetails;
import com.connectbundle.connect.model.User.SocialLinks;
import com.connectbundle.connect.model.User.StudentDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String name;
    private String headline;
    private String about;
    private String location;
    private String industry;
    private String currentPosition;

    private List<String> achievement;
    private List<String> interest;

    private SocialLinks socialLinks;

    private StudentDetails studentDetails;
    private FacultyDetails facultyDetails;
}
