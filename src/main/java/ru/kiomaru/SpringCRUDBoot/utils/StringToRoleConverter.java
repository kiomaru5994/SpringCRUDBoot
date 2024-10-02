package ru.kiomaru.SpringCRUDBoot.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kiomaru.SpringCRUDBoot.model.Role;
import ru.kiomaru.SpringCRUDBoot.repository.RoleRepository;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    final RoleRepository roleRepositoryImpl;

    public StringToRoleConverter(RoleRepository roleRepositoryImpl) {
        this.roleRepositoryImpl = roleRepositoryImpl;
    }

    @Override
    public Role convert(String source) {
        return roleRepositoryImpl.findByRoleName(source);
    }
}
