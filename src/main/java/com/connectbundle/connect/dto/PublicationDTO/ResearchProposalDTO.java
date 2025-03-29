package com.connectbundle.connect.dto.PublicationDTO;

import com.connectbundle.connect.dto.UserDTO.SimplifiedUserResponseDTO;
import com.connectbundle.connect.model.enums.ProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchProposalDTO {
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
    private ProposalStatus status;
    private String collaboratingAgency;
    private String abstractOfProject;
    private String reviewerCommitteeMembers;
     private String fundingAmount;
    private String dateOfApproval;

    // Simplified author information (e.g., the user who created the proposal)
    private SimplifiedUserResponseDTO author;
}
