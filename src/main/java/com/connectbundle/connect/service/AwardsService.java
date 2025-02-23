package com.connectbundle.connect.service;

import com.connectbundle.connect.dto.AwardsDTO.CreateAwardDTO;
import com.connectbundle.connect.model.Awards;
import com.connectbundle.connect.model.User;
import com.connectbundle.connect.repository.AwardsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AwardsService {

    @Autowired
    AwardsRepository awardsRepository;

    @Autowired
    UserService userService;

    public AwardServiceResponse<Awards> createAward(CreateAwardDTO award) {
        try {
            UserService.UserServiceResponse<User> getUser = userService.getUserByID(award.getRecipientID());
            if (getUser.isSuccess()) {
                Awards newAward = new Awards();
                newAward.setAward_name(award.getAwardName());
                newAward.setYear(award.getYear());
                newAward.setDescription(award.getDescription());
                newAward.setUser(getUser.getData());
                awardsRepository.save(newAward);
                return new AwardServiceResponse<>(true, "Award saved successfully", newAward);
            } else {
                return new AwardServiceResponse<>(false, "User not found", null);
            }
        } catch (Exception e) {
            return new AwardServiceResponse<>(false, e.getMessage(), null);
        }
    }

    public AwardServiceResponse<List<Awards>> getAllAwards() {
        try {
            List<Awards> allAwards = awardsRepository.findAll();
            return new AwardServiceResponse<>(true, "Awards fetched successfully", allAwards);
        } catch (Exception e) {
            return new AwardServiceResponse<>(false, e.getMessage(), null);
        }
    }

    // RESPONSE CLASS
    @Getter
    public static class AwardServiceResponse<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public AwardServiceResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
    }
}
