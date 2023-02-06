package com.example.airbnbb7.api;

import com.example.airbnbb7.db.customClass.SimpleResponse;
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
    public SimpleResponse save(@RequestBody FeedbackRequest feedbackRequest,
                     @PathVariable Long houseId){
        return feedbackService.saveFeedback(feedbackRequest,houseId);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public SimpleResponse delete(@PathVariable Long feedbackId){
        return feedbackService.deleteFeedback(feedbackId);
    }

    @PutMapping("/update/{feedbackId}")
    public SimpleResponse update(@PathVariable Long feedbackId,
                         @RequestBody FeedbackRequest feedbackRequest){
        return feedbackService.updateFeedback(feedbackId,feedbackRequest);
    }
}
