package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PublicationDTO.PublicationDTO;
import com.connectbundle.connect.dto.PublicationDTO.ResearchProposalDTO;
import com.connectbundle.connect.service.ResearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
public class ResearchController {

    @Autowired
    private  ResearchService researchService;


    @GetMapping("/publications/{id}")
    public ResponseEntity<BaseResponse<PublicationDTO>> getPublication(@PathVariable Long id) {
        return researchService.getPublication(id);
    }

    @PatchMapping("/publications/{id}")
    public ResponseEntity<BaseResponse<PublicationDTO>> updatePublication(@PathVariable Long id, @RequestBody PublicationDTO publicationDTO) {
        return researchService.updatePublication(id, publicationDTO);
    }

    @DeleteMapping("/publications/{id}")
    public ResponseEntity<BaseResponse<Void>> deletePublication(@PathVariable Long id) {
        return researchService.deletePublication(id);
    }

    @GetMapping("/proposals/{id}")
    public ResponseEntity<BaseResponse<ResearchProposalDTO>> getProposal(@PathVariable Long id) {
        return researchService.getProposal(id);
    }

    @PatchMapping("/proposals/{id}")
    public ResponseEntity<BaseResponse<ResearchProposalDTO>> updateProposal(@PathVariable Long id, @RequestBody ResearchProposalDTO proposalDTO) {
        return researchService.updateProposal(id, proposalDTO);
    }

    @DeleteMapping("/proposals/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteProposal(@PathVariable Long id) {
        return researchService.deleteProposal(id);
    }
}
