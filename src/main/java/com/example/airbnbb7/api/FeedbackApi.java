package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.entities.User;
import com.example.airbnbb7.db.service.FeedbackService;
import com.example.airbnbb7.dto.request.FeedbackRequestForSave;
import com.example.airbnbb7.dto.request.FeedbackRequestForUpdate;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FeedbackApi {

    private final FeedbackService feedbackService;

    @PostMapping("/{houseId}")
    @Operation(summary = "SAVE FEEDBACK" , description = "THIS ENDPOINT RESPONSIBLE FOR SAVING FEEDBACK")
    public SimpleResponse save(Authentication authentication, @RequestBody FeedbackRequestForSave feedbackRequestForSave,
                               @PathVariable Long houseId) {

        return feedbackService.saveFeedback(feedbackRequestForSave, houseId, authentication);
    }

    @DeleteMapping("/delete/{feedbackId}")
    @Operation(summary = "DELETE FEEDBACK" , description = "THIS ENDPOINT RESPONSIBLE FOR DELETING FEEDBACK")
    public SimpleResponse delete(@PathVariable Long feedbackId, Authentication authentication) {
        return feedbackService.deleteFeedback(authentication, feedbackId);
    }

    @PutMapping("/update/{feedbackId}")
    @Operation(summary = "UPDATE FEEDBACK" , description = "THIS ENDPOINT RESPONSIBLE FOR UPDATING FEEDBACK")
    public SimpleResponse update(@PathVariable Long feedbackId,
                                 @RequestBody FeedbackRequestForUpdate feedbackRequest,
                                 Authentication authentication) {
        return feedbackService.updateFeedback(authentication, feedbackId, feedbackRequest);
    }

    @PutMapping("/like/{feedbackId}")
    public void liking(@PathVariable Long feedbackId, Authentication authentication){
        feedbackService.liking(feedbackId,authentication);
    }

    @PutMapping("/disLike/{feedbackId}")
    public void disLiking(@PathVariable Long feedbackId,Authentication authentication){
        feedbackService.disLiking(feedbackId,authentication);
    }
}
