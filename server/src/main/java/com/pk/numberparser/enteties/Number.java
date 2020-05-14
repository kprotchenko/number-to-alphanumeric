package com.pk.numberparser.enteties;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Number {
    @Id
    @GeneratedValue
    private Long id;
    private @NonNull String number;
    private @NonNull String name;
    
    @JsonInclude()
    @Transient
    private Set<String> letterCombinations = new HashSet();
    @JsonInclude()
    @Transient
    private String letterCombinationsString;

    @JsonInclude()
    @Transient
    private Long letterCombinationsSize;

    @JsonInclude()
    @Transient
    private String stopWatchReportOnLetterCombination;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<String> getLetterCombinations() {
        return letterCombinations;
    }

    public void setLetterCombinations(Set<String> letterCombinations) {
        this.letterCombinations = letterCombinations;
    }

    public String getLetterCombinationsString() {
        return letterCombinationsString;
    }

    public void setLetterCombinationsString(String letterCombinationsString) {
        this.letterCombinationsString = letterCombinationsString;
    }

    public Long getLetterCombinationsSize() {
        return letterCombinationsSize;
    }

    public void setLetterCombinationsSize(Long letterCombinationsSize) {
        this.letterCombinationsSize = letterCombinationsSize;
    }

    public String getStopWatchReportOnLetterCombination() {
        return stopWatchReportOnLetterCombination;
    }

    public void setStopWatchReportOnLetterCombination(String stopWatchReportOnLetterCombination) {
        this.stopWatchReportOnLetterCombination = stopWatchReportOnLetterCombination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}