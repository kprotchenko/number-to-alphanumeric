package com.pk.numberparser.controllers;

import com.pk.numberparser.enteties.Number;
import com.pk.numberparser.repositories.PhoneNumberRepository;
import com.pk.numberparser.service.PhoneNumberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneNumberControllerTest {

    @Mock
    private PhoneNumberService phoneNumberService;

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @InjectMocks
    private PhoneNumberController phoneNumberController;

    @Test
    void getAllSavedPhoneNumbers() {
        Number number = createNumberOperater(Long.valueOf(111));
        final Collection<Number> output = new ArrayList<>();
        output.add(number);
        when(phoneNumberController.getAllSavedPhoneNumbers()).thenReturn(output);
        final Collection<Number> result = phoneNumberController.getAllSavedPhoneNumbers();
        assertArrayEquals(output.toArray(),result.toArray());
    }

    @Test
    void convertAlphanumericToRealNumber() {
        String alphanumeric = "1-800-OPERATER";
        String output = "18006737283";
        when(phoneNumberService.numberCombination(alphanumeric)).thenReturn(output);
        final String result = phoneNumberController.convertAlphanumericToRealNumber(alphanumeric);
        assertEquals(output,result);
    }

    @Test
    void getLetterCombinationsForANumber() {
        String numeric = "22";
        Set<String> output = getLetterCombinationsSet();
        when(phoneNumberService.letterCombinations(numeric)).thenReturn(output);
        final Collection<String> result = phoneNumberController.getLetterCombinationsForANumber(numeric);
        assertArrayEquals(output.toArray(),result.toArray());
    }



    @Test
    void getNumberById() {
        final Number output = prepOutputForUpdateOrSave();
        final ResponseEntity<Number> result = phoneNumberController.getNumberById(Long.valueOf(22));
        assertEquals(HttpStatus.OK,result.getStatusCode());
        checkGetNumberAssertions(output, result);
    }

    @Test
    void saveNumber() {
        final Number input = createNumber(Long.valueOf(22));
        final Number output = prepOutputForUpdateOrSave();
        when(phoneNumberRepository.save(input)).thenReturn(input);
        final ResponseEntity<Number> result = phoneNumberController.saveNumber(input);
        assertEquals(HttpStatus.CREATED,result.getStatusCode());
        checkGetNumberAssertions(output, result);
    }

    private Number prepOutputForUpdateOrSave() {
        final Number output = createNumber(Long.valueOf(22));
        final Collection<Number> outputList = new ArrayList<>();
        outputList.add(output);
        Collection<Long> singleIdList = new ArrayList<>();
        singleIdList.add(output.getId());
        when(phoneNumberRepository.findAllById(singleIdList)).thenReturn((List<Number>) outputList);
        when(phoneNumberService.letterCombinations(output.getNumber())).thenReturn(getLetterCombinationsSet());
        output.setLetterCombinations(getLetterCombinationsSet());
        output.setLetterCombinationsString(output.getLetterCombinations().toString());
        return output;
    }

    private void checkGetNumberAssertions(Number output, ResponseEntity<Number> result) {
        assertNotNull(result.getBody());
        assertNotNull(result.getBody().getStopWatchReportOnLetterCombination());
        assertEquals(output.getId(), result.getBody().getId());
        assertEquals(output.getNumber(), result.getBody().getNumber());
        assertNotNull(result.getBody().getLetterCombinations());
        assertArrayEquals(output.getLetterCombinations().toArray(), result.getBody().getLetterCombinations().toArray());
    }

    private Number createNumberOperater(Long id) {
        Number number = new Number();
        number.setId(id);
        number.setName(String.format("1-%s-OPERATER",id.toString()));
        number.setNumber(String.format("1-%s-673-7283",id.toString()));
        return number;
    }
    private Number createNumber(Long id) {
        Number number = new Number();
        number.setId(id);
        number.setName(id.toString());
        number.setNumber(id.toString());
        return number;
    }

    private Set<String> getLetterCombinationsSet() {
        Set<String> output = new HashSet<>();
        output.add("22");
        output.add("aa");
        output.add("bb");
        output.add("cc");
        output.add("ab");
        output.add("bc");
        output.add("ac");
        output.add("2a");
        output.add("c2");
        output.add("2b");
        output.add("b2");
        output.add("2c");
        output.add("a2");
        output.add("ca");
        output.add("ba");
        output.add("cb");
        return output;
    }

}