package com.example.zoom.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

/**
 * The Validation Processor.
 */
public class ValidationProcessor {

    /**
     * The process Binding Result.
     *
     * @param bindingResult bindingResult
     * @return list of String
     */
    public static List<String> processBindingResult(final BindingResult bindingResult) {
        List<String> errorMessage = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.add(error.getDefaultMessage());
            }
        }
        return errorMessage;
    }
}
