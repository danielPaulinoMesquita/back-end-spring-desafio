package com.testedesafio.demo.api.validation;


import com.testedesafio.demo.api.dto.ClienteDTO;
import com.testedesafio.demo.api.dto.TelefoneDTO;
import com.testedesafio.demo.model.Telefone;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ClienteValidator implements Validator {

    private static final String CPF_REGEX = "(\\d{3})\\.(\\d{3})\\.(\\d{3})\\-(\\d{2})";
    private static final String EMAIL_REGEX = "^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$";

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        ClienteDTO cliente = (ClienteDTO) o;

        errors.addAllErrors(validatorNome(cliente.getNome(), errors));
        errors.addAllErrors(validatorCpf(cliente.getCpf(), errors));
        errors.addAllErrors(validatorTelefones(cliente.getTelefones(),errors));
        errors.addAllErrors(validatorEmails(cliente.getEmails(),errors));
        validatorEndereco(cliente,errors);

    }

    private Errors validatorNome(String nome, Errors errors){
        if(StringUtils.isEmpty(nome)){
            errors.rejectValue("Nome", "Campo obrigátorio");
            return errors;
        }

        if(nome.length() <= 3 ){
            errors.rejectValue("Nome", "Campo deve ter no minimo 3 caracteres");
        }

        if (nome.length() > 100){
            errors.rejectValue("Nome", "Campo deve ter no máximo 100 caracteres");
        }

        return errors;
    }

    private Errors validatorCpf(String cpf, Errors errors){
        if(StringUtils.isEmpty(cpf)){
            errors.rejectValue("Cpf", "Campo obrigátorio");
            return errors;
        }

        if(!cpf.matches(CPF_REGEX)){
            errors.rejectValue("Cpf", "Cpf incorreto");
        }

        return errors;
    }

    private Errors validatorTelefones(Set<TelefoneDTO> telefones, Errors errors){
        if (! (telefones.size() >= 1)){
            errors.rejectValue("Telefone", "Ao menos um telefone deve ser cadastrado");
            return errors;
        }

        telefones.forEach(telefone -> {
            if (telefone.getNumero().isEmpty()){
                errors.rejectValue("Numero do Telefone", "Campo obrigatório");
            }
        });

        return errors;
    }

    private Errors validatorEmails(Set<String> emails, Errors errors){
        if (! (emails.size() >= 1)){
            errors.rejectValue("Email", "Ao menos um email deve ser cadastrado");
            return errors;
        }

        emails.forEach(email -> {
           if (email.isEmpty()){
               errors.rejectValue("Email", "Deve ser Preenchido");
           }

           if(email.matches(EMAIL_REGEX)){
               errors.rejectValue("Email", "Incorreto");
           }
        });

        return errors;
    }

    private void validatorEndereco(ClienteDTO cliente, Errors erros){
        validatorCamposObrigatorios("CEP",cliente.getCep(), erros);
        validatorCamposObrigatorios("Logradouro",cliente.getLogradouro(), erros);
        validatorCamposObrigatorios("Bairro,",cliente.getBairro(), erros);
        validatorCamposObrigatorios("Cidade",cliente.getCidade(), erros);
        validatorCamposObrigatorios("Uf",cliente.getUf(), erros);
    }

    private void validatorCamposObrigatorios(String tipo, String campo, Errors errors){
        if(campo.isEmpty()){
            errors.rejectValue(tipo, "Campo obrigátorio");
        }
    }


}
