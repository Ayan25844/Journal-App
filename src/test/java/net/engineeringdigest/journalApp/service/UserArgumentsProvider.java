package net.engineeringdigest.journalApp.service;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.*;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("jodhu").password("shyam").build()),
                Arguments.of(User.builder().username("modhu").password("modhu").build()));
    }
}
