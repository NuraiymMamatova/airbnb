package com.example.airbnbb7.db.customClass;

import com.example.airbnbb7.db.service.MasterInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SimpleResponse implements MasterInterface {

    private String message;

}
