package service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public final class ServiceProvider {

    @Getter
    private static final ServiceProvider instance = new ServiceProvider();

    @Getter
    private UserSecurity userSecurity = new NewsPortalUserSecurity();

    public ServiceProvider() {
    }

}
