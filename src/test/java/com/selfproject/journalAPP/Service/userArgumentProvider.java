package com.selfproject.journalAPP.Service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.selfproject.journalAPP.entity.User;

public class userArgumentProvider implements ArgumentsProvider{

	@Override
	public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
		
		return Stream.of(
				
				Arguments.of(User.builder().userName("sham").password("sham").build()),
				Arguments.of(User.builder().userName("Nehal").password("").build())
				);
	}

}
 