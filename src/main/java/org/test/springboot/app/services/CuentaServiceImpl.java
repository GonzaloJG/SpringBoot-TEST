package org.test.springboot.app.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.springboot.app.models.Banco;
import org.test.springboot.app.models.Cuenta;
import org.test.springboot.app.repositories.BancoRepository;
import org.test.springboot.app.repositories.CuentaRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CuentaServiceImpl implements CuentaService{

    private final CuentaRepository cuentaRepository;
    private final BancoRepository bancoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found id"));
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransaferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId)
                .orElseThrow(() -> new RuntimeException("not found bancoId"));
        return banco.getTotalTransferencia();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("not found cuentaId"));
        return cuenta.getSaldo();

    }

    @Override
    @Transactional
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen = cuentaRepository.findById(numCuentaOrigen)
                .orElseThrow(() -> new RuntimeException("not found numCuentaOrigen"));
        cuentaOrigen.debito(monto);
        cuentaRepository.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino)
                .orElseThrow(() -> new RuntimeException("not found numCuentaDestino"));
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId)
                .orElseThrow(() -> new RuntimeException("not found bancoId"));
        int totalTransferencias = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencias);
        bancoRepository.save(banco);
    }
}
