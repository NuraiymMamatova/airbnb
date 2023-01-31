package com.example.airbnbb7.api;

import com.example.airbnbb7.db.service.HouseService;
import com.example.airbnbb7.db.service.serviceImpl.HouseServiceImpl;
import com.example.airbnbb7.dto.response.HouseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house/" )
@Tag(name = "House Api",description = "House API")
public class HouseApi {

    private final HouseServiceImpl houseService;

    @GetMapping()
    public List<HouseResponse> getPopularHouses( Pageable pageable){
        return houseService.getPopularHouses(pageable);
    }
}
