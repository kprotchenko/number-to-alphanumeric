package com.pk.numberparser.controllers;

import com.pk.numberparser.repositories.PhoneNumberRepository;
import com.pk.numberparser.service.PhoneNumberService;
import com.pk.numberparser.service.StopWatch;
import com.pk.numberparser.enteties.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
class PhoneNumberController {
    private PhoneNumberRepository phoneNumberRepository;
    @Autowired
    private PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @GetMapping("/get-phone-numbers")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Number> phoneNumbers() {
        return phoneNumberRepository.findAll().stream()
                .collect(Collectors.toList());
    }
    @RequestMapping(value = "/get-real-number/{alphaNumeric}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public String realNumber(@PathVariable String alphaNumeric) {
        StopWatch stopWatch = new StopWatch("getNumericCombination");
        stopWatch.start();
        try {

            String numeric = phoneNumberService.numberCombination(alphaNumeric);
            return numeric;
        } finally {
            stopWatch.stop();
            stopWatch.prettyPrint();
        }
    }

    @RequestMapping(value = "/get-number/{id}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public Number number(@PathVariable Long id) {
        return getNumber(id);
    }

    private Number getNumber(@PathVariable Long id) {
        Collection<Long> singleIdList = new ArrayList<>();
        singleIdList.add(id);
        return phoneNumberRepository.findAllById(singleIdList).stream().peek(number -> {
            StopWatch stopWatch = new StopWatch("getNumericCombination");
            Set<String> letterCombinations = new HashSet<>();
//            String numeric = number.getNumber();
//            if (StringUtils.isEmpty(numeric)) {
//                numeric = phoneNumberService.numberCombination(number.getName());
//                number.setNumber(numeric);
//            }
//            phoneNumberRepository.save(number);
            try {
                stopWatch.start("conversion_for_number --> "+number.getName());
                letterCombinations = phoneNumberService.letterCombinations(number.getNumber());
            } finally {
                stopWatch.stop();
                number.setStopWatchReportOnLetterCombination(stopWatch.prettyPrint());
            }
            number.setLetterCombinationsString(letterCombinations.toString());
            number.setLetterCombinations(letterCombinations);
            number.setLetterCombinationsSize(Long.valueOf(letterCombinations.size()));
        }).findFirst().get();
    }

    @RequestMapping(value = "/save-number", method = RequestMethod.POST)
    @CrossOrigin(origins = "http://localhost:4200")
    public Number saveNumber(@RequestBody Number number) {
//        StopWatch stopWatch = new StopWatch("getAlphanumericCombinations");
//        String numeric;
//        numeric = phoneNumberService.numberCombination(number.getName());
//        number.setNumber(numeric);
//        phoneNumberRepository.save(number);
        Number numberResult = phoneNumberRepository.save(number);
        return getNumber(numberResult.getId());
    }

    @RequestMapping(value = "/get-letter-combinations/{phoneNumber}", method = RequestMethod.GET)
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<String> getLetterCombinationsForANumber(@PathVariable String phoneNumber) {
        StopWatch stopWatch = new StopWatch("getAlphanumericCombinations");
        stopWatch.start();
        try {
            return phoneNumberService.letterCombinations(phoneNumber);
        } finally {
            stopWatch.stop();
            stopWatch.prettyPrint();;
        }
    }


}