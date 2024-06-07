package com.guardioes.funcionarios;

import com.guardioes.funcionarios.repository.FuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TestesIntegrados {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@BeforeEach
	public void setUp() {
		System.out.println("------------------CHEGOU ATÉ AQUI ---------------");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		funcionarioRepository.deleteAll();
	}

	@Test
	void testarCadastrarFuncionario() throws Exception {
		String funcionarioJson = "{\"nome\": \"João\", \"cpf\": \"12345678900\", \"cargo\": \"CAIXA\"}";

		mockMvc.perform(post("/api/v1/funcionarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(funcionarioJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome").value("João"))
				.andExpect(jsonPath("$.cpf").value("12345678900"))
				.andExpect(jsonPath("$.cargo").value("CAIXA"));
	}

	@Test
	void testarInativarFuncionario() throws Exception {
		String funcionarioJson = "{\"nome\": \"João\", \"cpf\": \"12345678900\", \"cargo\": \"CAIXA\"}";

		mockMvc.perform(post("/api/v1/funcionarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(funcionarioJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.nome").value("João"))
				.andExpect(jsonPath("$.cpf").value("12345678900"))
				.andExpect(jsonPath("$.cargo").value("CAIXA"));

		mockMvc.perform(patch("/api/v1/funcionarios/inativar/12345678900"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.ativo").value(false));
	}

	@Test
	void testarCadastroCpfInvalido() throws Exception {
		String funcionarioJson = "{\"nome\": \"João\", \"cpf\": \"123456789\", \"cargo\": \"CAIXA\"}";

		mockMvc.perform(post("/api/v1/funcionarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(funcionarioJson))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("CPF inválido"));
	}

}