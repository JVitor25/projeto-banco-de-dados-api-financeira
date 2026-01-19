package com.apifinancas.service;

import com.apifinancas.exception.ValidationException;
import com.apifinancas.model.Fatura;
import com.apifinancas.repository.FaturaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository faturaRepository;

    public List<Fatura> findAll() {
        return faturaRepository.findAll();
    }

    public Optional<Fatura> findById(Long id) {
        return faturaRepository.findById(id);
    }

    public Fatura save(Fatura fatura) {
        Long idCartao = fatura.getCartaoCredito().getIdCartao();
        List<Fatura> listaFatura = faturaRepository.findByMesAnoAndCartao(
                fatura.getMesReferencia(),
                fatura.getAnoReferencia(),
                idCartao
        );
        if (!listaFatura.isEmpty())
            throw new ValidationException(
                    String.format("Já existe uma fatura para este cartão no período %s/%s.",
                            fatura.getMesReferencia(),
                            fatura.getAnoReferencia()
                    ));
        return faturaRepository.save(fatura);
    }

    public Optional<Fatura> update(Long id, Fatura faturaDetails) {
        return faturaRepository.findById(id).map(fatura -> {
            BeanUtils.copyProperties(faturaDetails, fatura, getNullPropertyNames(faturaDetails));
            return faturaRepository.save(fatura);
        });
    }

    public boolean deleteById(Long id) {
        if (faturaRepository.existsById(id)) {
            faturaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}