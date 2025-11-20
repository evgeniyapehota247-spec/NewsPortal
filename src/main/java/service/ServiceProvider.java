package service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import service.impl.NewsPortalUserSecurity;
import service.impl.NewsServiceImpl;

@AllArgsConstructor
public final class ServiceProvider {

    @Getter
    private static final ServiceProvider instance = new ServiceProvider();

    @Getter
    private UserSecurity userSecurity = new NewsPortalUserSecurity();

    @Getter
    private final NewsService newsService = new NewsServiceImpl();

    public ServiceProvider() {
    }

}
