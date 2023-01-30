package com.example.airbnbb7.db.service;

import com.example.airbnbb7.dto.response.AnnouncementResponseForVendor;

public interface HouseService {

    AnnouncementService getHouse(Long houseId, Long userId);

    AnnouncementResponseForVendor getHouseForVendor(Long houseId);

}
