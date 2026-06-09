package me.seonkyukim.springbootdeveloper.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import me.seonkyukim.springbootdeveloper.dto.WritingSuggestionRequest;
import me.seonkyukim.springbootdeveloper.dto.WritingSuggestionsResponse;

@Service
public class WritingAssistantService {

	private final ChatClient chatClient;
	private final PromptTemplate template;
	
	private final BeanOutputConverter<WritingSuggestionsResponse> outputConverter = 
					new BeanOutputConverter<>(WritingSuggestionsResponse.class);
	
	public WritingAssistantService ( ChatClient.Builder chatClientBuilder,
								   @Value("classpath:prompts/writing-assistant.st") Resource promptResource
								 ) {
									 this.chatClient = chatClientBuilder.build();
									 this.template = new PromptTemplate(promptResource);
	}
	
	public WritingSuggestionsResponse getWritingAssist(WritingSuggestionRequest request) {
		
		String prompt = template.create(request.toMap(outputConverter.getFormat())).getContents();
	
		String response = chatClient.prompt().user(prompt).call().content();
		
		return outputConverter.convert(response);
	}
}
