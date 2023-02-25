package com.example.airbnbb7.db.customClass;

import com.example.airbnbb7.db.service.AnnouncementService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleResponse implements AnnouncementService {

    private String message;

}
