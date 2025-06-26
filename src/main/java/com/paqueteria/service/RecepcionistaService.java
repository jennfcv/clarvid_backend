package com.paqueteria.service;

import com.paqueteria.domain.Authority;
import com.paqueteria.domain.Recepcionista;
import com.paqueteria.domain.User;
import com.paqueteria.repository.RecepcionistaRepository;
import com.paqueteria.repository.UserRepository;
import com.paqueteria.security.AuthoritiesConstants;
import com.paqueteria.service.dto.RecepcionistaDTO;
import com.paqueteria.service.mapper.RecepcionistaMapper;
import com.paqueteria.service.mapper.SucursalMapper;
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
public class RecepcionistaService {

    private final Logger log = LoggerFactory.getLogger(RecepcionistaService.class);
    private static final String ENTITY_NAME = "recepcionista";

    private final RecepcionistaRepository recepcionistaRepository;
    private final RecepcionistaMapper recepcionistaMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final SucursalMapper sucursalMapper;

    public RecepcionistaService(
        RecepcionistaRepository recepcionistaRepository,
        RecepcionistaMapper recepcionistaMapper,
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        MailService mailService,
        SucursalMapper sucursalMapper
    ) {
        this.recepcionistaRepository = recepcionistaRepository;
        this.recepcionistaMapper = recepcionistaMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.sucursalMapper = sucursalMapper;
    }

    public RecepcionistaDTO save(RecepcionistaDTO dto) {
        log.debug("Request to save Recepcionista : {}", dto);

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
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new BadRequestAlertException("Email es requerido", ENTITY_NAME, "emailempty");
        }

        // Verificar usuario existente
        String login = dto.getCi().toLowerCase();
        userRepository.findOneByLogin(login).ifPresent(existing -> {
            throw new BadRequestAlertException("Ya existe un usuario con este CI", ENTITY_NAME, "userexists");
        });

        // Crear usuario
        String password = RandomUtil.generatePassword();
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(dto.getNombre());
        user.setLastName(dto.getApellido());
        user.setEmail(dto.getEmail());
        user.setActivated(true);
        user.setLangKey("es");

        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.RECEPCIONISTA);
        user.setAuthorities(Set.of(authority));
        user = userRepository.save(user);

        // Crear recepcionista
        Recepcionista recepcionista = recepcionistaMapper.toEntity(dto);
        recepcionista.setUsuario(user);
        recepcionista.setSucursal(dto.getSucursal() != null ? sucursalMapper.toEntity(dto.getSucursal()) : null);
        recepcionista = recepcionistaRepository.save(recepcionista);

        // Enviar email
        mailService.sendCreationEmail(user, password);

        return recepcionistaMapper.toDto(recepcionista);
    }

    public RecepcionistaDTO update(RecepcionistaDTO dto) {
        log.debug("Request to update Recepcionista : {}", dto);
        Recepcionista recepcionista = recepcionistaMapper.toEntity(dto);
        recepcionista.setSucursal(dto.getSucursal() != null ? sucursalMapper.toEntity(dto.getSucursal()) : null);
        recepcionista = recepcionistaRepository.save(recepcionista);
        return recepcionistaMapper.toDto(recepcionista);
    }

    public Optional<RecepcionistaDTO> partialUpdate(RecepcionistaDTO dto) {
        log.debug("Request to partially update Recepcionista : {}", dto);
        return recepcionistaRepository
            .findById(dto.getId())
            .map(existing -> {
                recepcionistaMapper.partialUpdate(existing, dto);
                return existing;
            })
            .map(recepcionistaRepository::save)
            .map(recepcionistaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<RecepcionistaDTO> findAll(Pageable pageable) {
        return recepcionistaRepository.findAll(pageable).map(recepcionistaMapper::toDto);
    }

    public Page<RecepcionistaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return recepcionistaRepository.findAllWithEagerRelationships(pageable).map(recepcionistaMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RecepcionistaDTO> findOne(Long id) {
        return recepcionistaRepository.findOneWithEagerRelationships(id).map(recepcionistaMapper::toDto);
    }

    public void delete(Long id) {
        recepcionistaRepository.deleteById(id);
    }
}
