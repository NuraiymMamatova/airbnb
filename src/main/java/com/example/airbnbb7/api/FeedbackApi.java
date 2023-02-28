package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
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
@CrossOrigin
public class FeedbackApi {

    private final FeedbackService feedbackService;

    @PostMapping("/{houseId}")
    @Operation(summary = "SAVE FEEDBACK", description = "THIS ENDPOINT RESPONSIBLE FOR SAVING FEEDBACK")
    public SimpleResponse save(Authentication authentication, @RequestBody FeedbackRequestForSave feedbackRequestForSave,
                               @PathVariable Long houseId) {

        return feedbackService.saveFeedback(feedbackRequestForSave, houseId, authentication);
    }

    @DeleteMapping("/delete/{feedbackId}")
    @Operation(summary = "DELETE FEEDBACK", description = "THIS ENDPOINT RESPONSIBLE FOR DELETING FEEDBACK")
    public SimpleResponse delete(@PathVariable Long feedbackId, Authentication authentication) {
        return feedbackService.deleteFeedback(authentication, feedbackId);
    }

    @PutMapping("/update/{feedbackId}")
    @Operation(summary = "UPDATE FEEDBACK", description = "THIS ENDPOINT RESPONSIBLE FOR UPDATING FEEDBACK")
    public SimpleResponse update(@PathVariable Long feedbackId,
                                 @RequestBody FeedbackRequestForUpdate feedbackRequest,
                                 @RequestParam(required = false) boolean like,
                                 @RequestParam(required = false) boolean dislike,
                                 Authentication authentication) {
        return feedbackService.updateFeedback(authentication, feedbackId, feedbackRequest, like, dislike);
    }

}
