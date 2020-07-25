package com.mashup.ootd.web.controller.style;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mashup.ootd.domain.style.dto.StyleResponse;
import com.mashup.ootd.domain.style.service.StyleService;
import com.mashup.ootd.web.message.OotdResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/styles")
public class StyleController {
	
	private final StyleService styleService;
	
	@GetMapping
	public ResponseEntity<OotdResponse<List<StyleResponse>>> list() {
		List<StyleResponse> response = styleService.list();
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(OotdResponse.<List<StyleResponse>>builder()
						.code(HttpStatus.OK.value())
						.msg("스타일 정보 반환")
						.data(response)
						.build());
	}

}
