package com.connectbundle.connect.model.Publication;

import com.connectbundle.connect.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "publications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
}
