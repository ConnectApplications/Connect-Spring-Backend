package com.connectbundle.connect.model.Publication;

import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.model.enums.ProposalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "research_proposals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResearchProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameOfPI;
    private String affiliationOfPI;
    private String nameOfCoPI;
    private String affiliationOfCoPI;
    private String titleOfProposal;
    private String researchAgencySubmittedTo;
    private String titleOfResearchScheme;
    private String periodOfProject;
    private String fundingRequest;
    private String dateOfSubmission;

    private String collaboratingAgency;
    private String abstractOfProject;
    private String reviewerCommitteeMembers;

    @Enumerated(EnumType.STRING)
    private ProposalStatus status = ProposalStatus.PENDING;

    private String fundingAmount;
    private String dateOfApproval;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;
}
