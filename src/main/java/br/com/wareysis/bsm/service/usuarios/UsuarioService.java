package br.com.wareysis.bsm.service.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.bsm.core.service.MessageService;
import br.com.wareysis.bsm.dto.tipos.TipoPerfilUsuarioDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioCreateDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioResponseDto;
import br.com.wareysis.bsm.dto.usuarios.UsuarioUpdateDto;
import br.com.wareysis.bsm.entity.usuario.Usuario;
import br.com.wareysis.bsm.exceptions.usuarios.UsuarioException;
import br.com.wareysis.bsm.mapper.usuarios.UsuarioMapper;
import br.com.wareysis.bsm.repository.usuarios.UsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class UsuarioService {

    @Inject
    Logger log;

    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    MessageService messageService;

    @Inject
    UsuarioPerfilService usuarioPerfilService;

    @Inject
    UsuarioFirebaseService usuarioFirebaseService;

    @Inject
    TipoPerfilUsuarioService tipoPerfilUsuarioService;

    @Inject
    UsuarioRepository usuarioRepository;

    private static final String USUARIO_UID_NOT_FOUND = "usuario.uid.not.found";

    @Transactional
    public UsuarioResponseDto create(UsuarioCreateDto dto) {

        UserRecord userRecord = usuarioFirebaseService.createUserInFirebase(dto);

        log.info(String.format("BsmApp -> APPLICATION: Salvar usuário -> email: %s, nome: %s", dto.email(), dto.nomeCompleto()));

        Usuario usuario = persistNovoUsuario(dto, userRecord);
        usuarioPerfilService.persistNovosPerfis(usuario, dto.perfis());

        List<TipoPerfilUsuarioDto> perfilDtos = tipoPerfilUsuarioService.findTipoPerfisDto(usuario.getId());

        return usuarioMapper.toDto(usuario, perfilDtos);

    }

    @Transactional
    public UsuarioResponseDto update(UsuarioUpdateDto dto) {

        usuarioFirebaseService.updateUserInFirebase(dto);

        log.info(String.format("BsmApp -> APPLICATION: Alterar usuário com uid %s", dto.id()));

        Usuario usuario = usuarioRepository.findByIdOptional(dto.id())
                .orElseThrow(() -> new UsuarioException(messageService.getMessage(USUARIO_UID_NOT_FOUND, dto.id()), Status.BAD_REQUEST));

        updateUsuario(dto, usuario);
        usuarioPerfilService.updatePerfis(usuario, dto.perfis());

        usuarioRepository.flush();

        List<TipoPerfilUsuarioDto> perfilDtos = tipoPerfilUsuarioService.findTipoPerfisDto(usuario.getId());

        return usuarioMapper.toDto(usuario, perfilDtos);

    }

    @Transactional
    public void delete(UUID id) {

        if (id == null) {
            throw new UsuarioException(messageService.getMessage("usuario.uid.null"), Status.BAD_REQUEST);
        }

        usuarioFirebaseService.deleteUserInFirebase(id.toString());

        Usuario usuario = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new UsuarioException(messageService.getMessage(USUARIO_UID_NOT_FOUND, id), Status.BAD_REQUEST));

        usuario.delete();

        usuarioRepository.flush();
    }

    public List<UsuarioResponseDto> findAll() {

        List<UsuarioResponseDto> usuarioResponseDtoList = new ArrayList<>();

        List<Usuario> usuarioList = usuarioRepository.listAll();

        for (Usuario usuario : usuarioList) {

            List<TipoPerfilUsuarioDto> perfilDtos = tipoPerfilUsuarioService.findTipoPerfisDto(usuario.getId());
            UsuarioResponseDto usuarioResponseDto = usuarioMapper.toDto(usuario, perfilDtos);
            usuarioResponseDtoList.add(usuarioResponseDto);

        }

        return usuarioResponseDtoList;
    }

    public UsuarioResponseDto findById(UUID id) {

        Usuario usuario = usuarioRepository.findByIdOptional(id)
                .orElseThrow(() -> new UsuarioException(messageService.getMessage(USUARIO_UID_NOT_FOUND, id), Status.BAD_REQUEST));

        List<TipoPerfilUsuarioDto> perfilDtos = tipoPerfilUsuarioService.findTipoPerfisDto(usuario.getId());

        return usuarioMapper.toDto(usuario, perfilDtos);
    }

    public UsuarioResponseDto findByEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioException(messageService.getMessage("usuario.email.not.found", email), Status.BAD_REQUEST));

        List<TipoPerfilUsuarioDto> perfilDtos = tipoPerfilUsuarioService.findTipoPerfisDto(usuario.getId());

        return usuarioMapper.toDto(usuario, perfilDtos);
    }

    public List<UsuarioResponseDto> findByNome(String nome) {

        if (StringUtils.isBlank(nome)) {
            return List.of();
        }

        List<UsuarioResponseDto> usuarioResponseDtoList = new ArrayList<>();

        List<Usuario> usuarioList = usuarioRepository.findByNome(nome);

        for (Usuario usuario : usuarioList) {

            List<TipoPerfilUsuarioDto> perfilDtos = tipoPerfilUsuarioService.findTipoPerfisDto(usuario.getId());
            UsuarioResponseDto usuarioResponseDto = usuarioMapper.toDto(usuario, perfilDtos);
            usuarioResponseDtoList.add(usuarioResponseDto);

        }

        return usuarioResponseDtoList;
    }

    private void updateUsuario(UsuarioUpdateDto dto, Usuario usuario) {

        if (!StringUtils.isBlank(dto.email())) {
            usuario.setEmail(dto.email());
        }

        if (!StringUtils.isBlank(dto.nomeCompleto())) {
            usuario.setNomeCompleto(dto.nomeCompleto());
        }

        if (!StringUtils.isBlank(dto.cpf())) {
            usuario.setCpf(dto.cpf());
        }

        if (!StringUtils.isBlank(dto.telefone())) {
            usuario.setTelefone(dto.telefone());
        }

        if (dto.habilitado() != null) {
            usuario.setHabilitado(dto.habilitado());
        }

        if (dto.alterarSenha() != null) {
            usuario.setAlterarSenha(dto.alterarSenha());
        }
    }

    private Usuario persistNovoUsuario(UsuarioCreateDto dto, UserRecord userRecord) {

        Usuario usuario = new Usuario();

        usuario.setId(UUID.fromString(userRecord.getUid()));
        usuario.setEmail(userRecord.getEmail());
        usuario.setNomeCompleto(userRecord.getDisplayName());
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefone());
        usuario.setEmailVerificado(false);
        usuario.setHabilitado(true);
        usuario.setAlterarSenha(false);

        usuario.persist();

        return usuario;

    }

}
