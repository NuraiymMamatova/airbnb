package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.FeedbackService;
import com.example.airbnbb7.dto.request.FeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback")
public class FeedbackApi {

    private final FeedbackService feedbackService;

    @PostMapping("/{houseId}")
    public String save(@RequestBody FeedbackRequest feedbackRequest,
                       @PathVariable Long houseId) {
        feedbackService.saveFeedback(feedbackRequest, houseId);
        return "Feedback successfully leaved!";
    }

    @DeleteMapping("/delete/{feedbackId}")
    public String delete(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return "Feedback successfully deleted!";
    }

    @PutMapping("/update/{feedbackId}")
    public String update(@PathVariable Long feedbackId,
                         @RequestBody FeedbackRequest feedbackRequest) {
        feedbackService.updateFeedback(feedbackId, feedbackRequest);
        return "Feedback successfully updated!";
    }
}
