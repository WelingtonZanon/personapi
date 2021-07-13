package com.wz.personapi.controller;

import static com.wz.personapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wz.personapi.DTO.PersonDTO;
import com.wz.personapi.builder.PersonDTOBuilder;
import com.wz.personapi.services.PersonService;


@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

	private static final String PERSON_API_URL_PATH = "/person";
	
	private PageImpl<PersonDTO> page;

	private MockMvc mockMvc;
	
	PersonDTO personDTO;

	
	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(personController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((s, locale) -> new MappingJackson2JsonView()).build();	
		
		// given for all test
				personDTO = PersonDTOBuilder.builder().build().toPersonDTO();
	}

	@Test
	void whenPOSTIsCalledThenAPersonIsCreated() throws Exception {
		

		// when
		when(personService.insert(personDTO)).thenReturn(personDTO);

		// then
		ResultActions result = mockMvc.perform(
				post(PERSON_API_URL_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(personDTO)));

		result.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.firstName", is(personDTO.getFirstName())))
			.andExpect(jsonPath("$.lastName", is(personDTO.getLastName())))
			.andExpect(jsonPath("$.cpf", is(personDTO.getCpf())));
	}
	
	 @Test
	    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
	        // given
	        personDTO.setFirstName(null);

	        // then
	        mockMvc.perform(post(PERSON_API_URL_PATH)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(asJsonString(personDTO)))
	                .andExpect(status().isBadRequest());
	    }
	    @Test
	    void whenGETIsCalledWithValidIdThenOkStatusIsReturned() throws Exception {
	        

	        //when
	        when(personService.findByID(personDTO.getId())).thenReturn(personDTO);

	        // then
	        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(PERSON_API_URL_PATH + "/" + personDTO.getId())
	                .contentType(MediaType.APPLICATION_JSON));
	        
	        result.andExpect(status().isOk())
	        .andExpect(jsonPath("$.firstName", is(personDTO.getFirstName())))
			.andExpect(jsonPath("$.lastName", is(personDTO.getLastName())))
			.andExpect(jsonPath("$.cpf", is(personDTO.getCpf())));
	    }
	    
	    @Test
	    void whenGETPageWithIsCalledThenOkStatusIsReturned() throws Exception {
	    	

	        // when	    	
			page = new PageImpl<>(List.of(personDTO));
			when(personService.findAllPaged(any())).thenReturn(page);
	    	
	    	ResultActions result = mockMvc.perform(get(PERSON_API_URL_PATH).accept(MediaType.APPLICATION_JSON));
			result.andExpect(status().isOk());

	        // then
			result.andExpect(jsonPath("$.content").exists());
	    }
	    
	    @Test
	    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
	       

	        //when
	        doNothing().when(personService).delete(personDTO.getId());

	        // then
	        mockMvc.perform(MockMvcRequestBuilders.delete(PERSON_API_URL_PATH + "/" + personDTO.getId())
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNoContent());
	    }
	    
//		@Test
//		public void updateShouldReturnPersonDTOWhenIdExist() throws Exception {
//			// given
//	    	PersonDTO personDTO = PersonDTOBuilder.builder().build().toPersonDTO(); 
//	    	String jsonBody = objectMapper.writeValueAsString(personDTO);
//			
//	    	// when
//			String expectdFirstName = personDTO.getFirstName();
//			String expectdLastName = personDTO.getLastName();		
//			
//			
//			// then 			
//			ResultActions result = mockMvc.perform(put("/person/{id}", VALID_PERSON_ID)
//					.content(jsonBody)
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON));			
//			
//			result.andExpect(status().isCreated())
//			.andExpect(jsonPath("$.id").exists())
//			.andExpect(jsonPath("$.id", is(VALID_PERSON_ID)))
//			.andExpect(jsonPath("$.firstName", is(expectdFirstName)))
//			.andExpect(jsonPath("$.lastName", is(expectdLastName)))
//			.andExpect(jsonPath("$.cpf", is(personDTO.getCpf())));
//		}


}
