package com.mashup.ootd;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

public interface ApiDocumentUtils {
	
	static OperationRequestPreprocessor getDocumentRequest() {
		return preprocessRequest(
				modifyUris()
					.host("52.78.79.159")
					.removePort(),
				prettyPrint());
	}
	
	static OperationResponsePreprocessor getDocumentResponse() {
		return preprocessResponse(prettyPrint());
	}

}