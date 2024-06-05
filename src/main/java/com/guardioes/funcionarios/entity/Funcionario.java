package com.guardioes.funcionarios.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "funcionarios")
public class Funcionario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    @CPF
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    private Boolean ativo = true;

    public enum Cargo {
        GERENTE,
        VENDEDOR,
        CAIXA
    }

}