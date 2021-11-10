package com.getir.readingisgood.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/statistics",
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE},
        consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_XML_VALUE})
public class StatisticsController {
}
