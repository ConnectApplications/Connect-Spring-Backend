package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PublicationDTO.CreatePublicationDTO;
import com.connectbundle.connect.dto.PublicationDTO.CreateResearchProposalDTO;
import com.connectbundle.connect.dto.PublicationDTO.PublicationDTO;
import com.connectbundle.connect.dto.PublicationDTO.ResearchProposalDTO;
import com.connectbundle.connect.service.ResearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
@Tag(name = "Research", description = "Research-related endpoints for publications and proposals")
public class ResearchController {

    @Autowired
    private ResearchService researchService;

    @PostMapping("/publications")
    @Operation(summary = "Create Publication", description = "Create a new research publication")
    public ResponseEntity<BaseResponse<PublicationDTO>> createPublication(@Valid @RequestBody CreatePublicationDTO publicationDTO) {
        return researchService.createPublication(publicationDTO);
    }

    @GetMapping("/publications/{id}")
    @Operation(summary = "Get Publication", description = "Get a research publication by ID")
    public ResponseEntity<BaseResponse<PublicationDTO>> getPublication(@PathVariable Long id) {
        return researchService.getPublication(id);
    }

    @PatchMapping("/publications/{id}")
    @Operation(summary = "Update Publication", description = "Update an existing research publication")
    public ResponseEntity<BaseResponse<PublicationDTO>> updatePublication(@PathVariable Long id, @RequestBody PublicationDTO publicationDTO) {
        return researchService.updatePublication(id, publicationDTO);
    }

    @DeleteMapping("/publications/{id}")
    @Operation(summary = "Delete Publication", description = "Delete a research publication")
    public ResponseEntity<BaseResponse<Void>> deletePublication(@PathVariable Long id) {
        return researchService.deletePublication(id);
    }

    @PostMapping("/proposals")
    @Operation(summary = "Create Research Proposal", description = "Create a new research proposal")
    public ResponseEntity<BaseResponse<ResearchProposalDTO>> createProposal(@Valid @RequestBody CreateResearchProposalDTO proposalDTO) {
        return researchService.createProposal(proposalDTO);
    }

    @GetMapping("/proposals/{id}")
    @Operation(summary = "Get Research Proposal", description = "Get a research proposal by ID")
    public ResponseEntity<BaseResponse<ResearchProposalDTO>> getProposal(@PathVariable Long id) {
        return researchService.getProposal(id);
    }

    @PatchMapping("/proposals/{id}")
    @Operation(summary = "Update Research Proposal", description = "Update an existing research proposal")
    public ResponseEntity<BaseResponse<ResearchProposalDTO>> updateProposal(@PathVariable Long id, @RequestBody ResearchProposalDTO proposalDTO) {
        return researchService.updateProposal(id, proposalDTO);
    }

    @DeleteMapping("/proposals/{id}")
    @Operation(summary = "Delete Research Proposal", description = "Delete a research proposal")
    public ResponseEntity<BaseResponse<Void>> deleteProposal(@PathVariable Long id) {
        return researchService.deleteProposal(id);
    }
}
