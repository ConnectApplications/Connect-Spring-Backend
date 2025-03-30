package com.connectbundle.connect.dto.PublicationDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePublicationDTO {

    @NotNull(message = "Type is required")
    private String type;

    @NotNull(message = "Author is required")
    private String username;

    @NotNull(message = "Name of author is required")
    private String nameOfAuthor;
    private String affiliationOfFirstAuthor;
    private List<String> nameOfOtherAuthors;
    private String affiliationOfOtherAuthors;

    private String scopusWebOfScience;
    private String chapterBookJournalConference;

    @NotNull(message = "Title of paper is required")
    private String titleOfPaper;

    private String titleOfJournalConference;
    private String volumeAndIssue;
    private String monthAndYearOfPublication;
    private String publisherOfJournal;
    private String doiOrUrl;
    private String pageNumbersOrArticleId;
    private String impactFactorIfAny;


}
