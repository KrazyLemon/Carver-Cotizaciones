package com.example.carver.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    //Inyeccion de dependencias
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }
    public void addNewUsuario(Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());
        if(usuarioOptional.isPresent()){
            throw new IllegalStateException("Email ya registrado");
        }
        usuarioRepository.save(usuario);
    }
    public void deleteUsuario(int usuarioId) {
        boolean exists = usuarioRepository.existsById(usuarioId);
        if(!exists){
            throw new IllegalStateException("Usuario con id " + usuarioId + " no existe");
        }
        usuarioRepository.deleteById(usuarioId);
    }

    @Transactional  
    public void updateUsuario(int usuarioId, String name, String lastName, String secondLastName, String email, String password) {
        //Verifica si existe el usuario
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new IllegalStateException(
                "Usuario con id " + usuarioId + " no existe"
        ));
        //Verifica si el nombre no es nulo y si es diferente al que ya esta registrado
        if(name != null && name.length() > 0 && !name.equals(usuario.getName())){
            usuario.setName(name);
        }
        if(lastName != null && lastName.length() > 0 && !lastName.equals(usuario.getLastName())){
            usuario.setLastName(lastName);
        }
        if(secondLastName != null && secondLastName.length() > 0 && !secondLastName.equals(usuario.getSecondLastName())){
            usuario.setSecondLastName(secondLastName);
        }
        if(email != null && email.length() > 0 && !email.equals(usuario.getEmail())){
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
            if(usuarioOptional.isPresent()){
                throw new IllegalStateException("Email ya registrado");
            }
            usuario.setEmail(email);
        }
        if(password != null && password.length() > 0 && !password.equals(usuario.getPassword())){
            usuario.setPassword(password);
        }
    }
}
