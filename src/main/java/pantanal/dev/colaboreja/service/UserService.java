package pantanal.dev.colaboreja.service;


import lombok.RequiredArgsConstructor;
import pantanal.dev.colaboreja.model.ChangePasswordRequest;
import pantanal.dev.colaboreja.model.User;
import pantanal.dev.colaboreja.repository.UserRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // Checar se sua senha está certa
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Senha erradda");
        }
        // Checar se as senha se coincidem
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Senha não são iguais");
        }

        // Atualizar senha
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Salva nova senha
        repository.save(user);
    }
}