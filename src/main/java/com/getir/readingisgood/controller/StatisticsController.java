package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.request.StatRequestDTO;
import com.getir.readingisgood.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/statistics",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class StatisticsController {

        private final StatisticService statisticService;

        @PostMapping("/all")
        public ResponseEntity<?> statistic(@RequestBody StatRequestDTO statRequestDTO) {
                return ResponseEntity.ok().body(statisticService.statistic());
        }
}
