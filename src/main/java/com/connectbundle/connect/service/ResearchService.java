package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PublicationDTO.PublicationDTO;
import com.connectbundle.connect.dto.PublicationDTO.ResearchProposalDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Publication.Publication;
import com.connectbundle.connect.model.Publication.ResearchProposal;
import com.connectbundle.connect.repository.PublicationRepository;
import com.connectbundle.connect.repository.ResearchProposalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResearchService {

    @Autowired
    private PublicationRepository publicationRepository;
    @Autowired
    private ResearchProposalRepository researchProposalRepository;
    @Autowired
    private  ModelMapper modelMapper;


    public ResponseEntity<BaseResponse<PublicationDTO>> getPublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        PublicationDTO publicationDTO = modelMapper.map(publication, PublicationDTO.class);
        return BaseResponse.success(publicationDTO, "Publication fetched successfully", 1);
    }

    public ResponseEntity<BaseResponse<PublicationDTO>> updatePublication(Long id, PublicationDTO publicationDTO) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        // Map the incoming DTO onto the existing entity
        modelMapper.map(publicationDTO, publication);
        publicationRepository.save(publication);
        PublicationDTO responseDTO = modelMapper.map(publication, PublicationDTO.class);
        return BaseResponse.success(responseDTO, "Publication updated successfully", 1);
    }

    public ResponseEntity<BaseResponse<Void>> deletePublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        publicationRepository.delete(publication);
        return BaseResponse.success(null, "Publication deleted successfully", 0);
    }

    public ResponseEntity<BaseResponse<ResearchProposalDTO>> getProposal(Long id) {
        ResearchProposal proposal = researchProposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResearchProposal", "id", id));
        ResearchProposalDTO proposalDTO = modelMapper.map(proposal, ResearchProposalDTO.class);
        return BaseResponse.success(proposalDTO, "Research Proposal fetched successfully", 1);
    }

    public ResponseEntity<BaseResponse<ResearchProposalDTO>> updateProposal(Long id, ResearchProposalDTO proposalDTO) {
        ResearchProposal proposal = researchProposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResearchProposal", "id", id));
        // Map the incoming DTO onto the existing entity
        modelMapper.map(proposalDTO, proposal);
        researchProposalRepository.save(proposal);
        ResearchProposalDTO responseDTO = modelMapper.map(proposal, ResearchProposalDTO.class);
        return BaseResponse.success(responseDTO, "Research Proposal updated successfully", 1);
    }

    public ResponseEntity<BaseResponse<Void>> deleteProposal(Long id) {
        ResearchProposal proposal = researchProposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResearchProposal", "id", id));
        researchProposalRepository.delete(proposal);
        return BaseResponse.success(null, "Research Proposal deleted successfully", 0);
    }

}
