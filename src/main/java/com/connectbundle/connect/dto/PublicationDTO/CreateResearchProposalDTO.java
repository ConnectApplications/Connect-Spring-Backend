package com.connectbundle.connect.dto.PublicationDTO;

import com.connectbundle.connect.model.enums.ProposalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateResearchProposalDTO {

    @NotNull(message = "Author is required")
    private String username;
    private String nameOfPI;
    private String affiliationOfPI;
    private String nameOfCoPI;
    private String affiliationOfCoPI;
    @NotNull(message = "Title of proposal is required")
    private String titleOfProposal;

    private String researchAgencySubmittedTo;
    private String titleOfResearchScheme;
    private String periodOfProject;
    private String fundingRequest;
    private String dateOfSubmission;
    private ProposalStatus status;
    private String collaboratingAgency;
    private String abstractOfProject;
    private String reviewerCommitteeMembers;
    private String fundingAmount;
    private String dateOfApproval;

}
