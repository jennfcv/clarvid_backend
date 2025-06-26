package com.paqueteria.service;

import com.paqueteria.domain.Authority;
import com.paqueteria.domain.Repartidor;
import com.paqueteria.domain.User;
import com.paqueteria.repository.RepartidorRepository;
import com.paqueteria.repository.UserRepository;
import com.paqueteria.security.AuthoritiesConstants;
import com.paqueteria.service.dto.RepartidorDTO;
import com.paqueteria.service.mapper.RepartidorMapper;
import com.paqueteria.web.rest.errors.BadRequestAlertException;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

@Service
@Transactional
public class RepartidorService {

    private final Logger log = LoggerFactory.getLogger(RepartidorService.class);
    private static final String ENTITY_NAME = "repartidor";

    private final RepartidorRepository repartidorRepository;
    private final RepartidorMapper repartidorMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public RepartidorService(
        RepartidorRepository repartidorRepository,
        RepartidorMapper repartidorMapper,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        MailService mailService
    ) {
        this.repartidorRepository = repartidorRepository;
        this.repartidorMapper = repartidorMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public RepartidorDTO save(RepartidorDTO dto) {
        log.debug("Request to save Repartidor : {}", dto);

        // Validaciones
        if (dto.getCi() == null || dto.getCi().isEmpty()) {
            throw new BadRequestAlertException("CI es requerido", ENTITY_NAME, "ciempty");
        }
        if (dto.getNombre() == null || dto.getNombre().isEmpty()) {
            throw new BadRequestAlertException("Nombre es requerido", ENTITY_NAME, "nombreempty");
        }
        if (dto.getApellido() == null || dto.getApellido().isEmpty()) {
            throw new BadRequestAlertException("Apellido es requerido", ENTITY_NAME, "apellidoempty");
        }
        if (dto.getEmail() == null || !dto.getEmail().matches(".+@.+\\..+")) {
            throw new BadRequestAlertException("Email inválido", ENTITY_NAME, "emailinvalid");
        }

        // Verificar usuario existente por CI
        String login = dto.getCi().toLowerCase();
        userRepository.findOneByLogin(login).ifPresent(existing -> {
            throw new BadRequestAlertException("Ya existe un usuario con este CI", ENTITY_NAME, "userexists");
        });

        // Verificar email único
        userRepository.findOneByEmailIgnoreCase(dto.getEmail()).ifPresent(existing -> {
            throw new BadRequestAlertException("Email ya registrado", ENTITY_NAME, "emailexists");
        });

        // Crear usuario
        String password = RandomUtil.generatePassword();
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(dto.getNombre());
        user.setLastName(dto.getApellido());
        user.setEmail(dto.getEmail().toLowerCase());
        user.setActivated(true);
        user.setLangKey("es");

        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.REPARTIDOR);
        user.setAuthorities(Set.of(authority));
        user = userRepository.save(user);

        // Crear repartidor
        Repartidor repartidor = repartidorMapper.toEntity(dto);
        repartidor.setUsuario(user);
        repartidor.setEmail(dto.getEmail().toLowerCase());
        repartidor = repartidorRepository.save(repartidor);

        // Enviar email
        mailService.sendCreationEmail(user, password);

        return repartidorMapper.toDto(repartidor);
    }

    public RepartidorDTO update(RepartidorDTO dto) {
        log.debug("Request to update Repartidor : {}", dto);
        Repartidor repartidor = repartidorMapper.toEntity(dto);
        repartidor = repartidorRepository.save(repartidor);
        return repartidorMapper.toDto(repartidor);
    }

    public Optional<RepartidorDTO> partialUpdate(RepartidorDTO dto) {
        log.debug("Request to partially update Repartidor : {}", dto);
        return repartidorRepository
            .findById(dto.getId())
            .map(existing -> {
                repartidorMapper.partialUpdate(existing, dto);
                return existing;
            })
            .map(repartidorRepository::save)
            .map(repartidorMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<RepartidorDTO> findAll(Pageable pageable) {
        return repartidorRepository.findAll(pageable).map(repartidorMapper::toDto);
    }

    public Page<RepartidorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return repartidorRepository.findAllWithEagerRelationships(pageable).map(repartidorMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RepartidorDTO> findOne(Long id) {
        return repartidorRepository.findOneWithEagerRelationships(id).map(repartidorMapper::toDto);
    }

    public void delete(Long id) {
        repartidorRepository.deleteById(id);
    }
}
