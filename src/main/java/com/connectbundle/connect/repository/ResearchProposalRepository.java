package com.connectbundle.connect.repository;

import com.connectbundle.connect.model.Publication.ResearchProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResearchProposalRepository extends JpaRepository<ResearchProposal, Long> {
}
