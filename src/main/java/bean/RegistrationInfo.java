package bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegistrationInfo {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;

    private RegistrationInfo(RegBuilder builder) {
        firstName = builder.getFirstName();
        lastName = builder.getLastName();
        email = builder.getEmail();
        password = builder.getPassword();
    }

    @Data
    @NoArgsConstructor
    public static class RegBuilder implements Builder<RegistrationInfo> {

        private String firstName;
        private String lastName;
        private String email;
        private String password;

        public RegBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public RegBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RegBuilder email(String email) {
            this.email = email;
            return this;
        }

        public RegBuilder password(String password) {
            this.password = password;
            return this;
        }

        @Override
        public RegistrationInfo build() {
            return new RegistrationInfo(this);
        }
    }
}
