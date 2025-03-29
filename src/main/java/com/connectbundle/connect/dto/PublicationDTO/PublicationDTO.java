package com.connectbundle.connect.dto.PublicationDTO;

import com.connectbundle.connect.dto.UserDTO.SimplifiedUserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {
    private Long id;
    private String type;

    private String nameOfAuthor;
    private String affiliationOfFirstAuthor;
    private List<String> nameOfOtherAuthors;
    private String affiliationOfOtherAuthors;

    private String scopusWebOfScience;
    private String chapterBookJournalConference;

    private String titleOfPaper;
    private String titleOfJournalConference;
    private String volumeAndIssue;
    private String monthAndYearOfPublication;
    private String publisherOfJournal;
    private String doiOrUrl;
    private String pageNumbersOrArticleId;
    private String impactFactorIfAny;

    private SimplifiedUserResponseDTO author;
}
