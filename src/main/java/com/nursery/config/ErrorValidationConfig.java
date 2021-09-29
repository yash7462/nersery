package com.nursery.config;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import lombok.extern.java.Log;
@Log
@Service
public class ErrorValidationConfig {

	public Map<String, String> validateRequest(BindingResult result) {
		log.info("START--------validateRequest-----START");
		if (result.hasErrors()) {
			Map<String, String> map = new HashedMap<>();
			for (FieldError error : result.getFieldErrors()) {
				map.put(error.getField(), error.getDefaultMessage());
			}
			log.info("END-----validateRequest has error-----END");
			
			//return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);
			return map;
		} else {
			log.info("END-----validateRequest has no error-----END");
			return null;
		}

	}
}
