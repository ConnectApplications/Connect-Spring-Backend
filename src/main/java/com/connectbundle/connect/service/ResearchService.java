package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.BaseResponse;
import com.connectbundle.connect.dto.PublicationDTO.CreatePublicationDTO;
import com.connectbundle.connect.dto.PublicationDTO.CreateResearchProposalDTO;
import com.connectbundle.connect.dto.PublicationDTO.PublicationDTO;
import com.connectbundle.connect.dto.PublicationDTO.ResearchProposalDTO;
import com.connectbundle.connect.dto.UserDTO.SimplifiedUserResponseDTO;
import com.connectbundle.connect.exception.ResourceNotFoundException;
import com.connectbundle.connect.model.Publication.Publication;
import com.connectbundle.connect.model.Publication.ResearchProposal;
import com.connectbundle.connect.model.User.User;
import com.connectbundle.connect.repository.PublicationRepository;
import com.connectbundle.connect.repository.ResearchProposalRepository;
import com.connectbundle.connect.repository.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<BaseResponse<PublicationDTO>> getPublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        
        // Manual mapping to avoid ModelMapper issues
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setType(publication.getType());
        publicationDTO.setNameOfAuthor(publication.getNameOfAuthor());
        publicationDTO.setAffiliationOfFirstAuthor(publication.getAffiliationOfFirstAuthor());
        publicationDTO.setNameOfOtherAuthors(publication.getNameOfOtherAuthors());
        publicationDTO.setAffiliationOfOtherAuthors(publication.getAffiliationOfOtherAuthors());
        publicationDTO.setScopusWebOfScience(publication.getScopusWebOfScience());
        publicationDTO.setChapterBookJournalConference(publication.getChapterBookJournalConference());
        publicationDTO.setTitleOfPaper(publication.getTitleOfPaper());
        publicationDTO.setTitleOfJournalConference(publication.getTitleOfJournalConference());
        publicationDTO.setVolumeAndIssue(publication.getVolumeAndIssue());
        publicationDTO.setMonthAndYearOfPublication(publication.getMonthAndYearOfPublication());
        publicationDTO.setPublisherOfJournal(publication.getPublisherOfJournal());
        publicationDTO.setDoiOrUrl(publication.getDoiOrUrl());
        publicationDTO.setPageNumbersOrArticleId(publication.getPageNumbersOrArticleId());
        publicationDTO.setImpactFactorIfAny(publication.getImpactFactorIfAny());
        
        // Map author separately to avoid conversion issues
        if (publication.getAuthor() != null) {
            publicationDTO.setAuthor(modelMapper.map(publication.getAuthor(), SimplifiedUserResponseDTO.class));
        }
        
        return BaseResponse.success(publicationDTO, "Publication fetched successfully", 1);
    }

    public ResponseEntity<BaseResponse<PublicationDTO>> createPublication(CreatePublicationDTO createDTO) {
        String username = createDTO.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Create and manually set fields
        Publication publication = new Publication();
        publication.setType(createDTO.getType());
        publication.setNameOfAuthor(createDTO.getNameOfAuthor());
        publication.setAffiliationOfFirstAuthor(createDTO.getAffiliationOfFirstAuthor());
        publication.setNameOfOtherAuthors(createDTO.getNameOfOtherAuthors());
        publication.setAffiliationOfOtherAuthors(createDTO.getAffiliationOfOtherAuthors());
        publication.setScopusWebOfScience(createDTO.getScopusWebOfScience());
        publication.setChapterBookJournalConference(createDTO.getChapterBookJournalConference());
        publication.setTitleOfPaper(createDTO.getTitleOfPaper());
        publication.setTitleOfJournalConference(createDTO.getTitleOfJournalConference());
        publication.setVolumeAndIssue(createDTO.getVolumeAndIssue());
        publication.setMonthAndYearOfPublication(createDTO.getMonthAndYearOfPublication());
        publication.setPublisherOfJournal(createDTO.getPublisherOfJournal());
        publication.setDoiOrUrl(createDTO.getDoiOrUrl());
        publication.setPageNumbersOrArticleId(createDTO.getPageNumbersOrArticleId());
        publication.setImpactFactorIfAny(createDTO.getImpactFactorIfAny());
        
        // Set author manually
        publication.setAuthor(user);
        
        Publication savedPublication = publicationRepository.save(publication);
        
        // Manual mapping to response DTO
        PublicationDTO savedPublicationDTO = new PublicationDTO();
        savedPublicationDTO.setId(savedPublication.getId());
        savedPublicationDTO.setType(savedPublication.getType());
        savedPublicationDTO.setNameOfAuthor(savedPublication.getNameOfAuthor());
        savedPublicationDTO.setAffiliationOfFirstAuthor(savedPublication.getAffiliationOfFirstAuthor());
        savedPublicationDTO.setNameOfOtherAuthors(savedPublication.getNameOfOtherAuthors());
        savedPublicationDTO.setAffiliationOfOtherAuthors(savedPublication.getAffiliationOfOtherAuthors());
        savedPublicationDTO.setScopusWebOfScience(savedPublication.getScopusWebOfScience());
        savedPublicationDTO.setChapterBookJournalConference(savedPublication.getChapterBookJournalConference());
        savedPublicationDTO.setTitleOfPaper(savedPublication.getTitleOfPaper());
        savedPublicationDTO.setTitleOfJournalConference(savedPublication.getTitleOfJournalConference());
        savedPublicationDTO.setVolumeAndIssue(savedPublication.getVolumeAndIssue());
        savedPublicationDTO.setMonthAndYearOfPublication(savedPublication.getMonthAndYearOfPublication());
        savedPublicationDTO.setPublisherOfJournal(savedPublication.getPublisherOfJournal());
        savedPublicationDTO.setDoiOrUrl(savedPublication.getDoiOrUrl());
        savedPublicationDTO.setPageNumbersOrArticleId(savedPublication.getPageNumbersOrArticleId());
        savedPublicationDTO.setImpactFactorIfAny(savedPublication.getImpactFactorIfAny());
        
        // Map author separately
        savedPublicationDTO.setAuthor(modelMapper.map(user, SimplifiedUserResponseDTO.class));
        
        return BaseResponse.success(savedPublicationDTO, "Publication created successfully", 1);
    }

    public ResponseEntity<BaseResponse<PublicationDTO>> updatePublication(Long id, PublicationDTO updateDTO) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        
        // Save reference to author to preserve relationship
        User author = publication.getAuthor();
        
        // Update fields manually to avoid conversion issues
        if (updateDTO.getType() != null) publication.setType(updateDTO.getType());
        if (updateDTO.getNameOfAuthor() != null) publication.setNameOfAuthor(updateDTO.getNameOfAuthor());
        if (updateDTO.getAffiliationOfFirstAuthor() != null) publication.setAffiliationOfFirstAuthor(updateDTO.getAffiliationOfFirstAuthor());
        if (updateDTO.getNameOfOtherAuthors() != null) publication.setNameOfOtherAuthors(updateDTO.getNameOfOtherAuthors());
        if (updateDTO.getAffiliationOfOtherAuthors() != null) publication.setAffiliationOfOtherAuthors(updateDTO.getAffiliationOfOtherAuthors());
        if (updateDTO.getScopusWebOfScience() != null) publication.setScopusWebOfScience(updateDTO.getScopusWebOfScience());
        if (updateDTO.getChapterBookJournalConference() != null) publication.setChapterBookJournalConference(updateDTO.getChapterBookJournalConference());
        if (updateDTO.getTitleOfPaper() != null) publication.setTitleOfPaper(updateDTO.getTitleOfPaper());
        if (updateDTO.getTitleOfJournalConference() != null) publication.setTitleOfJournalConference(updateDTO.getTitleOfJournalConference());
        if (updateDTO.getVolumeAndIssue() != null) publication.setVolumeAndIssue(updateDTO.getVolumeAndIssue());
        if (updateDTO.getMonthAndYearOfPublication() != null) publication.setMonthAndYearOfPublication(updateDTO.getMonthAndYearOfPublication());
        if (updateDTO.getPublisherOfJournal() != null) publication.setPublisherOfJournal(updateDTO.getPublisherOfJournal());
        if (updateDTO.getDoiOrUrl() != null) publication.setDoiOrUrl(updateDTO.getDoiOrUrl());
        if (updateDTO.getPageNumbersOrArticleId() != null) publication.setPageNumbersOrArticleId(updateDTO.getPageNumbersOrArticleId());
        if (updateDTO.getImpactFactorIfAny() != null) publication.setImpactFactorIfAny(updateDTO.getImpactFactorIfAny());
        
        // Ensure author relationship is preserved
        publication.setAuthor(author);
        
        Publication savedPublication = publicationRepository.save(publication);
        
        // Manual mapping to response DTO
        PublicationDTO responseDTO = new PublicationDTO();
        responseDTO.setId(savedPublication.getId());
        responseDTO.setType(savedPublication.getType());
        responseDTO.setNameOfAuthor(savedPublication.getNameOfAuthor());
        responseDTO.setAffiliationOfFirstAuthor(savedPublication.getAffiliationOfFirstAuthor());
        responseDTO.setNameOfOtherAuthors(savedPublication.getNameOfOtherAuthors());
        responseDTO.setAffiliationOfOtherAuthors(savedPublication.getAffiliationOfOtherAuthors());
        responseDTO.setScopusWebOfScience(savedPublication.getScopusWebOfScience());
        responseDTO.setChapterBookJournalConference(savedPublication.getChapterBookJournalConference());
        responseDTO.setTitleOfPaper(savedPublication.getTitleOfPaper());
        responseDTO.setTitleOfJournalConference(savedPublication.getTitleOfJournalConference());
        responseDTO.setVolumeAndIssue(savedPublication.getVolumeAndIssue());
        responseDTO.setMonthAndYearOfPublication(savedPublication.getMonthAndYearOfPublication());
        responseDTO.setPublisherOfJournal(savedPublication.getPublisherOfJournal());
        responseDTO.setDoiOrUrl(savedPublication.getDoiOrUrl());
        responseDTO.setPageNumbersOrArticleId(savedPublication.getPageNumbersOrArticleId());
        responseDTO.setImpactFactorIfAny(savedPublication.getImpactFactorIfAny());
        
        // Map author separately
        responseDTO.setAuthor(modelMapper.map(author, SimplifiedUserResponseDTO.class));
        
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
        
        // Manual mapping to avoid ModelMapper issues
        ResearchProposalDTO proposalDTO = new ResearchProposalDTO();
        proposalDTO.setId(proposal.getId());
        proposalDTO.setNameOfPI(proposal.getNameOfPI());
        proposalDTO.setAffiliationOfPI(proposal.getAffiliationOfPI());
        proposalDTO.setNameOfCoPI(proposal.getNameOfCoPI());
        proposalDTO.setAffiliationOfCoPI(proposal.getAffiliationOfCoPI());
        proposalDTO.setTitleOfProposal(proposal.getTitleOfProposal());
        proposalDTO.setResearchAgencySubmittedTo(proposal.getResearchAgencySubmittedTo());
        proposalDTO.setTitleOfResearchScheme(proposal.getTitleOfResearchScheme());
        proposalDTO.setPeriodOfProject(proposal.getPeriodOfProject());
        proposalDTO.setFundingRequest(proposal.getFundingRequest());
        proposalDTO.setDateOfSubmission(proposal.getDateOfSubmission());
        proposalDTO.setStatus(proposal.getStatus());
        proposalDTO.setCollaboratingAgency(proposal.getCollaboratingAgency());
        proposalDTO.setAbstractOfProject(proposal.getAbstractOfProject());
        proposalDTO.setReviewerCommitteeMembers(proposal.getReviewerCommitteeMembers());
        proposalDTO.setFundingAmount(proposal.getFundingAmount());
        proposalDTO.setDateOfApproval(proposal.getDateOfApproval());
        
        // Map author separately to avoid conversion issues
        if (proposal.getAuthor() != null) {
            proposalDTO.setAuthor(modelMapper.map(proposal.getAuthor(), SimplifiedUserResponseDTO.class));
        }
        
        return BaseResponse.success(proposalDTO, "Research Proposal fetched successfully", 1);
    }

    public ResponseEntity<BaseResponse<ResearchProposalDTO>> createProposal(CreateResearchProposalDTO createDTO) {
        String username = createDTO.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        
        // Create and manually set fields
        ResearchProposal proposal = new ResearchProposal();
        proposal.setNameOfPI(createDTO.getNameOfPI());
        proposal.setAffiliationOfPI(createDTO.getAffiliationOfPI());
        proposal.setNameOfCoPI(createDTO.getNameOfCoPI());
        proposal.setAffiliationOfCoPI(createDTO.getAffiliationOfCoPI());
        proposal.setTitleOfProposal(createDTO.getTitleOfProposal());
        proposal.setResearchAgencySubmittedTo(createDTO.getResearchAgencySubmittedTo());
        proposal.setTitleOfResearchScheme(createDTO.getTitleOfResearchScheme());
        proposal.setPeriodOfProject(createDTO.getPeriodOfProject());
        proposal.setFundingRequest(createDTO.getFundingRequest());
        proposal.setDateOfSubmission(createDTO.getDateOfSubmission());
        proposal.setStatus(createDTO.getStatus());
        proposal.setCollaboratingAgency(createDTO.getCollaboratingAgency());
        proposal.setAbstractOfProject(createDTO.getAbstractOfProject());
        proposal.setReviewerCommitteeMembers(createDTO.getReviewerCommitteeMembers());
        proposal.setFundingAmount(createDTO.getFundingAmount());
        proposal.setDateOfApproval(createDTO.getDateOfApproval());
        
        // Set author manually
        proposal.setAuthor(user);
        
        ResearchProposal savedProposal = researchProposalRepository.save(proposal);
        
        // Manual mapping to response DTO
        ResearchProposalDTO savedProposalDTO = new ResearchProposalDTO();
        savedProposalDTO.setId(savedProposal.getId());
        savedProposalDTO.setNameOfPI(savedProposal.getNameOfPI());
        savedProposalDTO.setAffiliationOfPI(savedProposal.getAffiliationOfPI());
        savedProposalDTO.setNameOfCoPI(savedProposal.getNameOfCoPI());
        savedProposalDTO.setAffiliationOfCoPI(savedProposal.getAffiliationOfCoPI());
        savedProposalDTO.setTitleOfProposal(savedProposal.getTitleOfProposal());
        savedProposalDTO.setResearchAgencySubmittedTo(savedProposal.getResearchAgencySubmittedTo());
        savedProposalDTO.setTitleOfResearchScheme(savedProposal.getTitleOfResearchScheme());
        savedProposalDTO.setPeriodOfProject(savedProposal.getPeriodOfProject());
        savedProposalDTO.setFundingRequest(savedProposal.getFundingRequest());
        savedProposalDTO.setDateOfSubmission(savedProposal.getDateOfSubmission());
        savedProposalDTO.setStatus(savedProposal.getStatus());
        savedProposalDTO.setCollaboratingAgency(savedProposal.getCollaboratingAgency());
        savedProposalDTO.setAbstractOfProject(savedProposal.getAbstractOfProject());
        savedProposalDTO.setReviewerCommitteeMembers(savedProposal.getReviewerCommitteeMembers());
        savedProposalDTO.setFundingAmount(savedProposal.getFundingAmount());
        savedProposalDTO.setDateOfApproval(savedProposal.getDateOfApproval());
        
        // Map author separately
        savedProposalDTO.setAuthor(modelMapper.map(user, SimplifiedUserResponseDTO.class));
        
        return BaseResponse.success(savedProposalDTO, "Research proposal created successfully", 1);
    }

    public ResponseEntity<BaseResponse<ResearchProposalDTO>> updateProposal(Long id, ResearchProposalDTO updateDTO) {
        ResearchProposal proposal = researchProposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResearchProposal", "id", id));
        
        // Save reference to author to preserve relationship
        User author = proposal.getAuthor();
        
        // Update fields manually to avoid conversion issues
        if (updateDTO.getNameOfPI() != null) proposal.setNameOfPI(updateDTO.getNameOfPI());
        if (updateDTO.getAffiliationOfPI() != null) proposal.setAffiliationOfPI(updateDTO.getAffiliationOfPI());
        if (updateDTO.getNameOfCoPI() != null) proposal.setNameOfCoPI(updateDTO.getNameOfCoPI());
        if (updateDTO.getAffiliationOfCoPI() != null) proposal.setAffiliationOfCoPI(updateDTO.getAffiliationOfCoPI());
        if (updateDTO.getTitleOfProposal() != null) proposal.setTitleOfProposal(updateDTO.getTitleOfProposal());
        if (updateDTO.getResearchAgencySubmittedTo() != null) proposal.setResearchAgencySubmittedTo(updateDTO.getResearchAgencySubmittedTo());
        if (updateDTO.getTitleOfResearchScheme() != null) proposal.setTitleOfResearchScheme(updateDTO.getTitleOfResearchScheme());
        if (updateDTO.getPeriodOfProject() != null) proposal.setPeriodOfProject(updateDTO.getPeriodOfProject());
        if (updateDTO.getFundingRequest() != null) proposal.setFundingRequest(updateDTO.getFundingRequest());
        if (updateDTO.getDateOfSubmission() != null) proposal.setDateOfSubmission(updateDTO.getDateOfSubmission());
        if (updateDTO.getStatus() != null) proposal.setStatus(updateDTO.getStatus());
        if (updateDTO.getCollaboratingAgency() != null) proposal.setCollaboratingAgency(updateDTO.getCollaboratingAgency());
        if (updateDTO.getAbstractOfProject() != null) proposal.setAbstractOfProject(updateDTO.getAbstractOfProject());
        if (updateDTO.getReviewerCommitteeMembers() != null) proposal.setReviewerCommitteeMembers(updateDTO.getReviewerCommitteeMembers());
        if (updateDTO.getFundingAmount() != null) proposal.setFundingAmount(updateDTO.getFundingAmount());
        if (updateDTO.getDateOfApproval() != null) proposal.setDateOfApproval(updateDTO.getDateOfApproval());
        
        // Ensure author relationship is preserved
        proposal.setAuthor(author);
        
        ResearchProposal savedProposal = researchProposalRepository.save(proposal);
        
        // Manual mapping to response DTO
        ResearchProposalDTO responseDTO = new ResearchProposalDTO();
        responseDTO.setId(savedProposal.getId());
        responseDTO.setNameOfPI(savedProposal.getNameOfPI());
        responseDTO.setAffiliationOfPI(savedProposal.getAffiliationOfPI());
        responseDTO.setNameOfCoPI(savedProposal.getNameOfCoPI());
        responseDTO.setAffiliationOfCoPI(savedProposal.getAffiliationOfCoPI());
        responseDTO.setTitleOfProposal(savedProposal.getTitleOfProposal());
        responseDTO.setResearchAgencySubmittedTo(savedProposal.getResearchAgencySubmittedTo());
        responseDTO.setTitleOfResearchScheme(savedProposal.getTitleOfResearchScheme());
        responseDTO.setPeriodOfProject(savedProposal.getPeriodOfProject());
        responseDTO.setFundingRequest(savedProposal.getFundingRequest());
        responseDTO.setDateOfSubmission(savedProposal.getDateOfSubmission());
        responseDTO.setStatus(savedProposal.getStatus());
        responseDTO.setCollaboratingAgency(savedProposal.getCollaboratingAgency());
        responseDTO.setAbstractOfProject(savedProposal.getAbstractOfProject());
        responseDTO.setReviewerCommitteeMembers(savedProposal.getReviewerCommitteeMembers());
        responseDTO.setFundingAmount(savedProposal.getFundingAmount());
        responseDTO.setDateOfApproval(savedProposal.getDateOfApproval());
        
        // Map author separately
        responseDTO.setAuthor(modelMapper.map(author, SimplifiedUserResponseDTO.class));
        
        return BaseResponse.success(responseDTO, "Research Proposal updated successfully", 1);
    }

    public ResponseEntity<BaseResponse<Void>> deleteProposal(Long id) {
        ResearchProposal proposal = researchProposalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResearchProposal", "id", id));
        researchProposalRepository.delete(proposal);
        return BaseResponse.success(null, "Research Proposal deleted successfully", 0);
    }
}
