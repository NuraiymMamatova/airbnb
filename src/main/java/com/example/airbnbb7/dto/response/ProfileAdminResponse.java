package com.example.airbnbb7.dto.response;

import com.example.airbnbb7.db.service.MasterInterface;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
public class ProfileAdminResponse implements MasterInterface {

    private Long id;

    private String profileName;

    private String profileContact;

    private List<HouseResponseForAdminUsers> houseResponseForAdminUsers;

    public ProfileAdminResponse(Long id, String profileName, String profileContact) {
        this.id = id;
        this.profileName = profileName;
        this.profileContact = profileContact;
    }
}
