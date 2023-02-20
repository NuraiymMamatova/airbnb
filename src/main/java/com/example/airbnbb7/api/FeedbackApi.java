package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
import com.example.airbnbb7.db.service.FeedbackService;
import com.example.airbnbb7.dto.request.FeedbackRequestForSave;
import com.example.airbnbb7.dto.request.FeedbackRequestForUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class FeedbackApi {

    private final FeedbackService feedbackService;

    @PostMapping("/{houseId}")
    public SimpleResponse save(Authentication authentication, @RequestBody FeedbackRequestForSave feedbackRequestForSave,
                               @PathVariable Long houseId) {

        return feedbackService.saveFeedback(feedbackRequestForSave, houseId, authentication);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public SimpleResponse delete(@PathVariable Long feedbackId, Authentication authentication) {
        return feedbackService.deleteFeedback(authentication, feedbackId);
    }

    @PutMapping("/update/{feedbackId}")
    public SimpleResponse update(@PathVariable Long feedbackId,
                                 @RequestBody FeedbackRequestForUpdate feedbackRequest,
                                 Authentication authentication) {
        return feedbackService.updateFeedback(authentication, feedbackId, feedbackRequest);
    }
}
