package com.pk.numberparser.controllers;

import com.pk.numberparser.repositories.PhoneNumberRepository;
import com.pk.numberparser.service.PhoneNumberService;
import com.pk.numberparser.service.StopWatch;
import com.pk.numberparser.enteties.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
class PhoneNumberController {
    private PhoneNumberRepository phoneNumberRepository;
    private PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberRepository phoneNumberRepository, PhoneNumberService phoneNumberService) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping("/get-phone-numbers")
    @CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody Collection<Number> getAllSavedPhoneNumbers() {
        return phoneNumberRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/get-real-number/{alphaNumeric}")
    @CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody String convertAlphanumericToRealNumber(@PathVariable String alphaNumeric) {
        StopWatch stopWatch = new StopWatch("getNumericCombination");
        stopWatch.start();
        try {
            return phoneNumberService.numberCombination(alphaNumeric);
        } finally {
            stopWatch.stop();
            stopWatch.prettyPrint();
        }
    }

    @GetMapping(value = "/get-letter-combinations/{phoneNumber}")
    @CrossOrigin(origins = "http://localhost:4200")
    public @ResponseBody Collection<String> getLetterCombinationsForANumber(@PathVariable String phoneNumber) {
        StopWatch stopWatch = new StopWatch("getAlphanumericCombinations");
        stopWatch.start();
        try {
            return phoneNumberService.letterCombinations(phoneNumber);
        } finally {
            stopWatch.stop();
            stopWatch.prettyPrint();
        }
    }

    @GetMapping(value = "/get-number/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Number> getNumberById(@PathVariable Long id) {
        return getNumber(id)
                .map(existing-> ResponseEntity.ok().body(existing))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/save-number")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Number> saveNumber(@RequestBody Number number) {
        Number numberResult = phoneNumberRepository.save(number);
        return getNumber(numberResult.getId())
                .map(existing-> ResponseEntity.status(HttpStatus.CREATED).body(existing))
                .orElse(ResponseEntity.notFound().build());
    }

    private Optional<Number> getNumber(@PathVariable Long id) {
        Collection<Long> singleIdList = new ArrayList<>();
        singleIdList.add(id);
        return phoneNumberRepository.findAllById(singleIdList)
                .stream()
                .map(this::addAlphanumerics)
                .findFirst().get();
    }


    private Optional<Number> addAlphanumerics(Number number) {
        StopWatch stopWatch = new StopWatch("getNumericCombination");
        Set<String> letterCombinations = new HashSet<>();
        try {
            stopWatch.start("conversion_for_number --> " + number.getName());
            letterCombinations = phoneNumberService.letterCombinations(number.getNumber());
        } finally {
            stopWatch.stop();
            number.setStopWatchReportOnLetterCombination(stopWatch.prettyPrint());
        }
        number.setLetterCombinationsString(letterCombinations.toString());
        number.setLetterCombinations(letterCombinations);
        number.setLetterCombinationsSize(Long.valueOf(letterCombinations.size()));
        return Optional.ofNullable(number);
    }
}